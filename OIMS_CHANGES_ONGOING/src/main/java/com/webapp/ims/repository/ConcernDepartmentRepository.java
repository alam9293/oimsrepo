package com.webapp.ims.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ConcernDepartment;
@Repository
public interface ConcernDepartmentRepository extends CrudRepository<ConcernDepartment, String> {

}
