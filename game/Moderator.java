package game;

import net.Server;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server implements Runnable{

	private SpielDaten spiel_daten;
	
	public Moderator(Game game) {
		spiel_daten = new SpielDaten();
		setGame(game);
	}
	
	
	public SpielDaten getDaten() {
		return spiel_daten;
	}
	
	
	
	
	
	
	

}
