package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import game.Game;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

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
	private Ladebildschirm laden;
	
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
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if(hauptSpielPanel != null)
					hauptSpielPanel.update(frame.getBounds());

			}
		});
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Werwolf");
		frame.setBounds(0, 0, 1100, 700);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = null;
		try {
			image = ImageIO.read(new File("res/claw_cursor.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX(), 
		           frame.getY()), "img");
		frame.setCursor(c);
		
	}
	
	public void wechseln(JLayeredPane panel) {
		frame.getContentPane().removeAll();
		current_panel = panel;
		frame.getContentPane().add(panel);
		frame.setLocation(0, 0);
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

	public Ladebildschirm getLadeBildschrim() {
		return laden;
	}

	public void setLadeBildschirm(Ladebildschirm laden) {
		this.laden = laden;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	
}
