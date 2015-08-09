package Modelo;

import java.math.BigDecimal;

public class CierreCaja {
	private Integer id;
	private String fecha;
	private Integer noFacturaInicio;
	private Integer noFacturaFinal;
	private BigDecimal total=new BigDecimal(0.0);
	private BigDecimal efectivo=new BigDecimal(0.0);
	private BigDecimal tarjeta=new BigDecimal(0.0);
	private BigDecimal credito=new BigDecimal(0.0);
	
	public void setId(Integer i){
		id=i;
	}
	public Integer getId(){
		return id;
	}
	public void setCredito(BigDecimal c){
		credito=c;
	}
	public BigDecimal getCredito(){
		return credito;
	}
	
	public void setEfectivo(BigDecimal e){
		efectivo=e;
	}
	public BigDecimal getEfectivo(){
		return efectivo;
	}
	
	
	public void setTarjeta(BigDecimal t){
		tarjeta=t;
	}
	public BigDecimal getTarjeta(){
		return tarjeta;
	}
	
	
	
	
	public void setTotal(BigDecimal t){
		total=t;
	}
	public BigDecimal getTotal(){
		return total;
	}
	
	public void setNoFacturaFinal(Integer i){
		noFacturaFinal=i;
	}
	public Integer getNoFacturaFinal(){
		return noFacturaFinal;
	}
	
	public void setNoFacturaInicio(Integer i){
		noFacturaInicio=i;
	}
	public Integer getNoFacturaInicio(){
		return noFacturaInicio;
	}
	
	public void setFecha(String f){
		fecha=f;
	}
	public String getFecha(){
		return fecha;
	}
	

}
