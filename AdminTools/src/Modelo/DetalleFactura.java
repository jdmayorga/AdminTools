package Modelo;

public class DetalleFactura {
	private Articulo articulo=new Articulo();;
	private double cantidad;
	private double impuesto;
	private double total;
	private int idFactura;
	
	public void setIdFactura(int id){
		idFactura=id;
	}
	public int getIdFactura(){
		return idFactura;
	}
	public void setListArticulos(Articulo a){
		articulo=a;
	}
	public Articulo getArticulo(){
		return articulo;
	}
	
	public void setCantidad(double c){
		cantidad=c;
	}
	public double getCantidad(){
		return cantidad;
	}
	
	public void setImpuesto(double i){
		impuesto=i;
	}
	public double getImpuesto(){
		return impuesto;
	}
	
	public void setTotal(double t){
		total=t;
	}
	public double getTotal(){
		return total;
	}

}
