import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.swing.event.*;

public class BeatBox {
	
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList; //store checkboxes in an ArrayList
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;

	//names of instruments that we have avaliable
	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
								"Hand Clap", "High Tom" , "Hi Bongo", "Maracas" , "Whistle" , "Low Conga", 
								"Cow Bell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};

	//the actual key for the sound
	int[] instrument = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

	public static void main(String[] args) {
		new BeatBox().buildGUI();
	}

	public void buildGUI() {
		theFrame = new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		//create an empty border gives us a argin between edges of the panel and components
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		checkboxList= new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);

		//build buttons & addActionListener
		JButton start = new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);

		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);

		JButton upTempo = new JButton("Tempo Up");
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);

		JButton downTempo = new JButton("Tempo down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);

		JButton save = new JButton("Save");
		save.addActionListener(new MySendListener());
		buttonBox.add(save);

		JButton load = new JButton("Load");
		load.addActionListener(new MyReadInListener());
		buttonBox.add(load);


		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for(int i = 0; i <16; i++){
			nameBox.add(new Label(instrumentNames[i]));
		}

		background.add(BorderLayout.EAST, buttonBox);
		background.add(BorderLayout.WEST, nameBox);

		theFrame.getContentPane().add(background);

		GridLayout grid = new GridLayout(16,16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);

		//make checkboxes and set to false.. and add them to ArrayList & gui panel
		for (int i = 0; i <256; i++){
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}

		setUpMidi();

		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	/* setUpMidi- The MIDI set-up stuff for getting the sequencer,sequence and track
	 */
	public void setUpMidi(){
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);

		} catch(Exception e){
			e.printStackTrace();
		}

	}

	/* buildTrackAndStart - Make a 16 element array to hold values for instrument across all 16 beats
	 * if instrument is not to be played then put a zer0
	 */
	public void buildTrackAndStart(){
		int[] trackList = null;

		sequence.deleteTrack(track);//delete old track
		track = sequence.createTrack();

		for(int i = 0; i <16; i++){ //16 rows and sounds
			trackList = new int[16];

			int key = instrument[i];//set the key that represents which instruments 

			for(int j = 0; j <16 ; j++){ //do this for each of the beats
				JCheckBox jc = checkboxList.get(j+16*i);
				if(jc.isSelected()){
					trackList[j] = key;
				} else {
					trackList[j] = 0;
				}
			}
			makeTracks(trackList);//for this instrument and for all 16 beats, make events and add to track
			track.add(makeEvent(172,1,127,0,16));
		}
		track.add(makeEvent(192,9,1,0,15)); 
		try{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY); //loop continously 
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/* Create classes for the ActionListener 
	 *
	 */
	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a){
			buildTrackAndStart();
		}
	}//close inner class 

	public class MyStopListener implements ActionListener {
		public void actionPerformed(ActionEvent a){
			sequencer.stop();
		}
	}//close inner class

	public class MyUpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor *1.03));
		}
	}//close inner class
	public class MyDownTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor *.97));
		}
	}//close inner class

	/* makeTracks - this makes events for one instrument at a time for all 16 beats
	 * Each index in array will hold the key of that instrument or a zero.
	 */
	public void makeTracks(int[] list){
		for(int i = 0; i < 16; i++){
			int key = list[i];

			if (key!= 0){
				//note ON and OFF
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
			}
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
		MidiEvent event = null;
		try{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);

		} catch (Exception e){
			e.printStackTrace();
		}
		return event;
	}

	public class MySendListener implements ActionListener {
		public void actionPerformed(ActionEvent ev){
			boolean[] checkboxState = new boolean[256];

			for (int i = 0; i < 256; i++){
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if(check.isSelected()){
					checkboxState[i] = true;
				}
			}

			try {
				FileOutputStream fileStream = new FileOutputStream(new File("checkbox.ser"));
				ObjectOutputStream os = new ObjectOutputStream(fileStream);
				os.writeObject(checkboxState);
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}//close MySendListerner 

	public class MyReadInListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			boolean[] checkboxState = null;

			try{
				FileInputStream fileIn = new FileInputStream(new File("checkbox.ser"));
				ObjectInputStream is = new ObjectInputStream(fileIn);
				checkboxState = (boolean[]) is.readObject();

			} catch (Exception ex){
				ex.printStackTrace();
			}

			for(int i = 0; i < 256; i++){
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if(checkboxState[i]) {
					check.setSelected(true);
				} else {
					check.setSelected(false);
				}
			}

			sequencer.stop();
			buildTrackAndStart();

		}
	}
}