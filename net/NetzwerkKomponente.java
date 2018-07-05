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

import javax.swing.JOptionPane;

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
	protected Socket socket;
	/**Ein Grundlegender Output-Stream als Outbox*/
	private OutputStream output;
	/**Ein Grundlegender Input-Stream als Inbox*/
	private InputStream input;
	/**Ein Leser für die Inbox*/
	private InputStreamReader input_reader;
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
			input_reader = new InputStreamReader(input);
			output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			reader = new BufferedReader(input_reader);
			byte_output = new ByteArrayOutputStream();
			byte_input = new ByteArrayInputStream(byte_output.toByteArray());
		} catch (IOException e) {
			out.SpielAusgabe.error("Setupfehler", "Fehler beim erstellen der Kommunikationssysteme!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sucht nach verfügbaren Port
	 * @return port
	 * */
	public int generatePort() {
		while(!available(get_self_port())) {
			set_self_port(new Random().nextInt(0xFFFF)+1);
		}
		System.out.println("Port generiert:"+get_self_port());
		return get_self_port();
	}
	
	/**
	 * Überprüft, ob ein Port auf dem PC verfügbar ist
	 * @return (true=verfügbar/false=besetzt)
	 * @see #generatePort()
	 * @param _port Port zum prüfen
	 * */
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
	 * Greift addressierte Nachrichten vom typ String und byte[] auf 
	 * */
	protected void auffassen() {
		String nachricht = null;
		byte[] data = new byte[64];
		is_listening = true;
		while(is_listening) {
			
			try {
				input.read(data);
				nachricht = reader.readLine();
			} catch (IOException e) {
				out.SpielAusgabe.error("Lesefehler (Internet)", "Fehler beim lesen der Netzwerk-inbox!");
				e.printStackTrace();
			}
			verarbeiten(nachricht);
			verarbeiten(data);
		}
	}
	/**
	 * Definiert, wie mit den Empfangenen Daten (byte[]) umgegangen wird.
	 * @param data Der Byte-Array der Verarbeitet werden soll
	 * @see auffassen
	 * */
	protected abstract void verarbeiten(byte[] data);
	/**
	 * Definiert, wie mit den Empfangenen Daten (String) umgegangen wird.
	 * @param data Der String der Verarbeitet werden soll
	 * @see auffassen
	 * */
	protected abstract void verarbeiten(String data);
	
	/**
	 * Verschickt einen String
	 * @param nachricht Die Nachricht (String) die verschickt werden soll
	 * */
	public void schreiben(String nachricht) {
		writer.println(nachricht);
		writer.flush();
	}
	
	/**
	 * Verschickt Daten in Form eines Byte-Arrays
	 * @param data Die Daten die verschickt werden sollen.
	 * */
	public void schreiben(byte[] data) {
		try {
			output.write(data);
			output.flush();
		} catch (IOException e) {
			out.SpielAusgabe.error("Outboxfehler", "Fehler beim verschicken von byte[]");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Zerstört den Anschluss und versucht den Client auszuloggen
	 * */
	public void destroy() {
		int dest = -1;
		byte[] data = new byte[1];
		data[0] = (byte)dest;
		schreiben(data);
		try {
			//nachricht({-1})
			if(socket!=null) 
				socket.close();
			Thread.currentThread().join();
			
		} catch (IOException e) {
			out.SpielAusgabe.error("Exit Fehler", "Ein Fehler ist beim schließen der Netzwerkkomponente aufgetreten!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Logged out!");
		is_listening = false;
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
	 * @param ziel_ip_addresse Die IP Addresse zum setzen
	 * */
	public void set_ziel_ip_addresse(String ziel_ip_addresse) {
		this.ziel_ip_addresse = ziel_ip_addresse;
		try {
			this.ziel_inet_address = InetAddress.getByName(this.ziel_ip_addresse);
		} catch (UnknownHostException e) {
			out.SpielAusgabe.error("Host unerreichbar", "Die Ziel IP-Addresse "+ziel_ip_addresse+" ist unerrichbar!");
			e.printStackTrace();
		}
	}
	/**
	 * Setzt die InetAddresse und die IP Adresse des Ziels
	 * @param ziel_address Zeil InetAddress
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
