package View.Design;


import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;

import View.BotonGuardar;

public class Articulos extends JDialog {
	
	
	
	public Articulos() {
		getContentPane().setLayout(null);
		
		JButton btnGuardar = new BotonGuardar();
		//btnGuardar.setBounds(64, 69, 89, 23);
		btnGuardar.setLocation(164, 66);
		getContentPane().add(btnGuardar);
		
		
		
	}
}
