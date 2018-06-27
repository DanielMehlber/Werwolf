package test;

import net.Server;

public class ServerCreator {

	public static void main(String[] args) {
		System.out.println("Servertest start");
		Server server = new Server();
		server.set_max_anschluesse(1);
		Thread sth = new Thread(server);
		sth.setName("Server");
		sth.start();
		

	}

}
