package com.cdgtaxi.ibs.web.constraint;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;

public class LaterThanNowConstraint extends RequiredConstraint {
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		super.validate(arg0, arg1);
		if(arg1!=null && arg1 instanceof Date && ((Date)arg1).before(Calendar.getInstance().getTime())){
			throw new WrongValueException(arg0, "* Must be later than today");   
		}
	}
}