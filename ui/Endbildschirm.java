package ui;

import javax.swing.JPanel;

import game.Todesursache;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class Endbildschirm extends JLayeredPane {

	/**
	 * Create the panel.
	 */
	JLabel bg;
	private GameWindow window;
	public Endbildschirm(GameWindow window) {
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,800);
		setLayout(null);
		
		bg = new JLabel("");
		bg.setBackground(Color.BLACK);
		
		bg.setHorizontalAlignment(SwingConstants.CENTER);
		bg.setBounds(-31, -45, 1200, 900);
		add(bg);
		bg.setOpaque(false);
		
		JLabel lblBitteLasseDas = new JLabel("Bitte lasse das Fenster ge\u00F6ffnet, es wird sich am Ende automatisch schlie\u00DFen!");
		lblBitteLasseDas.setFont(new Font("Stencil", Font.PLAIN, 17));
		lblBitteLasseDas.setHorizontalAlignment(SwingConstants.CENTER);
		lblBitteLasseDas.setBounds(128, 95, 770, 59);
		add(lblBitteLasseDas);
		
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
			
			break;}
		case LIEBE: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/deadly_love.png")));
			break;}
		case WERWOLF: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/DeadByWerwolf.jpg")));
			break;}
		}
	}
	
	
	

}
