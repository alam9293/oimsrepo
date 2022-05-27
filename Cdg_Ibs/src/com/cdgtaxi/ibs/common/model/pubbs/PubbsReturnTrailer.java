package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.Arrays;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.model.Field;
import com.cdgtaxi.ibs.util.StringUtil;

public class PubbsReturnTrailer {
	public static final int expectedLength = 61;
	public String rawStrData;
	public int lineNo;
	
	public Field rowIdentifier = new Field("rowIdentifier", Field.VARCHAR, 3, Arrays.asList(new String[]{"TRL"}));
	public Field filler1 = new Field("filler1", Field.VARCHAR, 2);
	public Field totalFeedLine = new Field("totalFeedLine", Field.VARCHAR, 8);
	public Field hashTotalA = new Field("hashTotalA", Field.VARCHAR, 16);
	public Field hashTotalB = new Field("hashTotalB", Field.VARCHAR, 16);
	public Field hashTotalC = new Field("hashTotalC", Field.VARCHAR, 16);
	
	public PubbsReturnTrailer(String rawStrData) throws FieldException {
		if (rawStrData.length() != expectedLength)
			throw new IncorrectLengthException(rawStrData.length() + "", expectedLength + "");

		this.rawStrData = rawStrData;
		
//		System.out.println("rowIdentifier > '"+rawStrData.substring(0, 3) +"'");
//		System.out.println("filler1 > '" + rawStrData.substring(3, 5) +"'");
//		System.out.println("totalFeedLine > '"+rawStrData.substring(5, 13)+"'");
//		System.out.println("hashTotalA > '" +rawStrData.substring(13, 29)+"'");
//		System.out.println("hashTotalB > '"+rawStrData.substring(20, 45)+"'");
//		System.out.println("hashTotalC > '"+rawStrData.substring(45, 61)+"'");
//		System.out.println("filler2 > '"+rawStrData.substring(61, 216)+"'");

		rowIdentifier.setData(rawStrData.substring(0, 3));
		filler1.setData(rawStrData.substring(3, 5));
		totalFeedLine.setData(rawStrData.substring(5, 13));
		hashTotalA.setData(rawStrData.substring(13, 29));
		hashTotalB.setData(rawStrData.substring(29, 45));
		hashTotalC.setData(rawStrData.substring(45, 61));
	}

	public String toString(){
		return 
			"\n"+rowIdentifier.getName()	+": "	+rowIdentifier.getData()+
			"\n"+filler1.getName()			+": "	+filler1.getData()+
			"\n"+totalFeedLine.getName()	+": "	+totalFeedLine.getData()+
			"\n"+hashTotalA.getName()		+": "	+hashTotalA.getData()+
			"\n"+hashTotalB.getName()		+": "	+hashTotalB.getData()+
			"\n"+hashTotalC.getName()		+": "	+hashTotalC.getData()
			;
	}
	
	public static void main(String[] args) {
		try {
//			PubbsTrailer test = new PubbsTrailer("TRL" + StringUtil.appendRight("RCTV", 15, " ") + StringUtil.appendRight("11", 9, " "));
			PubbsReturnTrailer test = new PubbsReturnTrailer("TRL"		//rowIdentifier
					+ "  "									//filler1
					+ StringUtil.appendLeft("01", 8, "0") 	//totalFeedLine
					+ StringUtil.appendLeft("1234567", 16, "0") //HashTotalA
					+ StringUtil.appendLeft("1234567", 16, "0") //HashTotalB
					+ StringUtil.appendLeft("1234567", 16, "0")); //HashTotalC
			System.out.println(test.rawStrData);
			System.out.println(test.toString());
		} catch (FieldException e) {
			e.printStackTrace();
		}
		
	}
}
