package com.webapp.ims.uam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.repository.TblUsersServiceRepository;
import com.webapp.ims.uam.service.TblUsersService;

@Service
@Transactional
public class TblUsersServiceImpl implements TblUsersService 
{

	@Autowired
	TblUsersServiceRepository tblUsersServiceRepository;
	
	@Autowired
	EntityManager em;
	

	@Override
	public TblUsers getLoginByuserName(String userName) {
		
		Object obj =null;
		for(TblUsers loginuser: tblUsersServiceRepository.findAll())
		{
			if (loginuser.getUserName().equalsIgnoreCase(userName))
			{
				obj = loginuser;
			}
		}
		return (TblUsers) obj;
	}

	@Override
	public List<TblUsers> getEnableUserName(String loginUser) {
		// TODO Auto-generated method stub
		return tblUsersServiceRepository.getEnableUsersDetail(loginUser);
	}
	
	@Override
	public List<TblUsers> getDisableUserName(String loginUser) {
		// TODO Auto-generated method stub
		return tblUsersServiceRepository.getDisableUsersDetail(loginUser);
	}

	@Override
	public List<TblUsers> getLockUserName(String loginUser) {
		// TODO Auto-generated method stub
		return tblUsersServiceRepository.getLockUsersDetail(loginUser);
	}
	
	@Override
	public int executeNativeQuery(String taskquery) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(taskquery).executeUpdate();
	}

	@Override
	public TblUsers getLoginUsrDetail(String userName) 
	{
		// TODO Auto-generated method stub
		ArrayList<String>usrstatus = new ArrayList<String>();
		usrstatus.add("A");
		usrstatus.add("D");
		usrstatus.add("T");
		return tblUsersServiceRepository.getLoginUsrDetail(userName,usrstatus);
		
	}

	@Override
	public Optional<TblUsers> getLoginIdById(String userId) {
		// TODO Auto-generated method stub
		return tblUsersServiceRepository.findById(userId);
	}
	
	@Override
	public char[] getRandomPassword() {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$%&";
		String values = Capital_chars + Small_chars + numbers + symbols;
		Random rndm_method = new Random();
		char[] password = new char[8];
		for (int i = 0; i < 8; i++) {
			password[i] = values.charAt(rndm_method.nextInt(values.length()));
		}
		return password;
	}
	
	// gopal
	@Override
	public List<TblUsers> saveLoginList(List<TblUsers> login) {
		return tblUsersServiceRepository.saveAll(login);
	}

	@Override
	public void insertWithQuery(TblUsers login) {
		String str = "INSERT INTO  oims_dev.loc.\"Users\" (\"User_Name\", \"Password\",  \"Department_Name\",  \"Status\",\"User_First_Name\", \"User_Middle_Name\", \"User_Last_Name\",\"Captcha\",\"Created_By\") "
				+ "VALUES(?1, ?2, ?3, ?4,'','','','','')";
		em.createNativeQuery(str).setParameter(1, login.getUserName()).setParameter(2, login.getPassword())
				.setParameter(3, login.getDepartment()).setParameter(4, "active").executeUpdate();
	}
	

}
