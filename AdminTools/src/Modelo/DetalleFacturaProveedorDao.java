package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleFacturaProveedorDao {
	private Conexion conexion;
	private PreparedStatement agregarDetalle=null;
	private InventarioDao inventarioDao;
	private KardexDao kardexDao;
	public DetalleFacturaProveedorDao(Conexion conn){
		conexion=conn;
		inventarioDao=new InventarioDao(conexion);
		kardexDao=new KardexDao(conexion);
		/*try {
			agregarDetalle=conexion.getConnection().prepareStatement( "INSERT INTO detalle_factura_compra(numero_compra,codigo_articulo,precio,cantidad,impuesto,subtotal) VALUES (?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalle>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalle(DetalleFacturaProveedor detalle,int noCompra){
		boolean resultado=false;
		Connection conn=null;
		
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			agregarDetalle=conn.prepareStatement( "INSERT INTO detalle_factura_compra(numero_compra,codigo_articulo,precio,cantidad,impuesto,subtotal) VALUES (?,?,?,?,?,?)");
			agregarDetalle.setInt(1, noCompra);
			agregarDetalle.setInt(2, detalle.getArticulo().getId());
			agregarDetalle.setDouble(3, detalle.getPrecioCompra());
			agregarDetalle.setDouble(4, detalle.getCantidad());
			agregarDetalle.setDouble(5, detalle.getImpuesto());
			agregarDetalle.setDouble(6, detalle.getTotal());
			agregarDetalle.executeUpdate();
			
			
			
			Inventario inventario=new Inventario();
			//se consigue el inventario del articulo
			inventario=inventarioDao.buscarArticulo(detalle.getArticulo().getId());
			
			//se verifica que exite el articulo en el inventario
			if(inventario!=null){
				//se agrega al inventario la nueva cantidad
				inventario.incremetarExistencia(detalle.getCantidad());
				//se actualizar el articulo del inventario
				inventarioDao.actualizarInventario(inventario);
			}
			else{//sino esta el inventario el articulo, se debe crear el inventario
				
				//para implementar varias bodegas hay que cambiar el codigo de la bodega
				Inventario inventario1=new Inventario();
				inventario1.getBodega().setId(1);
				inventario1.setArticulo(detalle.getArticulo());
				inventario1.setExistencia(detalle.getCantidad());
				inventarioDao.agregarInventario(inventario1);				
			}
			
			//se crea y guarda la nuevo movimiento del kardex
			Kardex myKardex =new Kardex();
			
			myKardex.setArticulo(detalle.getArticulo());
			myKardex.setEntrada(detalle.getCantidad());
			
			//hay que cambiar para implementar multiples bodegas
			myKardex.getBodega().setId(1);
			myKardex.setNoDocumento(""+noCompra);
			
			kardexDao.agregarEntrada(myKardex);
			
			
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
			resultado= false;
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(agregarDetalle != null)agregarDetalle.close();
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

}
