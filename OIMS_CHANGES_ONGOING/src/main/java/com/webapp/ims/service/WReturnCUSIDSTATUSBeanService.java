package com.webapp.ims.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.webapp.ims.model.ApplicantDetails;

public interface WReturnCUSIDSTATUSBeanService {

	String createApplicantDetailObject(ApplicantDetails applicantDetails)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	Map<String, String> WReturn_CUSID_STATUS(ApplicantDetails applicantDetails);


}