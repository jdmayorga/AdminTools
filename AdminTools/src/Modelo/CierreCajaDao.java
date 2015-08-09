package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CierreCajaDao {
	
	private Conexion conexion=null;
	private PreparedStatement seleccionarCierre=null;
	private PreparedStatement registrarCierre=null;
	
	public CierreCajaDao(Conexion conn){
		//Class(Conexion);
		conexion =conn;
	}
	
	public boolean registrarCierre(){
		boolean resultado=false;
		 Connection con = null;
		 
		 //SE CONSIGUE EL ITEM PARA EL CIERRE DE CAJA
		 CierreCaja unCierre=this.getCierre();
		 String sql= "INSERT INTO cierre_caja("
					+ "fecha,"
					+ "factura_inicial,"
					+ "factura_final,"
					+ "efectivo,"
					+ "creditos,"
					+ "totalventa)"
					+ " VALUES (now(),?,?,?,?,?)";
		 if(unCierre!=null)
		 try {
				con = Conexion.getPoolConexion().getConnection();
				registrarCierre=con.prepareStatement(sql);
				
				registrarCierre.setInt(1,unCierre.getNoFacturaInicio() );
				registrarCierre.setInt(2,unCierre.getNoFacturaFinal() );
				registrarCierre.setBigDecimal(3, unCierre.getEfectivo());
				registrarCierre.setBigDecimal(4, unCierre.getCredito());
				registrarCierre.setBigDecimal(5, unCierre.getTotal());
				
				
				
				registrarCierre.executeUpdate();//se guarda el encabezado de la factura
				resultado=true;
				
		 }catch (SQLException e) {
				e.printStackTrace();
				resultado=false;
			}
		finally
		{
			try{
				
				//if(res != null) res.close();
				if(registrarCierre != null)registrarCierre.close();
				if(con != null) con.close();
             
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
		return resultado;
	}
	public CierreCaja getCierre(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="select * from v_cierre_caja;";
        //Statement stmt = null;
    	CierreCaja unaCierre=new CierreCaja();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			seleccionarCierre = con.prepareStatement(sql);
			
			
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				
				existe=true;
				
				unaCierre.setNoFacturaInicio(res.getInt("factura_inicio"));
				unaCierre.setNoFacturaFinal(res.getInt("factura_ultima"));
				unaCierre.setEfectivo(res.getBigDecimal("total_efectivo"));
				unaCierre.setCredito(res.getBigDecimal("total_credito"));
				unaCierre.setTarjeta(res.getBigDecimal("total_tarjeta"));
				unaCierre.setTotal(res.getBigDecimal("total"));
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarCierre != null)seleccionarCierre.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return unaCierre;
			}
			else return null;
		
	}

}
