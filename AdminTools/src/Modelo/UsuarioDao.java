package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
	
	//private PreparedStatement getFecha=null;
	//private Connection conexionBD=null;
	private Conexion conexion;
	//private PreparedStatement agregarFactura=null;
	//private PreparedStatement seleccionarFacturasPendientes=null;
	//private PreparedStatement seleccionarFacturas=null;
	//private PreparedStatement elimiarTem = null;
	private PreparedStatement comprobarAdmin = null;
	
	//private DetalleFacturaDao detallesDao=null;
	
	public UsuarioDao(Conexion conn){
		conexion =conn;
	}
	
	public boolean comprobarAdmin(String pwd){
		
		boolean resultado=false;

		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT * FROM usuario where permiso='administrador' and clave=?";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			comprobarAdmin = con.prepareStatement(sql);
			comprobarAdmin.setString(1, pwd);
			
			res = comprobarAdmin.executeQuery();
			while(res.next()){
				resultado=true;
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
				resultado=false;
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(comprobarAdmin != null)comprobarAdmin.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
		
	
		
		return resultado;
	}

}
