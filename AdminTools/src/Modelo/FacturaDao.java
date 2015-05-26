package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class FacturaDao {
	
	private PreparedStatement getFecha=null;
	private Connection conexionBD=null;
	private Conexion conexion;
	
	public FacturaDao(Conexion conn){
		//Class(Conexion);
		conexion =conn;
		/*try {
			conexionBD=conn.getPoolConexion().getConnection();
			getFecha=conexionBD.prepareStatement("SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public String getFechaSistema(){
		String fecha="";
		//DataSource ds=DBCPDataSourceFactory.getDataSource("mysql");
		Connection conn=null;
		ResultSet res=null;
		//Statement instrucions=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			getFecha = conn.prepareStatement("SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
			
			//res=getFecha.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y %h:%i:%s %p') as fecha;");
			res=getFecha.executeQuery();
			while(res.next()){
				fecha=res.getString("fecha");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			if(res != null) res.close();
            if(getFecha != null)getFecha.close();
            if(conn != null) conn.close();
            
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
			} // fin de catch
		
		
		
		return fecha;
	}
	
	public void desconectarBD(){
		try {
			if(getFecha != null)getFecha.close();
			if(conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
