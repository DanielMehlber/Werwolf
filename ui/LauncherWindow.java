package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Window.Type;

/**
 * Hällt die Frames, die das Spiel steuern
 * @author Daniel Mehlber
 * */
public class LauncherWindow {

	private JFrame frmWerwolf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherWindow window = new LauncherWindow();
					window.frmWerwolf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWerwolf = new JFrame();
		frmWerwolf.setResizable(false);
		frmWerwolf.setTitle("Werwolf");
		frmWerwolf.setBounds(100, 100, 800, 600);
		frmWerwolf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		change(new DorfErstellen(this));
	}
	
	public void change(JPanel jframe) {
		frmWerwolf.getContentPane().add(jframe);
		frmWerwolf.getContentPane().setBounds(jframe.getBounds());
		
	}

}
