package Modelo;

public class Bodega {
	private int id;
	private String descripcion;
	
	public void setId(int i){
		id=i;
	}
	public int getId(){
		return id;
	}
	public void setDescripcion(String d){
		descripcion =d;
	}
	public String getDescripcion(){
		return descripcion;
	}
	@Override
	public String toString(){
		return "Id:"+id+", Descripcion:"+descripcion;
	}

}
