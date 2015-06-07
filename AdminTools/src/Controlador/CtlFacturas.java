package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Modelo.CodBarraDao;
import Modelo.Conexion;
import Modelo.Factura;
import Modelo.FacturaDao;
import Modelo.UsuarioDao;
import View.ViewCrearArticulo;
import View.ViewFacturas;

public class CtlFacturas implements ActionListener, MouseListener, ChangeListener {
	private ViewFacturas view;
	
	private FacturaDao myFacturaDao=null;
	private Conexion conexion=null;
	private Factura myFactura;
	private UsuarioDao myUsuarioDao=null;
	
	
	//fila selecciona enla lista
	private int filaPulsada;
	
	public CtlFacturas(ViewFacturas v,Conexion conn) {
		view =v;
		view.conectarControlador(this);
		conexion=conn;
		myFacturaDao=new FacturaDao(conexion);
		cargarTabla(myFacturaDao.todasfacturas());
		myFactura=new Factura();
		myUsuarioDao=new UsuarioDao(conexion);
		view.setVisible(true);
	}
	
	public void cargarTabla(List<Factura> facturas){
		//JOptionPane.showMessageDialog(view, " "+facturas.size());
		this.view.getModelo().limpiarFacturas();
		
		if(facturas!=null){
			for(int c=0;c<facturas.size();c++){
				this.view.getModelo().agregarFactura(facturas.get(c));
				
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaFacturas().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            //int idFactura= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            
            this.view.getBtnEliminar().setEnabled(true);
            this.myFactura=this.view.getModelo().getFactura(filaPulsada);
            /*/se consigue el proveedore de la fila seleccionada
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
        		
        		
        	}*/
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
		String comando=e.getActionCommand();
		
		switch (comando){
		case "FECHA":
			this.view.getTxtBuscar2().setEditable(true);
			//JOptionPane.showMessageDialog(view, "Clip en fecha");
			break;
			
		case "TODAS":
			this.view.getTxtBuscar2().setEditable(false);
			this.view.getTxtBuscar2().setText("");
			this.view.getTxtBuscar1().setText("");
			
			break;
		case "ID":
			this.view.getTxtBuscar2().setEditable(false);
			this.view.getTxtBuscar2().setText("");
			this.view.getTxtBuscar1().setText("");
			
			break;
		case "BUSCAR":
			
			//si la busqueda es por id
			if(this.view.getRdbtnId().isSelected()){
				myFactura=myFacturaDao.facturasPorId(Integer.parseInt(this.view.getTxtBuscar1().getText()));
				if(myFactura!=null){												
					this.view.getModelo().limpiarFacturas();
					this.view.getModelo().agregarFactura(myFactura); 
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro la factura");
				}
				
			}
			//si la busqueda es por fecha
			if(this.view.getRdbtnFecha().isSelected()){  
				String fecha1=this.view.getTxtBuscar1().getText();
				String fecha2=this.view.getTxtBuscar2().getText();
				cargarTabla(myFacturaDao.facturasPorFechas(fecha1,fecha2));
				//this.view.getTxtBuscar1().setText("");
				//this.view.getTxtBuscar2().setText("");
				}
			
			
			//si la busqueda son tadas
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(myFacturaDao.todasfacturas());
				this.view.getTxtBuscar1().setText("");
				}
			break;
		case "ANULARFACTURA":
				int resul=JOptionPane.showConfirmDialog(view, "�Desea anular la factura no "+myFactura.getIdFactura()+"?");
				//sin confirmo la anulacion
				if(resul==0){
					String pwd=JOptionPane.showInputDialog(view, "Escriba la contrase�a admin", "Seguridad", JOptionPane.INFORMATION_MESSAGE);
					
					//comprabacion del permiso administrativo
					if(this.myUsuarioDao.comprobarAdmin(pwd)){
						//se anula la factura en la bd
						if(myFacturaDao.anularFactura(myFactura))
							myFactura.setEstado("NULA");
						//JOptionPane.showMessageDialog(view, "Usuario Valido");
					}else{
						JOptionPane.showMessageDialog(view, "Usuario Invalido");
					}
					
				}
			break;
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub facturasPorId
		
	}

}
