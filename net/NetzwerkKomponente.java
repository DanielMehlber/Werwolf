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
	/**Der Socket auf diesem PC, für diese Komponente*/
	private Socket socket;
	/**Ein Grundlegender Output-Stream als Outbox*/
	private OutputStream output;
	/**Ein Grundlegender Input-Stream als Inbox*/
	private InputStream input;
	/**Eine Byte-Inbox*/
	private ByteArrayInputStream byte_input;
	/**Eine Byte-Outbox*/
	private ByteArrayOutputStream byte_output;
	/**Eine Outbox, die alles verschickt außer Bytes*/
	private PrintWriter writer;
	/**Eine Ibox, die alles aufnimmt, außer Bytes*/
	private BufferedReader reader;
	/**Der Thread in dem die Komponente laufen wird*/
	private Thread thread_listen;
	private boolean is_listening;
	private byte[] inbox_nachricht;
	
	public NetzwerkKomponente() {
		
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
		while(!available(self_port)) {
			self_port = new Random().nextInt(0xFFFF)+1;
		}
		return self_port;
	}
	
	public static boolean available(int port) {
		try (Socket ignored = new Socket("localhost", port)) {
	        return false;
	    } catch (IOException ignored) {
	        return true;
	    }
	}
	
	/**
	 * Greift addressierte Nachrichten auf. 
	 * */
	protected void auffassen() {
		while(is_listening && (!socket.isClosed())) {
				
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
			output.write(data, 0, data.length);
		} catch (IOException e) {
			System.err.println("Fehler beim schreiben einer Bytenachricht.");
			e.printStackTrace();
		}
	}

	protected int get_ziel_port() {
		return ziel_port;
	}

	protected void set_ziel_port(int ziel_port) {
		this.ziel_port = ziel_port;
	}

	protected boolean isListening() {
		return is_listening;
	}

	protected void setListening(boolean is_listening) {
		this.is_listening = is_listening;
	}

	protected Socket getSocket() {
		return socket;
	}

	protected OutputStream getOutputStream() {
		return output;
	}

	protected InputStream getInputStream() {
		return input;
	}

	protected PrintWriter getWriter() {
		return writer;
	}

	protected BufferedReader getReader() {
		return reader;
	}

	protected Thread get_thread_listen() {
		return thread_listen;
	}
	
	/**
	 * Setzt die IP Adresse des Ziels und gibt sie an die InetAddress weiter
	 * */
	protected void set_ziel_ip_addresse(String ziel_ip_addresse) {
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
	protected void set_ziel_inet_address(InetAddress ziel_address) {
		this.ziel_inet_address = ziel_address;
		this.ziel_ip_addresse = ziel_address.getHostAddress();
	}

	protected String get_ziel_ip_addresse() {
		return ziel_ip_addresse;
	}

	protected InetAddress get_ziel_inet_addresse() {
		return ziel_inet_address;
	}

	protected ByteArrayInputStream get_byte_input() {
		return byte_input;
	}

	protected ByteArrayOutputStream get_byte_output() {
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
