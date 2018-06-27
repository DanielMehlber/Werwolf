package test;

import java.util.Scanner;

import net.Client;

public class ClientCreator {

	public static void main(String[] args) {
		Client client = new Client();
		Scanner s = new Scanner(System.in);
		System.out.println("IP:");
		String ip = s.nextLine();
		System.out.println("PORT:");
		String str_port = s.nextLine();
		int port = Integer.parseInt(str_port);
		client.set_ziel_ip_addresse(ip);
		client.set_ziel_port(port);
		Thread ct = new Thread(client);
		ct.start();

	}

}
