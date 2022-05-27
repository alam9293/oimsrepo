package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;

public class NumberLessOrGreaterConstraint extends RequiredConstraint {

	BigDecimal lessValue ;
	BigDecimal greaterValue;
	boolean isLessEqual;
	boolean isGreaterEqual;
	boolean isMandatory;
	
	public NumberLessOrGreaterConstraint(boolean isMandatory, Number lessValue, boolean isLessEqual, Number greaterValue, boolean isGreaterEqual)
	{
		this.isMandatory = isMandatory;
		this.lessValue = new BigDecimal(lessValue.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.greaterValue = new BigDecimal(greaterValue.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.isLessEqual = isLessEqual;
		this.isGreaterEqual = isGreaterEqual;
	}
	
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		
		if(isMandatory)
			super.validate(arg0, arg1);
		
		if(arg1!=null){
			
			BigDecimal amount = new BigDecimal(String.valueOf(arg1));
			if(isLessEqual) {
				if(amount.compareTo(lessValue)>0)
					throw new WrongValueException(arg0, "* Must be equal or lesser than " + lessValue);
			}
			else {
				if(amount.compareTo(lessValue)>=0)
					throw new WrongValueException(arg0, "* Must be lesser than " + lessValue);
			}
				
			if(isGreaterEqual) {
				if(amount.compareTo(greaterValue)<0)
					throw new WrongValueException(arg0, "* Must be equal or greater than " + greaterValue);
			}
			else {
				if(amount.compareTo(greaterValue)<=0)
					throw new WrongValueException(arg0, "* Must be greater than " + greaterValue);
			}
		}
	}
	
}
