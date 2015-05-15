package View;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.DropMode;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import Controlador.CtlFacturar;

import java.awt.Font;

public class ViewFacturar extends JDialog {
	private JTable tableDetalle;
	private TablaModeloFactura modeloTabla;
	private JPanel panelAcciones;
	private JPanel panelDatosFactura;
	private JLabel lblFecha;
	private JTextField txtFechafactura;
	private JLabel lblCodigoCliente;
	private JTextField txtIdcliente;
	private JTextField txtNombrecliente;
	
	private ButtonGroup grupoOpciones;
	private JRadioButton rdbtnCredito;
	private JRadioButton rdbtnContado;
	private JTextField txtSubtotal;
	private JLabel lblSubtotal;
	private JTextField txtImpuesto;
	private JLabel lblImpuesto;
	private JTextField txtTotal;
	private JLabel lblTotal;
	private JLabel lblNombreCliente;
	private JLabel lblContado;
	private JLabel lblCredito;
	
	public ViewFacturar() {
		
		panelAcciones=new JPanel();
		panelAcciones.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Opciones", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAcciones.setBounds(20, 0, 754, 60);
		panelAcciones.setLayout(null);
		
		this.setTitle("Facturar");
		getContentPane().add(panelAcciones);
		
		JButton btnBuscar = new JButton("F1 Buscar");
		btnBuscar.setBounds(26, 26, 132, 23);
		panelAcciones.add(btnBuscar);
		
		JButton btnCliente = new JButton("F2 Cliente");
		btnCliente.setBounds(186, 26, 132, 23);
		panelAcciones.add(btnCliente);
		
		JButton btnCobrar = new JButton("F3 Cobrar");
		btnCobrar.setBounds(328, 26, 132, 23);
		panelAcciones.add(btnCobrar);
		
		JButton btnGuardar = new JButton("F4 Guardar");
		btnGuardar.setBounds(470, 26, 132, 23);
		panelAcciones.add(btnGuardar);
		
		JButton btnCerrar = new JButton("F5 Cerrar");
		btnCerrar.setBounds(612, 26, 132, 23);
		panelAcciones.add(btnCerrar);
		
		
		panelDatosFactura=new JPanel();
		panelDatosFactura.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Datos Generales", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDatosFactura.setBounds(20, 71, 754, 84);
		panelDatosFactura.setLayout(null);
		
		
		getContentPane().add(panelDatosFactura);
		
		lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(20, 23, 40, 14);
		panelDatosFactura.add(lblFecha);
		
		txtFechafactura = new JTextField();
		txtFechafactura.setEditable(false);
		txtFechafactura.setBounds(20, 44, 104, 29);
		panelDatosFactura.add(txtFechafactura);
		txtFechafactura.setColumns(10);
		
		lblCodigoCliente = new JLabel("Id Cliente");
		lblCodigoCliente.setBounds(156, 23, 61, 14);
		panelDatosFactura.add(lblCodigoCliente);
		
		txtIdcliente = new JTextField();
		txtIdcliente.setBounds(156, 44, 104, 29);
		panelDatosFactura.add(txtIdcliente);
		txtIdcliente.setColumns(10);
		
		txtNombrecliente = new JTextField();
		txtNombrecliente.setToolTipText("Nombre Cliente");
		txtNombrecliente.setBounds(280, 44, 256, 29);
		panelDatosFactura.add(txtNombrecliente);
		txtNombrecliente.setColumns(10);
		
		grupoOpciones = new ButtonGroup();
		rdbtnCredito = new JRadioButton("");
		rdbtnCredito.setBounds(669, 50, 31, 23);
		grupoOpciones.add(rdbtnCredito);
		panelDatosFactura.add(rdbtnCredito);
		
		rdbtnContado = new JRadioButton("");
		rdbtnContado.setVerticalAlignment(SwingConstants.TOP);
		rdbtnContado.setSelected(true);
		rdbtnContado.setBounds(573, 50, 46, 23);
		grupoOpciones.add(rdbtnContado);
		panelDatosFactura.add(rdbtnContado);
		
		lblNombreCliente = new JLabel("Nombre Cliente");
		lblNombreCliente.setBounds(280, 23, 104, 14);
		panelDatosFactura.add(lblNombreCliente);
		
		lblContado = new JLabel("Contado");
		lblContado.setBounds(563, 23, 56, 14);
		panelDatosFactura.add(lblContado);
		
		lblCredito = new JLabel("Credito");
		lblCredito.setBounds(664, 23, 46, 14);
		panelDatosFactura.add(lblCredito);
		
		modeloTabla=new TablaModeloFactura();
		tableDetalle = new JTable(modeloTabla);
		tableDetalle.getColumnModel().getColumn(0).setPreferredWidth(140);     //Tamaño de las columnas de las tablas
		tableDetalle.getColumnModel().getColumn(1).setPreferredWidth(200);	//
		tableDetalle.getColumnModel().getColumn(2).setPreferredWidth(90);	//
		tableDetalle.getColumnModel().getColumn(3).setPreferredWidth(90);	//
		tableDetalle.getColumnModel().getColumn(4).setPreferredWidth(90);	//
		tableDetalle.getColumnModel().getColumn(5).setPreferredWidth(90);	//
		tableDetalle.getColumnModel().getColumn(6).setPreferredWidth(100);	//
		
		JScrollPane scrollPane = new JScrollPane(tableDetalle);
		scrollPane.setBounds(20, 179, 754, 270);
		getContentPane().add(scrollPane);
		
		this.setSize(800, 600);
		getContentPane().setLayout(null);
		
		Font myFont=new Font("OCR A Extended", Font.PLAIN, 45);
		txtSubtotal = new JTextField();
		txtSubtotal.setFont(myFont);
		txtSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubtotal.setText("00");
		
		txtSubtotal.setEditable(false);
		txtSubtotal.setBounds(224, 485, 164, 44);
		getContentPane().add(txtSubtotal);
		txtSubtotal.setColumns(10);
		
		lblSubtotal = new JLabel("SubTotal");
		lblSubtotal.setBounds(224, 460, 59, 14);
		getContentPane().add(lblSubtotal);
		
		txtImpuesto = new JTextField();
		txtImpuesto.setHorizontalAlignment(SwingConstants.RIGHT);
		txtImpuesto.setFont(myFont);
		txtImpuesto.setText("00");
		txtImpuesto.setEditable(false);
		txtImpuesto.setBounds(421, 485, 164, 44);
		getContentPane().add(txtImpuesto);
		txtImpuesto.setColumns(10);
		
		lblImpuesto = new JLabel("Impuesto");
		lblImpuesto.setBounds(421, 460, 59, 14);
		getContentPane().add(lblImpuesto);
		
		txtTotal = new JTextField();
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setFont(myFont);
		txtTotal.setText("00");
		txtTotal.setEditable(false);
		txtTotal.setBounds(610, 485, 164, 44);
		getContentPane().add(txtTotal);
		txtTotal.setColumns(10);
		
		lblTotal = new JLabel("Total");
		lblTotal.setBounds(610, 460, 46, 14);
		getContentPane().add(lblTotal);
	}
	public void conectarContralador(CtlFacturar c){
		
	}
	public static void main(String arg[]){
		 JDialog.setDefaultLookAndFeelDecorated(true);
		ViewFacturar vista=new ViewFacturar();
		vista.setVisible(true);
		vista.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
