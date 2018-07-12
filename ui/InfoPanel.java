package ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import karten.Kreatur;

import javax.swing.JSeparator;
import java.awt.Button;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoPanel extends JPanel {
	private JLabel lblIdentity;
	public Button exit;
	private GameWindow window;
	/**
	 * Create the panel.
	 */
	public InfoPanel(GameWindow window) {
		this.window = window;
		setBackground(Color.DARK_GRAY);
		setBounds(0,0,500,800);
		setLayout(null);
		
		JLabel lblYouAre = new JLabel("Du bist...");
		lblYouAre.setForeground(Color.WHITE);
		lblYouAre.setFont(new Font("Perpetua Titling MT", Font.BOLD, 17));
		lblYouAre.setBounds(10, 24, 300, 37);
		add(lblYouAre);
		
		lblIdentity = new JLabel("ERROR");
		lblIdentity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdentity.setForeground(Color.RED);
		lblIdentity.setFont(new Font("Stencil", Font.PLAIN, 37));
		lblIdentity.setBounds(36, 60, 408, 48);
		add(lblIdentity);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.GRAY);
		separator.setBounds(36, 119, 408, 28);
		add(separator);
		
		exit = new Button("X");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				exit.requestFocus();
			}
		});
		exit.setBounds(420, 10, 70, 22);
		add(exit);
		
		//ERROR: NullPointerException
		//setKreatur(window.getGame().getSpieler().getSpielerDaten().getKreatur());
	}
	
	public Button getExitButton() {
		return exit;
	}
	
	public void werwolf() {
		JLabel ww_pic = new JLabel("");
		ww_pic.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/werwolf_info.png")));
		ww_pic.setBounds(20, 158, 200, 200);
		add(ww_pic);
		
		lblIdentity.setText("Ein Werwolf");
		
		JTextArea ww_info = new JTextArea();
		ww_info.setEditable(false);
		ww_info.setForeground(Color.WHITE);
		ww_info.setText("Eine Kreatur der Nacht.\r\nNiemand au\u00DFerhalb deines\r\nRudels wei\u00DF von dir, und \r\ndas sollte besser auch so bleiben.\r\n\r\nTot allen Nicht-Werw\u00F6lfen!");
		ww_info.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		ww_info.setBackground(Color.DARK_GRAY);
		ww_info.setBounds(230, 158, 260, 200);
		add(ww_info);
		
		JTextArea ww_anleitung = new JTextArea();
		ww_anleitung.setEditable(false);
		ww_anleitung.setText("Ein Werwolf geht niemals Allein!\r\nDu kennst dein Rudel und gegenseitiger Schutz ist ein Gesetz.\r\n\r\nIn der Nacht stimmt ihr f\u00FCr eure Beute ab:\r\nKlicke dazu mit Rechsklick auf eine Karte deiner Wahl.\r\nAchtung: Bein einer Pattsituation erh\u00E4llt einer von euch\r\neine Doppelstimme.\r\n\r\nAm Tag versuchen die Bewohner euch dranzukriegen!\r\nSeid geschickt und versucht den Verdacht von euch abzulenken!\r\n\r\nViel Gl\u00FCck und guten Appetit!");
		ww_anleitung.setForeground(Color.WHITE);
		ww_anleitung.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		ww_anleitung.setBackground(Color.DARK_GRAY);
		ww_anleitung.setBounds(10, 376, 480, 208);
		add(ww_anleitung);
		
		List list = new List();
		list.setForeground(Color.RED);
		list.setBackground(Color.LIGHT_GRAY);
		list.setFont(new Font("Dialog", Font.PLAIN, 21));
		list.setBounds(10, 590, 434, 141);
		add(list);
		list.add("Dein Rudel:");
	}
	
	public void hexe() {
		lblIdentity.setText("Adella, die Hexe");
		JLabel hexe_pic = new JLabel("");
		hexe_pic.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/AdellaHexe.png")));
		hexe_pic.setBounds(29, 137, 200, 133);
		add(hexe_pic);
		
		JLabel hexe_potion_pic = new JLabel("");
		hexe_potion_pic.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/potion.jpg")));
		hexe_potion_pic.setBounds(29, 462, 200, 253);
		add(hexe_potion_pic);
		
		JTextArea hexe_beschreibung = new JTextArea();
		hexe_beschreibung.setEditable(false);
		hexe_beschreibung.setForeground(Color.WHITE);
		hexe_beschreibung.setText("Einst fl\u00FCchtete Adella aus den teifen des Waldes vor der \r\nOberhexe, und suchte unbemerkt asyl bei den Dorfbewohnern.\r\n Und da die Bewohner nicht die hellsten K\u00F6pfe sind, f\u00E4llt ihnen\r\n auch nicht auf, dass du schon 236 Jahre alt bist. \r\nJede nacht ziehst du um die H\u00E4user und riskierst\r\n dabei erwischt zu werden. Du tr\u00E4gst immer 2 Tr\u00E4nke bei dir:\r\nEinen Todes, und einen Schutztrank");
		hexe_beschreibung.setFont(new Font("Papyrus", Font.PLAIN, 16));
		hexe_beschreibung.setBackground(Color.DARK_GRAY);
		hexe_beschreibung.setBounds(29, 273, 461, 195);
		add(hexe_beschreibung);
		
		JTextArea hexe_anleitung = new JTextArea();
		hexe_anleitung.setEditable(false);
		hexe_anleitung.setText("In jeder nacht kannst\r\ndu ein Werwolfopfer\r\nsch\u00FCtzen (auch dich selbst),\r\nsowie jemanden t\u00F6ten\r\n\r\nKlicke dazu mit rechtsklick auf\r\ndie gew\u00FCnschte Karte und w\u00E4hle.\r\nVergiss nicht: Du bist auf der\r\nSeite der Dorfbewohner");
		hexe_anleitung.setForeground(Color.WHITE);
		hexe_anleitung.setFont(new Font("Papyrus", Font.PLAIN, 16));
		hexe_anleitung.setBackground(Color.DARK_GRAY);
		hexe_anleitung.setBounds(239, 462, 235, 253);
		add(hexe_anleitung);
	}
	
	public void setKreatur(Kreatur k) {
		switch(k) {
		case BUERGER:{break;}
		case HEXE: {hexe(); break;}
		case SEHERIN: {break;}
		case WERWOLF: {werwolf(); break;}
		case ARMOR: {break;}
		case JAEGER: {break;}
		}
	}
	
	
}
