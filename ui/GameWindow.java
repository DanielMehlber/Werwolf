package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import game.Game;

public class GameWindow {

	public JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JLayeredPane current_panel;
	private Game game;
	private HauptSpielPanel hauptSpielPanel;
	
	/**
	 * Create the application.
	 */
	public GameWindow(Game game) {
		this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Werwolf");
		frame.setBounds(0, 0, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void wechseln(JLayeredPane panel) {
		frame.getContentPane().removeAll();
		current_panel = panel;
		frame.getContentPane().add(panel);
		frame.revalidate();
		frame.repaint();
	}
	
	
	
	public JLayeredPane getCurrentPanel() {
		return current_panel;
	}
	
	
	public Game getGame() {
		return game;
	}
	
	public void redraw() {
		frame.repaint();
	}
	
	public HauptSpielPanel getHauptSpielPanel() {
		return this.hauptSpielPanel;
	}
	
	public void setHauptSpielPanel(HauptSpielPanel panel) {
		this.hauptSpielPanel = panel;
	}
}
