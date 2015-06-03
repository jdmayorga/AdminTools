package Controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.CodBarraDao;
import Modelo.Conexion;
import Modelo.Marca;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import View.TablaModeloMarca;
import View.ViewCrearArticulo;
import View.ViewCrearMarca;
import View.ViewCrearProveedor;
import View.ViewListaArticulo;

public class CtlArticuloLista implements ActionListener,MouseListener, WindowListener {
	public ViewListaArticulo view;
	public ViewCrearArticulo viewArticulo;
	
	
	private Articulo myArticulo;
	private ArticuloDao myArticuloDao;
	
	//fila selecciona enla lista
	private int filaPulsada;
	
	//pool de conexion
	private Conexion conexion=null;
	
	public CtlArticuloLista(ViewListaArticulo view,Conexion conn){
		//se estable el pool de conexiones del inicio y para su utilizacion
		conexion=conn;
		this.view=view;
		myArticulo=new Articulo();
		myArticuloDao=new ArticuloDao(conexion);
		cargarTabla(myArticuloDao.todoArticulos());
		this.view.setVisible(true);
		
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
		
		case "INSERTAR":
			//crea la ventana para ingresar un nuevo proveedor
			viewArticulo= new ViewCrearArticulo(this.view);
			
			//se crea el controlador de la ventana y se le pasa la view
			CtlArticulo ctl=new CtlArticulo(viewArticulo,myArticuloDao,conexion);
			
			//se llama la metodo conectarCtl encargado de hacer set al manejador de eventos
			viewArticulo.conectarCtl(ctl);
			
			boolean resuldoGuarda=ctl.agregarArticulo();
			if(resuldoGuarda){
				this.view.getModelo().agregarArticulo(ctl.getArticuloGuardado());
				
				/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
				int row =  this.view.getTablaArticulos().getRowCount () - 1;
				Rectangle rect = this.view.getTablaArticulos().getCellRect(row, 0, true);
				this.view.getTablaArticulos().scrollRectToVisible(rect);
				this.view.getTablaArticulos().clearSelection();
				this.view.getTablaArticulos().setRowSelectionInterval(row, row);
				TablaModeloMarca modelo = (TablaModeloMarca)this.view.getTablaArticulos().getModel();
				modelo.fireTableDataChanged();
			}
			//this.view.modelo.agregarProveedor(ctl.myProveedor);//se agrega el nuevo proveedor registrado a la tabla de la vista
			
			
			break;
		case "ELIMINAR":
			//JOptionPane.showMessageDialog(view, myArticulo);
			int resul=JOptionPane.showConfirmDialog(view, "Desea eleminar el articulo \""+myArticulo.getArticulo()+"\"?");
			//JOptionPane.showMessageDialog(view, c);
			if(resul==0){
				//se elemina el articulo de la BD y se procesa el resultado
				boolean resulEliminar=myArticuloDao.eliminarArticulo(myArticulo.getId());
				if(resulEliminar){
					this.view.getModelo().eliminarArticulos(filaPulsada);
					this.view.getModelo().fireTableDataChanged();
					this.view.getBtnEliminar().setEnabled(false);
					JOptionPane.showMessageDialog(view, "Se elimino el articulo");
					
				}
				
			}
			break;
			
				
			}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaArticulos().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            int identificador= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            
          //se consigue el proveedore de la fila seleccionada
            myArticulo=this.view.getModelo().getArticulo(filaPulsada);
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
	        	myArticulo=this.view.getModelo().getArticulo(filaPulsada);
        		//myArticulo=this.view.getModelo().getArticulo(filaPulsada);//se consigue el Marca de la fila seleccionada
	           
	        	//crea la ventana para ingresar un nuevo proveedor
				viewArticulo= new ViewCrearArticulo(this.view);
				
				//se crea el controlador de la ventana y se le pasa la view
				CtlArticulo ctlActulizarArticulo=new CtlArticulo(viewArticulo,myArticuloDao,conexion);
				viewArticulo.conectarCtl(ctlActulizarArticulo);
				
				//se crea el objeto para casultar los codigos de barra en la bd
				CodBarraDao myCodBarraDao=new CodBarraDao(conexion);
				
				//se estable los codigos de bara encontrados al objeto myArticulo;
				myArticulo.setCodBarras(myCodBarraDao.getCodsArticulo(myArticulo.getId()));
				
				//se llama del metodo actualizar marca para que se muestre la ventanda y procesa la modificacion
				boolean resultado=ctlActulizarArticulo.actualizarArticulo(myArticulo);
				
				//se proceso el resultado de modificar la marca
				if(resultado){
					this.view.getModelo().cambiarArticulo(filaPulsada, ctlActulizarArticulo.getArticulo());//se cambia en la vista
					this.view.getModelo().fireTableDataChanged();//se refrescan los cambios
					this.view.getTablaArticulos().getSelectionModel().setSelectionInterval(filaPulsada,filaPulsada);//se seleciona lo cambiado
				}	
			
				
				
				
	        }//fin del if del doble click
        	else{//si solo seleccion la fila se guarda el id de proveedor para accion de eliminar
        		
        		this.view.getBtnEliminar().setEnabled(true);
        		/*idProveedor=identificador;
        		filaTabla=filaPulsada;*/
        		
        	}
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
	
	
	public void cargarTabla(List<Articulo> articulos){
		//JOptionPane.showMessageDialog(view, articulos);
		
		if(articulos!=null){
			this.view.getModelo().limpiarArticulos();
			for(int c=0;c<articulos.size();c++){
				this.view.getModelo().agregarArticulo(articulos.get(c));
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//this.myArticulorDao.close();
		this.view.setVisible(false);
		this.conexion.desconectar();
		
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

}
