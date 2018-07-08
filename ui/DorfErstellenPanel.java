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

import sun.launcher.resources.launcher;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 * @author Daniel Mehlber
 * */
public class DorfErstellenPanel extends JPanel {
	
	public ChangeListener cl;
	public JSpinner num_werwoelfe, num_bewohner;
	public JLabel lblGesamt;
	public JProgressBar progressBar;
	public JButton button;
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
	public DorfErstellenPanel(LauncherWindow window) {
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
				updateGesamtLabel();
				
			}
		};
		
		button = new JButton("Dorf erstellen");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dorfErstellen();
				
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
		
		num_werwoelfe = new JSpinner();
		num_werwoelfe.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
		num_werwoelfe.setBounds(266, 141, 75, 20);
		add(num_werwoelfe);
		num_werwoelfe.addChangeListener(cl);
		
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
		
		num_bewohner = new JSpinner();
		num_bewohner.setModel(new SpinnerNumberModel(new Integer(5), new Integer(5), null, new Integer(1)));
		num_bewohner.setBounds(434, 141, 75, 20);
		add(num_bewohner);
		num_bewohner.addChangeListener(cl);
		
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
		lblCode.setBounds(304, 330, 179, 50);
		add(lblCode);
		
		lblIp = new JLabel("IP");
		lblIp.setToolTipText("Die IP Addresse");
		lblIp.setVisible(false);
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblIp.setForeground(Color.BLUE);
		lblIp.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblIp.setBounds(242, 300, 289, 31);
		add(lblIp);
		
		JLabel ww_image = new JLabel("");
		ww_image.setIcon(new ImageIcon(DorfErstellenPanel.class.getResource("/res/WerewolfImage.png")));
		ww_image.setBounds(557, 0, 243, 600);
		add(ww_image);
		
		loading = new JLabel("");
		loading.setIcon(new ImageIcon(DorfErstellenPanel.class.getResource("/res/loading.gif")));
		loading.setBounds(316, 490, 145, 58);
		add(loading);
		loading.setVisible(false);
		
		updateGesamtLabel();
	}
	
	public void aktualisiereVerbundeneSpieler(int ges, int connected) {
		lblPlayerConnected.setVisible(true);
		lblPlayerConnected.setText(""+connected+" / "+ges);
		progressBar.setValue(connected*100/ges);
	}
	
	public void aktualisiereVerbundeneSpieler() {
		int ges = window.getGame().getSpielDaten().get_max_spieler();
		int con = window.getGame().getSpielDaten().getSpielerAnzahl();
		aktualisiereVerbundeneSpieler(ges, con);
	}
	
	public void verbindungsInfoAnzeigen(String ip, int code) {
		lblIp.setText(ip);
		lblCode.setText(""+code);
		lblIp.setVisible(true);
		lblCode.setVisible(true);
	}
	
	public void setStatus(Status s) {
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
			button.setText("Generiere Server...");
			
			num_werwoelfe.setEnabled(false);
			num_bewohner.setEnabled(false);
			break;}
		case WARTEN_AUF_SPIELER: {
			loading.setVisible(true);
			progressBar.setIndeterminate(true);
			progressBar.setStringPainted(true);
			progressBar.setIndeterminate(false);
			button.setText("Warten auf Spieler");
			break;}
		case FERTIG: {
			//window.change();
			break;}
		}
	}
	
	private void dorfErstellen() {
		window.getGame().dorf_erstellen(this);
	}
	
	private void updateGesamtLabel() {
		int wer = (int)num_bewohner.getValue();
		int bew = (int)num_werwoelfe.getValue();
		int ges = wer+bew;
		lblGesamt.setText("Gesamt: "+ges);
	}
	
	public int getGesamteSpielerAnzahl() {
		int wer = (int)num_bewohner.getValue();
		int bew = (int)num_werwoelfe.getValue();
		return wer+bew;
	}
}
