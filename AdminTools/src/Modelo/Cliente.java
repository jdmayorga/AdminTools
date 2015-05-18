package Modelo;

public class Cliente {
	
	private int id;
	private String nombre;
	private String direccion;
	private String telefono;
	private String celular;
	private String rtn;
	
	public String getRtn(){
		return rtn;
	}
	public void setRtn(String r){
		rtn=r;
	}
	public String getCelular(){
		return celular;
	}
	public void setCelular(String c){
		celular=c;
	}
	public String getTelefono(){
		return telefono;
	}
	public void setTelefono(String t){
		telefono=t;
	}
	
	public String getDereccion(){
		return direccion;
	}
	public void setDireccion(String d){
		direccion=d;
	}
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String n){
		nombre=n;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int i){
		id=i;
	}
	@Override
	public String toString(){
		return "Id:"+id+", rtn:"+rtn+", nombre:"+nombre+", direccion:"+direccion+", telefono:"+telefono+", celular:"+celular;
	}
}
