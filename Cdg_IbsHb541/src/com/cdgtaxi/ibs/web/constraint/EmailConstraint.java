package com.cdgtaxi.ibs.web.constraint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class EmailConstraint implements Constraint{

	private Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
	
	public void validate(Component arg0, Object arg1)
			throws WrongValueException {
		if(arg1!=null && arg1.toString().length()!=0){
			Matcher m = emailPattern.matcher((String)arg1);
			boolean b = m.matches();
			if(b==false){
				throw new WrongValueException(arg0, "* Invalid email format");
			}
		}
	}
}
