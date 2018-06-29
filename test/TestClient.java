package test;

import java.util.Scanner;

import net.Client;

class TestClient{
	public static void main(String[] args) {
		Client cl = new Client();
		Scanner s = new Scanner(System.in);
		String ip = s.nextLine();
		cl.set_ziel_ip_addresse(ip);
		String port = s.nextLine();
		cl.set_ziel_port(Integer.parseInt(port));
		Thread t = new Thread(cl);
		t.start();
		System.out.println("Nachricht:");
		String message = s.nextLine();
		cl.nachricht(new byte[0], message);
	}
}