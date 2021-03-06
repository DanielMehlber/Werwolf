package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * @author Daniel Mehlber
 * */
public class HauptMenuPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	LauncherWindow window;
	public HauptMenuPanel(LauncherWindow window) {
		this.window = window;
		setLayout(null);
		setBounds(0, 0, 800, 600);
		JButton btnDorfErstellen = new JButton("Dorf erstellen");
		btnDorfErstellen.setForeground(Color.RED);
		btnDorfErstellen.setBackground(Color.BLACK);
		btnDorfErstellen.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnDorfErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.wechseln(new DorfErstellenPanel(window));
			}
		});
		btnDorfErstellen.setBounds(453, 88, 179, 42);
		add(btnDorfErstellen);
		
		JButton btnDorfBeitreten = new JButton("Dorf beitreten");
		btnDorfBeitreten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.wechseln(new DorfBeitretenPanel(window));
			}
		});
		btnDorfBeitreten.setForeground(Color.RED);
		btnDorfBeitreten.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnDorfBeitreten.setBackground(Color.BLACK);
		btnDorfBeitreten.setBounds(453, 162, 179, 42);
		add(btnDorfBeitreten);
		
		JButton btnVerlassen = new JButton("Verlassen");
		btnVerlassen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnVerlassen.setForeground(Color.RED);
		btnVerlassen.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnVerlassen.setBackground(Color.BLACK);
		btnVerlassen.setBounds(453, 238, 179, 42);
		add(btnVerlassen);
		
		JLabel bg = new JLabel("");
		bg.setIcon(new ImageIcon(HauptMenuPanel.class.getResource("/res/WerwolfMainMenu2_edit.jpg")));
		bg.setBackground(Color.GREEN);
		bg.setBounds(getBounds());
		add(bg);
		
		JLabel lblWerwlfe = new JLabel("Werw\u00F6lfe");
		lblWerwlfe.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 34));
		lblWerwlfe.setBounds(41, 34, 284, 56);
		add(lblWerwlfe);
		
	}
}
