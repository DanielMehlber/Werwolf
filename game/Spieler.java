package game;

import net.Client;

public class Spieler{
	
	
	private String name;
	private boolean alive;
	private Client client;
	
	public Spieler(String name) {
		this.name = name;
		alive = true;
		
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void kill() {
		alive = false;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
	

}
