package ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

import jdk.nashorn.internal.ir.SetSplitState;

import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DorfBeitretenPanel extends JPanel {
	private JTextField txtIP;
	private JTextField txtPort;
	private JLabel loading;
	private JProgressBar progressBar;
	private JButton btnDorfAufsuchen;
	private JLabel lblPlayerConnected;
	private LauncherWindow window;
	private JTextField txtName;
	private JLabel lblMaxZeichen;
	private JLabel lblUngueltig;
	private JLabel lblMussZahlSein;
	public static enum Status{
		BEREIT, VERBINDEN, WARTEN_AUF_SPIELER, FERTIG
	}

	/**
	 * Create the panel.
	 */
	public DorfBeitretenPanel(LauncherWindow window) {
		this.window = window;
		setBackground(Color.BLACK);
		setBounds(0,0,800,600);
		setLayout(null);
		
		KeyListener kl = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				setValidity(checkNameValidity(txtName.getText()),
						checkIPValidity(txtIP.getText()), checkPortValidity(txtPort.getText()));
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		};
		
		lblMaxZeichen = new JLabel("Zeichenanzahl !");
		lblMaxZeichen.setBackground(Color.RED);
		lblMaxZeichen.setForeground(Color.RED);
		lblMaxZeichen.setBounds(335, 92, 124, 14);
		add(lblMaxZeichen);
		
		lblUngueltig = new JLabel("Ungueltig");
		lblUngueltig.setForeground(Color.RED);
		lblUngueltig.setBackground(Color.RED);
		lblUngueltig.setBounds(335, 140, 124, 14);
		add(lblUngueltig);
		
		lblMussZahlSein = new JLabel("Muss zahl sein!");
		lblMussZahlSein.setForeground(Color.RED);
		lblMussZahlSein.setBackground(Color.RED);
		lblMussZahlSein.setBounds(335, 204, 124, 14);
		add(lblMussZahlSein);
		
		
		progressBar = new JProgressBar();
		progressBar.setBackground(Color.BLACK);
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.RED);
		progressBar.setEnabled(false);
		progressBar.setBounds(0, 549, 800, 22);
		add(progressBar);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(DorfBeitretenPanel.class.getResource("/res/Village_edit.jpg")));
		label.setBounds(335, -112, 465, 724);
		add(label);
		
		txtIP = new JTextField();
		txtIP.addKeyListener(kl);
		txtIP.setHorizontalAlignment(SwingConstants.CENTER);
		txtIP.setBackground(Color.DARK_GRAY);
		txtIP.setForeground(Color.BLUE);
		txtIP.setFont(new Font("DialogInput", Font.BOLD, 20));
		txtIP.setText("IP");
		txtIP.setToolTipText("IP Adresse");
		txtIP.setBounds(24, 126, 301, 35);
		add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.addKeyListener(kl);
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
		btnDorfAufsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dorf_beitreten();
			}
		});
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
		loading.setIcon(new ImageIcon(DorfErstellenPanel.class.getResource("/res/loading.gif")));
		loading.setBounds(101, 490, 145, 58);
		add(loading);
		
		txtName = new JTextField();
		txtName.addKeyListener(kl);
		txtName.setToolTipText("IP Adresse");
		txtName.setText("Name");
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setForeground(Color.RED);
		txtName.setFont(new Font("Bradley Hand ITC", Font.BOLD, 23));
		txtName.setColumns(10);
		txtName.setBackground(Color.DARK_GRAY);
		txtName.setBounds(24, 80, 301, 35);
		add(txtName);
		loading.setVisible(false);
		
		setStatus(Status.BEREIT);
		setValidity(checkNameValidity(txtName.getText()),
				checkIPValidity(txtIP.getText()), checkPortValidity(txtPort.getText()));
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
	
	public void dorf_beitreten() {
		window.getGame().dorf_beitreten(this);
	}
	
	public void aktualisiereVerbundeneSpieler(int ges, int connected) {
		lblPlayerConnected.setVisible(true);
		lblPlayerConnected.setText(""+connected+" / "+ges);
		progressBar.setValue(connected*100/ges);
		lblPlayerConnected.setToolTipText("Es fehlen noch "+(ges-connected)+" Spieler");
	}
	
	public void aktualisiereVerbundeneSpieler() {
		int ges = window.getGame().getSpielDaten().get_max_spieler();
		int con = window.getGame().getSpielDaten().getSpielerAnzahl();
		aktualisiereVerbundeneSpieler(ges, con);
	}
	
	private void setValidity(boolean name , boolean ip, boolean port) {
		setNameValid(name);
		setIPValid(ip);
		setPortValid(port);
		btnDorfAufsuchen.setEnabled(name && ip && port);
	}
	
	public boolean checkNameValidity(String name) {
		if(name.length() > 8 || name.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private void setNameValid(boolean b) {
		lblMaxZeichen.setVisible(!b);
	}
	
	public boolean checkIPValidity(String ip) {
		if(ip.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean checkPortValidity(String port) {
		try {
			int p = Integer.parseInt(port);
		}catch(NumberFormatException e){
			return false;
		}
		if (Integer.parseInt(port)==0) {
			return false;
		}
		return true;
	}
	
	private void setIPValid(boolean b) {
		lblUngueltig.setVisible(!b);
	}
	
	private void setPortValid(boolean b) {
		lblMussZahlSein.setVisible(!b);
	}

	public String getName() {
		return txtName.getText();
	}
	
	public int getPort() {
		return Integer.parseInt(txtPort.getText());
	}
	
	public String getIP() {
		return txtIP.getText();
	}
	
}
