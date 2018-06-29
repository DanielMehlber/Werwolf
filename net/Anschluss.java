package net;

import java.io.IOException;

import javax.swing.JOptionPane;



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
	}

	@Override
	protected void verarbeiten(byte[] data) {}
	
	@Override
	protected void verarbeiten(String data) {}
	
	/**
	 * �ffnet einen Anschluss f�r einen Client und wartet auf diesen
	 * */
	private void anschlussErstellen() {
		try {
			socket = server.getServerSocket().accept();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim Erstellen eines Anschlusses!");
			System.exit(-1);
		}
		System.out.println("Ein Client hat sich verbunden!");
	}

	

}
