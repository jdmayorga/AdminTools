package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Factura {
	
	private String fecha;
	private String idFactura;
	private Integer tipoFactura=1;
	private Cliente cliente;
	private List<DetalleFactura> detalles=new ArrayList<DetalleFactura>();
	private double totalImpuesto=0.0;
	private double total=0.0;
	private double subTotal=0.0;
	private String fechaVencimento=null;
	private int estado=1;
	
	public int getEstado(){
		return estado;
	}
	public void setEstado(int e){
		estado=e;
	}
	public String getFechaVencimento(){
		return fechaVencimento;
	}
	public void setFechaVencimento(String f){
			fechaVencimento=f;
	}
	
	public void setCliente(Cliente c){
		cliente=c;
	}
	public Cliente getCliente(){
		return cliente;
	}
	
	public void setFecha(String f){
		fecha=f;
	}
	public String getFecha(){
		return fecha;
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
	
	public void setDetalles(List<DetalleFactura> d){
		detalles=d;
	}
	public List<DetalleFactura> getDetalles(){
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
