package Controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;





import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Modelo.AbstractJasperReports;
import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.Cliente; 
import Modelo.ClienteDao;
import Modelo.Conexion;
import Modelo.DetalleFactura;
import Modelo.Factura;
import Modelo.FacturaDao;
import View.ViewFacturar;
import View.ViewListaArticulo;
import View.ViewListaClientes;

public class CtlFacturar  implements ActionListener, MouseListener, TableModelListener, WindowListener, KeyListener  {
	
	private ViewFacturar view;
	private Factura myFactura=new Factura();
	private FacturaDao facturaDao=null;//=new FacturaDao();
	private ClienteDao clienteDao=null;//=new ClienteDao();
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private Cliente myCliente=null;
	private Conexion conexion=null;
	private ViewListaArticulo viewListaArticulo=null;
	private CtlArticuloBuscar ctlArticulo=null;
	
	public CtlFacturar(ViewFacturar v,Conexion conn){
		view=v;
		view.conectarContralador(this);
		conexion=conn;		
		
		
		view.getModeloTabla().agregarDetalle();
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		clienteDao=new ClienteDao(conexion);
		facturaDao=new FacturaDao(conexion);
		
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		//view.setVisible(true);
		this.view.getTxtIdcliente().setText("1");;
		this.view.getTxtNombrecliente().setText("Cliente Normal");
		//la ventana buscar articulo y su controlador
		viewListaArticulo=new ViewListaArticulo();
		ctlArticulo =new CtlArticuloBuscar(viewListaArticulo,conexion);
		viewListaArticulo.conectarControladorBuscar(ctlArticulo);
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		//JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(comando){
		
		case "BUSCARCLIENTE":
			myCliente=null;
			myCliente=clienteDao.buscarCliente(Integer.parseInt(this.view.getTxtIdcliente().getText()));
			
			if(myCliente!=null){
				this.view.getTxtNombrecliente().setText(myCliente.getNombre());
			}else{
				this.view.getTxtIdcliente().setText("");
				this.view.getTxtNombrecliente().setText("");
				JOptionPane.showMessageDialog(view, "Cliente no encontrado");
			}
			
			break;
		case "ACTUALIZAR":
			this.actualizar();
			break;
		case "BUSCARARTICULO":
			this.buscarArticulo();
		break;
		
		case "CERRAR":
			this.salir();
		break;
		case "BUSCARCLIENTES":
			this.buscarCliente();
			break;
		case "COBRAR":
			this.cobrar();
			break;
		case "GUARDAR":
			this.guardar();
			break;
		
		}
		
	}
	


	private void setFactura(){
		
		//sino se ingreso un cliente en particular que coge el cliente por defecto
		if(myCliente==null){
			myCliente=new Cliente();
			myCliente.setId(Integer.parseInt(this.view.getTxtIdcliente().getText()));
			myCliente.setNombre(this.view.getTxtNombrecliente().getText());
			
		}
		
		if(this.view.getRdbtnContado().isSelected()){
			myFactura.setTipoFactura(1);
		}
		
		if(this.view.getRdbtnCredito().isSelected()){
			myFactura.setTipoFactura(2);
		}
		
		myFactura.setCliente(myCliente);
		myFactura.setDetalles(this.view.getModeloTabla().getDetalles());
		myFactura.setFecha(facturaDao.getFechaSistema());
		//JOptionPane.showMessageDialog(view, myCliente);*/
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
		int colum=e.getColumn();
		int row=e.getFirstRow();
		//JOptionPane.showMessageDialog(view, myArticulo);
		//JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(e.getType()){
		
		case TableModelEvent.HEADER_ROW:
			
		break;
		
			case TableModelEvent.UPDATE:
				
				//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModeloTabla().getValueAt(row, 0);
			        myArticulo=this.view.getModeloTabla().getDetalle(row).getArticulo();
			        //JOptionPane.showMessageDialog(view, myArticulo);
			        //this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(this.view.getModeloTabla().getDetalle(row).getArticulo().getCodBarra().get(0).getCodigoBarra());
					if(myArticulo.getId()==-2){
						String cod=this.view.getModeloTabla().getDetalle(row).getArticulo().getCodBarra().get(0).getCodigoBarra();
						this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(cod);
						
					}else{
						this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					}
					//JOptionPane.showMessageDialog(view, myArticulo);
					
					if(myArticulo!=null){
						this.view.getModeloTabla().setArticulo(myArticulo, row);
						
						calcularTotales();
						this.view.getModeloTabla().agregarDetalle();
						
						
						/*boolean toggle = false;
						boolean extend = false;
						this.view.geTableDetalle().requestFocus();
							
						this.view.geTableDetalle().changeSelection(row,colum+3, toggle, extend);*/
							
						
					}else{
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
						this.view.getModeloTabla().getDetalle(row).getArticulo().setId(-1);
						this.view.getModeloTabla().agregarDetalle();
						calcularTotales();
					}
					
					
				}
				if(colum==3){
					//calcularTotal(this.view.getModeloTabla().getDetalle(row));
					calcularTotales();
					boolean toggle = false;
					boolean extend = false;
					this.view.geTableDetalle().requestFocus();
					
					this.view.geTableDetalle().changeSelection(row,colum+1, toggle, extend);
					this.view.getModeloTabla().agregarDetalle();
					
					
					
					this.view.geTableDetalle().requestFocus();
						
					this.view.geTableDetalle().changeSelection(row,colum+3, false, false);
				}
				if(colum==6){
					calcularTotales();
					//JOptionPane.showMessageDialog(view, "Modifico el Descuento "+this.view.getModeloTabla().getDetalle(row).getDescuentoItem().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
				}
				/*if(colum==3){
					calcularTotal(this.view.getModelo().getDetalle(row));
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row+1,0, toggle, extend);				
				}
				
				*/
			break;
		}
		
	}
	
	
public void calcularTotales(){
	
	//se establecen los totales en cero
	this.myFactura.resetTotales();
	
	for(int x=0; x<this.view.getModeloTabla().getDetalles().size();x++){
		
		DetalleFactura detalle=this.view.getModeloTabla().getDetalle(x);
		
		
		if(detalle.getArticulo().getId()!=-1)
			if(detalle.getCantidad().doubleValue()!=0 && detalle.getArticulo().getPrecioVenta()!=0){
				
				
				
				//se obtien la cantidad y el precio de compra por unidad
				BigDecimal cantidad=detalle.getCantidad();
				BigDecimal precioVenta= new BigDecimal(detalle.getArticulo().getPrecioVenta());
				
				//se calcula el total del item
				BigDecimal totalItem=cantidad.multiply(precioVenta);
				
				int desc=detalle.getDescuento();
			
				if(desc==1)
				{
					BigDecimal des=totalItem.multiply(new BigDecimal(0.05));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);
					
				}else if(desc==2){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.10));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==3){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.15));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==4){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.20));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==5){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.25));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==6){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.30));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==7){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.35));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==8){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.40));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==9){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.45));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}else if(desc==10){
					BigDecimal des=totalItem.multiply(new BigDecimal(0.50));
					detalle.setDescuentoItem(des);
					totalItem=totalItem.subtract(des);	
				}
				
				
				
				//se obtiene el impuesto del articulo 
				BigDecimal porcentaImpuesto =new BigDecimal(detalle.getArticulo().getImpuestoObj().getPorcentaje());
				BigDecimal porImpuesto=new BigDecimal(0);
				porImpuesto=porcentaImpuesto.divide(new BigDecimal(100));
				porImpuesto=porImpuesto.add(new BigDecimal(1));
						//new BigDecimal(((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1);
				
				
				
				//se calcula el total sin  el impuesto;
				BigDecimal totalsiniva= new BigDecimal("0.0");
				totalsiniva=totalItem.divide(porImpuesto,2,BigDecimal.ROUND_HALF_EVEN);//.divide(porImpuesto);// (totalItem)/(porcentaImpuesto);
			
				
				//se calcula el total de impuesto del item
				BigDecimal impuestoItem=totalItem.subtract(totalsiniva);//-totalsiniva;
				
				
				
				//se estable el total y impuesto en el modelo
				myFactura.setTotal(totalItem);
				myFactura.setTotalImpuesto(impuestoItem);
				myFactura.setSubTotal(totalsiniva);
				//myFactura.getDetalles().add(detalle);
				myFactura.setTotalDescuento(detalle.getDescuentoItem());
				
				detalle.setSubTotal(totalsiniva.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				detalle.setImpuesto(impuestoItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				//myFactura.getDetalles()
				
				//se establece en la y el impuesto en el item de la vista
				//detalle.setImpuesto(impuesto2.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				detalle.setTotal(totalItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				
				//se establece el total e impuesto en el vista
				this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtDescuento().setText(""+myFactura.getTotalDescuento().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				
				
				
				
			
				
				//this.view.getModelo().fireTableDataChanged();
			}//fin del if
		
	}//fin del for
	}
	
public void calcularTotal(DetalleFactura detalle){
		
		if(detalle.getCantidad().doubleValue()!=0 && detalle.getArticulo().getPrecioVenta()!=0){
			
			//se obtien la cantidad y el precio de compra por unidad
			BigDecimal cantidad=detalle.getCantidad();
			BigDecimal precioVenta= new BigDecimal(detalle.getArticulo().getPrecioVenta());
			
			
			
			//se obtiene el impuesto del articulo 
			BigDecimal porcentaImpuesto =new BigDecimal(detalle.getArticulo().getImpuestoObj().getPorcentaje());
			BigDecimal porImpuesto=new BigDecimal(0);
			porImpuesto=porcentaImpuesto.divide(new BigDecimal(100));
			porImpuesto=porImpuesto.add(new BigDecimal(1));
					//new BigDecimal(((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1);
			
			//se calcula el total del item
			BigDecimal totalItem=cantidad.multiply(precioVenta);
			
			//se calcula el total sin  el impuesto;
			BigDecimal totalsiniva= new BigDecimal("0.0");
			totalsiniva=totalItem.divide(porImpuesto,2,BigDecimal.ROUND_HALF_EVEN);//.divide(porImpuesto);// (totalItem)/(porcentaImpuesto);
		
			
			//se calcula el total de impuesto del item
			BigDecimal impuestoItem=totalItem.subtract(totalsiniva);//-totalsiniva;
			
			
			
			//se estable el total y impuesto en el modelo
			myFactura.setTotal(totalItem);
			myFactura.setTotalImpuesto(impuestoItem);
			myFactura.setSubTotal(totalsiniva);
			//myFactura.getDetalles().add(detalle);
			
			detalle.setSubTotal(totalsiniva.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			detalle.setImpuesto(impuestoItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			//myFactura.getDetalles()
			
			//se establece en la y el impuesto en el item de la vista
			//detalle.setImpuesto(impuesto2.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			detalle.setTotal(totalItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			
			//se establece el total e impuesto en el vista
			this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			
			
			
			
		
			
			//this.view.getModelo().fireTableDataChanged();
		}
	}

	

	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getKeyCode()==KeyEvent.VK_F1){
			buscarArticulo();
		}
		if(e.getKeyCode()==KeyEvent.VK_F2){
			buscarCliente();
		}
		if(e.getKeyCode()==KeyEvent.VK_F3){
			cobrar();
		}
		if(e.getKeyCode()==KeyEvent.VK_F4){
			guardar();
		}
		if(e.getKeyCode()==KeyEvent.VK_F5){
			salir();
		}
		if(e.getKeyCode()==KeyEvent.VK_DELETE){
			 //Recoger qué fila se ha pulsadao en la tabla
			int filaPulsada = this.view.geTableDetalle().getSelectedRow();
			 if(filaPulsada>=0){
				 this.view.getModeloTabla().eliminarDetalle(filaPulsada);
				 this.calcularTotales();
			 }
			
		}
	}
	
	private void salir(){
		//facturaDao.desconectarBD();
		//this.clienteDao.desconectarBD();
		this.myArticuloDao.desconectarBD();
		this.view.setVisible(false);
		
	}
	private void guardar(){
		setFactura();
		facturaDao.registrarFacturaTemp(myFactura);
		myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
		this.view.setVisible(false);
		
	}
	private void actualizar() {
		// TODO Auto-generated method stub
		setFactura();
		facturaDao.actualizarFacturaTemp(myFactura);
		this.view.setVisible(false);
		
	}
	private void cobrar(){
		
		//JOptionPane.showInternalInputDialog(view, "Pago con", "Cobro", JOptionPane.INFORMATION_MESSAGE);
		setFactura();
		boolean resul=facturaDao.registrarFactura(myFactura);
				
		if(resul){
			myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
			
				try {
					this.view.setVisible(false);
					this.view.dispose();
					AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul.jasper",myFactura.getIdFactura() );
					//AbstractJasperReports.showViewer();
					AbstractJasperReports.imprimierFactura();
					myFactura=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}else{
			JOptionPane.showMessageDialog(view, "No se guardo la factura", "Error Base de Datos", JOptionPane.ERROR_MESSAGE);
			this.view.setVisible(false);
			this.view.dispose();
		}
		
	}
	private void buscarArticulo(){
	
		//se llama el metodo que mostrar la ventana para buscar el articulo
		ctlArticulo.view.getTxtBuscar().setText("");
		ctlArticulo.view.getTxtBuscar().selectAll();
		ctlArticulo.view.getTxtBuscar().requestFocus(true); 
		//ctlArticulo.view.getTxtBuscar().selectAll();
		Articulo myArticulo=ctlArticulo.buscarArticulo(view);
		
		//se comprueba si le regreso un articulo valido
		if(myArticulo.getArticulo()!=null && myArticulo.getId()!=-1){
			this.view.getModeloTabla().setArticulo(myArticulo);
			//this.view.getModelo().getDetalle(row).setCantidad(1);
			
			//calcularTotal(this.view.getModeloTabla().getDetalle(row));
			calcularTotales();
			this.view.getModeloTabla().agregarDetalle();
		}
		
		myArticulo=null;
		
	}
	
	private void buscarCliente(){
		//se crea la vista para buscar los cliente
		ViewListaClientes viewListaCliente=new ViewListaClientes ();
		
		CtlClienteBuscar ctlBuscarCliente=new CtlClienteBuscar(viewListaCliente,conexion);
		
		myCliente=ctlBuscarCliente.buscarCliente(view);
		//se comprueba si le regreso un articulo valido
		if(myCliente.getNombre()!=null && myCliente.getId()!=-1){
			this.view.getTxtIdcliente().setText(""+myCliente.getId());;
			this.view.getTxtNombrecliente().setText(myCliente.getNombre());
		
		}else{
			JOptionPane.showMessageDialog(view, "No se encontro el cliente");
			this.view.getTxtIdcliente().setText("1");;
			this.view.getTxtNombrecliente().setText("Cliente Normal");
		}
		viewListaCliente.dispose();
		ctlBuscarCliente=null;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//facturaDao.desconectarBD();
		//this.clienteDao.desconectarBD();
		//this.myArticuloDao.desconectarBD();
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
	
	public void cargarFacturaView(){
		
		this.view.getTxtIdcliente().setText(""+myFactura.getCliente().getId());;
		this.view.getTxtNombrecliente().setText(myFactura.getCliente().getNombre());
		
		//se establece el total e impuesto en el vista
		this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		
		this.view.getModeloTabla().setDetalles(myFactura.getDetalles());
	}


	public Factura actualizarFactura(Factura f) {
		// TODO Auto-generated method stub
		this.myFactura=f;
		cargarFacturaView();
		this.view.getBtnGuardar().setVisible(false);
		this.view.getBtnActualizar().setVisible(true);
		this.view.getModeloTabla().agregarDetalle();
		this.view.setVisible(true);
		
		return myFactura;
		
	}


	public Factura getAccion() {
		view.setVisible(true);
		return myFactura;
	}

}
