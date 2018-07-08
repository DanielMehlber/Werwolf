package game;

import net.Server;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server implements Runnable{

	private SpielDaten spiel_daten;
	
	public Moderator(Game game, SpielDaten spieldaten) {
		super(spieldaten);
		spiel_daten = spieldaten;
		setGame(game);
	}
	
	
	public SpielDaten getSpielDaten() {
		return spiel_daten;
	}
	
	public void updateSpielDaten(SpielDaten daten) {
		this.spiel_daten = daten;
	}
	
	
	
	
	
	
	

}
