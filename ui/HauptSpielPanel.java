package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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

import game.Kandidat;
import game.SpielDaten;
import game.Spieler;
import karten.Kreatur;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class HauptSpielPanel extends JDesktopPane {
	JButton btnShowInfo;
	/**
	 * Create the panel.
	 */
	private GameWindow window;
	private InfoPanel infoPanel;
	public JLabel uhr;
	
	private JSlider slider;
	private ArrayList<Karte> karten_liste;
	private JLabel lblSchlafen;
	private JLabel naechstes;
	private Phone phone;
	
	public HauptSpielPanel(GameWindow window) {
		window.setHauptSpielPanel(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
			}
		});
		
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,700);
		setBounds(window.frame.getBounds());
		setLayout(null);
		
		lblSchlafen = new JLabel("...Du schl\u00E4fst ...");
		lblSchlafen.setBackground(Color.BLACK);
		lblSchlafen.setFont(new Font("Impact", Font.PLAIN, 63));
		lblSchlafen.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchlafen.setForeground(Color.RED);
		lblSchlafen.setBounds(-14, 536, 1200, 101);
		add(lblSchlafen);
		lblSchlafen.setVisible(false);
		lblSchlafen.setOpaque(false);
		
		btnShowInfo = new JButton("");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoPanel.setVisible(true);
				infoPanel.show();
				btnShowInfo.setVisible(false);
			}
		});
		btnShowInfo.setIcon(new ImageIcon(HauptSpielPanel.class.getResource("/res/lexicon_icon.png")));
		btnShowInfo.setBounds(10, 49, 50, 50);
		add(btnShowInfo);
		
		phone = new Phone(window);
		phone.setNormalBounds(new Rectangle(100, 100, 380, 740));
		phone.setLocation(176, 49);
		add(phone);
		phone.show();
		phone.getDesktopIcon().setLocation((int)(getBounds().getWidth()-phone.getBounds().getWidth()), (int)(getBounds().getHeight()-phone.getBounds().getHeight()));
		
		naechstes = new JLabel("");
		naechstes.setForeground(Color.RED);
		naechstes.setHorizontalAlignment(SwingConstants.CENTER);
		naechstes.setBounds(353, 380, 422, 14);
		add(naechstes);
		
		uhr = new JLabel();
		uhr.setText("99:99");
		uhr.setHorizontalAlignment(SwingConstants.CENTER);
		uhr.setFont(new Font("OCR A Extended", Font.BOLD, 46));
		uhr.setForeground(Color.RED);
		uhr.setBounds(453, 293, 200, 101);
		add(uhr);
		
		//Uhrfont
		Font uhr_font = null;
		try {
		    //create the font to use. Specify the size!
		    uhr_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/alarm clock.ttf")).deriveFont(58f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/alarm clock.ttf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		uhr.setFont(uhr_font);
		
		setNaechstePhaseBeschreibung(0, 0, "");
		
		getDesktopManager().iconifyFrame(phone);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(karten_liste != null)
					kartenPositionieren(slider.getValue(),uhr.getBounds().x + uhr.getBounds().width/2, uhr.getBounds().y + uhr.getBounds().height / 2);
			}
		});
		slider.setMinimum(100);
		slider.setMaximum(500);
		slider.setPaintLabels(true);
		slider.setToolTipText("Radius der Karten");
		slider.setValue(300);
		slider.setBounds(10, 11, 200, 26);
		add(slider);
		
		
		infoPanel = new InfoPanel(window);
		infoPanel.setBounds(0, 11, 500, 771);
		infoPanel.setVisible(true);
		btnShowInfo.setVisible(false);
		infoPanel.show();
		add(infoPanel);
		
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
		infoPanel.setRudel();
	}
	
	public void removeKarte(String name) {
		Karte k = getKarte(name);
		karten_liste.remove(k);
		remove(k);
		kartenPositionieren(slider.getValue(), uhr.getBounds().x + uhr.getBounds().width/2, uhr.getBounds().y + uhr.getBounds().height / 2);
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
		String s = zuZeit(stunden);
		String m = zuZeit(minuten);
		uhr.setText(s+":"+m);
		
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
	
	public void setNaechstePhaseBeschreibung(int stunde, int minute, String beschreibung) {
		String b = "Um ";
		String s = zuZeit(stunde);
		String m = zuZeit(minute);
		b = "Um "+s+":"+m+" "+beschreibung;
		naechstes.setText(b);
	}
	
	public String zuZeit(int i) {
		String zeit = null;
		String b = String.valueOf(i);
		char [] cb = b.toCharArray();
		if(cb.length == 1) {
			char[] c = new char[2];
			c[0] = '0';
			c[1] = cb[0];
			zeit = String.valueOf(c);
			return zeit;
		}else {
			return String.valueOf(i);
		}
		
	}
	
	public Phone getPhone() {
		return phone;
	}

	public Karte getKarte(String name) {
		for(int i = 0; i < karten_liste.size(); i++) {
			Karte karte = karten_liste.get(i);
			if(karte.getSpielerName().equals(name)) {
				return karte;
			}
		}
		
		return null;
	}
	
	public void abstimmungBeiAllenKartenSetzen(boolean b) {
		System.out.println("VOTE: Abstimmung von allen = "+b);
		for(int i = 0; i < karten_liste.size(); i++) {
			karten_liste.get(i).abstimmenFreischalten(b);
		}
	}
	
	public void abstimmungSpielerSetzen(String name, boolean b) {
		System.out.println("VOTE: Abstimmung von "+name+" = "+b);
		getKarte(name).abstimmenFreischalten(b);
	}
	
	
	public void amorFreischalten(boolean b) {
		for(Karte k : karten_liste) {
			k.verliebenFreischalten(b);
		}
	}
	
	public void hexeFreischalten(boolean b) {
		String opfer_name = null;
		if(b) {
			opfer_name = getGameWindow().getGame().getSpielDaten().getWerwolfOpferName();
			if(opfer_name == null) {
				opfer_name = "Niemand";
			}
			out.SpielAusgabe.info(null, "Retten ?", opfer_name+" ist kurz davor von den Werw�lfen zuerfleischt zu werden.\n"
					+ "Wenn du noch einen Rettungstrank hast, hast du auch eine Wahl!");
		}
		
		for(Karte k : karten_liste) {
			k.hexeFreischalten(b);
		}
	}
	
	public void seherinFreischalten(boolean b) {
		for(Karte k : karten_liste) {
			k.seherinFreischalten(b);
		}
	}
	
	public void werwolfFreischalten(boolean b) {
		SpielDaten spielDaten = getGameWindow().getGame().getSpielDaten();
		for(Karte k : karten_liste) {
			if(getGameWindow().getGame().getSpielDaten().isSpielerAlive(k.getSpielerName())) {
				String name = k.getSpielerName();
				Spieler spieler = spielDaten.getSpieler(name);
				if(!spieler.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
					k.werwolfFreischalten(b);
				}
			}
		}
	}
	
	public void spielerToeten(String name) {
		for(Karte karte : karten_liste) {
			if(karte.getSpielerName().equals(name)) {
				karte.setAlive(false);
				karte.enttarnen(getGameWindow().getGame().getSpielDaten().getSpieler(name).getSpielerDaten().getKreatur());
			}
		}
		
	}
	
	public void addTotenmeldung(String name, String txt, String id) {
		TotenmeldungUI tm = new TotenmeldungUI();
		tm.setSpielerName(name);
		tm.setText(txt, id);
		add(tm);
		tm.show();
		tm.setBounds(800, 11, 390, 500);
	}
	
	public void update(Rectangle b) {
		setBounds(0,0,b.width, b.height);
		this.uhr.setLocation((int)(b.width/2 - uhr.getBounds().width/2), (int)(b.height/2 - uhr.getBounds().height/2));
		this.naechstes.setLocation((int) (b.width/2 - naechstes.getBounds().width/2), (int) (uhr.getBounds().y + uhr.getBounds().height));
		kartenPositionieren(slider.getValue(), uhr.getBounds().x + uhr.getBounds().width/2, uhr.getBounds().y + uhr.getBounds().height / 2);
		this.lblSchlafen.setLocation((int)(b.width/2 - lblSchlafen.getBounds().width/2), (int)(b.getHeight() - 300));
		phone.getDesktopIcon().setLocation((int)(uhr.getBounds().x - phone.getDesktopIcon().getBounds().width), (int)(uhr.getBounds().y - phone.getDesktopIcon().getBounds().height));
	}
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
}
