package net;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Fasst die Kernelemente jedes Sockets in einer Oberklasse zusammen
 * @author Daniel Mehlber
 * 
 * */
public abstract class NetzwerkKomponente {
	
	/**Die Ziel IP Addresse im String Format*/
	private String ziel_ip_addresse;
	/**Die Ziel InetAddress*/
	private InetAddress ziel_inet_address;
	/**Der Port, auf dem "auf der anderen Seite" der Server gespeichert ist*/
	private int ziel_port;
	/**Der Port der Komponente*/
	private int self_port;
	/**Der Socket auf diesem PC, f�r diese Komponente*/
	protected Socket socket;
	/**Ein Grundlegender Output-Stream als Outbox*/
	private OutputStream output;
	/**Ein Grundlegender Input-Stream als Inbox*/
	private InputStream input;
	/**Eine Byte-Inbox*/
	private ByteArrayInputStream byte_input;
	/**Eine Byte-Outbox*/
	private ByteArrayOutputStream byte_output;
	/**Eine Outbox, die alles verschickt au�er Bytes*/
	private PrintWriter writer;
	/**Eine Ibox, die alles aufnimmt, au�er Bytes*/
	private BufferedReader reader;
	/**Der Thread in dem die Komponente laufen wird*/
	private Thread thread_listen;
	private boolean is_listening;
	private byte[] inbox_nachricht;
	
	public NetzwerkKomponente() {
		is_listening = true;
	}
	
	/**
	 * Erstellt die Kommunikations-Systeme der Komponente (Input & Output von Text, Int und Bytes)
	 * */
	protected void kommunikationBereitstellen() {
		if(socket == null) {
			System.err.println("Der Socket existiert noch nicht!");
			return;
		}
		
		try {
			input = socket.getInputStream();
			output = socket.getOutputStream();
			writer = new PrintWriter(output);
			reader = new BufferedReader(new InputStreamReader(input));
			byte_output = new ByteArrayOutputStream();
			byte_input = new ByteArrayInputStream(byte_output.toByteArray());
		} catch (IOException e) {
			System.err.println("Fehler beim erstellen der Kommunikations-Systeme");
			e.printStackTrace();
		}
		
	}
	
	public int generatePort() {
		while(!available(get_self_port())) {
			set_self_port(new Random().nextInt(0xFFFF)+1);
		}
		System.out.println("Port generiert:"+get_self_port());
		return get_self_port();
	}
	
	public static boolean available(int _port) {
		if(_port <= 0) {
			return false;
		}
		
		try (Socket ignored = new Socket("localhost", _port)) {
	        return false;
	    } catch (IOException ignored) {
	        return true;
	    }
	}
	
	/**
	 * Greift addressierte Nachrichten auf. 
	 * */
	protected void auffassen() {
		byte[] in = new byte[64];
		while(is_listening && (!socket.isClosed())) {
			if(byte_input.available()!=0) {
				System.out.println(true);
			}
			
		}
	}
	/**
	 * Definiert, wie mit den Empfangenen Daten umgegangen wird.
	 * @param data Der Byte-Array der Verarbeitet werden soll
	 * @see auffassen
	 * */
	protected abstract void verarbeiten(byte[] data);
	/**
	 * Schreibt eine Nachricht in Form eines Byte-Arrays an die definierte ip-addresse und port
	 * @param data Der Byte-Array der gesendet werden soll.
	 * */
	public void schreiben(byte[] data) {

		try {
			byte_output.write(data, 0, data.length);
			byte_output.flush();
			
		} catch (IOException e) {
			System.err.println("Fehler beim schreiben einer Bytenachricht.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Zerst�rt den Anschluss und versucht den Client auszuloggen
	 * */
	public void destroy() {
		try {
			//nachricht({-1})
			socket.close();
			
		} catch (IOException e) {
			System.err.println("Fehler beim Schlie�en des Anschlusses");
			e.printStackTrace();
		}
	}
	
	protected int get_ziel_port() {
		return ziel_port;
	}

	public void set_ziel_port(int ziel_port) {
		this.ziel_port = ziel_port;
	}

	protected boolean isListening() {
		return is_listening;
	}

	protected void setListening(boolean is_listening) {
		this.is_listening = is_listening;
	}

	public Socket getSocket() {
		return socket;
	}

	public OutputStream getOutputStream() {
		return output;
	}

	public InputStream getInputStream() {
		return input;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	protected Thread get_thread_listen() {
		return thread_listen;
	}
	
	/**
	 * Setzt die IP Adresse des Ziels und gibt sie an die InetAddress weiter
	 * */
	public void set_ziel_ip_addresse(String ziel_ip_addresse) {
		this.ziel_ip_addresse = ziel_ip_addresse;
		try {
			this.ziel_inet_address = ziel_inet_address.getByName(this.ziel_ip_addresse);
		} catch (UnknownHostException e) {
			System.err.println("Fehler beim Setzen der InetAddress aus '"+this.ziel_ip_addresse+"' !");
			e.printStackTrace();
		}
	}
	/**
	 * Setzt die InetAddresse und die IP Adresse des Ziels
	 * */
	public void set_ziel_inet_address(InetAddress ziel_address) {
		this.ziel_inet_address = ziel_address;
		this.ziel_ip_addresse = ziel_address.getHostAddress();
	}

	public String get_ziel_ip_addresse() {
		return ziel_ip_addresse;
	}

	public InetAddress get_ziel_inet_addresse() {
		return ziel_inet_address;
	}

	public ByteArrayInputStream get_byte_input() {
		return byte_input;
	}

	public ByteArrayOutputStream get_byte_output() {
		return byte_output;
	}

	public int get_self_port() {
		return self_port;
	}

	public void set_self_port(int self_port) {
		this.self_port = self_port;
	}
	
	


	
	
	
	
	
	//Getter und Setter
	

}
