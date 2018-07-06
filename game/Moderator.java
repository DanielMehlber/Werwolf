package game;

import net.Server;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server{

	private SpielDaten daten;
	
	public Moderator() {
		daten = new SpielDaten();
	}
	
	
	public SpielDaten getDaten() {
		return daten;
	}
	
	
	
	
	

}
