package Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Factura {
	
	private String fecha;
	private String idFactura;
	private Integer tipoFactura=1;
	private Cliente cliente;
	private List<DetalleFactura> detalles=new ArrayList<DetalleFactura>();
	private BigDecimal totalImpuesto=new BigDecimal(0.0);
	private BigDecimal total=new BigDecimal(0.0);
	private BigDecimal subTotal=new BigDecimal(0.0);
	private BigDecimal totalDescuento=new BigDecimal(0.0);
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
	
	public void setTotalImpuesto(BigDecimal tImp){
		totalImpuesto=totalImpuesto.add(tImp);
	}
	public BigDecimal getTotalImpuesto(){
		return totalImpuesto;
	}
	
	public void setTotal(BigDecimal t){
		total=total.add(t);
	}
	public BigDecimal getTotal(){
		return total;
	}
	
	public void setSubTotal(BigDecimal s){
		subTotal=subTotal.add(s);
	}
	public BigDecimal getSubTotal(){
		return subTotal;
	}
	
	public void setTotalDescuento(BigDecimal s){
		totalDescuento=totalDescuento.add(s);
	}
	public BigDecimal getTotalDescuento(){
		return totalDescuento;
	}
	
	public void resetTotales(){
		totalImpuesto=BigDecimal.ZERO;
		total=BigDecimal.ZERO;
		subTotal=BigDecimal.ZERO;
		totalDescuento=BigDecimal.ZERO;
	}

}
