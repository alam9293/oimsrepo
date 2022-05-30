package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;

public class RequiredHiLoCustomLengthBigDecimalConstraint extends RequiredConstraint{
	private BigDecimal hiLimit;
	private BigDecimal loLimit;
	private final String nine = "9";
	
	/**
	 * To create the max limit based on the input.
	 * This constraint is only for decimalbox.
	 * E.g. input 10 will construct a limit of 9999999999.99
	 * @param maxLength
	 */
	public RequiredHiLoCustomLengthBigDecimalConstraint(int maxLength){
		if(maxLength <=0)
			throw new IllegalArgumentException("Invalid argument!");
		
		String maxValue = "";
		for(int i=0; i<maxLength; i++){
			maxValue += nine;
		}
		
		hiLimit = new BigDecimal(maxValue+".99");
		loLimit = new BigDecimal(maxValue+".99").negate();
	}
	
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		super.validate(arg0, arg1);
		
		if(!(arg0 instanceof Decimalbox)){
			throw new ClassCastException("Error: This constraint only works for decimalbox");
		}
		
		if(arg1!=null){
			BigDecimal amount = (BigDecimal)arg1;
			if(amount.compareTo(hiLimit)>0){
				throw new WrongValueException(arg0, "* Must be lesser than or equals to "+hiLimit.toString());
			}
			else if(amount.compareTo(loLimit)<0){
				throw new WrongValueException(arg0, "* Must be greater than or equals to "+loLimit.toString());
			}
		}
	}
}