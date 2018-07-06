package game;

import karten.Kreatur;

public class SpielerDaten {
	
	private String name;
	private boolean alive;
	private Kreatur kreatur;
	
	public SpielerDaten() {
		alive = true;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected boolean isAlive() {
		return alive;
	}

	protected void setAlive(boolean alive) {
		this.alive = alive;
	}

	protected Kreatur getKreatur() {
		return kreatur;
	}

	protected void setKreatur(Kreatur kreatur) {
		this.kreatur = kreatur;
	}

	
}
