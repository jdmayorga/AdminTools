package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JLabel;

import Controlador.CtlMenuPrincipal;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;

public class ViewMenuPrincipal extends JFrame {
	private final JLabel usuario = new JLabel("Usuario:");
	
	private JMenuItem mntmCerrarFacturacion;
	private JMenuItem mntmProveedores;
	private JMenuItem mntmArticulos;
	private JMenuItem mntmMarcas;
	private JMenuItem mntmAgregarCompras;
	private JMenuItem mntmFacturar;
	private JMenuItem mntmClientes;
	private JMenuItem mntmBuscarFacturas;
	private JMenuItem mntmFacturasIngresadas;
	private JLabel lblUserName;
	
	public ViewMenuPrincipal() {
		setTitle("AdminTools");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewMenuPrincipal.class.getResource("/View/imagen/logo-admin-tool1.png")));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnInventario = new JMenu("Inventario");
		menuBar.add(mnInventario);
		
		mntmProveedores = new JMenuItem("Proveedores");
		mnInventario.add(mntmProveedores);
		
		mntmArticulos = new JMenuItem("Articulos");
		mnInventario.add(mntmArticulos);
		
		mntmMarcas = new JMenuItem("Marcas");
		mnInventario.add(mntmMarcas);
		
		JMenuItem mntmRequisicion = new JMenuItem("Requisicion");
		mnInventario.add(mntmRequisicion);
		
		JMenu mnFacturacion = new JMenu("Facturacion");
		menuBar.add(mnFacturacion);
		
		mntmFacturar = new JMenuItem("Facturar");
		mnFacturacion.add(mntmFacturar);
		
		mntmCerrarFacturacion = new JMenuItem("Cerrar Facturacion");
		mnFacturacion.add(mntmCerrarFacturacion);
		

		mntmClientes = new JMenuItem("Clientes");
		mnFacturacion.add(mntmClientes);
		
		mntmBuscarFacturas = new JMenuItem("Buscar Facturas");
		mnFacturacion.add(mntmBuscarFacturas);
		
		JMenu mnCompras = new JMenu("Compras");
		menuBar.add(mnCompras);
		
		mntmAgregarCompras = new JMenuItem("Agregar");
		mnCompras.add(mntmAgregarCompras);
		
		mntmFacturasIngresadas = new JMenuItem("Facturas Ingresadas");
		mnCompras.add(mntmFacturasIngresadas);
		
		JMenu mnCuentasPorCobrar = new JMenu("Cuentas por Cobrar");
		menuBar.add(mnCuentasPorCobrar);
		
		JMenuItem mntmPagosClientes = new JMenuItem("Pagos clientes");
		mnCuentasPorCobrar.add(mntmPagosClientes);
		
		JMenu mnCuentasPorPagar = new JMenu("Cuentas Por Pagar");
		menuBar.add(mnCuentasPorPagar);
		
		JMenuItem mntmFacturasPendientes = new JMenuItem("Facturas pendientes");
		mnCuentasPorPagar.add(mntmFacturasPendientes);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de..");
		menuBar.add(mntmAcercaDe);
		setSize(1024,700);
		
		JPanel panel = new JPanel();
		//panel.setBackground(new Color(0, 191, 255));
		//panel.setBackground(new Color(119, 136, 153));
		panel.setSize(700, 100);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.add(usuario);
		
		lblUserName = new JLabel("Unico");
		panel.add(lblUserName);
		
		JPanel panel_1 = new panelFondo();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		
		setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void conectarControlador(CtlMenuPrincipal c){
		
		mntmProveedores.addActionListener(c);
		mntmProveedores.setActionCommand("PROVEEDORES");
		
		mntmArticulos.addActionListener(c);
		mntmArticulos.setActionCommand("ARTICULOS");
		
		mntmMarcas.addActionListener(c);
		mntmMarcas.setActionCommand("MARCAS");
		
		mntmAgregarCompras.addActionListener(c);
		mntmAgregarCompras.setActionCommand("AGREGARCOMPRAS");
		
		mntmFacturar.addActionListener(c);
		mntmFacturar.setActionCommand("FACTURAR");
		
		mntmClientes.addActionListener(c);
		mntmClientes.setActionCommand("CLIENTES");
		
		mntmBuscarFacturas.addActionListener(c);
		mntmBuscarFacturas.setActionCommand("BUSCARFACTURAS");
		
		mntmCerrarFacturacion.addActionListener(c);
		mntmCerrarFacturacion.setActionCommand("CERRARFACTURACION");
		
		
		mntmFacturasIngresadas.addActionListener(c);
		mntmFacturasIngresadas.setActionCommand("LISTAFACTURASCOMPRA");
		
	}
	public JLabel getLblUserName(){
		return lblUserName;
	}
	
	private class panelFondo extends JPanel{
		@Override
		   public void paintComponent(Graphics g){
		      Dimension tamanio = getSize();
		      ImageIcon imagenFondo = new ImageIcon(getClass().
		      getResource("/View/imagen/fondo-sistema.jpg"));
		      g.drawImage(imagenFondo.getImage(), 0, 0,
		      tamanio.width, tamanio.height, null);
		      setOpaque(false);
		      super.paintComponent(g);
		   }
	}

}
