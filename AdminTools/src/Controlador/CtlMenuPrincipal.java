package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Modelo.Conexion;
import View.ViewAgregarCompras;
import View.ViewFacturar;
import View.ViewListaArticulo;
import View.ViewListaMarca;
import View.ViewListaProveedor;
import View.ViewMenuPrincipal;

public class CtlMenuPrincipal implements ActionListener {
	
	public ViewMenuPrincipal view;
	public Conexion conexion=null;
	
	
	public CtlMenuPrincipal(ViewMenuPrincipal view, Conexion conn){
		conexion=conn;
		this.view=view;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		switch(comando){
			case "PROVEEDORES":
				ViewListaProveedor viewListaProveedor=new ViewListaProveedor();
				CtlProveedorLista ctlProveedor=new CtlProveedorLista(viewListaProveedor,conexion);
				viewListaProveedor.conectarControlador(ctlProveedor);
				
				break;
			case "ARTICULOS":
				ViewListaArticulo viewListaArticulo=new ViewListaArticulo();
				CtlArticuloLista ctlArticulo =new CtlArticuloLista(viewListaArticulo,conexion);
				
				viewListaArticulo.conectarControlador(ctlArticulo);
				
				break;
			case "AGREGARCOMPRAS":
				ViewAgregarCompras viewAgregarCompras= new ViewAgregarCompras(this.view);
				CtlAgregarCompras ctl=new CtlAgregarCompras(viewAgregarCompras,conexion);
				break;
			case "FACTURAR":
				
			
				ViewFacturar vistaFacturar=new ViewFacturar(this.view);
				
				CtlFacturar ctlFacturar=new CtlFacturar(vistaFacturar);
		
				
				break;
			case "MARCAS":
				//se crea la lista de marcas
				ViewListaMarca viewMarcas=new ViewListaMarca(this.view);
				
				// se crea el control para la view lista marcas
				CtlMarcaLista ctlMarca =new CtlMarcaLista(viewMarcas,conexion); 
				
				//conectar los controles al objeto que ctlMarca
				viewMarcas.conectarControlador(ctlMarca);
				
				//hacer visible la view lista de marcas
				viewMarcas.setVisible(true);
				break;
		}
		
	}

}
