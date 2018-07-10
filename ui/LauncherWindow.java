package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Window.Type;

/**
 * H�llt die Frames, die das Spiel steuern
 * @author Daniel Mehlber
 * */
public class LauncherWindow implements Runnable{

	private JFrame frame;

	/**
	 * Create the application.
	 */
	private JPanel current_panel;
	private Game game;
	private boolean isReady;
	public LauncherWindow(Game game) {
		this.game = game;
		isReady = false;
		
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
		frame.setVisible(true);
		isReady = true;
	}
	
	public void wechseln(JPanel jframe) {
		if(frame == null) {
			out.SpielAusgabe.error(null, "No Thread created", "LauncherWindow needs a new Thread and needs to be started!");
		}
		if(current_panel != null)
			frame.remove(current_panel);
		current_panel = jframe;
		frame.getContentPane().add(jframe);
		frame.getContentPane().setBounds(jframe.getBounds());
		frame.validate();
	}
	
	public Game getGame() {
		return game;
	}

	@Override
	public void run() {
		initialize();
		
	}
	
	public boolean isBereit() {
		return isReady;
	}
	
	public void close() {
		frame.dispose();
	}

}
