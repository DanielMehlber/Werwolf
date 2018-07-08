package game;

import net.Server;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server implements Runnable{

	private Game game;
	
	public Moderator(Game game) {
		super(game);
		this.game = game;
	}
	
	
	public Game getGame() {
		return game;
	}
	
	
	
	
	
	
	

}
