package com.webapp.ims.uam.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.uam.model.TblUsers;

@Repository
public interface TblUsersServiceRepository extends JpaRepository<TblUsers, String>
{
	@Query(" from TblUsers tbluser  where userName!=:loginUser and userName not IN ('System','Admin') and status='A' order by userName")
	public List<TblUsers> getEnableUsersDetail(String loginUser);
	
	@Query(" from TblUsers tbluser  where userName!=:loginUser and userName not IN ('System','Admin') and status='D' and usrActiveStatus ='X' order by userName")
	public List<TblUsers> getDisableUsersDetail(String loginUser);

	@Query(" from TblUsers tbluser  where userName=:userName and status IN (:usrstatus) order by userName")
	public TblUsers getLoginUsrDetail(String userName, ArrayList<String> usrstatus);

	@Query(" from TblUsers tbluser  where userName!=:loginUser and userName not IN ('System','Admin') and lockedStatus='9' and status='A' and usrActiveStatus ='A' order by userName")
	public List<TblUsers> getLockUsersDetail(String loginUser);
	

}