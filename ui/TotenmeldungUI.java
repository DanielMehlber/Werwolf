package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Label;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TotenmeldungUI extends JInternalFrame {

	/**
	 * Create the panel.
	 */
	JLabel lblName;
	JTextArea text;
	public TotenmeldungUI() {
		setBorder(null);
		setTitle("Totenmeldung");
		setClosable(true);
		setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		
		lblName = new JLabel("ein_spieler ist gestorben");
		lblName.setForeground(Color.RED);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Impact", Font.PLAIN, 22));
		lblName.setBounds(10, 54, 352, 52);
		getContentPane().add(lblName);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TotenmeldungUI.class.getResource("/res/grabstein.jpg")));
		label.setBounds(0, 0, 388, 243);
		getContentPane().add(label);
		
		text = new JTextArea();
		text.setForeground(Color.WHITE);
		text.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 16));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setBackground(Color.DARK_GRAY);
		text.setBounds(10, 256, 368, 233);
		text.setEditable(false);
		getContentPane().add(text);

	}
	
	public void setSpielerName(String name) {
		lblName.setText(name + " ist gestorben");
	}
	
	public void setText(String _text, String identity) {
		text.setText(_text + "\n"+ identity);
	}
}
