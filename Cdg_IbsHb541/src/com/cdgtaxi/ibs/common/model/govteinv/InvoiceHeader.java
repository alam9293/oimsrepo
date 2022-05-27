package com.cdgtaxi.ibs.common.model.govteinv;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;
import com.cdgtaxi.ibs.util.StringUtil;


public class InvoiceHeader {
	
	public static final String SUCCESS = "SUC";
	public static final String REJECT = "REJ";
	
	public static final int expectedLength = 315;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[]{"000"}));
	public Field businessUnit = new Field("businessUnit", Field.VARCHAR, 5);
	public Field invoiceNo = new Field("invoiceNo", Field.VARCHAR, 30);
	public Field invoiceDate = new Field("invoiceDate", Field.DATE_dd_MM_yyyy, 10);
	public Field vendorId = new Field("vendorId", Field.VARCHAR, 10);
	public Field invoiceStatus = new Field("invoiceStatus", Field.VARCHAR, 3, Arrays.asList(new String[]{SUCCESS, REJECT}));
	public Field remarks = new Field("remarks", Field.VARCHAR, 254);
	
	public InvoiceHeader(String rawStrData) throws FieldException{
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		this.rawStrData = rawStrData;
		
		rowIdentifier.setData(rawStrData.substring(0, 3));
		businessUnit.setData(rawStrData.substring(3, 8));
		invoiceNo.setData(rawStrData.substring(8, 38));
		invoiceDate.setData(rawStrData.substring(38, 48));
		vendorId.setData(rawStrData.substring(48, 58));
		invoiceStatus.setData(rawStrData.substring(58, 61));
		remarks.setData(rawStrData.substring(61, 315));
	}
	
	public String toString(){
		return 
			"\n"+rowIdentifier.getName()	+": "	+rowIdentifier.getData()+
			"\n"+businessUnit.getName()		+": "	+businessUnit.getData()+
			"\n"+invoiceNo.getName()		+": "	+invoiceNo.getData()+
			"\n"+invoiceDate.getName()		+": "	+invoiceDate.getData()+
			"\n"+vendorId.getName()			+": "	+vendorId.getData()+
			"\n"+invoiceStatus.getName()	+": "	+invoiceStatus.getData()+
			"\n"+remarks.getName()			+": "	+remarks.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			InvoiceHeader test = new InvoiceHeader("00012345"+StringUtil.appendRight("INV123",30, " ")+"21/05/2012V000000001"+SUCCESS+StringUtil.appendRight("REMARKS 123", 254, " "));
			System.out.println(test.rawStrData);
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
