package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DissbursmentReimbrsDepositeGST;
import com.webapp.ims.dis.repository.DisbursmentReimbrsofDepositGSTRepository;
import com.webapp.ims.dis.service.DisbursmentRiembrsDepositGSTService;

@Service
@Transactional
public class DisbursmentReimbrsDepositGSTServiceImpl implements DisbursmentRiembrsDepositGSTService {

	@Autowired
	DisbursmentReimbrsofDepositGSTRepository disbursReimDepositGSTRepository;

	@Override
	public DissbursmentReimbrsDepositeGST saveDisbursmentReimbrsDetails(
			DissbursmentReimbrsDepositeGST disReimbrsDetails) {
		return disbursReimDepositGSTRepository.save(disReimbrsDetails);
	}

	@Override
	public DissbursmentReimbrsDepositeGST updateDisbursmentReimbrsDetails(
			DissbursmentReimbrsDepositeGST disReimbrsDetails) {
		return disbursReimDepositGSTRepository.save(disReimbrsDetails);
	}

	@Override
	public DissbursmentReimbrsDepositeGST getDetailsBydisAppId(String appId) {
		return disbursReimDepositGSTRepository.getDetailsBydisAppId(appId);
	}

}
