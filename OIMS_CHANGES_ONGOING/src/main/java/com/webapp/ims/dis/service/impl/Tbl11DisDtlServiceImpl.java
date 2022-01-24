package com.webapp.ims.dis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl11DisDtl;
import com.webapp.ims.dis.repository.Tbl11DisDtlServiceRepository;
import com.webapp.ims.dis.service.Tbl11DisDtlService;

@Service
@Transactional
public class Tbl11DisDtlServiceImpl implements Tbl11DisDtlService 
{
	private final Logger logger = LoggerFactory.getLogger(Tbl11DisDtlServiceImpl.class);
    
	@Autowired
	Tbl11DisDtlServiceRepository Tbl11DisDtlServiceRepository;
	
	
	
	@Override
	public void saveDisAmountDtlTbl11(EvaluateApplicationDisTbl11DisDtl Tbl11SancdDtl)
	{
		logger.info("saveSancdAmountDtlTbl11 method start");
		Tbl11DisDtlServiceRepository.save(Tbl11SancdDtl);
	}

	@Override
	public void removeDisAmountDtlTbl11(String id) 
	{
		logger.info("removeSancdAmountDtlTbl11 method start");
		Tbl11DisDtlServiceRepository.deleteById(id);
	}

	@Override
	public List<EvaluateApplicationDisTbl11DisDtl> getDataDisDtlTbl11byAppid(String AppId) {
		return Tbl11DisDtlServiceRepository.fetchTbl11DisDataJoin(AppId);
	}
	
	@Override
	public EvaluateApplicationDisTbl11DisDtl getDataDisDtlTbl11byId(String Id) {
		return Tbl11DisDtlServiceRepository.getOne(Id);
	}
    
	@Override
	public Date getCurrentTime() {
		// TODO Auto-generated method stub
		return Tbl11DisDtlServiceRepository.getCurrentTime();
	}
	
	
}
