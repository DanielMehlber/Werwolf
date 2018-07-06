package game;

public class Game {
	
	public Game() {
		
	}

	public static void main(String[] args) {
		Moderator m = new Moderator();
		m.set_max_anschluesse(10);
		m.server_starten();
	}

}
