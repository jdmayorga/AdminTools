package View;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ViewCambioPago extends JDialog {
	private JTextField txtEfectivo;
	private JTextField textField;
	public ViewCambioPago() {
		this.setSize(262, 196);
		this.setPreferredSize(new Dimension(341, 287));
		this.setResizable(false);
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		JLabel lblPagaCon = new JLabel("Paga con:");
		lblPagaCon.setBounds(28, 38, 57, 14);
		getContentPane().add(lblPagaCon);
		
		txtEfectivo = new JTextField();
		txtEfectivo.setBounds(82, 35, 135, 20);
		getContentPane().add(txtEfectivo);
		txtEfectivo.setColumns(10);
		
		JLabel lblCambio = new JLabel("Cambio:");
		lblCambio.setBounds(28, 85, 57, 14);
		getContentPane().add(lblCambio);
		
		textField = new JTextField();
		textField.setBounds(82, 82, 135, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(28, 146, 89, 23);
		getContentPane().add(btnAceptar);
	}
}
