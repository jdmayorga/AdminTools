package Modelo;

public class Usuario {
	private int codigo;
	private String nombre="";
	private String apellido="";
	private String user="";
	private String permiso="";
	private String pwd="";
	private int tipo_permiso;
	
	public void setCodigo(int c){
		codigo=c;
	}
	public int getCodigo(){
		return codigo;
	}
	public void setTipoPermiso(int tp){
		tipo_permiso=tp;
	}
	public int getTipoPermiso(){
		return tipo_permiso;
	}
	
	public void setNombre(String n){
		nombre=n;
	}
	public String getNombre(){
		return nombre;
	}
	
	public void setApellido(String p){
		apellido=p;
	}
	public String getApellido(){
		return apellido;
	}
	
	public void setUser(String u){
		user=u;
	}
	public String getUser(){
		return user;
	}
	
	public void setPermiso(String p){
		permiso=p;
	}
	public String getPermiso(){
		return permiso;
	}
	
	
	public void setPwd(String p){
		pwd=p;
	}
	public String getPwd(){
		return pwd;
	}
	
	
			

}
