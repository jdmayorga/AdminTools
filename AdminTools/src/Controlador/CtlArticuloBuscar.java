package Controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.Conexion;
import View.TablaModeloMarca;
import View.ViewCrearArticulo;
import View.ViewFacturar;
import View.ViewListaArticulo;

public class CtlArticuloBuscar implements ActionListener,MouseListener, WindowListener {
	
	public ViewListaArticulo view;
	//public ViewCrearArticulo viewArticulo;
	
	
	private Articulo myArticulo;
	private ArticuloDao myArticuloDao;
	
	//fila selecciona enla lista
	private int filaPulsada;
	
	//pool de conexion
	private Conexion conexion=null;
	
	public CtlArticuloBuscar(ViewListaArticulo view, Conexion conn){
		
		conexion=conn;
		this.view=view;
		myArticulo=new Articulo();
		myArticuloDao=new ArticuloDao(conexion);
		cargarTabla(myArticuloDao.todoArticulos());
		//this.view.setVisible(true);
	}
	
	public void cargarTabla(List<Articulo> articulos){
		//JOptionPane.showMessageDialog(view, articulos);
		this.view.getModelo().limpiarArticulos();
		for(int c=0;c<articulos.size();c++){
			this.view.getModelo().agregarArticulo(articulos.get(c));
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		myArticuloDao.desconectarBD();
		this.view.setVisible(false);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaArticulos().getSelectedRow();
		if (e.getClickCount() == 2){
			myArticulo=this.view.getModelo().getArticulo(filaPulsada);
			myArticuloDao.desconectarBD();
			this.view.setVisible(false);
			//JOptionPane.showMessageDialog(null,myMarca);
			this.view.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//se recoge el comando programado en el boton que se hizo click
				String comando=e.getActionCommand();
				

				
				
				switch(comando){
				case "BUSCAR":
					//si se seleciono el boton ID
					if(this.view.getRdbtnId().isSelected()){  
						myArticulo=myArticuloDao.buscarArticulo(Integer.parseInt(this.view.getTxtBuscar().getText()));
						if(myArticulo!=null){												
							this.view.getModelo().limpiarArticulos();
							this.view.getModelo().agregarArticulo(myArticulo);
						}else{
							JOptionPane.showMessageDialog(view, "No se encuentro el articulo");
						}
					} 
					
					if(this.view.getRdbtnArticulo().isSelected()){ //si esta selecionado la busqueda por nombre	
						
						cargarTabla(myArticuloDao.buscarArticulo(this.view.getTxtBuscar().getText()));
				        
						}
					if(this.view.getRdbtnMarca().isSelected()){  
						cargarTabla(myArticuloDao.buscarArticuloMarca(this.view.getTxtBuscar().getText()));
						}
					
					if(this.view.getRdbtnTodos().isSelected()){  
						cargarTabla(myArticuloDao.todoArticulos());
						this.view.getTxtBuscar().setText("");
						}
					break;
				
				
					
						
					}
	}
	
	public Articulo buscarArticulo(ViewFacturar v){
		
		//this.myArticuloDao.cargarInstrucciones();
		cargarTabla(myArticuloDao.todoArticulos());
		myArticulo=null;
		this.view.getBtnEliminar().setEnabled(false);
		this.view.getBtnAgregar().setEnabled(false);
		this.view.setLocationRelativeTo(v);
		this.view.setModal(true);
		this.view.setVisible(true);
		return this.myArticulo;
	}

}