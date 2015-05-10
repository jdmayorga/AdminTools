package Modelo;




public class DetalleFacturaProveedor {
	private Articulo articulo;
	private double cantidad;
	private double impuesto;
	private double total;
	private double precioCompra;
	
	

	
	public DetalleFacturaProveedor(){
		articulo=new Articulo();
	}
	public DetalleFacturaProveedor(Articulo a,double c,double i,double t){
		
	}
	
	public void setPrecioCompra(double p){
		precioCompra=p;
	}
	public double getPrecioCompra(){
		return precioCompra;
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
