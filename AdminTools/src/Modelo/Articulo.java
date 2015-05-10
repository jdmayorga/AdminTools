package Modelo;

import java.util.List;
import java.util.Vector;
 
public class Articulo {
	
	private int codigo=-1;
	private String articulo;
	
	private String marca;
	//private double impuesto;
	private Marca mar=new Marca();
	private Impuesto imp=new Impuesto();
	private List<CodBarra> codigos;
	private double precio;
	private double precioCompra;
	public Articulo(){
		
	}
	
	public Articulo(int c, String a,String m,Impuesto i, Vector<CodBarra> cods, double p){
		codigo=c;
		articulo=a;
		marca=m;
		imp=i;
		codigos=cods;
		precio=p;
	}
	public void setPrecioCompra(double pc){
		precioCompra=pc;
	}
	public double getPrecioCompra(){
		return precioCompra;
	}
	public void setPrecio(double p){
		precio=p;
	}
	public double getPrecio(){
		return precio;
	}
	
	public void setCodBarra(Vector<CodBarra> cods){
		codigos=cods;
		
	}
	public List<CodBarra> getCodBarra(){
		return codigos;
	}
	public void setCodBarras(List<CodBarra> cods){
		codigos=cods;
	}
	public void setId(int c){
		codigo=c;
	}
	public int getId(){
		return codigo;
	}
	
	public Marca getMarcaObj(){
		return mar;
	}
	public void setMarcaObj(Marca m){
		mar=m;
		
	}
	
	public Impuesto getImpuestoObj(){
		return imp;
	}
	public void setImpuestoObj(Impuesto i){
		imp=i;
		
	}
	
	public void setArticulo(String a){
		articulo=a;
	}
	public String getArticulo(){
		return articulo;
	}
	
	
	
	public void setMarca(String m){
		marca=m;
	}
	public String getMarca(){
		return marca;
	}
	
	@Override
	public String toString(){
		return "Id Articulo:"+codigo+", Articulo:"+articulo+", Marca["+mar.toString()+"]"+", Impueso:"+imp.getPorcentaje()+"%"+", Codigos Barra["+codigos+"]";
	}

}
