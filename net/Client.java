package net;

import java.net.Socket;

import javax.swing.JOptionPane;



/**
 * Ein Client, der sich mit einem Serveranschluss verbinden und kommunizieren kann
 * @author Daniel Mehlber
 * */
public class Client extends NetzwerkKomponente implements Runnable{

	public Client() {
		
	}

	@Override
	public void run() {
		verbinden();
		kommunikationBereitstellen();
		auffassen();
	}

	@Override
	protected void verarbeiten(byte[] data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void verarbeiten(String data) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Verbindet sich mit einem Anschluss
	 * */
	private void verbinden() {
		try {
			socket = new Socket(get_ziel_inet_addresse(), get_ziel_port());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fehler bim Verbinden mit Anschluss. bitte prüfe die eingegebenen Daten (IP, PORT)",
					"Verbindungsfehler", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	

}
