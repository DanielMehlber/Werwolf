package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;



public class Abstimmung implements Serializable{
	
	private SpielDaten spielDaten;
	private ArrayList<Kandidat> kandidaten;
	private ArrayList<String> bereits_absgestimmt;
	private boolean offen;
	
	public Abstimmung(SpielDaten daten) {
		spielDaten = daten;
		offen = true;
		kandidaten = new ArrayList<Kandidat>();
		bereits_absgestimmt = new ArrayList<String>();
	}

	public void stimme(String absender, String s) {
		if(!offen) {
			out.SpielAusgabe.error(null, "Abstimmung geschlossen", "Die Abstimmung ist geschlossen");
			return;
		}
		
		for(int y = 0; y < bereits_absgestimmt.size(); y++) {
			if(bereits_absgestimmt.get(y).equals(absender)) {
				out.SpielAusgabe.error(null, "Nicht schummeln", "Die hast bereits abgestimmt. Deine Wahl ist getroffen und du kannst sie nicht mehr ändern!");
				return;
			}
		}
		
		bereits_absgestimmt.add(absender);
		
		if(spielDaten.getSpieler(s)==null) {
			out.SpielAusgabe.error(null, "Nicht gefunden", "Der Spieler "+s+" existiert nicht!");
			return;
		}
		
		for(int i = 0; i < kandidaten.size(); i++) {
			Kandidat k = kandidaten.get(i);
			if(k.getName().equals(s)) {
				k.stimme();
				return;
			}
		}
		kandidaten.add(new Kandidat(s));
	}
	
	public SpielDaten getSpielDaten() {
		return spielDaten;
	}

	public boolean isOffen() {
		return offen;
	}

	public void setOffen(boolean offen) {
		this.offen = offen;
	}
	
	public Kandidat getGewinner() {
		if(kandidaten.size() == 0) {
			return null;
		}
		java.util.Collections.sort(kandidaten);
		return kandidaten.get(0);
	}
	
	public int getWaehlerAnzahl() {
		return bereits_absgestimmt.size();
	}
	
}
