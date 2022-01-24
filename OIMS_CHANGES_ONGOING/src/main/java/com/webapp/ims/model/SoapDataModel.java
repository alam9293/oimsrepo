package com.webapp.ims.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SoapDataModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	Map<String, String> niveshSoapResponse = new HashMap<>();

	public Map<String, String> getNiveshSoapResponse() {
		return niveshSoapResponse;
	}

	public void setNiveshSoapResponse(Map<String, String> niveshSoapResponse) {
		this.niveshSoapResponse = niveshSoapResponse;
	}

}
