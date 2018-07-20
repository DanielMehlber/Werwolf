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
import javax.swing.JOptionPane;

import game.Spieler;
import net.InetDataFormatter;

public class Phone extends JInternalFrame {

	/**
	 * Create the panel.
	 */
	private TextArea chat;
	private GameWindow window;
	
	private TextArea schreiben;
	private List spieler_panel;
	
	private boolean gesperrt;
	
	public Phone(GameWindow window) {
		this.window = window;
		gesperrt = false;
		window.getGame().setPhone(this);
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
		
		spieler_panel = new List();
		spieler_panel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = spieler_panel.getSelectedItem();
				schreiben.insert(name, schreiben.getCaretPosition());
			}
		});
		spieler_panel.setFont(new Font("DialogInput", Font.PLAIN, 12));
		spieler_panel.setBackground(Color.LIGHT_GRAY);
		spieler_panel.setBounds(152, 84, 190, 468);
		getContentPane().add(spieler_panel);
		spieler_panel.setVisible(false);
		
		Button senden = new Button("Senden");
		senden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				senden();
			}
		});
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
		chat.setFont(new Font("DialogInput", Font.PLAIN, 13));
		chat.setBounds(29, 84, 313, 468);
		getContentPane().add(chat);
		chat.setEditable(false);
		
		schreiben = new TextArea();
		schreiben.setFont(new Font("DialogInput", Font.PLAIN, 17));
		schreiben.setText("Hallo Ihr alle");
		schreiben.setBounds(29, 552, 313, 91);
		getContentPane().add(schreiben);
		schreiben.setColumns(1);
		schreiben.setRows(1);
		
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Phone.class.getResource("/res/cell-phone.png")));
		label.setBounds(10, 0, 373, 712);
		getContentPane().add(label);
		
		spielerListeAktualisieren();
		
	}
	
	public void addMessage(String absender, String inhalt) {
		String seperator = "----------------------------------";
		String text =  "\n" + "[@"+absender+"]: \n"+inhalt+"\n"+seperator;
		chat.setText(chat.getText() + text);
	}
	
	public void senden() {
		if(gesperrt)
			return;
		String nachricht = schreiben.getText();
		nachricht = "["+window.getGame().getSpieler().getSpielerDaten().getName()+"]"+nachricht;
		window.getGame().getSpieler().chatSchreiben(nachricht);
		schreiben.setText("");
	}
	
	public void empfangen(String nachricht) {
		if(gesperrt)
			return;
		int one = nachricht.indexOf("[");
		int two = nachricht.indexOf("]");
		String absender = nachricht.substring(one+1, two);
		String inhalt = nachricht.substring(two + 1);
		addMessage(absender, inhalt);
		if(inhalt.contains("@"+window.getGame().getSpieler().getSpielerDaten().getName())) {
			//TODO: Alternative ohne Ton!
			JOptionPane.showMessageDialog(null, "Du wurdest im öffentlichen Chat erwähnt", "Chataktivität", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void spielerListeAktualisieren() {
		spieler_panel.removeAll();
		for(int i = 0; i < window.getGame().getSpielDaten().getSpielerListe().size(); i++) {
			String name = window.getGame().getSpielDaten().getSpielerListe().get(i).getSpielerDaten().getName();
			spieler_panel.add("@"+name);
		}
	}
	
	public void sperren(boolean b) {
		gesperrt = b;
		schreiben.setEnabled(!b);
	}
	
	
}
