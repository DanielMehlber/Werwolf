package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.Game;
import game.Spieler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameWindow {

	public JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow(null);
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JLayeredPane current_panel;
	private Game game;
	private HauptSpielPanel hauptSpielPanel;
	private Ladebildschirm laden;
	private JMenuBar menuBar;
	private ArrayList<JMenu> spieler_menus;
	private JMenu spiel;
	private JMenu spieler;
	private JMenu zeit;
	
	/**
	 * Create the application.
	 */
	public GameWindow(Game game) {
		this.game = game;
		spieler_menus = new ArrayList<JMenu>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if(hauptSpielPanel != null)
					hauptSpielPanel.update(frame.getBounds());

			}
		});
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Werwolf");
		frame.setBounds(0, 0, 1100, 700);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = null;
		try {
			image = ImageIO.read(new File("res/claw_cursor.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX(), 
		           frame.getY()), "img");
		frame.setCursor(c);
		
		if(getGame().getModerator() != null) {
			menu();
		}
	}
	
	public void wechseln(JLayeredPane panel) {
		frame.getContentPane().removeAll();
		current_panel = panel;
		frame.getContentPane().add(panel);
		frame.setLocation(0, 0);
		frame.revalidate();
		frame.repaint();
		
	}
	
	
	
	public JLayeredPane getCurrentPanel() {
		return current_panel;
	}
	
	
	public Game getGame() {
		return game;
	}
	
	public void redraw() {
		frame.repaint();
	}
	
	public HauptSpielPanel getHauptSpielPanel() {
		return this.hauptSpielPanel;
	}
	
	public void setHauptSpielPanel(HauptSpielPanel panel) {
		this.hauptSpielPanel = panel;
	}

	public Ladebildschirm getLadeBildschrim() {
		return laden;
	}

	public void setLadeBildschirm(Ladebildschirm laden) {
		this.laden = laden;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void menu() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		spiel = new JMenu("Spiel", false);
		spieler = new JMenu("Spieler");
		zeit = new JMenu("Zeit");
		menuBar.add(spiel);
		menuBar.add(spieler);
		menuBar.add(zeit);
		JMenuItem pause = new JMenuItem("Pause");
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().getModerator().pause();
				
			}
		});
		
		JMenuItem fortsetzen = new JMenuItem("Fortsetzen");
		fortsetzen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().getModerator().fortsetzen();
				
			}
		});
		
		JMenuItem beenden = new JMenuItem("Server beenden");
		beenden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().getModerator().serverBeenden();
				
			}
		});
		
		spiel.add(pause);
		spiel.add(fortsetzen);
		spiel.addSeparator();
		spiel.add(beenden);
		
		JMenuItem xone = new JMenuItem("x1");
		xone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getGame().minuteInSekunden(1);
			}
		});
		zeit.add(xone);
		
		JMenuItem xtwo = new JMenuItem("x2");
		xtwo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getGame().minuteInSekunden(0.5);
			}
		});
		zeit.add(xtwo);
		
		JMenuItem xfive = new JMenuItem("x5");
		xfive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getGame().minuteInSekunden(0.2);
			}
		});
		zeit.add(xfive);
		
	}
	
	
	public JMenu createSpielerMenu(String name) {
		JMenu sp = new JMenu(name);
		spieler_menus.add(sp);
		JMenuItem rauswerfen = new JMenuItem("entfernen");
		rauswerfen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = sp.getText();
				getGame().getModerator().rauswerfen(name);
				updateSpieler();
			}
		});
		
		sp.add(rauswerfen);
		spieler.add(sp);
		return sp;
	}
	
	public void updateSpieler() {
		spieler.removeAll();
		for(Spieler s : getGame().getSpielDaten().getSpielerListe()) {
			createSpielerMenu(s.getSpielerDaten().getName());
		}
	}
	
	
}
