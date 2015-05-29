package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;










import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;



import javax.swing.JOptionPane;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.CodBarra;
import Modelo.Conexion;
import Modelo.Impuesto;
import Modelo.ImpuestoDao;
import Modelo.Marca;
import View.ComboBoxImpuesto;
import View.ViewCrearArticulo;
import View.ViewListaArticulo;
import View.ViewListaMarca;

public class CtlArticulo extends MouseAdapter implements ActionListener,KeyListener,  WindowListener {
	
	
	public ViewCrearArticulo view;
	private ArticuloDao myArticuloDao;
	private Articulo myArticulo=new Articulo();
	private boolean resultaOperacion=false;
	private Conexion conexion;
	private ImpuestoDao myImpuestoDao;
	
	public CtlArticulo(ViewCrearArticulo view, ArticuloDao a,Conexion conn){
		conexion=conn;
		this.view=view;
		this.myArticuloDao=a;
		cargarComboBox();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		switch (comando){
		
		case "BUSCAR":
				ViewListaMarca viewListaM=new ViewListaMarca(view);
				CtlMarcaBuscar ctl=new CtlMarcaBuscar(viewListaM,conexion);
				viewListaM.conectarControladorBusqueda(ctl);
				//se crea una marca y se llena con la busqueda que selecciona el usuario
				Marca myMarca=ctl.buscarMarca();
				
			//se compara si el usuario selecciono un marca
				if(myMarca.getMarca()!=null && myMarca.getId()!=0){
					//se pasa la marca buscada y selecciona al nuevo articulo
					myArticulo.setMarcaObj(myMarca);
					
					//se muestra el nombre de la marca en la caja de texto
					view.getTxtMarca().setText(myMarca.getMarca());
				
				}else{
					JOptionPane.showMessageDialog(this.view,"No seleciono una Marca");
				}
			break;
		case "GUARDAR":
			//filtrar los codigos de barra del articulo
			if(this.view.getModeloCodBarra().getSize()==0){
				int resul=JOptionPane.showConfirmDialog(view, "Desea guardar un articulos sin condigos de barra?");
				if(resul==0){
					guardarArticulo();
					this.view.dispose();
				}
			}else{
				guardarArticulo();
				this.view.dispose();
			}
			
			
			break;
		case "NUEVOCODIGO":
			CodBarra newCodigo=new CodBarra();
			newCodigo.setCodigoBarra(this.view.getTxtCodigo().getText());
			newCodigo.setCodArticulo(0);
			this.view.getModeloCodBarra().addCodBarra(newCodigo);
			this.view.getTxtCodigo().setText("");
			break;
		case "ELIMINARCODIGO":
				//se obtiene el index del codigo selecionado
				int indexCodigoSelecionado=this.view.getListCodigos().getSelectedIndex();
				
				//con el index del codigo seleccionado se obtiene el objeto codigo selecionado			
				CodBarra cod= this.view.getModeloCodBarra().getCodBarra(indexCodigoSelecionado);
				
				//se pregunta si en verdad se quiere borrar el codigo de bgarra
				int confirmacion=JOptionPane.showConfirmDialog(view, "¿Desea eliminar el codigo de barra "+cod+" ?");
				
				// si se confirma la eliminacion se procede a eliminar
				if(confirmacion==0){
					
					//se eliminar el codigo en la view list y se coloca el eliminado en una lista especial para eliminar de la bd
					this.view.getModeloCodBarra().eliminarCodBarra(indexCodigoSelecionado);
					
					
				}
				
				
			break;
		case "ACTUALIZAR":
			cargarDatosArticuloView();
				//se actulizar el articulo en la base de datos
				if(myArticuloDao.actualizarArticulo(myArticulo,this.view.getModeloCodBarra().getCodsElimnar())){
					JOptionPane.showMessageDialog(view, "Se Actualizo el articulo");
					resultaOperacion=true;
					this.view.dispose();
				}
			break;
		case "CANCELAR":
			//cargarDatosArticuloView();
			this.view.setVisible(false);
			
			break;
		}
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<< metodo que devuelve el articulo guardado >>>>>>>>>>>>>>>>>>>>>>>>>         */
	public Articulo getArticuloGuardado(){
		return myArticulo;
		
		
	}
	
	public void guardarArticulo(){
		
		cargarDatosArticuloView();
		
		//se ejecuta la accion de guardar
		if(myArticuloDao.registrarArticulo(myArticulo)){
			
			JOptionPane.showMessageDialog(this.view, "Se ha registrado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
			myArticulo.setId(myArticuloDao.getIdArticuloRegistrado());//se completa el proveedor guardado con el ID asignado por la BD
			resultaOperacion=true;
			this.view.setVisible(false);
			
			
		}
		else{
			JOptionPane.showMessageDialog(this.view, "No se Registro");
			resultaOperacion=false;
			this.view.setVisible(false);
		}
		
	}
	
	private void cargarDatosArticuloView(){
		//Se establece el nombre del articulo
		myArticulo.setArticulo(this.view.getTxtNombre().getText());
		
		//Se establece el impuesto seleccionado
		Impuesto imp= (Impuesto) this.view.getCbxImpuesto().getSelectedItem();
		myArticulo.setImpuestoObj(imp);
		
		//JOptionPane.showMessageDialog(view, this.view.getCbxTipo().getSelectedItem());
		//Se establece los codigos de barra
		myArticulo.setCodBarras(this.view.getModeloCodBarra().getCodsBarras());
		
		//se establece el precion de articulo
		myArticulo.setPrecioVenta(Double.parseDouble(this.view.getTxtPrecio().getText()));
	}
	
	
	public boolean agregarArticulo(){
		this.view.setVisible(true);
		return resultaOperacion;
	}
	
	
	public boolean actualizarArticulo(Articulo a){
		//se carga la configuracionde la view articulo para la actulizacion
		this.view.configActualizar();
		
		
		//se establece el nombre de articulo en la view
		this.view.getTxtNombre().setText(a.getArticulo());
		
		//se establece la marca en la view
		this.view.getTxtMarca().setText(a.getMarcaObj().getMarca());
		
		//si el articulo tiene una lista de codigos de barra se incluyen en la view
		if(a.getCodBarra()!=null){
			this.view.getModeloCodBarra().setCodsBarra(a.getCodBarra());
		}
		
		
		//se consigue el index del impuesto del articulo
		int indexImpuesto=this.view.getListaCbxImpuesto().buscarImpuesto(a.getImpuestoObj());
		//se selecciona el impuesto apropiado para el articulo
		this.view.getCbxImpuesto().setSelectedIndex(indexImpuesto);
		
		
		//se establece el articulo de la clase this
		myArticulo=a;
		
		//se estable el precio del articulo en la view
		this.view.getTxtPrecio().setText(""+a.getPrecioVenta());
				
		// se hace visible la ventana modal
		this.view.setVisible(true);
		
		
		
		return this.resultaOperacion;
	}
	
	public Articulo getArticulo(){
		return myArticulo;
	}
	
	private void cargarComboBox(){
		//se crea el objeto para obtener de la bd los impuestos
		myImpuestoDao=new ImpuestoDao(conexion);
	
		//se obtiene la lista de los impuesto y se le pasa al modelo de la lista
		this.view.getListaCbxImpuesto().setLista(myImpuestoDao.todoImpuesto());
		
		
		//se remueve la lista por defecto
		this.view.getCbxImpuesto().removeAllItems();
	
		this.view.getCbxImpuesto().setSelectedIndex(1);
	}
	
	 // maneja el evento de oprimir el botón del ratón
	public void mousePressed( MouseEvent evento )
	{
		check(evento);
		checkForTriggerEvent( evento ); // comprueba el desencadenador
	} // fin del método mousePressed
	
	// maneja el evento de liberación del botón del ratón
	public void mouseReleased( MouseEvent evento )
	{
		check(evento);
		checkForTriggerEvent( evento ); // comprueba el desencadenador
	} // fin del método mouseReleased
	
	// determina si el evento debe desencadenar el menú contextual
	private void checkForTriggerEvent( MouseEvent evento )
	{
		if ( evento.isPopupTrigger() )
			this.view.getMenuContextual().show(evento.getComponent(), evento.getX(), evento.getY() );
	} // fin del método checkForTriggerEvent
	
	public void check(MouseEvent e)
	{ 
		if (e.isPopupTrigger()) { //if the event shows the menu 
			this.view.getListCodigos().setSelectedIndex(this.view.getListCodigos().locationToIndex(e.getPoint())); //select the item 
			//menuContextual.show(listCodigos, e.getX(), e.getY()); //and show the menu 
		} 
		
		
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
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
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
	
}
