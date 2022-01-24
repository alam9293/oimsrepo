package com.webapp.ims.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class DisLogin  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String dislogin;
	
	private String	locNumber;
	
	
	
	public String getLocNumber() {
		return locNumber;
	}

	public void setLocNumber(String locNumber) {
		this.locNumber = locNumber;
	}

	public String getDislogin() {
		return dislogin;
	}

	public void setDislogin(String dislogin) {
		this.dislogin = dislogin;
	}

	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
}
