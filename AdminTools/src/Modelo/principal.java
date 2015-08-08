package Modelo;

import java.sql.SQLException;

import javax.sql.DataSource;

import Controlador.CtlAgregarCompras;
import Controlador.CtlLogin;
import Controlador.CtlMenuPrincipal;
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
		
			ViewMenuPrincipal principal=new ViewMenuPrincipal();
			CtlMenuPrincipal ctl=new CtlMenuPrincipal(principal,conexion);
			principal.conectarControlador(ctl);
		
		}
		/*Conexion conexion=new Conexion();
		
		ViewAgregarCompras view= new ViewAgregarCompras();
		CtlAgregarCompras ctl=new CtlAgregarCompras(view,conexion);
		//view.conectarControlador(ctl);*/
		

	}

}
