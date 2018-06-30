package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;

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
	
	private JPanel current_panel;
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
		frame.setResizable(false);
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
	
	public void change(JPanel panel) {
		if(current_panel != null)
			frame.remove(current_panel);
		current_panel = panel;
		frame.getContentPane().add(panel);
	}
	
	public void showInfoBoard() {
		infoPanel.setVisible(true);
	}
	
	public JPanel getCurrentPanel() {
		return current_panel;
	}
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
}
