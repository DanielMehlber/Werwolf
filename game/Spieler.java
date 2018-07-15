package game;

import java.io.Serializable;

import karten.Kreatur;
import net.Client;

public class Spieler implements Serializable{
	

	private Client client;
	private SpielerDaten spieler_daten;
	private boolean getarnt;
	
	
	public Spieler(SpielerDaten daten, Game game) {
		this.spieler_daten = daten;
		
	}
	
	/**
	 * Mithilfe dieser Methode und des gesetzten Clients kann sich der Spieler verbinden
	 * @param ziel_ip_addresse Die IP Addresse des Ziels
	 * @param ziel_port Der Port auf dem sich der Remote Server befindet
	 * */
	public void verbinden(String ziel_ip_addresse, int ziel_port) {
		System.out.println("Verbindung zum Server wird aufgebaut...");
		if(client == null) {
			out.SpielAusgabe.error(null, "Spieler: Kein Client", "Der Client muss erstellt und gesetzt werden!");
			return;
		}
		Thread th = new Thread(client);
		th.start();
		
	}
	
	/**
	 * Tötet einen Spieler
	 * */
	public void sterben() {
		spieler_daten.setLebendig(false);
	}
	
	/**
	 * Sendet, dass der Spieler bereit ist
	 * */
	public void bereit_senden() {
		byte[] data = getClient().getFormatter().formatieren(2, getClient().getFormatter().ObjectToByteArray(getSpielerDaten().getName()));
		getClient().schreiben(data);
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
		client.schreiben(client.getFormatter().formatieren((byte)0, client.getFormatter().ObjectToByteArray(spieler_daten)));
	}
	
	public boolean isVerbunden() {
		return client.isBereit();
	}
	
	public void setGetarnt(boolean g) {
		this.getarnt = g;
	}
	
	public void setSpielerDaten(SpielerDaten daten) {
		this.spieler_daten = daten;
	}
	
	public void chatSchreiben(String nachricht) {
		byte[] n = getClient().getFormatter().formatieren(3, client.getFormatter().ObjectToByteArray(nachricht));
		System.out.println(client.getFormatter().ByteArrayToString(n));
		getClient().schreiben(n);
	}
}
