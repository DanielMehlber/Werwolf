package test;

import net.Server;

class TestServer{
	public static void main(String[]args) {
		Server server = new Server();
		server.set_max_anschluesse(1);
		Thread t = new Thread(server);
		t.start();
	}
}
