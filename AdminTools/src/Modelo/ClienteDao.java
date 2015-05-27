package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

public class ClienteDao {
	
	private int idClienteRegistrado;
	
	private Connection conexionBD=null;
	private Conexion conexion;
	private PreparedStatement insertarNuevaCliente=null;
	private PreparedStatement seleccionarTodasLosClientes=null;
	private PreparedStatement buscarClienteID=null;
	
	public ClienteDao(Conexion conn){
		conexion=conn;
		
	}
	
	public void setIdClienteRegistrado(int i){
		idClienteRegistrado=i;
	}
	public int getIdClienteRegistrado(){
		return idClienteRegistrado;
	} 
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los clientes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Cliente> todoClientes(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
        
        //Statement stmt = null;
       	List<Cliente> clientes=new ArrayList<Cliente>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarTodasLosClientes = con.prepareStatement("SELECT * FROM cliente;");
			
			res = seleccionarTodasLosClientes.executeQuery();
			while(res.next()){
				Cliente unCliente=new Cliente();
				existe=true;
				unCliente.setId(res.getInt("codigo_cliente"));
				unCliente.setNombre(res.getString("nombre_cliente"));
				unCliente.setDireccion(res.getString("direccion"));
				unCliente.setTelefono(res.getString("telefono"));
				unCliente.setCelular(res.getString("movil"));
				unCliente.setRtn(res.getString("rtn"));			
				
				clientes.add(unCliente);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarTodasLosClientes != null)seleccionarTodasLosClientes.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return clientes;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar clientes por id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
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
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarCliente(Cliente myCliente)
	{
		JOptionPane.showConfirmDialog(null, myCliente);
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			insertarNuevaCliente=con.prepareStatement( "INSERT INTO cliente(nombre_cliente,direccion,telefono,movil,rtn) VALUES (?,?,?,?,?)");
			
			insertarNuevaCliente.setString( 1, myCliente.getNombre() );
			insertarNuevaCliente.setString( 2, myCliente.getDereccion() );
			insertarNuevaCliente.setString( 3, myCliente.getTelefono());
			insertarNuevaCliente.setString(4, myCliente.getCelular());
			insertarNuevaCliente.setString(5, myCliente.getRtn());
			
			resultado=insertarNuevaCliente.executeUpdate();
			
			rs=insertarNuevaCliente.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdClienteRegistrado(rs.getInt(1));
			}
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			conexion.desconectar();
            return false;
		}
		finally
		{
			try{
				if(rs!=null)rs.close();
				 if(insertarNuevaCliente != null)insertarNuevaCliente.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
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
