package com.webapp.ims.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.Department;
import com.webapp.ims.repository.DepartmentRepository;
import com.webapp.ims.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentRepository deptRepository;

	@Override
	public String findDeptIdByDeptName(String deptName) {
		return deptRepository.findDeptIdBynewDeptName(deptName);
	}

	@Override
	public List<Department> saveDepartmentList(List<Department> deptList) {

		Set<String> emailList = new HashSet<>();
		Iterable<Department> it = getDepartmentList();
		for (Department department : it) {
			emailList.add(department.getDeptEmail());
		}
		List<Department> finalList = new ArrayList<>();
		for (Department department : deptList) {
			if (!emailList.contains(department.getDeptEmail())) {
				finalList.add(department);
			}
		}
		return (List<Department>) deptRepository.saveAll(finalList);
	}

	@Override
	public Iterable<Department> getDepartmentList() {
		return deptRepository.findAll();
	}
}
