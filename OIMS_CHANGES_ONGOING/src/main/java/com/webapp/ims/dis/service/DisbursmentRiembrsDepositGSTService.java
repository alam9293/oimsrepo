package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.DissbursmentReimbrsDepositeGST;

@Service
public interface DisbursmentRiembrsDepositGSTService {

	public DissbursmentReimbrsDepositeGST saveDisbursmentReimbrsDetails(
			DissbursmentReimbrsDepositeGST disReimbrsDetails);

	public DissbursmentReimbrsDepositeGST updateDisbursmentReimbrsDetails(
			DissbursmentReimbrsDepositeGST disReimbrsDetails);

	public DissbursmentReimbrsDepositeGST getDetailsBydisAppId(String appId);
	
	

}