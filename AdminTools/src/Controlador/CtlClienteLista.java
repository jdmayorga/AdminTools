package Controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Modelo.Articulo;
import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Conexion;
import View.TablaModeloMarca;
import View.ViewCrearCliente;
import View.ViewListaClientes;

public class CtlClienteLista implements ActionListener {
	private ViewListaClientes view;
	private Conexion conexion=null;
	private ClienteDao clienteDao=null;
	
	public CtlClienteLista(ViewListaClientes v,Conexion conn){
		view=v;
		conexion=conn;
		view.conectarControlador(this);
		
		clienteDao=new ClienteDao(conexion);
		cargarTabla(clienteDao.todoClientes());
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		switch (comando){
		case "NUEVO":
			ViewCrearCliente view=new ViewCrearCliente();
			CtlCliente ctlCliente=new CtlCliente(view,conexion);
			
			boolean resuldoGuarda=ctlCliente.agregarCliente();
			if(resuldoGuarda){
				this.view.getModelo().agregarCliente(ctlCliente.getClienteGuardado());
				
				/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
				int row =  this.view.getTablaClientes().getRowCount () - 1;
				Rectangle rect = this.view.getTablaClientes().getCellRect(row, 0, true);
				this.view.getTablaClientes().scrollRectToVisible(rect);
				this.view.getTablaClientes().clearSelection();
				this.view.getTablaClientes().setRowSelectionInterval(row, row);
				TablaModeloMarca modelo = (TablaModeloMarca)this.view.getTablaClientes().getModel();
				modelo.fireTableDataChanged();
			}
			break;
		}
	}
	public void cargarTabla(List<Cliente> clientes){
		//JOptionPane.showMessageDialog(view, articulos);
		this.view.getModelo().limpiarClientes();
		for(int c=0;c<clientes.size();c++){
			this.view.getModelo().agregarCliente(clientes.get(c));
		}
	}

}
