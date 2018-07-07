package game;
import net.Client;
import ui.*;

public class Game {

	private static String version = "ALPHA.07-07-18";
	private LauncherWindow launcher;
	private SpielDaten spiel_daten;
	private Moderator moderator;
	private Spieler spieler;
	private DorfErstellen dorf_erstellen_ui;
	private DorfBeitreten dorf_beitreten_ui;
	public Game() {
		spiel_daten = new SpielDaten();
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
		
	}
	
	public void dorfBeitreten(DorfBeitreten ui) {
		
	}
	
	public DorfErstellen getDotfErstellenUI() {
		return this.dorf_erstellen_ui;
	}
	
	public DorfBeitreten getDorfBeitretenUI() {
		return this.dorf_beitreten_ui;
	}
	
	public SpielDaten getSpielDaten() {
		return spiel_daten;
	}
	
	public void aktualisiereSpielDaten(SpielDaten data) {
		this.spiel_daten = data;
	}
	


}
