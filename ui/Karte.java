package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Karte extends JPanel {

	/**
	 * Create the panel.
	 */
	public Karte() {
		setBounds(0,0,125, (int)(125/0.712));
		setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Karte.class.getResource("/res/unknown_icon.jpg")));
		label.setBackground(Color.MAGENTA);
		label.setBounds(20, 20, 85, 85);
		add(label);
		
		JPanel lblNameBg = new JPanel();
		lblNameBg.setBounds(20, 115, 83, 14);
		add(lblNameBg);
		lblNameBg.setLayout(null);
		
		JLabel lblname = new JLabel("Spieler");
		lblname.setBounds(0, 0, 85, 14);
		lblNameBg.add(lblname);
		lblname.setBackground(Color.WHITE);
		lblname.setFont(new Font("Papyrus", Font.BOLD, 11));
		lblname.setForeground(Color.RED);
		lblname.setHorizontalAlignment(SwingConstants.CENTER);
	
		
		JLabel bg = new JLabel("");
		bg.setToolTipText("Name");
		bg.setIcon(new ImageIcon(Karte.class.getResource("/res/Card_edit.jpg")));
		bg.setBounds(0, 0, 125, 175);
		add(bg);
	}

}
