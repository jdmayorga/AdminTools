package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JLabel;

import Controlador.CtlMenuPrincipal;

import java.awt.Color;

public class ViewMenuPrincipal extends JFrame {
	private final JLabel usuario = new JLabel("Usuario:");
	
	private JMenuItem mntmCerrarFacturacion;
	private JMenuItem mntmProveedores;
	private JMenuItem mntmArticulos;
	private JMenuItem mntmMarcas;
	private JMenuItem mntmAgregarCompras;
	private JMenuItem mntmFacturar;
	public ViewMenuPrincipal() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnInventario = new JMenu("Inventario");
		menuBar.add(mnInventario);
		
		mntmProveedores = new JMenuItem("Proveedores");
		mnInventario.add(mntmProveedores);
		
		mntmArticulos = new JMenuItem("Articulos");
		mnInventario.add(mntmArticulos);
		
		JMenuItem mntmIngresos = new JMenuItem("Ingresos");
		mnInventario.add(mntmIngresos);
		
		mntmMarcas = new JMenuItem("Marcas");
		mnInventario.add(mntmMarcas);
		
		JMenu mnFacturacion = new JMenu("Facturacion");
		menuBar.add(mnFacturacion);
		
		mntmFacturar = new JMenuItem("Facturar");
		mnFacturacion.add(mntmFacturar);
		
		mntmCerrarFacturacion = new JMenuItem("Cerrar Facturacion");
		mnFacturacion.add(mntmCerrarFacturacion);
		
		JMenu mnCompras = new JMenu("Compras");
		menuBar.add(mnCompras);
		
		mntmAgregarCompras = new JMenuItem("Agregar");
		mnCompras.add(mntmAgregarCompras);
		
		JMenu mnCuentasPorPagar = new JMenu("Cuentas Por Pagar");
		menuBar.add(mnCuentasPorPagar);
		
		JMenuItem mntmFacturasPendientes = new JMenuItem("Facturas pendientes");
		mnCuentasPorPagar.add(mntmFacturasPendientes);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de..");
		menuBar.add(mntmAcercaDe);
		setSize(1024,700);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(119, 136, 153));
		panel.setSize(700, 100);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.add(usuario);
		
		JLabel lblUserName = new JLabel("David");
		panel.add(lblUserName);
		
		
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
		
	}

}
