package out;

import javax.swing.JOptionPane;

public class SpielAusgabe {

	public static void error(String title, String message) {
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.err.println("ERROR: "+message);
				JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
	}
	
	
}
