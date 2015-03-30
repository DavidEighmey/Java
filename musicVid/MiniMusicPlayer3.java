import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 {
	static JFrame f = new JFrame("Music Video");
	static MyDrawPanel m1;

	public static void main(String[] args) {
		MiniMusicPlayer3 mini = new MiniMusicPlayer3();
		mini.go();
	}//main

	public void setUpGui() {
		m1 = new MyDrawPanel();
		f.setContentPane(m1);
		f.setBounds(30,30,300,300);
		
		f.setSize(300,300);
		f.setVisible(true);
	}//SetUpGui

	public void go(){
		setUpGui();

		try{

			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addControllerEventListener(m1, new int[] {127});
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();

			int r = 0;
			for (int i = 0; i < 60; i +=4){
				r = (int) ((Math.random() * 50) + 1);
				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i));
				track.add(makeEvent(128,1,r,100,i+2));
			}

			sequencer.setSequence(seq);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}//end go

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a, tick);

		} catch (Exception ex){
			
		}
		return event;
	}//end makeEvent


	class MyDrawPanel extends JPanel implements ControllerEventListener {
		boolean msg = false;

		public void controlChange(ShortMessage event){
			msg = true;
			repaint();
		}//end controlChange

		public void paintComponent(Graphics g) {
			if(msg){
				int red = (int) (Math.random() * 250);
				int green = (int) (Math.random() *250);
				int blue = (int) (Math.random() * 250);

				g.setColor(new Color(red, green, blue));

				int ht = (int) ((Math.random() *120) +10);
				int width = (int) ((Math.random() * 120) +10);

				int x = (int) ((Math.random() *40) + 10);
				int y = (int) ((Math.random() * 40) + 10);

				g.fillOval(x,y,ht,width);
				msg = false;
			}
		}//end paintComponent
	}//end MyDrawPanel
}//end MiniMusicPlayer3
