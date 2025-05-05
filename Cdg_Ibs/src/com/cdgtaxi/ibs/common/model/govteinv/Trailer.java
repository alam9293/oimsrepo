package com.cdgtaxi.ibs.common.model.govteinv;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;
import com.cdgtaxi.ibs.util.StringUtil;

public class Trailer {
	public static final int expectedLength = 27;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[]{"TRL"}));
	public Field transactionId = new Field("transactionId", Field.VARCHAR, 15, Arrays.asList(new String[]{"RCTV           "}));
	public Field totalFeedLine = new Field("totalFeedLine", Field.INTEGER, 9);
	
	public Trailer(String rawStrData) throws FieldException {
		if (rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length() + "", expectedLength + "");

		this.rawStrData = rawStrData;
		
		rowIdentifier.setData(rawStrData.substring(0, 3));
		transactionId.setData(rawStrData.substring(3, 18));
		totalFeedLine.setData(rawStrData.substring(18, 27));
	}

	public String toString(){
		return 
			"\n"+rowIdentifier.getName()	+": "	+rowIdentifier.getData()+
			"\n"+transactionId.getName()	+": "	+transactionId.getData()+
			"\n"+totalFeedLine.getName()	+": "	+totalFeedLine.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			Trailer test = new Trailer("TRL" + StringUtil.appendRight("RCTV", 15, " ") + StringUtil.appendRight("11", 9, " "));
			System.out.println(test.rawStrData);
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
		
	}
}
