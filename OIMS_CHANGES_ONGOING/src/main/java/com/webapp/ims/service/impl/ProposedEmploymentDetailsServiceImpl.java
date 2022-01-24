package com.webapp.ims.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.webapp.ims.model.DeptProposedEmploymentDetails;
import com.webapp.ims.model.DeptProprietorDetails;
import com.webapp.ims.model.DeptSkilledUnSkilledEmployemnt;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.DeptSkillUnSkilledRepository;
import com.webapp.ims.repository.ProposedEmploymentDetailsRepository;
import com.webapp.ims.service.ProposedEmploymentDetailsService;

@Service
@Transactional
public class ProposedEmploymentDetailsServiceImpl implements ProposedEmploymentDetailsService {

	@Autowired        
	ProposedEmploymentDetailsRepository proposedEmploymentDetailsRepository;
	@Autowired
	DeptProposedEmploymentDetailsRepository deptProposedEmploymentDetailsRepository;
	
	@Override
	public ProposedEmploymentDetails saveProposedEmploymentDetails(
			ProposedEmploymentDetails proposedEmploymentDetails) {
		
		

		return saveProposedEmploymentDetailsExtra(proposedEmploymentDetails);
	}
//  vinay start
	
	
	public ProposedEmploymentDetails saveProposedEmploymentDetailsExtra(ProposedEmploymentDetails proposedEmploymentDetails) {
		
	
		

			
				final DeptProposedEmploymentDetails deptProposedEmploymentDetails = new DeptProposedEmploymentDetails();
				BeanUtils.copyProperties(proposedEmploymentDetails, deptProposedEmploymentDetails);
				
				deptProposedEmploymentDetails.setSkilledUnSkilledEmployemnt(new ArrayList<>());
				
				if(proposedEmploymentDetails!=null && !CollectionUtils.isEmpty(proposedEmploymentDetails.getSkilledUnSkilledEmployemnt())) {
					proposedEmploymentDetails.getSkilledUnSkilledEmployemnt().parallelStream().forEach(source->{
						DeptSkilledUnSkilledEmployemnt target=new DeptSkilledUnSkilledEmployemnt();
						BeanUtils.copyProperties(source, target);
						deptProposedEmploymentDetails.getSkilledUnSkilledEmployemnt().add(target);						
					});
					
				}
				
				deptProposedEmploymentDetailsRepository.save(deptProposedEmploymentDetails);
				
				return proposedEmploymentDetailsRepository.save(proposedEmploymentDetails);
		
		
	
		
	}
	
	
	
	
	
	// ends vinay 
	@Override
	public ProposedEmploymentDetails updateProposedEmploymentDetails(
			ProposedEmploymentDetails proposedEmploymentDetails) {
		proposedEmploymentDetailsRepository.delete(proposedEmploymentDetails);
		return proposedEmploymentDetailsRepository.save(proposedEmploymentDetails);
	}

	@Override
	public Optional<ProposedEmploymentDetails> getProposedEmploymentById(String id) {

		return proposedEmploymentDetailsRepository.findById(id);
	}

	@Override
	@Query("from ProposedEmploymentDetails where appId = :id ")
	public ProposedEmploymentDetails getProposedEmploymentDetailsByappId(String appId) {
		return proposedEmploymentDetailsRepository.getProposedEmploymentDetailsByappId(appId);

	}

	@Override
	@Query("from ProposedEmploymentDetails where Id = :propId ")
	public ProposedEmploymentDetails getPropEmpById(String propId) {

		return proposedEmploymentDetailsRepository.getPropEmpById(propId);
	}

	@Override
	public String getPropEmplIdByapplId(String appId) {
		return proposedEmploymentDetailsRepository.getPropEmplIdByapplId(appId);
	}

}
