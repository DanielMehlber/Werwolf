package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainGame extends JPanel {
	JButton btnShowInfo;
	/**
	 * Create the panel.
	 */
	private GameWindow window;
	public MainGame(GameWindow window) {
		setBackground(Color.BLACK);
		this.window = window;
		setBounds(0,0,1100,800);
		setBounds(window.frame.getBounds());
		setLayout(null);
		
		btnShowInfo = new JButton("");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showInfoBoard();
			}
		});
		btnShowInfo.setIcon(new ImageIcon(MainGame.class.getResource("/res/lexicon_icon.png")));
		btnShowInfo.setBounds(10, 11, 50, 50);
		add(btnShowInfo);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(MainGame.class.getResource("/res/chat_icon.png")));
		button.setBounds(10, 72, 50, 50);
		add(button);
		
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

}
