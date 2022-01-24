package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import com.webapp.ims.model.ConcernDepartment;

public interface ConcernDepartmentService {

	public List<ConcernDepartment> getConcernDepartmentList();

	public ConcernDepartment saveConcernDepartment(ConcernDepartment concernDepartment);

	public List<ConcernDepartment> saveConcernDepartmentList(List<ConcernDepartment> concernDepartmentList);

	public Optional<ConcernDepartment> getConcernDepartmentById(String id);

	public void deleteConcernDepartmentById(String id);

}
