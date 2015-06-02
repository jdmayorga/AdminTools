package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

public class FacturaDao {
	
	private PreparedStatement getFecha=null;
	private Connection conexionBD=null;
	private Conexion conexion;
	private PreparedStatement agregarFactura=null;
	private PreparedStatement seleccionarFacturasPendientes=null;
	
	private DetalleFacturaDao detallesDao=null;
	private ClienteDao myClienteDao=null;
	
	private Integer idFacturaGuardada=null;
	public FacturaDao(Conexion conn){
		//Class(Conexion);
		conexion =conn;
		detallesDao=new DetalleFacturaDao(conexion);
		myClienteDao=new ClienteDao(conexion);
		
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
		finally{
			try{
				
				if(res != null) res.close();
	            if(getFecha != null)getFecha.close();
	            if(conn != null) conn.close();
	            
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		}
		
		
		return fecha;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas temporal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFacturaTemp(Factura myFactura){
		boolean resultado=false;
		ResultSet rs=null;
		
		Connection conn=null;
		//int idFactura=0;
		
		String sql= "INSERT INTO encabezado_factura_temp("
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
			
			
			
			agregarFactura.executeUpdate();//se guarda el encabezado de la factura
			rs=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				//idFactura=rs.getInt(1);
				idFacturaGuardada=rs.getInt(1);
				
			}
			
			//se guardan los detalles de la fatura
			for(int x=0;x<myFactura.getDetalles().size();x++){
				
				if(myFactura.getDetalles().get(x).getArticulo().getId()!=-1)
					detallesDao.agregarDetalleTemp(myFactura.getDetalles().get(x), idFacturaGuardada);
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
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFactura(Factura myFactura){
		boolean resultado=false;
		ResultSet rs=null;
		
		Connection conn=null;
		//int idFactura=0;
		
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
			conn=Conexion.getPoolConexion().getConnection();
			agregarFactura=conexion.getConnection().prepareStatement(sql);
			agregarFactura.setBigDecimal(1,myFactura.getSubTotal() );
			agregarFactura.setBigDecimal(2, myFactura.getTotalImpuesto());
			agregarFactura.setBigDecimal(3, myFactura.getTotal());
			agregarFactura.setInt(4, myFactura.getCliente().getId());
			agregarFactura.setBigDecimal(5, myFactura.getTotalDescuento());
			agregarFactura.setString(6, "ACT");
			agregarFactura.setInt(7, myFactura.getTipoFactura());
			agregarFactura.setString(8, "unico");
			
			
			
			agregarFactura.executeUpdate();//se guarda el encabezado de la factura
			rs=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				//idFactura=rs.getInt(1);
				idFacturaGuardada=rs.getInt(1);
				
			}
			
			//se guardan los detalles de la fatura
			for(int x=0;x<myFactura.getDetalles().size();x++){
				
				if(myFactura.getDetalles().get(x).getArticulo().getId()!=-1)
					detallesDao.agregarDetalle(myFactura.getDetalles().get(x), idFacturaGuardada);
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
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Factura> facturasEnProceso(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
        
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			seleccionarFacturasPendientes = con.prepareStatement("SELECT * FROM encabezado_factura_temp;");
			
			res = seleccionarFacturasPendientes.executeQuery();
			while(res.next()){
				Factura unaFactura=new Factura();
				existe=true;
				unaFactura.setIdFactura(res.getInt("numero_factura"));
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setCliente(unCliente);
				
				unaFactura.setFecha(res.getString("fecha"));
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				unaFactura.setTotalDescuento(res.getBigDecimal("descuento"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				
				unaFactura.setDetalles(detallesDao.detallesFacturaPendiente(res.getInt("numero_factura")));
				
				
				facturas.add(unaFactura);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarFacturasPendientes != null)seleccionarFacturasPendientes.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return facturas;
			}
			else return null;
		
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

	public Integer getIdFacturaGuardada() {
		// TODO Auto-generated method stub
		return idFacturaGuardada;
	}

	

}
