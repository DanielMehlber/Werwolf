package game;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SpielDaten {
	
	private ArrayList<Spieler> spieler_liste = new ArrayList<Spieler>();
	public SpielDaten() {
		
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
			if(spieler_liste.get(i).getName()==name) {
				return spieler_liste.get(i);
			}
		}
		out.SpielAusgabe.error("Spieler nicht gefunden", "Der Spieler "+name+" ist nicht in der Spielerliste verzeichet!");
		return null;
	}

}
