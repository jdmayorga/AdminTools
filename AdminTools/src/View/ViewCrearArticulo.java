package View;



import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import Controlador.CtlArticulo;
import java.awt.Font;
import javax.swing.JList;


public class ViewCrearArticulo extends JDialog {
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblMarca;
	private JComboBox cbxImpuesto ;
	private JLabel lblImpuesto;
	private Font myFont;
	private BotonCancelar btnCancelar;
	private BotonGuardar btnGuardar;
	private BotonActualizar btnActualizar;
	
	private JTextField txtMarca;
	private JButton btnBuscar;
	private JList listCodigos;
	private JTextField txtCodigo;
	
	
	private JMenuItem mntmEliminar;
	

	
	private JPopupMenu menuContextual; // permite al usuario seleccionar el color
	
	
	
	//se crea el modelo de la lista de los impuestos
	private ComboBoxImpuesto modeloImpuesto;//=new ComboBoxImpuesto();
	
	private ListaModeloCodBarra modeloCodBarra;
	private JTextField txtPrecio;
	
	

	public ViewCrearArticulo(ViewListaArticulo view) {
		super(view,"Agregar Articulos",Dialog.ModalityType.DOCUMENT_MODAL);
		
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setResizable(false);
		getContentPane().setLayout(null);
		
		myFont=new Font("Verdana", Font.PLAIN, 12);
		
		
		
		
		menuContextual = new JPopupMenu(); // crea el menú contextual
		
		//opcion del menu flotante
		mntmEliminar = new JMenuItem("Eliminar");
		menuContextual.add(mntmEliminar);
		
		
		//Nombre Articulo
		lblNombre=new JLabel();
		lblNombre.setText("Nombre");
		lblNombre.setFont(myFont);
		lblNombre.setBounds(32, 11, 76, 23);
		getContentPane().add(lblNombre);
		
		txtNombre=new JTextField(30);
		txtNombre.setBounds(118, 11, 257, 23);
		txtNombre.setFont(myFont);
		getContentPane().add(txtNombre);
		
		
		//Marca
		lblMarca = new JLabel("Marca");
		lblMarca.setBounds(32, 45, 89, 23);
		lblMarca.setFont(myFont);
		getContentPane().add(lblMarca);
		
		
		//Impuesto
		lblImpuesto = new JLabel("Impuesto");
		lblImpuesto.setBounds(32, 79, 89, 23);
		lblImpuesto.setFont(myFont);
		getContentPane().add(lblImpuesto);
		
		
		
		
		cbxImpuesto = new JComboBox();
		modeloImpuesto=new ComboBoxImpuesto();
		cbxImpuesto.setModel(modeloImpuesto);
		cbxImpuesto.setBounds(118, 79, 257, 23);
		cbxImpuesto.setFont(myFont);
		getContentPane().add(cbxImpuesto);
		
		
		//botones
		btnGuardar = new BotonGuardar();
		btnGuardar.setLocation(32, 322);
		//tnCancelar.setLocation(42, 175);
		getContentPane().add(btnGuardar);
		
		btnActualizar=new BotonActualizar();
		btnActualizar.setLocation(32, 322);
		getContentPane().add(btnActualizar);
		btnActualizar.setVisible(false);
		
		btnCancelar = new BotonCancelar();
		//btnCancelar.setBounds(212, 175, 135, 39);
		btnCancelar.setLocation(244, 322);
		getContentPane().add(btnCancelar);
		
		btnBuscar = new JButton("...");
		btnBuscar.setBounds(357, 46, 18, 23);
		getContentPane().add(btnBuscar);
		
		txtMarca = new JTextField();
		txtMarca.setEditable(false);
		txtMarca.setBounds(118, 45, 229, 23);
		getContentPane().add(txtMarca);
		txtMarca.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setLayout(new FloawLayout());
		scrollPane.setBounds(118, 155, 257, 101);
		
		getContentPane().add(scrollPane);
		
		modeloCodBarra=new ListaModeloCodBarra();
		listCodigos = new JList();
		listCodigos.setModel(modeloCodBarra);
		listCodigos.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(listCodigos);
		
				
		JLabel lblCodigoBarra = new JLabel("Codigo Barra");
		lblCodigoBarra.setFont(myFont);
		lblCodigoBarra.setBounds(32, 126, 89, 14);
		getContentPane().add(lblCodigoBarra);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(118, 124, 257, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio Venta");
		lblPrecio.setFont(myFont);
		lblPrecio.setBounds(32, 270, 89, 14);
		getContentPane().add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(118, 267, 257, 20);
		getContentPane().add(txtPrecio);
		txtPrecio.setColumns(10);
		
		setSize(434,406);
		
		//centrar la ventana en la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
	}
	
	public void conectarCtl(CtlArticulo m){
		
		btnGuardar.addActionListener(m);
		btnGuardar.setActionCommand("GUARDAR");
		
		
		btnBuscar.addActionListener(m);
		btnBuscar.setActionCommand("BUSCAR");
		
		btnActualizar.addActionListener(m);
		btnActualizar.setActionCommand("ACTUALIZAR");

		btnCancelar.addActionListener(m);
		btnCancelar.setActionCommand("CANCELAR");
		
		txtCodigo.addActionListener(m);
		txtCodigo.setActionCommand("NUEVOCODIGO");
		 
		listCodigos.addKeyListener(m);
		listCodigos.addMouseListener(m);
		
		mntmEliminar.addActionListener(m);
		mntmEliminar.setActionCommand("ELIMINARCODIGO");
	}
	public BotonActualizar getBtnActualizar(){
		return btnActualizar;
	}
	public JButton getBtnGuardar(){
		return btnGuardar;
	}
	public JTextField getTxtMarca(){
		return txtMarca;
	}
	public JTextField getTxtNombre(){
		return txtNombre;
	}
	public JComboBox getCbxImpuesto(){
		return cbxImpuesto;
	}
	
	public ComboBoxImpuesto getListaCbxImpuesto(){
		return modeloImpuesto;
	}
	
	public ListaModeloCodBarra getModeloCodBarra(){
		return modeloCodBarra;
	}
	public JTextField getTxtCodigo(){
		return txtCodigo;
	}
	
	public void configActualizar(){
		btnGuardar.setVisible(false);
		btnActualizar.setVisible(true);
		this.setTitle("Actualizar Articulo");
	}
	public JList getListCodigos(){
		return listCodigos;
	}
	public JTextField getTxtPrecio(){
		return txtPrecio;
	}
	public JPopupMenu getMenuContextual(){
		return menuContextual;
		
	}
}
