import javax.sound.midi.*;

public class MiniMiniMusicApp {
	
	public static void main(String[] args) {
		MiniMiniMusicApp mini = new MiniMiniMusicApp();
		mini.play();
	}

	public void play() {

		try{
			Sequencer player = MidiSystem.getSequencer();//get Seq
			player.open();//open it

			Sequence seq = new Sequence(Sequence.PPQ, 4);

			Track track = seq.createTrack(); //ask for track

			ShortMessage a = new ShortMessage(); //midi events inside track
			a.setMessage(144, 1, 44, 100);
			MidiEvent noteOn = new MidiEvent(a, 1);
			track.add(noteOn);

			ShortMessage b = new ShortMessage();
			b.setMessage(128, 1, 44, 100);
			MidiEvent noteOff = new	MidiEvent(b, 16);
			track.add(noteOff);

			player.setSequence(seq);

			player.start();


		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}