package out;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SpielAusgabe {

	public static void error(Component parent, String title, String message) {
		
		
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	System.err.println("ERROR: "+message);
				JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);      
	        }
	    });
		
		
	}
	
	
}
