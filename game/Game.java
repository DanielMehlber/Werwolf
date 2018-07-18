package game;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import karten.Kreatur;
import net.Client;
import net.InetDataFormatter;
import ui.DorfBeitretenPanel;
import ui.DorfBeitretenPanel.Status;
import ui.DorfErstellenPanel;
import ui.GameWindow;
import ui.HauptMenuPanel;
import ui.HauptSpielPanel;
import ui.Ladebildschirm;
import ui.LauncherWindow;
import ui.Phone;
import zeit.ZeitEvent;

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
	
	private final int MINUTE_IN_SECONDS = 1;
	
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
		
		ui.verbindungsInfoAnzeigen(moderator.get_self_ip(), moderator.get_self_port());
		warten.start();
	}
	
	/**
	 * Einem Dorf beitreten
	 * @param ui Das DorfBeitereten Panel, in dem die Parameter gespeichert sind
	 * */
	public void dorfBeitreten(DorfBeitretenPanel ui) {
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
		System.out.println("Das Hauptspiel wird gestartet...");
		HauptSpielPanel hauptSpielPanel = new HauptSpielPanel(gameWindow);
		gameWindow.wechseln(hauptSpielPanel);
		gameWindow.setHauptSpielPanel(hauptSpielPanel);
		gameWindow.getFrame().setResizable(true);
		hauptSpielPanel.kartenErstellen();
		if(moderator != null)
			moderator.starten();
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
		this.spieler.setSpielerDaten(daten.getSpieler(spieler.getSpielerDaten().getName()).getSpielerDaten());
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
	
	public void setSpielStatus(SpielStatus s) {
		switch(s) {
		case VORBEREITUNG: {
			normalize();
			out.SpielAusgabe.info(null, "TIPP", "Fahre mit dem Cursor über die Uhrzeit, um zu sehen was gerade passiert");
			setPhase("Die erste und letzte Vorbereitung vor der Nacht, die Opfer einfordern wird");
			setNaechstePhaseBeschreibung(18, 0, "beginnt die Nacht erneut");
			setUISchlafen(false);
			lockPhone(false);
			break;}
		case MORGEN: {
			normalize();
			eventÜberspringen("vorbereitung");
			setPhase("Der Morgen ist angebrochen und alle erwachen");
			naechtlicheGeschehnisseVerarbeiten();
			setNaechstePhaseBeschreibung(8, 0, "beginnt das Gericht");
			setUISchlafen(false);
			lockPhone(false);
			
			break;}
		case GERICHT: {
			normalize();
			setPhase("Das Gericht hat sich zusammengefunden");
			setNaechstePhaseBeschreibung(12, 0, "wird abgestimmt!");
			getSpielDaten().setAbstimmung(new Abstimmung(getSpielDaten()));
			break;}
		case ABSTIMMUNG: {
			normalize();
			setPhase("Die Abstimmung hat begonnen!");
			setNaechstePhaseBeschreibung(13, 0, "findet die Hinrichtung statt");
			lockPhone(true);
			getGameWindow().getHauptSpielPanel().abstimmungBeiAllenKartenSetzen(true);
			break;}
		case HINRICHTUNG_NACHMITTAG: {
			normalize();
			setPhase("Die Kirchenglocken leuten den Nachmittag und die feierliche Hinrichtung eines verurteilten Werwolfes ein.");
			setNaechstePhaseBeschreibung(18, 00, "bricht die Nacht herein. Vllt erfährst du etwas?");
			minuteInSekunden(0.1);
			lockPhone(false);
			//Abstimmung beenden
			getSpielDaten().getAbstimmung().setOffen(false);
			getGameWindow().getHauptSpielPanel().abstimmungBeiAllenKartenSetzen(false);
			//Gewinner hinrichten
			String hinzurichtenden = getSpielDaten().getAbstimmung().getGewinner().getName();
			System.out.println(hinzurichtenden+" hat eine Hinrichtung gewonnen!");
			getSpielDaten().setVerurteilterSpielerName(hinzurichtenden);
			
			hinrichten();
			//TODO: Hinweise festlegen
			
			break;}
		case NACHT: {
			normalize();
			setPhase("Die Nacht schlägt erneut zu");
			setNaechstePhaseBeschreibung(18, 30, "erwachen die Werwölfe");
			lockPhone(true);
			setUISchlafen(true);
			break;}
		case WERWOLF: {
			getSpielDaten().setAbstimmung(new Abstimmung(spiel_daten));
			normalize();
			setPhase("Die Werwölfe sind erwacht und ziehen um die Häuser. Bete!");
			setNaechstePhaseBeschreibung(20, 0, "erwacht Amor");
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				lockPhone(false);
				setUISchlafen(false);
				getGameWindow().getHauptSpielPanel().werwolfFreischalten(true);
				
			}
			
			break;}
		case AMOR: {
			normalize();
			//Abstimmung schließen und Opfer setzen
			getSpielDaten().getAbstimmung().setOffen(false);
			werwolfOpferSetzen();
			spielDatenTeilen();
			
			//Amor
			setPhase("Der Schrei des Opfers ließ Amor erwachen");
			setNaechstePhaseBeschreibung(21, 0, "erwacht die Hexe");
			setUISchlafen(true);
			//Amorfunktionen freischalten
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.ARMOR)) {
				getGameWindow().getHauptSpielPanel().amorFreischalten(true);
				setUISchlafen(false);
			} 
			
			break;}
		case HEXE: {
			normalize();
			setPhase("Die Hexe fühlt sich gezwungen in die Situation einzugreifen");
			setNaechstePhaseBeschreibung(22, 0, "erwacht die Seherin");
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.HEXE)) {
				getGameWindow().getHauptSpielPanel().hexeFreischalten(true);
				setUISchlafen(false);
			}
			
			setUISchlafen(true);
			break;}
		case SEHERIN: {
			normalize();
			setPhase("Die Seherin lässt es sich nicht nehmen, den Werwölfen auf die Schliche zu kommen...");
			setNaechstePhaseBeschreibung(23, 0, "legen sich alle schlafen");
			setUISchlafen(true);
			
			//Seherinfunktion freischalten
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.SEHERIN)) {
				getGameWindow().getHauptSpielPanel().seherinFreischalten(true);
				setUISchlafen(false);
			}
			
			break;}
		case SCHLAFEN: {
			normalize();
			setPhase("Über das übernatürliche Konzert legt sich ein dichter Nebel. Die Nacht ist wieder ruhig.");
			setNaechstePhaseBeschreibung(7, 0, "Morgengrauen");
			zeitRaffer();
			setUISchlafen(true);
			break;}
		}
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
	}
	
	public void lockPhone(boolean b) {
		getGameWindow().getHauptSpielPanel().getPhone().sperren(b);
	}
	
	public void normalize() {
		if(moderator != null) {
			moderator.getZeitSystem().setMinuteInSekunden(MINUTE_IN_SECONDS);
		}
		
		//Amorfunktionen beenden
		if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.ARMOR)) {
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
	
	public void eventÜberspringen(String name) {
		if(moderator == null)
			return;
		ZeitEvent e = moderator.getZeitSystem().getEvent(name);
		e.setAktion(()->zeitRaffer());
	}
	
	public void zeitRaffer() {
		if(moderator == null) {
			InetDataFormatter formatter = spieler.getClient().getFormatter();
			spieler.getClient().schreiben(new byte[] {(byte)5});
		}else {
			moderator.getZeitSystem().setMinuteInSekunden(0.01);
		}
	}
	
	public void setUISchlafen(boolean b) {
		getGameWindow().getHauptSpielPanel().setSchlafen(b);
	}
	
	public void spielDatenTeilen() {
		System.out.println("Spieldaten werden geteilt...");
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
			}
			
			//Tote in der UI umsetzen
			getGameWindow().getHauptSpielPanel().addTotenmeldung(toter.getName(), toter.getText(), toter.getIdentityText());
			getGameWindow().getHauptSpielPanel().spielerToeten(toter.getName());
			
			//Spielerlisten aktualisieren
			getSpielDaten().toteBegraben();
			spielDatenTeilen();
		}	
	}
	
	public void liebesPaarPruefen() {
		ArrayList<Spieler> liebespaar = getSpielDaten().getLiebespaar();
		if(liebespaar.size() == 2) {
			
		}
	}
	
	public void werwolfOpferSetzen() {
		Kandidat k = getSpielDaten().getAbstimmung().getGewinner();
		if(k == null) {
			return;
		}
		getSpielDaten().setWerwolfOpfer(k.getName());
		
	}
	
	/**
	 * Erstellt eine Todesmeldung der Nacht und schickt sie zu den Spielern
	 * */
	public void naechtlicheGeschehnisseVerarbeiten() {
		Todesmeldung meldung = new Todesmeldung();
		ArrayList<Spieler> tote = getSpielDaten().getToteSpieler();
		String opfer_name = getSpielDaten().getWerwolfOpferName();
		
		if(opfer_name != null) {
			//Durch Hexe gerettet ? 
			if(getSpielDaten().getSpieler(opfer_name).getSpielerDaten().isLebendig()) {
				out.SpielAusgabe.info(null, "Rettung durch Hexe", opfer_name+" wurde von der Hexe gerettet!");
			}else {
			//Oder doch den Werwölfen zum fraß gefallen
				meldung.addToten(new Toter(opfer_name, Todesursache.WERWOLF, getSpielDaten().getSpieler(opfer_name).getSpielerDaten().getKreatur()));
				getSpielDaten().setWerwolfOpfer(opfer_name);
			}
		}
		
		//Tod durch Hexe
		for(Spieler s : tote) {
			if(!s.getSpielerDaten().getName().equals(opfer_name)) {
				meldung.addToten(new Toter(s.getSpielerDaten().getName(), Todesursache.HEXE, s.getSpielerDaten().getKreatur()));
			}
			
		}
		
		moeglichesLiebesOpferHinzufuegen(meldung);
		
		if(moderator != null) 
			moderator.todesnachrichtSenden(meldung);
		
	
	}
	
	/**
	 * Erstellt eine Todesmeldung am Nachmittag und schickt diese zu den Spielern
	 * */
	public void hinrichten() {
		//Verurteilten zur Meldunghinzufügen
		Todesmeldung meldung = new Todesmeldung();
		String name = getSpielDaten().getVerurteilterSpielerName();
		if(name == null) {
			return;
		}
		Spieler verurteilter = getSpielDaten().getSpieler(name);
		meldung.addToten(new Toter(name, Todesursache.HINRICHTUNG, verurteilter.getSpielerDaten().getKreatur()));
		
		moeglichesJaegerZielHinzufuegen(meldung, verurteilter);
		moeglichesLiebesOpferHinzufuegen(meldung);
		
		//Spieler als Tot setzen
		for(Toter toter : meldung.getTotenListe()) {
			getSpielDaten().getSpieler(toter.getName()).getSpielerDaten().setLebendig(false);
		}
		
		if(moderator != null) 
			moderator.todesnachrichtSenden(meldung);
		
	}
	
	/**
	 * Prüft ob es sich beim Spieler um einen Jäger handelt, und fügt das Opfer hinzu
	 * */
	public void moeglichesJaegerZielHinzufuegen(Todesmeldung meldung, Spieler s) {
		if(s.getSpielerDaten().getKreatur().equals(Kreatur.JAEGER)) {
			String zielName = getSpielDaten().getJaegerZiel();
			Spieler ziel = getSpielDaten().getSpieler(zielName);
			meldung.addToten(new Toter(zielName, Todesursache.HINRICHTUNG, ziel.getSpielerDaten().getKreatur()));
		}
	}
	
	/**
	 * Prüft ob die Toten teil eines Liebespaares sind, wenn ja, dann wird die Liebe auch noch hinzugefügt
	 * */
	public void moeglichesLiebesOpferHinzufuegen(Todesmeldung meldung) {
		for(Toter t : meldung.getTotenListe()) {
			Spieler s = getSpielDaten().getSpieler(t.getName());
			Spieler liebe;
			if((liebe=getSpielDaten().getLiebe(s.getSpielerDaten().getName()))!=null) {
				meldung.addToten(new Toter(liebe.getSpielerDaten().getName(), Todesursache.LIEBE, liebe.getSpielerDaten().getKreatur()));
			}
		}
	}
	
	public void spielStandUeberpruefen() {
		if(sindNurWesen()) {
			out.SpielAusgabe.info(null, "SPIEL BEENDET", "Die Werwoelfe sind alle Tot! Glückwunsch, das Dorf ist gerettet!");
		}
		
		if(sindNurWerwoelfe()) {
			out.SpielAusgabe.info(null, "SPIEL BEENDET", "Die Werwölfe haben gewonnen!");
		}
		
		if(getSpielDaten().getSpielerListe().size() == 2) {
			//Check fpr Liebespaar
		}
	}
	
	private boolean sindNurWerwoelfe() {
		boolean b = true;
		for(Spieler s : getSpielDaten().getSpielerListe()) {
			if(!s.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				b = false;
			}
		}
		return b;
	}
	
	private boolean sindNurWesen() {
		boolean b = true;
		for(Spieler s : getSpielDaten().getSpielerListe()) {
			if(s.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				b = false;
			}
		}
		return b;
	}
	
}