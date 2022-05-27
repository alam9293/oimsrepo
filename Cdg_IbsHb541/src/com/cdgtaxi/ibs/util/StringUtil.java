package com.cdgtaxi.ibs.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public class StringUtil {
	public static final String GLOBAL_STRING_FORMAT = "String";
	public static final String GLOBAL_EMPTY_NUMERIC_FORMAT = "EMPTY_NUMERIC";
	public static final String GLOBAL_DECIMAL_FORMAT = "####,##0.00";
	public static final String GLOBAL_INTEGER_FORMAT = "#,##0";
	public static final String DECIMAL_IN_INTEGER_FORMAT = "#,##0";
	public static final String WITHOUT_DECIMAL_PLAIN_FORMAT = "#########0";
	
	/**
	 * To add decimal point at the 11th position of the String parameter.
	 * @param amount Transaction amount in String length of 12 e.g. $10.50 -> 000000001050
	 * @return Formatted amount in String
	 * @throws IllegalArgumentException thrown when parameter's length != 12
	 */
	public synchronized static String addDecimalPoint(String amount) throws IllegalArgumentException{
		if(amount.length()!=12) {
			throw new IllegalArgumentException();
		} else {
			return amount.substring(0, 10)+"."+amount.substring(10, 12);
		}
	}

	/**
	 * Method to convert from bigdecimal to string for display.
	 * 12345 -> 12,345.00
	 * @param value the big decimal
	 * @param format the format
	 * @return the formatted string
	 */
	public static String bigDecimalToString(BigDecimal value, String format){
		return (value!=null)? new DecimalFormat(format).format(value.doubleValue()) : null;
	}
	
	public static String bigDecimalToStringWithGDFormat(BigDecimal value){
		return (value!=null)? bigDecimalToString(value, StringUtil.GLOBAL_DECIMAL_FORMAT) : null;
	}
	
	public static String bigDecimalToString(BigDecimal value){
		return (value!=null)? value.toString() : null; 
	}
	
	
	public static BigDecimal stringToBigDecimal(String value) throws ParseException{
		
		if(value==null){
			return null;
		}
		
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		format.setParseBigDecimal(true);
		BigDecimal number = (BigDecimal) format.parse(value);
		
		return number;
	}

	/**
	 * Method to convert from number to string for display.
	 * @param value of the number
	 * @param format the format
	 * @return the formatted string
	 */
	public static String numberToString(Number value, String format){
		return new DecimalFormat(format).format(value.doubleValue());
	}
	
	public static String numberToString(Number value){
		return (value!=null)? value.toString() : null;
	}

	/**
	 * This method works like LPAD of oracle. It will append one str
	 * and the other towards the left hand side making the str to the intended
	 * length. Example appendLeft("1",6,"0") returns "000001".
	 * @param str1 Original String to be appended
	 * @param length Intended String length
	 * @param str2 String to be appended on the left
	 * @return Appended String
	 */
	public synchronized static String appendLeft(String str1, int length, String str2){
		int lengthToAppend = length-str1.length();
		for(int i=0; i<lengthToAppend; i++){
			str1 = str2+str1;
		}

		return str1;
	}
	
	/**
	 * This method works like RPAD of oracle. It will append one str
	 * and the other towards the right hand side making the str to the intended
	 * length. Example appendRight("1",6,"0") returns "100000".
	 * @param str1 Original String to be appended
	 * @param length Intended String length
	 * @param str2 String to be appended on the right
	 * @return Appended String
	 */
	public synchronized static String appendRight(String str1, int length, String str2){
		int lengthToAppend = length-str1.length();
		for(int i=0; i<lengthToAppend; i++){
			str1 = str1+str2;
		}

		return str1;
	}

	/**
	 * To remove one character from the given String
	 * @param str1 The original String to have its certain character to be removed
	 * @param str2 The character to be removed
	 * @return The String after removing the character
	 */
	public static String removeCharacter(String str1, String str2){
		String[] strArray = str1.split(str2);
		String newStr = "";
		for (String element : strArray) {
			newStr += element;
		}
		return newStr;
	}

	public static boolean isBlank(String value) {
		return value == null || value.equals("");
	}

	public static Number stringToNumber(String number) throws ParseException {
		return new DecimalFormat().parse(number);
	}

	public static String maskNric(String nric) {
		String maskNric = nric;
		
		//1. if null or empty, return back empty
		if(nric == null || nric.equals(""))
	            return "";
		
		try
		{
			MstbMasterTable master = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.NRIC_PDPA_CONFIG_MASTER_TYPE,
								"CONT");
			Integer maskNumber = Integer.parseInt(master.getMasterValue().trim());
			
			//2. if mask no config is 0 or null, return same string back
			if(maskNumber == null || maskNumber <= 0)
				return nric;
			
			//3. if mask number is bigger than or same string length.. mask everything except last?
			if(nric.length() <= maskNumber)
			{
				int maskLength = nric.length() - 1;
			
				StringBuilder sbMaskString = new StringBuilder(maskLength);
			        
			    for(int i = 0; i < maskLength; i++){
			            sbMaskString.append("*");
			    }
			    maskNric = sbMaskString.toString() + nric.substring(nric.length() - 1);
			}
			//4. if mask smaller than string , just mask the first x number of char
			else
			{
				StringBuilder sbMaskString = new StringBuilder(maskNumber);

			    for(int i = 0; i < maskNumber; i++){
			            sbMaskString.append("*");
			    }
			    maskNric = sbMaskString.toString() + nric.substring(maskNumber , nric.length());
			}
		}
		catch(Exception e)
		{ }
		return maskNric;
	}
}
