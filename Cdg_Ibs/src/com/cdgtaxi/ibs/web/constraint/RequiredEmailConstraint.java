package com.cdgtaxi.ibs.web.constraint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class RequiredEmailConstraint extends RequiredConstraint implements Constraint{

	private Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
	
	public void validate(Component component, Object value)
			throws WrongValueException {
		
		super.validate(component, value);
		
		Matcher m = emailPattern.matcher((String)value);
		boolean b = m.matches();
		
		if(b==false){
			throw new WrongValueException(component, "* Invalid email format");
		}
	}
}
