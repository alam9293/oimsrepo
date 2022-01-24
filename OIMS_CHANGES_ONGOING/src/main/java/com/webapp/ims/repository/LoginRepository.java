package com.webapp.ims.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webapp.ims.login.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login,Integer> {

	public Login getLoginNameByuserNameAndDepartment(String userName, String department);
	public Login getLoginIdById(String userId);
	
	public Login findById(String userid);
	
	public List<Login> findByDepartmentIn(List<String> department);
	
	@Query("Select users.password, users.department from Login users where users.userName=:userName and users.department=:department")
	public Login findpasswordByuserName(String userName, String department);
	
}
