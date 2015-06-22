package Modelo;

import java.util.ArrayList;
import java.util.List;

public class FacturaCompra {
	private String fechaCompra;
	private int noCompra;
	private String idFactura;
	private Integer tipoFactura=1;
	private Proveedor proveedor;
	private List<DetalleFacturaProveedor> detalles=new ArrayList<DetalleFacturaProveedor>();
	private double totalImpuesto=0.0;
	private double total=0.0;
	private double subTotal=0.0;
	private String fechaVencimento=null;
	private String estado;
	private int agregadoAkardex;
	
	public FacturaCompra(){
		
	}
	public int getAgregadoAkardex(){
		return agregadoAkardex;
	}
	public void setAgregadoAkardex(int a){
		agregadoAkardex=a;
	}
	
	public void setNoCompra(int i){
		noCompra=i;
	}
	public int getNoCompra(){
		return noCompra;
	}
	public String getEstado(){
		return estado;
	}
	public void setEstado(String e){
		estado=e;
	}
	public String getFechaVencimento(){
		return fechaVencimento;
	}
	public void setFechaVencimento(String f){
			fechaVencimento=f;
	}
	
	public void setProveedor(Proveedor p){
		proveedor=p;
	}
	public Proveedor getProveedor(){
		return proveedor;
	}
	
	public void setFechaCompra(String f){
		fechaCompra=f;
	}
	public String getFechaCompra(){
		return fechaCompra;
	}
	
	public void setIdFactura(String idF){
		idFactura=idF;
	}
	public String getIdFactura(){
		return idFactura;
	}
	
	public void setTipoFactura(Integer t){
		tipoFactura=t;
	}
	public Integer getTipoFactura(){
		return tipoFactura;
	}
	
	public void setDetalles(List<DetalleFacturaProveedor> d){
		detalles=d;
	}
	public List<DetalleFacturaProveedor> getDetalles(){
		return detalles;
	}
	
	public void setTotalImpuesto(double tImp){
		totalImpuesto=totalImpuesto+tImp;
	}
	public double getTotalImpuesto(){
		return totalImpuesto;
	}
	
	public void setTotal(double t){
		total=total+t;
	}
	public double getTotal(){
		return total;
	}
	public void setSubTotal(double s){
		subTotal+=s;
	}
	public double getSubTotal(){
		return subTotal;
	}

}
