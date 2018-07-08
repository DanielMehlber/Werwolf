/*
package test;

import java.util.Scanner;

import game.SpielDaten;
import net.InetDataFormatter;
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
		while(!server.getServerBereit()) {
			System.out.print("");
		}
		InetDataFormatter tool = new InetDataFormatter();
		while(true) {
			System.out.println("String:");
			String m = s.nextLine();
			SpielDaten daten = new SpielDaten();
			byte[] data = tool.formatieren((byte)1, tool.ObjectToByteArray(daten));
			server.rufen(data);
			
		}
	}

}
*/