package game;

import java.io.Serializable;

public class SpielStatusPaket implements Serializable{

	private SpielStatus status;
	private String beschreibung;
	private int nextStunde;
	private int nextMinute;
	private String nextBeschreibung;
	
	public SpielStatusPaket(SpielStatus status) {
		this.status = status;
	}

	public SpielStatus getStatus() {
		return status;
	}

	public void setStatus(SpielStatus status) {
		this.status = status;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public int getNextStunde() {
		return nextStunde;
	}

	public void setNextStunde(int nextStunde) {
		this.nextStunde = nextStunde;
	}

	public int getNextMinute() {
		return nextMinute;
	}

	public void setNextMinute(int nextMinute) {
		this.nextMinute = nextMinute;
	}

	public String getNextBeschreibung() {
		return nextBeschreibung;
	}

	public void setNextBeschreibung(String nextBeschreibung) {
		this.nextBeschreibung = nextBeschreibung;
	}
	
	

}
