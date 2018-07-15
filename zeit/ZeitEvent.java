package zeit;

import java.util.concurrent.Callable;

/**
 * Ein Events, das an einem bestimmten Zeitpunkt von ZeitSystem ausgeführt wird
 * */
public class ZeitEvent {
	
	private Runnable aktion;
	private int stunde;
	private int minute;
	private String name;
	
	
	public ZeitEvent(Runnable aktion) {
		this.aktion = aktion;
		this.name = "unbenanntes_event";
	}
	
	public ZeitEvent(Runnable aktion, int stunde, int minute) {
		this(aktion);
		this.stunde = stunde;
		this.minute = minute;
	}
	
	public ZeitEvent(String name, Runnable aktion, int stunde, int minute) {
		this(aktion, stunde, minute);
		this.name = name;
	}

	public Runnable getAktion() {
		return aktion;
	}

	public void setAktion(Runnable aktion) {
		this.aktion = aktion;
	}

	public int getStunde() {
		return stunde;
	}

	public void setStunde(int stunde) {
		this.stunde = stunde;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void run() {
		try {
			aktion.run();
		} catch (Exception e) {
			System.err.println("Das ZeitEvent "+name+" kann nicht ausgeführt werden!");
			e.printStackTrace();
		}
	}

}
