package ui;

import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ladebildschirm extends JPanel {

	/**
	 * Create the panel.
	 */
	private GameWindow window;
	private JLabel loading;
	private JButton btnBereit;
	public static enum Status{
		WARTEN, FERTIG
	}
	
	public Ladebildschirm(GameWindow window) {
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,800);
		setBounds(window.frame.getBounds());
		setLayout(null);
		
		btnBereit = new JButton("Bereit");
		btnBereit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setStatus(Status.FERTIG);
			}
		});
		btnBereit.setForeground(Color.RED);
		btnBereit.setFont(new Font("Felix Titling", Font.BOLD, 15));
		btnBereit.setBackground(Color.BLACK);
		btnBereit.setBounds(421, 677, 243, 42);
		add(btnBereit);
		
		loading = new JLabel("");
		loading.setIcon(new ImageIcon(DorfErstellen.class.getResource("/res/loading.gif")));
		loading.setBounds(462, 619, 145, 58);
		add(loading);
		
		JTextArea txtrDsterwaldEineKleiner = new JTextArea();
		txtrDsterwaldEineKleiner.setEditable(false);
		txtrDsterwaldEineKleiner.setText("D\u00FCsterwald, 2018\r\n\r\nEin kleines Dorf liegt inmitten der Wipfel, die die Sicht auf die Berge verdecken.\r\nAber unter den Bewohnern leben nicht nur Menschen, sondern auch\r\nanderes Blut fand seinen Weg unter die Leute. Sie leben immer \r\nfriedlich miteinander, da niemand das Geheimnis des anderen wei\u00DF,\r\nund jeder meint, er sei der einzige Nichtmensch. \r\n\r\nW\u00E4hrend die Bewohner gem\u00FCtlich in ihrer Kneipe sitzen und sich zulaufen lassen, \r\nfassen die Werw\u00F6lfe, eine als ausgerottet geglaubte Blutlinie, einen Plan:\r\n\r\nLange genug haben wir gewartet, diese Nacht schlagen wir zu!\r\nM\u00F6gen die Werw\u00F6lfe als einzige bestehen!\r\n\r\n");
		txtrDsterwaldEineKleiner.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 15));
		txtrDsterwaldEineKleiner.setForeground(Color.WHITE);
		txtrDsterwaldEineKleiner.setBackground(Color.BLACK);
		txtrDsterwaldEineKleiner.setBounds(136, 112, 816, 478);
		add(txtrDsterwaldEineKleiner);
		loading.setVisible(false);
		
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
}