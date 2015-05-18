package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class FacturaDao {
	
	public FacturaDao(){
		
	}
	
	public String getFechaSistema(){
		String fecha="";
		DataSource ds=DBCPDataSourceFactory.getDataSource("mysql");
		Connection conn=null;
		ResultSet res=null;
		Statement instrucions=null;
		try {
			conn=ds.getConnection();
			instrucions = conn.createStatement();
			
			//res=instrucions.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y %h:%i:%s %p') as fecha;");
			res=instrucions.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
			while(res.next()){
				fecha=res.getString("fecha");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			if(res != null) res.close();
            if(instrucions != null)instrucions.close();
            if(conn != null) conn.close();
            
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
			} // fin de catch
		
		
		
		return fecha;
	}

}
