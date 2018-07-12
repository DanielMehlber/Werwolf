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
	private JLabel uhr;
	
	private ArrayList<Karte> karten_liste;
	
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
		
		Phone phone = new Phone();
		phone.setNormalBounds(new Rectangle(100, 100, 380, 740));
		phone.setLocation(176, 49);
		add(phone);
		phone.show();
		
		uhr = new JLabel("12:20");
		uhr.setHorizontalAlignment(SwingConstants.CENTER);
		uhr.setFont(new Font("Segoe UI Light", Font.BOLD, 46));
		uhr.setForeground(Color.RED);
		uhr.setBounds(486, 373, 200, 101);
		add(uhr);
		
		getDesktopManager().iconifyFrame(phone);
		
		revalidate();
		window.redraw();
		repaint();
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
		
		kartenPositionieren(200, uhr.getBounds().x + uhr.getBounds().width/2, uhr.getBounds().y + uhr.getBounds().height / 2);
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
		
		revalidate();
	}
	

}
