package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.ImageIcon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWindow {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JLayeredPane current_panel;
	private InfoPanel infoPanel;
	
	/**
	 * Create the application.
	 */
	public GameWindow() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Werwolf");
		frame.setBounds(0, 0, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infoPanel = new InfoPanel(this);
		infoPanel.setBounds(60, 11, 500, 771);
		frame.getContentPane().add(infoPanel);
		infoPanel.setVisible(true);
		change(new MainGame(this));
	}
	
	public void change(JLayeredPane panel) {
		if(current_panel != null)
			frame.remove(current_panel);
		current_panel = panel;
		frame.getContentPane().add(panel, new BorderLayout().CENTER);
		
	}
	
	public void showInfoBoard() {
		infoPanel.setVisible(true);
	}
	
	public JLayeredPane getCurrentPanel() {
		return current_panel;
	}
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
	
	
	
	
}
