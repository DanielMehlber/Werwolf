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
		
		//change(new MainGame(this));
	}
	
	public void change(JLayeredPane panel) {
		if(current_panel != null)
			frame.remove(current_panel);
		current_panel = panel;
		frame.getContentPane().add(panel, new BorderLayout().CENTER);
		
	}
	
	
	
	public JLayeredPane getCurrentPanel() {
		return current_panel;
	}
	
	
	public Game getGame() {
		return game;
	}
	
	
}
