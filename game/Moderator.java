package game;

import net.Server;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server implements Runnable{

	private Game game;
	private boolean moderiert;
	
	public Moderator(Game game) {
		super(game);
		this.game = game;
		this.moderiert = true;
	}
	
	
	public Game getGame() {
		return game;
	}
	
	public void uebernehmen() {
		moderiert = true;
		game.getSpielDaten().kartenZiehen();
		super.spielDatenTeilen();
		
	}
	
	public boolean getModeriert() {
		return moderiert;
	}
	
	
	
	
	

}
