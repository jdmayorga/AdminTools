package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class FacturaCompraDao {
	private Conexion conexion=null;
	private PreparedStatement agregarFactura=null;
	private DetalleFacturaProveedorDao detallesDao;
	
	public FacturaCompraDao(Conexion conn){
		conexion=conn;
		detallesDao=new DetalleFacturaProveedorDao(conexion);
		try {
			agregarFactura=conexion.getConnection().prepareStatement( "INSERT INTO encabezado_factura_compra(fecha,subtotal,impuesto,total,codigo_cliente,no_factura_compra,tipo_factura,fecha_vencimiento,estado_factura,fecha_ingreso) VALUES (?,?,?,?,?,?,?,?,?,now())");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFactura(FacturaCompra fac)
	{
		
		boolean resultado;
		ResultSet rs=null;
		int idFactura=0;
		
		try 
		{
			agregarFactura.setString(1, fac.getFechaCompra());
			agregarFactura.setDouble(2, fac.getSubTotal());
			agregarFactura.setDouble(3, fac.getTotalImpuesto());
			agregarFactura.setDouble(4, fac.getTotal());
			agregarFactura.setInt(5, fac.getProveedor().getId());
			agregarFactura.setString(6, fac.getIdFactura());
			agregarFactura.setInt(7, fac.getTipoFactura());
			agregarFactura.setString(8, fac.getFechaVencimento());
			
			if(fac.getTipoFactura()==1){
				//uno para factura pagada
				agregarFactura.setInt(9, 1);
			}
			if (fac.getTipoFactura()==2){
				//dos para factura pendiente de pago
				agregarFactura.setInt(9, 2);
			}
			
			agregarFactura.executeUpdate();
			rs=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				idFactura=rs.getInt(1);
			}
			
			//JOptionPane.showMessageDialog(null,""+idFactura);
			for(int x=0;x<fac.getDetalles().size();x++){
				detallesDao.agregarDetalle(fac.getDetalles().get(x), idFactura);
			}
			
			resultado= true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				rs.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
		return resultado;
	}
	
	public String getFechaSistema(){
		String fecha="";
		
		Statement instrucions;
		try {
			instrucions = conexion.getConnection().createStatement();
			ResultSet res=null;
			//res=instrucions.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y %h:%i:%s %p') as fecha;");
			res=instrucions.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
			while(res.next()){
				fecha=res.getString("fecha");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return fecha;
	}

}
