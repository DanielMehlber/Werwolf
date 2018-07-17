package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Beinhaltet Tote
 * */
public class Todesmeldung implements Serializable{
	
	private ArrayList<Toter> tote;
	public Todesmeldung() {
		tote = new ArrayList<Toter>();
	}
	
	public void addToten(Toter t) {
		if(!istSchonInListe(t))
			tote.add(t);
	}
	
	public ArrayList<Toter> getTotenListe(){
		return tote;
	}
	
	public boolean istSchonInListe(Toter t) {
		for(Toter toter : tote) {
			if(toter.getName().equals(t.getName())) {
				return true;
			}
		}
		return false;
	}

}
