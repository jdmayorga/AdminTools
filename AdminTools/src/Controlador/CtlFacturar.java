package Controlador;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Modelo.DetalleFactura;
import Modelo.FacturaDao;
import View.ViewFacturar;

public class CtlFacturar extends KeyAdapter implements ActionListener, MouseListener, TableModelListener,KeyEventDispatcher, WindowListener  {
	private ViewFacturar view;
	private FacturaDao facturaDao=new FacturaDao();
	
	public CtlFacturar(ViewFacturar v){
		view=v;
		view.conectarContralador(this);
				
		//conseguir la fecha la facturaa
		view.getTxtFechafactura().setText(facturaDao.getFechaSistema());
		view.getModeloTabla().agregarDetalle();
		//view.setVisible(true);
	
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
		int colum=e.getColumn();
		int row=e.getFirstRow();
		
		
		switch(e.getType()){
		
			case TableModelEvent.UPDATE:
				
				/*//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModelo().getValueAt(row, 0);
					this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					if(myArticulo!=null){
						this.view.getModelo().setArticulo(myArticulo, row);
						//this.view.getModelo().getDetalle(row).setCantidad(1);
						boolean toggle = false;
						boolean extend = false;
						this.view.getTablaArticulos().requestFocus();
							
						this.view.getTablaArticulos().changeSelection(row,colum+2, toggle, extend);
							
						calcularTotal(this.view.getModelo().getDetalle(row));
					}else{
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
					}
					
					
				}
				if(colum==2){
					calcularTotal(this.view.getModelo().getDetalle(row));
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row,colum+1, toggle, extend);
				}
				if(colum==3){
					calcularTotal(this.view.getModelo().getDetalle(row));
					boolean toggle = false;
					boolean extend = false;
					this.view.getTablaArticulos().requestFocus();
						
					this.view.getTablaArticulos().changeSelection(row+1,0, toggle, extend);				
				}
				
				*/
			break;
		}
		
	}

	

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
			
		/*if(e.getKeyCode()==KeyEvent.VK_F1){
			JOptionPane.showMessageDialog(view,"Se presiono la tecla f1 "+ e.getKeyCode());
			
		}*/
		//JOptionPane.showMessageDialog(view,"Se presiono la tecla f1 "+ e.getID());
		
		if(e.getID() == KeyEvent.KEY_PRESSED){
			JOptionPane.showMessageDialog(view,"Se presiono la tecla f1 "+ e.getKeyCode()+" ,componente:"+e.getSource());
		}
		
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(view, e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
