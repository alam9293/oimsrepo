package com.cdgtaxi.ibs.web.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;

public class RequiredConstraint implements Constraint{

	public void validate(Component arg0, Object arg1)
			throws WrongValueException {
		
		if(arg1==null || arg1.toString().length()==0){
			throw new WrongValueException(arg0, "* Mandatory field");   
		}
	}
	
	public static void validate(Combobox combobox){
		if(combobox.getSelectedItem()==null
				|| combobox.getRawText()==null
				|| combobox.getRawText().length()==0)
			throw new WrongValueException(combobox, "* Mandatory field");  
	}
}
