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


import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Conexion;
import Modelo.DetalleFactura;

import Modelo.Factura;

import Modelo.FacturaDao;
import View.ViewFacturar;

public class CtlFacturar  implements ActionListener, MouseListener, TableModelListener, WindowListener, KeyListener  {
	private ViewFacturar view;
	private Factura myFactura=new Factura();
	private FacturaDao facturaDao=new FacturaDao();
	private ClienteDao clienteDao=new ClienteDao();
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private Cliente myCliente=null;
	private Conexion conexion=null;
	
	public CtlFacturar(ViewFacturar v,Conexion conn){
		view=v;
		view.conectarContralador(this);
		conexion=conn;		
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		view.getModeloTabla().agregarDetalle();
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		//view.setVisible(true);
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		JOptionPane.showMessageDialog(view, "paso de celdas");
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
		
		JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(e.getType()){
		
		case TableModelEvent.HEADER_ROW:
			
		break;
		
			case TableModelEvent.UPDATE:
				
				//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModeloTabla().getValueAt(row, 0);
					this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					//JOptionPane.showMessageDialog(view, myArticulo);
					if(myArticulo!=null){
						this.view.getModeloTabla().setArticulo(myArticulo, row);
						//this.view.getModelo().getDetalle(row).setCantidad(1);
						boolean toggle = false;
						boolean extend = false;
						this.view.geTableDetalle().requestFocus();
							
						this.view.geTableDetalle().changeSelection(row,colum+3, toggle, extend);
							
						calcularTotal(this.view.getModeloTabla().getDetalle(row));
						this.view.getModeloTabla().agregarDetalle();
					}else{
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
						this.view.getModeloTabla().getDetalle(row).getArticulo().setId(-1);
					}
					
					
				}
				if(colum==3){
					calcularTotal(this.view.getModeloTabla().getDetalle(row));
					boolean toggle = false;
					boolean extend = false;
					this.view.geTableDetalle().requestFocus();
					
					this.view.geTableDetalle().changeSelection(row,colum+1, toggle, extend);
					this.view.getModeloTabla().agregarDetalle();
					
					
					
					this.view.geTableDetalle().requestFocus();
						
					this.view.geTableDetalle().changeSelection(row,colum+3, false, false);
				}
				if(colum==6){
					JOptionPane.showMessageDialog(view, "Modifico el Descuento "+this.view.getModeloTabla().getDetalle(row).getDescuento());
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
	
	for(int x=0; x<this.view.getModeloTabla().getDetalles().size();x++){
		
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
			myFactura.getDetalles().add(detalle);
			
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
			myFactura.getDetalles().add(detalle);
			
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
	}
	
	private void salir(){
		this.view.setVisible(false);
		
	}
	private void guardar(){
		
	}
	private void cobrar(){
		
	}
	private void buscarArticulo(){
		
	}
	
	private void buscarCliente(){
		
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
