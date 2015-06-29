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
import Modelo.Conexion;
import Modelo.DetalleFacturaProveedor;
import Modelo.Factura;
import Modelo.Requisicion;
import View.ViewRequisicion;

public class CtlRequisicion implements ActionListener, MouseListener, TableModelListener, KeyListener   {
	private ViewRequisicion view=null;
	private Conexion conexion=null;
	private Requisicion myRequisicion=null;
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	
	public CtlRequisicion(ViewRequisicion v,Conexion conn){
		view=v;
		conexion=conn;
		myRequisicion=new Requisicion();
		
		view.getModelo().agregarDetalle();
		myArticuloDao=new ArticuloDao(conexion);
		view.conectarContralador(this);
		
		
		
		view.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
		int colum=e.getColumn();
		int row=e.getFirstRow();
		
		
		switch(e.getType()){
		
			case TableModelEvent.UPDATE:
				
				
				//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModelo().getValueAt(row, 0);
			        
			        myArticulo=this.view.getModelo().getDetalle(row).getArticulo();
					//myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					
					//se ingreso un codigo de barra y si el articulo en la bd 
			        if(myArticulo.getId()==-2){
						String cod=this.view.getModelo().getDetalle(row).getArticulo().getCodBarra().get(0).getCodigoBarra();
						this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(cod);
						
					}else{//sino se ingreso un codigo de barra se busca por id de articulo
						this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					}
					
			        
					if(myArticulo!=null){
						this.view.getModelo().setArticulo(myArticulo, row);
						//this.view.getModelo().getDetalle(row).setCantidad(1);
						boolean toggle = false;
						boolean extend = false;
						this.view.getTablaArticulos().requestFocus();
							
						this.view.getTablaArticulos().changeSelection(row,colum+2, toggle, extend);
							
						//calcularTotales();
						
						//se agrega otra fila en la tabla
						//this.view.getModelo().agregarDetalle();
					}else{
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
						
						//sino se encuentra se estable un id de -1 para que sea eliminado el articulo en la tabla
						this.view.getModelo().getDetalle(row).getArticulo().setId(-1);
						
						//se agrega la nueva fila de la tabla
						//this.view.getModelo().agregarDetalle();
						
						// se vuelve a calcular los totales
						//calcularTotales();
					}
					
					
				}
				if(colum==2){
					calcularTotales();
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row,colum+1, toggle, extend);
					
					//se agrega la nueva fila de la tabla
					//this.view.getModelo().agregarDetalle();
					//calcularTotales();
				}
				if(colum==3){
					calcularTotales();
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
					//se agrega la nueva fila de la tabla
					//this.view.getModelo().agregarDetalle();
						
					this.view.getTablaArticulos().changeSelection(row+1,0, toggle, extend);	
					//calcularTotales();
				}
				
				//se agrega la nueva fila de la tabla
				//this.view.getModelo().agregarDetalle();
			break;
		}
		
		
		
	}
	
public void calcularTotales(){
		
		//se establecen los totales en cero
		this.myRequisicion.resetTotales();
		
		//se recoren los detalles de la factura
		for(int x=0; x<this.view.getModelo().getDetalles().size();x++){
			
			//se obtiene cada detalle por separado de la factura
			DetalleFacturaProveedor detalle=this.view.getModelo().getDetalle(x);
			
			
			if(detalle.getArticulo().getId()!=-1)//si el detalle es valido
				//if(detalle.getCantidad()!=0 && detalle.getPrecioCompra()!=0)
				if(detalle.getCantidad().doubleValue()!=0 && detalle.getPrecioCompra().doubleValue()!=0){
					
					
					
					//se obtien la cantidad y el precio de compra por unidad
					BigDecimal cantidad=detalle.getCantidad();
					BigDecimal precioCompra=detalle.getPrecioCompra();
					
					//se calcula el total del item
					BigDecimal totalItem=cantidad.multiply(precioCompra);
					
					
					//se estable el total y impuesto en el modelo
					myRequisicion.setTotal(totalItem);
					
					detalle.setTotal(totalItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
					
					//se establece el total e impuesto en el vista
					this.view.getTxtTotal().setText(""+myRequisicion.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
					
					//se agrega la nueva fila de la tabla
					this.view.getModelo().agregarDetalle();
					
				
					
					//this.view.getModelo().fireTableDataChanged();
				}//fin del if
			
		}//fin del for
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
