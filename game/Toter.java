package game;

import java.io.Serializable;

import com.sun.org.apache.xpath.internal.compiler.Keywords;

import karten.Kreatur;

/**
 * 
 * */
public class Toter implements Serializable{

	private String name;
	private Todesursache ursache;
	private String text;
	private String kreaturText;
	
	public Toter(String name, Todesursache ursache, Kreatur kreatur) {
		this.name = name;
		setUrsache(ursache);
		setKreaturText(kreatur);
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
	
	public void setKreaturText(Kreatur kreatur) {
		switch(kreatur) {
		case ARMOR: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Bei dem Verurteilten handelte es sich um Amor, dem Gott der Liebe, der versteckt im Dorf unter nem Namen '"+name+"' gelebt hat"
					+ "\nAls böder Werwolf angeprangert verließ er während der Hinrichtung den Düsterwald und verfluchte euch alle.";
			}
			break;}
		case BUERGER: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Ein unschuldiger Mensch. Wie kann man einen einfachen Bürger wegen keiner bewiesenen Untat köpfen? ";
			}
			break;}
		case HEXE: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Als der Henker den abgetrennten Kopf in der Hand hällt, und ihn richtung Publikum streckt, spricht der Kopf einen letzten Fluch."
					+ "\nIhr seid nun ohne Schutz und Todestrank auf euch gestellt. "+name+" ist eine mächtige Hexe gewesen.";
			}
			break;}
		case JAEGER: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Zu schnell wurde der Jäger zum gejagten. Das gefiel ihm nicht, und so tötete er seine letzte Beute bevor er selbst an der Reihe war.";
			}
			break;}
		case SEHERIN: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Tot hilft euch eine Seherin nichts mehr.";	
			}
			break;}
		case WERWOLF: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Als der Kopf in den Korb fiel, renke und streckte sich dieser, als auch der Körper. Die Leute wichen zurück als nun ein \n"
						+ "Werwolf vor ihnen lag. Die Bewohener waren sich einig: Dieser schlecht imitierte Mensch konnte nur ein Werwolf sein.";
			}
			break;}
		}
	}
	
	

}
