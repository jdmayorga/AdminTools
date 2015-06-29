package View;

import java.awt.Dialog;
import java.awt.Window;

import javax.swing.JDialog;

public class viewRequisicion extends JDialog {
	
	
	public viewRequisicion(Window view){
		super(view,"Requisicion de insumos",Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.setSize(300, 200);
	}

}
