import javax.sound.midi.*;

public class MusicTest1 {
	
	public void play() {

		try{
			Sequencer sequencer = MidiSystem.getSequencer(); //create our sequencer object
			System.out.println("We got our Sequencer");
		
		} catch(MidiUnavailableException ex) {
			System.out.println("bummer");
		}
	}

	public static void main(String[] args) {
		MusicTest1 mt = new MusicTest1();

		mt.play();

	}
}