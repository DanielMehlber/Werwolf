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
	private JLabel loading;
	private JProgressBar progressBar;
	private JButton btnDorfAufsuchen;
	private JLabel lblPlayerConnected;
	private LauncherWindow window;
	public static enum Status{
		BEREIT, VERBINDEN, WARTEN_AUF_SPIELER, FERTIG
	}

	/**
	 * Create the panel.
	 */
	public DorfBeitreten(LauncherWindow window) {
		this.window = window;
		setBackground(Color.BLACK);
		setBounds(0,0,800,600);
		setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(Color.BLACK);
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
		txtPort.setToolTipText("Dorfnummer");
		txtPort.setText("Port");
		txtPort.setForeground(Color.RED);
		txtPort.setFont(new Font("DialogInput", Font.BOLD, 43));
		txtPort.setColumns(10);
		txtPort.setBackground(Color.DARK_GRAY);
		txtPort.setBounds(71, 184, 208, 55);
		add(txtPort);
		
		btnDorfAufsuchen = new JButton("Dorf aufsuchen");
		btnDorfAufsuchen.setForeground(Color.RED);
		btnDorfAufsuchen.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnDorfAufsuchen.setBackground(Color.BLACK);
		btnDorfAufsuchen.setBounds(58, 274, 243, 42);
		add(btnDorfAufsuchen);
		
		lblPlayerConnected = new JLabel("7 / 24");
		lblPlayerConnected.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerConnected.setFont(new Font("Felix Titling", Font.BOLD, 29));
		lblPlayerConnected.setForeground(Color.BLUE);
		lblPlayerConnected.setBounds(101, 429, 150, 50);
		add(lblPlayerConnected);
		
		loading = new JLabel("");
		loading.setIcon(new ImageIcon(DorfErstellen.class.getResource("/res/loading.gif")));
		loading.setBounds(101, 490, 145, 58);
		add(loading);
		loading.setVisible(false);
		setStatus(Status.WARTEN_AUF_SPIELER);
		//setStatus(Status.VERBINDEN);
		//setStatus(Status.WARTEN_AUF_SPIELER);
		set_player_connected(24, 12);
	}
	
	public void setStatus(Status status) {
		switch(status) {
		case BEREIT: {
			loading.setVisible(false);
			progressBar.setVisible(false);
			lblPlayerConnected.setVisible(false);
			break;}
		case VERBINDEN:{
			txtIP.setEditable(false);
			txtPort.setEditable(false);
			progressBar.setVisible(true);
			progressBar.setIndeterminate(true);
			btnDorfAufsuchen.setText("Verbinden...");
			btnDorfAufsuchen.setEnabled(false);
			break;}
		case WARTEN_AUF_SPIELER: {
			btnDorfAufsuchen.setText("Warten auf Spieler");
			loading.setVisible(true);
			progressBar.setIndeterminate(false);
			lblPlayerConnected.setVisible(true);
			break;}
		case FERTIG: {
			//Spiel laden
			break;}
		}
	}
	
	public void set_player_connected(int ges, int connected) {
		lblPlayerConnected.setVisible(true);
		lblPlayerConnected.setText(""+ges+" / "+connected);
		progressBar.setValue(connected*100/ges);
		lblPlayerConnected.setToolTipText("Es fehlen noch "+(ges-connected)+" Spieler");
	}
	
	
}
