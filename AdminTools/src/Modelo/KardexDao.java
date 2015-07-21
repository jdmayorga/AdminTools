package Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KardexDao {
	private Conexion conexion=null;
	private PreparedStatement buscarArticulo=null;
	private PreparedStatement insertarNuevoMovimiento=null;
	private String ultimoRistro="SELECT "
			+ " v_kardex.can_saldo, "
			+ "v_kardex.precio_saldo, "
			+ "v_kardex.total_saldo,"
			+ "v_kardex.cod,"
			+ "v_kardex.codigo_bodega,"
			+ "v_kardex.codigo_articulo "
			+ " FROM v_kardex "
			+ " WHERE "
			+ " v_kardex.codigo_articulo = ? AND "
			+ " v_kardex.codigo_bodega = ? "
			+ " ORDER BY "
			+ " v_kardex.cod DESC LIMIT 1";
	public KardexDao(Conexion conn){
		conexion=conn;
		
		/*try {
			//buscarArticulo=conexion.getConnection().prepareStatement("SELECT * FROM kardex where codigo_articulo=? and codigo_bodega=? LIMIT 1,1");
			//insertarNuevoMovimiento=conexion.getConnection().prepareStatement( "INSERT INTO kardex(no_documento,codigo_articulo,codigo_bodega,entrada,fecha) VALUES (?,?,?,?,now())");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public boolean agregarEntrada(Kardex kar){
		boolean resultado=false;
		Connection conn=null;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			insertarNuevoMovimiento=conn.prepareStatement( "INSERT INTO kardex(no_documento,codigo_articulo,codigo_bodega,entrada,salida,fecha) VALUES (?,?,?,?,?,now())");
			//insertarNuevoMovimiento=conn.prepareStatement(sql);
			insertarNuevoMovimiento.setString(1, kar.getNoDocumento());
			insertarNuevoMovimiento.setInt(2, kar.getArticulo().getId());
			insertarNuevoMovimiento.setInt(3, kar.getBodega().getId());
			insertarNuevoMovimiento.setDouble(4, kar.getEntrada());
			insertarNuevoMovimiento.setDouble(5, kar.getSalida());
			insertarNuevoMovimiento.executeUpdate();
			resultado=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(insertarNuevoMovimiento != null)insertarNuevoMovimiento.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
		return resultado;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar articulo en el kardex por por id articulo y id bodega>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Kardex buscarKardex(int idArticulo,int idBodega){
		Kardex myKardex=new Kardex();
		boolean existe=false;
		Connection conn=null;
		ResultSet res=null;
		ArticuloDao articuloDao=new ArticuloDao(conexion);
		BodegaDao bodegaDao=new BodegaDao(conexion);
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarArticulo=conn.prepareStatement("SELECT * FROM kardex where codigo_articulo=? and codigo_bodega=? LIMIT 1,1");
			buscarArticulo.setInt(1, idArticulo);
			buscarArticulo.setInt(2, idBodega);
			res=buscarArticulo.executeQuery();
			while(res.next()){
				existe=true;
				myKardex.setArticulo(articuloDao.buscarArticulo(idArticulo));
				myKardex.setBodega(bodegaDao.buscarBodega(idBodega));
				myKardex.setEntrada(res.getDouble("entrada"));
				myKardex.setSalida(res.getDouble("salida"));
				myKardex.setId(res.getInt("idKardex"));
				myKardex.setFecha(res.getString("fecha"));
				myKardex.setNoDocumento(res.getString("no_documento"));
				myKardex.setExistencia(res.getDouble("existencia"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(buscarArticulo != null)buscarArticulo.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		if(existe){
			return myKardex;
			
		}else{
			return null;
		}
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar articulo en el kardex por por id articulo y id bodega>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public BigDecimal buscarKardexPrecio(int idArticulo,int idBodega){
		
		boolean existe=false;
		Connection conn=null;
		ResultSet res=null;
		BigDecimal precioCompra=new BigDecimal(00);
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarArticulo=conn.prepareStatement(ultimoRistro);
			buscarArticulo.setInt(1, idArticulo);
			buscarArticulo.setInt(2, idBodega);
			res=buscarArticulo.executeQuery();
			while(res.next()){
				existe=true;
				
				precioCompra=res.getBigDecimal("precio_saldo");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(buscarArticulo != null)buscarArticulo.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		if(existe){
			return precioCompra;
			
		}else{
			return null;
		}
	}

}
