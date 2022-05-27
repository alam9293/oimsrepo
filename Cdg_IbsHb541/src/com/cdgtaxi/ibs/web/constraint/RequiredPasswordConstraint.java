package com.cdgtaxi.ibs.web.constraint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class RequiredPasswordConstraint extends RequiredConstraint implements Constraint{
	//Contain at least a number and character
	private Pattern passwordPattern = Pattern.compile("^\\w*(?=\\w*\\d)(?=\\w*[a-zA-Z])\\w*$");
	private int minLength = 8;
	private int maxLength = 20;
	
	public void validate(Component component, Object value)
		throws WrongValueException {
		
		super.validate(component, value);
		
//		String errorMsg = "";
//		String password = (String)value;
//		
//		if(password.length()<minLength || password.length()>maxLength)
//			errorMsg += "* Password length must be between "+minLength+" to "+maxLength+" characters\n";
////			throw new WrongValueException(component, "Password length must be between 8 to 20 characters"); 
//		
//		Matcher m = passwordPattern.matcher((String)value);
//		boolean b = m.matches();
//		
//		if(b==false)
//			errorMsg += "* Invalid password format\n";
////			throw new WrongValueException(component, "Invalid password format");   
//		
//		if(!errorMsg.equals("")){
//			throw new WrongValueException(component, errorMsg);
//		}
	}
}
