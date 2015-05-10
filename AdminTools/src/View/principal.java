package View;

import Controlador.CtlAgregarCompras;
import Controlador.CtlMenuPrincipal;
import Modelo.Conexion;

public class principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conexion conexion= new Conexion();
		ViewMenuPrincipal principal=new ViewMenuPrincipal();
		CtlMenuPrincipal ctl=new CtlMenuPrincipal(principal,conexion);
		principal.conectarControlador(ctl);
		
		
		/*Conexion conexion=new Conexion();
		
		ViewAgregarCompras view= new ViewAgregarCompras();
		CtlAgregarCompras ctl=new CtlAgregarCompras(view,conexion);
		//view.conectarControlador(ctl);*/
		

	}

}
