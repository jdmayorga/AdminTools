package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import View.ViewCambioPago;

public class CtlCambioPago implements ActionListener,ItemListener {
	private ViewCambioPago view=null;
	private boolean estadoPago=false;
	private BigDecimal efectivo;
	private BigDecimal cambio;
	
	private BigDecimal total;
	
	
	public CtlCambioPago(ViewCambioPago v,BigDecimal t){
		total=t;
		view=v;
		view.conectarCtl(this);
		view.getTxtEfectivo().requestFocusInWindow();
		//view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		switch (comando){
		case "CAMBIO":
			//parseBigDecimal( view.getTxtEfectivo().getText());
			efectivo=new BigDecimal(view.getTxtEfectivo().getText());
			cambio=efectivo.subtract(total);
			view.getTxtCambio().setText(""+cambio.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			estadoPago=true;
		break;
		}
	}

	public boolean pagar() {
		// TODO Auto-generated method stub
		view.setVisible(true);
		return estadoPago;
	}
	public BigDecimal getEfectivo(){
		return efectivo;
	}
	public BigDecimal getCambio(){
		return cambio;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(view.getTglbtnEfectivo().isSelected()){
			//JOptionPane.showMessageDialog(view, "Efectivo");
			view.getPanelTarjeta().setVisible(true);
			view.getPanelEfectivo().setVisible(false);
			view.getTxtEfectivo().requestFocusInWindow();
		}
		
		if(view.getTglbtnTarjetaCredito().isSelected()){
			view.getPanelEfectivo().setVisible(true);
			view.getPanelTarjeta().setVisible(false);
			view.getTxtReferencia().requestFocusInWindow();
			
			//JOptionPane.showMessageDialog(view, "Tarjeta Credito");
		}
	}

}
