package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Modelo.DetalleFactura;
import Modelo.DetalleFacturaProveedor;

public class TablaModeloFactura extends AbstractTableModel {
	final private String []columnNames= {
			"Id Articulo", "Articulo", "Precio Unidad", "Cantidad","SubTotal","Impuesto", "Total"
		};
	private List<DetalleFactura> detallesFactura=new ArrayList<DetalleFactura>();
	
	
	@Override
	public String getColumnName(int columnIndex) {
	        return columnNames[columnIndex];
	        
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
		return null;
	}

}
