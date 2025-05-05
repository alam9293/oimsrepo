package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.Field;


public class PubbsReturnIncomingHeader {
	public static final int expectedLength = 31;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[] { "HDR" }));
	public Field filler1 = new Field("filler", Field.VARCHAR, 2);
	public Field companyCode = new Field("companyCode", Field.VARCHAR, 7);
	public Field transactionId = new Field("transactionId", Field.VARCHAR, 3);
	public Field filler2 = new Field("filler2", Field.VARCHAR, 2);
	public Field creationDate = new Field("creationDate", Field.DATE_yyyymmdd, 8);
	public Field creationTime = new Field("creationTime", Field.DATE_HHMMSS2, 6);
	
	public PubbsReturnIncomingHeader(String rawStrData) throws FieldException{
		
//		if(rawStrData.length() != expectedLength)
//			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		rowIdentifier.setData(rawStrData.substring(0, 3));
		filler1.setData(rawStrData.substring(3, 5));
		companyCode.setData(rawStrData.substring(5, 12));
		transactionId.setData(rawStrData.substring(12, 15));
		filler2.setData(rawStrData.substring(15, 17));
		creationDate.setData(rawStrData.substring(17, 25));
		creationTime.setData(rawStrData.substring(25, 31));
	}
	
	public String toString(){
		return 
				"\n"+rowIdentifier.getName()		+": "	+rowIdentifier.getData()+
				"\n"+filler1.getName()				+": "	+filler1.getData()+
				"\n"+companyCode.getName()			+": "	+companyCode.getData()+
				"\n"+transactionId.getName()		+": "	+transactionId.getData()+
				"\n"+filler2.getName()				+": "	+filler2.getData()+
				"\n"+creationDate.getName()			+": "	+creationDate.getData()+
				"\n"+creationTime.getName()			+": "	+creationTime.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			PubbsReturnIncomingHeader testHeader = new PubbsReturnIncomingHeader("HDR"	//ROW IDENTIFIER
					+ "  "			// filler1
					+ "COMPANY" 	// company code
					+ "001"			// transaction ID
					+ "  "			// Filler2
					+ "20180911"	// Creation Date
					+ "1625");		//Creation Time
			System.out.println(testHeader.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
