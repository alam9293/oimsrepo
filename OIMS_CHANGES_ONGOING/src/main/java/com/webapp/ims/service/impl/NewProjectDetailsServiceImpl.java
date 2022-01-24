package com.webapp.ims.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.webapp.ims.model.DeptNewProjectDetails;
import com.webapp.ims.model.NewProjectDetails;

import com.webapp.ims.repository.DeptNewProjectDetailsRepository;
import com.webapp.ims.repository.NewProjectRepository;
import com.webapp.ims.service.NewProjectDetailsService;

@Service
@Transactional
public class NewProjectDetailsServiceImpl implements NewProjectDetailsService{

	@Autowired
	NewProjectRepository newProjDtlRepository;
	@Autowired
	DeptNewProjectDetailsRepository deptnewProjectDetailsRepository;
	
	@Override
	public List<NewProjectDetails> getNewProjList() {
		
		return newProjDtlRepository.findAll();
	}

	@Override
	public void saveNewProject(NewProjectDetails newProjDetails) {
		newProjDtlRepository.saveAndFlush(newProjDetails);
		
	}

	@Override
	public List<NewProjectDetails> saveNewProjList(List<NewProjectDetails> newProjDetailsList) {
		
		return saveNewProjListExtra(newProjDetailsList);
	}
	
	public List<NewProjectDetails> saveNewProjListExtra(List<NewProjectDetails> newProjDetailsList) {
		List<DeptNewProjectDetails> extraPropsnew = new ArrayList<>();

		if (!CollectionUtils.isEmpty(newProjDetailsList)) {
			for (NewProjectDetails pd : newProjDetailsList) {
				DeptNewProjectDetails deptNewProjectDetails = new DeptNewProjectDetails();
				BeanUtils.copyProperties(pd, deptNewProjectDetails);
				extraPropsnew.add(deptNewProjectDetails);
			}
		}
		deptnewProjectDetailsRepository.saveAll(extraPropsnew);

		return newProjDtlRepository.saveAll(newProjDetailsList);
	}

	@Override
	public List<NewProjectDetails> getNewProjListById(String id) {
		
		return newProjDtlRepository.findNewProjListByNpdId(id);
	}

	@Override
	public void deleteNewProjById(String id) {
		newProjDtlRepository.deleteById(id);
		
	}

	@Override
	public void deleteNewProject(NewProjectDetails newProjDetails) {
		newProjDtlRepository.delete(newProjDetails);
		
	}

}
