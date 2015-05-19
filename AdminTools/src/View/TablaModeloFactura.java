package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Modelo.Articulo;
import Modelo.DetalleFactura;
import Modelo.DetalleFacturaProveedor;

public class TablaModeloFactura extends AbstractTableModel {
	final private String []columnNames= {
			"Id Articulo", "Articulo", "Precio Unidad", "Cantidad","Descuento","SubTotal","Impuesto", "Total"
		};
	private List<DetalleFactura> detallesFactura=new ArrayList<DetalleFactura>();
	
	public void agregarDetalle(){
		for(int x=0;x<detallesFactura.size();x++){
			if(detallesFactura.get(x).getArticulo().getId()==-1){
				detallesFactura.remove(x);
			}
		}
		DetalleFactura uno =new DetalleFactura();
		detallesFactura.add(uno);
	}
	@Override
	public String getColumnName(int columnIndex) {
	        return columnNames[columnIndex];
	        
	  }
	public DetalleFactura getDetalle(int row){
		return detallesFactura.get(row);
	}
	public void setArticulo(Articulo a, int row){
		detallesFactura.get(row).setListArticulos(a);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return detallesFactura.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(detallesFactura.get(rowIndex).getArticulo().getId()==-1){
			return null;
		}
		else{
		switch (columnIndex) {
		case 0:
				
			return detallesFactura.get(rowIndex).getArticulo().getId();
		case 1:
			return detallesFactura.get(rowIndex).getArticulo().getArticulo();
		case 2:
		 return detallesFactura.get(rowIndex).getArticulo().getPrecioVenta();
		case 3:
			return detallesFactura.get(rowIndex).getCantidad();
		case 4:
			return detallesFactura.get(rowIndex).getDescuento();
		case 5:
			return  detallesFactura.get(rowIndex).getSubTotal();
		case 6:
			return  detallesFactura.get(rowIndex).getDescuento();
		case 7:
			return  detallesFactura.get(rowIndex).getTotal();
		 default:
	            return null;
		}
		}
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		DetalleFactura detalle = detallesFactura.get(rowIndex);
		String v=(String) value;
		switch(columnIndex){
		case 0:
			detallesFactura.get(rowIndex).getArticulo().setId((Integer.parseInt(v)));
				this.fireTableCellUpdated(rowIndex, columnIndex);
				//this.fireTableDataChanged();
				//fireTableCellUpdated(row,col);
				//fireTableDataChanged();
				
				break;
		case 3:
			
			detallesFactura.get(rowIndex).setCantidad(Double.parseDouble(v));
			fireTableCellUpdated(rowIndex, columnIndex);
				//fireTableDataChanged();
			break;
		case 4:
			detallesFactura.get(rowIndex).setDescuento(Integer.parseInt(v));
			fireTableCellUpdated(rowIndex, columnIndex);
			
			break;
	}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean resul=false;
		if(columnIndex==0)
			resul= true;
		
		if(columnIndex==3)
			resul=true;
		if(columnIndex==4)
			resul=true;
	
		
		
		return resul;
	}

}
