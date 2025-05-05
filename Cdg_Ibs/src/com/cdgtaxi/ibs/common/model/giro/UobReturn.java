package com.cdgtaxi.ibs.common.model.giro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.Field;

public class UobReturn {
	public static final String REJECTED_BY_BANK = "BANK";
	public static final String REJECTED_BY_IBS = "IBS";
	
	public String returnFileName;
	public String expectedOutgoingFileName;
	
	public Field mandatoryCode = new Field("mandatoryCode", Field.VARCHAR, 1, Arrays.asList(new String[]{"U"}));
	public Field tapeFormat = new Field("tapeFormat", Field.VARCHAR, 2, Arrays.asList(new String[]{"IT"}));
	public Field outgoingFileIndicator = new Field("outgoingFileIndicator", Field.VARCHAR, 1, Arrays.asList(new String[]{"O"}));
	public Field dayAndMonth = new Field("dayAndMonth", Field.LONG_LEADING_ZEROES, 4);
	public Field sequenceNo = new Field("sequenceNo", Field.LONG_LEADING_ZEROES, 2);
	public Field processStatus = new Field("processStatus", Field.VARCHAR, 1, Arrays.asList(new String[]{"O"}));
	
	public UobReturnHeader uobReturnHeader;
	public List<UobReturnDetail> uobReturnDetails = new LinkedList<UobReturnDetail>();
	public UobReturnTrailer uobReturnTrailer;
	
	public UobReturn(String returnFileName) throws FieldException{
		mandatoryCode.setData(returnFileName.substring(0, 1));
		tapeFormat.setData(returnFileName.substring(1, 3));
		outgoingFileIndicator.setData(returnFileName.substring(3, 4));
		dayAndMonth.setData(returnFileName.substring(4, 8));
		sequenceNo.setData(returnFileName.substring(8, 10));
		processStatus.setData(returnFileName.substring(10, 11));
		
		expectedOutgoingFileName = 
			mandatoryCode.getData()+
			tapeFormat.getData()+
			"I"+ //incomingFileIndicator
			dayAndMonth.getData()+
			sequenceNo.getData();
		
		this.returnFileName = returnFileName.substring(0, 11);
	}
	
	public String toString(){
		String returnString = "";
		returnString += uobReturnHeader.toString();
		returnString += "\n";
		for(UobReturnDetail detail : uobReturnDetails){
			returnString += detail.toString();
			returnString += "\n";
		}
		returnString += uobReturnTrailer.toString();
		return returnString;
	}
	
	public static void main(String[] args) {
		try{
			//C:\Users\Seng Tat\Desktop\UITO170301O.txt
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter Absolute File Path e.g. C/test/test.txt or C:\\test\\test.txt: ");
			String filePath = br.readLine();
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
			
			//Read everything in the line and store first
			List<String> linesOfData = new ArrayList<String>();
			String line;
			while((line = reader.readLine()) != null){
				//in case last line is just a carriage return
				if(line.length()>0) linesOfData.add(line);
			}
			
			//From lines of data and transform to entities
			//With the lines of data in a collection, we can determine first and last line
			UobReturn uobReturn = new UobReturn("UITO170301O");
			for(int i=0; i< linesOfData.size(); i++){
				System.out.println("Line "+(i+1)+": "+linesOfData.get(i));
				
				//first record
				if(i == 0)
					uobReturn.uobReturnHeader = new UobReturnHeader(linesOfData.get(i));
				//last record
				else if(i == (linesOfData.size() - 1))
					uobReturn.uobReturnTrailer = new UobReturnTrailer(linesOfData.get(i));
				//the rest are details
				else
					uobReturn.uobReturnDetails.add(new UobReturnDetail(linesOfData.get(i)));
			}
			
			System.out.println(uobReturn.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
