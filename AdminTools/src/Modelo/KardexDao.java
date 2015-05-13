package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KardexDao {
	private Conexion conexion=null;
	private PreparedStatement buscarArticulo=null;
	private PreparedStatement insertarNuevoMovimiento=null;
	
	public KardexDao(Conexion conn){
		conexion=conn;
		
		try {
			buscarArticulo=conexion.getConnection().prepareStatement("SELECT * FROM kardex where codigo_articulo=? and codigo_bodega=? LIMIT 1,1");
			insertarNuevoMovimiento=conexion.getConnection().prepareStatement( "INSERT INTO kardex(no_documento,codigo_articulo,codigo_bodega,entrada,fecha) VALUES (?,?,?,?,now())");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean agregarEntrada(Kardex kar){
		boolean resultado=false;
		try {
			insertarNuevoMovimiento.setString(1, kar.getNoDocumento());
			insertarNuevoMovimiento.setInt(2, kar.getArticulo().getId());
			insertarNuevoMovimiento.setInt(3, kar.getBodega().getId());
			insertarNuevoMovimiento.setDouble(4, kar.getEntrada());
			insertarNuevoMovimiento.executeUpdate();
			resultado=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar articulo en el kardex por por id articulo y id bodega>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Kardex buscarKardex(int idArticulo,int idBodega){
		Kardex myKardex=new Kardex();
		boolean existe=false;
		ResultSet res=null;
		ArticuloDao articuloDao=new ArticuloDao(conexion);
		BodegaDao bodegaDao=new BodegaDao(conexion);
		
		try {
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
		
		if(existe){
			return myKardex;
			
		}else{
			return null;
		}
	}

}
