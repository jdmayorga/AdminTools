package Modelo;

import javax.sql.DataSource;

import Controlador.CtlAgregarCompras;
import Controlador.CtlMenuPrincipal;
import View.ViewMenuPrincipal;

public class principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conexion conexion= new Conexion();
		DataSource poolConexion=DBCPDataSourceFactory.getDataSource("mysql");
		ViewMenuPrincipal principal=new ViewMenuPrincipal();
		CtlMenuPrincipal ctl=new CtlMenuPrincipal(principal,conexion,poolConexion);
		principal.conectarControlador(ctl);
		
		
		/*Conexion conexion=new Conexion();
		
		ViewAgregarCompras view= new ViewAgregarCompras();
		CtlAgregarCompras ctl=new CtlAgregarCompras(view,conexion);
		//view.conectarControlador(ctl);*/
		

	}

}
