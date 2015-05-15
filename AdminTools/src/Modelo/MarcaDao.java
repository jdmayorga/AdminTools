package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class MarcaDao {
	
	private int idMarcaRegistrada;
	private Conexion conexion;
	
	private PreparedStatement seleccionarTodasLasMarcas = null;
	private PreparedStatement seleccionarPersonasPorApellido = null;
	private PreparedStatement insertarNuevaMarca = null;
	private PreparedStatement actualizarMarca = null;
	private PreparedStatement eliminarMarca = null;
	private PreparedStatement buscarMarca = null;
	private PreparedStatement buscarMarcaObseracion=null;
	private PreparedStatement buscarMarcaNombre=null;
	
	
	public MarcaDao(Conexion conn){
		conexion=conn;
		try{
			
			//conexion= new Conexion();
			seleccionarTodasLasMarcas = conexion.getDataSource().getConnection().prepareStatement("SELECT * FROM marcas");
			insertarNuevaMarca=conexion.getConnection().prepareStatement( "INSERT INTO marcas(descripcion,observacion) VALUES (?,?)");
			actualizarMarca=conexion.getConnection().prepareStatement("UPDATE marcas SET descripcion = ?, observacion = ? WHERE codigo_marca = ?");
			eliminarMarca=conexion.getConnection().prepareStatement("DELETE FROM marcas WHERE codigo_marca = ?");
			buscarMarca=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where codigo_marca =  ?");
			buscarMarcaObseracion=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where observacion LIKE ? ;");
			buscarMarcaNombre=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where descripcion LIKE ? ;");
		}
		catch ( SQLException excepcionSql )
		{
			excepcionSql.printStackTrace();
			System.exit( 1 );
		} // fin de catch
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eleminar marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarMarca(int id){
		int resultado=0;
		
		try {
			
			eliminarMarca.setInt( 1, id );
				resultado=eliminarMarca.executeUpdate();
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar los marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarMarca(Marca myMarca){
		
		
		
		
		try {
			
				actualizarMarca.setString(1, myMarca.getMarca());
				actualizarMarca.setString(2, myMarca.getObservacion());
				
				actualizarMarca.setInt(3, myMarca.getId());
				
				
				actualizarMarca.executeUpdate();
				return true;
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
            
			
		}
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< selecciona de la Bd todas las MArcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Marca> todoMarcas(){
		List<Marca> marcas=new ArrayList<Marca>();
		ResultSet res=null;
		
		boolean existe=false;
		try {
			
			
			
			res = seleccionarTodasLasMarcas.executeQuery();
			while(res.next()){
				Marca unaMarca=new Marca();
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				marcas.add(unaMarca);
			 }
			//res.close();
			//conexion.desconectar();
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				res.close();
				seleccionarTodasLasMarcas.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
			if (existe) {
				return marcas;
			}
			else return null;
			
		}
	
	
	public void setIdMarcaRegistrada(int i){
		idMarcaRegistrada=i;
	}
	public int getIdMarcaRegistrada(){
		return idMarcaRegistrada;
	} 
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarMarca(Marca myMarca)
	{
		
		int resultado=0;
		ResultSet rs=null;
		
		try 
		{
			insertarNuevaMarca.setString( 1, myMarca.getMarca() );
			insertarNuevaMarca.setString( 2, myMarca.getObservacion() );
			
			resultado=insertarNuevaMarca.executeUpdate();
			
			rs=insertarNuevaMarca.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdMarcaRegistrada(rs.getInt(1));
			}
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
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
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar marca por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Marca buscarMarca(int i){
		Marca unaMarca=new Marca();
		ResultSet res=null;
		boolean existe=false;
		try {
			
			buscarMarca.setInt(1, i);
			res = buscarMarca.executeQuery();
			while(res.next()){
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				
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
				return unaMarca;
			}
			else return null;
		
		
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los marcas por observacion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Marca> buscarMarcasObservacion(String busqueda){
		List<Marca> marcas=new ArrayList<Marca>();
		ResultSet res=null;
		 
		boolean existe=false;
		try {
			
		
			buscarMarcaNombre.setString(1, "%" + busqueda + "%");
			res = buscarMarcaNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Marca unaMarca=new Marca();
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				marcas.add(unaMarca);
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
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return marcas;
			}
			else return null;
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los marcas por observacion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Marca> buscarMarcas(String busqueda){
		List<Marca> marcas=new ArrayList<Marca>();
		ResultSet res=null;
		 
		boolean existe=false;
		try {
			
		
			buscarMarcaObseracion.setString(1, "%" + busqueda + "%");
			res = buscarMarcaObseracion.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Marca unaMarca=new Marca();
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				marcas.add(unaMarca);
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
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return marcas;
			}
			else return null;
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<cierra la conexión a la base de datos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public void close()
		{
			conexion.desconectar();
		} //// fin del método close
	

}
