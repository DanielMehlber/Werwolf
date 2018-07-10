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
	private Spieler spieler;
	public Anschluss(Server server) {
		this.server = server;
		
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
		byte[] inhalt = formatter.getInhalt(data);
		switch((int)data[0]) {
			//Anmeldung
			case 0:{
				System.out.println("Eine Anmeldung wurde eingereicht...");
				SpielerDaten spieler_daten = (SpielerDaten)formatter.ByteArrayToObject(inhalt);
				SpielDaten spiel_daten = server.getGame().getSpielDaten();
				
				if(!server.getGame().getSpielDaten().spielerNameFrei(spieler_daten.getName())) {
					System.err.println("Anmeldung abgelehnt. Die UserDaten sind bereits vergeben!");
					schreiben(formatter.formatieren(0, formatter.ObjectToByteArray(new Boolean(false))));
					server.anschlussErsetzen(this);
					return;
				}
				System.out.println("Anmeldung erfolgreich. "+spieler_daten.getName()+" erfolgreich eingeloggt!");
				schreiben(formatter.formatieren(0, formatter.ObjectToByteArray(new Boolean(true))));
				
				//Spieler zu SpielDaten hinzufügen und die anderen Clients zukommen lassen
				Spieler spieler = new Spieler(spieler_daten, server.getGame());
				this.spieler = spieler;
				
				//SpielDaten aktulisieren und weiterleiten
				server.getGame().getSpielDaten().addSpieler(spieler);
				server.rufen(formatter.formatieren(1, formatter.ObjectToByteArray(spiel_daten)));
				server.getGame().getDorfErstellenPanel().aktualisiereVerbundeneSpieler();
				break;
			}
			case -2:{
				spieler.getSpielerDaten().setBereit(true);
				server.rufen(formatter.formatieren(1, formatter.ObjectToByteArray(server.getGame().getSpielDaten())));
				//TODO: Prüfen ob alle Spieler bereit sind
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
	
	public Spieler getSpieler() {
		return spieler;
	}

	

}
