package game;

import net.Server;

public class Moderator extends Server{
	
	/**
	 * Leitet und organisiert das Spiel
	 * */
	private SpielDaten daten;
	public Moderator() {
		daten = new SpielDaten();
	}
	
	public SpielDaten getDaten() {
		return daten;
	}
	
	
	

}
