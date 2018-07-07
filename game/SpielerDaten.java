package game;

import java.io.Serializable;

import karten.Kreatur;

public class SpielerDaten implements Serializable{
	
	private String name;
	private boolean amLeben;
	private Kreatur kreatur;
	
	public SpielerDaten() {
		amLeben = true;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected boolean isLebendig() {
		return amLeben;
	}

	protected void setLebendig(boolean alive) {
		this.amLeben = alive;
	}

	protected Kreatur getKreatur() {
		return kreatur;
	}

	protected void setKreatur(Kreatur kreatur) {
		this.kreatur = kreatur;
	}

	
}
