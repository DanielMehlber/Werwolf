package game;

import java.io.Serializable;

/**
 * 
 * */
public class Toter implements Serializable{

	private String name;
	private Todesursache ursache;
	private String text;
	
	public Toter(String name, Todesursache ursache) {
		this.name = name;
		setUrsache(ursache);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Todesursache getUrsache() {
		return ursache;
	}
	
	public String getText() {
		return text;
	}
	
	public void setUrsache(Todesursache ursache) {
		this.ursache = ursache;
		
		switch(ursache) {
		case HEXE: {
			text = name + " ist dem Todestrank der Hexe zum Opder gefallen. \n"
					+ "Das Leiden war nicht von langer Dauer, "+name+ " fiel schon nach wenigen Sekunden regungslos in den kalten Schnee"
					+ "und wurde dort wenige Stunden später, am frühen Morgen aufgefunden. Von der Hexe jedoch, keine Spur.";
			break;
		}
		case HINRICHTUNG: {
			text = name+ " blickte in den leeren Korb, in den sein Kopf fallen würde. Aus dem Augenwinkel das fallende Beil erblickend, "
					+ "schloss "+name+" seine Augen und öffnete sie erst wieder, als der Henker seinen Kopf, an den Haaren, in die Höhe riss.";
			break;
		}
		case LIEBE: {
			text = name + " erschauderte, als sie den Kadaver ihrer geraubten Liebe eigenhändig beerdigte. Das Leichentuch war zur Seite"
					+ "gerutscht, sodass "+name+" das verunstaltete, leblose Antlitz erblickte. Überkommen und von ekel und der verdorbenheit"
					+ "dieser grausamen Welt, blickte "+name+ " entgegen des Horizonts, der sich weit hinter Düsterwald erstreckte und ging geradeaus."
					+ "Als der Boden immer näher kam, fühlte "+name+ " sich ihrer Liebe so nah!";
			break;
		}
		case WERWOLF: {
			text = name + "konnte sich nicht mehr rechtzeitig unter dem Bett verstecken, als die Werwölfe die Türe einbrachen. Doch was hätte das"
					+ "genützt? Sie hätten "+name+" so oder so gewittert und gefressen. Auch wenn er die Augen der Verräter erkannte, auch wenn "
					+ "er wusste, wer seine Mörder waren, nützt dies euch nichts. Er ist tot und sein Kadaver zerfetzt...";
			break;
		}
		}
	}
	
	

}
