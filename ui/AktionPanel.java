package ui;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import karten.Kreatur;

public class AktionPanel extends JPopupMenu {

	JMenuItem schliessen;
	JMenuItem abstimmen;
	JMenuItem retten;
	JMenuItem toeten;
	JMenuItem jagt_ziel;
	JMenuItem enttarnen;
	JMenuItem verlieben;
	public AktionPanel() {
		schliessen = new JMenuItem("[X] Schließen");
		abstimmen = new JMenuItem("Abstimmen");
		retten = new JMenuItem("Schützen");
		toeten = new JMenuItem("Töten");
		jagt_ziel = new JMenuItem("Jagtziel");
		enttarnen = new JMenuItem("Enttarnen");
		verlieben = new JMenuItem("Verlieben");
		
		schliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
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
			break;
		}
		}
	}

}
