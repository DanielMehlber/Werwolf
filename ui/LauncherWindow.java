package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Window.Type;

/**
 * Hällt die Frames, die das Spiel steuern
 * @author Daniel Mehlber
 * */
public class LauncherWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherWindow window = new LauncherWindow(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private JPanel current_panel;
	private Game game;
	public LauncherWindow(Game game) {
		this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Werwolf");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		change(new DorfBeitreten(this));
	}
	
	public void change(JPanel jframe) {
		if(current_panel != null)
			frame.remove(current_panel);
		current_panel = jframe;
		frame.getContentPane().add(jframe);
		frame.getContentPane().setBounds(jframe.getBounds());
		
	}

}
