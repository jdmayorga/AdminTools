package Modelo;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ProveedorDao {
	private int idProveedorRegistrado;
	public Conexion conexion;
	private Proveedor myProveedor;
	
	private PreparedStatement insertarNuevoProveedor = null;
	private PreparedStatement seleccionarTodasLosProveedores = null;
	private PreparedStatement elimiarProveedor = null;
	private PreparedStatement actualizarProveedor = null;
	private PreparedStatement buscarProveedor = null;
	private PreparedStatement buscarProveedorNombre = null;
	private PreparedStatement buscarProveedorDireccion = null;
	
	public ProveedorDao(Conexion conn){
		
		conexion=conn;
		try {
			
			//conexion= new Conexion();
			myProveedor=new Proveedor();
		
		
			seleccionarTodasLosProveedores = conexion.getConnection().prepareStatement( "SELECT * FROM proveedor;");
			insertarNuevoProveedor=conexion.getConnection().prepareStatement( "INSERT INTO proveedor(nombre_proveedor,telefono,celular,direccion) VALUES (?,?,?,?)");
			elimiarProveedor=conexion.getConnection().prepareStatement("DELETE FROM proveedor WHERE codigo_proveedor = ?");
			buscarProveedor=conexion.getConnection().prepareStatement("SELECT codigo_proveedor,nombre_proveedor,telefono,celular,direccion FROM proveedor where codigo_proveedor =  ?");
			buscarProveedorNombre=conexion.getConnection().prepareStatement("SELECT * FROM proveedor where nombre_proveedor LIKE ? ;");
			buscarProveedorDireccion=conexion.getConnection().prepareStatement("SELECT * FROM proveedor where direccion LIKE ? ;");
			actualizarProveedor=conexion.getConnection().prepareStatement("UPDATE proveedor SET nombre_proveedor = ?, telefono =?, celular = ?, direccion = ?  WHERE codigo_proveedor = ?");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agregar un proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarProveedor(Proveedor myProveedor)
	{
		
		int resultado=0;
		ResultSet rs=null;
		try 
		{
			
			insertarNuevoProveedor.setString( 1, myProveedor.getNombre() );
			insertarNuevoProveedor.setString( 2, myProveedor.getTelefono() );
			insertarNuevoProveedor.setString( 3, myProveedor.getCelular() );
			insertarNuevoProveedor.setString( 4, myProveedor.getDireccion() );
			
			resultado=insertarNuevoProveedor.executeUpdate();
			rs=insertarNuevoProveedor.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdProveedorRegistrado(rs.getInt(1));
				//int clave=rs.getInt(1);
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
				rs.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para el atributo de ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public int getIdProveedorRegistrado(){
		return idProveedorRegistrado;
	}
	
	public void setIdProveedorRegistrado(int id){
		idProveedorRegistrado=id;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarProveedor(Proveedor myProveedor){
		
		
		
		
		try {
			
				actualizarProveedor.setString(1, myProveedor.getNombre());
				actualizarProveedor.setString(2, myProveedor.getTelefono());
				actualizarProveedor.setString(3, myProveedor.getCelular());
				actualizarProveedor.setString(4, myProveedor.getDireccion());
				actualizarProveedor.setInt(5, myProveedor.getId());
				
				actualizarProveedor.executeUpdate();
				/*Statement estatuto = conexion.getConnection().createStatement();
				estatuto.executeUpdate("UPDATE proveedor SET nombre_proveedor ='"+myProveedor.getNombre()+"', telefono ='"
					+myProveedor.getTelefono()+"', celular = '"+myProveedor.getCelular()+"', direccion = '"+myProveedor.getDireccion()+"'  WHERE codigo_proveedor ="+myProveedor.getId());
				estatuto.close();
				conexion.desconectar();*/
				return true;
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
            
			
		}
		
	}


	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Eliminar los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarProveedor(int id){
		
		int resultado=0;
		
		try {
			
				elimiarProveedor.setInt( 1, id );
				resultado=elimiarProveedor.executeUpdate();
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar proveedor por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Proveedor buscarPro(int i){
		//Conexion conex= new Conexion();
		Proveedor unoPro=new Proveedor();
		ResultSet res=null;
		boolean existe=false;
		try {
			
			buscarProveedor.setInt(1, i);
			res = buscarProveedor.executeQuery();
			while(res.next()){
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
			finally
			{
				try{
					res.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
				excepcionSql.printStackTrace();
				conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return unoPro;
			}
			else return null;
		
		
		
	}

	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los proveedores por nombre>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Proveedor> buscarProveedorNombre(String busqueda){
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		ResultSet res=null;
		 
		boolean existe=false;
		try {
			
		
			buscarProveedorNombre.setString(1, "%" + busqueda + "%");
			res = buscarProveedorNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Proveedor unoPro=new Proveedor();
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				proveedores.add(unoPro);
				//System.out.println(unoPro);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					res.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return proveedores;
			}
			else return null;
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar los proveedores por direccion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Proveedor> buscarProveedorDireccion(String busqueda){
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		ResultSet res=null;
		 
		boolean existe=false;
		try {
			
		
			buscarProveedorDireccion.setString(1, "%" + busqueda + "%");
			res = buscarProveedorDireccion.executeQuery();
			while(res.next()){
				Proveedor unoPro=new Proveedor();
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				proveedores.add(unoPro);
				//System.out.println(unoPro);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					res.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return proveedores;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca todos los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/	
	public List<Proveedor> todoProveedor(){
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		
		ResultSet res=null;
		boolean existe=false;
		
		try {
			
			
			res = seleccionarTodasLosProveedores.executeQuery();
			while(res.next()){
				Proveedor unoPro=new Proveedor();
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				proveedores.add(unoPro);
			 }					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
			finally
			{
				try{
					res.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return proveedores;
			}
			else return null;
		
		
		//return  proveedores;
	}
	
	public ProveedorDao getProveedorDao(){
		return this;
	}
	
/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<cierra la conexión a la base de datos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public void close()
		{
			conexion.desconectar();
		} //// fin del método close

}
