package ui;

import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ladebildschirm extends JLayeredPane {

	/**
	 * Create the panel.
	 */
	private GameWindow window;
	private JLabel loading;
	private JButton btnBereit;
	private JLabel label;
	public static enum Status{
		WARTEN, FERTIG
	}
	
	public Ladebildschirm(GameWindow window) {
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,700);
		window.getFrame().setBounds(0,0,1100, 750);
		setBounds(window.frame.getBounds());
		setLayout(null);
		
		btnBereit = new JButton("Bereit");
		btnBereit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setStatus(Status.FERTIG);
				window.getGame().getSpieler().getSpielerDaten().setBereit(true);
				window.getGame().getSpieler().bereit_senden();
			}
		});
		
		loading = new JLabel("");
		loading.setIcon(new ImageIcon(DorfErstellenPanel.class.getResource("/res/loading.gif")));
		loading.setBounds(463, 572, 145, 58);
		add(loading);
		loading.setVisible(false);
		btnBereit.setForeground(Color.RED);
		btnBereit.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnBereit.setBounds(420, 629, 243, 42);
		btnBereit.setBackground(new Color(0,0,0));
		add(btnBereit);
		
		JTextArea txtrDsterwaldEineKleiner = new JTextArea();
		txtrDsterwaldEineKleiner.setEditable(false);
		txtrDsterwaldEineKleiner.setText("D\u00FCsterwald, 2018\r\n\r\nEin kleines Dorf liegt inmitten der Wipfel, die die Sicht auf die Berge verdecken.\r\nAber unter den Bewohnern leben nicht nur Menschen, sondern auch\r\nanderes Blut fand seinen Weg unter die Leute. Sie leben immer \r\nfriedlich miteinander, da niemand das Geheimnis des anderen wei\u00DF,\r\nund jeder meint, er sei der einzige Nichtmensch. \r\n\r\nW\u00E4hrend die Bewohner gem\u00FCtlich in ihrer Kneipe sitzen und sich zulaufen lassen, \r\nfassen die Werw\u00F6lfe, eine als ausgerottet geglaubte Blutlinie, einen Plan:\r\n\r\nLange genug haben wir gewartet, diese Nacht schlagen wir zu!\r\nM\u00F6gen die Werw\u00F6lfe als einzige bestehen!\r\n\r\n");
		txtrDsterwaldEineKleiner.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 15));
		txtrDsterwaldEineKleiner.setForeground(Color.WHITE);
		txtrDsterwaldEineKleiner.setBackground(new Color(0, 0, 0, 0));
		txtrDsterwaldEineKleiner.setBounds(139, 83, 816, 478);
		add(txtrDsterwaldEineKleiner);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Ladebildschirm.class.getResource("/res/Ladebildschirm.jpg")));
		label.setBounds(0, -122, 1200, 822);
		add(label);
		
		setStatus(Status.WARTEN);
	}
	
	public void setStatus(Status s) {
		switch(s) {
		case WARTEN: {break;}
		case FERTIG: {
			loading.setVisible(true);
			btnBereit.setEnabled(false);
			btnBereit.setText("Warten auf Andere");
			break;}
		
		}
	}
	
	public void update(Rectangle b) {
		setBounds(b);
		Point before = btnBereit.getLocation();
		this.btnBereit.setLocation((int)(b.getWidth() / 2 - btnBereit.getBounds().getWidth()/2), (int)(b.getHeight() - btnBereit.getBounds().getHeight() / 2 - btnBereit.getBounds().getHeight() + 10));
		this.loading.setLocation(before.x + btnBereit.getLocation().x, before.y + btnBereit.getLocation().y);
		this.label.setLocation((int)(b.getWidth() / 2 - label.getBounds().getWidth() / 2), (int)(b.getHeight() / 2 - label.getBounds().getHeight() / 2));
	}
}
