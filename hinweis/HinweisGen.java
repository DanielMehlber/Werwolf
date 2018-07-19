package hinweis;

import java.util.Random;

import game.Game;
import game.Spieler;
import karten.Kreatur;

public class HinweisGen {

	private Game game;
	public HinweisGen(Game game) {
		this.game = game;
	}
	
	public String hinweis(String name, String zeuge) {
		Hinweis h;
		Spieler spieler = game.getSpielDaten().getSpieler(name);
		Kreatur k = spieler.getSpielerDaten().getKreatur();
		Random ran = new Random();
		int i = ran.nextInt(10) + 1;
		
		if(i < 9) {
			//Wahrheit zu 80-90 %
			int q = ran.nextInt(11)+1;
			if(q < 8) {
				//Für eine spezifische Kreatur zu 20 - 30%
				h = new Hinweis(name, zeuge, k, true);
			}else {
				//Gegen irgenteine andere Kreatur 70-80%
				h = new Hinweis(name, zeuge, eineAndere(k), false);
			}
		}else {
			int q = ran.nextInt(11)+1;
			if(q < 8) {
				//Für eine spezifische Kreatur zu 20 - 30%
				h = new Hinweis(name, zeuge, k, false);
			}else {
				//Gegen irgenteine andere Kreatur 70-80%
				h = new Hinweis(name, zeuge, eineAndere(k), true);
			}
		}
		
		return h.getHinweis();
	}
	
	public Kreatur eineAndere(Kreatur k) {
		Kreatur kreatur = k;
		int i = new Random().nextInt(7)+1;
		while(kreatur != k) {
			switch(i) {
			case 1: {kreatur = Kreatur.ARMOR;break;}
			case 2: {kreatur = Kreatur.BUERGER;break;}
			case 3: {kreatur = Kreatur.HEXE;break;}
			case 4: {kreatur = Kreatur.JAEGER;break;}
			case 5: {kreatur = Kreatur.SEHERIN;break;}
			case 6: {kreatur = Kreatur.WERWOLF;break;}
			}
		}
		return kreatur;
	}

}
