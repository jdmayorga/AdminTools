package Controlador;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;

import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.DetalleFactura;
import Modelo.DetalleFacturaProveedor;
import Modelo.Factura;
import Modelo.FacturaCompra;
import Modelo.FacturaDao;
import View.ViewFacturar;

public class CtlFacturar extends KeyAdapter implements ActionListener, MouseListener, TableModelListener,KeyEventDispatcher, WindowListener  {
	private ViewFacturar view;
	private Factura myFactura=new Factura();
	private FacturaDao facturaDao=new FacturaDao();
	private ClienteDao clienteDao=new ClienteDao();
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private Cliente myCliente=null;
	
	public CtlFacturar(ViewFacturar v,DataSource poolConexion){
		view=v;
		view.conectarContralador(this);
				
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		view.getModeloTabla().agregarDetalle();
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(poolConexion);
		//view.setVisible(true);
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
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
		
		
		switch(e.getType()){
		
		case	TableModelEvent.ALL_COLUMNS:
				JOptionPane.showMessageDialog(view, "se insertar una celda");
			break;
		
			case TableModelEvent.UPDATE:
				
				//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModeloTabla().getValueAt(row, 0);
					this.myArticulo=this.myArticuloDao.buscarArticuloId(identificador);
					JOptionPane.showMessageDialog(view, myArticulo);
					if(myArticulo!=null){
						this.view.getModeloTabla().setArticulo(myArticulo, row);
						//this.view.getModelo().getDetalle(row).setCantidad(1);
						boolean toggle = false;
						boolean extend = false;
						this.view.geTableDetalle().requestFocus();
							
						this.view.geTableDetalle().changeSelection(row,colum+3, toggle, extend);
							
						calcularTotal(this.view.getModeloTabla().getDetalle(row));
					}else{
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
					}
					
					
				}
				/*if(colum==2){
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
				
				*/
			break;
		}
		
	}
	
public void calcularTotal(DetalleFactura detalle){
		
		if(detalle.getCantidad()!=0 && detalle.getArticulo().getPrecioVenta()!=0){
			//se obtien la cantidad y el precio de compra por unidad
			double cantidad=detalle.getCantidad();
			double precioCompra=detalle.getArticulo().getPrecioVenta();
			
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
			this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto());
			this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal());
			
			//se establece en la y el impuesto en el item de la vista
			detalle.setImpuesto(impuesto2.doubleValue());
			detalle.setTotal(totalItem);
			myFactura.getDetalles().add(detalle);
			
		
			
			//this.view.getModelo().fireTableDataChanged();
		}
	}

	

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
			
		/*if(e.getKeyCode()==KeyEvent.VK_F1){
			JOptionPane.showMessageDialog(view,"Se presiono la tecla f1 "+ e.getKeyCode());
			
		}*/
		//JOptionPane.showMessageDialog(view,"Se presiono la tecla f1 "+ e.getID());
		
		if(e.getID() == KeyEvent.KEY_PRESSED){
			JOptionPane.showMessageDialog(view,"Se presiono la tecla f1 "+ e.getKeyCode()+" ,componente:"+e.getSource());
		}
		
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(view, e);
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
