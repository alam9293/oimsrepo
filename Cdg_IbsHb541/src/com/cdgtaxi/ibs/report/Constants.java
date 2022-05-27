package com.cdgtaxi.ibs.report;

import java.util.LinkedHashMap;

public class Constants {

	//REPORT FORMAT
    public static final String FORMAT_PDF 		= "application/pdf";
    public static final String FORMAT_RTF 		= "application/rtf";
    public static final String FORMAT_EXCEL		= "application/vnd.ms-excel";
    public static final String FORMAT_CSV		= "application/csv";
    public static final String FORMAT_CUSTOMIZE	= "application/octet-stream";
    
    //REPORT EXTENSION
    public static final String EXTENSION_CSV		= "CSV";
    public static final String EXTENSION_EXCEL		= "XLS";
    public static final String EXTENSION_PDF		= "PDF";
    public static final String EXTENSION_RTF		= "RTF";
    public static final String EXTENSION_CUSTOMIZE	= "CUSTOMIZE";
    
    public static final LinkedHashMap<String,String> FORMAT_MAP = new LinkedHashMap<String,String>(){
    	{
    		put(FORMAT_CSV, 	EXTENSION_CSV);
    		put(FORMAT_EXCEL, 	EXTENSION_EXCEL);
        	put(FORMAT_PDF, 	EXTENSION_PDF);
        	put(FORMAT_RTF, 	EXTENSION_RTF);
    	}
    };
    
    public static final LinkedHashMap<String,String> EXTENSION_MAP = new LinkedHashMap<String,String>(){
    	{
    		put(EXTENSION_CSV, 			FORMAT_CSV);
    		put(EXTENSION_EXCEL, 		FORMAT_EXCEL);
        	put(EXTENSION_PDF, 			FORMAT_PDF);
        	put(EXTENSION_RTF, 			FORMAT_RTF);
        	put(EXTENSION_CUSTOMIZE, 	FORMAT_CUSTOMIZE);
    	}
    };

    public static final String TEXT_QUALIFIER = "\"";
   
    public static final String PRINTED_BY = "printedBy";
    
    public static final String NON_CONFIGURABLE_CONSTANT_TYPE = "NC";
    public static final String CONFIGURABLE_CONSTANT_TYPE = "CC";
    public static final String REQUIRED = "REQUIRED";
}

