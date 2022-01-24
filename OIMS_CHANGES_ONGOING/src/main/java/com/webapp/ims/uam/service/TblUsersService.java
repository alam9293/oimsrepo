package com.webapp.ims.uam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webapp.ims.uam.model.TblUsers;

@Service
public interface TblUsersService {

	
	public TblUsers getLoginByuserName(String userName);

	List<TblUsers> getEnableUserName(String loginUser);
	
	List<TblUsers> getDisableUserName(String loginUser);
	
	List<TblUsers> getLockUserName(String loginUser);

	public int executeNativeQuery(String taskquery);

	public TblUsers getLoginUsrDetail(String userName);

	public Optional<TblUsers>  getLoginIdById(String userId);
	
	public List<TblUsers> saveLoginList(List<TblUsers> login);
	
	public char[] getRandomPassword();
	
	public void insertWithQuery(TblUsers login);
	

}