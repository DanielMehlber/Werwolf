package net;

import java.io.IOException;

import javax.swing.JOptionPane;

import game.SpielDaten;
import game.Spieler;
import game.SpielerDaten;



/**
 * Ein Anschluss für einen Client-Socket, zum Austausch von Informationen zwischen Server und Client
 * @author Daniel Mehlber
 * */
public class Anschluss extends NetzwerkKomponente implements Runnable{
	
	
	private Server server;
	public Anschluss(Server server) {
		this.server = server;
		super.setGame(server.getGame());
		
	}

	@Override
	public void run() {
		anschlussErstellen();
		kommunikationBereitstellen();
		auffassen();
		destroy();
	}

	@Override
	protected void verarbeiten(byte[] data) {
		System.out.println("Bytes vom Client erhalten!");
		System.out.println(formatter.ByteArrayToString(data));
		byte[] inhalt = formatter.getInhalt(data);
		switch((int)data[0]) {
			//Anmeldung
			case 0:{
				System.out.println("Eine Anmeldung wurde eingereicht...");
				SpielerDaten spieler_daten = (SpielerDaten)formatter.ByteArrayToObject(inhalt);
				SpielDaten spiel_daten = server.getSpielDaten();
				
				//Überprüfen, ob Spielername noch frei.
				if(!spiel_daten.spielerNameFrei(spieler_daten.getName())) {
					schreiben(formatter.formatieren((byte)0, formatter.ObjectToByteArray(new Boolean(false))));
					System.out.println("Der Spielername ist ungültig!");
					//TODO: Wenn Name nicht mehr frei
					return;
				}
				
				//Spieler zu SpielDaten hinzufügen und die anderen Clients zukommen lassen
				Spieler spieler = new Spieler(spieler_daten.getName());
				server.getSpielDaten().addSpieler(spieler);
				System.out.println("Sende Erlaubnis");
				schreiben(formatter.formatieren((byte)0, formatter.ObjectToByteArray(new Boolean(true))));
				System.out.println("Sende SpielDaten an alle");
				while(!isFertigGeschrieben()) {System.out.print("");}
				server.rufen(formatter.formatieren((byte)1, formatter.ObjectToByteArray(spiel_daten)));
				
				System.out.println("Die Anmeldung von "+spieler.getSpielerDaten().getName()+" ist abgeschlossen");
				server.getGame().getDorfErstellenPanel().aktualisiereVerbundeneSpieler();
				break;
			}
		}
	}
	
	@Override
	protected void verarbeiten(String data) {}
	
	/**
	 * Öffnet einen Anschluss für einen Client und wartet auf diesen
	 * */
	private void anschlussErstellen() {
		try {
			socket = server.getServerSocket().accept();
		} catch (IOException e) {
			out.SpielAusgabe.error(null, "Anschluss fehler", "Fehler beim akzeptieren eines Anschlusses von Seiten des Servers");
			System.exit(-1);
		}
		System.out.println("Ein Client hat sich verbunden!");
		
	}

	

}
