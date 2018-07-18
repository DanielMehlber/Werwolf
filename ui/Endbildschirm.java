package ui;

import javax.swing.JPanel;

import game.Todesursache;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Endbildschirm extends JLayeredPane {

	/**
	 * Create the panel.
	 */
	JLabel bg;
	private GameWindow window;
	public Endbildschirm(GameWindow window) {
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1200,900);
		setLayout(null);
		
		bg = new JLabel("");
		bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/deadly_love.png")));
		bg.setHorizontalAlignment(SwingConstants.CENTER);
		bg.setBounds(0, 0, 1200, 900);
		add(bg);
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
		case HINRICHTUNG: {break;}
		case LIEBE: {
			
			break;}
		case WERWOLF: {
			bg.setIcon(new ImageIcon(Endbildschirm.class.getResource("/res/DeadByWerwolf.jpg")));
			break;}
		}
	}
	

}
