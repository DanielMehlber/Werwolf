package net;

import java.net.Socket;

import javax.swing.JOptionPane;

import game.Spieler;



/**
 * Ein Client, der sich mit einem Serveranschluss verbinden und kommunizieren kann
 * @author Daniel Mehlber
 * */
public class Client extends NetzwerkKomponente implements Runnable{
	
	private Spieler spieler;
	public Client() {
		
	}
	
	public Client(Spieler spieler) {
		this.spieler = spieler;
	}

	@Override
	public void run() {
		verbinden();
		kommunikationBereitstellen();
		auffassen();
		destroy();
	}

	@Override
	protected void verarbeiten(byte[] data) {
		System.out.println("Bytes vom Server erhalten!");
		System.out.println("->"+(int)data[0]);
		switch((int)data[0]) {
		case -1: {destroy(); break;}
		}
		
	}
	
	@Override
	protected void verarbeiten(String data) {
		System.out.println("String vom Server erhalten!");
		
	}
	
	/**
	 * Verbindet sich mit einem Anschluss
	 * */
	private void verbinden() {
		try {
			socket = new Socket(get_ziel_inet_addresse(), get_ziel_port());
		} catch (Exception e) {
			out.SpielAusgabe.error("Verbindungsfehler", "Bitte überpüfe Die IP_Addresse und den Port des Zielservers oder deine Internetverbindung!");
		}
		
	}

	protected Spieler getSpieler() {
		return spieler;
	}

	protected void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	
	

	

}
