package com.cdgtaxi.ibs.common.model.govteinv;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;

public class ControlHeader {

	public static final int expectedLength = 77;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[] { "999" }));
	public Field transactionId = new Field("transactionId", Field.VARCHAR, 15, Arrays.asList(new String[] { "RCTV           " }));
	public Field filler = new Field("filler", Field.VARCHAR, 40);
	public Field creationDateAndTime = new Field("creationDateAndTime", Field.DATE_TIME, 19);

	public List<InvoiceHeader> invoiceHeaders = new LinkedList<InvoiceHeader>();
	public Trailer trailer;

	public ControlHeader(String rawStrData) throws FieldException {
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		this.rawStrData = rawStrData;
		
		rowIdentifier.setData(rawStrData.substring(0, 3));
		transactionId.setData(rawStrData.substring(3, 18));
		filler.setData(rawStrData.substring(18, 58));
		creationDateAndTime.setData(rawStrData.substring(58, 77));
	}

	public String toString(){
		return 
			"\n"+rowIdentifier.getName()		+": "	+rowIdentifier.getData()+
			"\n"+transactionId.getName()		+": "	+transactionId.getData()+
			"\n"+filler.getName()				+": "	+filler.getData()+
			"\n"+creationDateAndTime.getName()	+": "	+creationDateAndTime.getData()
		;
	}

	public static void main(String[] args) {
		try {
			ControlHeader test = new ControlHeader("999RCTV                                                   21/05/2012 13:45:33");
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
