package com.webapp.ims.dis.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl11SancdDtl;
import com.webapp.ims.dis.service.Tbl11SancDtlService;
import com.webapp.ims.dis.repository.Tbl11SancDtlServiceRepository;

@Service
@Transactional
public class Tbl11SancDtlServiceImpl implements Tbl11SancDtlService 
{
	private final Logger logger = LoggerFactory.getLogger(Tbl11SancDtlServiceImpl.class);
    
	@Autowired
	Tbl11SancDtlServiceRepository Tbl11SancDtlServiceRepository;
	
	
	
	@Override
	public void saveSancdAmountDtlTbl11(EvaluateApplicationDisTbl11SancdDtl Tbl11SancdDtl)
	{
		logger.info("saveSancdAmountDtlTbl11 method start");
		Tbl11SancDtlServiceRepository.save(Tbl11SancdDtl);
	}

	@Override
	public void removeSancdAmountDtlTbl11(String id) 
	{
		logger.info("removeSancdAmountDtlTbl11 method start");
		Tbl11SancDtlServiceRepository.deleteById(id);
	}

	@Override
	public List<EvaluateApplicationDisTbl11SancdDtl> getDataSancdDtlTbl11byAppid(String AppId) {
		// TODO Auto-generated method stub
		return Tbl11SancDtlServiceRepository.fetchTbl11SancDataJoin(AppId);
	}

	@Override
	public Date getCurrentTime() {
		// TODO Auto-generated method stub
		return Tbl11SancDtlServiceRepository.getCurrentTime();
	}
    
	
	
}
