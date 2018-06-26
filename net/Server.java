package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Ein Server, der mehrere Anschl�sse bereitstellen kann.
 * @author Daniel Mehlber
 * */
public class Server extends NetzwerkKomponente implements Runnable{
	
	private ServerSocket server;
	private int max_anschluesse;
	private ArrayList<Anschluss> anschluss_liste;	
	public Server() {
		max_anschluesse = 0;
	}

	@Override
	public void run() {
		generatePort();
		verbindungEinrichten();
		//Ein Server an sich hat keine Kommunikation
	}
	
	/**
	 * Erstellung eines ServerSockets
	 * */
	private void verbindungEinrichten() {
		try {
			server = new ServerSocket(super.get_self_port());
		} catch (IOException e) {
			System.err.println("Fehler, bei der Erstellung des ServerSockets mit Port"+super.get_self_port());
			e.printStackTrace();
		}
	}
	
	 /**
	  * Bereitstellen von Anschl�ssen, an denen Client-Sockts andocken k�nnen
	  * */
	private void anschluesseErstellen() {
		if(max_anschluesse < 0) {
			System.err.println("Fehler, keine maximalen Anschl�sse definiert");
		}
		for(int i = 0; i < max_anschluesse; i++) {
			//Anschluss �ffnen
		}
	}

	@Override //Server is not listening
	protected void verarbeiten(byte[] data) {return;}
	

}
