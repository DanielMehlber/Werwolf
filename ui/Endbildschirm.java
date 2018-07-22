package ui;

import javax.swing.JPanel;

import game.Ende;
import game.Todesursache;
import zeit.ZeitEvent;
import zeit.ZeitSystem;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class Endbildschirm extends JLayeredPane {

	/**
	 * Create the panel.
	 */
	JLabel bg;
	private GameWindow window;
	public Endbildschirm(GameWindow window, Todesursache ursache) {
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,800);
		setLayout(null);
		window.getFrame().setResizable(false);
		window.getFrame().setBounds(0, 0, 1100, 800);
		
		JLabel lblBitteLasseDas = new JLabel("Bitte lasse das Fenster ge\u00F6ffnet, es wird sich am Ende automatisch schlie\u00DFen!");
		lblBitteLasseDas.setForeground(Color.RED);
		lblBitteLasseDas.setFont(new Font("Stencil", Font.PLAIN, 17));
		lblBitteLasseDas.setHorizontalAlignment(SwingConstants.CENTER);
		lblBitteLasseDas.setBounds(128, 95, 770, 59);
		add(lblBitteLasseDas);
		
		bg = new JLabel("");
		bg.setBackground(Color.BLACK);
		
		bg.setHorizontalAlignment(SwingConstants.CENTER);
		bg.setBounds(-31, -45, 1200, 900);
		add(bg);
		bg.setOpaque(false);
		
		setTodeursache(ursache);
	}
	
	public GameWindow getGameWindow() {
		return window;
	}
	
	public void setTodeursache(Todesursache ursache) {
		switch(ursache) {
		case HEXE: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/DeadByWitch.jpg")));
			break;
			}
		case HINRICHTUNG: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/gallow.jpg")));
			break;}
		case LIEBE: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/deadly_love.png")));
			break;}
		case WERWOLF: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/DeadByWerwolf.jpg")));
			break;}
		}
	}
	
	public void setEnde(Ende ende) {
		switch(ende) {
		case GUT: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("")));
			break;}
		case BOESE: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/werewolf_win.jpg")));
			break;}
		case LIEBE: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/love wins.png")));
			break;}
		}
	}
	
	
	

}
