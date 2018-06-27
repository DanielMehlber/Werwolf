package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Wirft eine Fehlermeldung aus
 * @author Daniel Mehlber
 * */
public class ErrorThrow extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ErrorThrow dialog = new ErrorThrow("Irgentein verrückter fehler!", "Beschreibung");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ErrorThrow(String error, String stack) {
		setTitle("ERROR");
		setBounds(100, 100, 450, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			TextArea textArea = new TextArea();
			textArea.setText(error+"\n\n"+stack);
			textArea.setFont(new Font("DialogInput", Font.PLAIN, 14));
			textArea.setForeground(Color.RED);
			textArea.setBackground(Color.BLACK);
			textArea.setEditable(false);
			contentPanel.add(textArea, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

}
