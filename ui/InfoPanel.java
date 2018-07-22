package ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;

import game.Spieler;
import karten.Kreatur;

import javax.swing.JSeparator;
import java.awt.Button;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Toolkit;

public class InfoPanel extends JPanel {
	private JLabel lblIdentity;
	public Button exit;
	private GameWindow window;
	private JTextArea txtrAlsGottDer;
	private JTextArea seherin_beschreibung;
	private JTextArea jaeger_beschreibung;
	private JLabel label_2;
	private JTextArea jaeger_anleitung;
	private JTextArea txtrDuBistWie;
	private JLabel label_3;
	private JLabel lblYouAre;
	private JLabel lblDeinJagtziel;
	private JLabel label_4;
	/**
	 * Create the panel.
	 */
	public InfoPanel(GameWindow window) {
		this.window = window;
		setBackground(Color.DARK_GRAY);
		setBounds(0,0,500,800);
		setLayout(null);
		
		lblYouAre = new JLabel("Du bist...");
		lblYouAre.setForeground(Color.WHITE);
		lblYouAre.setFont(new Font("Perpetua", Font.BOLD, 17));
		lblYouAre.setBounds(10, 24, 300, 37);
		add(lblYouAre);
		
		lblIdentity = new JLabel("ERROR");
		lblIdentity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdentity.setForeground(Color.RED);
		lblIdentity.setFont(new Font("Stencil", Font.PLAIN, 30));
		lblIdentity.setBounds(36, 60, 408, 48);
		add(lblIdentity);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.GRAY);
		separator.setBounds(36, 119, 408, 28);
		add(separator);
		
		exit = new Button("X");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				exit.requestFocus();
				window.getHauptSpielPanel().btnShowInfo.setVisible(true);
			}
		});
		exit.setBounds(420, 10, 70, 22);
		add(exit);
		
		setKreatur(window.getGame().getSpieler().getSpielerDaten().getKreatur());
		setPlayerName(window.getGame().getSpieler().getSpielerDaten().getName());
		
		
	}
	
	public Button getExitButton() {
		return exit;
	}
	
	public void setPlayerName(String name) {
		lblYouAre.setText("Du bist "+name+ " ...");
	}
	
	public void buerger() {
		lblIdentity.setText("Ein Bürger");
		txtrDuBistWie = new JTextArea();
		txtrDuBistWie.setForeground(Color.WHITE);
		txtrDuBistWie.setBackground(Color.DARK_GRAY);
		txtrDuBistWie.setFont(new Font("Papyrus", Font.PLAIN, 16));
		txtrDuBistWie.setLineWrap(true);
		txtrDuBistWie.setWrapStyleWord(true);
		txtrDuBistWie.setText("Du bist, wie es scheint, einer der wenigen wirklichen Menschen in D\u00FCsterwald. Zusammen mit deinen Mitb\u00FCrgern versuchst du, die Werw\u00F6lfe ein f\u00FCr alle Mal loszuwerden. Aber sei vorsichtig, wen du beschuldigst, am Ende rammst du dir damit noch selbst das Messer in die Brust.");
		txtrDuBistWie.setEditable(false);
		txtrDuBistWie.setBounds(36, 141, 416, 148);
		add(txtrDuBistWie);
		
		label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/buerger_pic.jpg")));
		label_3.setBounds(31, 295, 441, 221);
		add(label_3);
	}
	
	public void jaeger() {
		lblIdentity.setText("Ein Jäger");
		jaeger_beschreibung = new JTextArea();
		jaeger_beschreibung.setBackground(Color.DARK_GRAY);
		jaeger_beschreibung.setForeground(Color.WHITE);
		jaeger_beschreibung.setFont(new Font("Papyrus", Font.PLAIN, 13));
		jaeger_beschreibung.setWrapStyleWord(true);
		jaeger_beschreibung.setLineWrap(true);
		jaeger_beschreibung.setText("Die Armbrust ist dein bester Freund, wenn es ums T\u00F6ten von diesen elendigen Biestern geht. Du bist in D\u00FCsterwald geboren und w\u00FCrdest dein Leben f\u00FCr dein Dorf geben, doch du bist ebenso aufopfernd, wie du rachs\u00FCchtig bist. Dein J\u00E4hzorn sucht jeden heim, der dich beschuldigt, ein Werwolf zu sein, schlie\u00DFlich bist du derjenige, dessen Frau sie als Erstes geholt haben. Wenn du zu Tod durch den Strick verurteilt wirst, steht es dir frei, jemanden mit in den Tod zu nehmen. Wer wei\u00DF, vielleicht waren es die Werw\u00F6lfe, die dein eigenes Dorf auf dich gehetzt haben, und wenn Amor bereits zwei dieser Biester erwischt hat, gelingt es dir m\u00F6glicherweise, zwei Fliegen mit einer Klappe zu schlagen.\r\n");
		jaeger_beschreibung.setBounds(36, 145, 419, 229);
		add(jaeger_beschreibung);
		jaeger_beschreibung.setEditable(false);
		
		label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/jaeger_hirsch.png")));
		label_2.setBounds(36, 372, 216, 214);
		add(label_2);
		
		jaeger_anleitung = new JTextArea();
		jaeger_anleitung.setWrapStyleWord(true);
		jaeger_anleitung.setText("Du kannst jederzeit im Spiel einen anderen Spieler mir Rechsklick aus w\u00E4hlen und\r\nihn als dein Ziel zu markieren. Wenn du stirbst, egal ob vom Werwolf oder in der\r\nAbstimmung, du nimmst ihn mit ins Grab!");
		jaeger_anleitung.setLineWrap(true);
		jaeger_anleitung.setForeground(Color.WHITE);
		jaeger_anleitung.setFont(new Font("Papyrus", Font.PLAIN, 16));
		jaeger_anleitung.setBackground(Color.DARK_GRAY);
		jaeger_anleitung.setBounds(230, 384, 260, 229);
		add(jaeger_anleitung);
		jaeger_anleitung.setEditable(false);
		
		lblDeinJagtziel = new JLabel("Dein Jagtziel: ");
		lblDeinJagtziel.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		lblDeinJagtziel.setForeground(Color.RED);
		lblDeinJagtziel.setBounds(36, 641, 419, 37);
		add(lblDeinJagtziel);
		setJagtziel(">>Niemand<<");
	}
	
	public void seherin() {
		lblIdentity.setText("a.k.a. Mergo, Seherin");
		seherin_beschreibung = new JTextArea();
		seherin_beschreibung.setForeground(Color.WHITE);
		seherin_beschreibung.setBackground(Color.DARK_GRAY);
		seherin_beschreibung.setFont(new Font("Papyrus", Font.PLAIN, 14));
		seherin_beschreibung.setWrapStyleWord(true);
		seherin_beschreibung.setLineWrap(true);
		seherin_beschreibung.setText("Du hast viele Namen, wo du herkommst, doch hier hei\u00DFt du Mergo. Manche nennen dich eine Ausgeburt der H\u00F6lle, andere ein Geschenk des Himmels. Es ist deine Gabe und dein Fluch, jeden so zu sehen wie er wirklich ist. Du wachst kurz vor den anderen Dorfbewohnern auf und kannst jeden Morgen das wahre Wesen einer deiner Nachbarn erkennen. Doch sei auf der Hut, wenn du deine Vermutungen \u00E4u\u00DFerst, denn wie erkl\u00E4rst du, was du gesehen hast?\r\n\r\nUm deine Kraft einzusetzenm klicke in der Nacht mit rechsklick auf einen Spieler deiner Wahl und w\u00E4hle \"Enth\u00FCllen\"");
		seherin_beschreibung.setBounds(46, 286, 398, 279);
		add(seherin_beschreibung);
		seherin_beschreibung.setEditable(false);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/seherin.jpg")));
		label.setBounds(46, 133, 398, 142);
		add(label);
		
		label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/eye-gif.gif")));
		label_4.setBounds(49, 551, 395, 201);
		add(label_4);
	}
	
	public void werwolf() {
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/werwolf_gif.gif")));
		label.setBounds(10, 595, 346, 164);
		add(label);
		JLabel ww_pic = new JLabel("");
		ww_pic.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/werwolf_info.png")));
		ww_pic.setBounds(20, 158, 200, 200);
		add(ww_pic);
		
		lblIdentity.setText("Ein Werwolf");
		
		JTextArea ww_info = new JTextArea();
		ww_info.setEditable(false);
		ww_info.setForeground(Color.WHITE);
		ww_info.setText("Eine Kreatur der Nacht.\r\nNiemand au\u00DFerhalb deines\r\nRudels wei\u00DF von dir, und \r\ndas sollte besser auch so bleiben.\r\n\r\nTot allen Nicht-Werw\u00F6lfen!");
		ww_info.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		ww_info.setBackground(Color.DARK_GRAY);
		ww_info.setBounds(230, 158, 260, 200);
		add(ww_info);
		ww_info.setEditable(false);
		
		JTextArea ww_anleitung = new JTextArea();
		ww_anleitung.setEditable(false);
		ww_anleitung.setText("Ein Werwolf geht niemals Allein!\r\nDu kennst dein Rudel und gegenseitiger Schutz ist ein Gesetz.\r\n\r\nIn der Nacht stimmt ihr f\u00FCr eure Beute ab:\r\nKlicke dazu mit Rechsklick auf eine Karte deiner Wahl.\r\nAchtung: Bein einer Pattsituation erh\u00E4llt einer von euch\r\neine Doppelstimme.\r\n\r\nAm Tag versuchen die Bewohner euch dranzukriegen!\r\nSeid geschickt und versucht den Verdacht von euch abzulenken!\r\n\r\nViel Gl\u00FCck und guten Appetit!");
		ww_anleitung.setForeground(Color.WHITE);
		ww_anleitung.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		ww_anleitung.setBackground(Color.DARK_GRAY);
		ww_anleitung.setBounds(10, 376, 480, 208);
		add(ww_anleitung);
		ww_anleitung.setEditable(false);
		
	}
	
	public void hexe() {
		lblIdentity.setText("a.k.a. Adella, die Hexe");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/witch_gif.gif")));
		label.setBounds(281, 128, 165, 95);
		add(label);
		JLabel hexe_pic = new JLabel("");
		hexe_pic.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/AdellaHexe.png")));
		hexe_pic.setBounds(10, 128, 165, 95);
		add(hexe_pic);
		
		JLabel hexe_potion_pic = new JLabel("");
		hexe_potion_pic.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/potion.jpg")));
		hexe_potion_pic.setBounds(20, 469, 174, 224);
		add(hexe_potion_pic);
		
		JTextArea hexe_beschreibung = new JTextArea();
		hexe_beschreibung.setWrapStyleWord(true);
		hexe_beschreibung.setLineWrap(true);
		hexe_beschreibung.setEditable(false);
		hexe_beschreibung.setForeground(Color.WHITE);
		hexe_beschreibung.setText("Dein Name ist Adella, ganze 236 Jahre hast du als Hexe bereits in D\u00FCsterwald \u00FCberlebt. Mit Tr\u00E4nken ver\u00E4nderst du hin und wieder dein Aussehen und gibst dich als deine eigenen Verwandten aus, sodass die anderen Dorfbewohner nicht merken, was du bist. Nachdem das Morden in D\u00FCsterwald beginnt, kannst du nicht riskieren, entdeckt zu werden, die Situation ist zu brenzlig. Du hast lediglich zwei Tr\u00E4nke bei dir: Einen Todes- und einen Schutztrank. Jeden Morgen wachst du nach den Werw\u00F6lfen und von den anderen Dorfbewohnern auf, du kannst w\u00E4hlen, ob du neben dem Opfer der Werw\u00F6lfe noch jemanden mit dem Todestrank umbringst, oder die arme Seele \u2013 gegebenenfalls auch deine eigene \u2013 mit dem Schutztrank vor eine grausamen Tod rettest. Oder du h\u00E4ltst dich im Hintergrund, schlie\u00DFlich hast du all die Jahre nur \u00FCberlegt, weil du dich zuerst um dich selbst gek\u00FCmmert und stets drei Z\u00FCge voraus gedacht hast.");
		hexe_beschreibung.setFont(new Font("Papyrus", Font.PLAIN, 12));
		hexe_beschreibung.setBackground(Color.DARK_GRAY);
		hexe_beschreibung.setBounds(10, 234, 480, 224);
		add(hexe_beschreibung);
		hexe_beschreibung.setEditable(false);
		
		JTextArea hexe_anleitung = new JTextArea();
		hexe_anleitung.setEditable(false);
		hexe_anleitung.setText("In jeder nacht kannst\r\ndu ein Werwolfopfer\r\nsch\u00FCtzen (auch dich selbst),\r\nsowie jemanden t\u00F6ten\r\n\r\nKlicke dazu mit rechtsklick auf\r\ndie gew\u00FCnschte Karte und w\u00E4hle.\r\nVergiss nicht: Du bist auf der\r\nSeite der Dorfbewohner");
		hexe_anleitung.setForeground(Color.WHITE);
		hexe_anleitung.setFont(new Font("Papyrus", Font.PLAIN, 16));
		hexe_anleitung.setBackground(Color.DARK_GRAY);
		hexe_anleitung.setBounds(204, 462, 270, 238);
		add(hexe_anleitung);
		hexe_anleitung.setEditable(false);
	}
	
	public void amor() {
		lblIdentity.setText("Der Amor (undercover)");
		txtrAlsGottDer = new JTextArea();
		txtrAlsGottDer.setFont(new Font("Papyrus", Font.PLAIN, 14));
		txtrAlsGottDer.setForeground(Color.WHITE);
		txtrAlsGottDer.setBackground(Color.DARK_GRAY);
		txtrAlsGottDer.setText("Als Gott der Liebe hast du deinem Dorf viele gute Tage bereitet, doch was hilft dir schon Liebe gegen den Biss eines Werwolfes? Vieles. Dein Ziel ist es, zwei Werw\u00F6lfe ineinander zu verlieben, damit du zwei dieser Kreaturen auf einmal vom Spielplan fegst, wenn einer von ihnen durch die t\u00E4gliche Dorfabstimmung zum Tode verurteilt wird. Aber w\u00E4hle weise, wen du mit deinen Pfeilen triffst! Schlie\u00DFlich willst du doch keine unschuldigen B\u00FCrger umbringen. Oder?");
		txtrAlsGottDer.setLineWrap(true);
		txtrAlsGottDer.setWrapStyleWord(true);
		txtrAlsGottDer.setBounds(36, 133, 217, 372);
		add(txtrAlsGottDer);
		txtrAlsGottDer.setEditable(false);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/loyality_knows_no_bounds.jpg")));
		label.setBounds(263, 139, 209, 329);
		add(label);
		
		JTextArea txtrWennDuErwachst = new JTextArea();
		txtrWennDuErwachst.setWrapStyleWord(true);
		txtrWennDuErwachst.setText("Wenn du erwachst, klicke mit Rechsklick auf die Spieler und w\u00E4hle \"Verlieben\", \r\noder warte bis deine Zeit um ist.\r\nW\u00E4hle weise, du hast nur einen Chance !");
		txtrWennDuErwachst.setLineWrap(true);
		txtrWennDuErwachst.setForeground(Color.WHITE);
		txtrWennDuErwachst.setFont(new Font("Papyrus", Font.PLAIN, 18));
		txtrWennDuErwachst.setBackground(Color.DARK_GRAY);
		txtrWennDuErwachst.setBounds(238, 516, 234, 273);
		add(txtrWennDuErwachst);
		txtrWennDuErwachst.setEditable(false);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(InfoPanel.class.getResource("/res/Amor_pic.jpg")));
		label_1.setBounds(24, 516, 185, 185);
		add(label_1);
	}
	
	public void setKreatur(Kreatur k) {
		switch(k) {
		case BUERGER:{buerger();break;}
		case HEXE: {hexe(); break;}
		case SEHERIN: {seherin();break;}
		case WERWOLF: {werwolf();break;}
		case AMOR: {amor();break;}
		case JAEGER: {jaeger();break;}
		}
	}
	
	public void setRudel() {
		if(!window.getGame().getSpieler().getSpielerDaten().getKreatur().equals(Kreatur.WERWOLF))
			return;
		for(int i = 0 ; i < window.getGame().getSpielDaten().getWerwolf_liste().size(); i++) {
			Spieler ww = window.getGame().getSpielDaten().getWerwolf_liste().get(i);
			String name = ww.getSpielerDaten().getName();
			Karte ww_karte = window.getHauptSpielPanel().getKarte(name);
			ww_karte.enttarnen(Kreatur.WERWOLF);
		}
		
	}
	
	public void setJagtziel(String name) {
		lblDeinJagtziel.setText("Dein Jagtziel: "+name);
	}
}
