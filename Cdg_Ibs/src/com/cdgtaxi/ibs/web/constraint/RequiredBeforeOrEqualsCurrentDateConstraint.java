package com.cdgtaxi.ibs.web.constraint;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;

import com.cdgtaxi.ibs.util.DateUtil;

public class RequiredBeforeOrEqualsCurrentDateConstraint extends RequiredConstraint implements Constraint{

	public void validate(Component arg0, Object arg1)
			throws WrongValueException {
		
		if(!(arg0 instanceof Datebox))
			throw new ClassCastException("Error: This constraint only works for datebox");
		
		super.validate(arg0, arg1);
		
		Date currentDate = DateUtil.convertSqlDateToUtilDate(DateUtil.getCurrentDate());
		if(((Date)arg1).compareTo(currentDate) > 0)
			throw new WrongValueException(arg0, "* Date must be before or equals to current date");
	}
}
