package out;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SpielAusgabe {

	public static String seperator = "#----------------------------------------------------------------------------------#";
	
	public static void error(Component parent, String title, String message) {
		
		
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	System.err.println("ERROR: "+message);
				JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);      
	        }
	    });
		
		
	}
	
	public static void info(Component parent, String title, String nachricht) {
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
				JOptionPane.showMessageDialog(parent, nachricht, title, JOptionPane.INFORMATION_MESSAGE);      
	        }
	    });
	}
	
	public static void seperator() {
		System.out.println(seperator);
	}
	
	
}
