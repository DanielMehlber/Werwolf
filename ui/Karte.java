package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;

import game.Spieler;

import java.awt.Font;

public class Karte extends JPanel {

	private JLabel icon;
	
	private HauptSpielPanel window;
	private String name;
	
	public static String WERWOLF_ICON = "res/werwolf_icon.jpg";
	public static String AMOR_ICON = "";
	public static String HUNTER_ICON = "res/hunter_icon.jpg";
	public static String BUERGER_ICON = "res/human_icon.jpg";
	public static String SEHERIN_ICON = "res/seherin_icon.jpg";
	public static String UNKNOWN_ICON = "res/unknown_icon.jpg";
	
	/**
	 * Create the panel.
	 */
	public Karte(HauptSpielPanel window) {
		this.window = window;
		setBounds(0,0,125, (int)(125/0.712));
		setLayout(null);
		
		icon = new JLabel("");
		icon.setIcon(new ImageIcon(Karte.class.getResource("/res/unknown_icon.jpg")));
		icon.setBackground(Color.MAGENTA);
		icon.setBounds(20, 20, 85, 85);
		add(icon);
		
		JPanel lblNameBg = new JPanel();
		lblNameBg.setBounds(20, 115, 83, 14);
		add(lblNameBg);
		lblNameBg.setLayout(null);
		
		JLabel lblname = new JLabel("Spieler");
		lblname.setBounds(0, 0, 85, 14);
		lblNameBg.add(lblname);
		lblname.setBackground(Color.WHITE);
		lblname.setFont(new Font("Papyrus", Font.BOLD, 11));
		lblname.setForeground(Color.RED);
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
	
		
		JLabel bg = new JLabel("");
		bg.setToolTipText("Name");
		bg.setIcon(new ImageIcon(Karte.class.getResource("/res/Card_edit.jpg")));
		bg.setBounds(0, 0, 125, 175);
		add(bg);
	}

	public Spieler getSpielerFromGameData(String name) {
		return window.getGameWindow().getGame().getSpielDaten().getSpieler(name);
	}
	
	public void setSpieler(Spieler spieler) {
		this.name = spieler.getSpielerDaten().getName();
	}
	
	public void aktualisieren() {
		getSpielerFromGameData(name);
		//überprüfen ob spieler enttarnt wurde
	}
	
	public void setIcon(String icon_path) {
		icon.setIcon(new ImageIcon(icon_path));
	}
	
	
}
