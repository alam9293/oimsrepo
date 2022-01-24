package com.webapp.ims.dis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;
import com.webapp.ims.dis.repository.ReimbrsGSTTableRepository;
import com.webapp.ims.dis.service.DisReimbrsGStTableService;

@Service
@Transactional
public class DisReimbrsGSTTableServiceImpl implements DisReimbrsGStTableService {

	@Autowired
	ReimbrsGSTTableRepository reimbrsGStTableRepo;

	@Override
	public List<DisReimbrsDepositeGstTable> saveReimbrsGSTTableList(
			List<DisReimbrsDepositeGstTable> reimbrsGStTableList) {
		return reimbrsGStTableRepo.saveAll(reimbrsGStTableList);
	}

	@Override
	public List<DisReimbrsDepositeGstTable> getListBydisAppId(String applId) {
		return reimbrsGStTableRepo.findBydisAppId(applId);
	}

	@Override
	public void deletedisId(String id) {
		reimbrsGStTableRepo.deleteById(id);

	}

}
