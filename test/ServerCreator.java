package test;

import java.util.Scanner;

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
		while(true) {
			System.out.println("BYTE:");
			String m = s.nextLine();
			byte[] data = new byte[2];
			data[0] = 0;
			data[1] = (byte)Integer.parseInt(m);
			server.rufen(data);
			//server.rufen("");
			
		}
	}

}
