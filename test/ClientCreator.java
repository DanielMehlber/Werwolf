/*
package test;

import java.util.Scanner;

import net.Client;
import net.InetDataConverter;

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
		ct.setName("Client");
		ct.start();
		InetDataConverter tool = new InetDataConverter();
		while(true) {
			System.out.println("Zahl:");
			String u = s.nextLine();
			Integer m = Integer.parseInt(u);
			byte[] data = tool.ObjectToByteArray(m);
			client.schreiben(data);
		}
		
	}

}
*/