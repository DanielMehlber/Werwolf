package net;

import java.io.IOException;

import javax.swing.JOptionPane;

import game.Spieler;



/**
 * Ein Anschluss für einen Client-Socket, zum Austausch von Informationen zwischen Server und Client
 * @author Daniel Mehlber
 * */
public class Anschluss extends NetzwerkKomponente implements Runnable{
	
	
	private Server server;
	public Anschluss(Server server) {
		this.server = server;
		super.setGame(server.getGame());
		
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
		byte[] content = dataConverter.getContent(data);
		switch(data[0]) {
		//Other one logged out
		case -1: {destroy(); break;}
		case 0: {
			String name = (String)dataConverter.ByteArrayToObject(content);
			//Spielername bereits vergeben --> Kann nicht einloggen!
			if (!getGame().getSpielDaten().spielerNameFrei(name)) {
				Boolean b = false;
				byte[] message = dataConverter.format((byte)0, dataConverter.ObjectToByteArray(b));
				schreiben(message);
				return;
			}
			Spieler spieler = new Spieler(name);
			getGame().getSpielDaten().addSpieler(spieler);
			getGame().getDotfErstellenUI().set_player_connected(
					getGame().getSpielDaten().getMax_spieler()
					,getGame().getSpielDaten().getSpielerAnzahl());
			System.out.println("Spieler "+name+" hat sich registriert");
			Boolean b = true;
			byte[] message = dataConverter.format((byte)0, dataConverter.ObjectToByteArray(b));
			schreiben(message);
			break;}
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
			out.SpielAusgabe.error("Anschluss fehler", "Fehler beim akzeptieren eines Anschlusses von Seiten des Servers");
			System.exit(-1);
		}
		System.out.println("Ein Client hat sich verbunden!");
		
	}

	

}
