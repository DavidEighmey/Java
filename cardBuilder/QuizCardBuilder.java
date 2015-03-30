import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardBuilder {

	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;

	public static void main(String[] args) {
		QuizCardBuilder builder = new QuizCardBuilder();
		builder.go();
	}
	/* go - gui code to add to our card builder
	 *
	 */
	public void go() {

		frame = new JFrame("Quiz Card Builder");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);

		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);

		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);

		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JButton nextButton = new JButton("Next Card");

		cardList = new ArrayList<QuizCard>();

		JLabel qLabel = new JLabel("Question: ");
		JLabel aLabel = new JLabel("Answer: ");

		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);

		nextButton.addActionListener(new NextCardListener());

		JMenuBar menuBar = new JMenuBar(); //make menu bar
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New"); //new item
		JMenuItem saveMenuItem = new JMenuItem("Save"); //save item
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());

		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
	}//end go

	public class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev){

			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	}//end NextCardListener

	public class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);

			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());//bring file dialog box& allowes user to pick where
		}
	}//end SaveMenuListener

	public class NewMenuListener implements ActionListener {
		public void  actionPerformed(ActionEvent ev) {
			cardList.clear();
			clearCard();
		}
	}//end NewMenuListener

	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}//end clearCard

	/* saveFile - actually file writing. 
	 *
	 */
	private void saveFile(File file) {
		try {
			//buffered writing to be more effective at file saving
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(QuizCard card:cardList){
				//write out 1 card per line question / answer
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close();
		} catch (IOException ex){
			System.out.println("Couldn't write the cardlist out");
			ex.printStackTrace();
		}
	}//end saveFile
}//end QuizCardBuilder