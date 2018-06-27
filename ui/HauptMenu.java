package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

/**
 * @author Daniel Mehlber
 * */
public class HauptMenu extends JPanel {

	/**
	 * Create the panel.
	 */
	public HauptMenu() {
		setLayout(null);
		setBounds(0, 0, 800, 600);
		JButton btnDorfErstellen = new JButton("Dorf erstellen");
		btnDorfErstellen.setForeground(Color.RED);
		btnDorfErstellen.setBackground(Color.BLACK);
		btnDorfErstellen.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnDorfErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDorfErstellen.setBounds(407, 96, 179, 42);
		add(btnDorfErstellen);
		
		JButton btnDorfBeitreten = new JButton("Dorf beitreten");
		btnDorfBeitreten.setForeground(Color.RED);
		btnDorfBeitreten.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnDorfBeitreten.setBackground(Color.BLACK);
		btnDorfBeitreten.setBounds(407, 170, 179, 42);
		add(btnDorfBeitreten);
		
		JButton btnVerlassen = new JButton("Verlassen");
		btnVerlassen.setForeground(Color.RED);
		btnVerlassen.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnVerlassen.setBackground(Color.BLACK);
		btnVerlassen.setBounds(407, 246, 179, 42);
		add(btnVerlassen);
		
		JLabel bg = new JLabel("");
		bg.setIcon(null);
		bg.setBackground(Color.GREEN);
		bg.setBounds(getBounds());
		add(bg);
		
		JLabel lblWerwlfe = new JLabel("Werw\u00F6lfe");
		lblWerwlfe.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 34));
		lblWerwlfe.setBounds(41, 34, 284, 56);
		add(lblWerwlfe);
		
	}
}
