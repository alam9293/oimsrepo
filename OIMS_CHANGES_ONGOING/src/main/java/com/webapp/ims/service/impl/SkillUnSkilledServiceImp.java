package com.webapp.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.webapp.ims.repository.SkillUnSkilledRepository;
import com.webapp.ims.service.SkillUnSkilledService;

public class SkillUnSkilledServiceImp implements SkillUnSkilledService{

	@Autowired
	SkillUnSkilledRepository skillUnSkilledRepository;

	@Override
	public void deleteByAllById(String id) {
		skillUnSkilledRepository.deleteById(id);
		
	}
	

}
