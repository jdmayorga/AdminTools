package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.Conexion;
import Modelo.DetalleFacturaProveedor;
import Modelo.FacturaCompra;
import Modelo.FacturaCompraDao;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import View.ViewAgregarCompras;

public class CtlAgregarCompras implements ActionListener,MouseListener,TableModelListener {
	public ViewAgregarCompras view;
	private Conexion conexion=null;
	private ArticuloDao myArticuloDao;
	private Articulo myArticulo;
	private Proveedor myProveedor;
	private ProveedorDao myProveedorDao;
	private FacturaCompra myFactura;
	private FacturaCompraDao myFacturaDao;
	 
	public CtlAgregarCompras(ViewAgregarCompras view,Conexion conn){
		//se asigna las variales recibida a las variables locales
		conexion=conn;
		this.view=view;
		this.view.conectarControlador(this);
		
		myFactura=new FacturaCompra();
		//se crea las clases para consultar a la BD
		this.myArticuloDao=new ArticuloDao(conexion);
		this.myProveedorDao=new ProveedorDao(conexion);
		this.myFacturaDao=new FacturaCompraDao(conexion);
		// view.getTablaArticulos().getModel().addTableModelListener(this);
		///this.view.getTablaArticulos().getSelectionModel().set
		
		//se llena la tabla de articulos llamando a este metodo 
		agregarNuevoDetalle();
		//this.view.getTxtFechaIngreso().setText(myFacturaDao.getFechaSistema());
		this.view.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		switch(comando){
			case "BUSCARPROVEEDOR":
				//String stIdProveedor=this.view.getTxtIdProveedor().getText();
				//int inIdProveedor=Integer.parseInt(stIdProveedor);
				myProveedor=myProveedorDao.buscarPro(Integer.parseInt(this.view.getTxtIdProveedor().getText()));
				if(myProveedor!=null){
					this.view.getTxtNombreproveedor().setText(myProveedor.getNombre());
					this.view.gettxtTelefonoProveedor().setText(myProveedor.getTelefono());
					//JOptionPane.showMessageDialog(view, "Intento Buscar Proveedor");
					this.myFactura.setProveedor(myProveedor);
				}
				else{
					JOptionPane.showMessageDialog(view, "No se encuentro el proveedor");
					this.view.getTxtNombreproveedor().setText("");
					this.view.gettxtTelefonoProveedor().setText("");
				}
				break;
			case "GUARDARCOMPRA":
				
				if(this.view.getDateCompra().isValid()==false){
					//JOptionPane.showMessageDialog(view, this.view.getDateCompra().isValid());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(this.view.getDateCompra().getDate());
					myFactura.setFechaCompra(date);
					//JOptionPane.showMessageDialog(null,date);
				}else{
					JOptionPane.showMessageDialog(view,"Ingrese la fecha de la compra");
					break;
				}
				
				//se obtine el numero de factura de compra para incoorporarla al modelo
				myFactura.setIdFactura(this.view.getTxtNofactura().getText());
				
				//si la factura el al credito se completa la fecha de vencimiento de la factura 
				if(myFactura.getTipoFactura()==2){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(this.view.getDateVencFactura().getDate());
					myFactura.setFechaVencimento(date);
				}
				//JOptionPane.showMessageDialog(view, myFactura);
				boolean result=this.myFacturaDao.registrarFactura(myFactura);
				if(result){
					JOptionPane.showMessageDialog(view,"Se guarda la factura");
				}else{
					
				}
	
				
				
				break;
			case "CREDITO":
				this.view.getDateVencFactura().setEnabled(true);
				myFactura.setTipoFactura(2);
				break;
			case "CONTADO":
				this.view.getDateVencFactura().setEnabled(false);
				myFactura.setTipoFactura(1);
				//this.view.getDateCompra().cleanup();
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
		
		//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow()+", "+e.getType());
		/*if (e.getType() == TableModelEvent.UPDATE){
			JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
			
		}*/
		int colum=e.getColumn();
		int row=e.getFirstRow();
		
		
		switch(e.getType()){
		
			case TableModelEvent.UPDATE:
				
				//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModelo().getValueAt(row, 0);
					this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					this.view.getModelo().setArticulo(myArticulo, row);
					//this.view.getModelo().getDetalle(row).setCantidad(1);
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row,colum+2, toggle, extend);
						
					calcularTotal(this.view.getModelo().getDetalle(row));
					
				}
				if(colum==2){
					calcularTotal(this.view.getModelo().getDetalle(row));
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row,colum+1, toggle, extend);
				}
				if(colum==3){
					calcularTotal(this.view.getModelo().getDetalle(row));
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row+1,0, toggle, extend);				
				}
				
				
			break;
		}
		
		
		
	}
	public void agregarNuevoDetalle(){
		for(int x=0;x<50;x++){
			DetalleFacturaProveedor uno =new DetalleFacturaProveedor();
			this.view.getModelo().agregarDetalle(uno);
		}
	}
	
	public void calcularTotal(DetalleFacturaProveedor detalle){
		
		if(detalle.getCantidad()!=0 && detalle.getPrecioCompra()!=0){
			//se obtien la cantidad y el precio de compra por unidad
			double cantidad=detalle.getCantidad();
			double precioCompra=detalle.getPrecioCompra();
			
			//se obtiene el impuesto del articulo 
			double porcentaImpuesto =((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1;
			
			//se calcula el total del item
			double totalItem=cantidad*precioCompra;
			//se calcula el total sin  el impuesto;
			double totalsiniva=(totalItem)/(porcentaImpuesto);
			
			//se redondea el total
			BigDecimal total=new BigDecimal(totalItem);
			BigDecimal total2= total.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			totalItem=total2.doubleValue();
			
			//se calcula el total de impuesto del item
			double impuestoItem=totalItem-totalsiniva;
			
			//se redondea el total del impuesto del item
			BigDecimal impuesto=new BigDecimal(impuestoItem);
			BigDecimal impuesto2=impuesto.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
			//se redondea el total del impuesto del item
			BigDecimal subt=new BigDecimal(totalsiniva);
			BigDecimal subtTotal=subt.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			totalsiniva=subtTotal.doubleValue();
			
			//se estable el total y impuesto en el modelo
			myFactura.setTotal(totalItem);
			myFactura.setTotalImpuesto(impuesto2.doubleValue());
			myFactura.setSubTotal(totalsiniva);
			
			//se establece el total e impuesto en el vista
			this.view.getTxtTotal().setText(""+myFactura.getTotal());
			this.view.getTxtTotalimpuesto().setText(""+myFactura.getTotalImpuesto());
			this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal());
			
			//se establece en la y el impuesto en el item de la vista
			detalle.setImpuesto(impuesto2.doubleValue());
			detalle.setTotal(totalItem);
			myFactura.getDetalles().add(detalle);
			
		
			
			//this.view.getModelo().fireTableDataChanged();
		}
	}

	
}
