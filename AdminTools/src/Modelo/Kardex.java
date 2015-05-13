package Modelo;

public class Kardex {
	private int id;
	private String noDocumento;
	private Articulo articulo=new Articulo();
	private Bodega bodega=new Bodega();
	private double entrada=0;
	private double salida=0.0;
	private double existencia=0.0;
	
	public double getExistencia(){
		return existencia;
	}
	public void setExistencia(double e){
		existencia=e;
	}
	
	public void incremetarExistencia(double i){
		existencia+=i;
	}
	public void decrementarExistencia(double d){
		existencia-=d;
	}
	
	private String fecha;
	
	public String getFecha(){
		return fecha;
	}
	public void setFecha(String f){
		fecha=f;
	}
	public double getSalida(){
		return salida;
	}
	public void setSalida(double s){
		salida=s;
	}
	public double getEntrada(){
		return entrada;
	}
	public void setEntrada(double e){
		entrada=e;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int i){
		id=i;
	}
	
	public String getNoDocumento(){
		return noDocumento;
	}
	public void setNoDocumento(String n){
		noDocumento=n;
	}
	
	public Articulo getArticulo(){
		return articulo;
	}
	public void setArticulo(Articulo a){
		articulo=a;
	}
	

	public void setBodega(Bodega b){
		bodega=b;
	}
	public Bodega getBodega(){
		return bodega;
	}

}
