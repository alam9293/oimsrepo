package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;

public class RequiredCustomLengthBigDecimalConstraint extends RequiredZeroOrGreaterConstraint{
	private BigDecimal limit;
	private final String nine = "9";
	
	/**
	 * To create the max limit based on the input.
	 * This constraint is only for decimalbox.
	 * E.g. input 10 will construct a limit of 9999999999.99
	 * @param maxLength
	 */
	public RequiredCustomLengthBigDecimalConstraint(int maxLength){
		if(maxLength <=0)
			throw new IllegalArgumentException("Invalid argument!");
		
		String maxValue = "";
		for(int i=0; i<maxLength; i++){
			maxValue += nine;
		}
		
		limit = new BigDecimal(maxValue+".99");
	}
	
	/**
	 * To create the max limit based on the input.
	 * This constraint is only for decimalbox.
	 * @param limit e.g. 100.00 99.99 9999.99
	 */
	public RequiredCustomLengthBigDecimalConstraint(String limit){
		if(limit==null || limit.length()==0)
			throw new IllegalArgumentException("Invalid argument!");
		
		this.limit = new BigDecimal(limit);
	}
	
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		super.validate(arg0, arg1);
		
		if(!(arg0 instanceof Decimalbox)){
			throw new ClassCastException("Error: This constraint only works for decimalbox");
		}
		
		if(arg1!=null){
			BigDecimal amount = (BigDecimal)arg1;
			if(amount.compareTo(limit)>0){
				throw new WrongValueException(arg0, "* Must be lesser than or equals to "+limit.toString());
			}
		}
	}
}