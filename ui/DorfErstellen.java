package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 * @author Daniel Mehlber
 * */
public class DorfErstellen extends JPanel {
	
	ChangeListener cl;
	JSpinner spinner, spinner_1;
	JLabel lblGesamt;
	JProgressBar progressBar;
	JButton button;
	LauncherWindow window;
	private JLabel lblPlayerConnected;
	private JLabel lblCode;
	private JLabel lblIp;
	private JLabel loading;
	
	public static enum Status{
		BEREIT, GEN_SERVER, WARTEN_AUF_SPIELER, FERTIG
	}
	/**
	 * Create the panel.
	 */
	public DorfErstellen(LauncherWindow window) {
		setBackground(Color.BLACK);
		this.window = window;
		setLayout(null);
		setBounds(0,0,800,600);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(Color.BLACK);
		progressBar.setForeground(Color.RED);
		progressBar.setBounds(0, 549, 800, 22);
		progressBar.setEnabled(false);
		add(progressBar);
		
		cl = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int wer = (int)spinner_1.getValue();
				int bew = (int)spinner.getValue();
				int ges = wer+bew;
				lblGesamt.setText("Gesamt: "+ges);
				
			}
		};
		
		button = new JButton("Dorf erstellen");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				status(Status.GEN_SERVER);
				
				
			}
		});
		button.setForeground(Color.RED);
		button.setFont(new Font("Felix Titling", Font.BOLD, 15));
		button.setBackground(Color.BLACK);
		button.setBounds(266, 430, 243, 42);
		add(button);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(187, 287, 433, 2);
		add(separator);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
		spinner.setBounds(266, 141, 75, 20);
		add(spinner);
		spinner.addChangeListener(cl);
		
		JLabel label = new JLabel("Werw\u00F6lfe");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("Felix Titling", Font.BOLD, 16));
		label.setBounds(242, 114, 115, 31);
		add(label);
		
		JLabel lblBewohner = new JLabel("Bewohner");
		lblBewohner.setHorizontalAlignment(SwingConstants.CENTER);
		lblBewohner.setForeground(Color.RED);
		lblBewohner.setFont(new Font("Felix Titling", Font.BOLD, 16));
		lblBewohner.setBounds(416, 114, 115, 31);
		add(lblBewohner);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(5), new Integer(5), null, new Integer(1)));
		spinner_1.setBounds(434, 141, 75, 20);
		add(spinner_1);
		spinner_1.addChangeListener(cl);
		
		lblGesamt = new JLabel("Gesamt: ");
		lblGesamt.setHorizontalAlignment(SwingConstants.CENTER);
		lblGesamt.setForeground(Color.RED);
		lblGesamt.setFont(new Font("Felix Titling", Font.BOLD, 16));
		lblGesamt.setBounds(334, 230, 115, 31);
		add(lblGesamt);
		
		lblPlayerConnected = new JLabel("Gesamt: ");
		lblPlayerConnected.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerConnected.setForeground(Color.RED);
		lblPlayerConnected.setFont(new Font("Felix Titling", Font.BOLD, 16));
		lblPlayerConnected.setBounds(334, 399, 115, 31);
		add(lblPlayerConnected);
		lblPlayerConnected.setVisible(false);
		
		lblCode = new JLabel("Code");
		lblCode.setToolTipText("Die Dorfnummer");
		lblCode.setVisible(false);
		lblCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblCode.setForeground(Color.RED);
		lblCode.setFont(new Font("DialogInput", Font.BOLD, 33));
		lblCode.setBounds(305, 300, 179, 50);
		add(lblCode);
		
		lblIp = new JLabel("IP");
		lblIp.setToolTipText("Die IP Addresse");
		lblIp.setVisible(false);
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblIp.setForeground(Color.BLUE);
		lblIp.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblIp.setBounds(242, 344, 289, 31);
		add(lblIp);
		
		JLabel ww_image = new JLabel("");
		ww_image.setIcon(new ImageIcon(DorfErstellen.class.getResource("/res/WerewolfImage.png")));
		ww_image.setBounds(557, 0, 243, 600);
		add(ww_image);
		
		loading = new JLabel("");
		loading.setIcon(new ImageIcon(DorfErstellen.class.getResource("/res/loading.gif")));
		loading.setBounds(316, 490, 145, 58);
		add(loading);
		loading.setVisible(false);
		status(Status.WARTEN_AUF_SPIELER);
		set_player_connected(24, 5);
		
	}
	
	public void set_player_connected(int ges, int connected) {
		lblPlayerConnected.setVisible(true);
		lblPlayerConnected.setText(""+ges+" / "+connected);
		progressBar.setValue(connected*100/ges);
	}
	
	public void activate_connection_info(String ip, int code) {
		lblIp.setText(ip);
		lblCode.setText(""+code);
		lblIp.setVisible(true);
		lblCode.setVisible(true);
	}
	
	public void status(Status s) {
		switch(s) {
		case BEREIT:{
			progressBar.setVisible(false);
			loading.setVisible(false);
			break;
		}
		case GEN_SERVER: {
			progressBar.setVisible(true);
			progressBar.setIndeterminate(true);
			progressBar.setStringPainted(true);
			loading.setVisible(true);
			button.setEnabled(false);
			button.setText("Generating Server...");
			
			spinner.setEnabled(false);
			spinner_1.setEnabled(false);
			break;}
		case WARTEN_AUF_SPIELER: {
			loading.setVisible(true);
			progressBar.setIndeterminate(true);
			progressBar.setStringPainted(true);
			progressBar.setIndeterminate(false);
			button.setText("Waiting for Players");
			break;}
		case FERTIG: {
			//window.change();
			break;}
		}
	}
}
