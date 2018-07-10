package net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import game.Game;
import game.SpielDaten;




/**
 * Ein Server, der mehrere Anschlüsse bereitstellen kann.
 * @author Daniel Mehlber
 * */
public class Server extends NetzwerkKomponente implements Runnable{
	
	private ServerSocket server;
	private int max_anschluesse;
	private ArrayList<Anschluss> anschluss_liste;	
	private boolean bereit;
	
	private Game game;
	
	public Server(Game game) {
		this.game = game;
		max_anschluesse = -1;
		bereit = false;
	}
	
	/**
	 * Ermöglicht anderen Objekten den Server, ohne sich um Threading kümmern zu müssen, zu starten
	 * */
	public void server_starten() {
		max_anschluesse = game.getSpielDaten().get_max_spieler();
		Thread th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		System.out.println("Server wird gestartet...");
		generatePort();
		verbindungEinrichten();
		anschluesseErstellen();
		System.out.println("Abgeschlossen...");
		try {
			server.getInetAddress();
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
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
			JOptionPane.showMessageDialog(null, "Server an Port"+get_self_port()+" nicht erstellbar",
					"Fehler bei der Servererstellung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	 /**
	  * Bereitstellen von Anschlüssen, an denen Client-Sockts andocken können
	  * */
	private void anschluesseErstellen() {
		if(max_anschluesse <= 0) {
			JOptionPane.showMessageDialog(null, "Die Anzahl der Anschlüsse ist ungültig!",
					"Anschlusszahl <= 0", JOptionPane.ERROR_MESSAGE);
			return;
		}
		anschluss_liste = new ArrayList<Anschluss>();
		for(int i = 0; i < max_anschluesse; i++) {
			Anschluss a = new Anschluss(this);
			anschluss_liste.add(a);
			Thread at = new Thread(a);
			at.start();
			bereit = true;
		}
	}
	
	/**
	 * Eine Nachricht an alle verfügbaren Clients
	 * @param nachricht Nachricht zum verschicken
	 * */
	public void rufen(String nachricht) {
		for(int i = 0; i < anschluss_liste.size(); i++) {
			if(anschluss_liste.get(i).getOutputStream()!=null) {
				anschluss_liste.get(i).schreiben(nachricht);
			}
		}
	}
	
	public void rufen(byte[] data) {
		boolean irgenteinEmpfaenger = false;
		for(int i = 0; i < anschluss_liste.size(); i++) {
			if(anschluss_liste.get(i).getOutputStream()!=null && !anschluss_liste.get(i).getSocket().isClosed()) {
				anschluss_liste.get(i).schreiben(data);
				irgenteinEmpfaenger = true;
			}
		}
		if(!irgenteinEmpfaenger) {
			System.err.println("Keiner zum Zuschicken da !");
		}
	}
	
	
	
	
	public Anschluss getAnschlussByName(String name) {
		for(int i = 0; i < anschluss_liste.size(); i++) {
			Anschluss anschluss = anschluss_liste.get(i);
			//TODO: Anschluss SpielerDaten
		}
		return null;
	} 
	
	public void nachricht(Anschluss a, byte[] data) {
		a.schreiben(data);
	}
	
	public Anschluss[] getLebendeSpieler() {
		//TODO Implement
		return null;
	}

	@Override //Server is not listening
	protected void verarbeiten(byte[] data) {return;}
	@Override
	protected void verarbeiten(String data) {return;}
	
	public ServerSocket getServerSocket() {
		return server;
	}
	
	public void set_max_anschluesse(int i) {
		max_anschluesse = i;
	}
	
	public int get_max_anschluesse() {
		return max_anschluesse;
	}
	
	public boolean getServerBereit() {
		return this.bereit;
	}
	
	public ArrayList<Anschluss> getAnschlussListe() {
		return anschluss_liste;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void anschlussErsetzen(Anschluss anschluss) {
		try {
			anschluss.getSocket().close();
			anschluss.destroy();
			anschluss_liste.remove(anschluss);
			Anschluss neu = new Anschluss(this);
			anschluss_liste.add(neu);
			Thread neu_thread = new Thread(neu);
			neu_thread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
