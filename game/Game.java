package game;
import ui.*;

public class Game {

	private static String version = "ALPHA.07-07-18";
	LauncherWindow launcher;
	private SpielDaten daten;
	Moderator moderator;
	Spieler spieler;
	private DorfErstellen dorf_erstellen_ui;
	public Game() {
		daten = new SpielDaten();
		launcher = new LauncherWindow(this);
		Thread th_launcher = new Thread(launcher);
		th_launcher.start();
		while(!launcher.isReady()) {System.out.print("");}
		launcher.change(new HauptMenu(launcher));
	}
	
	public static void main(String[]args) {
		Game game = new Game();
	}
	
	public void dorfErstellen(DorfErstellen ui) {
		this.dorf_erstellen_ui = ui;
		ui.setStatus(DorfErstellen.Status.GEN_SERVER);
		moderator = new Moderator(this);
		int max_anschluesse = (int)ui.num_bewohner.getValue() + (int)ui.num_werwoelfe.getValue();
		moderator.set_max_anschluesse(max_anschluesse);
		moderator.server_starten();
		while(!moderator.getServerCreated()) {System.out.print("");}
		ui.setStatus(DorfErstellen.Status.WARTEN_AUF_SPIELER);
		ui.activate_connection_info(moderator.get_self_ip(), moderator.get_self_port());
	}
	
	public DorfErstellen getDotfErstellenUI() {
		return this.dorf_erstellen_ui;
	}
	


}
