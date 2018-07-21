package game;

import javax.print.attribute.SetOfIntegerSyntax;
import javax.swing.JOptionPane;

import net.Server;
import zeit.ZeitEvent;
import zeit.ZeitSystem;

/**
 * Leitet und organisiert das Spiel
 * */
public class Moderator extends Server implements Runnable{

	private Game game;
	private boolean moderiert;
	private ZeitSystem zeitSystem;
	
	public Moderator(Game game) {
		super(game);
		this.game = game;
		this.moderiert = true;
		this.zeitSystem = new ZeitSystem(16, 30, 1);
		this.zeitSystem.setGame(getGame());
		eventsSetzen();
	}
	
	public void eventsSetzen() {
		Runnable r_vorbereitung = ()->vorbereitung();
		zeitSystem.addEvent(new ZeitEvent("vorbereitung", r_vorbereitung , 17, 45));
		Runnable r_nacht = ()->nacht();
		zeitSystem.addEvent(new ZeitEvent("nacht_einleiten", r_nacht, 18, 0));
		Runnable r_ww = ()->werwolf();
		zeitSystem.addEvent(new ZeitEvent("werwolf", r_ww, 18, 30));
		Runnable r_amor = ()->amor();
		zeitSystem.addEvent(new ZeitEvent("amor", r_amor, 20, 0));
		Runnable r_hexe = ()->hexe();
		zeitSystem.addEvent(new ZeitEvent("hexe", r_hexe, 21, 0));
		Runnable r_seherin = ()->seherin();
		zeitSystem.addEvent(new ZeitEvent("seherin", r_seherin, 22, 0));
		Runnable r_schlaf = ()->schlafen();
		zeitSystem.addEvent(new ZeitEvent("schlafen", r_schlaf, 23, 0));
		Runnable r_morgen = ()->morgen();
		zeitSystem.addEvent(new ZeitEvent("morgen", r_morgen, 7,0));
		Runnable r_gericht = ()->gericht();
		zeitSystem.addEvent(new ZeitEvent("gericht", r_gericht, 8, 0));
		Runnable r_abstimmung = ()->abstimmung();
		zeitSystem.addEvent(new ZeitEvent("abstimmen", r_abstimmung, 12, 0));
		Runnable r_hinrichtung = ()->hinrichten();
		zeitSystem.addEvent(new ZeitEvent("hinrichtung", r_hinrichtung, 13, 0));
	}
	
	public Game getGame() {
		return game;
	}
	
	public void uebernehmen() {
		moderiert = true;
		game.getSpielDaten().kartenZiehen();
		super.spielDatenTeilen();
		
	}
	
	public boolean getModeriert() {
		return moderiert;
	}
	
	public void starten() {
		zeitSystem.los();
	}
	
	private void vorbereitung() {
		System.err.println("SERVER: Vorbereitungzeit");
		updateSpielStatus(SpielStatus.VORBEREITUNG, "Viel Spaß beim Spielen!","beginnt die erste Nacht", zeitSystem.getEvent("nacht_einleiten").getStunde(), zeitSystem.getEvent("nacht_einleiten").getMinute());
		
	}
	
	private void nacht() {
		System.err.println("SERVER: Nacht eingeleitet");
		getGame().getGameWindow().getHauptSpielPanel().setSchlafen(true);
		updateSpielStatus(SpielStatus.NACHT, "Die Nacht legt sich über das Tal...",
				"erwachen die Werwölfe", zeitSystem.getEvent("werwolf").getStunde(), zeitSystem.getEvent("werwolf").getMinute());
	}
	
	public void werwolf() {
		System.err.println("SERVER: Werwölfe sind an der Reihe");
		updateSpielStatus(SpielStatus.WERWOLF, "Die Werwölfe ziehen um die Häuser, sei auf der Hut...",
				"erwacht Amor", zeitSystem.getEvent("amor").getStunde(), zeitSystem.getEvent("amor").getMinute());
	}
	
	public void amor() {
		System.err.println("SERVER: Amor ist an der Reihe");
		updateSpielStatus(SpielStatus.AMOR,"Amor spannt seinen Bogen..." ,
				"zieht die Hexe um die Häuser", zeitSystem.getEvent("hexe").getStunde(), zeitSystem.getEvent("hexe").getMinute());
	}
	
	public void hexe() {
		System.err.println("SERVER: Die Hexe ist nun an der Reihe");
		updateSpielStatus(SpielStatus.HEXE,"Die Hexe zieht wieder um die Häuser und jongliert mit Leben und Tot...", 
				"erblickt die Seherin das bisher verborgene", zeitSystem.getEvent("seherin").getStunde(), zeitSystem.getEvent("seherin").getMinute());
	}
	
	public void seherin() {
		System.err.println("SERVER: Die Seherin ist an der Reihe");
		updateSpielStatus(SpielStatus.SEHERIN,"Die Seherin blickt nun in die Aura eines Bewohners...",
				"legen sich alle wieder schlafen", zeitSystem.getEvent("schlafen").getStunde(), zeitSystem.getEvent("schlafen").getMinute());
	}
	
	public void schlafen() {
		System.err.println("SERVER: Alle schlfen bis zum Morgen");
		updateSpielStatus(SpielStatus.SCHLAFEN,"Alle Bewohner schlafen wieder...",
				"geht die Sonne auf", zeitSystem.getEvent("morgen").getStunde(), zeitSystem.getEvent("morgen").getMinute());
	}
	
	public void morgen() {
		System.err.println("SERVER: Der Morgen graut");
		updateSpielStatus(SpielStatus.MORGEN,"Der Morgen graut und alle wachen auf...",
				"tagt das Gericht", zeitSystem.getEvent("gericht").getStunde(), zeitSystem.getEvent("gericht").getMinute());
	}
	
	public void gericht() {
		System.err.println("SERVER: Das Gericht hat sich zusammengefunden!");
		updateSpielStatus(SpielStatus.GERICHT,"Aufgrund der Morde der letzten Nacht, hat sich das Dorf zum Gericht zusammengefunden..."
				, "wird abgestimmt...", zeitSystem.getEvent("abstimmen").getStunde(), zeitSystem.getEvent("abstimmen").getMinute());
	}
	
	public void abstimmung() {
		System.err.println("SERVER: Die Abstimmung beginnt nun!");
		updateSpielStatus(SpielStatus.ABSTIMMUNG,"Die Bewohner von Düsterwald stimmen nun ab."
				, "wir die Hinrichtung vollzogen", zeitSystem.getEvent("hinrichtung").getStunde(), zeitSystem.getEvent("hinrichtung").getMinute());
	}
	
	public void hinrichten() {
		System.err.println("SERVER: Die hinrichtung wird vollzogen");
		updateSpielStatus(SpielStatus.HINRICHTUNG_NACHMITTAG,"Die Kirchenglocken lassen die Hinrichtung beginnen...",
				"legt sich die Dunkelheit über das Land", zeitSystem.getEvent("nacht_einleiten").getStunde(), zeitSystem.getEvent("nacht_einleiten").getMinute());
	}
	
	public void updateSpielStatus(SpielStatus s, String beschreibung, String nextBeschreibung, int nextStunde, int nextMinute) {
		SpielStatusPaket paket = new SpielStatusPaket(s);
		paket.setBeschreibung(beschreibung);
		paket.setNextBeschreibung(nextBeschreibung);
		paket.setNextStunde(nextStunde);
		paket.setNextMinute(nextMinute);
		spielStatusTeilen(paket);
	}
	
	public void spielStatusTeilen(SpielStatusPaket paket) {
		rufen(formatter.formatieren(5, formatter.ObjectToByteArray(paket)));
		System.out.println("SERVER: Spiel Status geteilt....");
	}
	
	public ZeitSystem getZeitSystem() {
		return zeitSystem;
	}
	
	
	public void todesnachrichtSenden(Todesmeldung meldung) {
		rufen(formatter.formatieren(6, formatter.ObjectToByteArray(meldung)));
	}
	
	
	
	
	
	

}
