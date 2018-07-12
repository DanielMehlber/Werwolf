package game;
import java.io.Serializable;

import javax.swing.JOptionPane;

import net.Client;
import ui.*;
import ui.DorfBeitretenPanel.Status;

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
		gameWindow = new GameWindow(this);
		Ladebildschirm lb = new Ladebildschirm(gameWindow);
		lb.setStatus(Ladebildschirm.Status.WARTEN);
		gameWindow.change(lb);
		gameWindow.frame.setVisible(true);
	}
	
	/**
	 * Nachdem die Spieler Bereit sind, startet das Spiel
	 * @see Client
	 * */
	public void spielStarten() {
		System.out.println("Das Hauptspiel wird gestartet...");
		HauptSpielPanel hauptSpielPanel = new HauptSpielPanel(gameWindow);
		if(moderator != null)
			moderator.uebernehmen();
		
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
	
	
}
