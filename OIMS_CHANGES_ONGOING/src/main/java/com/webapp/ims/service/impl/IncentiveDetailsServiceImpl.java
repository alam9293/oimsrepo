package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.service.IncentiveDetailsService;

@Service
@Transactional
public class IncentiveDetailsServiceImpl implements IncentiveDetailsService {

	@Autowired
	IncentiveDetailsRepository incentiveDetailsRepository;
	@Autowired
	DeptIncentiveDetailsRepository deptIncRepository;

	@Override
	public List<IncentiveDetails> getAllIncentiveDetails() {
		return incentiveDetailsRepository.findAll();
	}

	@Override
	public IncentiveDetails saveIncentive(IncentiveDetails incentiveDetails) {
		DeptIncentiveDetails obj = new DeptIncentiveDetails();
		BeanUtils.copyProperties(incentiveDetails, obj);
		deptIncRepository.save(obj);

		return incentiveDetailsRepository.save(incentiveDetails);
	}

	@Override
	public Optional<IncentiveDetails> getIncentiveById(Integer id) {
		return incentiveDetailsRepository.findById(id);
	}

	@Override
	public void deleteIncentiveDetailsById(Integer id) {
		incentiveDetailsRepository.deleteById(id);
	}

	@Override
	public void deleteIncentive(IncentiveDetails incentiveDetails) {
		incentiveDetailsRepository.delete(incentiveDetails);

	}

	@Override
	@Query(" from IncentiveDetails isfId = :isfId ")
	public IncentiveDetails getIncentiveisfById(String isfId) {

		return incentiveDetailsRepository.getIncentiveisfById(isfId);
	}

	@Override
	public IncentiveDetails updateIncentiveDetails(IncentiveDetails incentiveDetails) {
		DeptIncentiveDetails obj = new DeptIncentiveDetails();
		BeanUtils.copyProperties(incentiveDetails, obj);
		deptIncRepository.save(obj);

		return incentiveDetailsRepository.save(incentiveDetails);
	}

	@Override
	@Query("from IncentiveDetails where ISF_APC_Id = :appId ")
	public IncentiveDetails getIncentiveByisfapcId(String appId) {
		return incentiveDetailsRepository.getIncentiveByisfapcId(appId);
	}

	@Override
	public String getIsfIdByApplId(String applicantId) {
		return incentiveDetailsRepository.getIsfIdByApplId(applicantId);
	}

	@Override
	public void updateComments(String apcId, String isfSgstComment, String isfScstComment, String isffwComment,
			String isfBplComment, String isfElecdutyComment, String isfMandiComment, String isfStampComment,
			String isfStampremComment, String isfStampscstComment, String isfepfComment, String isfepfaddComment,
			String isfepfscComment, String isfcapisComment, String isfcapisaComment, String isfinfComment,
			String isfinfaComment, String isfloanComment, String isfdisComment, String isfelepodownComment,
			String isfdifferabilComment) {

		deptIncRepository.updateComments(apcId, isfSgstComment, isfScstComment, isffwComment, isfBplComment,
				isfElecdutyComment, isfMandiComment, isfStampComment,
				isfelepodownComment, isfdisComment, isfloanComment, isfinfaComment, isfcapisaComment, isfinfComment,
				isfepfscComment, isfcapisComment, isfepfComment, isfepfaddComment, isfStampremComment,
				isfStampscstComment,  isfdifferabilComment);
		incentiveDetailsRepository.updateComments(apcId, isfSgstComment, isfScstComment, isffwComment, isfBplComment,
				isfElecdutyComment, isfMandiComment, isfStampComment, isfloanComment, isfdisComment,
				isfelepodownComment, isfinfaComment, isfcapisaComment, isfinfComment,
				isfepfscComment, isfcapisComment, isfepfComment, isfStampremComment,
				isfStampscstComment, isfepfaddComment, isfdifferabilComment);
	}

	@Override
	public void updateCommentsConDept0(String apcId, String isfMandiComment) {
		deptIncRepository.updateCommentsConDept0(apcId, isfMandiComment);
		incentiveDetailsRepository.updateCommentsConDept0(apcId, isfMandiComment);
		
	}

	@Override
	public void updateCommentsConDept25(String apcId, String isfElecdutyComment, String isfelepodownComment) {
		deptIncRepository.updateCommentsConDept25(apcId, isfElecdutyComment, isfelepodownComment);
		incentiveDetailsRepository.updateCommentsConDept25(apcId, isfElecdutyComment, isfelepodownComment);
		
	}

	@Override
	public void updateCommentsConDept5(String apcId, String isfStampComment, String isfStampremComment,
			String isfStampscstComment) {
		deptIncRepository.updateCommentsConDept5(apcId, isfStampComment, isfStampremComment, isfStampscstComment);
		incentiveDetailsRepository.updateCommentsConDept5(apcId, isfStampComment, isfStampremComment, isfStampscstComment);
		
		
	}

	@Override
	public void updateCommentsConDept4(String apcId, String isfepfComment, String isfepfaddComment,
			String isfepfscComment, String isfdifferabilComment) {
		deptIncRepository.updateCommentsConDept4(apcId, isfepfComment, isfepfaddComment, isfepfscComment,isfdifferabilComment );
		incentiveDetailsRepository.updateCommentsConDept4(apcId, isfepfComment, isfepfaddComment, isfepfscComment, isfdifferabilComment);
		
		
	}

	@Override
	public void updateCommentsConDept2(String apcId, String isfSgstComment, String isfScstComment, String isffwComment,
			String isfBplComment, String isfcapisaComment, String isfcapisComment, String isfinfaComment,
			String isfinfComment, String isfloanComment, String isfdisComment) {
		deptIncRepository.updateCommentsConDept2(apcId, isfSgstComment, isfScstComment, isffwComment,isfBplComment, isfcapisaComment, isfcapisComment,
				isfinfaComment,isfloanComment, isfinfComment, isfdisComment);
		incentiveDetailsRepository.updateCommentsConDept2(apcId, isfSgstComment, isfScstComment, isffwComment,isfBplComment, isfcapisaComment, isfcapisComment,
				isfinfaComment,isfloanComment, isfinfComment, isfdisComment);
		
		
	}

	@Override
	public void updateCommentsConDeptDis0(String apcId, String isfMandiComment) {
		//deptIncRepository.updateCommentsConDeptDis0(apcId, isfMandiComment);
		incentiveDetailsRepository.updateCommentsConDeptDis0(apcId, isfMandiComment);
		
	}

	@Override
	public void updateCommentsConDeptDis25(String apcId, String isfElecdutyComment, String isfelepodownComment) {
		//deptIncRepository.updateCommentsConDeptDis25(apcId, isfElecdutyComment, isfelepodownComment);
		incentiveDetailsRepository.updateCommentsConDeptDis25(apcId, isfElecdutyComment, isfelepodownComment);
		
	}

	@Override
	public void updateCommentsConDeptDis5(String apcId, String isfStampComment, String isfStampremComment,
			String isfStampscstComment) {
		//deptIncRepository.updateCommentsConDeptDis5(apcId, isfStampComment, isfStampremComment, isfStampscstComment);
		incentiveDetailsRepository.updateCommentsConDeptDis5(apcId, isfStampComment, isfStampremComment, isfStampscstComment);
		
		
	}

	@Override
	public void updateCommentsConDeptDis4(String apcId, String isfepfComment, String isfepfaddComment,
			String isfepfscComment, String isfdifferabilComment) {
		//deptIncRepository.updateCommentsConDeptDis4(apcId, isfepfComment, isfepfaddComment, isfepfscComment,isfdifferabilComment );
		incentiveDetailsRepository.updateCommentsConDeptDis4(apcId, isfepfComment, isfepfaddComment, isfepfscComment, isfdifferabilComment);
		
		
	}

	@Override
	public void updateCommentsConDeptDis2(String apcId, String isfSgstComment, String isfScstComment, String isffwComment,
			String isfBplComment, String isfcapisaComment, String isfcapisComment, String isfinfaComment,
			String isfinfComment, String isfloanComment, String isfdisComment) {
		//deptIncRepository.updateCommentsConDeptDis2(apcId, isfSgstComment, isfScstComment, isffwComment,isfBplComment, isfcapisaComment, isfcapisComment,
				//isfinfaComment,isfloanComment, isfinfComment, isfdisComment);
		incentiveDetailsRepository.updateCommentsConDeptDis2(apcId, isfSgstComment, isfScstComment, isffwComment,isfBplComment, isfcapisaComment, isfcapisComment,
				isfinfaComment,isfloanComment, isfinfComment, isfdisComment);
		
		
	}

}
