package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webapp.ims.model.BusinessEntityDetails;

@Service
public interface BusinessEntityDetailsService {

	public List<BusinessEntityDetails> getAllBusinessEntities();

	public BusinessEntityDetails saveBusinessEntity(BusinessEntityDetails businessEntity);

	public Optional<BusinessEntityDetails> getBusinessEntityById(String id);

	public void deleteBusinessEntityById(Integer id);

	public void deleteBusinessEntity(BusinessEntityDetails businessEntity);

	public BusinessEntityDetails updateBusinessEntity(BusinessEntityDetails businessEntityDetails);

	public Iterable<BusinessEntityDetails> findAll();

	public BusinessEntityDetails getBusinessEntityByapplicantDetailId(String appId);

	public BusinessEntityDetails getBusinDetById(String besiId);

	// public BusinessEntityDetailsFood findByunitid(String unitid);

	public void commonDetails(String appId, HttpSession session, Model model);

}
