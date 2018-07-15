package net;

import java.io.IOException;

import javax.swing.JOptionPane;

import game.SpielDaten;
import game.Spieler;
import game.SpielerDaten;



/**
 * Ein Anschluss f�r einen Client-Socket, zum Austausch von Informationen zwischen Server und Client
 * @author Daniel Mehlber
 * */
public class Anschluss extends NetzwerkKomponente implements Runnable{
	
	
	private Server server;
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
		System.out.println("Bytes vom Client erhalten! Type: "+(int)data[0]);
		byte[] inhalt = formatter.getInhalt(data);
		switch((int)data[0]) {
			case -1:{
				System.err.println("Client ausgeloggt");
				destroy();
			}
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
				
				//Spieler zu SpielDaten hinzuf�gen und die anderen Clients zukommen lassen
				Spieler spieler = new Spieler(spieler_daten, server.getGame());
				
				//SpielDaten aktulisieren und weiterleiten
				server.getGame().getSpielDaten().addSpieler(spieler);
				server.spielDatenTeilen();
				server.getGame().getDorfErstellenPanel().aktualisiereVerbundeneSpieler();
				break;
			}
			case 2:{
				System.out.println("Ein Spieler ist Bereit!");
				server.getGame().getSpielDaten().getSpieler((String)formatter.ByteArrayToObject(inhalt)).getSpielerDaten().setBereit(true);
				
				server.spielDatenTeilen();
				if(server.getGame().sindAlleBereit()) {
					System.err.println("Es sind alle Bereit, der startcode wird gesendet...");
					server.rufen(new byte[] {2});
				}
				
				break;
			}
			
			case 3: {
				System.out.println("Chatnachricht weiterleiten...");
				server.rufen(data);
				break;
			}
		}
	}
	
	@Override
	protected void verarbeiten(String data) {}
	
	/**
	 * �ffnet einen Anschluss f�r einen Client und wartet auf diesen
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
