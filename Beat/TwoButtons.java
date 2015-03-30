import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TwoButtons {

	JFrame frame;
	JLabel label;

	public static void main (String[] args) {
		TwoButtons gui = new TwoButtons();
		gui.go();

	}

	public void go(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton labelButton = new JButton("Change Text");
		labelButton.addActionListener(new LabelListener());

		JButton colorButton = new JButton("Change Circle");
		colorButton.addActionListener(new ColorListener());

		label = new JLabel("I Am A Label");
		MyDrawPanel drawPanael = new MyDrawPanel();

		frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
		frame.getContentPane().add(BorderLayout.CENTER, drawPanael);
		frame.getContentPane().add(BorderLayout.EAST, labelButton);
		frame.getContentPane().add(BorderLayout.WEST, label);
		frame.setSize(500,500);
		frame.setVisible(true);
	}

	class LabelListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			label.setText("hello from beyond");
		}

	}

	class ColorListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			frame.repaint();
		}
	}

}