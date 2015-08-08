package Modelo;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import Controlador.CtlAgregarCompras;
import Controlador.CtlFacturar;
import Controlador.CtlLogin;
import Controlador.CtlMenuPrincipal;
import View.ViewFacturar;
import View.ViewLogin;
import View.ViewMenuPrincipal;

public class principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Conexion conexion= new Conexion();
		
		AbstractJasperReports.loadFileReport();
		
		
		try {
			AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 1, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ViewLogin viewLogin =new ViewLogin(); 
		CtlLogin ctlLogin=new CtlLogin(viewLogin,conexion);
		
		boolean login=ctlLogin.login();
		
		if(login){
			
			if(conexion.getUsuarioLogin().getTipoPermiso()==1){
				ViewMenuPrincipal principal=new ViewMenuPrincipal();
				CtlMenuPrincipal ctl=new CtlMenuPrincipal(principal,conexion);
				principal.conectarControlador(ctl);
			}
			if(conexion.getUsuarioLogin().getTipoPermiso()==2){
				//JOptionPane.showMessageDialog(viewLogin, "jola");
				ViewFacturar vistaFacturar=new ViewFacturar(null);
				vistaFacturar.pack();
				CtlFacturar ctlFacturar=new CtlFacturar(vistaFacturar,conexion );
				vistaFacturar.setVisible(true);
			}
		
			
		
		}
		/*Conexion conexion=new Conexion();
		
		ViewAgregarCompras view= new ViewAgregarCompras();
		CtlAgregarCompras ctl=new CtlAgregarCompras(view,conexion);
		//view.conectarControlador(ctl);*/
		

	}

}
