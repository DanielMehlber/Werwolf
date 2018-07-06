package game;

import karten.Kreatur;
import net.Client;

public class Spieler{
	
	private String name;
	private boolean alive;
	private Client client;
	private SpielDaten daten;
	private Kreatur kreatur;
	
	public Spieler(String name) {
		this.name = name;
		alive = true;
	}
	
	public void verbinden(String ziel_ip_addresse, int ziel_port) {
		client = new Client(this, ziel_ip_addresse, ziel_port);
		Thread th = new Thread(client);
		th.start();
	}
	
	public void setKreatur(Kreatur kreatur) {
		this.kreatur = kreatur;
	}
	
	public Kreatur getKreatur() {
		return this.kreatur;
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
