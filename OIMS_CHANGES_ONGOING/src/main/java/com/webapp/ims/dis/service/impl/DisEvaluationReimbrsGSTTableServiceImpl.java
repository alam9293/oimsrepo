package com.webapp.ims.dis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DisEvaluationReimbrsDepositeGstTable;
import com.webapp.ims.dis.repository.DisEvaluationReimbrsGSTTableRepository;
import com.webapp.ims.dis.service.DisEvaluationReimbrsGStTableService;

@Service
@Transactional
public class DisEvaluationReimbrsGSTTableServiceImpl implements DisEvaluationReimbrsGStTableService {

	@Autowired
	DisEvaluationReimbrsGSTTableRepository reimbrsGStTableRepo;

	@Override
	public List<DisEvaluationReimbrsDepositeGstTable> saveReimbrsGSTTableList(
			List<DisEvaluationReimbrsDepositeGstTable> reimbrsGStTableList) {
		return reimbrsGStTableRepo.saveAll(reimbrsGStTableList);
	}

	@Override
	public List<DisEvaluationReimbrsDepositeGstTable> getListBydisAppId(String applId) {
		return reimbrsGStTableRepo.findBydisAppId(applId);
	}

}
