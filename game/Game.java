package game;
import javax.swing.JOptionPane;

import net.Client;
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
			break;}
		case MORGEN: {
			normalize();
			eventÜberspringen("vorbereitung");
			setPhase("Der Morgen ist angebrochen und alle erwachen");
			break;}
		case GERICHT: {
			normalize();
			setPhase("Das Gericht hat sich zusammengefunden");
			break;}
		case ABSTIMMUNG: {
			normalize();
			setPhase("Die Abstimmung hat begonnen!");
			break;}
		case HINRICHTUNG_NACHMITTAG: {
			normalize();
			setPhase("Die Kirchenglocken leuten den Nachmittag und die feierliche Hinrichtung eines verurteilten Werwolfes ein.");
			minuteInSekunden(0.1);
			break;}
		case NACHT: {
			normalize();
			setPhase("Die Nacht schlägt erneut zu");
			break;}
		case WERWOLF: {
			normalize();
			setPhase("Die Werwölfe sind erwacht und ziehen um die Häuser. Bete!");
			break;}
		case AMOR: {
			normalize();
			setPhase("Der Schrei des Opfers ließ Amor erwachen");
			break;}
		case HEXE: {
			normalize();
			setPhase("Die Hexe fühlt sich gezwungen in die Situation einzugreifen");
			break;}
		case SEHERIN: {
			normalize();
			setPhase("Die Seherin lässt es sich nicht nehmen, den Werwölfen auf die Schliche zu kommen...");
			break;}
		case SCHLAFEN: {
			normalize();
			setPhase("Über das übernatürliche Konzert legt sich ein dichter Nebel. Die Nacht ist wieder ruhig.");
			break;}
		}
	}
	
	public void setPhase(String text) {
		getGameWindow().getHauptSpielPanel().setPhase(text);
	}
	
	public void minuteInSekunden(double s) {
		if(moderator != null) {
			moderator.getZeitSystem().setMinuteInSekunden(s);
		}
	}
	
	public void normalize() {
		if(moderator != null) {
			moderator.getZeitSystem().setMinuteInSekunden(1);
		}
	}
	
	public void eventÜberspringen(String name) {
		if(moderator == null)
			return;
		ZeitEvent e = moderator.getZeitSystem().getEvent(name);
		e.setAktion(()->zeitRaffer());
	}
	
	public void zeitRaffer() {
		moderator.getZeitSystem().setMinuteInSekunden(0.01);
	}
	
	
	
	
}
