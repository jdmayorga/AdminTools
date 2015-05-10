package Modelo;

public class Inventario {
	private Bodega bodega;
	private Articulo articulo;
	private double existencia;
	private double precioVenta;
	
	public Inventario(){
		bodega=new Bodega();
		articulo=new Articulo();
	}
	
	public void setBodega(Bodega b){
		bodega=b;
	}
	public Bodega getBodega(){
		return bodega;
	}
	
	public void setExistencia(double e){
		existencia=e;
	}
	public double getExistencia(){
		return existencia;
	}
	
	public void setPrecioVenta(double p){
		precioVenta=p;
	}
	public double getPrecioVenta(){
		return precioVenta;
	}
	public Articulo getArticulo(){
		return articulo;
	}
	public void setArticulo(Articulo a){
		articulo=a;
	}
	public void incremetarExistencia(double i){
		existencia+=i;
	}
	public void decrementarExistencia(double d){
		existencia-=d;
	}
	
	@Override
	public String toString(){
		return "Articulo["+articulo+"], Bodega["+bodega+"], Existencia:"+existencia+", Precio Venta:"+precioVenta;
	}
}
