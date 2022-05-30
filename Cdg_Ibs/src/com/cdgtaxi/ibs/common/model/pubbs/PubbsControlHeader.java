package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;
import com.cdgtaxi.ibs.util.StringUtil;

public class PubbsControlHeader {

	public static final int expectedLength = 200;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[] { "HDR" }));
	public Field filler1 = new Field("filler1", Field.VARCHAR, 2);
	public Field companyCode = new Field("companyCode", Field.VARCHAR, 7);
	public Field transactionId = new Field("transactionId", Field.VARCHAR, 3);
	public Field filler2 = new Field("filler2", Field.VARCHAR, 2);
	public Field creationDate = new Field("creationDate", Field.DATE_yyyymmdd, 8);
	public Field creationTime = new Field("creationTime", Field.DATE_HHMMSS2, 6);
	public Field vendorId = new Field("vendorId", Field.VARCHAR, 10);
	public Field filler3 = new Field("filler3", Field.VARCHAR, 159);

	public List<PubbsInvoiceHeader> invoiceHeaders = new LinkedList<PubbsInvoiceHeader>();
	public PubbsTrailer trailer;

	public PubbsControlHeader(String rawStrData) throws FieldException, IncorrectLengthException {
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		this.rawStrData = rawStrData;
//		System.out.println("rowIdentifier > '"+rawStrData.substring(0, 3) +"'");
//		System.out.println("filler1 > '" + rawStrData.substring(3, 5) +"'");
//		System.out.println("companyCode > '"+rawStrData.substring(5, 12)+"'");
//		System.out.println("transactionID > '" +rawStrData.substring(12, 15)+"'");
//		System.out.println("filler2 > '"+rawStrData.substring(15, 17)+"'");
//		System.out.println("creationDate > '"+rawStrData.substring(17, 25)+"'");
//		System.out.println("creationTime > '"+rawStrData.substring(25, 31)+"'");
//		System.out.println("vendorId > '"+rawStrData.substring(31, 41)+"'");
//		System.out.println("filler3 > '"+rawStrData.substring(41, 200)+"'");
		
		
		rowIdentifier.setData(rawStrData.substring(0, 3));
		filler1.setData(rawStrData.substring(3, 5));
		companyCode.setData(rawStrData.substring(5, 12));
		transactionId.setData(rawStrData.substring(12, 15));
		filler2.setData(rawStrData.substring(15, 17));
		creationDate.setData(rawStrData.substring(17, 25));
		creationTime.setData(rawStrData.substring(25, 31));
		vendorId.setData(rawStrData.substring(31, 41));
		filler3.setData(rawStrData.substring(41, 200));
	}

	public String toString(){
		return 
			"\n"+rowIdentifier.getName()		+": "	+rowIdentifier.getData()+
			"\n"+filler1.getName()				+": "	+filler1.getData()+
			"\n"+companyCode.getName()			+": "	+companyCode.getData()+
			"\n"+transactionId.getName()		+": "	+transactionId.getData()+
			"\n"+filler2.getName()				+": "	+filler2.getData()+
			"\n"+creationDate.getName()			+": "	+creationDate.getData()+
			"\n"+creationTime.getName()			+": "	+creationTime.getData()+
			"\n"+vendorId.getName()				+": "	+vendorId.getData()+
			"\n"+filler3.getName()				+": "	+filler3.getData()
		;
	}

	public static void main(String[] args) {
		try {
			PubbsControlHeader test = new PubbsControlHeader("HDR"	//ROW IDENTIFIER
					+ "  "			// filler1
					+ "COMPANY" 	// company code
					+ "001"			// transaction ID
					+ "  "			// Filler2
					+ "20180911"	// Creation Date
					+ "1625"		//Creation Time
					+ "VENDOR_IDD" 	// Vendor ID
					+StringUtil.appendLeft(" ", 159, " "));	//Filler3
			
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
