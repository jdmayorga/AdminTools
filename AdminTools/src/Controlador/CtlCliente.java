package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Modelo.Articulo;
import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Conexion;
import View.ViewCrearCliente;

public class CtlCliente implements ActionListener {
	
	private ViewCrearCliente view;
	private Conexion conexion=null;
	private Cliente myCliente=null;
	private ClienteDao myClienteDao=null;
	private boolean resultaOperacion=false;
	
	public CtlCliente(ViewCrearCliente v,Conexion conn){
		view=v;
		conexion=conn;
		view.conectarControlador(this);
		myCliente=new Cliente();
		myClienteDao=new ClienteDao(conn);
		view.setLocationRelativeTo(view);
		view.setModal(true);
		//view.setVisible(true);
		
	}
	private void getCliente(){
		myCliente.setNombre(view.getTxtNombre().getText());
		myCliente.setDireccion(view.getTxtDireccion().getText());
		myCliente.setTelefono(view.getTxtTelefono().getText());
		myCliente.setCelular(view.getTxtMovil().getText());
		myCliente.setRtn(view.getTxtRtn().getText());
	}
	
	public boolean agregarCliente(){
		this.view.setVisible(true);
		return resultaOperacion;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<< metodo que devuelve el cliente guardado >>>>>>>>>>>>>>>>>>>>>>>>>         */
	public Cliente getClienteGuardado(){
		return myCliente;
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch(comando){
		case "GUARDAR":
			getCliente();
			//se ejecuta la accion de guardar
			if(myClienteDao.registrarCliente(myCliente)){
				JOptionPane.showMessageDialog(this.view, "Se ha registrado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
				myCliente.setId(myClienteDao.getIdClienteRegistrado());//se completa el proveedor guardado con el ID asignado por la BD
				resultaOperacion=true;
				this.view.setVisible(false);
			}else{
				JOptionPane.showMessageDialog(this.view, "No se Registro");
				resultaOperacion=false;
				this.view.setVisible(false);
			}
			break;
		case "CANCELAR":
			break;
		}
	}

}
