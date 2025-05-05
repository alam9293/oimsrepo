package com.cdgtaxi.ibs.common.model.recurring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class RecurringFileHandler extends Thread{
	
	private static final Logger logger = Logger.getLogger(RecurringFileHandler.class);
	private File directory;
	private File file;
	private BufferedReader reader;
	
	public RecurringFileHandler(String directoryStr, 
			String fileName) throws Exception{
		
		directory = new File(directoryStr);
		if(!directory.exists()) {
			System.out.println("Incoming File Directory("+directoryStr+") not found!");
		}
		
		file = new File(directoryStr+fileName);
		if(!file.exists()) 
			System.out.println("Incoming File not found!");
	}
	
	public void open() throws IOException{
		reader = new BufferedReader(new FileReader(file));
	}
	
	public String readLine() throws IOException{
		return reader.readLine();
	}
	
	public List<String> readEntireFile() throws IOException{
		//Read everything in the line and store first
		List<String> linesOfData = new ArrayList<String>();
		String line;
		while((line = this.readLine()) != null){
			//in case last line is just a carriage return
			if(line.length()>0) linesOfData.add(line);
		}
		
		return linesOfData; 
	}
	
	public void close() throws IOException{
		reader.close();
	}
}
