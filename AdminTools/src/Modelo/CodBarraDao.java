package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CodBarraDao {
	Conexion conexion=null;
	private PreparedStatement insertarNuevoCodBarras=null;
	private PreparedStatement eliminarCodsBarra = null;
	private PreparedStatement buscarCodigos=null;
	private PreparedStatement eliminarCodsPorCodBarra=null;
	public CodBarraDao(Conexion conn){
		conexion=conn;
		try{
			insertarNuevoCodBarras=conexion.getConnection().prepareStatement( "INSERT INTO codigos_articulos(codigo_articulo,codigo_barra) VALUES (?,?)");
			eliminarCodsBarra=conexion.getConnection().prepareStatement("DELETE FROM codigos_articulos WHERE codigo_articulo = ?");
			eliminarCodsPorCodBarra=conexion.getConnection().prepareStatement("DELETE FROM codigos_articulos WHERE codigo_barra = ? and codigo_articulo= ?");
			buscarCodigos=conexion.getConnection().prepareStatement("SELECT codigo_articulo,codigo_barra FROM codigos_articulos where codigo_articulo =  ?");
			
		}catch ( SQLException excepcionSql )
		{
			excepcionSql.printStackTrace();
			System.exit( 1 );
		} // fin de catch
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< selecciona de la Bd todas las MArcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<CodBarra> getCodsArticulo(int codArticulo){
		List<CodBarra> cods=new ArrayList<CodBarra>();
		ResultSet res=null;
		
		boolean existe=false;
		try {
			
			
			buscarCodigos.setInt(1, codArticulo);
			res = buscarCodigos.executeQuery();
			while(res.next()){
				CodBarra unCod=new CodBarra();
				existe=true;

				unCod.setCodArticulo(res.getInt("codigo_articulo"));
				unCod.setCodigoBarra(res.getString("codigo_barra"));
				
				cods.add(unCod);
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
				return cods;
			}
			else return null;
			
		}
	
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarCodigo(int idArticulo){
		int resultado=0;
		
		try {
			
				eliminarCodsBarra.setInt( 1, idArticulo );
				resultado=eliminarCodsBarra.executeUpdate();
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarCodigo(CodBarra cod){
		int resultado=0;
		
		try {
			
				eliminarCodsPorCodBarra.setString( 1, cod.getCodigoBarra() );
				eliminarCodsPorCodBarra.setInt( 2, cod.getCodArticulo() );
				
				
				resultado=eliminarCodsPorCodBarra.executeUpdate();
		
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de codigos de barra>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarCodsBarra(List<CodBarra> codsBarras){
		
		try{
			for(int x=0;x<codsBarras.size();x++){
				insertarNuevoCodBarras.setInt(1, codsBarras.get(x).getCodArticulo());
				insertarNuevoCodBarras.setString(2,codsBarras.get(x).getCodigoBarra() );
				insertarNuevoCodBarras.executeUpdate();
				
			}
			return true;
		}catch (SQLException e) {
				e.printStackTrace();
				conexion.desconectar();
	            return false;
			}
		
			
	

	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de codigos de barra>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarCodsBarra(CodBarra codBarra){
		
		try{
			
				insertarNuevoCodBarras.setInt(1, codBarra.getCodArticulo());
				insertarNuevoCodBarras.setString(2,codBarra.getCodigoBarra() );
				insertarNuevoCodBarras.executeUpdate();
				
			
			return true;
		}catch (SQLException e) {
				e.printStackTrace();
				conexion.desconectar();
	            return false;
			}
		
			
	

	}
}
