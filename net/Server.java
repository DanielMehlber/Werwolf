package net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ui.ErrorThrow;

/**
 * Ein Server, der mehrere Anschlüsse bereitstellen kann.
 * @author Daniel Mehlber
 * */
public class Server extends NetzwerkKomponente implements Runnable{
	
	private ServerSocket server;
	private int max_anschluesse;
	private ArrayList<Anschluss> anschluss_liste;	
	public Server() {
		max_anschluesse = -1;
	}

	@Override
	public void run() {
		System.out.println("Server wird gestartet...");
		generatePort();
		verbindungEinrichten();
		anschluesseErstellen();
		//Ein Server an sich hat keine Kommunikation
		System.out.println("Abgeschlossen...");
		try {
			server.getInetAddress();
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Erstellung eines ServerSockets
	 * */
	private void verbindungEinrichten() {
		try {
			server = new ServerSocket(super.get_self_port());
		} catch (Exception e) {
			System.err.println("Fehler, bei der Erstellung des ServerSockets mit Port"+super.get_self_port());
			new ErrorThrow("Server kann nicht auf gefundenen Port generiert werden!",
					"port="+get_self_port());
			e.printStackTrace();
		}
	}
	
	 /**
	  * Bereitstellen von Anschlüssen, an denen Client-Sockts andocken können
	  * */
	private void anschluesseErstellen() {
		if(max_anschluesse <= 0) {
			System.err.println("Fehler, keine maximalen Anschlüsse definiert");
			new ErrorThrow("Maximalen Anschlüsse nicht definiert!", "anschlusse="+max_anschluesse).show();
			return;
		}
		anschluss_liste = new ArrayList<Anschluss>();
		for(int i = 0; i < max_anschluesse; i++) {
			Anschluss a = new Anschluss(this);
			anschluss_liste.add(a);
			Thread at = new Thread(a);
			at.start();
		}
	}

	@Override //Server is not listening
	protected void verarbeiten(byte[] data) {return;}
	
	public ServerSocket getServerSocket() {
		return server;
	}
	
	public void set_max_anschluesse(int i) {
		max_anschluesse = i;
	}
	
	public int get_max_anschluesse() {
		return max_anschluesse;
	}

}
