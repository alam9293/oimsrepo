package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class RequiredGreaterThanZeroConstraint extends RequiredConstraint implements Constraint{

	public void validate(Component component, Object value)
			throws WrongValueException {
		
		super.validate(component, value);
		
		if(value instanceof BigDecimal){
			if(((BigDecimal)value).compareTo(new BigDecimal(0)) <= 0)
				throw new WrongValueException(component, "* Value must be greater than 0");
		}
		else if(value instanceof Double){
			if(((Double)value) <= 0)
				throw new WrongValueException(component, "* Value must be greater than 0");
		}
		else if(value instanceof Long){
			if(((Long)value) <= 0)
				throw new WrongValueException(component, "* Value must be greater than 0");
		}
		else if(value instanceof Integer){
			if(((Integer)value) <= 0)
				throw new WrongValueException(component, "* Value must be greater than 0");
		}
	}
}
