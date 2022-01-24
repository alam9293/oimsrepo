package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.IncentiveDetails;

@Repository
public interface IncentiveDetailsRepository extends JpaRepository<IncentiveDetails, Integer> {
	@Query("Select incdtl.id from IncentiveDetails incdtl where incdtl.isfapcId=:applicantId  ")
	public String getIsfIdByApplId(@Param(value = "applicantId") String applicantId);

	public IncentiveDetails getIncentiveisfById(String isfId);

	public IncentiveDetails getIncentiveByisfapcId(String appId);

	@Query(value = "select isf.id from IncentiveDetails isf where isf.status IN ('Submit')")
	public List<IncentiveDetails> getAllIncentiveDetailsBystatus();
	
	@Query(value = "select isf.id from IncentiveDetails isf where isf.dis_status IN ('Submit')")
	public List<IncentiveDetails> getAllDisIncentiveDetailsBystatus();

	@Modifying
	@Query("Update IncentiveDetails isf set isf.isfSgstComment=:isfSgstComment, isf.isfScstComment=:isfScstComment, isf.isffwComment=:isffwComment,isf.isfBplComment=:isfBplComment, isf.isfElecdutyComment=:isfElecdutyComment, isf.isfMandiComment=:isfMandiComment, isf.isfStampComment=:isfStampComment, isf.isfloanComment=:isfloanComment, isf.isfdisComment=:isfdisComment,isf.isfelepodownComment=:isfelepodownComment, isf.isfinfaComment=:isfinfaComment,isf.isfcapisaComment=:isfcapisaComment, isf.isfinfComment=:isfinfComment,isf.isfepfscComment=:isfepfscComment, isf.isfepfComment=:isfepfComment, isf.isfepfaddComment=:isfepfaddComment,isf.isfStampremComment=:isfStampremComment, isf.isfStampscstComment=:isfStampscstComment, isf.isfdifferabilComment=:isfdifferabilComment, isf.isfcapisComment=:isfcapisComment where isf.isfapcId=:isfapcId ")
	public void updateComments(@Param(value = "isfapcId") String apcId,
			@Param(value = "isfSgstComment") String isfSgstComment,
			@Param(value = "isfScstComment") String isfScstComment,
			@Param(value = "isffwComment") String isffwComment,
			@Param(value = "isfBplComment") String isfBplComment,
			@Param(value = "isfElecdutyComment") String isfElecdutyComment,
			@Param(value = "isfMandiComment") String isfMandiComment,

			@Param(value = "isfStampComment") String isfStampComment,
			@Param(value = "isfloanComment") String isfloanComment,
			@Param(value = "isfdisComment") String isfdisComment,
			@Param(value = "isfelepodownComment") String isfelepodownComment,
			@Param(value = "isfinfaComment") String isfinfaComment,
			@Param(value = "isfcapisaComment") String isfcapisaComment,
			@Param(value = "isfinfComment") String isfinfComment,
			@Param(value = "isfepfscComment") String isfepfscComment,
			@Param(value = "isfcapisComment") String isfcapisComment,
			@Param(value = "isfepfComment") String isfepfComment,
			@Param(value = "isfepfaddComment") String isfepfaddComment,
			@Param(value = "isfStampremComment") String isfStampremComment,
			@Param(value = "isfStampscstComment") String isfStampscstComment,
			@Param(value = "isfdifferabilComment") String isfdifferabilComment);

	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfMandiComment=:isfMandiComment where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept0(@Param(value = "isfapcId") String apcId,
			
			@Param(value = "isfMandiComment") String isfMandiComment);
	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfElecdutyComment=:isfElecdutyComment , isf.isfelepodownComment=:isfelepodownComment  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept25(@Param(value = "isfapcId") String apcId, @Param(value = "isfElecdutyComment") String isfElecdutyComment, @Param(value = "isfelepodownComment") String isfelepodownComment);

	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfStampComment=:isfStampComment ,isf.isfStampremComment=:isfStampremComment , isf.isfStampscstComment=:isfStampscstComment  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept5(@Param(value = "isfapcId") String apcId, @Param(value = "isfStampComment") String isfStampComment, @Param(value = "isfStampremComment") String isfStampremComment, @Param(value = "isfStampscstComment") String isfStampscstComment);
	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfepfComment=:isfepfComment ,isf.isfepfaddComment=:isfepfaddComment , isf.isfepfscComment=:isfepfscComment, isf.isfdifferabilComment=:isfdifferabilComment  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept4(@Param(value = "isfapcId") String apcId, @Param(value = "isfepfComment") String isfepfComment, @Param(value = "isfepfaddComment") String isfepfaddComment, 
			@Param(value = "isfepfscComment") String isfepfscComment, @Param(value = "isfdifferabilComment") String isfdifferabilComment);

	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfSgstComment=:isfSgstComment ,isf.isfScstComment=:isfScstComment , isf.isffwComment=:isffwComment, isf.isfBplComment=:isfBplComment, isf.isfcapisaComment=:isfcapisaComment, isf.isfcapisComment=:isfcapisComment, isf.isfinfaComment=:isfinfaComment , isf.isfloanComment=:isfloanComment ,isf.isfinfComment=:isfinfComment ,isf.isfdisComment=:isfdisComment  where isf.isfapcId=:isfapcId ")

	public void updateCommentsConDept2(@Param(value = "isfapcId") String apcId, @Param(value = "isfSgstComment") String isfSgstComment, @Param(value = "isfScstComment") String isfScstComment, @Param(value = "isffwComment")  String isffwComment, @Param(value = "isfBplComment") String isfBplComment, @Param(value = "isfcapisaComment") String isfcapisaComment, @Param(value = "isfcapisComment") String isfcapisComment, @Param(value = "isfinfaComment") String isfinfaComment,
		@Param(value = "isfloanComment") String isfloanComment, @Param(value = "isfinfComment") String isfinfComment, @Param(value = "isfdisComment") String isfdisComment);

//For DIS
	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfMandiCommentdis=:isfMandiCommentdis where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDeptDis0(@Param(value = "isfapcId") String apcId,
			
			@Param(value = "isfMandiCommentdis") String isfMandiComment);
	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfElecdutyCommentdis=:isfElecdutyCommentdis , isf.isfelepodownCommentdis=:isfelepodownCommentdis  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDeptDis25(@Param(value = "isfapcId") String apcId, @Param(value = "isfElecdutyCommentdis") String isfElecdutyComment, @Param(value = "isfelepodownCommentdis") String isfelepodownComment);

	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfStampCommentdis=:isfStampCommentdis ,isf.isfStampremCommentdis=:isfStampremCommentdis , isf.isfStampscstCommentdis=:isfStampscstCommentdis  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDeptDis5(@Param(value = "isfapcId") String apcId, @Param(value = "isfStampCommentdis") String isfStampComment, @Param(value = "isfStampremCommentdis") String isfStampremComment, @Param(value = "isfStampscstCommentdis") String isfStampscstComment);
	
	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfepfCommentdis=:isfepfCommentdis ,isf.isfepfaddCommentdis=:isfepfaddCommentdis , isf.isfepfscCommentdis=:isfepfscCommentdis, isf.isfdifferabilCommentdis=:isfdifferabilCommentdis  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDeptDis4(@Param(value = "isfapcId") String apcId, @Param(value = "isfepfCommentdis") String isfepfComment, @Param(value = "isfepfaddCommentdis") String isfepfaddComment, 
			@Param(value = "isfepfscCommentdis") String isfepfscComment, @Param(value = "isfdifferabilCommentdis") String isfdifferabilComment);

	@Modifying
	@Query("Update IncentiveDetails isf set  isf.isfSgstCommentdis=:isfSgstCommentdis ,isf.isfScstCommentdis=:isfScstCommentdis , isf.isffwCommentdis=:isffwCommentdis, isf.isfBplCommentdis=:isfBplCommentdis, isf.isfcapisaCommentdis=:isfcapisaCommentdis, isf.isfcapisCommentdis=:isfcapisCommentdis, isf.isfinfaCommentdis=:isfinfaCommentdis , isf.isfloanCommentdis=:isfloanCommentdis ,isf.isfinfCommentdis=:isfinfCommentdis ,isf.isfdisCommentdis=:isfdisCommentdis  where isf.isfapcId=:isfapcId ")

	public void updateCommentsConDeptDis2(@Param(value = "isfapcId") String apcId, @Param(value = "isfSgstCommentdis") String isfSgstComment, @Param(value = "isfScstCommentdis") String isfScstComment, @Param(value = "isffwCommentdis")  String isffwComment, @Param(value = "isfBplCommentdis") String isfBplComment, @Param(value = "isfcapisaCommentdis") String isfcapisaComment, @Param(value = "isfcapisCommentdis") String isfcapisComment, @Param(value = "isfinfaCommentdis") String isfinfaComment,
		@Param(value = "isfloanCommentdis") String isfloanComment, @Param(value = "isfinfCommentdis") String isfinfComment, @Param(value = "isfdisCommentdis") String isfdisComment);

}
