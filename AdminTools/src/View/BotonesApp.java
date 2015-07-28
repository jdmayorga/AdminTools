package View;

import javax.swing.JButton;

public class BotonesApp extends JButton {
	private static final int ancho=128;
	private static final int alto=45;
	
	public BotonesApp(){
		this.setSize(ancho, alto);
		
	}
	public BotonesApp(String titulo){
		super(titulo);
		this.setSize(ancho, alto);
		
	}
	

}
