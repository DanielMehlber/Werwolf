package ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JButton;

public class DorfBeitreten extends JPanel {
	private JTextField txtIP;
	private JTextField txtPort;

	/**
	 * Create the panel.
	 */
	public DorfBeitreten() {
		setBackground(Color.BLACK);
		setBounds(0,0,800,600);
		setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.RED);
		progressBar.setEnabled(false);
		progressBar.setBounds(0, 549, 800, 22);
		add(progressBar);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(DorfBeitreten.class.getResource("/res/Village_edit.jpg")));
		label.setBounds(335, -112, 465, 724);
		add(label);
		
		txtIP = new JTextField();
		txtIP.setHorizontalAlignment(SwingConstants.CENTER);
		txtIP.setBackground(Color.DARK_GRAY);
		txtIP.setForeground(Color.RED);
		txtIP.setFont(new Font("DialogInput", Font.BOLD, 20));
		txtIP.setText("IP");
		txtIP.setToolTipText("IP Adresse");
		txtIP.setBounds(24, 126, 301, 35);
		add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setHorizontalAlignment(SwingConstants.CENTER);
		txtPort.setToolTipText("IP Adresse");
		txtPort.setText("Port");
		txtPort.setForeground(Color.RED);
		txtPort.setFont(new Font("DialogInput", Font.BOLD, 43));
		txtPort.setColumns(10);
		txtPort.setBackground(Color.DARK_GRAY);
		txtPort.setBounds(71, 184, 208, 55);
		add(txtPort);
		
		JButton btnDorfAufsuchen = new JButton("Dorf aufsuchen");
		btnDorfAufsuchen.setForeground(Color.RED);
		btnDorfAufsuchen.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnDorfAufsuchen.setBackground(Color.BLACK);
		btnDorfAufsuchen.setBounds(53, 475, 243, 42);
		add(btnDorfAufsuchen);
		
		JLabel label_1 = new JLabel("7 / 24");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Felix Titling", Font.BOLD, 29));
		label_1.setForeground(Color.BLUE);
		label_1.setBounds(95, 414, 150, 50);
		add(label_1);
		
	}
}
