package View;

import java.awt.Image;

import javax.swing.ImageIcon;



public class BotonGuardar extends BotonesApp {
	private ImageIcon imgGuardar;
	
	
	BotonGuardar(){
		super("Guardar");
		
		imgGuardar=new ImageIcon(BotonCancelar.class.getResource("/View/imagen/ic_launcher.png"));
		
		 Image image = imgGuardar.getImage();
		    // reduce by 50%
		 image = image.getScaledInstance(image.getWidth(null)/2, image.getHeight(null)/2, Image.SCALE_SMOOTH);
		 imgGuardar.setImage(image);
	
		this.setIcon(imgGuardar);
			
		
	}
	
	
	
  

}
