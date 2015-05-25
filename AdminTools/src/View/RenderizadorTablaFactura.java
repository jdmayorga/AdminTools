package View;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class RenderizadorTablaFactura implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		 JTextField editor = new JTextField();
		 editor.setText("");
		    if (value != null)
		      editor.setText(value.toString());
		    
		    editor.setBackground((row % 2 == 0) ? Color.white : Color.cyan);
		    
		    
		   if(column==0){
				   editor.setHorizontalAlignment(SwingConstants.CENTER);
			  }
		   if(column==1){
			   editor.setHorizontalAlignment(SwingConstants.LEFT);
		   }
		   if(column==2){
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   }
		   if(column==3)
			   editor.setHorizontalAlignment(SwingConstants.CENTER);
		   if(column==4)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   if(column==5)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   if(column==6)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   if(column==7)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		 
		    if (isSelected) {
		    	editor.setBackground(new Color(254, 172, 172));
	        }
		 return editor;
	}
	
	

}
