package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.Field;
import com.cdgtaxi.ibs.util.StringUtil;

public class PubbsReturnIncomingTrailer {
	public static final int expectedLength = 13;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[]{"TRL"}));
	public Field filler1 = new Field("filler1", Field.VARCHAR, 2);
	public Field totalFeedLine = new Field("totalFeedLine", Field.VARCHAR, 8);
	
	public PubbsReturnIncomingTrailer(String rawStrData) throws FieldException{
//		if(rawStrData.length() != expectedLength)
//			throw new IncorrectLengthException(rawStrData.length()+"", expectedLength+"");
		
		rowIdentifier.setData(rawStrData.substring(0, 3));
		filler1.setData(rawStrData.substring(3, 5));
		totalFeedLine.setData(rawStrData.substring(5, 13));
	}
	
	public String toString(){
		return 
				"\n"+rowIdentifier.getName()	+": "	+rowIdentifier.getData()+
				"\n"+filler1.getName()			+": "	+filler1.getData()+
				"\n"+totalFeedLine.getName()	+": "	+totalFeedLine.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
			PubbsReturnIncomingTrailer test = new PubbsReturnIncomingTrailer("TRL"
					+ "  "										//filler1
					+ StringUtil.appendLeft("01", 8, "0")); 	//totalFeedLine
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
		
	}
}
