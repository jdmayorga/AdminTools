package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ClienteDao {
	
	
	private PreparedStatement buscarClienteID=null;
	private Connection conexionBD=null;
	private Conexion conexion;
	
	
	public ClienteDao(Conexion conn){
		conexion=conn;
		
	}
	
	
	
	public Cliente buscarCliente(int id){
		Cliente myCliente=new Cliente();
		//se crear un referencia al pool de conexiones
		
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
       
		
		ResultSet res=null;
		
		boolean existe=false;
		
		
		try {
			con = conexion.getPoolConexion().getConnection();
			
			buscarClienteID=conexionBD.prepareStatement("SELECT * FROM cliente where codigo_cliente=?");
			
			buscarClienteID.setInt(1, id);
			res=buscarClienteID.executeQuery();
			while(res.next()){
				myCliente.setId(res.getInt("codigo_cliente"));
				myCliente.setNombre(res.getString("nombre_cliente"));
				myCliente.setTelefono(res.getString("telefono"));
				myCliente.setCelular(res.getString("movil"));
				myCliente.setRtn(res.getString("rtn"));
				existe=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			if(res != null) res.close();
            if(buscarClienteID != null)buscarClienteID.close();
            if(con != null) con.close();
            
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();

			} // fin de catch
		
		if(existe){
				return myCliente;
		}
		else
			return null;
		
	
		
	}
	
	public void desconectarBD(){
		 try {
			 if(buscarClienteID != null)buscarClienteID.close();
			 if(conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
