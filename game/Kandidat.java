package game;

import java.io.Serializable;

/**
 * Representiert einen Kandidaten für eine Abstimmung
 * */
public class Kandidat implements Serializable, Comparable<Kandidat>{

	private String spieler;
	private int stimmen;
	public Kandidat(String spieler) {
		this.spieler = spieler;
		stimmen = 1;
		if(spieler == null) {
			System.err.println("Der Spieler darf nicht null sein!");
		}
	}
	
	public void stimme() {
		stimmen += stimmen;
	}
	
	public int getStimmen() {
		return stimmen;
	}
	
	public String getName() {
		return spieler;
	}

	@Override
	public int compareTo(Kandidat o) {
		return o.getStimmen() - this.getStimmen();
	}

}
