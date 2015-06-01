package Modelo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
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
		 
		try {
			report = (JasperReport) JRLoader.loadObjectFromFile( path );
			reportFilled = JasperFillManager.fillReport( report, parametros, conn );
		}
		catch( JRException ex ) {
			ex.printStackTrace();
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
		//viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				viewer.setVisible(false);
				//viewer.dispose();
			}
		});
		viewer.setVisible( true );
		viewer.setTitle("Factura");
		
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
