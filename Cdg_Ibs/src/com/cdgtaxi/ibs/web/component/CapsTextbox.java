package com.cdgtaxi.ibs.web.component;

import org.zkoss.zul.Textbox;

public class CapsTextbox extends Textbox{
	
	public String getValue(){
		String value = super.getValue();
		if(value!=null) value = value.toUpperCase().trim();
		return value;
	}
}
