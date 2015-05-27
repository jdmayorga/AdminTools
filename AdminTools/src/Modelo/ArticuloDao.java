package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

public class ArticuloDao {
	private int idArticuloRegistrado;
	
	private Conexion conexion=null;
	private PreparedStatement insertarNuevaArticulo=null;
	private PreparedStatement seleccionarTodasLosArticulos=null;
	private PreparedStatement eliminarArticulo = null;
	private PreparedStatement actualizarArticulo=null;
	private PreparedStatement buscarArticulo=null;
	private PreparedStatement buscarArticuloMarca=null;
	private PreparedStatement buscarArticuloNombre=null;
	private CodBarraDao myCodBarraDao=null;
	private Connection conexionBD=null;
	
	public ArticuloDao(Conexion conn){
		conexion=conn;
		
		myCodBarraDao=new CodBarraDao(conexion);
		/*try{
			conexionBD=conexion.getPoolConexion().getConnection();
			
			seleccionarTodasLosArticulos = conexionBD.prepareStatement("SELECT * FROM v_articulos;");
			insertarNuevaArticulo=conexionBD.prepareStatement( "INSERT INTO articulo(articulo,codigo_marca,codigo_impuesto,precio_articulo) VALUES (?,?,?,?)");
			eliminarArticulo=conexionBD.prepareStatement("DELETE FROM articulo WHERE codigo_articulo = ?");
			actualizarArticulo=conexionBD.prepareStatement("UPDATE articulo SET articulo = ?, codigo_marca = ? ,codigo_impuesto = ?, precio_articulo=? WHERE codigo_articulo = ?");
			buscarArticulo=conexionBD.prepareStatement("SELECT * FROM v_articulos where codigo_articulo =  ?");
			buscarArticuloNombre=conexionBD.prepareStatement("SELECT * FROM v_articulos where marca LIKE ? ;");
			buscarArticuloMarca=conexionBD.prepareStatement("SELECT * FROM v_articulos where marca LIKE ? ;");
			
			/*
			buscarMarca=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where codigo_marca =  ?");
			buscarMarcaObseracion=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where observacion LIKE ? ;");
			buscarMarcaNombre=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where descripcion LIKE ? ;");*/
		/*}
		catch ( SQLException excepcionSql )
		{
			excepcionSql.printStackTrace();
			System.exit( 1 );
		} // fin de catch*/
		
	}
	public void cargarInstrucciones(){
		try {
			seleccionarTodasLosArticulos = conexionBD.prepareStatement("SELECT * FROM v_articulos;");
			insertarNuevaArticulo=conexionBD.prepareStatement( "INSERT INTO articulo(articulo,codigo_marca,codigo_impuesto,precio_articulo) VALUES (?,?,?,?)");
			eliminarArticulo=conexionBD.prepareStatement("DELETE FROM articulo WHERE codigo_articulo = ?");
			actualizarArticulo=conexionBD.prepareStatement("UPDATE articulo SET articulo = ?, codigo_marca = ? ,codigo_impuesto = ?, precio_articulo=? WHERE codigo_articulo = ?");
			buscarArticulo=conexionBD.prepareStatement("SELECT * FROM v_articulos where codigo_articulo =  ?");
			buscarArticuloNombre=conexionBD.prepareStatement("SELECT * FROM v_articulos where marca LIKE ? ;");
			//buscarArticuloMarca=conexionBD.prepareStatement("SELECT * FROM v_articulos where marca LIKE ? ;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void desconectarBD(){
		try {
			if(seleccionarTodasLosArticulos!=null)seleccionarTodasLosArticulos.close();
			if(insertarNuevaArticulo!=null)insertarNuevaArticulo.close();
			if(eliminarArticulo!=null)eliminarArticulo.close();
			if(actualizarArticulo!=null)actualizarArticulo.close();
			if(buscarArticulo!=null)buscarArticulo.close();
			if(buscarArticuloNombre!=null)buscarArticuloNombre.close();
			if(buscarArticuloMarca!=null)buscarArticuloMarca.close();
			if(conexionBD!=null)conexionBD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public ArticuloDao(DataSource poolConexion) {
		// TODO Auto-generated constructor stub
		try {
			conexionBD=poolConexion.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los articulos por marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Articulo> buscarArticuloMarca(String busqueda){
		List<Articulo> articulos=new ArrayList<Articulo>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try {
			
			conn=conexion.getPoolConexion().getConnection();
			buscarArticuloMarca=conn.prepareStatement("SELECT * FROM v_articulos where marca LIKE ? ;");
			
		
			buscarArticuloMarca.setString(1, "%" + busqueda + "%");
			res = buscarArticuloMarca.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Articulo unArticulo=new Articulo();
				existe=true;
				unArticulo.setId(Integer.parseInt(res.getString("codigo_articulo")));
				unArticulo.setArticulo(res.getString("articulo"));
				unArticulo.getMarcaObj().setMarca(res.getString("marca"));
				unArticulo.getMarcaObj().setId(res.getInt("codigo_marca"));
				unArticulo.getImpuestoObj().setPorcentaje(res.getString("impuesto"));
				unArticulo.getImpuestoObj().setId(res.getInt("codigo_impuesto"));
				unArticulo.setPrecioVenta(res.getDouble("precio_articulo"));
				unArticulo.setTipoArticulo(res.getInt("tipo_articulo"));
				articulos.add(unArticulo);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					if(res!=null)res.close();
					if(buscarArticuloMarca!=null)buscarArticuloMarca.close();
					if(conn!=null)conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return articulos;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los articulo  por nombre>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Articulo> buscarArticulo(String busqueda){
		List<Articulo> articulos=new ArrayList<Articulo>();
		
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarArticuloNombre=conn.prepareStatement("SELECT * FROM v_articulos where articulo LIKE ? ;");
		
			buscarArticuloNombre.setString(1, "%" + busqueda + "%");
			res = buscarArticuloNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Articulo unArticulo=new Articulo();
				existe=true;
				unArticulo.setId(Integer.parseInt(res.getString("codigo_articulo")));
				unArticulo.setArticulo(res.getString("articulo"));
				unArticulo.getMarcaObj().setMarca(res.getString("marca"));
				unArticulo.getMarcaObj().setId(res.getInt("codigo_marca"));
				unArticulo.getImpuestoObj().setPorcentaje(res.getString("impuesto"));
				unArticulo.getImpuestoObj().setId(res.getInt("codigo_impuesto"));
				unArticulo.setPrecioVenta(res.getDouble("precio_articulo"));
				articulos.add(unArticulo);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					if(res!=null)res.close();
					if(conn!=null)conn.close();
					if(buscarArticuloNombre!=null)buscarArticuloNombre.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return articulos;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar articulo por por ID pool>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Articulo buscarArticuloId(int i){
		Articulo unArticulo=new Articulo();
		ResultSet res=null;
		PreparedStatement buscarArt=null;
		Connection conn=null;
		boolean existe=false;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarArt=conn.prepareStatement("SELECT * FROM v_articulos where codigo_articulo =  ?");
			buscarArt.setInt(1, i);
			res = buscarArt.executeQuery();
			while(res.next()){
				existe=true;
				unArticulo.setId(Integer.parseInt(res.getString("codigo_articulo")));
				unArticulo.setArticulo(res.getString("articulo"));
				unArticulo.getMarcaObj().setMarca(res.getString("marca"));
				unArticulo.getMarcaObj().setId(res.getInt("codigo_marca"));
				unArticulo.getImpuestoObj().setPorcentaje(res.getString("impuesto"));
				unArticulo.getImpuestoObj().setId(res.getInt("codigo_impuesto"));
				unArticulo.setPrecioVenta(res.getDouble("precio_articulo"));
				
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
			finally
			{
				try{
					if(res != null) res.close();
	                if(buscarArt != null)buscarArt.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
				excepcionSql.printStackTrace();
				conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return unArticulo;
			}
			else return null;
		
		
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar articulo por por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Articulo buscarArticulo(int i){
		Articulo unArticulo=new Articulo();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarArticulo=conn.prepareStatement("SELECT * FROM v_articulos where codigo_articulo =  ?");
			buscarArticulo.setInt(1, i);
			res = buscarArticulo.executeQuery();
			while(res.next()){
				existe=true;
				unArticulo.setId(Integer.parseInt(res.getString("codigo_articulo")));
				unArticulo.setArticulo(res.getString("articulo"));
				unArticulo.getMarcaObj().setMarca(res.getString("marca"));
				unArticulo.getMarcaObj().setId(res.getInt("codigo_marca"));
				unArticulo.getImpuestoObj().setPorcentaje(res.getString("impuesto"));
				unArticulo.getImpuestoObj().setId(res.getInt("codigo_impuesto"));
				unArticulo.setPrecioVenta(res.getDouble("precio_articulo"));
				
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
			finally
			{
				try{
					if(res!=null)res.close();
					if(buscarArticulo != null)buscarArticulo.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
				excepcionSql.printStackTrace();
				conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return unArticulo;
			}
			else return null;
		
		
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar un articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarArticulo(int id){
		int resultado=0;
		Connection conn=null;
		
		try {
				conn=conexion.getPoolConexion().getConnection();
				
				eliminarArticulo=conn.prepareStatement("DELETE FROM articulo WHERE codigo_articulo = ?");
				
				eliminarArticulo.setInt( 1, id );
				resultado=eliminarArticulo.executeUpdate();
				myCodBarraDao.eliminarCodigo(id);
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
				if(eliminarArticulo != null)eliminarArticulo.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar los articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarArticulo(Articulo a, List<CodBarra> codsElimniar){
		int resultado;
		
		Connection conn=null;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			actualizarArticulo=conn.prepareStatement("UPDATE articulo SET articulo = ?, codigo_marca = ? ,codigo_impuesto = ?, precio_articulo=? WHERE codigo_articulo = ?");
			actualizarArticulo.setString(1,a.getArticulo());
			actualizarArticulo.setInt(2, a.getMarcaObj().getId());
			actualizarArticulo.setInt(3, a.getImpuestoObj().getId());
			actualizarArticulo.setDouble(4, a.getPrecioVenta());
			actualizarArticulo.setInt(5, a.getId());
			
			resultado=actualizarArticulo.executeUpdate();
			//JOptionPane.showMessageDialog(null, a+","+resultado );
			
			//se elimina los codigos de barra selecionados
			for(int c=0;c<codsElimniar.size();c++){
				myCodBarraDao.eliminarCodigo(codsElimniar.get(c));
			}
			
			//se agregan los codigos nuevos registrados
			for(int x=0;x<a.getCodBarra().size();x++){
				
				
				if(a.getCodBarra().get(x).getCodArticulo()==0){
					
					a.getCodBarra().get(x).setCodArticulo(a.getId());
					myCodBarraDao.registrarCodsBarra(a.getCodBarra().get(x));
				}
				
			}
			return true;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
        }
		finally
		{
			try{
				
				if(actualizarArticulo != null)actualizarArticulo.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	
	

	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Articulo> todoArticulos(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
        
        //Statement stmt = null;
       	List<Articulo> articulos=new ArrayList<Articulo>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarTodasLosArticulos = con.prepareStatement("SELECT * FROM v_articulos;");
			
			res = seleccionarTodasLosArticulos.executeQuery();
			while(res.next()){
				Articulo unArticulo=new Articulo();
				existe=true;
				unArticulo.setId(Integer.parseInt(res.getString("codigo_articulo")));
				unArticulo.setArticulo(res.getString("articulo"));
				unArticulo.getMarcaObj().setMarca(res.getString("marca"));
				unArticulo.getMarcaObj().setId(res.getInt("codigo_marca"));
				unArticulo.getImpuestoObj().setPorcentaje(res.getString("impuesto"));
				unArticulo.getImpuestoObj().setId(res.getInt("codigo_impuesto"));
				unArticulo.setPrecioVenta(res.getDouble("precio_articulo"));
				
				
				articulos.add(unArticulo);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarTodasLosArticulos != null)seleccionarTodasLosArticulos.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return articulos;
			}
			else return null;
		
	}
	
	
	public void setIdArticuloRegistrado(int i){
		idArticuloRegistrado=i;
	}
	public int getIdArticuloRegistrado(){
		return idArticuloRegistrado;
	} 
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarArticulo(Articulo myArticulo)
	{
		
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			insertarNuevaArticulo=con.prepareStatement( "INSERT INTO articulo(articulo,codigo_marca,codigo_impuesto,precio_articulo) VALUES (?,?,?,?)");
			
			insertarNuevaArticulo.setString( 1, myArticulo.getArticulo() );
			insertarNuevaArticulo.setInt( 2, myArticulo.getMarcaObj().getId() );
			insertarNuevaArticulo.setDouble( 3, myArticulo.getImpuestoObj().getId());
			insertarNuevaArticulo.setDouble(4, myArticulo.getPrecioVenta());
			
			resultado=insertarNuevaArticulo.executeUpdate();
			
			rs=insertarNuevaArticulo.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdArticuloRegistrado(rs.getInt(1));
			}
			
			//se completa el listado de barras de codigos con el codigo del articulo creado
			for(int x=0;x<myArticulo.getCodBarra().size();x++){
				myArticulo.getCodBarra().get(x).setCodArticulo(idArticuloRegistrado);
			}
			
			myCodBarraDao.registrarCodsBarra(myArticulo.getCodBarra());
			//myArticulo.getCodBarra().size();
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
				 if(insertarNuevaArticulo != null)insertarNuevaArticulo.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}

}
