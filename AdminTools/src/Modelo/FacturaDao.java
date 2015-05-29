package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class FacturaDao {
	
	private PreparedStatement getFecha=null;
	private Connection conexionBD=null;
	private Conexion conexion;
	private PreparedStatement agregarFactura=null;
	
	private DetalleFacturaDao detallesDao=null;
	
	public FacturaDao(Conexion conn){
		//Class(Conexion);
		conexion =conn;
		/*try {
			conexionBD=conn.getPoolConexion().getConnection();
			getFecha=conexionBD.prepareStatement("SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para conseguir la fecha>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public String getFechaSistema(){
		String fecha="";
		//DataSource ds=DBCPDataSourceFactory.getDataSource("mysql");
		Connection conn=null;
		ResultSet res=null;
		//Statement instrucions=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			getFecha = conn.prepareStatement("SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
			
			//res=getFecha.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y %h:%i:%s %p') as fecha;");
			res=getFecha.executeQuery();
			while(res.next()){
				fecha=res.getString("fecha");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			if(res != null) res.close();
            if(getFecha != null)getFecha.close();
            if(conn != null) conn.close();
            
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
			} // fin de catch
		
		
		
		return fecha;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFactura(Factura myFactura){
		boolean resultado=false;
		ResultSet rs=null;
		
		Connection conn=null;
		int idFactura=0;
		
		String sql= "INSERT INTO encabezado_factura("
				+ "fecha,"
				+ "subtotal,"
				+ "impuesto,"
				+ "total,"
				+ "codigo_cliente,"
				+ "descuento,"
				+ "estado_factura,"
				+ "tipo_factura,"
				+ "usuario)"
				+ " VALUES (now(),?,?,?,?,?,?,?,?)";
		
		try 
		{
			conn=conexion.getPoolConexion().getConnection();
			agregarFactura=conexion.getConnection().prepareStatement(sql);
			agregarFactura.setBigDecimal(1,myFactura.getSubTotal() );
			agregarFactura.setBigDecimal(2, myFactura.getTotalImpuesto());
			agregarFactura.setBigDecimal(3, myFactura.getTotal());
			agregarFactura.setInt(4, myFactura.getCliente().getId());
			agregarFactura.setBigDecimal(5, myFactura.getTotalDescuento());
			agregarFactura.setString(6, "ACT");
			agregarFactura.setInt(7, myFactura.getTipoFactura());
			agregarFactura.setString(8, "unico");
			
			
			
			agregarFactura.executeUpdate();
			rs=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				idFactura=rs.getInt(1);
			}
			
			//JOptionPane.showMessageDialog(null,""+idFactura);
			for(int x=0;x<myFactura.getDetalles().size();x++){
				detallesDao.agregarDetalle(myFactura.getDetalles().get(x), idFactura);
			}
			
			resultado= true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				if(rs != null) rs.close();
	            if(agregarFactura != null)agregarFactura.close();
	            if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
		
		
		
		return resultado;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para desconectar las conexiones>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public void desconectarBD(){
		try {
			if(getFecha != null)getFecha.close();
			if(conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
