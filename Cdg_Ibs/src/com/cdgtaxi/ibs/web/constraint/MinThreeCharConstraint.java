package com.cdgtaxi.ibs.web.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class MinThreeCharConstraint implements Constraint {

	public void validate(Component arg0, Object arg1) throws WrongValueException {
		if(arg1!=null && arg1.toString().length()!=0){
			if(arg1.toString().length()>3){
				throw new WrongValueException(arg0, "* Min 3 characters");
			}
		}
	}

}
