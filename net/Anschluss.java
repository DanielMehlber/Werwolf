package net;

import java.io.IOException;

import ui.ErrorThrow;

/**
 * Ein Anschluss für einen Client-Socket, zum Austausch von Informationen zwischen Server und Client
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
	}

	@Override
	protected void verarbeiten(byte[] data) {
		switch(data[0]) {
		case -1: {destroy();} //Exit
		case 0: { //Zahl
			System.out.println(data[1]);
		}
		case 1: {} //ChatMessage
		case 2: {}
		}
		
	}
	
	private void anschlussErstellen() {
		try {
			socket = server.getServerSocket().accept();
		} catch (IOException e) {
			ErrorThrow err = new ErrorThrow("Fehler beim öffnen eines Anschlusses", e.getMessage());
		}
		System.out.println("Ein Client hat sich verbunden!");
	}

}
