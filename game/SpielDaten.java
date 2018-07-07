package game;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SpielDaten implements Serializable{
	
	private ArrayList<Spieler> spieler_liste = new ArrayList<Spieler>();
	private int max_spieler;
	private boolean waiting_for_players;
	public SpielDaten() {
		set_waiting_for_players(true);
	}
	
	public void addSpieler(Spieler spieler) {
		spieler_liste.add(spieler);
	}
	
	public void removeSpieler(Spieler spieler) {
		spieler_liste.remove(spieler_liste.indexOf(spieler));
	}
	
	public void removeSpieler(String name) {
		Spieler spieler;
		if ((spieler = getSpieler(name))!=null) {
			spieler_liste.remove(spieler);
		}else {
			return;
		}
	}
	
	public Spieler getSpieler(String name) {
		for(int i = 0; i < spieler_liste.size(); i++) {
			if(spieler_liste.get(i).getSpielerDaten().getName()==name) {
				return spieler_liste.get(i);
			}
		}
		//out.SpielAusgabe.error("Spieler nicht gefunden", "Der Spieler "+name+" ist nicht in der Spielerliste verzeichet!");
		return null;
	}

	public int getMax_spieler() {
		return max_spieler;
	}

	public void setMax_spieler(int max_spieler) {
		this.max_spieler = max_spieler;
	}
	
	public int getSpielerAnzahl() {
		return spieler_liste.size();
	}
	
	public boolean spielerNameFrei(String name) {
		if(getSpieler(name)==null) {
			return true;
		} 
		return false;
	}

	public boolean is_waiting_for_players() {
		return waiting_for_players;
	}

	public void set_waiting_for_players(boolean waiting_for_players) {
		this.waiting_for_players = waiting_for_players;
	}
	
	
}
