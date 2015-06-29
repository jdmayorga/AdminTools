package View;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import Modelo.Articulo;
import Modelo.ArticuloDao;
import Modelo.CodBarra;
import Modelo.Conexion;
import Modelo.DetalleFactura;
import Modelo.DetalleFacturaProveedor;

import javax.swing.table.DefaultTableModel;

public class DmtFacturaProveedores extends AbstractTableModel {
	
	final private String []columnNames= {
			"Id Articulo", "Nombre", "Cantidad", "Precio Unidad","Impuesto", "Total"
		};
	private List<DetalleFacturaProveedor> detallesFactura=new ArrayList<DetalleFacturaProveedor>();
	private double totalCompra=0;
	
	public DmtFacturaProveedores(){
		//datosVacios();
	
	}
	
	public void agregarDetalle(){
		//JOptionPane.showMessageDialog(null,detallesFactura.size() );
		for(int x=0;x<detallesFactura.size();x++){
			
			//JOptionPane.showMessageDialog(null,detallesFactura.get(x).getArticulo() );
			if(detallesFactura.get(x).getArticulo().getId()<0){
				//JOptionPane.showMessageDialog(null,detallesFactura.get(x).getArticulo()+ "..Eliminando");
				detallesFactura.remove(x);
				
			}
		}
		DetalleFacturaProveedor uno =new DetalleFacturaProveedor();
		detallesFactura.add(uno);
		fireTableDataChanged();
		//JOptionPane.showMessageDialog(null,detallesFactura.size() );
	}
	public void setTotalCompra(double t){
		totalCompra+=t;
	}
	public double getTotalCompra(){
		return totalCompra;
	}
	public void agregarDetalle(DetalleFacturaProveedor detalle) {
		detallesFactura.add(detalle);
        fireTableDataChanged();
    }
	
	public void setArticulo(Articulo a, int row){
		detallesFactura.get(row).setListArticulos(a);
	}
	

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
		        	
		        		if(detallesFactura.get(rowIndex).getCantidad().doubleValue()!=0)
		        			return detallesFactura.get(rowIndex).getCantidad();
		        		else
		        			return null;
		        	
		        case 3:
		        	
		        		if(detallesFactura.get(rowIndex).getPrecioCompra().doubleValue()!=0){
		        			return detallesFactura.get(rowIndex).getPrecioCompra();
		        			
		        			
		        		}
		        		else
		        			return null;
		        
		        case 4:
		        	
		        		if(detallesFactura.get(rowIndex).getPrecioCompra().doubleValue()!=0){
		        			return detallesFactura.get(rowIndex).getImpuesto();
		        		}
		        		else
		        			return null;
		        	
		        case 5:
		        	
		        		if(detallesFactura.get(rowIndex).getPrecioCompra().doubleValue()!=0){
		        			return detallesFactura.get(rowIndex).getTotal();
		        		}
		        		else
		        			return null;
		        	
		        default:
		            return null;
				}
	}
	}
	
	
	public void setDetalles(List<DetalleFacturaProveedor> d){
		detallesFactura.clear();
		if(d!=null){
			for(int x=0;x<d.size();x++){
				detallesFactura.add(d.get(x));
			}
		}else
			agregarDetalle();
		//detallesFactura=d;
		fireTableDataChanged();
	}
	
	public DetalleFacturaProveedor getDetalle(int row){
		return detallesFactura.get(row);
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		DetalleFacturaProveedor detalle = detallesFactura.get(rowIndex);
		//JOptionPane.showMessageDialog(null, value);
		String v=(String) value;
		//JOptionPane.showMessageDialog(null, "Columan"+columnIndex+" fila"+rowIndex);
		switch(columnIndex){
			case 0:
				try{
					int id=Integer.parseInt(v);
					detallesFactura.get(rowIndex).getArticulo().setId(id);
					
					//this.fireTableDataChanged();
					//fireTableCellUpdated(row,col);
					//fireTableDataChanged();
				}catch(NumberFormatException e){
					CodBarra cod=new CodBarra();
					cod.setCodigoBarra(v);
					detallesFactura.get(rowIndex).getArticulo().getCodBarra().add(cod);
					detallesFactura.get(rowIndex).getArticulo().setId(-2);
					//detallesFactura.get(rowIndex).getArticulo().getCodBarra().add(e)
				}
				this.fireTableCellUpdated(rowIndex, columnIndex);
					break;
			case 2:
				
				detallesFactura.get(rowIndex).setCantidad(new BigDecimal(v));
				fireTableCellUpdated(rowIndex, columnIndex);
					//fireTableDataChanged();
				break;
			case 3:
				detallesFactura.get(rowIndex).setPrecioCompra(new BigDecimal(v));
				fireTableCellUpdated(rowIndex, columnIndex);
				
				break;
		}
        /*switch (columnIndex) {
            case 0:
            	try{
            		int ii=Integer.parseInt(v);
            		ArticuloDao uno=new ArticuloDao(conn);
            		detalle.setListArticulos(uno.buscarArticulo(ii));
            		
            		JOptionPane.showMessageDialog(null, detalle.getArticulo().getPrecio());
            		detalle.setCantidad(1);
            		double precio=detalle.getArticulo().getPrecio();
            		double cantidad=detalle.getCantidad();
                	double total=precio*cantidad;
                	detalle.setTotal(total);
                	//se obtiene el porcentaje de impuesto
                	double porcentaImpuesto =((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1;
                	
                	double totalsiniva=(detalle.getTotal())/(porcentaImpuesto);
                	double totaliva=total-totalsiniva;
                	JOptionPane.showMessageDialog(null,"Porcentaje:"+porcentaImpuesto+" Precio:"+detalle.getArticulo().getPrecio()+" impuesto Total:"+totalsiniva+" Cantidad:"+cantidad+" Total:"+detalle.getTotal());
                	detalle.setImpuesto(totalsiniva);
                	DetalleFacturaProveedor newDetalle=new DetalleFacturaProveedor();
                	detallesFactura.add(newDetalle);
            		fireTableDataChanged();
            		//detalle.getArticulo().setId(ii);
            	}catch(NumberFormatException nfe){
            		JOptionPane.showMessageDialog(null, nfe.toString());
            	}
            	
            	break;
            case 2:
            	int ii=Integer.parseInt(v);
            	//articulo.setMarca((String) value)
            	detalle.setCantidad(ii);
            	double total=detalle.getArticulo().getPrecio()*detalle.getCantidad();
            	detalle.setTotal(total);
            	
            	double porcentaImpuesto =((Double.parseDouble(detalle.getArticulo().getImpuestoObj().getPorcentaje())  )/100)+1;
            	
            	double totalsiniva=(detalle.getTotal())/(porcentaImpuesto);
            	double totaliva=total-totalsiniva;
            
            	detalle.setImpuesto(totaliva);
            	fireTableDataChanged();
            
        }*/

       // fireTableCellUpdated(rowIndex, columnIndex);
    }


@Override
public Class getColumnClass(int columnIndex) {
	//        return getValueAt(0, columnIndex).getClass();
    return String.class;
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
	boolean resul=false;
	if(columnIndex==0)
		resul= true;
	if(columnIndex==1)
		resul=false;
	if(columnIndex==2)
		resul=true;
	if(columnIndex==3)
		resul=true;
	if(columnIndex==4)
		resul=false;
	if(columnIndex==5)
		resul=false;
	
	
	return resul;
}
public DetalleFacturaProveedor getDetalles(int index) {
	// TODO Auto-generated method stub
	return detallesFactura.get(index);
}


public List<DetalleFacturaProveedor> getDetalles() {
	// TODO Auto-generated method stub
	return detallesFactura;
}

}
