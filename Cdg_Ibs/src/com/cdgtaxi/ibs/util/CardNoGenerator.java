package com.cdgtaxi.ibs.util;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbCardNoSequence;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.google.common.base.Strings;

public class CardNoGenerator {

	private static final Logger logger = Logger.getLogger(CardNoGenerator.class);

	//The existing card no generator is extremely messy, this is the the version that more human readable
	//All the business logic should be same with previous version. 
	//Same with previous version, this generator is not thread safe, that might be same card no being generated, not idea why this can happen.
	
	
	public static Integer nextCardNoSeq(PmtbCardNoSequence seq) {

		BigDecimal count = seq.getCount();
		return count.add(BigDecimal.valueOf(1)).intValue();
	}
	

	public static String generateCardNo(Integer number, PmtbProductType productType) {

		String binRange = productType.getBinRange();
		String subBinRange = productType.getSubBinRange();
		String cardNoPefix = "";
		String cardNoSuffix = "";
		String startNumberStr = String.valueOf(number);
		
		if (binRange != null && !NonConfigurableConstants.BOOLEAN_NA.equals(binRange)) {
			cardNoPefix = productType.getBinRange();
		}
		if (subBinRange != null && !NonConfigurableConstants.BOOLEAN_NA.equals(subBinRange)) {
			cardNoPefix += productType.getSubBinRange();
		}
		
		int defaultCardLen = productType.getNumberOfDigit();
		
		int cardNoSuffixLen = defaultCardLen - cardNoPefix.length();
		String cardNo = "";
		if (NonConfigurableConstants.getBoolean(productType.getLuhnCheck())) {
			cardNoSuffix = Strings.padStart(startNumberStr, cardNoSuffixLen - 1, '0');
			cardNo = cardNoPefix + cardNoSuffix;
			cardNo = ProductUtil.getluhnCheckedCardNo(cardNo);
		} else {
			cardNoSuffix = Strings.padStart(startNumberStr, cardNoSuffixLen, '0');
			cardNo = cardNoPefix + cardNoSuffix;
		}

		
		
		if(cardNo.length() > defaultCardLen){
			throw new WrongValueException("	Card No:  " + cardNo +" length is over the Maximum length");
		}
		

		logger.info("Card No: "  + cardNo + " Luhn Check: " + productType.getLuhnCheck());

		return cardNo;
	}


	public static void main(String[] args) {

		PmtbProductType type = new PmtbProductType();
		type.setBinRange("123456");
		type.setSubBinRange("7890");
		type.setLuhnCheck(NonConfigurableConstants.BOOLEAN_YN_NO);
		type.setNumberOfDigit(12);
		type.setOneTimeUsage(NonConfigurableConstants.BOOLEAN_YN_YES);
		
		
		
		
	}

}
