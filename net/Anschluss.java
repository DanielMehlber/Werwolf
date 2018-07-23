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
	private String name;
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
				break;
			}
			//Anmeldung
			case 0:{
				
				if(!dataGueltig(inhalt)) {
					server.anschlussErsetzen(this);
					return;
				}
				
				System.out.println("Eine Anmeldung wurde eingereicht...");
				SpielerDaten spieler_daten = (SpielerDaten)formatter.ByteArrayToObject(inhalt);
				SpielDaten spiel_daten = server.getGame().getSpielDaten();
				setName(spieler_daten.getName());
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
				
				//SpielDaten aktulisieren und weiterleiten
				server.getGame().getSpielDaten().addSpieler(spieler);
				server.spielDatenTeilen();
				server.getGame().getDorfErstellenPanel().aktualisiereVerbundeneSpieler();
				break;
			}
			//Spieler ist bereit
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
			//Chatnachricht weiterleiten
			case 3: {
				System.out.println("Chatnachricht weiterleiten...");
				server.rufen(data);
				break;
			}
			//SpielDaten weiterleiten (Bei Abstimmung etc)
			case 4: {
				if(!dataGueltig(inhalt)){
					return;
				}
				server.rufen(formatter.formatieren(1, formatter.ObjectToByteArray(server.getGame().getSpielDaten())));
				break;
			}
			//Zeitraffer
			case 5: {
				server.getGame().getModerator().zeitRaffer();
				break;
			}
			//Event Ueberspringen
			case 6: {
				if(!dataGueltig(inhalt)){
					return;
				}
				server.getGame().eventUeberspringen((String)formatter.ByteArrayToObject(inhalt));
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
