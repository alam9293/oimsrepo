package com.cdgtaxi.ibs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.google.common.collect.Maps;

public class ProductUtil {
	
	
	public void writeProperty(){
		String str="";
		String key="";
		String val="";
		try{
			Properties pro = new Properties();
			File f = new File(str + ".properties");
			FileInputStream in = new FileInputStream(f);
		    pro.load(in);
		    System.out.print("Enter Key : ");
		    key = "This is my key";
		    System.out.print("Enter Value : ");
		    val = "Value";
		    pro.setProperty(key, val);
		    pro.store(new FileOutputStream(str + ".properties"),null);
		  
		}catch(IOException ioe){}
	}
	
	public void readProductProperties(){
		Map elixirReportProperties = (Map)SpringUtil.getBean("elixirReportProperties");
		//retrieve properties value
		String isStartFromMax="";
		String maxCardNumber="";
		
		isStartFromMax = (String)elixirReportProperties.get("start_from_max");
		maxCardNumber = (String)elixirReportProperties.get("max_card_number");
		
		
	}
	public static String getluhnCheckedCardNo(String cardno)
	{
		String luhnCheckedCardNo="";
		luhnCheckedCardNo=cardno+""+getCheckDigit(cardno);
		return luhnCheckedCardNo;	
	}
	public static int getCheckDigit0(String cardno)
	{
		int[] digits = toIntArray(cardno);
		int total = 0;
		int checkdigit=0;
		int temp=0;
		for (int i=0; i<cardno.length(); i++)
		{
			if (i%2==0){
				temp = digits[i]*2;
				if (temp>10)
					digits[i]=temp-9;
				else
					digits[i]=temp;
			}
			total += digits[i];
		}
		int modulo = total % 10;
		   if(modulo > 0)
		   {
			   checkdigit = 10 - modulo;
		   }else {
			   checkdigit = 0;
		   }
		
		return checkdigit;
	}
	public static int[] toIntArray(String str)
	{
		int[] intArray = new int[str.length()];
		for (int i=0; i<str.length(); i++)
			intArray[i]=Character.getNumericValue(str.charAt(i));
		return intArray;
	}
	public static int getCheckDigit(String idWithoutCheckdigit) 
	{
		// allowable characters within identifier
		//String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";
		String validChars = "0123456789";
		// remove leading or trailing whitespace, convert to uppercase
		idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();
		// this will be a running total
		int sum = 0;
		// loop through digits from right to left
		for (int i = 0; i < idWithoutCheckdigit.length(); i++) {
			//set ch to "current" character to be processed
			char ch = idWithoutCheckdigit
			        .charAt(idWithoutCheckdigit.length() - i - 1);
		    // throw exception for invalid characters
		    if (validChars.indexOf(ch) == -1)
		    	throw new RuntimeException(
			        "\"" + ch + "\" is an invalid character");
		    // our "digit" is calculated using ASCII value - 48
		    int digit = (int)ch - 48;
		    // weight will be the current digit's contribution to
			// the running total
			int weight;
			if (i % 2 == 0) {
				// for alternating digits starting with the rightmost, we
				// use our formula this is the same as multiplying x 2 and
				// adding digits together for values 0 to 9.  Using the
				// following formula allows us to gracefully calculate a
				// weight for non-numeric "digits" as well (from their
				// ASCII value - 48).
				weight = (2 * digit) - (int) (digit / 5) * 9;
			} else {
				// even-positioned digits just contribute their ascii
			    // value minus 48
			    weight = digit;
			}
			// keep a running total of weights
			sum += weight;
		}
		// avoid sum less than 10 (if characters below "0" allowed,
		// this could happen)
		sum = Math.abs(sum) + 10;
		// check digit is amount needed to reach next number
		// divisible by ten
		return (10 - (sum % 10)) % 10;
	}
	
	

	public static void validateBalanceExpiryDateNoPast(Date balanceExpiryDate){
		
		Calendar expiryDateCal = Calendar.getInstance();
		expiryDateCal.setTime(balanceExpiryDate);
		Calendar currentCal = Calendar.getInstance();

		if (expiryDateCal.compareTo(currentCal) <= 0) {
			throw new WrongValueException("Balance Expiry Date cannot be earlier than current date.");
		}
	}
	
	public static void validateNoEarlierThanCurrentBalanceExpiryDate(Date balanceExpiryDate, Date currentBalanceExpiryDate){

		if (balanceExpiryDate.before(currentBalanceExpiryDate)) {
			throw new WrongValueException("Balance Expiry Date cannot be earlier than current Balance Expiry Date.");
		}
	}
	
	
	public static LinkedHashMap<String, String> getProductStatusForPrepaidRequest(){
		
		LinkedHashMap<String, String> statuses = NonConfigurableConstants.PRODUCT_STATUS;
		
		//clone a new statues map
		LinkedHashMap<String, String> condolidatedStatuses = Maps.newLinkedHashMap();
		
		for (String key: statuses.keySet()) {
			condolidatedStatuses.put(key, statuses.get(key));
		}
		
		condolidatedStatuses.remove(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
		condolidatedStatuses.remove(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
		condolidatedStatuses.remove(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
		
		return condolidatedStatuses;
		
	}
	
	
	
	
	
	
}
