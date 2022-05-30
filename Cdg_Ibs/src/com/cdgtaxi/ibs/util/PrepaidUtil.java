package com.cdgtaxi.ibs.util; 

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;

import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.forms.ApplyAmtInfo;
import com.cdgtaxi.ibs.common.model.forms.TransferableAmtInfo;


public class PrepaidUtil {
	
	private static Logger logger = Logger.getLogger(PrepaidUtil.class);
	
	//the usage amount is apply to card value first then cash plus
	public static ApplyAmtInfo calculateCardValuePrefApplyAmount(BigDecimal cardValue, BigDecimal cashPlus, BigDecimal usageAmt){
		
		BigDecimal applyCardValue = BigDecimal.ZERO;
		BigDecimal applyCashPlus = BigDecimal.ZERO;
		BigDecimal applyCreditBalance = usageAmt!=null ? usageAmt : BigDecimal.ZERO;
		
		BigDecimal exceedUsage = cardValue.subtract(usageAmt);
		
		if(exceedUsage.doubleValue()<0){
			applyCardValue = cardValue;
			applyCashPlus = exceedUsage.negate();
			
			//if result causing cash plus causing negative, the exceed amount will be deduct from card value, and card value will resulted in negative
			BigDecimal exeedCashPlus = cashPlus.subtract(applyCashPlus);
			if(exeedCashPlus.doubleValue() < 0){
				applyCashPlus = cashPlus;
				applyCardValue = applyCardValue.add(exeedCashPlus.negate());
			}
			
		} else {
			applyCardValue = usageAmt;
		}
		
		ApplyAmtInfo applyAmount= new ApplyAmtInfo();
		
		applyAmount.setApplyCardValue(applyCardValue);
		applyAmount.setApplyCashplus(applyCashPlus);
		applyAmount.setApplyCreditBalance(applyCreditBalance);
	
		return applyAmount;
		
	}
	
	//the usage amount is apply to cash plus first then card value
	public static TransferableAmtInfo calculateCashplusPrefApplyAmount(BigDecimal cardValue, BigDecimal cashPlus, BigDecimal usageAmt){
		
		BigDecimal applyCardValue = BigDecimal.ZERO;
		BigDecimal applyCashPlus = BigDecimal.ZERO;
		BigDecimal applyCreditBalance = usageAmt!=null ? usageAmt : BigDecimal.ZERO;
		
		BigDecimal exceedUsage = cashPlus.subtract(usageAmt);
		
		if(exceedUsage.doubleValue()<0){
			applyCardValue = exceedUsage.negate();
			applyCashPlus = cashPlus;
			
		} else {
			applyCashPlus = usageAmt;
		}
		
		TransferableAmtInfo transferableAmtInfo= new TransferableAmtInfo();
		
		transferableAmtInfo.setCardValue(applyCardValue);
		transferableAmtInfo.setCashplus(applyCashPlus);
		transferableAmtInfo.setCreditBalance(applyCreditBalance);
	
		return transferableAmtInfo;
		
	}
	
	
	public static ApplyAmtInfo deductProductAmtWithApplyAmtInfo(PmtbProduct product, ApplyAmtInfo applyAmount, boolean zeroConstraint){
		
		logger.debug("deduct amount of product " + product.getProductNo() + " with ApplyAmtInfo");
		
		BigDecimal cardValue = product.getCardValue();
		BigDecimal cashPlus = product.getCashplus();
		BigDecimal creditBalance = product.getCreditBalance();
		
		BigDecimal applyCardValue = applyAmount.getApplyCardValue();
		BigDecimal applyCashPlus = applyAmount.getApplyCashplus();
		BigDecimal applyCreditBalance = applyAmount.getApplyCreditBalance();
		
		BigDecimal finalCardValue = cardValue.subtract(applyCardValue);
		BigDecimal finalCashplus = cashPlus;
		
		if(applyCashPlus!=null){
			finalCashplus = cashPlus.subtract(applyCashPlus);
		}
	
		BigDecimal finalCreditBalance = creditBalance.subtract(applyCreditBalance);
		
		//this mean the value and cash back are not enough to deduct the usage fee
		if(zeroConstraint && creditBalance.doubleValue() < 0){
			throw new WrongValueException("Failed to proceed, as the opration will cause total credit balance lesser than zero.");
		}
	
		logger.debug("card value: " + cardValue + " -> " + finalCardValue + " (" + applyCardValue +")");
		logger.debug("cash plus: " + cashPlus + " -> " + finalCashplus  + " (" + applyCashPlus +")" );
		logger.debug("credit balance: " + creditBalance + " -> " + finalCreditBalance + " (" + applyCreditBalance +")" );
		
		//set the value to product
		product.setCreditBalance(finalCreditBalance);
		product.setCardValue(finalCardValue);
		product.setCashplus(finalCashplus);
		
		return applyAmount;
		
	}
	
	
	public static ApplyAmtInfo addProductAmtWithApplyAmtInfo(PmtbProduct product, ApplyAmtInfo applyAmount){
		
		logger.debug("add amount of product " + product.getProductNo() + " with ApplyAmtInfo");
		
		BigDecimal cardValue = product.getCardValue() !=null ? product.getCardValue() : BigDecimal.ZERO;
		BigDecimal cashPlus = product.getCashplus() !=null ? product.getCashplus() : BigDecimal.ZERO;
		BigDecimal creditBalance = product.getCreditBalance() !=null ? product.getCreditBalance() : BigDecimal.ZERO;
		
		BigDecimal applyCardValue = applyAmount.getApplyCardValue();
		BigDecimal applyCashPlus = applyAmount.getApplyCashplus();
		BigDecimal applyCreditBalance = applyAmount.getApplyCreditBalance();
		
		BigDecimal finalCardValue = cardValue.add(applyCardValue);
		BigDecimal finalCashplus = cashPlus;
		
		if(applyCashPlus!=null){
			finalCashplus = cashPlus.add(applyCashPlus);
		}
	
		BigDecimal finalCreditBalance = creditBalance.add(applyCreditBalance);
		
		logger.debug("card value: " + cardValue + " -> " + finalCardValue + " (" + applyCardValue +")");
		logger.debug("cash plus: " + cashPlus + " -> " + finalCashplus  + " (" + applyCashPlus +")" );
		logger.debug("credit balance: " + creditBalance + " -> " + finalCreditBalance + " (" + applyCreditBalance +")" );
		
		//set the value to product
		product.setCreditBalance(finalCreditBalance);
		product.setCardValue(finalCardValue);
		product.setCashplus(finalCashplus);
		
		return applyAmount;
		
	}
	
	
	
	public static void main(String[] args){
		
		
		ApplyAmtInfo calculateApplyAmount = calculateCardValuePrefApplyAmount(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(5));
		
		System.out.println(calculateApplyAmount);
	}
	
	
}