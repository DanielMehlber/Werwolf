package game;
import java.io.Serializable;

import javax.swing.JOptionPane;

import net.Client;
import ui.*;
import ui.DorfBeitretenPanel.Status;

public class Game{
	
	private LauncherWindow launcher;
	private DorfErstellenPanel dorf_erstellen_panel;
	private DorfBeitretenPanel dorf_beitreten_panel;
	
	private SpielDaten spiel_daten;
	private Moderator moderator;
	private Spieler spieler;
	
	public Game() {
		launcher = new LauncherWindow(this);
		Thread launcher_thread = new Thread(launcher);
		launcher_thread.start();
		while(!launcher.isBereit()) {System.out.print("");}
		launcher.wechseln(new HauptMenuPanel(launcher));
		
		spiel_daten = new SpielDaten();
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
	
	public void dorf_erstellen(DorfErstellenPanel ui) {
		dorf_erstellen_panel = ui;
		moderator = new Moderator(this);
		//SpielDaten setzen
		spiel_daten.set_max_spieler(ui.getGesamteSpielerAnzahl());
		
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
		
		while(!spieler.isVerbunden()) {System.out.print("");}
		
		spieler.anmelden();
		
		//Warten auf Spieler
		
		ui.verbindungsInfoAnzeigen(moderator.get_self_ip(), moderator.get_self_port());
		
		
		
	}
	
	public void dorf_beitreten(DorfBeitretenPanel ui) {
		dorf_beitreten_panel = ui;
		
		ui.setStatus(Status.VERBINDEN);
		String name = ui.getName();
		String ip = ui.getIP();
		int port = ui.getPort();
		Spieler spieler = new Spieler(new SpielerDaten(name), this);
		
		Client client = new Client(this, spieler, ip, port);
		spieler.setClient(client);
		
		spieler.verbinden(ip, port);
		while(!spieler.getClient().isBereit()) {System.out.print("");}
		spieler.anmelden();
		ui.setStatus(Status.WARTEN_AUF_SPIELER);
	}
	
	
	public SpielDaten getSpielDaten() {
		return spiel_daten;
	}
	
	public void setSpielDaten(SpielDaten daten) {
		this.spiel_daten = daten;
	}
	
}
