package zeit;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import game.Game;
import game.Moderator;
import net.InetDataFormatter;

/**
 * Steuert die Zeit im Spiel, dessen Geschwindigkeit, und führt die Events aus, die damit verbunden sind
 * */
public class ZeitSystem{
	
	private Game game;
	private int stunde;
	private int minute;
	private double minuteInSekunden;
	private ArrayList<ZeitEvent> events;
	private Callable fertig;
	
	private boolean naechsteRunde;
	
	public ZeitSystem(int stunde, int minute) {
		this.stunde = stunde;
		this.minute = minute;
		events = new ArrayList<ZeitEvent>();
		this.naechsteRunde = true;
	}
	
	public ZeitSystem(int stunde, int minute, double minuteInSekunden) {
		this(stunde, minute);
		this.minuteInSekunden = minuteInSekunden;
	}
	
	public ZeitSystem() {
		this(0,0,1);
	}
	
	public void los() {
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(naechsteRunde) {
					System.out.println("---Zyklus gestartet---");
					//TODO: Überspringt 23 Uhr, aber mei das müssen eh alle warten
					starten(23, 59);
				}
				
				//SpielEnde
				
			}
		});
		
		th.start();
	}
	

	
	private void starten(int end_stunde, int end_min) {
		addieren(1,0);
		while((!equalsUhrzeit(end_stunde, end_min))&&naechsteRunde) {
			try {
				Thread.currentThread().sleep((int)(minuteInSekunden * 1000));
				addieren(0, 1);
				eventsAufrufen();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		System.out.println("---Zyklus beendet---");
		
	}
	
	public void addieren(int stunden, int minuten) {
		this.stunde += stunden;
		this.minute += minuten;
		check();
	}
	
	public void check() {
		if(!(stunde < 25)) {
			stunde = 0;
		}
		
		if(minute > 59) {
			stunde += 1;
			minute = minute - 60;
		}
		
		//System.out.println(stunde + ":"+minute);
		uhrAktualisieren();
		
	}
	
	public void eventsAufrufen() {
		for(int i = 0; i < events.size(); i++) {
			ZeitEvent e = events.get(i);
			if(e.getMinute() == minute && e.getStunde() == stunde) {
				e.run();
				
			}
		}
	}
	
	public void eventEntfernen(String name) {
		ZeitEvent e = getEvent(name);
		if(e == null) {
			return;
		}
		events.remove(e);
	}
	
	public void uhrAktualisieren() {
		InetDataFormatter formatter = getGame().getModerator().getFormatter();
		getGame().getModerator().rufen(formatter.formatieren(4, formatter.ObjectToByteArray(stunde+":"+minute)));
	}
	
	public void addEvent(ZeitEvent e) {
		events.add(e);
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

	public double getMinuteInSekunden() {
		return minuteInSekunden;
	}

	public void setMinuteInSekunden(double minuteInSekunden) {
		this.minuteInSekunden = minuteInSekunden;
	}

	public ArrayList<ZeitEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<ZeitEvent> events) {
		this.events = events;
	}

	public Callable getFertigAktion() {
		return fertig;
	}

	public void setFertigAktion(Callable fertig) {
		this.fertig = fertig;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean equalsUhrzeit(int stunde, int sekunde) {
		return stunde == this.stunde && stunde == this.stunde;
	}
	
	public ZeitEvent getEvent(String name) {
		for(int i = 0; i < events.size(); i++) {
			ZeitEvent e = events.get(i);
			if(e.getName().equals(name)) {
				return e;
			}
		}
		
		System.err.println("ERROR: Event "+name+" konnte nicht gefunden werden");
		
		return null;
	}
	
	public void beenden() {
		naechsteRunde = false;
	}

	
}
