package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Proveedor {
	private int id;
	private String nombre;
	private String telefono;
	private String celular;
	private String direccion;
	//Conexion conex= new Conexion();
	
	//contructor vacio
	public Proveedor(){
		
	}
	
	//contructor con todos los valores del proveedor
	public Proveedor(int i,String n,String t,String c,String d){
		nombre=n;
		id=i;
		celular=t;
		telefono=t;
		direccion=d;
	}
	public void setNombre(String n){
		nombre=n;
	}
	public String getNombre(){
		return nombre;
	}
	
	public void setId(int i){
		id=i;
	}
	public int getId(){
		return id;
	}
	
	public void setTelefono(String t){
		
		telefono=t;
	}
	public String getTelefono(){
		return telefono;
	}
	
	public void setCelular(String c){
		
		celular=c;
	}
	public String getCelular(){
		return celular;
	}
	
	public void setDireccion(String d){
		direccion=d;
		
	}
	public String getDireccion(){
		return direccion;
	}
	
	@Override
	public String toString(){
		return "Id:"+id+", Nombre:"+nombre+", Telefono:"+telefono+", Celular:"+celular+"\n Direccion:"+direccion;
	}
	
	
}
