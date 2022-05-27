package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Decimalbox;

public class NumberRangeConstraint implements Constraint{

	BigDecimal lowestValue ;
	BigDecimal highestValue;
	
	public NumberRangeConstraint(Number lowestValue, Number highestValue)
	{
		this.lowestValue = new BigDecimal(lowestValue.toString());
		this.highestValue = new BigDecimal(highestValue.toString());
	}
	
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		
		if(arg1!=null){
			Number number = (Number)arg1;
			BigDecimal amount = new BigDecimal(number.toString());
			
			if(amount.compareTo(lowestValue)==-1 || amount.compareTo(highestValue)==1){
				throw new WrongValueException(arg0, "* Must be in range of " + lowestValue + " to " + highestValue);
			}
		}
	}
}
