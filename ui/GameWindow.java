package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class GameWindow {

	private JFrame frmWerwolfVersion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.frmWerwolfVersion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		frmWerwolfVersion = new JFrame();
		frmWerwolfVersion.getContentPane().setBackground(Color.BLACK);
		frmWerwolfVersion.getContentPane().setLayout(new BorderLayout(0, 0));
		frmWerwolfVersion.setTitle("Werwolf Version");
		frmWerwolfVersion.setBounds(100, 100, 1100, 800);
		frmWerwolfVersion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
