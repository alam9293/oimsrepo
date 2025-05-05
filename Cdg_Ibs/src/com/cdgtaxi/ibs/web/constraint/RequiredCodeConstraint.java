package com.cdgtaxi.ibs.web.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class RequiredCodeConstraint extends RequiredConstraint implements Constraint{
	public void validate(Component component, Object value) throws WrongValueException {
		super.validate(component, value);
		if(value.toString().length()!=4){
			throw new WrongValueException(component, "* 4 characters only");
		}
	}
}
