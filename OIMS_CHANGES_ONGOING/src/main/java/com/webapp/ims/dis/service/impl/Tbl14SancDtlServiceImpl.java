package com.webapp.ims.dis.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl14SancdDtl;
import com.webapp.ims.dis.service.Tbl14SancDtlService;
import com.webapp.ims.dis.repository.Tbl14SancDtlServiceRepository;

@Service
@Transactional
public class Tbl14SancDtlServiceImpl implements Tbl14SancDtlService 
{
	private final Logger logger = LoggerFactory.getLogger(Tbl14SancDtlServiceImpl.class);
    
	@Autowired
	Tbl14SancDtlServiceRepository Tbl14SancDtlServiceRepository;
	
	
	
	@Override
	public void saveSancdAmountDtlTbl14(EvaluateApplicationDisTbl14SancdDtl Tbl14SancdDtl)
	{
		logger.info("saveSancdAmountDtlTbl14 method start");
		Tbl14SancDtlServiceRepository.save(Tbl14SancdDtl);
	}

	@Override
	public void removeSancdAmountDtlTbl14(String id) 
	{
		logger.info("removeSancdAmountDtlTbl14 method start");
		Tbl14SancDtlServiceRepository.deleteById(id);
	}

	@Override
	public List<EvaluateApplicationDisTbl14SancdDtl> getDataSancdDtlTbl14byAppid(String AppId) {
		// TODO Auto-generated method stub
		return Tbl14SancDtlServiceRepository.fetchTbl14SancDataJoin(AppId);
	}
    
	@Override
	public Date getCurrentTime() {
		// TODO Auto-generated method stub
		return Tbl14SancDtlServiceRepository.getCurrentTime();
	}
	
}
