package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

public class InventarioDao {
	private Conexion conexion;
	private PreparedStatement buscarArticulo=null;
	private PreparedStatement actualizarInventario=null;
	private PreparedStatement insertarNuevaInventario=null;
	
	public InventarioDao(Conexion conn){
		conexion=conn;
		
		try {
			buscarArticulo=conexion.getConnection().prepareStatement("SELECT * FROM v_existencia where codigo_articulo=? ;");
			actualizarInventario=conexion.getConnection().prepareStatement("UPDATE articulo_bodega SET existencia = ? WHERE codigo_articulo = ? and codigo_bodega=?");
			insertarNuevaInventario=conexion.getConnection().prepareStatement( "INSERT INTO articulo_bodega(codigo_bodega,codigo_articulo,existencia) VALUES (?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar inventario>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarInventario(Inventario inv){
		boolean resultado=false;
		try{
			
			insertarNuevaInventario.setInt(1,inv.getBodega().getId());
			insertarNuevaInventario.setInt(2, inv.getArticulo().getId());
			insertarNuevaInventario.setDouble(3,inv.getExistencia());
			insertarNuevaInventario.executeUpdate();
			
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
			conexion.desconectar();
			resultado= false;
		}
		return resultado;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar los Inventario>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarInventario(Inventario inv){
		int resultado;
		try {
			
			actualizarInventario.setDouble(1,inv.getExistencia());
			actualizarInventario.setInt(2, inv.getArticulo().getId());
			actualizarInventario.setInt(3,inv.getBodega().getId());
			
			resultado=actualizarInventario.executeUpdate();
			//JOptionPane.showMessageDialog(null, a+","+resultado );
			
			return true;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
        }
	}
	
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar articulo del inventario por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
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
				//unArticulo.setPrecioVenta(res.getDouble("Precio"));
				unArticulo.getBodega().setId(res.getInt("codigo_bodega"));
				unArticulo.getBodega().setDescripcion(res.getString("descripcion_bodega"));
				unArticulo.getArticulo().setId(res.getInt("codigo_articulo"));
				unArticulo.getArticulo().setArticulo(res.getString("articulo"));
				unArticulo.getArticulo().setPrecioVenta(res.getDouble("precio_articulo"));
				
				
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
					existe=false;
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
