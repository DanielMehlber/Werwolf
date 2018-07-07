package game;

import java.io.Serializable;

import karten.Kreatur;
import net.Client;

public class Spieler implements Serializable{
	

	private Client client;
	private SpielDaten spiel_daten;
	private SpielerDaten spieler_daten;
	
	public Spieler(String name) {
		spieler_daten = new SpielerDaten();
		spieler_daten.setName(name);
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
		spieler_daten.setAlive(false);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public SpielDaten getSpielDaten() {
		return spiel_daten;
	}
	
	public void setSpielDaten(SpielDaten data) {
		this.spiel_daten = data;
	}
	
	public SpielerDaten getSpielerDaten() {
		return spieler_daten;
	}
	
}
