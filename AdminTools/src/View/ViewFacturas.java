package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
 
import Controlador.CtlFacturas;
import javax.swing.JLabel;

public class ViewFacturas extends JDialog {
	
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected BotonCobrarSmall btnCobrar;
	
	
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnFecha;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opci�n
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	protected JTextField txtBuscar1;
	
	
	
	private JTable tablaFacturas;
	private TablaModeloFacturados modelo;
	private JTextField txtBuscar2;

	public ViewFacturas(JFrame view) {
		
		
		miEsquema=new BorderLayout();
		this.setTitle("Facturas");
		this.setLocationRelativeTo(view);
		this.setModal(true);
		getContentPane().setLayout(miEsquema);
	
	
	
		//creacion de los paneles
		panelAccion=new JPanel();
		panelBusqueda=new JPanel();
		panelSuperior=new JPanel();
		
		panelAccion.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Acciones de registro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Busqueda de registros", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		//agregar componentes al panel acciones
		btnAgregar = new BotonAgregar();
		btnAgregar.setEnabled(false);
		btnAgregar.setMnemonic('r');
		panelAccion.add(btnAgregar);
	   
		btnEliminar = new BotonEliminar();
		btnEliminar.setToolTipText("Anular Facturas");
		btnEliminar.setEnabled(false);
	    panelAccion.add(btnEliminar);
	    
	    btnCobrar = new BotonCobrarSmall();
	    btnCobrar.setEnabled(false);
	    //btnLimpiar.setIcon(new ImageIcon("recursos/clear.png")); // NOI18N
	    panelAccion.add(btnCobrar);
	    //panelAccion.setVisible(false);	
	    
	    //configuracion del panel busqueda
	    grupoOpciones = new ButtonGroup(); // crea ButtonGroup
	    rdbtnTodos = new JRadioButton("Todos");
		rdbtnTodos.setSelected(true);
		panelBusqueda.add(rdbtnTodos);
		grupoOpciones.add(rdbtnTodos);
	
		//opciones de busquedas
		rdbtnId = new JRadioButton("ID",false);
		panelBusqueda.add(rdbtnId);
		grupoOpciones.add(rdbtnId);
		
		rdbtnFecha = new JRadioButton("Fecha",false);
		panelBusqueda.add(rdbtnFecha);
		grupoOpciones.add(rdbtnFecha);
		
		//elementos del panel buscar
		txtBuscar1=new JTextField(10);
		panelBusqueda.add(txtBuscar1);
		
		txtBuscar2 = new JTextField();
		txtBuscar2.setEditable(false);
		panelBusqueda.add(txtBuscar2);
		txtBuscar2.setColumns(10);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
	    
	    //tabla y sus componentes
		modelo=new TablaModeloFacturados();
		tablaFacturas=new JTable();
		tablaFacturas.setModel(modelo);
		RenderizadorTablaFacturas renderizador = new RenderizadorTablaFacturas();
		tablaFacturas.setDefaultRenderer(String.class, renderizador);
		
		tablaFacturas.getColumnModel().getColumn(0).setPreferredWidth(60);     //Tama�o de las columnas de las tablas
		tablaFacturas.getColumnModel().getColumn(1).setPreferredWidth(70);	//de las columnas
		tablaFacturas.getColumnModel().getColumn(2).setPreferredWidth(280);	//en la tabla
		tablaFacturas.getColumnModel().getColumn(3).setPreferredWidth(70);	//
		
		
		JScrollPane scrollPane = new JScrollPane(tablaFacturas);
		scrollPane.setBounds(36, 97, 742, 136);
	
	
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(744,600);
	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
public void conectarControlador(CtlFacturas c){
		
		rdbtnTodos.addActionListener(c);
		rdbtnTodos.setActionCommand("TODAS");
		
		rdbtnId.addActionListener(c);
		//rdbtnId.getActionCommand();
		rdbtnId.setActionCommand("ID");
		
		rdbtnFecha.addActionListener(c);
		rdbtnFecha.setActionCommand("FECHA");
		
		
		
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		 btnAgregar.addActionListener(c);
		 btnAgregar.setActionCommand("INSERTAR");
		 
		 btnEliminar.addActionListener(c);
		 btnEliminar.setActionCommand("ANULARFACTURA");
		 
		 btnCobrar.addActionListener(c);
		 btnCobrar.setActionCommand("COBRAR");
		 
		 txtBuscar1.addActionListener(c);
		 txtBuscar1.setActionCommand("BUSCAR");
		 
		 tablaFacturas.addMouseListener(c);
		 tablaFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public JTable getTablaFacturas(){
		return tablaFacturas;
	}
	public TablaModeloFacturados getModelo(){
		return modelo;
	}
	public JButton getBtnEliminar(){
		return btnEliminar;
	}
	public JRadioButton getRdbtnId(){
		return rdbtnId;
	}
	public JTextField getTxtBuscar1(){
		return txtBuscar1;
	}
	public JTextField getTxtBuscar2(){
		return txtBuscar2;
	}
	public BotonCobrarSmall getBtnCobrar(){
		return btnCobrar;
	}
	public JRadioButton getRdbtnFecha(){
		return rdbtnFecha;
	}
	public JRadioButton getRdbtnTodos(){
		return rdbtnTodos;
		
	}
	public BotonAgregar getBtnAgregar(){
		return btnAgregar;
	}


}
