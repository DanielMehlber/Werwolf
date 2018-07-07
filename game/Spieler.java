package game;

import karten.Kreatur;
import net.Client;

public class Spieler{
	

	private Client client;
	private SpielDaten spielData;
	private SpielerDaten data;
	
	public Spieler(String name) {
		data = new SpielerDaten();
		data.setName(name);
	}
	
	public void verbinden(String ziel_ip_addresse, int ziel_port) {
		if(client == null) {
			out.SpielAusgabe.error("Spieler: Kein Client", "Der Client muss erstellt und gesetzt werden!");
			return;
		}
		Thread th = new Thread(client);
		th.start();
	}
	
	
	public void kill() {
		data.setAlive(false);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public SpielDaten getSpielDaten() {
		return spielData;
	}
	
	public void setSpielDaten(SpielDaten data) {
		this.spielData = data;
	}
	
	public SpielerDaten getSpielerDaten() {
		return data;
	}
	
}
