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





import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Modelo.AbstractJasperReports;
import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.Cliente; 
import Modelo.ClienteDao;
import Modelo.Conexion;
import Modelo.DetalleFactura;
import Modelo.Factura;
import Modelo.FacturaDao;
import View.TablaModeloFactura;
import View.TablaModeloMarca;
import View.ViewCambioPago;
import View.ViewFacturar;
import View.ViewListaArticulo;
import View.ViewListaClientes;
import View.ViewListaFactura;

public class CtlFacturar  implements ActionListener, MouseListener, TableModelListener, WindowListener, KeyListener  {
	
	private ViewFacturar view;
	private Factura myFactura=null;
	private FacturaDao facturaDao=null;//=new FacturaDao();
	private ClienteDao clienteDao=null;//=new ClienteDao();
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private Cliente myCliente=null;
	private Conexion conexion=null;
	private int filaPulsada=0;
	private boolean resultado=false;
	//private ViewListaArticulo viewListaArticulo=null;
	//private CtlArticuloBuscar ctlArticulo=null;
	
	public CtlFacturar(ViewFacturar v,Conexion conn){
		view=v;
		view.conectarContralador(this);
		conexion=conn;	
		//se inicializan atributos de la factura
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		clienteDao=new ClienteDao(conexion);
		facturaDao=new FacturaDao(conexion);
		
		this.setEmptyView();
		/*view.getModeloTabla().agregarDetalle();
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		clienteDao=new ClienteDao(conexion);
		facturaDao=new FacturaDao(conexion);
		
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		//view.setVisible(true);
		this.view.getTxtIdcliente().setText("1");;
		this.view.getTxtNombrecliente().setText("Cliente Normal");
		
		this.view.getTxtBuscar().requestFocusInWindow();*/
		
		//this.view.setVisible(true);
		//la ventana buscar articulo y su controlador
		//viewListaArticulo=new ViewListaArticulo(this.view);
		//view.getTxtBuscar().requestFocusInWindow();
		//ctlArticulo =new CtlArticuloBuscar(viewListaArticulo,conexion);
		//viewListaArticulo.conectarControladorBuscar(ctlArticulo);
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		//JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(comando){
		case "BUSCARARTICULO2":
				if(myArticulo!=null){
					this.view.getModeloTabla().setArticulo(myArticulo);
					//this.view.getModelo().getDetalle(row).setCantidad(1);
					
					//calcularTotal(this.view.getModeloTabla().getDetalle(row));
					calcularTotales();
					this.view.getModeloTabla().agregarDetalle();
					view.getTxtArticulo().setText("");
					view.getTxtPrecio().setText("");
					view.getTxtBuscar().setText("");
					selectRowInset();
				}
			break;
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
		
		
		
			case TableModelEvent.UPDATE:
				
				//Se recoge el id de la fila marcada
		        int identificador=0; 
				
				//se ingreso un id o codigo de barra en la tabla
				if(colum==0){
					identificador=(int)this.view.getModeloTabla().getValueAt(row, 0);
			        myArticulo=this.view.getModeloTabla().getDetalle(row).getArticulo();
			        
					
			        //se ingreso un codigo de barra y si el articulo en la bd 
			        if(myArticulo.getId()==-2){
						String cod=this.view.getModeloTabla().getDetalle(row).getArticulo().getCodBarra().get(0).getCodigoBarra();
						this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(cod);
						
					}else{//sino se ingreso un codigo de barra se busca por id de articulo
						this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					}
					
					//si se encuentra  el articulo por codigo de barra o por id se calcula los totales y se agrega 
					if(myArticulo!=null){
						
						//se estable en articulo en la tabla
						this.view.getModeloTabla().setArticulo(myArticulo, row);
						//se calcula los totales
						calcularTotales();
						
						//se agrega otra fila en la tabla
						this.view.getModeloTabla().agregarDetalle();
						
					}else{//si no se encuentra
						
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
						//sino se encuentra se estable un id de -1 para que sea eliminado el articulo en la tabla
						this.view.getModeloTabla().getDetalle(row).getArticulo().setId(-1);
						
						//se agrega la nueva fila de la tabla
						this.view.getModeloTabla().agregarDetalle();
						
						// se vuelve a calcular los totales
						calcularTotales();
					}
					
					
					
					
					
				}
				
				//se cambia la cantidad en la tabla
				if(colum==3){
					
					calcularTotales();
					/*boolean toggle = false;
					boolean extend = false;
					this.view.geTableDetalle().requestFocus();
					
					this.view.geTableDetalle().changeSelection(row,colum+1, toggle, extend);
					
				
					this.view.getModeloTabla().agregarDetalle();
					
					
					
					this.view.geTableDetalle().requestFocus();
						
					this.view.geTableDetalle().changeSelection(row,colum+3, false, false);*/
				}
				
				//se agrego un descuento a la tabla
				if(colum==6){
					calcularTotales();
					//JOptionPane.showMessageDialog(view, "Modifico el Descuento "+this.view.getModeloTabla().getDetalle(row).getDescuentoItem().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
				}
				
				
			break;
		}
		
	}
	
	public boolean esValido(Character caracter)
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
				if(porcentaImpuesto.intValue()==15){
					myFactura.setTotalImpuesto(impuestoItem);
				}
				if(porcentaImpuesto.intValue()==18){
					myFactura.setTotalImpuesto18(impuestoItem);
				}
				myFactura.setSubTotal(totalsiniva);
				//myFactura.getDetalles().add(detalle);
				myFactura.setTotalDescuento(detalle.getDescuentoItem());
				
				detalle.setSubTotal(totalsiniva.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				detalle.setImpuesto(impuestoItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				//myFactura.getDetalles()
				
				//se establece en la y el impuesto en el item de la vista
				//detalle.setImpuesto(impuesto2.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				detalle.setTotal(totalItem.setScale(0, BigDecimal.ROUND_HALF_EVEN));
				
				//se establece el total e impuesto en el vista
				this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtImpuesto18().setText(""+myFactura.getTotalImpuesto18().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				this.view.getTxtDescuento().setText(""+myFactura.getTotalDescuento().setScale(2, BigDecimal.ROUND_HALF_EVEN));
				
				view.getModeloTabla().fireTableDataChanged();
				this.selectRowInset();
				
				
			
				
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
		
		/*if(e.getComponent()==this.view.getTxtBuscar()){
			//JOptionPane.showMessageDialog(view, "2");
			JOptionPane.showMessageDialog(view, view.getTxtBuscar().getText());
			this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText());
			
			JOptionPane.showMessageDialog(view, myArticulo);
			if(myArticulo!=null){
				view.getTxtArticulo().setText(myArticulo.getArticulo());
				view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
				
			}
		}*/
		
		//Recoger qu� fila se ha pulsadao en la tabla
		filaPulsada = this.view.geTableDetalle().getSelectedRow();
		if(e.getKeyCode()==KeyEvent.VK_F1){
			buscarArticulo();
		}else
			if(e.getKeyCode()==KeyEvent.VK_F2){
				buscarCliente();
			}else
				if(e.getKeyCode()==KeyEvent.VK_F3){
					cobrar();
				}else
					if(e.getKeyCode()==KeyEvent.VK_F4){
						guardar();
					}else
						if(e.getKeyCode()==KeyEvent.VK_F5){
							ViewListaFactura vistaFacturars=new ViewListaFactura(this.view);
							CtlFacturaLista ctlFacturas=new CtlFacturaLista(vistaFacturars,conexion );
							vistaFacturars.dispose();
							ctlFacturas=null;
						}else
						if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
							salir();
						}else
							if(e.getKeyCode()==KeyEvent.VK_DELETE){
								 
								 if(filaPulsada>=0){
									 this.view.getModeloTabla().eliminarDetalle(filaPulsada);
									 this.calcularTotales();
								 }
							}
								 
							
								
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
		filaPulsada = this.view.geTableDetalle().getSelectedRow();
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
		//if(txtCampo.getText().trim().length()==0)
		if(e.getComponent()==this.view.getTxtBuscar()&&view.getTxtBuscar().getText().trim().length()!=0){
			//JOptionPane.showMessageDialog(view, "2");
			//JOptionPane.showMessageDialog(view, view.getTxtBuscar().getText());
			this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText());
			
			//JOptionPane.showMessageDialog(view, myArticulo);
			if(myArticulo!=null){
				view.getTxtArticulo().setText(myArticulo.getArticulo());
				view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
				
			}
			else{
				myArticulo=null;
				view.getTxtArticulo().setText("");
				view.getTxtPrecio().setText("");
			}
		}
		else{
			myArticulo=null;
			view.getTxtArticulo().setText("");
			view.getTxtPrecio().setText("");
		}
		if(caracter=='+'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModeloTabla().masCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		if(caracter=='-'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModeloTabla().restarCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		
		
	}
		
	
	
	private void salir(){
		//facturaDao.desconectarBD();
		//this.clienteDao.desconectarBD();
		//this.myArticuloDao.desconectarBD();
		//this.myFactura.setIdFactura(-1);
		this.view.setVisible(false);
		
		
	}
	private void guardar(){
		
		setFactura();
		facturaDao.registrarFacturaTemp(myFactura);
		myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
		resultado=true;
		this.view.setVisible(false);
		
	}
	private void actualizar() {
		// TODO Auto-generated method stub
		setFactura();
		facturaDao.actualizarFacturaTemp(myFactura);
		this.view.setVisible(false);
		
	}
	private void cobrar(){
		
		
		//se muestra la vista para cobrar y introducir el cambio
		ViewCambioPago viewPago=new ViewCambioPago(this.view);
		CtlCambioPago ctlPago=new CtlCambioPago(viewPago,myFactura.getTotal());
		//se muestra y ventana del cobro y se devuelve un resultado del cobro
		boolean resulPago=ctlPago.pagar();
		
		//se procede a verificar si se cobro
		if(resulPago)
		{
			//si la forma de pago fue en efectivo
			if(ctlPago.getFormaPago()==1){
				myFactura.setPago(ctlPago.getEfectivo());
				myFactura.setCambio(ctlPago.getCambio());
				myFactura.setTipoPago(1);
			}
			//si la forma de pago fue con tarjeta de credito o debito
			if(ctlPago.getFormaPago()==2){
				myFactura.setPago(myFactura.getTotal());
				myFactura.setCambio(new BigDecimal(00));
				myFactura.setTipoPago(2);
				myFactura.setObservacion(ctlPago.getRefencia());
			}
			setFactura();
			boolean resul=facturaDao.registrarFactura(myFactura);
				
			if(resul){
				myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
				
					try {
						/*this.view.setVisible(false);
						this.view.dispose();*/
						//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul.jasper",myFactura.getIdFactura() );
						AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 1, myFactura.getIdFactura());
						//AbstractJasperReports.showViewer();
						AbstractJasperReports.imprimierFactura();
						//myFactura=null;
						setEmptyView();
						//myFactura.
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
		
	}
	private void buscarArticulo(){
	
		//se llama el metodo que mostrar la ventana para buscar el articulo
		ViewListaArticulo viewListaArticulo=new ViewListaArticulo(view);
		CtlArticuloBuscar ctlArticulo=new CtlArticuloBuscar(viewListaArticulo,conexion);
		
		viewListaArticulo.pack();
		ctlArticulo.view.getTxtBuscar().setText("");
		ctlArticulo.view.getTxtBuscar().selectAll();
		//ctlArticulo.view.getTxtBuscar().requestFocus(true);
		//ctlArticulo.view.getTxtBuscar().selectAll();
		view.getTxtBuscar().requestFocusInWindow();
		viewListaArticulo.conectarControladorBuscar(ctlArticulo);
		Articulo myArticulo1=ctlArticulo.buscarArticulo(view);
		
		//JOptionPane.showMessageDialog(view, myArticulo1);
		//se comprueba si le regreso un articulo valido
		if(myArticulo1!=null && myArticulo1.getId()!=-1){
			this.view.getModeloTabla().setArticulo(myArticulo1);
			//this.view.getModelo().getDetalle(row).setCantidad(1);
			
			//calcularTotal(this.view.getModeloTabla().getDetalle(row));
			calcularTotales();
			this.view.getModeloTabla().agregarDetalle();
			
			selectRowInset();
		}
		
		myArticulo=null;
		viewListaArticulo.dispose();
		ctlArticulo=null;
		
	}
	private void setEmptyView(){
		//se estable la tabla de detalles vacia
		view.getModeloTabla().setEmptyDetalles();
		
		//se agrega una fila vacia a la tabla detalle
		view.getModeloTabla().agregarDetalle();
		
		
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		
		//se estable un cliente generico para la factura
		this.view.getTxtIdcliente().setText("1");;
		this.view.getTxtNombrecliente().setText("Cliente Normal");
		
		
		this.view.getTxtArticulo().setText("");
		this.view.getTxtBuscar().setText("");
		this.view.getTxtDescuento().setText("");
		this.view.getTxtImpuesto().setText("0.00");
		this.view.getTxtImpuesto18().setText("0.00");
		this.view.getTxtPrecio().setText("0.00");
		this.view.getTxtSubtotal().setText("0.00");
		this.view.getTxtTotal().setText("0.00");
		
		//se estable el focus de la view en la caja de texto buscar
		this.view.getTxtBuscar().requestFocusInWindow();
		
	}
	private void buscarCliente(){
		//se crea la vista para buscar los cliente
		ViewListaClientes viewListaCliente=new ViewListaClientes (this.view);
		
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

	
	
	private void selectRowInset(){
		
		int row = this.view.geTableDetalle().getRowCount () - 2;
	    int col = 1;
	    boolean toggle = false;
	    boolean extend = false;
	    this.view.geTableDetalle().changeSelection(row, 0, toggle, extend);
	    this.view.geTableDetalle().changeSelection(row, col, toggle, extend);
	    this.view.geTableDetalle().addColumnSelectionInterval(0, 7);
		
		/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
		/*int row =  this.view.geTableDetalle().getRowCount () - 2;
		JOptionPane.showMessageDialog(view, row);
		/Rectangle rect = this.view.geTableDetalle().getCellRect(row, 0, true);
		this.view.geTableDetalle().scrollRectToVisible(rect);
		this.view.geTableDetalle().clearSelection();*/
		//this.view.geTableDetalle().setRowSelectionInterval(row, row);
		//view.geTableDetalle().setRowSelectionInterval(row, row);
		//view.geTableDetalle().clearSelection();
		//view.geTableDetalle().addRowSelectionInterval(row,row);
		//TablaModeloFactura modelo = (TablaModeloFactura)this.view.geTableDetalle().getModel();
		//modelo.fireTableDataChanged();
		//this.view.getModeloTabla().fireTableDataChanged();
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
		//this.myFactura.setIdFactura(-1);
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


	public boolean getAccion() {
		view.setVisible(true);
		return resultado;
	}


	public void viewFactura(Factura f) {
		// TODO Auto-generated method stub
		this.myFactura=f;
		cargarFacturaView();
		this.view.getPanelAcciones().setVisible(false);
		this.view.setVisible(true);
	}


	public Factura getFactura() {
		// TODO Auto-generated method stub
		return this.myFactura;
	}

}
