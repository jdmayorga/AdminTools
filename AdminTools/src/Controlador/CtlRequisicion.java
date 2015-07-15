package Controlador;

import java.awt.Rectangle;
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
import Modelo.KardexDao;
import Modelo.Requisicion;
import View.TablaModeloMarca;
import View.ViewRequisicion;

public class CtlRequisicion implements ActionListener, MouseListener, TableModelListener, KeyListener   {
	private ViewRequisicion view=null;
	private Conexion conexion=null;
	private Requisicion myRequisicion=null;
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private int filaPulsada=0;
	private KardexDao myKardex;
	
	public CtlRequisicion(ViewRequisicion v,Conexion conn){
		view=v;
		conexion=conn;
		myRequisicion=new Requisicion();
		
		view.getModelo().agregarDetalle();
		myArticuloDao=new ArticuloDao(conexion);
		view.conectarContralador(this);
		myKardex=new KardexDao(conexion);
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
		
		//comprobamos si hay algo que buscar
		if(e.getComponent()==this.view.getTxtBuscar()&&view.getTxtBuscar().getText().trim().length()!=0){
			//JOptionPane.showMessageDialog(view, "2");
			//se busca el articulo y se asigna el resultado en el objeto articulo
			this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText());
			
			//se comprueba si la busqueda devolvio un articulo
			if(myArticulo!=null){
				view.getTxtArticulo().setText(myArticulo.getArticulo());
				view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
				
			}else{//si no se encontro ningun articulo se elemina la busqueda anterior
				myArticulo=null;
				view.getTxtArticulo().setText("");
				view.getTxtPrecio().setText("");
			}
		}else{///sino hay nada que buscar se elemina la vista y el articulo
			myArticulo=null;
			view.getTxtArticulo().setText("");
			view.getTxtPrecio().setText("");
		}
		
		//Recoger qué fila se ha pulsadao en la tabla
		filaPulsada = this.view.getTablaArticulos().getSelectedRow();
		char caracter = e.getKeyChar();
		
		
		//para quitar los simnos mas o numero que ingrese en la busqueda
		if(e.getComponent()==this.view.getTxtBuscar()){
			Character caracter1 = new Character(e.getKeyChar());
	        if (!esValido(caracter1))
	        {
	           String texto = "";
	           for (int i = 0; i < view.getTxtBuscar().getText().length(); i++)
	                if (esValido(new Character(view.getTxtBuscar().getText().charAt(i))))
	                    texto += view.getTxtBuscar().getText().charAt(i);
	           			view.getTxtBuscar().setText(texto);
	                //view.getToolkit().beep();
	        }
		}
		
		if(caracter=='+'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModelo().masCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		if(caracter=='-'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModelo().restarCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		
	}

	private boolean esValido(Character caracter)
    {
        char c = caracter.charValue();
        if ( !(Character.isLetter(c) //si es letra
                || c == ' ' //o un espacio
                || c == 8 //o backspace
                || (Character.isDigit(c))
            ))
            return false;
        else
            return true;
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
						
						
						//se consigue el articulo en la bd
						this.view.getModelo().setArticulo(myArticulo, row);
						
						//se consiguie el precio de costo 
						this.view.getModelo().setPricioCompra(myKardex.buscarKardexPrecio(myArticulo.getId(), 1), row);
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
				}
				
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
		String comando=e.getActionCommand();
		
		switch (comando){
		case "BUSCARARTICULO2":
			if(myArticulo!=null){
				this.view.getModelo().setArticulo(myArticulo);
				
				
				this.view.getModelo().agregarDetalle();
				view.getTxtArticulo().setText("");
				view.getTxtPrecio().setText("");
				view.getTxtBuscar().setText("");
				
				
				int row =  this.view.getTablaArticulos().getRowCount () - 2;
				//this.view.getModelo().getDetalle(row).setCantidad(1);
				//JOptionPane.showMessageDialog(view, row);
				this.view.getModelo().setPricioCompra(myKardex.buscarKardexPrecio(myArticulo.getId(), 1), row);
				calcularTotales();
				selectRowInset();
			}
			break;
		}
		
	}
	private void selectRowInset(){
		/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
		int row =  this.view.getTablaArticulos().getRowCount () - 2;
		Rectangle rect = this.view.getTablaArticulos().getCellRect(row, 0, true);
		this.view.getTablaArticulos().scrollRectToVisible(rect);
		this.view.getTablaArticulos().clearSelection();
		this.view.getTablaArticulos().setRowSelectionInterval(row, row);
		TablaModeloMarca modelo = (TablaModeloMarca)this.view.getTablaArticulos().getModel();
		modelo.fireTableDataChanged();
	}

}
