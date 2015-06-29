package Modelo;


public class Requisicion  extends FacturaCompra{
	private int noRequisicion;
	private Departamento departamento=new Departamento();
	
	public void setDepartamento(Departamento d){
		departamento=d;
	}
	public Departamento getDepartamento(){
		return departamento;
	}
	
	public void setNoRequisicion(int n){
		noRequisicion=n;
	}
	public int getNoRequisicion(){
		return noRequisicion;
	}

}
