package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.service.ConcernDepartmentService;
import com.webapp.ims.repository.ConcernDepartmentRepository;

@Service
@Transactional
public class ConcernDepartmentServiceImpl implements ConcernDepartmentService {
	@Autowired
	ConcernDepartmentRepository conDeptRepository;

	@Override
	public List<ConcernDepartment> getConcernDepartmentList() {
		return (List<ConcernDepartment>) conDeptRepository.findAll();
	}

	@Override
	public ConcernDepartment saveConcernDepartment(ConcernDepartment concernDept) {
		return conDeptRepository.save(concernDept);
	}

	@Override
	public List<ConcernDepartment> saveConcernDepartmentList(List<ConcernDepartment> conDeptList) {
		return (List<ConcernDepartment>) conDeptRepository.saveAll(conDeptList);
	}

	@Override
	public Optional<ConcernDepartment> getConcernDepartmentById(String id) {
		return conDeptRepository.findById(id);
	}

	@Override
	public void deleteConcernDepartmentById(String id) {
		conDeptRepository.deleteById(id);
	}

}
