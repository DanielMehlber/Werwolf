package game;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import hinweis.Hinweis;
import hinweis.HinweisGen;
import karten.Kreatur;
import net.Anschluss;
import net.Client;
import net.InetDataFormatter;
import ui.DorfBeitretenPanel;
import ui.DorfBeitretenPanel.Status;
import ui.DorfErstellenPanel;
import ui.Endbildschirm;
import ui.GameWindow;
import ui.HauptMenuPanel;
import ui.HauptSpielPanel;
import ui.Ladebildschirm;
import ui.LauncherWindow;
import ui.Phone;
import zeit.ZeitEvent;

//Amor kann nicht erwachen
//Werwölfe können nur für sich selbst abstimmen

/**
 * Das Herzstück des Spiels 
 * */
public class Game{
	
	private LauncherWindow launcher;
	private GameWindow gameWindow;
	private DorfErstellenPanel dorf_erstellen_panel;
	private DorfBeitretenPanel dorf_beitreten_panel;
	
	private SpielDaten spiel_daten;
	private Moderator moderator;
	private Spieler spieler;
	private Phone phone;
	
	private HinweisGen hinweisGenerator =  new HinweisGen(this);
	
	private double MINUTE_IN_SECONDS = 1;
	
	private Thread warten;
	
	public Game() {
		launcher = new LauncherWindow(this);
		Thread launcher_thread = new Thread(launcher);
		launcher_thread.start();
		while(!launcher.isBereit()) {System.out.print("");}
		launcher.wechseln(new HauptMenuPanel(launcher));
		
		spiel_daten = new SpielDaten();
		
		warten = new Thread(new Runnable() {
			@Override
			public void run() {
				aufSpielerWarten();
		}});
	}
	
	public static void main(String[]args) {
		Game game = new Game();
	}
	
	public DorfErstellenPanel getDorfErstellenPanel() {
		return this.dorf_erstellen_panel;
	}
	
	public DorfBeitretenPanel getDorfBeitretenPanel() {
		return this.dorf_beitreten_panel;
	}
	
	
	public void dorfErstellen(DorfErstellenPanel ui) {
		out.SpielAusgabe.seperator();
		dorf_erstellen_panel = ui;
		moderator = new Moderator(this);
		//SpielDaten setzen
		spiel_daten.set_max_spieler(ui.getGesamteSpielerAnzahl());
		spiel_daten.setAnzahlWerwoelfe(ui.getAnzahlWerwoelfe());
		spiel_daten.setAnzahlWesen(ui.getAnzahlWesen());
		
		//Server erstellen mit Thread
		ui.setStatus(DorfErstellenPanel.Status.GEN_SERVER);
		moderator.server_starten();
		
		//Warten bis Server fertig ist
		while(!moderator.getServerBereit()) {System.out.print("");}
		
		ui.setStatus(DorfErstellenPanel.Status.WARTEN_AUF_SPIELER);
		out.SpielAusgabe.seperator();
		//Spieler erstllen
		String name = null; 
		while(!spiel_daten.spielerNameFrei(name)) {
			name = JOptionPane.showInputDialog("Wie möchstest du heißen?");
		}
		spieler = new Spieler(new SpielerDaten(name), this);
		Client client = new Client(this, spieler, "localhost", moderator.get_self_port());
		spieler.setClient(client);
		spieler.verbinden("localhost", moderator.get_self_port());
		
		//Warten bis der Spieler verbunden ist
		while(!spieler.isVerbunden()) {System.out.print("");}
		spieler.anmelden();
		
		//Warten auf weitere Spieler
		
		ui.verbindungsInfoAnzeigen(moderator.get_self_ip(), moderator.get_self_port(), moderator.getExternalIP());
		
		warten.start();
		
	}
	
	/**
	 * Einem Dorf beitreten
	 * @param ui Das DorfBeitereten Panel, in dem die Parameter gespeichert sind
	 * */
	public void dorfBeitreten(DorfBeitretenPanel ui) {
		out.SpielAusgabe.seperator();
		dorf_beitreten_panel = ui;
		
		//Verbinden und neuen Spieler erstellen
		ui.setStatus(Status.VERBINDEN);
		String name = ui.getName();
		String ip = ui.getIP();
		int port = ui.getPort();
		spieler = new Spieler(new SpielerDaten(name), this);
		
		//Einen Client zum Verbinden erstellen
		Client client = new Client(this, spieler, ip, port);
		spieler.setClient(client);
		spieler.verbinden(ip, port);
		
		//Warten bis der Client bereit zum anmelden ist
		while(!spieler.getClient().isBereit()) {System.out.print("");}
		spieler.anmelden();
		
		//Warten auf weitere Spieler
		ui.setStatus(Status.WARTEN_AUF_SPIELER);
		
		warten.start();
	}
	
	/**
	 * Prüft immer, ob noch gewartet werden muss. Wenn nicht, dann fortfahren
	 * */
	public void aufSpielerWarten() {
		while(getSpielDaten().get_warten_auf_spieler()) {System.out.print("");}
		spielEinleiten();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Schließt den Launcher und geht zum Spiel über
	 * */
	public void spielEinleiten() {
		launcher.close();
		if(moderator != null)
			moderator.uebernehmen();
		gameWindow = new GameWindow(this);
		Ladebildschirm lb = new Ladebildschirm(gameWindow);
		lb.setStatus(Ladebildschirm.Status.WARTEN);
		gameWindow.wechseln(lb);
		gameWindow.setLadeBildschirm(lb);
		gameWindow.frame.setVisible(true);
	}
	
	/**
	 * Nachdem die Spieler Bereit sind, startet das Spiel
	 * @see Client
	 * */
	public void spielStarten() {
		out.SpielAusgabe.seperator();
		System.out.println("Das Hauptspiel wird gestartet...");
		Kreatur spieler_kreatur = getSpielDaten().getSpieler(spieler.getSpielerDaten().getName()).getSpielerDaten().getKreatur();
		if(spieler_kreatur == null) {
			spielStarten();
			try {
				Thread.currentThread().sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getSpieler().getSpielerDaten().setKreatur(spieler_kreatur);
		HauptSpielPanel hauptSpielPanel = new HauptSpielPanel(gameWindow);
		hauptSpielPanel.kartenErstellen();
		gameWindow.wechseln(hauptSpielPanel);
		gameWindow.setHauptSpielPanel(hauptSpielPanel);
		gameWindow.getFrame().setResizable(true);
		if(moderator != null) {
			getGameWindow().updateSpieler();
			moderator.starten();
		}
	}
	
	/**
	 * Wird verwendet um festzustellen, ob alle bereit sind
	 * @return Sind alle angemeldeten Spieler bereit?
	 * */
	public boolean sindAlleBereit() {
		boolean alle_bereit = true;
		int bereit = 0;
		for(int i = 0; i < getSpielDaten().getSpielerAnzahl(); i++) {
			Spieler sp = getSpielDaten().getSpielerListe().get(i);
			if(!sp.getSpielerDaten().isBereit()) {
				alle_bereit = false;
			}else {
				bereit += 1;
			}
		}
		System.out.println("Überprüfe ob alle Spieler bereit sind... "+alle_bereit+" ["+bereit+"/"+getSpielDaten().getSpielerAnzahl()+"]");
		return alle_bereit;
	}
	
	public SpielDaten getSpielDaten() {
		return spiel_daten;
	}
	
	public void setSpielDaten(SpielDaten daten) {
		this.spiel_daten = daten;
		Spieler selbst = daten.getSpieler(spieler.getSpielerDaten().getName());
		if(selbst == null)
			return;
		this.spieler.setSpielerDaten(selbst.getSpielerDaten());
	}
	
	public LauncherWindow getLauncherWindow() {
		return launcher;
	}
	
	public GameWindow getGameWindow() {
		return gameWindow;
	}
	
	public Spieler getSpieler() {
		return spieler;
	}
	
	public Phone getPhone() {
		return phone;
	}
	
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	
	public Moderator getModerator() {
		return moderator;
	}
	
	public void setSpielStatus(SpielStatusPaket paket) {
		out.SpielAusgabe.seperator();
		setPhase(paket.getBeschreibung());
		setNaechstePhaseBeschreibung(paket.getNextStunde(), paket.getNextMinute(), paket.getNextBeschreibung());
		switch(paket.getStatus()) {
		case VORBEREITUNG: {
			normalize();
			out.SpielAusgabe.info(null, "TIPP", "Fahre mit dem Cursor über die Uhrzeit, um zu sehen was gerade passiert");
			setUISchlafen(false);
			lockPhone(false);
			break;}
		case MORGEN: {
			normalize();
			if(moderator!=null) {
				moderator.eventÜberspringen("vorbereitung");
				moderator.naechtlicheGeschehnisseVerarbeiten();
			}
			setUISchlafen(false);
			lockPhone(false);
			
			
			break;}
		case GERICHT: {
			normalize();

			if(moderator!=null)
				moderator.abstimmungErstellen();
			
			break;}
		case ABSTIMMUNG: {
			normalize();
			lockPhone(true);
			getGameWindow().getHauptSpielPanel().abstimmungBeiAllenKartenSetzen(true);
			break;}
		case HINRICHTUNG_NACHMITTAG: {
			normalize();
			
			if(moderator!=null)
				moderator.zeitRaffer();
			
			lockPhone(false);
			
			//Abstimmung beenden
			if(moderator!=null)
				moderator.abstimmungSchliessen();
			
			getGameWindow().getHauptSpielPanel().abstimmungBeiAllenKartenSetzen(false);
			
			//Hinweis ausgeben
			zufallsHinweisErzeugen();
			
			if(moderator!=null)
				moderator.gerichtsAbstimmungVerarbeiten();
			
			break;}
		case NACHT: {
			normalize();
			lockPhone(true);
			setUISchlafen(true);
			break;}
		case WERWOLF: {
			normalize();
			
			if(moderator != null)
				moderator.abstimmungErstellen();
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				lockPhone(false);
				setUISchlafen(false);
				getGameWindow().getHauptSpielPanel().werwolfFreischalten(true);
			}
			
			break;}
		case AMOR: {
			normalize();
			
			if(moderator!=null) {
				moderator.abstimmungSchliessen();
				moderator.werwolfAbstimmungVerarbeiten();
			}
			
			setUISchlafen(true);
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.AMOR)) {
				getGameWindow().getHauptSpielPanel().amorFreischalten(true);
				setUISchlafen(false);
			} 
			
			break;}
		case HEXE: {
			normalize();
			setUISchlafen(true);
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.HEXE)) {
				getGameWindow().getHauptSpielPanel().hexeFreischalten(true);
				setUISchlafen(false);
			}
			
			break;}
		case SEHERIN: {
			normalize();
			setUISchlafen(true);
			
			//Seherinfunktion freischalten
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.SEHERIN)) {
				getGameWindow().getHauptSpielPanel().seherinFreischalten(true);
				setUISchlafen(false);
			}
			
			break;}
		
		case SCHLAFEN: {
			normalize();
			
			if(moderator!=null)
				moderator.zeitRaffer();
			
			setUISchlafen(true);
			break;}
		}
	}
	
	
	
	public void zufallsHinweisErzeugen() {
		Spieler s = getSpielDaten().getRandomSpieler();
		String hinweis = hinweisGenerator.hinweis(s.getSpielerDaten().getName(), null);
		out.SpielAusgabe.info(null, "PSSSST, ein kleiner Tipp", hinweis);
	}
	
	public void setPhase(String text) {
		getGameWindow().getHauptSpielPanel().setPhase(text);
	}
	
	public void setNaechstePhaseBeschreibung(int stunde, int minute, String beschreibung) {
		getGameWindow().getHauptSpielPanel().setNaechstePhaseBeschreibung(stunde, minute, beschreibung);
	}
	
	public void minuteInSekunden(double s) {
		if(moderator != null) {
			moderator.getZeitSystem().setMinuteInSekunden(s);
		}
		this.MINUTE_IN_SECONDS = s;
	}
	
	public void lockPhone(boolean b) {
		getGameWindow().getHauptSpielPanel().getPhone().sperren(b);
	}
	
	public void normalize() {
		if(moderator != null) {
			moderator.getZeitSystem().setMinuteInSekunden(MINUTE_IN_SECONDS);
		}
		
		//Amorfunktionen beenden
		if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.AMOR)) {
			getGameWindow().getHauptSpielPanel().amorFreischalten(false);
		} 
		
		//Werwolfunktion beenden
		if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF))
			getGameWindow().getHauptSpielPanel().werwolfFreischalten(false);
		
		
		//Hexefunktionen beenden
		if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.HEXE)) {
			getGameWindow().getHauptSpielPanel().hexeFreischalten(false);
		}
		
		//Seherinfunktion beenden
		if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.SEHERIN)) {
			getGameWindow().getHauptSpielPanel().seherinFreischalten(false);
		}
	}
	
	
	
	
	public void setUISchlafen(boolean b) {
		getGameWindow().getHauptSpielPanel().setSchlafen(b);
	}
	
	public void spielDatenTeilen() {
		System.out.println("Spieldaten werden geteilt ...");
		getSpieler().getClient().schreiben(getSpieler().getClient().getFormatter().formatieren(4, getSpieler().getClient().getFormatter().ObjectToByteArray(spiel_daten)));
	}
	
	
	/**
	 * Lässt die Spieler in der Todesmeldung sterben
	 * */
	public void spielerSterbenLassen(Todesmeldung meldung) {
		for(Toter toter : meldung.getTotenListe()) {
			//Überprufen ob der Spieler selbst der Tote ist
			if(toter.getName().equals(getSpieler().getSpielerDaten().getName())) {
				System.err.println("DU BIST TOT");
				getGameWindow().wechseln(new Endbildschirm(getGameWindow(), toter.getUrsache()));
			}
			System.out.println(toter.toString());
		
			//Tote in der UI umsetzen
			getGameWindow().getHauptSpielPanel().addTotenmeldung(toter.getName(), toter.getText(), toter.getIdentityText());
			getGameWindow().getHauptSpielPanel().spielerToeten(toter.getName());
			
			//Spielerlisten aktualisieren
			getSpielDaten().toteBegraben();
			spielDatenTeilen();
		}
		
		//spielStandUeberpruefen(); TODO: Entkomment debug
	}
	
	public void zeitRafferAnfragen() {
		if(moderator == null) {
			InetDataFormatter formatter = getSpieler().getClient().getFormatter();
			getSpieler().getClient().schreiben(new byte[] {5});
		}else {
			moderator.zeitRaffer();
		}
	}
	
	public void eventUeberspringen(String name) {
		if(moderator==null) {
			InetDataFormatter formatter = getSpieler().getClient().getFormatter();
			getSpieler().getClient().schreiben(formatter.formatieren(6, formatter.ObjectToByteArray(name)));
		}else {
			moderator.eventÜberspringen(name);
		}
	}
	
	public void spielerRauswerfen(String name) {
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				getGameWindow().updateSpieler();
				getGameWindow().getHauptSpielPanel().removeKarte(name);
				out.SpielAusgabe.info(null, "Info", name+" wurde aus dem Server geworfen");
				System.out.println("Entfernung abgeschlossen");
			}
		});
		
		th.start();
	}
	
	
	

	
}