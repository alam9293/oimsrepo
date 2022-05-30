
package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class RequiredNumericConstraint extends RequiredConstraint implements Constraint{

	public void validate(Component arg0, Object arg1)
			throws WrongValueException {
		
		super.validate(arg0, arg1);
		
		String number=arg1.toString();
		try{
			BigDecimal numHolder=new BigDecimal(number);
		}
		catch(NumberFormatException nfe){
			throw new WrongValueException(arg0, "* Numeric field, no character is allowed");   
		}
	}
}
