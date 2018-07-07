package game;
import net.Client;
import ui.*;

public class Game {

	private static String version = "ALPHA.07-07-18";
	private LauncherWindow launcher;
	private SpielDaten daten;
	private Moderator moderator;
	private Spieler spieler;
	private DorfErstellen dorf_erstellen_ui;
	private DorfBeitreten dorf_beitreten_ui;
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
		getSpielDaten().setMax_spieler(max_anschluesse);
		while(!moderator.getServerCreated()) {System.out.print("");}
		ui.setStatus(DorfErstellen.Status.WARTEN_AUF_SPIELER);
		ui.activate_connection_info(moderator.get_self_ip(), moderator.get_self_port());
	}
	
	public void dorfBeitreten(DorfBeitreten ui) {
		dorf_beitreten_ui = ui;
		ui.setStatus(DorfBeitreten.Status.VERBINDEN);
		spieler = new Spieler(ui.getName());
		Client spielerClient = new Client(spieler);
		spielerClient.set_ziel_ip_addresse(ui.getIP());
		spielerClient.set_ziel_port(ui.getPort());
		Thread th_client = new Thread(spielerClient);
		th_client.start();
		spieler.setClient(spielerClient);
		while(!spielerClient.isVerbunden()) {System.out.print("");}
		spieler.getClient().schreiben(spieler.getClient().dataConverter.format((byte)0, 
				spieler.getClient().dataConverter.ObjectToByteArray(spieler.getSpielerDaten().getName())));
	}
	
	public DorfErstellen getDotfErstellenUI() {
		return this.dorf_erstellen_ui;
	}
	
	public SpielDaten getSpielDaten() {
		return daten;
	}
	
	public void refreshSpielData(SpielDaten data) {
		this.daten = data;
	}
	


}
