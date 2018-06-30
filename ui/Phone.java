package ui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.List;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;

public class Phone extends JPanel {

	/**
	 * Create the panel.
	 */
	public Phone() {
		setBackground(Color.BLACK);
		setLayout(null);
		
		TextArea chat = new TextArea();
		chat.setBounds(29, 50, 313, 502);
		add(chat);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("DialogInput", Font.PLAIN, 17));
		textArea.setText("Hallo Ihr alle");
		textArea.setBounds(29, 552, 313, 91);
		add(textArea);
		textArea.setColumns(1);
		textArea.setRows(1);
		
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Phone.class.getResource("/res/cell-phone.png")));
		label.setBounds(10, 0, 373, 712);
		add(label);

	}
}
