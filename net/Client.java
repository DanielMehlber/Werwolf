package net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	public Client(Spieler spieler, String ziel_ip_addresse, int ziel_port) {
		this.spieler = spieler;
		super.set_ziel_ip_addresse(ziel_ip_addresse);
		super.set_ziel_port(ziel_port);
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
		} catch (IOException e) {
			out.SpielAusgabe.error("Verbindungsfehler", "Bitte überpüfe Die IP_Addresse und den Port des Zielservers oder deine Internetverbindung!");
			e.printStackTrace();
		}
		
	}

	protected Spieler getSpieler() {
		return spieler;
	}

	protected void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	
	

	

}
