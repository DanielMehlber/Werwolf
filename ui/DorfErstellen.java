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
	
	public static enum Status{
		GEN_SERVERS, WAITING_FOR_PLAYERS, FINISHED
	}
	/**
	 * Create the panel.
	 */
	public DorfErstellen(LauncherWindow window) {
		this.window = window;
		setLayout(null);
		setBounds(0,0,800,600);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.RED);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(0, 549, 800, 22);
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
				
			}
		});
		button.setForeground(Color.RED);
		button.setFont(new Font("Felix Titling", Font.BOLD, 15));
		button.setBackground(Color.BLACK);
		button.setBounds(266, 475, 243, 42);
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
		lblPlayerConnected.setBounds(334, 433, 115, 31);
		add(lblPlayerConnected);
		lblPlayerConnected.setVisible(false);
		
		lblCode = new JLabel("Code");
		lblCode.setVisible(false);
		lblCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblCode.setForeground(Color.RED);
		lblCode.setFont(new Font("DialogInput", Font.BOLD, 33));
		lblCode.setBounds(305, 300, 179, 50);
		add(lblCode);
		
		lblIp = new JLabel("IP");
		lblIp.setVisible(false);
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblIp.setForeground(Color.BLUE);
		lblIp.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblIp.setBounds(242, 344, 289, 31);
		add(lblIp);
		JLabel bg = new JLabel("");
		bg.setFont(new Font("Tahoma", Font.BOLD, 11));
		bg.setBounds(getBounds());
		add(bg);
		
		status(Status.WAITING_FOR_PLAYERS);
		activate_connection_info("24.6435.1234.65", 23425);
		set_player_connected(23, 7);
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
		case GEN_SERVERS: {
			progressBar.setIndeterminate(true);
			progressBar.setStringPainted(true);
			
			button.setEnabled(false);
			button.setText("Generating Server...");
			break;}
		case WAITING_FOR_PLAYERS: {
			progressBar.setIndeterminate(true);
			progressBar.setStringPainted(true);
			progressBar.setIndeterminate(false);
			button.setText("Waiting for Players");
			break;}
		case FINISHED: {
			//window.change();
			break;}
		}
	}
}
