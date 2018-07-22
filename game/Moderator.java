package game;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.SetOfIntegerSyntax;
import javax.swing.JOptionPane;

import karten.Kreatur;
import net.Anschluss;
import net.InetDataFormatter;
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
	
	public void abstimmungErstellen() {
		game.getSpielDaten().setAbstimmung(new Abstimmung(game.getSpielDaten()));
		game.spielDatenTeilen();
	}
	
	public void abstimmungSchliessen() {
		Abstimmung abstimmung = game.getSpielDaten().getAbstimmung();
		if(abstimmung == null)
			return;
		abstimmung.setOffen(false);
		game.spielDatenTeilen();
	}
	
	public void abstimmungsErgebnisseLoeschen() {
		game.getSpielDaten().setAbstimmung(null);
		game.getSpielDaten().setWerwolfOpfer(null);
		game.getSpielDaten().setVerurteilterSpielerName(null);
		game.spielDatenTeilen();
	}
	
	/**
	 * Leitet die Verarbeitung der Werwolfabstimmung
	 * */
	public void werwolfAbstimmungVerarbeiten() {
		Abstimmung werwolf = game.getSpielDaten().getAbstimmung();
		werwolfOpferSetzen(werwolf);
		game.spielDatenTeilen();
		
	}

	/**
	 * Findet das Werwolfopfer heraus und setzt dieses in den Spieldaten
	 * */
	public void werwolfOpferSetzen(Abstimmung a) {
		Kandidat gewinner = a.getGewinner();
		if(gewinner == null) {
			System.err.println("VOTE: Es wurde kein Werwolf abgestimmt");
			out.SpielAusgabe.info(null, "Dumme Werwölfe", "Keine Sorge, eure Werwölfe sind, wie es scheint, vegetarier, und haben sich letzte Nacht"
					+ " lieber mit dem Gemüse aus eurem Beet vergnügt");
			return;
		}
		game.getSpielDaten().setWerwolfOpfer(gewinner.getName());
	}
	
	/**
	 * Verarbeitet die stattgefundene Abstimmung im Gericht
	 * */
	public void gerichtsAbstimmungVerarbeiten() {
		Abstimmung gericht = game.getSpielDaten().getAbstimmung();
		Kandidat gewinner = gericht.getGewinner();
		if(gewinner == null) {
			System.err.println("FEHLER: Das Gericht kam zu keinem Ergebnis!");
			out.SpielAusgabe.info(null, "Gericht", "Keine Sorge liebe Werwoelfe, das Gericht bringt es nicht auf die Reihe jemanden anzuklagen. Guten Appetit! ");
			return;
		}
		String hinzurichtender = gewinner.getName();
		System.out.println(hinzurichtender+" hat eine Hinrichtung gewonnen!");
		game.getSpielDaten().setVerurteilterSpielerName(hinzurichtender);
		hinrichtungDurchführen();
	}
	
	/**
	 * Erstellt eine Todesmeldung am Nachmittag und schickt diese zu den Spielern
	 * 
	 * */
	public void hinrichtungDurchführen() {
		//Verurteilten zur Meldunghinzufügen
		Todesmeldung meldung = new Todesmeldung();
		String name = game.getSpielDaten().getVerurteilterSpielerName();
		if(name == null) {
			return;
		}
		Spieler verurteilter = game.getSpielDaten().getSpieler(name);
		meldung.addToten(new Toter(name, Todesursache.HINRICHTUNG, verurteilter.getSpielerDaten().getKreatur()));
		
		moeglichesJaegerZielHinzufuegen(meldung, verurteilter);
		moeglichesLiebesOpferHinzufuegen(meldung);
		
		//Tote Spieler als Tot setzen
		for(Toter toter : meldung.getTotenListe()) {
			game.getSpielDaten().getSpieler(toter.getName()).getSpielerDaten().setLebendig(false);
		}
		
		spielDatenTeilen();
		todesnachrichtSenden(meldung);
		//getSpielDaten().setVerurteilterSpielerName(null);
	}
	
	/**
	 * Prüft ob es sich beim Spieler um einen Jäger handelt, und fügt das Opfer hinzu
	 * */
	public void moeglichesJaegerZielHinzufuegen(Todesmeldung meldung, Spieler s) {
		if(s.getSpielerDaten().getKreatur().equals(Kreatur.JAEGER)) {
			String zielName = game.getSpielDaten().getJaegerZiel();
			Spieler ziel = game.getSpielDaten().getSpieler(zielName);
			meldung.addToten(new Toter(zielName, Todesursache.JAEGER, ziel.getSpielerDaten().getKreatur()));
		}
	}
	
	/**
	 * Prüft ob die Toten teil eines Liebespaares sind, wenn ja, dann wird die Liebe auch noch hinzugefügt
	 * */
	public void moeglichesLiebesOpferHinzufuegen(Todesmeldung meldung) {
		ArrayList<Spieler> liebesopfer = new ArrayList<Spieler>();
		Todesmeldung meldung_copy = meldung.cloneMeldung();
		for(Toter t : meldung_copy.getTotenListe()) {
			Spieler s = game.getSpielDaten().getSpieler(t.getName());
			Spieler liebe;
			if((liebe=game.getSpielDaten().getLiebe(s.getSpielerDaten().getName()))!=null) {
				liebesopfer.add(liebe);
			}
		}
		for(Spieler s : liebesopfer) {
			meldung.addToten(new Toter(s.getSpielerDaten().getName(), Todesursache.LIEBE, s.getSpielerDaten().getKreatur()));
			System.out.println("DEATH: "+s.getSpielerDaten().getName()+" ist ein Opfer der Liebe");
		}
	}
	
	/**
	 * Verpackt die Abstimmungen und Aktionen von WW, Hexe, Amor & co in einer Todesmeldung
	 * */
	public void naechtlicheGeschehnisseVerarbeiten() {
		//Vorbereitungen
		liebesPaarPruefen();
		
		//Kümmern wir uns um die Toten
		Todesmeldung meldung = new Todesmeldung();
		ArrayList<Spieler> tote = game.getSpielDaten().getToteSpieler();
		String opfer_name = null;
		Abstimmung a = game.getSpielDaten().getAbstimmung();
		
		Kandidat opfer = a.getGewinner();
		if(opfer != null)
			opfer_name = opfer.getName();
		
		if(opfer_name != null) {
			//Durch Hexe gerettet ? 
			if(game.getSpielDaten().getSpieler(opfer_name).getSpielerDaten().isLebendig()) {
				showPopUp(opfer_name+" wurde von der Hexe gerettet!");
				System.out.println("DEATH: "+opfer_name+" wurde von der Hexe gerettet");
			}else {
			//Oder doch den Werwölfen zum fraß gefallen
				meldung.addToten(new Toter(opfer_name, Todesursache.WERWOLF, game.getSpielDaten().getSpieler(opfer_name).getSpielerDaten().getKreatur()));
				game.getSpielDaten().setWerwolfOpfer(opfer_name);
				System.out.println("DEATH: "+opfer_name+" ist den Werwoelfen zum Fraß gefallen");
			}
		}else {
			//TODO: remove
			System.err.println("Das Werwolfopfer ist null");
		}
		
		
		
		//Tod durch Hexe
		for(Spieler s : tote) {
			if(!s.getSpielerDaten().getName().equals(opfer_name)) {
				meldung.addToten(new Toter(s.getSpielerDaten().getName(), Todesursache.HEXE, s.getSpielerDaten().getKreatur()));
				System.out.println("DEATH: "+s.getSpielerDaten().getName()+" fiel der Hexe zum Opfer");
			}
			
		}
		
		moeglichesLiebesOpferHinzufuegen(meldung);
		
		todesnachrichtSenden(meldung);
		
		spielDatenTeilen();
		spielStandUeberpruefen();	
	
	}
	
	/**
	 * Prüft, ob Amors Auswahl gueltig war (2 Personen, die nicht die selben sind/ist) 
	 * */
	public void liebesPaarPruefen() {
		ArrayList<Spieler> liebespaar = game.getSpielDaten().getLiebespaar();
		if(liebespaar.size() == 2 && !(liebespaar.get(0).equals(liebespaar.get(1)))) {
			eventÜberspringen("amor");
		}else {
			liebespaar.clear();
		}
	}
	
	/**
	 * Erstetzt das Runnable eines bestimmten Events mit zeitraffer
	 * */
	public void eventÜberspringen(String name) {
		ZeitEvent e = getZeitSystem().getEvent(name);
		e.setAktion(()->zeitRaffer());
	}
	
	public void zeitRaffer() {	
		getZeitSystem().setMinuteInSekunden(0.01);
	}
	
	/**
	 * Prüft ob das Spiel vorbei ist
	 * */
	public void spielStandUeberpruefen() {
		if(game.getSpielDaten().getSpielerListe().size() == 2) {
			if(game.getSpielDaten().isliebesPaar(game.getSpielDaten().getSpielerListe().get(0).getSpielerDaten().getName(),game.getSpielDaten().getSpielerListe().get(1).getSpielerDaten().getName())) {
				showPopUp("Das Spiel ist zu Ende, und das Liebespaar hat gewonnen...");
				beenden();
			}
		}
		
		if(sindNurWesen()) {
			showPopUp("Die Dorfbewohner haben gewonnen!");
			beenden();
		}
		
		if(sindNurWerwoelfe()) {
			showPopUp("Die Werwölfe haben gewonnen!");
			beenden();
		}
		
	}
	
	/**
	 * Prüft ob unter den lebenden nur noch Werwölfe sind
	 * */
	private boolean sindNurWerwoelfe() {
		boolean b = true;
		for(Spieler s : game.getSpielDaten().getSpielerListe()) {
			if(!s.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				b = false;
			}
		}
		return b;
	}
	
	/**
	 * Prüft ob es keine Werwölfe mehr gibt
	 * */
	private boolean sindNurWesen() {
		boolean b = true;
		for(Spieler s : game.getSpielDaten().getSpielerListe()) {
			if(s.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
				b = false;
			}
		}
		return b;
	}
	
	/**
	 * Beendet das Spiel und schliesst alle Fenster in einer Minute
	 * */
	public void beenden() {
		getZeitSystem().beenden();
		ZeitSystem ende = new ZeitSystem(0,0);
		Runnable schliessen = ()->exitAll();
		ende.addEvent(new ZeitEvent(schliessen, 1, 0));
		ende.setMinuteInSekunden(1);
		ende.los();
	}
	
	public void showPopUp(String mitteilung) {
		schreiben(formatter.formatieren(7, formatter.ObjectToByteArray(mitteilung)));
		out.SpielAusgabe.info(null, "Mitteilung", mitteilung);
	}
	
	public void exitAll() {
		schreiben(new byte[] {-3});
	}
	
	public void rauswerfen(String name) {
		System.out.println(name + " wird entfernt...");
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				Anschluss a = getAnschlussByName(name);
				a.schreiben(new byte[] {-3});
				a.destroy();
				System.out.println("Anschluss aufgehoben");
				getAnschlussListe().remove(a);
				getGame().getSpielDaten().removeSpieler(name);
				spielDatenTeilen();
				rufen(formatter.formatieren(8, formatter.ObjectToByteArray(name)));
				System.out.println(name+" wurde entfernt");
			}
		};
		Thread th = new Thread(r);
		th.start();
	}
	
	public Anschluss getAnschlussByName(String name) {
		for(Anschluss a : getAnschlussListe()) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	
	public void pause() {
		zeitSystem.setPause(true);
	}
	
	public void fortsetzen() {
		zeitSystem.setPause(false);
	}
	
	public void serverBeenden() {
		rufen(new byte[] {-3});
		for(Anschluss a : getAnschlussListe()) {
			a.destroy();
		}
		destroy();
		try {
			getServerSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
}
