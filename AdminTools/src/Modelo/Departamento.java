package Modelo;

public class Departamento {
	
	private int codigo_depart;
	private String nombre;
	
	public void setNombre(String n){
		nombre=n;
	}
	public String  getNombre(){
		return nombre;
	}
	
	public void setCodigo_departento(int c){
		codigo_depart=c;
	}
	public int getCodigo_departento(){
		return codigo_depart;
	}

}
