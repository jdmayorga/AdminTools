package Modelo;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
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
	
	private static InputStream factura=null;
	private static InputStream facturaCompra=null;
	private static InputStream facturaReimpresion=null;
	private static InputStream cierreCaja=null;
	
	private static JasperReport	reportFactura;
	private static JasperReport	reportFacturaCompra;
	private static JasperReport	reportFacturaReimpresion;
	private static JasperReport	reportFacturaCierreCaja;
	
	
	public static void loadFileReport(){
		
		factura=AbstractJasperReports.class.getResourceAsStream("/Reportes/factura_texaco.jasper");
		facturaCompra=AbstractJasperReports.class.getResourceAsStream("/Reportes/Factura_Compra_Saint_Paul.jasper");
		facturaReimpresion=AbstractJasperReports.class.getResourceAsStream("/Reportes/Factura_Saint_Paul_Reimpresion.jasper");
		cierreCaja=AbstractJasperReports.class.getResourceAsStream("/Reportes/Cierre_Caja_Texaco.jasper");
		
		
		try {
			reportFactura = (JasperReport) JRLoader.loadObject( factura );
			reportFacturaCompra = (JasperReport) JRLoader.loadObject( facturaCompra );
			reportFacturaReimpresion= (JasperReport) JRLoader.loadObject( facturaReimpresion );
			reportFacturaCierreCaja= (JasperReport) JRLoader.loadObject( cierreCaja );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void createReport( Connection conn, int tipoReporte,Integer idFactura )
	{
		 Map parametros = new HashMap();
		 parametros.put("numero_factura",idFactura);
		 
		try {
			
			if(tipoReporte==1){
				reportFilled = JasperFillManager.fillReport( reportFactura, parametros, conn );
			}
			if(tipoReporte==2){
				reportFilled = JasperFillManager.fillReport( reportFacturaCompra, parametros, conn );
			}
			if(tipoReporte==3){
				reportFilled = JasperFillManager.fillReport( reportFacturaReimpresion, parametros, conn );
			}
			if(tipoReporte==4){
				reportFilled = JasperFillManager.fillReport( reportFacturaCierreCaja, parametros, conn );
			}
			
			
			
			
			
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
			JasperPrintManager.printReport(reportFilled, false);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void showViewer(Window view)
	{
		JDialog viewer2 = new JDialog(view,"Vista previa del reporte", Dialog.ModalityType.DOCUMENT_MODAL);
		viewer2.setSize(900,750);
		viewer2.setLocationRelativeTo(null);
		
		
		JasperViewer viewer3 = new JasperViewer( reportFilled );
		//viewer.setAlwaysOnTop( true );
		//viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		/*viewer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				viewer.setVisible(false);
				//viewer.dispose();
			}
		});*/
		//viewer2.setTitle("Factura");
		viewer2.getContentPane().add(viewer3.getContentPane());
		viewer2.setVisible( true );
		//viewer.setVisible( true );
		
		
	}
	
	 public static void Imprimir2(){  
		 
		 
		 
		 
		 try {


		        //String report = JasperCompileManager.compileReportToFile(sourceFileName);


		        //JasperPrint jasperPrint = JasperFillManager.fillReport(report, para, ds);


		        PrinterJob printerJob = PrinterJob.getPrinterJob();


		        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
		        printerJob.defaultPage(pageFormat);

		        int selectedService = 0;


		        AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("\\\\TEXACO-PC\\EPSON L210 Series", null));


		        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);

		        try {
		            printerJob.setPrintService(printService[selectedService]);

		        } catch (Exception e) {

		            System.out.println(e);
		        }
		        JRPrintServiceExporter exporter;
		        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		        printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
		        printRequestAttributeSet.add(new Copies(1));

		        // these are deprecated
		        exporter = new JRPrintServiceExporter();
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, reportFilled);
		        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
		        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
		        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
		        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
		        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
		        exporter.exportReport();

		    } catch (JRException e) {
		        e.printStackTrace();
		    }
		//}   
		         
		         
		         
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
