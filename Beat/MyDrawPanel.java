import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyDrawPanel extends JPanel {
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

		int red = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		Color startColor = new Color(red, blue, green);
	
		red = (int) (Math.random() * 256);
		blue = (int) (Math.random() * 256);
		green = (int) (Math.random() * 256);
		Color endColor = new Color(red, blue, green);

		GradientPaint gradient = new GradientPaint(70,70, startColor, 150, 150, endColor);
		g2d.setPaint(gradient);
		g2d.fillOval(70,70,100,100);
	}
}
