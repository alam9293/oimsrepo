package com.cdgtaxi.ibs.common.model.giro;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;


public class UobReturnHeader {
	public static final int expectedLength = 84;
	public String rawStrData;
	public int lineNo;
	
	public Field recordType = new Field("recordType", Field.LONG_LEADING_ZEROES, 1, Arrays.asList(new String[]{"1"}));
	public Field serviceType = new Field("serviceType", Field.VARCHAR, 10, Arrays.asList(new String[]{"IBGOTAP2  "}));
	public Field bankCode = new Field("originatingBankCode", Field.LONG_LEADING_ZEROES, 4);
	public Field branchCode = new Field("originatingBranchCode", Field.LONG_LEADING_ZEROES, 3);
	public Field bankAccountNo = new Field("originatingAccountNo", Field.LONG_LEADING_ZEROES, 11);
	public Field bankAccountName = new Field("originatingAccountName", Field.VARCHAR, 20);
	public Field creationDate = new Field("creationDate", Field.DATE, 8);
	public Field valueDate = new Field("valueDate", Field.DATE, 8);
	public Field rosReferenceNo = new Field("rosReferenceNo", Field.LONG_LEADING_ZEROES, 5);
	
	public UobReturnHeader(String rawStrData) throws FieldException{
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		recordType.setData(rawStrData.substring(0,1));
		serviceType.setData(rawStrData.substring(1,11));
		bankCode.setData(rawStrData.substring(11,15));
		branchCode.setData(rawStrData.substring(15,18));
		bankAccountNo.setData(rawStrData.substring(18,29));
		bankAccountName.setData(rawStrData.substring(29,49));
		creationDate.setData(rawStrData.substring(49,57));
		valueDate.setData(rawStrData.substring(57,65));
		rosReferenceNo.setData(rawStrData.substring(65,70));
	}
	
	public String toString(){
		return 
			"\n"+recordType.getName()		+": "	+recordType.getData()+
			"\n"+serviceType.getName()		+": "	+serviceType.getData()+
			"\n"+bankCode.getName()			+": "	+bankCode.getData()+
			"\n"+branchCode.getName()		+": "	+branchCode.getData()+
			"\n"+bankAccountNo.getName()	+": "	+bankAccountNo.getData()+
			"\n"+bankAccountName.getName()	+": "	+bankAccountName.getData()+
			"\n"+creationDate.getName()		+": "	+creationDate.getData()+
			"\n"+valueDate.getName()		+": "	+valueDate.getData()+
			"\n"+rosReferenceNo.getName()	+": "	+rosReferenceNo.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			UobReturnHeader testHeader = new UobReturnHeader("1IBGOTAP2  737500101011111111TEST                201007302010080200131              ");
			System.out.println(testHeader.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
