package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.sun.corba.se.impl.orbutil.ObjectUtility;

import game.SpielDaten;
import game.Spieler;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HauptSpielPanel extends JDesktopPane {
	JButton btnShowInfo;
	/**
	 * Create the panel.
	 */
	private GameWindow window;
	private InfoPanel infoPanel;
	public JLabel uhr;
	
	private ArrayList<Karte> karten_liste;
	private JLabel lblSchlafen;
	
	public HauptSpielPanel(GameWindow window) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
			}
		});
		
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1200,900);
		setBounds(window.frame.getBounds());
		setLayout(null);
		
		infoPanel = new InfoPanel(window);
		infoPanel.setBounds(60, 11, 500, 771);
		infoPanel.setVisible(true);
		infoPanel.show();
		
		lblSchlafen = new JLabel("Du schl\u00E4fst ...");
		lblSchlafen.setFont(new Font("Impact", Font.PLAIN, 63));
		lblSchlafen.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchlafen.setForeground(Color.RED);
		lblSchlafen.setBounds(0, 536, 1200, 101);
		add(lblSchlafen);
		lblSchlafen.setVisible(false);
		add(infoPanel);
		
		btnShowInfo = new JButton("");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoPanel.setVisible(true);
				infoPanel.show();
			}
		});
		btnShowInfo.setIcon(new ImageIcon(HauptSpielPanel.class.getResource("/res/lexicon_icon.png")));
		btnShowInfo.setBounds(10, 49, 50, 50);
		add(btnShowInfo);
		
		Phone phone = new Phone(window);
		phone.setNormalBounds(new Rectangle(100, 100, 380, 740));
		phone.setLocation(176, 49);
		add(phone);
		phone.show();
		
		uhr = new JLabel();
		uhr.setHorizontalAlignment(SwingConstants.CENTER);
		uhr.setFont(new Font("Segoe UI Light", Font.BOLD, 46));
		uhr.setForeground(Color.RED);
		uhr.setBounds(486, 373, 200, 101);
		add(uhr);
		
		getDesktopManager().iconifyFrame(phone);
		
	}
	
	public JButton getShowInfoButton() {
		return btnShowInfo;
	}
	
	/**
	 * Erstellt die Karten mit Hilfe der Spielerdaten und positioniert diese
	 * */
	public void kartenErstellen() {
		karten_liste = new ArrayList<Karte>();
		SpielDaten daten = window.getGame().getSpielDaten();
		for(int i = 0; i < daten.getSpielerAnzahl(); i++) {
			Spieler spieler = daten.getSpielerListe().get(i);
			Karte karte = new Karte(this);
			karte.setSpielerName(spieler.getSpielerDaten().getName());
			karten_liste.add(karte);
			add(karte);
			
		}
		
		kartenPositionieren(300, uhr.getBounds().x + uhr.getBounds().width/2, uhr.getBounds().y + uhr.getBounds().height / 2);
	}
	
	public GameWindow getGameWindow() {
		return window;
	}
	
	/**
	 * (Re-)Positioniert die Karten im Kreis
	 * @param radius Der Radius der Kreisform
	 * @param x Die X Ausgangsposition
	 * @param y Die Y Ausgangsposition
	 * @see #kartenErstellen()
	 * */
	public void kartenPositionieren(int radius, int x, int y) {
		int menge = karten_liste.size();
		double grad = (2.0 * Math.PI) / menge;
		
		for (int i = 0; i < menge; i++) {
			int posX = (int) ((Math.cos(grad * i) * radius) + x);
			int posY =(int) ((Math.sin(grad * i) * radius) + y);
			Karte karte = karten_liste.get(i);
			karte.setBounds(posX - karte.getBounds().width / 2, posY - karte.getBounds().height / 2, karte.getBounds().width, karte.getBounds().height);
		}
		
		
	}
	
	
	
	public void setZeit(int stunden, int minuten) {
		String zeit = "";
		String st = String.valueOf(stunden);
		if(st.toCharArray().length == 1)
			st = "0"+st;
		String min = String.valueOf(minuten);
		if(st.toCharArray().length == 1)
			min = "0"+min;
		zeit = st+":"+min;
		uhr.setText(zeit);
		
	}
	
	public void setSchlafen(boolean s) {
		if(s) {
			setEnabled(false);
			lblSchlafen.setVisible(true);
		}else {
			setEnabled(true);
			lblSchlafen.setVisible(false);
		}
	}
	
	public void setPhase(String text) {
		uhr.setToolTipText(text);
	}

}
