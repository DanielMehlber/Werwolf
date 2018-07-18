package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import game.Game;
import game.SpielDaten;
import game.Spieler;
import karten.Kreatur;

public class AktionMenu extends JPopupMenu {

	private Karte karte;
	
	JMenuItem schliessen;
	JMenuItem abstimmen;
	JMenuItem opfer_abstimmen;
	JMenuItem retten;
	JMenuItem toeten;
	JMenuItem jagt_ziel;
	JMenuItem enttarnen;
	JMenuItem verlieben;
	public AktionMenu(Karte karte) {
		this.karte = karte;
		schliessen = new JMenuItem("[X] Schließen");
		abstimmen = new JMenuItem("Abstimmen");
		abstimmen.setEnabled(false);
		opfer_abstimmen = new JMenuItem("Opfer abstimmen");
		opfer_abstimmen.setEnabled(false);
		retten = new JMenuItem("Schützen");
		retten.setEnabled(false);
		toeten = new JMenuItem("Töten");
		toeten.setEnabled(false);
		jagt_ziel = new JMenuItem("Jagtziel");
		enttarnen = new JMenuItem("Enttarnen");
		enttarnen.setEnabled(false);
		verlieben = new JMenuItem("Verlieben");
		verlieben.setEnabled(false);
		
		schliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});
		
		abstimmen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				abstimmen();
				setVisible(false);
			}
		});
		
		verlieben.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				verliebter();
				setVisible(false);
				
			}
		});
		
		retten.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				retten();
				setVisible(false);
			}
		});
		
		toeten.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				toeten();
				setVisible(false);
			}
		});
		
		enttarnen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sehen();
				setVisible(false);
			}
		});
		
		opfer_abstimmen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				abstimmen();
				setVisible(false);
				habenAlleWerwoelfeGewaehlt();
			}
		});
	}
	
	public void setKreatur(Kreatur k) {
		removeAll();
		add(schliessen);
		add(abstimmen);
		switch(k) {
		case BUERGER: {
			break;
		}
		case ARMOR: {
			add(verlieben);
			break;
		}
		case HEXE: {
			add(retten);
			add(toeten);
			break;
		}
		case JAEGER: {
			add(jagt_ziel);
			break;
		}
		case SEHERIN: {
			add(enttarnen);
			break;
		}
		case WERWOLF: {
			add(opfer_abstimmen);
			break;
		}
		}
	}
	
	public void abstimmenFreischalten(boolean b) {
		if(abstimmen == null) {
			return;
		}
		abstimmen.setEnabled(b);
	}
	
	public void werwolfFreischalten(boolean b) {
		opfer_abstimmen.setEnabled(b);
	}
	
	public void abstimmen() {
		String name = karte.getSpielerName();
		System.out.println("VOTE: Du hast für "+name+ " abgestimmt");
		karte.getHauptSpielPanel().getGameWindow().getGame().getSpielDaten().getAbstimmung().stimme(karte.getHauptSpielPanel().getGameWindow().getGame().getSpieler().getSpielerDaten().getName() ,name);
		karte.getHauptSpielPanel().getGameWindow().getGame().spielDatenTeilen();
	}
	
	public void amorFreischalten(boolean b) {
		verlieben.setEnabled(b);
	}
	
	public void verliebter() {
		ArrayList<Spieler> liebespaar = karte.getHauptSpielPanel().getGameWindow().getGame().getSpielDaten().getLiebespaar();
		if(liebespaar.size() == 2) {
			System.err.println("2 sind bereits verliebt");
			verlieben.setEnabled(false);
			return;
		}
		String name = karte.getSpielerName();
		Spieler v = karte.getSpielerFromGameData(name);
		liebespaar.add(v);
		System.out.println("SPIEL: Du hast "+name+" dazu gebracht sich zu verlieben");
		
		if(liebespaar.size() == 2) {
			verlieben.setEnabled(false);
			karte.getHauptSpielPanel().getGameWindow().getGame().zeitRaffer();
			karte.getHauptSpielPanel().getGameWindow().getGame().spielDatenTeilen();
			karte.getHauptSpielPanel().getGameWindow().getGame().eventÜberspringen("amor");
		}
	}
	
	public void hexeFreischalten(boolean b) {
		if(b) {
			SpielDaten daten = karte.getHauptSpielPanel().getGameWindow().getGame().getSpielDaten();
			if(daten.getWerwolfOpferName().equals(karte.getSpielerName()) && daten.getRettungsTrankAnzahl() != 0)
				retten.setEnabled(true);
			if(daten.getToetungsTrankAnzahl() != 0)
				toeten.setEnabled(true);
		}else {
			toeten.setEnabled(false);
			retten.setEnabled(false);
		}
	}
	
	public void retten() {
		karte.getHauptSpielPanel().getGameWindow().getGame().getSpielDaten().setRettungsTrankAnzahl(0);
		Game game = karte.getHauptSpielPanel().getGameWindow().getGame();
		Spieler opfer = game.getSpielDaten().getSpieler(game.getSpielDaten().getWerwolfOpferName());
		opfer.getSpielerDaten().setLebendig(true);
		game.spielDatenTeilen();
		hexeFreischalten(false);
		game.zeitRaffer();
		
		Game g = karte.getHauptSpielPanel().getGameWindow().getGame();
		if(g.getSpielDaten().getRettungsTrankAnzahl() == 0 && g.getSpielDaten().getToetungsTrankAnzahl() == 0) {
			karte.getHauptSpielPanel().getGameWindow().getGame().eventÜberspringen("hexe");
		}
	}
	
	public void toeten() {
		karte.getHauptSpielPanel().getGameWindow().getGame().getSpielDaten().setToetungsTrankAnzahl(0);
		Game game = karte.getHauptSpielPanel().getGameWindow().getGame();
		Spieler opfer = game.getSpielDaten().getSpieler(karte.getSpielerName());
		opfer.getSpielerDaten().setLebendig(false);
		game.spielDatenTeilen();
		hexeFreischalten(false);
		game.zeitRaffer();
	}
	
	public void seherinFreischalten(boolean b) {
		enttarnen.setEnabled(b);
	}
	
	public void sehen() {
		karte.enttarnen(null);
	}
	
	public void habenAlleWerwoelfeGewaehlt() {
		SpielDaten daten = karte.getHauptSpielPanel().getGameWindow().getGame().getSpielDaten();
		if(daten.getAbstimmung().getWaehlerAnzahl() == daten.getWerwolf_liste().size()) {
			karte.getHauptSpielPanel().getGameWindow().getGame().zeitRaffer();
		}
	}
	

}
