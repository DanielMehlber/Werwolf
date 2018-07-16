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
					+ "und wurde dort wenige Stunden sp�ter, am fr�hen Morgen aufgefunden. Von der Hexe jedoch, keine Spur.";
			break;
		}
		case HINRICHTUNG: {
			text = name+ " blickte in den leeren Korb, in den sein Kopf fallen w�rde. Aus dem Augenwinkel das fallende Beil erblickend, "
					+ "schloss "+name+" seine Augen und �ffnete sie erst wieder, als der Henker seinen Kopf, an den Haaren, in die H�he riss.";
			break;
		}
		case LIEBE: {
			text = name + " erschauderte, als sie den Kadaver ihrer geraubten Liebe eigenh�ndig beerdigte. Das Leichentuch war zur Seite"
					+ "gerutscht, sodass "+name+" das verunstaltete, leblose Antlitz erblickte. �berkommen und von ekel und der verdorbenheit"
					+ "dieser grausamen Welt, blickte "+name+ " entgegen des Horizonts, der sich weit hinter D�sterwald erstreckte und ging geradeaus."
					+ "Als der Boden immer n�her kam, f�hlte "+name+ " sich ihrer Liebe so nah!";
			break;
		}
		case WERWOLF: {
			text = name + "konnte sich nicht mehr rechtzeitig unter dem Bett verstecken, als die Werw�lfe die T�re einbrachen. Doch was h�tte das"
					+ "gen�tzt? Sie h�tten "+name+" so oder so gewittert und gefressen. Auch wenn er die Augen der Verr�ter erkannte, auch wenn "
					+ "er wusste, wer seine M�rder waren, n�tzt dies euch nichts. Er ist tot und sein Kadaver zerfetzt...";
			break;
		}
		}
	}
	
	public void setKreaturText(Kreatur kreatur) {
		switch(kreatur) {
		case ARMOR: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Bei dem Verurteilten handelte es sich um Amor, dem Gott der Liebe, der versteckt im Dorf unter nem Namen '"+name+"' gelebt hat"
					+ "\nAls b�der Werwolf angeprangert verlie� er w�hrend der Hinrichtung den D�sterwald und verfluchte euch alle.";
			}
			break;}
		case BUERGER: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Ein unschuldiger Mensch. Wie kann man einen einfachen B�rger wegen keiner bewiesenen Untat k�pfen? ";
			}
			break;}
		case HEXE: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Als der Henker den abgetrennten Kopf in der Hand h�llt, und ihn richtung Publikum streckt, spricht der Kopf einen letzten Fluch."
					+ "\nIhr seid nun ohne Schutz und Todestrank auf euch gestellt. "+name+" ist eine m�chtige Hexe gewesen.";
			}
			break;}
		case JAEGER: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Zu schnell wurde der J�ger zum gejagten. Das gefiel ihm nicht, und so t�tete er seine letzte Beute bevor er selbst an der Reihe war.";
			}
			break;}
		case SEHERIN: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Tot hilft euch eine Seherin nichts mehr.";	
			}
			break;}
		case WERWOLF: {
			if(ursache.equals(Todesursache.HINRICHTUNG)) {
				kreaturText = "Als der Kopf in den Korb fiel, renke und streckte sich dieser, als auch der K�rper. Die Leute wichen zur�ck als nun ein \n"
						+ "Werwolf vor ihnen lag. Die Bewohener waren sich einig: Dieser schlecht imitierte Mensch konnte nur ein Werwolf sein.";
			}
			break;}
		}
	}
	
	

}
