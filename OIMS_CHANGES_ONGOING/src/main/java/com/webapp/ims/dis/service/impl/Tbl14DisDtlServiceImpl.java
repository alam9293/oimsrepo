package com.webapp.ims.dis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl14DisDtl;
import com.webapp.ims.dis.repository.Tbl14DisDtlServiceRepository;
import com.webapp.ims.dis.service.Tbl14DisDtlService;

@Service
@Transactional
public class Tbl14DisDtlServiceImpl implements Tbl14DisDtlService 
{
	private final Logger logger = LoggerFactory.getLogger(Tbl14DisDtlServiceImpl.class);
    
	@Autowired
	Tbl14DisDtlServiceRepository Tbl14DisDtlServiceRepository;
	
	
	
	@Override
	public void saveDisAmountDtlTbl14(EvaluateApplicationDisTbl14DisDtl Tbl14SancdDtl)
	{
		logger.info("saveSancdAmountDtlTbl14 method start");
		Tbl14DisDtlServiceRepository.save(Tbl14SancdDtl);
	}

	@Override
	public void removeDisAmountDtlTbl14(String id) 
	{
		logger.info("removeSancdAmountDtlTbl14 method start");
		Tbl14DisDtlServiceRepository.deleteById(id);
	}

	@Override
	public List<EvaluateApplicationDisTbl14DisDtl> getDataDisDtlTbl14byAppid(String AppId) {
		return Tbl14DisDtlServiceRepository.fetchTbl14DisDataJoin(AppId);
	}
	
	@Override
	public EvaluateApplicationDisTbl14DisDtl getDataDisDtlTbl14byId(String Id) {
		return Tbl14DisDtlServiceRepository.getOne(Id);
	}
    
	@Override
	public Date getCurrentTime() {
		// TODO Auto-generated method stub
		return Tbl14DisDtlServiceRepository.getCurrentTime();
	}
	
	
}
