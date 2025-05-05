package com.cdgtaxi.ibs.common.model.giro;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;

public class UobReturnDetail {
	public static final int expectedLength = 84;
	public String rawStrData;
	public int lineNo;
	
	public Field recordType = new Field("recordType", Field.LONG_LEADING_ZEROES, 1, Arrays.asList(new String[]{"2"}));
	public Field bankCode = new Field("receivingBankCode", Field.LONG_LEADING_ZEROES, 4);
	public Field branchCode = new Field("receivingBranchCode", Field.LONG_LEADING_ZEROES, 3);
	public Field bankAccountNo = new Field("receivingAccountNo", Field.VARCHAR, 11);
	public Field bankAccountName = new Field("receivingAccountName", Field.VARCHAR, 20);
	public Field transactionCode = new Field("transactionCode", Field.LONG_LEADING_ZEROES, 2, Arrays.asList(new String[]{"30"}));
	public Field amount = new Field("amount", Field.DECIMAL_LEADING_ZEROES, 11);
	public Field particulars = new Field("particulars", Field.VARCHAR, 12);
	public Field reference = new Field("reference", Field.VARCHAR, 12);
	public Field clearFate = new Field("clearFate", Field.LONG_LEADING_ZEROES, 1, Arrays.asList(new String[]{"0","1"}));
	public Field rejectionCode = new Field("rejectionCode", Field.LONG_LEADING_ZEROES, 2);
	
	public UobReturnDetail(String rawStrData) throws FieldException{
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		recordType.setData(rawStrData.substring(0,1));
		bankCode.setData(rawStrData.substring(1,5));
		branchCode.setData(rawStrData.substring(5,8));
		bankAccountNo.setData(rawStrData.substring(8,19));
		bankAccountName.setData(rawStrData.substring(19,39));
		transactionCode.setData(rawStrData.substring(39,41));
		amount.setData(rawStrData.substring(41,52));
		particulars.setData(rawStrData.substring(52,64));
		reference.setData(rawStrData.substring(64,76));
		clearFate.setData(rawStrData.substring(76,77));
		rejectionCode.setData(rawStrData.substring(77,79));
	}
	
	public String toString(){
		return 
			"\n"+recordType.getName()		+": "	+recordType.getData()+
			"\n"+bankCode.getName()			+": "	+bankCode.getData()+
			"\n"+branchCode.getName()		+": "	+branchCode.getData()+
			"\n"+bankAccountNo.getName()	+": "	+bankAccountNo.getData()+
			"\n"+bankAccountName.getName()	+": "	+bankAccountName.getData()+
			"\n"+transactionCode.getName()	+": "	+transactionCode.getData()+
			"\n"+amount.getName()			+": "	+amount.getData()+
			"\n"+particulars.getName()		+": "	+particulars.getData()+
			"\n"+reference.getName()		+": "	+reference.getData()+
			"\n"+clearFate.getName()		+": "	+clearFate.getData()+
			"\n"+rejectionCode.getName()	+": "	+rejectionCode.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			UobReturnDetail test = new UobReturnDetail("27171081123456789  TEST A              30000026962642000309969    9300020485000     ");
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}
}
