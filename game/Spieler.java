package game;

import java.io.Serializable;

import karten.Kreatur;
import net.Client;

public class Spieler implements Serializable{
	

	private Client client;
	private SpielerDaten spieler_daten;
	
	
	public Spieler(SpielerDaten daten, Game game) {
		this.spieler_daten = daten;
		
	}
	
	public void verbinden(String ziel_ip_addresse, int ziel_port) {
		System.out.println("Verbindung zum Server wird aufgebaut...");
		if(client == null) {
			out.SpielAusgabe.error(null, "Spieler: Kein Client", "Der Client muss erstellt und gesetzt werden!");
			return;
		}
		Thread th = new Thread(client);
		th.start();
		
	}
	
	
	public void sterben() {
		spieler_daten.setLebendig(false);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
		
	public SpielerDaten getSpielerDaten() {
		return spieler_daten;
	}
	
	public void anmelden() {
		client.schreiben(client.formatter.formatieren((byte)0, client.formatter.ObjectToByteArray(spieler_daten)));
	}
	
	public boolean isVerbunden() {
		return client.isBereit();
	}
	
	
	
}
