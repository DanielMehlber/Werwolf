package game;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Speichert allerlei Informationen über das Spiel, und wird regelmäßig geteilt
 * */
public class SpielDaten implements Serializable{
	
	private ArrayList<Spieler> spieler_liste = new ArrayList<Spieler>();
	private int max_spieler;
	private boolean warten_auf_spieler;
	
	public SpielDaten() {
		set_warten_auf_spieler(true);
	}
	
	/**
	 * Einen Spieler zur Spielerliste hinzufügen
	 * @param spieler Den Spieler den man hinzufügen möchte
	 * */
	public void addSpieler(Spieler spieler) {
		spieler_liste.add(spieler);
		if(getSpielerAnzahl() == get_max_spieler()) {
			set_warten_auf_spieler(false);
		}
	}
	
	/**
	 * Einen Spieler mithilfe eines Referenzobjektes entfernen
	 * @param spieler Die existierende Referenz zum Spieler Objekt
	 * */
	public void removeSpieler(Spieler spieler) {
		spieler_liste.remove(spieler_liste.indexOf(spieler));
	}
	
	/**
	 * Einen Spieler anhand des Namens entfernen
	 * @param name Der Name des zu entfernenden Spielers
	 * */
	public void removeSpieler(String name) {
		Spieler spieler;
		if ((spieler = getSpieler(name))!=null) {
			spieler_liste.remove(spieler);
		}else {
			return;
		}
	}
	
	/**
	 * Einen Spieler anhand seines Namens zurückgeben
	 * @return Den gesuchten Spieler
	 * */
	public Spieler getSpieler(String name) {
		for(int i = 0; i < spieler_liste.size(); i++) {
			String n = spieler_liste.get(i).getSpielerDaten().getName();
			if(n.equals(name)) {
				return spieler_liste.get(i);
			}
		}
		System.out.println("---nicht gefunden---");
		return null;
	}
	
	/**
	 * Prüft ob eine Spielername noch frei ist, um dopplungen im Primary Key zu vermeiden
	 * @return ist der Name frei?
	 * */
	public boolean spielerNameFrei(String name) {
		if(name==null) {
			return false;
		}
		if(name.isEmpty()||name.length() < 2||name.length()>8) {
			return false;
		}
		if(getSpieler(name)==null) {
			return true;
		} 
		return false;
	}

	public int get_max_spieler() {
		return max_spieler;
	}

	public void set_max_spieler(int max_spieler) {
		this.max_spieler = max_spieler;
	}
	
	public int getSpielerAnzahl() {
		return spieler_liste.size();
	}
	

	public boolean get_warten_auf_spieler() {
		return warten_auf_spieler;
	}

	public void set_warten_auf_spieler(boolean waiting_for_players) {
		this.warten_auf_spieler = waiting_for_players;
	}
	
	public ArrayList<Spieler> getSpielerListe(){
		return this.spieler_liste;
	}
	
	
}
