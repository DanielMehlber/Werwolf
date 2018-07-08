package game;
import net.Client;
import ui.*;
import ui.DorfBeitretenPanel.Status;

public class Game {
	
	private LauncherWindow launcher;
	private DorfErstellenPanel dorf_erstellen_panel;
	private DorfBeitretenPanel dorf_beitreten_panel;
	
	private SpielDaten spiel_daten;
	private Moderator moderator;
	
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
		moderator = new Moderator(this, spiel_daten);
		//SpielDaten setzen
		moderator.getSpielDaten().set_max_spieler(ui.getGesamteSpielerAnzahl());
		
		//Server erstellen mit Thread
		ui.setStatus(DorfErstellenPanel.Status.GEN_SERVER);
		moderator.server_starten();
		
		//Warten bis Server fertig ist
		while(!moderator.getServerBereit()) {System.out.print("");}
		
		//Warten auf Spieler
		ui.setStatus(DorfErstellenPanel.Status.WARTEN_AUF_SPIELER);
		ui.verbindungsInfoAnzeigen(moderator.get_self_ip(), moderator.get_self_port());
		
	}
	
	public void dorf_beitreten(DorfBeitretenPanel ui) {
		dorf_beitreten_panel = ui;
		
		ui.setStatus(Status.VERBINDEN);
		String name = ui.getName();
		String ip = ui.getIP();
		int port = ui.getPort();
		Spieler spieler = new Spieler(name);
		
		Client client = new Client(this, spieler, ip, port);
		spieler.setClient(client);
		
		spieler.verbinden(ip, port);
		while(!spieler.getClient().isVerbunden()) {System.out.print("");}
		spieler.anmelden();
		ui.setStatus(Status.WARTEN_AUF_SPIELER);
	}
	
	
	public SpielDaten getSpielDaten() {
		return spiel_daten;
	}
	
}
