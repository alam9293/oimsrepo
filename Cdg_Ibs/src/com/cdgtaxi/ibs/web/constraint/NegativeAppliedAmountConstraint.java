package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class NegativeAppliedAmountConstraint implements Constraint{

	private Object amountNotToBeExceeded;
	
	public NegativeAppliedAmountConstraint(Object amountNotToBeExceeded){
		this.amountNotToBeExceeded = amountNotToBeExceeded;
	}
	
	public void validate(Component component, Object value)
			throws WrongValueException {
		
		//Not required, so if null let's ignore
		if(value==null) return;
		
		if(!(amountNotToBeExceeded.getClass().equals(value.getClass())))
			throw new ClassCastException("Error: Both the amounts to be validated are of different Class e.g. Long and Integer");
		
		if(value instanceof BigDecimal){
			if(((BigDecimal)value).compareTo(BigDecimal.ZERO) > 0)
				throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
			else
				if(((BigDecimal)value).compareTo((BigDecimal)amountNotToBeExceeded) < 0)
					throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
		}
		else if(value instanceof Double){
			if(((Double)value).compareTo(new Double(0)) > 0)
				throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
			else
				if(((Double)value).compareTo((Double)amountNotToBeExceeded) < 0)
					throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
		}
		else if(value instanceof Long){
			if(((Long)value).compareTo(new Long(0)) > 0)
				throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
			else
				if(((Long)value).compareTo((Long)amountNotToBeExceeded) < 0)
					throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
		}
		else if(value instanceof Integer){
			if(((Integer)value).compareTo(new Integer(0)) > 0)
				throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
			else
				if(((Integer)value).compareTo((Integer)amountNotToBeExceeded) < 0)
					throw new WrongValueException(component, "* Invalid applied amount, please adjust amount");
		}
	}
}
