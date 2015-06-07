package Modelo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRException;


public abstract class AbstractJasperReports
{
	private static JasperReport	report;
	private static JasperPrint	reportFilled;
	private static JasperViewer	viewer;
	

	public static void createReportFactura( Connection conn, String path,Integer idFactura )
	{
		 Map parametros = new HashMap();
		 parametros.put("numero_factura",idFactura);
		 
		 
		 
		 
		 InputStream ticketReportStream=null;
		 
		 
		 
		 //ticketReportStream=JReportPrintService.class.getResourceAsStream("/com/floreantpos/jreports/TicketReceiptReport.jasper");
		    /*JasperReport ticketReport=(JasperReport)JRLoader.loadObject(ticketReportStream);
		    JasperPrint jasperPrint=JasperFillManager.fillReport(ticketReport,map,new JRTableModelDataSource(new TicketDataSource(ticket)));
		    JasperViewer.viewReport(jasperPrint,false);
		    JasperPrintManager.printReport(jasperPrint,false);*/
		// Connection conn=null;
		 
		/* try {
			conn=conexion.getPoolConexion().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 
		try {
			
			ticketReportStream=AbstractJasperReports.class.getResourceAsStream("/Reportes/"+path);
			//report = (JasperReport) JRLoader.loadObjectFromFile( path );
			report = (JasperReport) JRLoader.loadObject( ticketReportStream );
			reportFilled = JasperFillManager.fillReport( report, parametros, conn );
			
		}
		catch( JRException ex ) {
			ex.printStackTrace();
		}
		try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
		
	}
	public static void imprimierFactura(){
		try {
			JasperPrintManager.printReport(reportFilled, true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void showViewer()
	{
		viewer = new JasperViewer( reportFilled ,false);
		viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		/*viewer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				viewer.setVisible(false);
				//viewer.dispose();
			}
		});*/
		viewer.setTitle("Factura");
		viewer.setVisible( true );
		
		
	}

	public static void exportToPDF( String destination )
	{
		try { 
			JasperExportManager.exportReportToPdfFile( reportFilled, destination );
		}
		catch( JRException ex ) {
			ex.printStackTrace();
		}
	}
	
}
