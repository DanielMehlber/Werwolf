package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;

import game.Spieler;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import karten.Kreatur;
import java.awt.Dimension;

public class Karte extends JPanel {

	private JLabel icon;
	private JLabel lblname;
	private JLabel blood;
	
	private HauptSpielPanel window;
	private String name;
	private AktionMenu menu;
	
	public static String WERWOLF_ICON = "res/werwolf_icon.jpg";
	public static String AMOR_ICON = "res/amor_icon.png";
	public static String HUNTER_ICON = "res/hunter_icon.jpg";
	public static String BUERGER_ICON = "res/human_icon.jpg";
	public static String SEHERIN_ICON = "res/seherin_icon.jpg";
	public static String UNKNOWN_ICON = "res/unknown_icon.jpg";
	public static String HEXE_ICON = "res/witch_icon.jpg";
	
	MouseAdapter ma = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			menu.setVisible(true);
			menu.setLocation(e.getLocationOnScreen());
			System.out.println("Karte ausgewählt");
			
		}
	};
	/**
	 * Create the panel.
	 */
	public Karte(HauptSpielPanel window) {
		this.window = window;
		setBounds(0,0,125, (int)(125/0.712));
		setLayout(null);
		addMouseListener(ma);
		
		menu = new AktionMenu(this);
		window.add(menu);
		menu.setVisible(false);
		menu.setKreatur(window.getGameWindow().getGame().getSpieler().getSpielerDaten().getKreatur());
		
		blood = new JLabel("New label");
		blood.setIcon(new ImageIcon(Karte.class.getResource("/res/blood.png")));
		blood.setBounds(0, -22, 125, 175);
		add(blood);
		
		
		icon = new JLabel("");
		icon.setIcon(new ImageIcon(Karte.class.getResource("/res/unknown_icon.jpg")));
		icon.setBackground(Color.MAGENTA);
		icon.setBounds(20, 20, 85, 85);
		add(icon);
		icon.addMouseListener(ma);
		
		JPanel lblNameBg = new JPanel();
		lblNameBg.setBounds(20, 115, 83, 14);
		add(lblNameBg);
		lblNameBg.setLayout(null);
		lblNameBg.addMouseListener(ma);
		
		lblname = new JLabel("Spieler");
		lblname.setBounds(0, 0, 85, 14);
		lblNameBg.add(lblname);
		lblname.setBackground(Color.WHITE);
		lblname.setFont(new Font("Papyrus", Font.BOLD, 11));
		lblname.setForeground(Color.RED);
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
		lblname.addMouseListener(ma);
		
		JLabel bg = new JLabel("");
		bg.setToolTipText("Name");
		bg.setIcon(new ImageIcon(Karte.class.getResource("/res/Card_edit.jpg")));
		bg.setBounds(0, 0, 125, 175);
		add(bg);
		bg.addMouseListener(ma);
		
		Spieler spieler = getHauptSpielPanel().getGameWindow().getGame().getSpieler();
		if(spieler.getSpielerDaten().getName().equals(name)) {
			enttarnen(spieler.getSpielerDaten().getKreatur());
		}
		
		setAlive(true);
	}

	public Spieler getSpielerFromGameData(String name) {
		return window.getGameWindow().getGame().getSpielDaten().getSpieler(name);
	}
	
	public void setSpieler(Spieler spieler) {
		this.name = spieler.getSpielerDaten().getName();
	}
	
	public void setAlive(boolean b) {
		blood.setVisible(!b);
		menu.setEnabled(b);
	}
	
	public void enttarnen(Kreatur kreatur) {
		String icon_path = null;
		if(kreatur == null)
			kreatur = getSpielerFromGameData(this.name).getSpielerDaten().getKreatur();
		switch(kreatur) {
		case BUERGER: {
			icon_path = BUERGER_ICON;
			break;
		}
		case ARMOR: {
			icon_path = AMOR_ICON;
			break;
		}
		case HEXE: {
			icon_path = HEXE_ICON;
			break;
		}
		case JAEGER: {
			icon_path = HUNTER_ICON;
			break;
		}
		case SEHERIN: {
			icon_path = SEHERIN_ICON;
			break;
		}
		case WERWOLF: {
			icon_path = WERWOLF_ICON;
			break;
		}
		}
		
		setIcon(icon_path);
		getHauptSpielPanel().seherinFreischalten(false);
		getHauptSpielPanel().getGameWindow().getGame().zeitRaffer();
	}
	
	public void setIcon(String icon_path) {
		icon.setIcon(new ImageIcon(icon_path));
	}
	
	public void setSpielerName(String name) {
		this.name = name;
		lblname.setText(name);
	}
	
	public String getSpielerName() {
		return name;
	}

	public HauptSpielPanel getHauptSpielPanel() {
		return window;
	}
	
	public void abstimmenFreischalten(boolean b) {
		menu.abstimmenFreischalten(b);
	}
	
	public void verliebenFreischalten(boolean b) {
		menu.amorFreischalten(b);
	}
	
	public void hexeFreischalten(boolean b) {
		menu.hexeFreischalten(b);
	}
	
	public void seherinFreischalten(boolean b) {
		menu.seherinFreischalten(b);
	}
	
	public void werwolfFreischalten(boolean b) {
		menu.werwolfFreischalten(b);
	}
}
