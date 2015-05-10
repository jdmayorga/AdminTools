package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class InventarioDao {
	private Conexion conexion;
	private PreparedStatement buscarArticulo=null;
	
	public InventarioDao(Conexion conn){
		conexion=conn;
		
		try {
			buscarArticulo=conexion.getConnection().prepareStatement("SELECT * FROM v_existenacia articulo.codigo_articulo=? ;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar marca por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Inventario buscarArticulo(int i){
		Inventario unArticulo=new Inventario();
		ResultSet res=null;
		boolean existe=false;
		try {
			
			buscarArticulo.setInt(1, i);
			res = buscarArticulo.executeQuery();
			while(res.next()){
				existe=true;
				
				unArticulo.setExistencia(res.getDouble("existencia"));
				unArticulo.setPrecioVenta(res.getDouble("Precio"));
				unArticulo.getBodega().setId(res.getInt("codigo_bodega"));
				unArticulo.getBodega().setDescripcion(res.getString("descripcion_bodega"));
				unArticulo.getArticulo().setId(res.getInt("codigo_articulo"));
				unArticulo.getArticulo().setArticulo(res.getString("articulo"));
				
				
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
				return unArticulo;
			}
			else return null;
		
		
		
	}

}
