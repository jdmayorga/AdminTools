package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Modelo.Marca;
import Modelo.MarcaDao;
import View.ViewCrearMarca;

public class CtlMarca implements ActionListener {
	
	private ViewCrearMarca viewMarca;
	private MarcaDao myMarcaDao;
	private Marca myMarca;
	
	boolean resultaOperacion=false;
	
	
	public CtlMarca(ViewCrearMarca view,MarcaDao m){
		this.viewMarca=view;
		this.myMarcaDao=m;
		
	}
	public void setMyMarcaDao(MarcaDao m){
		myMarcaDao=m;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		myMarca=new Marca();
		myMarca.setMarca(viewMarca.getTxtMarca().getText());
		myMarca.setObservacion(viewMarca.getTxtObservacion().getText());
		
		switch (comando){
		
		
			case "GUARDAR":
				
				if(myMarcaDao.registrarMarca(myMarca)){//se ejecuta la accion de guardar
					JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente","Informaci�n",JOptionPane.INFORMATION_MESSAGE);
					myMarca.setId(myMarcaDao.getIdMarcaRegistrada());//se completa el proveedor guardado con el ID asignado por la BD
					resultaOperacion=true;
					viewMarca.setVisible(false);
					this.viewMarca.dispose(); 
				}
				else{
					JOptionPane.showMessageDialog(null, "No se Registro");
					resultaOperacion=false;
					viewMarca.setVisible(false);
					this.viewMarca.dispose(); 
				}
				
				break;
				
			case "ACTUALIZAR":
				
				if(myMarcaDao.actualizarMarca(this.viewMarca.getMarca())){//ejecuta el metodo actualize en el Dao y espera que devuelva true o false
					JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente","Informaci�n",JOptionPane.INFORMATION_MESSAGE);
					viewMarca.setVisible(false);
					resultaOperacion=true;
				}
				else{
					JOptionPane.showMessageDialog(null, "No se Registro");
					resultaOperacion=false;
				}
				break;
		}
		
	}
	public Marca getMarca(){
		
		myMarca.setMarca(this.viewMarca.getTxtMarca().getText());
		myMarca.setObservacion(this.viewMarca.getTxtObservacion().getText());
		return myMarca;
	}
	public boolean agregarMarca(){
		this.viewMarca.setVisible(true);
		return resultaOperacion;
	}
	
	public boolean actualizarMarca(){
		this.viewMarca.setVisible(true);
		return resultaOperacion;
	}

}
