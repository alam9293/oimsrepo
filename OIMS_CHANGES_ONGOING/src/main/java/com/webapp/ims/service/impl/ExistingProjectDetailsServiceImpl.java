package com.webapp.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.webapp.ims.model.DeptExistingProjectDetails;
import com.webapp.ims.model.DeptProprietorDetails;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.repository.DeptExistingProjectDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.service.ExistingProjectDetailsService;

@Service
@Transactional
public class ExistingProjectDetailsServiceImpl implements ExistingProjectDetailsService {

	@Autowired
	ExistingProjectDetailsRepository existProjDtlRepository;
	@Autowired
	DeptExistingProjectDetailsRepository deptExistingProjectDetailsRepository;

	@Override
	public List<ExistingProjectDetails> getExistProjList() {
		return existProjDtlRepository.findAll();
	}

	@Override
	public void saveExistProject(ExistingProjectDetails existProjDetails) {
		existProjDtlRepository.saveAndFlush(existProjDetails);
	}

	@Override
	public List<ExistingProjectDetails> saveExistProjList(List<ExistingProjectDetails> existProjDetailsList) {
		return saveExistProjListExtra(existProjDetailsList);
	}

	public List<ExistingProjectDetails> saveExistProjListExtra(List<ExistingProjectDetails> existProjDetailsList) {
		List<DeptExistingProjectDetails> extraProps = new ArrayList<>();

		if (!CollectionUtils.isEmpty(existProjDetailsList)) {
			for (ExistingProjectDetails pd : existProjDetailsList) {
				DeptExistingProjectDetails deptExistingProjectDetails = new DeptExistingProjectDetails();
				BeanUtils.copyProperties(pd, deptExistingProjectDetails);
				extraProps.add(deptExistingProjectDetails);
			}
		}
		deptExistingProjectDetailsRepository.saveAll(extraProps);

		return existProjDtlRepository.saveAll(existProjDetailsList);
	}

	@Override
	public void deleteExistProjById(String id) {
		existProjDtlRepository.deleteById(id);
	}

	@Override
	public void deleteExistProject(ExistingProjectDetails existProjDetails) {
		existProjDtlRepository.delete(existProjDetails);
	}

	@Override
	public List<ExistingProjectDetails> getExistProjListById(String id) {
		return existProjDtlRepository.findExistProjListByEpdId(id);
	}

}
