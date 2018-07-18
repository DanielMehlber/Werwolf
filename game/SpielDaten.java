package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import karten.Buerger;
import karten.Kreatur;

/**
 * Speichert allerlei Informationen über das Spiel, und wird regelmäßig geteilt
 * */
public class SpielDaten implements Serializable{
	
	private ArrayList<Spieler> spieler_liste = new ArrayList<Spieler>();
	private int max_spieler;
	private boolean warten_auf_spieler;
	
	private ArrayList<Spieler> werwolf_liste;
	private ArrayList<Spieler> liebespaar;
	
	private String werwolfOpferName;
	private int rettungs_trank_anzahl = 1;
	private int toetungs_trank_anzahl = 1;
	private String jaegerZielName;
	private String verurteilterSpielerName;
	
	private Abstimmung abstimmung;
	
	private int anzahl_werwoelfe = 0;
	private int anzahl_wesen = 0;
	private final int ANZAHL_AMOR = 1;
	private final int ANZAHL_SEHERIN = 1;
	private final int ANZAHL_JAEGER = 1;
	private final int ANZAHL_HEXE = 1;
	private int anzahl_buerger;
	
	public SpielDaten() {
		set_warten_auf_spieler(true);
		werwolf_liste = new ArrayList<Spieler>();
		liebespaar = new ArrayList<Spieler>();
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
	
	/**
	 * Jeder Spieler zieht eine Karte und wird zu einem Wesen
	 * */
	public void kartenZiehen() {
		
		//TODO: Es kann meherer von einer Sorte geben? Austesten mir über Spielern. Wenn fehler besteht neu schreiben
		anzahl_buerger = getSpielerAnzahl() - (ANZAHL_AMOR + ANZAHL_HEXE + ANZAHL_JAEGER + ANZAHL_SEHERIN + anzahl_werwoelfe);
		int buerger = anzahl_buerger;
		int hexe = ANZAHL_HEXE;
		int seherin = ANZAHL_SEHERIN;
		int werwoelfe = anzahl_werwoelfe;
		int jaeger = ANZAHL_JAEGER;
		int amor = ANZAHL_AMOR;
		
		final int BUERGER = 0;
		final int AMOR = 1;
		final int HEXE = 2;
		final int WERWOLF = 3;
		final int SEHERIN = 4;
		final int JAEGER = 5;
		
		ArrayList<Spieler> spieler = (ArrayList<Spieler>) spieler_liste.clone();
		
		for(int i = 0; i < spieler.size(); i++) {
			boolean gezogen = false;
			Spieler s = spieler.get(i);
			while(!gezogen) {
				int zahl = new Random().nextInt(6);
				switch(zahl) {
				case BUERGER: {
					if(buerger > 0) {
						s.getSpielerDaten().setKreatur(Kreatur.BUERGER);
						buerger -= 1;
						gezogen = true;
					}
					break;}
				case AMOR: {
					if(amor > 0) {
						s.getSpielerDaten().setKreatur(Kreatur.ARMOR);
						amor -= 1;
						gezogen = true;
					}
					break;}
				case HEXE: {
					if(hexe > 0) {
						s.getSpielerDaten().setKreatur(Kreatur.HEXE);
						hexe -= 1;
						gezogen = true;
					}
					break;}
				case WERWOLF: {
					if(werwoelfe > 0) {
						s.getSpielerDaten().setKreatur(Kreatur.WERWOLF);
						werwoelfe -= 1;
						addWerwolf(s);
						gezogen = true;
					}
					break;}
				case SEHERIN: {
					if(seherin > 0) {
						s.getSpielerDaten().setKreatur(Kreatur.SEHERIN);
						seherin -= 1;
						gezogen = true;
					}
					break;}
				case JAEGER: {
					if(jaeger > 0) {
						s.getSpielerDaten().setKreatur(Kreatur.SEHERIN);
						jaeger -= 1;
						gezogen = true;
					}
					break;}
				}
			}
			System.out.println("Spieler kartenwahl");
		}
		System.out.println("Rollenverteilung abgeschlossen");
		
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

	public int getAnzahlWerwoelfe() {
		return anzahl_werwoelfe;
	}

	public void setAnzahlWerwoelfe(int anzahl_wwerwoelfe) {
		this.anzahl_werwoelfe = anzahl_wwerwoelfe;
	}

	public int getAnzahlWesen() {
		return anzahl_wesen;
	}

	public void setAnzahlWesen(int anzahl_wesen) {
		this.anzahl_wesen = anzahl_wesen;
	}
	
	public void addWerwolf(Spieler spieler) {
		if(!spieler.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
			try {
				throw new Exception("Spieler ist kein Werwolf");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		werwolf_liste.add(spieler);
			
	}
	
	public boolean isInLiebesPaar(String name) {
		for(Spieler s : liebespaar) {
			if(s.getSpielerDaten().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	public Spieler getLiebe(String name) {
		if(!isInLiebesPaar(name)) {
			return null;
		}
		
		for(Spieler s : liebespaar) {
			if(!s.getSpielerDaten().getName().equals(name)) {
				return s;
			}
		}
		
		return null;
	}
	
	public void removeWerwolf(String name) {
		werwolf_liste.remove(getSpieler(name));
	}

	public ArrayList<Spieler> getWerwolf_liste() {
		return werwolf_liste;
	}

	public void setWerwolf_liste(ArrayList<Spieler> werwolf_liste) {
		this.werwolf_liste = werwolf_liste;
	}

	public Abstimmung getAbstimmung() {
		return abstimmung;
	}

	public void setAbstimmung(Abstimmung abstimmung) {
		this.abstimmung = abstimmung;
	}
	
	public ArrayList<Spieler> getLiebespaar() {
		return liebespaar;
	}

	public String getWerwolfOpferName() {
		return werwolfOpferName;
	}

	public void setWerwolfOpfer(String opferName) {
		this.werwolfOpferName = opferName;
		getSpieler(opferName).getSpielerDaten().setLebendig(false);
	}
	
	public ArrayList<Spieler> getToteSpieler() {
		ArrayList<Spieler> tot = new ArrayList<Spieler>();
		for(Spieler s : getSpielerListe()) {
			if(!s.getSpielerDaten().isLebendig()) {
				tot.add(s);
			}
		}
		
		return tot;
	}
	
	/**
	 * Löscht die toten aus der SpielerListe und Werwolfliste
	 * */
	public void toteBegraben() {
		for(Spieler s : getSpielerListe()) {
			if(!s.getSpielerDaten().isLebendig()) {
				removeSpieler(s);
				if(s.getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF)) {
					removeWerwolf(s.getSpielerDaten().getName());
				}
			}
		}
	}

	public int getRettungsTrankAnzahl() {
		return rettungs_trank_anzahl;
	}

	public void setRettungsTrankAnzahl(int rettungs_trank_anzahl) {
		this.rettungs_trank_anzahl = rettungs_trank_anzahl;
	}

	public int getToetungsTrankAnzahl() {
		return toetungs_trank_anzahl;
	}

	public void setToetungsTrankAnzahl(int toetungs_trank_anzahl) {
		this.toetungs_trank_anzahl = toetungs_trank_anzahl;
	}
	
	public void setJaegerZiel(String name) {
		this.jaegerZielName = name;
	}
	
	public String getJaegerZiel() {
		return jaegerZielName;
	}

	public String getVerurteilterSpielerName() {
		return verurteilterSpielerName;
	}

	public void setVerurteilterSpielerName(String verurteilterSpielerName) {
		this.verurteilterSpielerName = verurteilterSpielerName;
	}
	
	
}
