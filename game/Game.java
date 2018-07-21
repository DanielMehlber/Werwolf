package game;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import hinweis.Hinweis;
import hinweis.HinweisGen;
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

//Amor kann nicht erwachen
//Werw�lfe k�nnen nur f�r sich selbst abstimmen

/**
 * Das Herzst�ck des Spiels 
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
	
	private final double MINUTE_IN_SECONDS = 0.4;
	
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
			name = JOptionPane.showInputDialog("Wie m�chstest du hei�en?");
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
	 * Pr�ft immer, ob noch gewartet werden muss. Wenn nicht, dann fortfahren
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
	 * Schlie�t den Launcher und geht zum Spiel �ber
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
		getSpieler().getSpielerDaten().setKreatur(getSpielDaten().getSpieler(getSpieler().getSpielerDaten().getName()).getSpielerDaten().getKreatur());
		HauptSpielPanel hauptSpielPanel = new HauptSpielPanel(gameWindow);
		hauptSpielPanel.kartenErstellen();
		gameWindow.wechseln(hauptSpielPanel);
		gameWindow.setHauptSpielPanel(hauptSpielPanel);
		gameWindow.getFrame().setResizable(true);
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
		System.out.println("�berpr�fe ob alle Spieler bereit sind... "+alle_bereit+" ["+bereit+"/"+getSpielDaten().getSpielerAnzahl()+"]");
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
	
	public void setSpielStatus(SpielStatusPaket paket) {
		setPhase(paket.getBeschreibung());
		setNaechstePhaseBeschreibung(paket.getNextStunde(), paket.getNextMinute(), paket.getNextBeschreibung());
		switch(paket.getStatus()) {
		case VORBEREITUNG: {
			normalize();
			out.SpielAusgabe.info(null, "TIPP", "Fahre mit dem Cursor �ber die Uhrzeit, um zu sehen was gerade passiert");
			setUISchlafen(false);
			lockPhone(false);
			break;}
		case MORGEN: {
			normalize();
			event�berspringen("vorbereitung");
			naechtlicheGeschehnisseVerarbeiten();
			setUISchlafen(false);
			lockPhone(false);
			
			
			break;}
		case GERICHT: {
			normalize();
			getSpielDaten().setAbstimmung(new Abstimmung(getSpielDaten()));
			break;}
		case ABSTIMMUNG: {
			normalize();
			lockPhone(true);
			getGameWindow().getHauptSpielPanel().abstimmungBeiAllenKartenSetzen(true);
			break;}
		case HINRICHTUNG_NACHMITTAG: {
			normalize();
			minuteInSekunden(0.1);
			lockPhone(false);
			//Abstimmung beenden
			getSpielDaten().getAbstimmung().setOffen(false);
			getGameWindow().getHauptSpielPanel().abstimmungBeiAllenKartenSetzen(false);
			//Gewinner hinrichten
			zufallsHinweisErzeugen();
			gerichtsAbstimmungAuswerten();
			
			break;}
		case NACHT: {
			normalize();
			lockPhone(true);
			setUISchlafen(true);
			break;}
		case WERWOLF: {
			normalize();
			getSpielDaten().setAbstimmung(new Abstimmung(spiel_daten));
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				lockPhone(false);
				setUISchlafen(false);
				getGameWindow().getHauptSpielPanel().werwolfFreischalten(true);
			}
			
			break;}
		case AMOR: {
			normalize();
			werwolfAbstimmungVerarbeiten();
			setUISchlafen(true);
			
			if(spieler.getSpielerDaten().getKreatur().equals(Kreatur.ARMOR)) {
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
			zeitRaffer();
			setUISchlafen(true);
			break;}
		}
	}
	
	public void gerichtsAbstimmungAuswerten() {
		Abstimmung a = getSpielDaten().getAbstimmung();
		if(a == null) {
			System.err.println("FEHLER: Es gab keine Abstimmung");
			return;
		}
		Kandidat gewinner = a.getGewinner();
		if(gewinner == null) {
			System.err.println("FEHLER: Das Gericht kam zu keinem Ergebnis!");
			out.SpielAusgabe.info(null, "Gericht", "Das Gericht kam zu keinem Ergebnis. Es wird Niemand hingerichtet!");
		}
		String hinzurichtender = gewinner.getName();
		System.out.println(hinzurichtender+" hat eine Hinrichtung gewonnen!");
		getSpielDaten().setVerurteilterSpielerName(hinzurichtender);
		hinrichten();
	}
	
	public void zufallsHinweisErzeugen() {
		Spieler s = getSpielDaten().getRandomSpieler();
		hinweisGenerator.hinweis(s.getSpielerDaten().getName(), null);
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
	
	public void event�berspringen(String name) {
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
		System.out.println("Spieldaten werden geteilt ...");
		getSpieler().getClient().schreiben(getSpieler().getClient().getFormatter().formatieren(4, getSpieler().getClient().getFormatter().ObjectToByteArray(spiel_daten)));
	}
	
	
	/**
	 * L�sst die Spieler in der Todesmeldung sterben
	 * */
	public void spielerSterbenLassen(Todesmeldung meldung) {
		for(Toter toter : meldung.getTotenListe()) {
			//�berprufen ob der Spieler selbst der Tote ist
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
		
		//spielStandUeberpruefen(); TODO: Entkomment debug
	}
	
	public void liebesPaarPruefen() {
		ArrayList<Spieler> liebespaar = getSpielDaten().getLiebespaar();
		if(liebespaar.size() == 2) {
			event�berspringen("amor");
		}else {
			liebespaar.clear();
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
	 * Erstellt eine Todesmeldung der Nacht, schickt sie zu den Spielern
	 * */
	public void naechtlicheGeschehnisseVerarbeiten() {
		liebesPaarPruefen();
		Todesmeldung meldung = new Todesmeldung();
		ArrayList<Spieler> tote = getSpielDaten().getToteSpieler();
		String opfer_name = getSpielDaten().getWerwolfOpferName();
		
		if(opfer_name != null) {
			//Durch Hexe gerettet ? 
			if(getSpielDaten().getSpieler(opfer_name).getSpielerDaten().isLebendig()) {
				out.SpielAusgabe.info(null, "Rettung durch Hexe", opfer_name+" wurde von der Hexe gerettet!");
			}else {
			//Oder doch den Werw�lfen zum fra� gefallen
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
		
		spielDatenTeilen();
		//spielStandUeberpruefen(); TODO: Um Spiel beenden zu k�nnen entkommentarisieren		
	
	}
	
	/**
	 * Erstellt eine Todesmeldung am Nachmittag und schickt diese zu den Spielern
	 * @see Game#gerichtsAbstimmungAuswerten()
	 * */
	public void hinrichten() {
		//Verurteilten zur Meldunghinzuf�gen
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
		
		getSpielDaten().setVerurteilterSpielerName(null);
		spielDatenTeilen();
	}
	
	/**
	 * Pr�ft ob es sich beim Spieler um einen J�ger handelt, und f�gt das Opfer hinzu
	 * */
	public void moeglichesJaegerZielHinzufuegen(Todesmeldung meldung, Spieler s) {
		if(s.getSpielerDaten().getKreatur().equals(Kreatur.JAEGER)) {
			String zielName = getSpielDaten().getJaegerZiel();
			Spieler ziel = getSpielDaten().getSpieler(zielName);
			meldung.addToten(new Toter(zielName, Todesursache.HINRICHTUNG, ziel.getSpielerDaten().getKreatur()));
		}
	}
	
	/**
	 * Pr�ft ob die Toten teil eines Liebespaares sind, wenn ja, dann wird die Liebe auch noch hinzugef�gt
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
		if(getSpielDaten().getSpielerListe().size() == 2) {
			if(spiel_daten.isliebesPaar(getSpielDaten().getSpielerListe().get(0).getSpielerDaten().getName(),getSpielDaten().getSpielerListe().get(1).getSpielerDaten().getName())) {
				out.SpielAusgabe.info(null, "SPIEL BEENDET", "Das Liebespaar hat gewonnen!");
				beenden();
			}
		}
		
		if(sindNurWesen()) {
			out.SpielAusgabe.info(null, "SPIEL BEENDET", "Die Werwoelfe sind alle Tot! Gl�ckwunsch, das Dorf ist gerettet!");
			beenden();
		}
		
		if(sindNurWerwoelfe()) {
			out.SpielAusgabe.info(null, "SPIEL BEENDET", "Die Werw�lfe haben gewonnen!");
			beenden();
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
	
	public void beenden() {
		if(moderator != null)
			moderator.getZeitSystem().beenden();
	}
	
	public void werwolfAbstimmungVerarbeiten() {
		Abstimmung werwolf = getSpielDaten().getAbstimmung();
		if(werwolf != null) {
			werwolf.setOffen(false);
			werwolfOpferSetzen();
		}else {
			System.err.println("FEHLER: Die Werwolfabstimmung ist null");
		}
		spielDatenTeilen();
	}
	
}