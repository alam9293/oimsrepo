package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;
import com.cdgtaxi.ibs.util.StringUtil;


public class PubbsInvoiceHeader {
	
	public static final String SUCCESS = "SUC";
	public static final String REJECT = "REJ";
	
	public static final int expectedLength = 316;
	public String rawStrData;
	public int lineNo;
	
	public Field recordIdentifier = new Field("recordIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[]{"DTL"}));
	public Field filler1 = new Field("filler1", Field.VARCHAR, 2);
	public Field customerAccountNumber = new Field("customerAccountNumber", Field.VARCHAR, 30);
	public Field transactionType = new Field("transactionType", Field.VARCHAR, 20);
	public Field billType = new Field("billType", Field.VARCHAR, 20);
	public Field invoiceNumber = new Field("invoiceNumber", Field.VARCHAR, 20);
	public Field invoiceDate = new Field("invoiceDate", Field.DATE_dd_MMM_yyyy, 11);
	public Field invoiceCurrencyCode = new Field("invoiceCurrencyCode", Field.VARCHAR, 15);
	public Field invoiceQty = new Field("invoiceQty", Field.VARCHAR, 16);
	public Field invoiceGrossAmount = new Field("invoiceGrossAmount", Field.VARCHAR, 16);
	public Field invoiceTaxAmount = new Field("invoiceTaxAmount", Field.VARCHAR, 16);
	public Field invoiceTotalAmount = new Field("invoiceTotalAmount", Field.VARCHAR, 16);
	public Field reference = new Field("reference", Field.VARCHAR, 30);
	public Field errorFlag = new Field("errorFlag", Field.VARCHAR, 1);
	public Field errorMessage = new Field("errorMessage", Field.VARCHAR, 100);
	
	public PubbsInvoiceHeader(String rawStrData) throws FieldException{
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		this.rawStrData = rawStrData;
		
//		System.out.println("recordIdentifier > '"+rawStrData.substring(0, 3) +"'");
//		System.out.println("filler1 > '" + rawStrData.substring(3, 5) +"'");
//		System.out.println("customerAccountNumber > '"+rawStrData.substring(5, 35)+"'");
//		System.out.println("transactionType > '" +rawStrData.substring(35, 55)+"'");
//		System.out.println("billType > '"+rawStrData.substring(55, 75)+"'");
//		System.out.println("invoiceNumber > '"+rawStrData.substring(75, 95)+"'");
//		System.out.println("invoiceDate > '"+rawStrData.substring(95, 106)+"'");
//		System.out.println("invoiceCurrencyCode > '"+rawStrData.substring(106, 121)+"'");
//		System.out.println("invoiceQty > '"+rawStrData.substring(121, 137)+"'");
//		System.out.println("invoiceGrossAmount > '"+rawStrData.substring(137, 153)+"'");
//		System.out.println("invoiceTaxAmount > '"+rawStrData.substring(153, 169)+"'");
//		System.out.println("invoiceTotalAmount > '"+rawStrData.substring(169, 185)+"'");
//		System.out.println("reference > '"+rawStrData.substring(185, 215)+"'");
//		System.out.println("errorFlag > '"+rawStrData.substring(215, 216)+"'");
//		System.out.println("errorMessage > '"+rawStrData.substring(216, 316)+"'");
		
		
		recordIdentifier.setData(rawStrData.substring(0, 3));
		filler1.setData(rawStrData.substring(3, 5));
		customerAccountNumber.setData(rawStrData.substring(5, 35));
		transactionType.setData(rawStrData.substring(35, 55));
		billType.setData(rawStrData.substring(55, 75));
		invoiceNumber.setData(rawStrData.substring(75, 95));
		invoiceDate.setData(rawStrData.substring(95, 106));
		invoiceCurrencyCode.setData(rawStrData.substring(106, 121));
		invoiceQty.setData(rawStrData.substring(121, 137));
		invoiceGrossAmount.setData(rawStrData.substring(137, 153));
		invoiceTaxAmount.setData(rawStrData.substring(153, 169));
		invoiceTotalAmount.setData(rawStrData.substring(169, 185));
		reference.setData(rawStrData.substring(185, 215));
		errorFlag.setData(rawStrData.substring(215, 216));
		errorMessage.setData(rawStrData.substring(216, 316));
	}
	
	public String toString(){
		return 
			"\n"+recordIdentifier.getName()	+": "	+recordIdentifier.getData()+
			"\n"+filler1.getName()			+": "	+filler1.getData()+
			"\n"+customerAccountNumber.getName()		+": "	+customerAccountNumber.getData()+
			"\n"+transactionType.getName()	+": "	+transactionType.getData()+
			"\n"+billType.getName()			+": "	+billType.getData()+
			"\n"+invoiceNumber.getName()	+": "	+invoiceNumber.getData()+
			"\n"+invoiceDate.getName()		+": "	+invoiceDate.getData()+
			"\n"+invoiceCurrencyCode.getName()	+": "	+invoiceCurrencyCode.getData()+
			"\n"+invoiceQty.getName()		+": "	+invoiceQty.getData()+
			"\n"+invoiceGrossAmount.getName()	+": "	+invoiceGrossAmount.getData()+
			"\n"+invoiceTaxAmount.getName()	+": "	+invoiceTaxAmount.getData()+
			"\n"+invoiceTotalAmount.getName()	+": "	+invoiceTotalAmount.getData()+
			"\n"+reference.getName()	+": "	+reference.getData()+
			"\n"+errorFlag.getName()	+": "	+errorFlag.getData()+
			"\n"+errorMessage.getName()		+": "	+errorMessage.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
//			PubbsInvoiceHeader test = new PubbsInvoiceHeader("00012345"+StringUtil.appendRight("INV123",30, " ")+"21/05/2012V000000001"+SUCCESS+StringUtil.appendRight("REMARKS 123", 254, " "));
			PubbsInvoiceHeader test = new PubbsInvoiceHeader("DTL"		//rowIdentifier
					+ "  "												//filler1
					+StringUtil.appendRight("15522556",30, " ") 		//customerAccountNumber
					+StringUtil.appendRight("INV",20, " ")				//transactionType
					+StringUtil.appendRight("2% ADMIN FEES",20, " ")	//BillType
					+StringUtil.appendRight("INVOICENUMBER",20, " ")	//invoiceNumber
					+ "01-JAN-2005"										//invoiceDate
					+StringUtil.appendRight("SGD",15, " ")				//invoice Currency Code
					+StringUtil.appendLeft("0.99+",16, "0")				//invoiceQty
					+StringUtil.appendLeft("0.99+",16, "0")				//invoiceGrossAmount
					+StringUtil.appendLeft("1.88+",16, "0")				//invoiceTaxAmount
					+StringUtil.appendLeft("2.87+",16, "0")				//invoiceTotalAmount
					+StringUtil.appendRight("REFERENCE",30, " ") 		//reference
					+"N" 												//errorFlag
					+StringUtil.appendRight("ERRORMESSAGE",100, " ")); 		//errorMessage
			System.out.println(test.rawStrData);
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
