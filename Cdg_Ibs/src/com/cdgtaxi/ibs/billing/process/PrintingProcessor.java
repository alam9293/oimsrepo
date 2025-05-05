package com.cdgtaxi.ibs.billing.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;

public class PrintingProcessor extends Thread{
	private static final Logger logger = Logger.getLogger(PrintingProcessor.class);
	private List<BmtbInvoiceHeader> invoices;
	private Long printReqId;
	private List<Listitem> printers;
	@SuppressWarnings("unchecked")
	private Map printingProperties;
	private static ThreadPoolManager threadPoolManager = null;
	
	private int sorting;
	public static final int SORTING_DEFAULT = 0;
	public static final int SORTING_ACCOUNT = 1;
	
	public PrintingProcessor(Long printReqId, List<BmtbInvoiceHeader> invoices, List<Listitem> printers,
			Map printingProperties, int sorting) throws IOException{
		this.printReqId = printReqId;
		this.invoices = invoices;
		this.printers = printers;
		this.printingProperties = printingProperties;
		if(threadPoolManager == null) threadPoolManager = new ThreadPoolManager();
		this.sorting = sorting;
	}
	
	public void run(){
		try{
			String directoryStr = printingProperties.get("printing.pdf.file.location")+"printing_req_"+printReqId+"/";
			logger.info("Making directory:"+directoryStr);
			File directory = new File(directoryStr);
			directory.mkdir();
			
			logger.info("Proceeding to generate hard copies into that directory");
			for(BmtbInvoiceHeader invoice : invoices){
				if(invoice.getBmtbInvoiceFile()==null){
					logger.warn("Invoice does not have invoice file record:"+invoice.getInvoiceNo());
					continue;
				}
				if(invoice.getAmtbAccountByAccountNo().getAmtbSubscTos() != null) {
					Set<AmtbSubscTo> subs = (Set<AmtbSubscTo>) invoice.getAmtbAccountByAccountNo().getAmtbSubscTos();
					String prepaid = "yes";
					
					for(AmtbSubscTo sub : subs)
					{
//						System.out.println("check prepaid > "+sub.getComp_id().getPmtbProductType().getPrepaid());
						if(sub.getComp_id().getPmtbProductType().getPrepaid().equals("N"))
						{
							prepaid = "no";
							break;
						}
					}
					if(prepaid.equals("yes")) {
						logger.warn("invoice's ("+invoice.getInvoiceNo()+") account ("+invoice.getAmtbAccountByAccountNo().getAccountNo()+") is prepaid, skipping record");
						continue;
					}
				}
				threadPoolManager.runTask(new PdfGenHandler(directoryStr, invoice));
			}
			
			while(true){
				Thread.sleep(1000*60);
				
				logger.info("Thread Pool Queue: "+threadPoolManager.threadPool.getQueue().size());
				logger.info("Thread Pool Active: "+threadPoolManager.threadPool.getActiveCount());
				if(threadPoolManager.threadPool.getQueue().size() == 0 &&
				threadPoolManager.threadPool.getActiveCount() == 0){
					break;
				}
			}
			
			File[] invoicesToBePrinted = directory.listFiles();
			logger.info("Total no. of files located inside the folder:"+invoicesToBePrinted.length);
			
			//Enhancement 11 May 2010 - #979 Print Invoice Sorting
			logger.info("Sorting the files by their names...");
			TreeSet<File> sortedFiles = new TreeSet<File>(
				new Comparator<File>() {
					public int compare(File file1, File file2) {
						int result = 0;
						
						switch (sorting) {
						case SORTING_ACCOUNT:
							String file1Name = file1.getName().split("\\.")[0];
							int custNo1 = Integer.parseInt(file1Name.split("_")[0]);
							int invoiceNo1 = Integer.parseInt(file1Name.split("_")[1]);
							
							String file2Name = file2.getName().split("\\.")[0];
							int custNo2 = Integer.parseInt(file2Name.split("_")[0]);
							int invoiceNo2 = Integer.parseInt(file2Name.split("_")[1]);
							
							if(custNo1 == custNo2){
								if(invoiceNo1 < invoiceNo2) result = -1;
								else if(invoiceNo1 == invoiceNo2) result = 0;
								else result = 1;
							}
							else if(custNo1 < custNo2) result = -1;
							else result = 1;
							
							break;
						case SORTING_DEFAULT:
							String dFile1Name = file1.getName().split("\\.")[0];
							String dFile2Name = file2.getName().split("\\.")[0];
							
							int dInvoiceNo1 = Integer.parseInt(dFile1Name.split("_")[0]);
							int dInvoiceNo2 = Integer.parseInt(dFile2Name.split("_")[0]);
							
							if(dInvoiceNo1 < dInvoiceNo2) result = -1;
							else if(dInvoiceNo1 == dInvoiceNo2) result = 0;
							else result = 1;
							
							break;
						}
						
						return result;
					}
				}
			);
			sortedFiles.addAll(Arrays.asList(invoicesToBePrinted));
			
			if(printingProperties.get("printing.log.sorting")!=null &&
					printingProperties.get("printing.log.sorting").equals("true")){
				logger.info("After sorting...");
				for(File file : sortedFiles){
					logger.info(file.getName());
				}
			}
			
			int counter = 0;
			for(File file : sortedFiles){
				String fileName = file.getName();
				logger.info("Printing file "+ fileName +" ...");
				String pdfpDirectory = (String) printingProperties.get("printing.pdfp.directory");
				
				String printer = (String)printers.get(counter).getLabel();
				counter++; if(counter==printers.size())counter = 0;
				
				String duplexMode = getDuplexMode(fileName, printer);
				/*String duplexMode = (String) printingProperties.get("printing.duplex");
				if(duplexMode==null) duplexMode = "1";*/	
				
				/*
				 * -d duplex : set duplex mode (if supported by printer)
        		 *	1=none, 2=long side, 3=short side
				 */
				Process process = Runtime.getRuntime().exec("\""+pdfpDirectory+"pdfp.exe\" -p \""+printer+"\" -o 1 -d "+duplexMode+" \""+file.getAbsolutePath()+"\"");
				
				//Interval Timing
				int printingInterval = new Integer((String)printingProperties.get("printing.interval")).intValue();
				if(printingInterval!=0) Thread.sleep(printingInterval*1000);
				
				String delete = (String)printingProperties.get("printing.pdf.file.delete");
				if(delete.equalsIgnoreCase("true")){
					logger.info("delete physical file on the server");
					file.delete();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String getDuplexMode(String fileName, String printerName){
		//last attr is printing type
		int printTypeIndex = fileName.lastIndexOf('_');
		String printType = fileName.substring(printTypeIndex+1, printTypeIndex+2);
		String duplexMode = (NonConfigurableConstants.INVOICE_PRINTING_SINGLE_SIDED.equals(printType))? "1" : "2";
		//[file name] -  [printer name] - [printer type & Duplex] 
		logger.info("[" +fileName + "] - [" + printerName + "] - [" + printType + " " + duplexMode + "]");
		return duplexMode;
	}
	
	
	private class ThreadPoolManager {
		private final Logger logger = Logger.getLogger(ThreadPoolManager.class);
	    long keepAliveTime = 30;
	    ThreadPoolExecutor threadPool = null;

	    public ThreadPoolManager() throws IOException
	    {
	    	final ArrayBlockingQueue<Runnable> queue =
	    		new ArrayBlockingQueue<Runnable>(9999999,true); //queue size

	    	threadPool = new ThreadPoolExecutor(10, 
	    			10,
	                keepAliveTime, TimeUnit.SECONDS, queue);
	    }

	    public void runTask(PdfGenHandler handler)
	    {
	    	try{
	    		threadPool.execute(handler);
	    	}
	    	catch(RejectedExecutionException ree){
	    		logger.error("Thread Pool Full");
	    		ree.printStackTrace();
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	}
	
	private class PdfGenHandler extends Thread{
		private BmtbInvoiceHeader invoice;
		private String directoryStr;
		
		public PdfGenHandler(String directoryStr, BmtbInvoiceHeader invoice){
			this.invoice = invoice;
			this.directoryStr = directoryStr;
		}
		
		public void run(){
			try{
				logger.info("Creating physical file: invoice no "+invoice.getInvoiceNo());
				
				AmtbAccount account = invoice.getAmtbAccountByAccountNo();
				while(account.getAmtbAccount()!=null)
					account = account.getAmtbAccount();
				
				//Enhancement 11 May 2010 - #979 Print Invoice Sorting
				String fileName;
				if(sorting == SORTING_ACCOUNT){
					fileName = account.getCustNo()+"_"+invoice.getInvoiceNo();
				}
				else {
					fileName = invoice.getInvoiceNo().toString();
				}
				fileName = fileName + "_" + account.getInvoicePrinting();
				
				File invoiceFile = new File(directoryStr+fileName+".pdf");
				invoiceFile.createNewFile();
				
				FileOutputStream fos = new FileOutputStream(invoiceFile);
				Blob blob = invoice.getBmtbInvoiceFile().getInvoiceFile();
				fos.write(blob.getBytes(1, (int) blob.length()));
				fos.close();
				logger.info("Physical file created: invoice no "+invoice.getInvoiceNo());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
