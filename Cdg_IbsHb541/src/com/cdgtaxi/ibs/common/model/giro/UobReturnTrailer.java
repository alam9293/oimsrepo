package com.cdgtaxi.ibs.common.model.giro;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;

public class UobReturnTrailer {
	public static final int expectedLength = 84;
	public String rawStrData;
	public int lineNo;
	
	public Field recordType = new Field("recordType", Field.VARCHAR, 1, Arrays.asList(new String[]{"9"}));
	public Field totalDebitAmount = new Field("totalDebitAmount", Field.DECIMAL_LEADING_ZEROES, 13);
	public Field totalCrebitAmount = new Field("totalCrebitAmount", Field.DECIMAL_LEADING_ZEROES, 13);
	public Field totalDebitCount = new Field("totalDebitCount", Field.LONG_LEADING_ZEROES, 7);
	public Field totalCreditCount = new Field("totalCreditCount", Field.LONG_LEADING_ZEROES, 7);
	public Field rejectionDebitAmount = new Field("rejectionDebitAmount", Field.DECIMAL_LEADING_ZEROES, 13);
	public Field rejectionCreditAmount = new Field("rejectionCreditAmount", Field.DECIMAL_LEADING_ZEROES, 13);
	public Field rejectionDebitCount = new Field("rejectionDebitCount", Field.LONG_LEADING_ZEROES, 7);
	public Field rejectionCreditCount = new Field("rejectionCreditCount", Field.LONG_LEADING_ZEROES, 7);
	
	public UobReturnTrailer(String rawStrData) throws FieldException{
		if(rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		recordType.setData(rawStrData.substring(0,1));
		totalDebitAmount.setData(rawStrData.substring(1,14));
		totalCrebitAmount.setData(rawStrData.substring(14,27));
		totalDebitCount.setData(rawStrData.substring(27,34));
		totalCreditCount.setData(rawStrData.substring(34,41));
		rejectionDebitAmount.setData(rawStrData.substring(41,54));
		rejectionCreditAmount.setData(rawStrData.substring(54,67));
		rejectionDebitCount.setData(rawStrData.substring(67,74));
		rejectionCreditCount.setData(rawStrData.substring(74,81));
	}
	
	public String toString(){
		return 
			"\n"+recordType.getName()			+": "	+recordType.getData()+
			"\n"+totalDebitAmount.getName()		+": "	+totalDebitAmount.getData()+
			"\n"+totalCrebitAmount.getName()	+": "	+totalCrebitAmount.getData()+
			"\n"+totalDebitCount.getName()		+": "	+totalDebitCount.getData()+
			"\n"+totalCreditCount.getName()		+": "	+totalCreditCount.getData()+
			"\n"+rejectionDebitAmount.getName()	+": "	+rejectionDebitAmount.getData()+
			"\n"+rejectionCreditAmount.getName()+": "	+rejectionCreditAmount.getData()+
			"\n"+rejectionDebitCount.getName()	+": "	+rejectionDebitCount.getData()+
			"\n"+rejectionCreditCount.getName()	+": "	+rejectionCreditCount.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			UobReturnTrailer test = new UobReturnTrailer("900000388159320000000000000000000400000000000000000000000000000000000000000000000   ");
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
		
	}
}
