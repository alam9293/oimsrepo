package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.webapp.ims.model.IncentiveDetails;

@Service
public interface IncentiveDetailsService {

	public List<IncentiveDetails> getAllIncentiveDetails();

	public IncentiveDetails saveIncentive(IncentiveDetails incentiveDetails);

	public IncentiveDetails updateIncentiveDetails(IncentiveDetails incentiveDetails);

	public Optional<IncentiveDetails> getIncentiveById(Integer id);

	public void deleteIncentiveDetailsById(Integer id);

	public void deleteIncentive(IncentiveDetails incentiveDetails);

	public IncentiveDetails getIncentiveisfById(String isfId);

	public IncentiveDetails getIncentiveByisfapcId(String appId);

	public String getIsfIdByApplId(String applicantId);

	public void updateComments(String apcId, String isfSgstComment, String isfScstComment, String isffwComment,
			String isfBplComment, String isfElecdutyComment, String isfMandiComment, String isfStampComment,
			String isfStampremComment, String isfStampscstComment, String isfepfComment, String isfepfaddComment,
			String isfepfscComment, String isfcapisComment, String isfcapisaComment, String isfinfComment,
			String isfinfaComment, String isfloanComment, String isfdisComment, String isfelepodownComment,
			String isfdifferabilComment);

	public void updateCommentsConDept0(String apcId, String isfMandiComment);

	public void updateCommentsConDept25(String apcId, String isfElecdutyComment, String isfelepodownComment);

	public void updateCommentsConDept5(String apcId, String isfStampComment, String isfStampremComment,
			String isfStampscstComment);

	public void updateCommentsConDept4(String apcId, String isfepfComment, String isfepfaddComment,
			String isfepfscComment, String isfdifferabilComment);

	public void updateCommentsConDept2(String apcId, String isfSgstComment, String isfScstComment,
			String isffwComment, String isfBplComment, String isfcapisaComment, String isfcapisComment,
			String isfinfaComment, String isfinfComment, String isfloanComment, String isfdisComment);
	
	//For Dis
	
	public void updateCommentsConDeptDis0(String apcId, String isfMandiComment);

	public void updateCommentsConDeptDis25(String apcId, String isfElecdutyComment, String isfelepodownComment);

	public void updateCommentsConDeptDis5(String apcId, String isfStampComment, String isfStampremComment,
			String isfStampscstComment);

	public void updateCommentsConDeptDis4(String apcId, String isfepfComment, String isfepfaddComment,
			String isfepfscComment, String isfdifferabilComment);

	public void updateCommentsConDeptDis2(String apcId, String isfSgstComment, String isfScstComment,
			String isffwComment, String isfBplComment, String isfcapisaComment, String isfcapisComment,
			String isfinfaComment, String isfinfComment, String isfloanComment, String isfdisComment);
}
