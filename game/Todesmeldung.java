package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Beinhaltet Tote
 * */
public class Todesmeldung implements Serializable{
	
	private ArrayList<Toter> tote;
	public Todesmeldung() {
		// TODO Auto-generated constructor stub
	}
	
	public void addToten(Toter t) {
		tote.add(t);
	}
	
	public ArrayList<Toter> getTotenListe(){
		return tote;
	}

}
