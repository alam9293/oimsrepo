package com.cdgtaxi.ibs.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cdgtaxi.ibs.common.model.forms.GstInfo;


public class GstUtil {

	
	public static final int AMT_WITHOUT_GST = 0;
	public static final int GST = 1;
	public static final int AMT_WITH_GST = 2;
	
	public static BigDecimal backwardCalculateGst(BigDecimal amount, BigDecimal gstRate){
		
		if(amount!=null){
			//multiple amount then divide instead of divide then multiple to solve Non-terminating decimal expansion exception
			//BigDecimal gst = amount.divide(BigDecimal.valueOf(100).add(gstRate)).multiply(gstRate);
			
			BigDecimal gst = amount.multiply(gstRate).divide(BigDecimal.valueOf(100).add(gstRate), 2, RoundingMode.HALF_UP);
			
			return gst;
		}
		return null;
	}
	
	public static GstInfo backwardCalculateGstInfo(BigDecimal amount, BigDecimal gstRate){
		
		BigDecimal amtWithoutGst = null;
		BigDecimal gst = null;
		BigDecimal amtWithGst = null;
		
		GstInfo gstInfo = new GstInfo();
		
		if(amount!=null){
		
			if(gstRate!=null){
				
				gst = backwardCalculateGst(amount, gstRate);
				amtWithoutGst = amount.subtract(gst);
				amtWithGst = amount;
				
			} else {
				amtWithoutGst = amount;
				amtWithGst = amount;
			}
		}
		
		
		gstInfo.setAmountWithoutGst(amtWithoutGst);
		gstInfo.setGst(gst);
		gstInfo.setAmountWithGst(amtWithGst);
		
		return gstInfo;
		
	}
	
	
	public static BigDecimal forwardCalculateGst(BigDecimal amount, BigDecimal gstRate){
		
		if(amount!=null && gstRate!=null){
			BigDecimal gst = amount.multiply(gstRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			return gst;
		}
		return null;
	}
	
	

	
	public static GstInfo forwardCalculateGstInfo(BigDecimal amount, BigDecimal gstRate){
		
		BigDecimal amtWithoutGst = null;
		BigDecimal gst = null;
		BigDecimal amtWithGst = null;
		
		GstInfo gstInfo= new GstInfo();
		
		if(amount!=null){
		
			if(gstRate!=null){
				
				gst = forwardCalculateGst(amount, gstRate);
				amtWithGst = amount.add(gst);
				amtWithoutGst = amount;
				
			} else {
				amtWithoutGst = amount;
				amtWithGst = amount;
			}
		}
		
		gstInfo.setAmountWithoutGst(amtWithoutGst);
		gstInfo.setGst(gst);
		gstInfo.setAmountWithGst(amtWithGst);
		
		return gstInfo;
		
	}
	
	
	public static void main(String[] args){
		
		GstInfo info =forwardCalculateGstInfo(BigDecimal.valueOf(-30), BigDecimal.valueOf(7.2323232));
		System.out.println("amtWithoutGst: " + info.getAmountWithoutGst());
		System.out.println("gst:" + info.getGst());
		System.out.println("amtWithGst: " + info.getAmountWithGst());
	
	}
	

	
	
}
