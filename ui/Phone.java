package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import game.Spieler;

public class Phone extends JInternalFrame {

	/**
	 * Create the panel.
	 */
	private TextArea chat;
	public Phone() {
		try {
			setIcon(true);
		} catch (PropertyVetoException e1) {
			
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setFrameIcon(new ImageIcon(Phone.class.getResource("/res/chat_icon.png")));
		setIconifiable(true);
		setBorder(null);
		setBounds(50,50,380, 740);
		setTitle("Chat");
		setBackground(new Color(0,0,0,0));
		setOpaque(false);
		getContentPane().setLayout(null);
		setBackground(new Color(0.0f, 0.0f, 0.0f));
		
		List spieler_panel = new List();
		spieler_panel.setFont(new Font("DialogInput", Font.PLAIN, 12));
		spieler_panel.setBackground(Color.LIGHT_GRAY);
		spieler_panel.setBounds(152, 84, 190, 468);
		getContentPane().add(spieler_panel);
		spieler_panel.setVisible(false);
		
		Button senden = new Button("Senden");
		senden.setBounds(152, 660, 70, 22);
		getContentPane().add(senden);
		
		Button btn_spieler_panel = new Button("<< Spieler >>");
		btn_spieler_panel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(spieler_panel.isVisible()) {
					spieler_panel.setVisible(false);
				}else {
					spieler_panel.setVisible(true);
				}
			}
		});
		btn_spieler_panel.setBounds(243, 56, 91, 22);
		getContentPane().add(btn_spieler_panel);
		
		
		
		chat = new TextArea();
		chat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chat.setBounds(29, 84, 313, 468);
		getContentPane().add(chat);
		
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("DialogInput", Font.PLAIN, 17));
		textArea.setText("Hallo Ihr alle");
		textArea.setBounds(29, 552, 313, 91);
		getContentPane().add(textArea);
		textArea.setColumns(1);
		textArea.setRows(1);
		
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Phone.class.getResource("/res/cell-phone.png")));
		label.setBounds(10, 0, 373, 712);
		getContentPane().add(label);
		
		for(int i = 0; i < 100; i++) {
			addMessage("ICH", "Hey, \nIch bins");
		}
	}
	
	public void addMessage(String absender, String inhalt) {
		String seperator = "____________________________________";
		String text =  "\n" + "("+absender+"): "+inhalt+"\n"+seperator;
		chat.setText(chat.getText() + text);
	}
	
	public void setSpieler(ArrayList<Spieler> spieler) {}
	
}
