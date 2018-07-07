package game;

import net.Server;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server implements Runnable{

	private SpielDaten daten;
	
	public Moderator(Game game) {
		daten = new SpielDaten();
		setGame(game);
	}
	
	
	public SpielDaten getDaten() {
		return daten;
	}
	
	
	
	
	
	
	

}
