package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Decimalbox;

public class CapitalConstraint implements Constraint{
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		if(!(arg0 instanceof Decimalbox)){
			throw new ClassCastException("Error: This constraint only works for decimalbox");
		}
		if(arg1!=null){
			BigDecimal amount = (BigDecimal)arg1;
			if(amount.compareTo(new BigDecimal(9999999999999.99))>0){
				throw new WrongValueException(arg0, "* Must be lesser than 9999999999999.99");
			}else if(amount.compareTo(new BigDecimal(0))<0){
				throw new WrongValueException(arg0, "* Must be greater than 0");
			}
		}
	}
}