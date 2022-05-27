package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.Field;

public class PubbsReturnIncomingDetail {
	public static final int expectedLength = 36;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[]{"DTL"}));
	public Field filler1 = new Field("filler1", Field.VARCHAR, 2);
	public Field customerAccountNumber = new Field("customerAccountNumber", Field.VARCHAR, 30);
	public Field recordStatus = new Field("recordStatus", Field.VARCHAR, 1);
	
	public PubbsReturnIncomingDetail(String rawStrData) throws FieldException{
//		if(rawStrData.length() != expectedLength)
//			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		rowIdentifier.setData(rawStrData.substring(0,3));
		filler1.setData(rawStrData.substring(3,5));
		customerAccountNumber.setData(rawStrData.substring(5,35));
		recordStatus.setData(rawStrData.substring(35,36));
	}
	
	public String toString(){
		return 
				"\n"+rowIdentifier.getName()			+": "	+rowIdentifier.getData()+
				"\n"+filler1.getName()					+": "	+filler1.getData()+
				"\n"+customerAccountNumber.getName()	+": "	+customerAccountNumber.getData()+
				"\n"+recordStatus.getName()				+": "	+recordStatus.getData()
				;
	}
	
	public static void main(String[] args) {
		try {
			PubbsReturnIncomingDetail test = new PubbsReturnIncomingDetail("DTL"  
											+ "  "
											+ "515                         "
											+ "C");
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
	}

	public Field getCustomerAccountNumber() {
		return customerAccountNumber;
	}

	public void setCustomerAccountNumber(Field customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}

	public Field getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Field recordStatus) {
		this.recordStatus = recordStatus;
	}
}
