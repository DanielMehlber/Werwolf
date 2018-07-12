package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import game.SpielDaten;
import game.Spieler;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HauptSpielPanel extends JDesktopPane {
	JButton btnShowInfo;
	/**
	 * Create the panel.
	 */
	private GameWindow window;
	private InfoPanel infoPanel;
	
	public HauptSpielPanel(GameWindow window) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("HEY!");
			}
		});
		
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,800);
		setBounds(window.frame.getBounds());
		setLayout(null);
		
		infoPanel = new InfoPanel(window);
		infoPanel.setBounds(60, 11, 500, 771);
		infoPanel.setVisible(true);
		
		btnShowInfo = new JButton("");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Show Info Board
			}
		});
		
		Phone phone = new Phone();
		phone.setNormalBounds(new Rectangle(100, 100, 380, 740));
		phone.setLocation(176, 49);
		add(phone);
		phone.show();
		btnShowInfo.setIcon(new ImageIcon(HauptSpielPanel.class.getResource("/res/lexicon_icon.png")));
		btnShowInfo.setBounds(10, 49, 50, 50);
		add(btnShowInfo);
		
		JLabel lblNewLabel = new JLabel("12:20");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 46));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(463, 364, 200, 101);
		add(lblNewLabel);
		
	}
	
	public JButton getShowInfoButton() {
		return btnShowInfo;
	}
	
	public void kartenSetzen() {
		SpielDaten daten = window.getGame().getSpielDaten();
		for(int i = 0; i < daten.getSpielerAnzahl(); i++) {
			Spieler spieler = daten.getSpielerListe().get(i);
			Karte karte = new Karte(this);
			karte.setName(spieler.getSpielerDaten().getName());
		}
	}
	
	public GameWindow getGameWindow() {
		return window;
	}
	

}
