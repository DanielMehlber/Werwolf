package game;

import java.io.Serializable;

import karten.Kreatur;

public class SpielerDaten implements Serializable{
	
	private String name;
	private Boolean amLeben;
	private Kreatur kreatur;
	
	public SpielerDaten(String name) {
		amLeben = true;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLebendig() {
		return amLeben;
	}

	public void setLebendig(boolean alive) {
		this.amLeben = alive;
	}

	public Kreatur getKreatur() {
		return kreatur;
	}

	public void setKreatur(Kreatur kreatur) {
		this.kreatur = kreatur;
	}

	
}
