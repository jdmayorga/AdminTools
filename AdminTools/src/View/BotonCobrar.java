package View;

import java.awt.Image;

import javax.swing.ImageIcon;

public class BotonCobrar extends BotonesApp {
private ImageIcon imgGuardar;
	
	
	BotonCobrar(){
			super("F3 Cobrar");
			
			imgGuardar=new ImageIcon(BotonCancelar.class.getResource("/View/imagen/Facturacion.png"));
			
			 Image image = imgGuardar.getImage();
			    // reduce by 50%
			 image = image.getScaledInstance(image.getWidth(null)/7, image.getHeight(null)/7, Image.SCALE_SMOOTH);
			 imgGuardar.setImage(image);
		
			this.setIcon(imgGuardar);
				
			
		}

}
