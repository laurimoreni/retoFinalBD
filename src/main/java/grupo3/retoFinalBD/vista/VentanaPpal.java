package grupo3.retoFinalBD.vista;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPpal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	public JTextArea textArea;
	public JButton btnOk;

	/**
	 * Create the frame.
	 */
	public VentanaPpal() {
		setResizable(false);
		setFont(new Font("Arial", Font.PLAIN, 12));
		setTitle("Carga de ficheros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Estado del proceso:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 484, 24);
		contentPane.add(lblNewLabel);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		textArea.setBounds(10, 46, 484, 402);
		contentPane.add(textArea);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnOk.setEnabled(false);
		btnOk.setFont(new Font("Arial", Font.PLAIN, 14));
		btnOk.setBounds(199, 459, 114, 34);
		contentPane.add(btnOk);
	}
}
