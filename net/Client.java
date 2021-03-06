package net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import game.Game;
import game.SpielDaten;
import game.SpielStatus;
import game.SpielStatusPaket;
import game.Spieler;
import game.Todesmeldung;
import karten.Kreatur;
import ui.HauptMenuPanel;
import ui.HauptSpielPanel;
import ui.DorfBeitretenPanel.Status;



/**
 * Ein Client, der sich mit einem Serveranschluss verbinden und kommunizieren kann
 * @author Daniel Mehlber
 * */
public class Client extends NetzwerkKomponente implements Runnable{
	
	private boolean bereit;
	private Spieler spieler;
	private Game game;
	public Client(Game game) {
		this.game = game;
		bereit = false;
		
	}
	
	public Client(Game game, Spieler spieler) {
		this.spieler = spieler;
		bereit = false;
		this.game = game;
	}
	
	public Client(Game game, Spieler spieler, String ziel_ip_addresse, int ziel_port) {
		this.spieler = spieler;
		super.set_ziel_ip_addresse(ziel_ip_addresse);
		super.set_ziel_port(ziel_port);
		bereit = false;
		this.game = game;
	}

	@Override
	public void run() {
		verbinden();
		kommunikationBereitstellen();
		bereit = true;
		auffassen();
		destroy();
	}

	@Override
	protected void verarbeiten(byte[] data) {
		//System.out.println("Bytes vom Server erhalten! Type: "+(int)data[0]);
		byte[] inhalt = formatter.getInhalt(data);
		switch((int)data[0]) {
		//Anmeldung best�tigt
		case -3: {
			System.exit(0);
			break;
		}
		case 0: {
			if(!dataGueltig(inhalt)){
				return;
			}
			
			System.out.println("Anmeldung empfangen");
			
			Boolean b = (Boolean)formatter.ByteArrayToObject(inhalt);
			if(b) {
				System.out.println("Erfolgreich Angemeldet");
			}else {
				System.err.println("Abgelehnt, Name bereits in Verwendung");
				out.SpielAusgabe.error(null, "Anmeldung verweigert", "Dein Name ist bereits in Verwendung");
				getGame().getDorfBeitretenPanel().setStatus(Status.BEREIT);
				getGame().getLauncherWindow().wechseln(new HauptMenuPanel(getGame().getLauncherWindow()));
			}
			break;
		}
		//Aktualisierung der Spieldaten
		case 1: {
			if(!dataGueltig(inhalt)){
				return;
			}
			System.out.println("SpielDaten aktualisierung empfangen");
			SpielDaten daten = (SpielDaten)formatter.ByteArrayToObject(inhalt);
			
			game.setSpielDaten(daten);
			
			if(game.getDorfBeitretenPanel() != null)
				game.getDorfBeitretenPanel().aktualisiereVerbundeneSpieler();
			else if(game.getDorfErstellenPanel() != null)
				game.getDorfErstellenPanel().aktualisiereVerbundeneSpieler();
			
			if(game.getSpielDaten().getAbstimmung()!=null) {
				System.out.println("  >>In den Spieldaten ist eine Abstimmung");
			}
			break;
			
		}
		//SpielStart einleiten
		case 2:{
			
			System.out.println("Alle sind bereit. Das Spiel kann gestartet werden");
			getGame().spielStarten();
			break;
		}
		case 3: {
			if(!dataGueltig(inhalt)){
				return;
			}
			System.out.println("Chatnachricht erhalten");
			game.getPhone().empfangen((String)formatter.ByteArrayToObject(inhalt));
			break;
		}
		//Zeit setzen
		case 4: {
			if(!dataGueltig(inhalt)){
				return;
			}
			String zeit = (String)formatter.ByteArrayToObject(inhalt);
			String st = zeit.substring(0, zeit.indexOf(":"));
			String min = zeit.substring(zeit.indexOf(":")+1, zeit.length());
			int stunde = Integer.parseInt(st);
			int minute = Integer.parseInt(min);
			HauptSpielPanel hsp = getGame().getGameWindow().getHauptSpielPanel();
			if(hsp != null)
				hsp.setZeit(stunde, minute);
			break;
		}
		//SpielStatus
		case 5: {
			if(!dataGueltig(inhalt)) {
				return;
			}
			SpielStatusPaket status = (SpielStatusPaket)formatter.ByteArrayToObject(inhalt);
			getGame().setSpielStatus(status);
			break;
		}
		//Todesmeldung
		case 6: {
			if(!dataGueltig(inhalt)){
				return;
			}
			Todesmeldung meldung = (Todesmeldung)formatter.ByteArrayToObject(inhalt);
			getGame().spielerSterbenLassen(meldung);
			break;
		}
		//Message
		case 7: {
			if(!dataGueltig(inhalt)){
				return;
			}
			String mitteilung = (String)formatter.ByteArrayToObject(inhalt);
			out.SpielAusgabe.info(null, "Mitteilung vom Moderator", mitteilung);
			break;
		}
		
		case 8: {
			if(!dataGueltig(inhalt)){
				return;
			}
			System.out.println("Ein Spieler soll entfernt werden...");
			String name = (String) formatter.ByteArrayToObject(inhalt);
			getGame().spielerRauswerfen(name);
			break;
		}
		default:{System.err.println("Die Nachricht mit dem Prefix "+(int)data[0]+" konnte nicht identifiziert werden!");break;}
		}
		
		
	}
	
	@Override
	@Deprecated
	protected void verarbeiten(String data) {
		System.out.println("String vom Server erhalten!");
		
	}
	
	/**
	 * Verbindet sich mit einem Anschluss
	 * */
	private void verbinden() {
		try {
			socket = new Socket(get_ziel_inet_addresse(), get_ziel_port());
		} catch (IOException e) {
			out.SpielAusgabe.error(null, "Verbindungsfehler", "Bitte �berp�fe Die IP_Addresse und den Port des Zielservers oder deine Internetverbindung!");
			e.printStackTrace();
		}
		System.out.println("Verbindung hergestellt...");
		
	}

	protected Spieler getSpieler() {
		return spieler;
	}

	protected void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	
	public boolean isBereit() {
		return bereit;
	}
	
	public Game getGame() {
		return game;
	}

	

}
