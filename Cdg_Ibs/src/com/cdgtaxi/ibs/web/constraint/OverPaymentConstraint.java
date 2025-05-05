package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class OverPaymentConstraint extends NonNegativityConstraint implements Constraint{

	private Object amountNotToBeExceeded;
	
	public OverPaymentConstraint(Object amountNotToBeExceeded){
		this.amountNotToBeExceeded = amountNotToBeExceeded;
	}
	
	public void validate(Component component, Object value)
			throws WrongValueException {
		
		//Not required, so if null let's ignore
		if(value==null) return;
		
		super.validate(component, value);
		
		if(!(amountNotToBeExceeded.getClass().equals(value.getClass())))
			throw new ClassCastException("Error: Both the amounts to be validated are of different Class e.g. Long and Integer");
		
		if(value instanceof BigDecimal){
			if(((BigDecimal)value).compareTo((BigDecimal)amountNotToBeExceeded) > 0)
				throw new WrongValueException(component, "* Over payment, please adjust payment amount");
		}
		else if(value instanceof Double){
			if(((Double)value) > (Double)amountNotToBeExceeded)
				throw new WrongValueException(component, "* Over payment, please adjust payment amount");
		}
		else if(value instanceof Long){
			if(((Long)value) > (Long)amountNotToBeExceeded)
				throw new WrongValueException(component, "* Over payment, please adjust payment amount");
		}
		else if(value instanceof Integer){
			if(((Integer)value) > (Integer)amountNotToBeExceeded)
				throw new WrongValueException(component, "* Over payment, please adjust payment amount");
		}
	}
}
