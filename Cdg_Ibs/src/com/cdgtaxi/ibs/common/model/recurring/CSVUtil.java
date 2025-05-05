package com.cdgtaxi.ibs.common.model.recurring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.csvreader.CsvWriter;

public class CSVUtil {
	private static final Logger logger = Logger.getLogger(CSVUtil.class);

	public synchronized static void writeToCsv(String fileLocation, String[] headerToWrite,	String[] contentToWrite, String folderPath, String entity, String labelDateTime, String locationOutput, char escapeChar) throws IOException {

		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(fileLocation).exists();
		try {
			File outputFolder= new File(locationOutput);
			if(!outputFolder.exists())
				outputFolder.mkdir();
			
			String entityTxt = "";
			if(!entity.trim().equals(""))
				entityTxt = "_"+ entity;
			
			String dest = locationOutput + labelDateTime + entityTxt;
			
			File destFile = new File(dest);
			
			if(escapeChar != '|')
			{
				if(!destFile.exists()){
					destFile.mkdir();
				}
			}
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter(fileLocation, true), escapeChar);
			
			if(escapeChar != '|')
				csvOutput.setForceQualifier(true);
			else
				csvOutput.setForceQualifier(false);
			// if the file didn't already exist then we need to write out the
			// header line
			if (!alreadyExists) {
				csvOutput.writeRecord(headerToWrite);
			}
			// else assume that the file already has the correct header line

			// write out content
			csvOutput.writeRecord(contentToWrite);

			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
	}
	
	public synchronized static void writeCsvFileRecDtl(String fileLocation, String[] headerToWrite, String[] contentToWrite, String folderPath, String entity, String labelDateTime, String locationOutput, char escapeChar, RecurringCsvData recurringCsvData) throws IOException {

		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(fileLocation).exists();
		try {		    
		    ICsvBeanWriter beanWriter = null;
		    beanWriter = new CsvBeanWriter(new FileWriter(fileLocation , true), CsvPreference.STANDARD_PREFERENCE);

			final CellProcessor[] processors = new CellProcessor[] { new Optional(), // Reference Id
					new Optional(), //Customer Token
					new Optional(),  //Amount
					new Optional(),  //Currency
//					new Optional(),  //Merchant
//					new Optional(),  //Account
					new Optional(),  //Invoice Date
					new Optional(), // Invoice Number
					new Optional(), // Customer Account No
					new Optional() // CabCharge No
					};

			// if the file didn't already exist then we need to write out the
			// header line
			if (!alreadyExists) {
				beanWriter.writeHeader(headerToWrite);
			}
			
			// else assume that the file already has the correct header line

			// write out content
			beanWriter.write(recurringCsvData, recurringCsvData.getHeadersvariable(), processors);

			beanWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
	}
	public synchronized static void writeCsvFile(String fileLocation, String[] headerToWrite, String[] contentToWrite, String folderPath, String entity, String labelDateTime, String locationOutput, char escapeChar) throws IOException {

		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(fileLocation).exists();
		try {
		    System.out.println(fileLocation);
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter(fileLocation, true), escapeChar);
			
			if(escapeChar != '|')
				csvOutput.setForceQualifier(true);
			else
				csvOutput.setForceQualifier(false);
			// if the file didn't already exist then we need to write out the
			// header line
			if (!alreadyExists) {
				csvOutput.writeRecord(headerToWrite);
			}
			// else assume that the file already has the correct header line

			// write out content
			csvOutput.writeRecord(contentToWrite);

			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
	}
	public synchronized static void replaceDelimter(String fileLocation, String delimit, String replaceNewDelimit) throws IOException
	{
		File fileToBeModified = new File(fileLocation);
	    String oldContent = "";
	         
	    BufferedReader reader = null;
	    FileWriter writer = null;
		
	    try
		{
	    	 reader = new BufferedReader(new FileReader(fileToBeModified));	
	    	 //Reading all the lines of input text file into oldContent
             
	          String line = reader.readLine();
	          
	          while (line != null) 
	          {
	        	  if(!oldContent.trim().equals(""))
					oldContent = oldContent + "\n";
	        	  
	                oldContent = oldContent + line;
	                line = reader.readLine();
	          }
	             
	            //Replacing oldString with newString in the oldContent
	            String newContent = oldContent.replaceAll(delimit, replaceNewDelimit);
	            
	            //Rewriting the input text file with newContent
	            writer = new FileWriter(fileToBeModified);
	            writer.write(newContent);
		}
	    catch (IOException e)
        {
            e.printStackTrace();
			throw new IOException();
        }
        finally
        {
            try
            {
                //Closing the resources
                reader.close();
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
	}
	
	public static String searchZip (String folderPath) {
		String returnZipPath = "";
		
		File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
        {
            if (file.isFile())
            {
                String[] filename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
                if(filename[1].equalsIgnoreCase("zip")) {//matching defined filename
	                returnZipPath = folderPath +"/"+ file.getName();
		        	break;
                }
            }
         }
        return returnZipPath;
	}
	public static String getZipAsc(String folderPath) {
		String returnZipPath = "";
		
		File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
        {
            if (file.isFile())
            {
                String[] filename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
                if(filename[1].equalsIgnoreCase("asc")) {//matching defined filename
	                returnZipPath = file.getName();
		        	break;
                }
            }
         }
        return returnZipPath;
	}
	public static void renameCsv(String folderPath, String entity, String labelDateTime) {
		File folder = new File(folderPath);
		if (folder.exists()) {
	        File[] listOfFiles = folder.listFiles();
	        logger.info("rename folder > "+folderPath);
	        for (File file : listOfFiles)
	        {
	            if (file.isFile())
	            {
	                String[] filename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
	                if(filename[1].equalsIgnoreCase("csv")) {//matching defined filename
	
	                	logger.info("looping through files.. csv found");
		                File file2 = new File(folderPath + "/"+entity+"_"+labelDateTime+".csv");
			        	file.renameTo(file2);
			        	logger.info("Renaming from > "+file.getName() + " to "+file2.getName());
			        	break;
	                }
	            }
	         }
	        logger.info("Rename complete");
		}
	}
	   public static void copyFolder(File src, File dest)
	        	throws IOException{

		   InputStream in = null;
		   OutputStream out = null; 
		   logger.info("SRC > "+src);
		   try{
	        	if(src.isDirectory()){
	        		
	        		//if directory not exists, create it
	        		if(!dest.exists()){
	        		   dest.mkdir();
	        		   logger.info("Directory copied from "
	                                  + src + "  to " + dest);
	        		}

	        		//list all the directory contents
	        		String files[] = src.list();

	        		for (String file : files) {
	        		   //construct the src and dest file structure

	        		   File srcFile = new File(src, file);
	        		   File destFile = new File(dest, file);
	        		   //recursive copy
	        		   copyFolder(srcFile,destFile);
	        		}

	        	}else{
	        		//if csv, zip or asc, skip it
	        		 String[] filename = src.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
	                 if(!(filename[1].equalsIgnoreCase("csv") || filename[1].equalsIgnoreCase("zip") || filename[1].equalsIgnoreCase("asc"))) {//matching defined filename
		        		//if !csv !zip !asc, file, then copy it
		        		//Use bytes stream to support all file types
		        			in = new FileInputStream(src);
		        	        out = new FileOutputStream(dest);
	
		        	        byte[] buffer = new byte[1024];
	
		        	        int length;
		        	        //copy the file content in bytes
		        	        while ((length = in.read(buffer)) > 0){
		        	    	   out.write(buffer, 0, length);
		        	        }
	
		        	        in.close();
		        	        out.close();
		        	        logger.info("File copied from " + src + " to " + dest);
	                 }
	        	}
	        }catch(Exception e){
	        	e.printStackTrace();
	        	throw new IOException();
	        }
		   finally{
			   if(in != null)
				   in.close();
			   if(out != null)
				   out.close();
		   }
	   }
	   public static void moveFileToBackUp(String labelDateTime, String srcFolder, String srcFileName, String destFold)
				throws Exception {

			File srcFile = new File(srcFolder);
			File destFolder = new File(destFold);
			File destFile = new File(destFolder, srcFileName);

			if (srcFile.exists()) {
				InputStream in = null;
				OutputStream out = null;

				if (!destFolder.exists()) {
					destFolder.mkdir();
					logger.info("Directory created " + destFolder);
				}
				// Use bytes stream to support all file types
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);
				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}

				in.close();
				out.close();
				logger.info("File move from " + srcFolder + " to " + destFolder);
//				System.out.println(srcFile.getAbsoluteFile());
//				System.out.println(srcFile.delete());

			}
		}
}
