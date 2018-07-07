package test;

import java.util.Scanner;

import game.SpielDaten;
import net.InetDataConverter;
import net.Server;

public class ServerCreator {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Servertest start");
		Server server = new Server();
		server.set_max_anschluesse(1);
		Thread sth = new Thread(server);
		sth.setName("Server");
		sth.start();
		while(!server.getServerCreated()) {
			System.out.print("");
		}
		InetDataConverter tool = new InetDataConverter();
		while(true) {
			System.out.println("String:");
			String m = s.nextLine();
			SpielDaten daten = new SpielDaten();
			byte[] data = tool.format((byte)1, tool.ObjectToByteArray(daten));
			server.rufen(data);
			
		}
	}

}
