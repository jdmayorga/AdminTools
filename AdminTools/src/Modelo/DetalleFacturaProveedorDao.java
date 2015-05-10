package Modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleFacturaProveedorDao {
	private Conexion conexion;
	private PreparedStatement agregarDetalle=null;
	private InventarioDao inventarioDao;
	public DetalleFacturaProveedorDao(Conexion conn){
		conexion=conn;
		inventarioDao=new InventarioDao(conexion);
		try {
			agregarDetalle=conexion.getConnection().prepareStatement( "INSERT INTO detalle_factura_compra(numero_compra,codigo_articulo,precio,cantidad,impuesto,subtotal) VALUES (?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalle>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalle(DetalleFacturaProveedor detalle,int noCompra){
		boolean resultado=false;
		Inventario inventario;
		try{
			agregarDetalle.setInt(1, noCompra);
			agregarDetalle.setInt(2, detalle.getArticulo().getId());
			agregarDetalle.setDouble(3, detalle.getPrecioCompra());
			agregarDetalle.setDouble(4, detalle.getCantidad());
			agregarDetalle.setDouble(5, detalle.getImpuesto());
			agregarDetalle.setDouble(6, detalle.getTotal());
			agregarDetalle.executeUpdate();
			
			inventario=inventarioDao.buscarArticulo(detalle.getArticulo().getId());
			//se verifica que exite el articulo en la bodega
			if(inventario!=null){
				//se agrega al inventario la nueva cantidad
				inventario.incremetarExistencia(detalle.getCantidad());
			}
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
			conexion.desconectar();
			resultado= false;
		}
		return resultado;
	}

}
