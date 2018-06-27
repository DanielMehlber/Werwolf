package net;

import java.io.IOException;
import java.net.Socket;

import ui.ErrorThrow;

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
	
	private void verbinden() {
		try {
			socket = new Socket(get_ziel_inet_addresse(), get_ziel_port());
		} catch (Exception e) {
			ErrorThrow et = new ErrorThrow("Kann sich nicht mit der gegebenen InetAddress oder Port verbinden\nport="+get_ziel_port()+
					"\nip="+get_ziel_ip_addresse(), e.getMessage());
			et.show();
			et.setVisible(true);
			e.printStackTrace();
		}
		
	}

}
