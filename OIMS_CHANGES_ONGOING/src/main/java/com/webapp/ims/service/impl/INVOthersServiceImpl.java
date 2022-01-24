package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.INVOthers;
import com.webapp.ims.repository.InvOthersRepository;
import com.webapp.ims.service.INVOthersService;

@Service
@Transactional
public class INVOthersServiceImpl implements INVOthersService {

	@Autowired
	private InvOthersRepository invOthersRepository;

	@Override
	public List<INVOthers> getAllByINV_ID(String INV_ID) {
		return invOthersRepository.findAllByinvid(INV_ID);
	}

	@Override
	public void save(INVOthers invOthers) {
		invOthersRepository.save(invOthers);
	}

	@Override
	public Optional<INVOthers> findById(String ID) {
		return invOthersRepository.findById(ID);

	}

	@Override
	public void deleteById(String ID) {
		invOthersRepository.deleteById(ID);
	}

}
