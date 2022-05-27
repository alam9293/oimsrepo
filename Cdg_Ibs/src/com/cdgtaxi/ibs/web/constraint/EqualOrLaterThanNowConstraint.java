package com.cdgtaxi.ibs.web.constraint;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;

import com.cdgtaxi.ibs.util.DateUtil;

public class EqualOrLaterThanNowConstraint implements Constraint{
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		if(!(arg0 instanceof Datebox)){
			throw new ClassCastException("Error: This constraint only works for datebox");
		}
		
		if(arg1 instanceof Date){
			Date date = (Date)arg1;
			if(!DateUtil.isToday(date) && date.before(DateUtil.getCurrentDate())){
				throw new WrongValueException(arg0, "* Date must be equal to or later than current date");
			}
		}
	}
}