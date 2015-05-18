package Modelo;

import java.sql.*;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Clase que permite conectar con la base de datos
 * @author jdmayorga
 *
 */
public class Conexion {
	
	
	
	private  BasicDataSource basicDataSource;
   private DataSource dataSource;
   static String bd = "texaco";
   static String login = "root";
   static String password = "jdmm123";
   static String url = "jdbc:mysql://localhost/"+bd;
   static String driver="com.mysql.jdbc.Driver";

   Connection conn = null;

   /** Constructor de DbConnection */
   public Conexion() {
	   BasicDataSource basicDataSource = new BasicDataSource();
	   
	   basicDataSource.setDriverClassName(driver);
	   basicDataSource.setUsername(login);
	   basicDataSource.setPassword(password);
	   basicDataSource.setUrl(url);
	   basicDataSource.setMaxActive(50);
	  
	   
	  // basicDataSource.setValidationQuery("select 1");

	    dataSource = basicDataSource;
	   
	   try {

		   conn=basicDataSource.getConnection();// dataSource.getConnection();

           if(conn!=null){

               //JOptionPane.showMessageDialog(null, "Conectado");
               System.out.println("Conección a base de datos "+bd+" OK");

           }

       } catch (SQLException e) {

           System.out.println(e);

       }/*finally{


               try {

            	   conn.close();

               } catch (SQLException ex) {

                   System.out.println(ex);

               }


       }
	   
	   
	   
	   
     /* try{
         //obtenemos el driver de para mysql
         Class.forName(driver);
         //obtenemos la conexión
         conn = DriverManager.getConnection(url,login,password);

         if (conn!=null){
            System.out.println("Conección a base de datos "+bd+" OK");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }*/
	  // conn.p
   }
   
   public DataSource getDataSource(){
	   return dataSource;
   }
   /**Permite retornar la conexión*/
   public Connection getConnection(){
	  
      return conn;
   }

   public void desconectar(){
      //conn = null;
      try {
		conn.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

}