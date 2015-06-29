package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Modelo.AbstractJasperReports;
import Modelo.CierreCajaDao;
import Modelo.Conexion;
import View.ViewAgregarCompras;
import View.ViewFacturar;
import View.ViewFacturas;
import View.ViewListaArticulo;
import View.ViewListaClientes;
import View.ViewListaFactura;
import View.ViewListaFacturasCompra;
import View.ViewListaMarca;
import View.ViewListaProveedor;
import View.ViewMenuPrincipal;
import View.ViewRequisicion;

public class CtlMenuPrincipal implements ActionListener {
	
	public ViewMenuPrincipal view;
	public Conexion conexion=null;
	
	
	
	public CtlMenuPrincipal(ViewMenuPrincipal view, Conexion conn){
		conexion=conn;
		this.view=view;
		
		view.getLblUserName().setText(conexion.getUsuarioLogin().getNombre());
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		switch(comando){
		
			case "REQUISICIONES":
					ViewRequisicion viewRequi=new ViewRequisicion(view);
					CtlRequisicion ctlRequi=new CtlRequisicion(viewRequi,conexion);
				break;
	
			case "CERRARFACTURACION":
				try {
					//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
					AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 4, 0);
					
					//this.view.setModal(false);
					//AbstractJasperReports.imprimierFactura();
					AbstractJasperReports.showViewer(this.view);
					
					CierreCajaDao cierre=new CierreCajaDao(conexion);
					
					if(cierre.registrarCierre()){
						JOptionPane.showMessageDialog(view, "Se creo el cierre de caja");
					}
					
				} catch (SQLException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				break;
			case "PROVEEDORES":
				ViewListaProveedor viewListaProveedor=new ViewListaProveedor(view);
				CtlProveedorLista ctlProveedor=new CtlProveedorLista(viewListaProveedor,conexion);
				viewListaProveedor.conectarControlador(ctlProveedor);
				
				break;
			case "ARTICULOS":
				ViewListaArticulo viewListaArticulo=new ViewListaArticulo(view);
				CtlArticuloLista ctlArticulo =new CtlArticuloLista(viewListaArticulo,conexion);
				
				viewListaArticulo.conectarControlador(ctlArticulo);
				
				break;
			case "AGREGARCOMPRAS":
				ViewAgregarCompras viewAgregarCompras= new ViewAgregarCompras(this.view);
				CtlAgregarCompras ctlAgregarCompra=new CtlAgregarCompras(viewAgregarCompras,conexion);
				
				viewAgregarCompras.setVisible(true);
				viewAgregarCompras.dispose();
				viewAgregarCompras=null;
				ctlAgregarCompra=null;
				break;
			case "FACTURAR":
				
				ViewListaFactura vistaFacturars=new ViewListaFactura(this.view);
				CtlFacturaLista ctlFacturas=new CtlFacturaLista(vistaFacturars,conexion );
				
				vistaFacturars.setVisible(true);
			
				/*ViewFacturar vistaFacturar=new ViewFacturar(this.view);
				
				CtlFacturar ctlFacturar=new CtlFacturar(vistaFacturar,conexion );
				vistaFacturar.setVisible(true);*/
		
				
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
			case "CLIENTES":
				ViewListaClientes viewClientes=new ViewListaClientes();
				CtlClienteLista  ctlClientes=new CtlClienteLista(viewClientes,conexion);
				break;
			case "BUSCARFACTURAS":
				
				ViewFacturas viewBuscarFacturas = new ViewFacturas(this.view);
				CtlFacturas cltBuscarFacturas= new CtlFacturas(viewBuscarFacturas,conexion);
				break;
				
			case "LISTAFACTURASCOMPRA":
				ViewListaFacturasCompra viewFacturasCompra=new ViewListaFacturasCompra(this.view);
				CtlFacturasCompra ctlFacturasCompra=new CtlFacturasCompra(viewFacturasCompra,conexion);
				viewFacturasCompra.dispose();
				viewFacturasCompra=null;
				ctlFacturasCompra=null;
				break;
		}
		
	}

}
