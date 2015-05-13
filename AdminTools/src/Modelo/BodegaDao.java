package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BodegaDao {
	
	private Conexion conexion=null;
	private PreparedStatement buscarBodega=null;
	public BodegaDao(Conexion conn){
		conexion=conn;
		try {
			buscarBodega=conexion.getConnection().prepareStatement("SELECT * FROM bodega where codigo_bodega=?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Bodega buscarBodega(int id){
		Bodega myBodega=new Bodega();
		ResultSet res=null;
		boolean existe=false;
		try {
			buscarBodega.setInt(1, id);
			res=buscarBodega.executeQuery();
			while(res.next()){
				existe=true;
				myBodega.setId(res.getInt("codigo_bodega"));
				myBodega.setDescripcion(res.getString("descripcion_bodega"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		if(existe)
			return myBodega;
		else
			return null;
			
	}

}
