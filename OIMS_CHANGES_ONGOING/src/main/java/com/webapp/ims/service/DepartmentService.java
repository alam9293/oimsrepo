package com.webapp.ims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.Department;

@Service
public interface DepartmentService {
	public String findDeptIdByDeptName(String deptName);

	public List<Department> saveDepartmentList(List<Department> deptList);

	public Iterable<Department> getDepartmentList();

}
