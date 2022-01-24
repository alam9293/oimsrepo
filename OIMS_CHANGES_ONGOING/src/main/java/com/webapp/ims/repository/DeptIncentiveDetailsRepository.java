package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DeptIncentiveDetails;

@Repository
public interface DeptIncentiveDetailsRepository extends JpaRepository<DeptIncentiveDetails, Integer> {
	@Query("Select incdtl.id from DeptIncentiveDetails incdtl where incdtl.isfapcId=:applicantId  ")
	public String getIsfIdByApplId(@Param(value = "applicantId") String applicantId);

	public DeptIncentiveDetails getIncentiveisfById(String isfId);

	public DeptIncentiveDetails getIncentiveByisfapcId(String appId);
	
	public DeptIncentiveDetails findByIsfapcId(String appId);
	
	public DeptIncentiveDetails findById(String isfId);
	
	@Modifying
	@Query(value="Update DeptIncentiveDetails isf set isf.sgstRemark=:sgstRemark, isf.scstRemark=:scstRemark, isf.stampDutyExemptRemark=:stampDutyExemptRemark, isf.stampDutyReimRemark=:stampDutyReimRemark, isf.capIntSubRemark=:capIntSubRemark, isf.infraIntSubRemark=:infraIntSubRemark, isf.loanIntSubRemark=:loanIntSubRemark, isf.inputTaxRemark=:inputTaxRemark, isf.elecDutyCapRemark=:elecDutyCapRemark, isf.elecDutyDrawnRemark=:elecDutyDrawnRemark, isf.mandiFeeRemark=:mandiFeeRemark, isf.diffAbleWorkRemark=:diffAbleWorkRemark where isf.isfapcId=:isfapcId" )
	public void updateRemark(@Param(value = "sgstRemark")String sgstRemark, @Param(value = "scstRemark")String scstRemark,@Param(value = "stampDutyExemptRemark")String stampDutyExemptRemark,@Param(value = "stampDutyReimRemark")String stampDutyReimRemark,@Param(value = "capIntSubRemark")String capIntSubRemark,@Param(value = "infraIntSubRemark")String infraIntSubRemark,@Param(value = "loanIntSubRemark")String loanIntSubRemark,@Param(value = "inputTaxRemark")String inputTaxRemark,@Param(value = "elecDutyCapRemark")String elecDutyCapRemark,@Param(value = "elecDutyDrawnRemark")String elecDutyDrawnRemark,@Param(value = "mandiFeeRemark")String mandiFeeRemark,@Param(value = "diffAbleWorkRemark")String diffAbleWorkRemark, @Param(value = "isfapcId")String isfapcId) ;

	@Modifying
	@Query("Update DeptIncentiveDetails isf set isf.isfSgstComment=:isfSgstComment, isf.isfScstComment=:isfScstComment, isf.isffwComment=:isffwComment,isf.isfBplComment=:isfBplComment, isf.isfElecdutyComment=:isfElecdutyComment, isf.isfMandiComment=:isfMandiComment, isf.isfStampComment=:isfStampComment, isf.isfloanComment=:isfloanComment, isf.isfdisComment=:isfdisComment,isf.isfelepodownComment=:isfelepodownComment, isf.isfinfaComment=:isfinfaComment,isf.isfcapisaComment=:isfcapisaComment, isf.isfinfComment=:isfinfComment,isf.isfepfscComment=:isfepfscComment, isf.isfepfComment=:isfepfComment, isf.isfepfaddComment=:isfepfaddComment,isf.isfStampremComment=:isfStampremComment, isf.isfStampscstComment=:isfStampscstComment, isf.isfdifferabilComment=:isfdifferabilComment where isf.isfapcId=:isfapcId ")
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
			@Param(value = "isfepfComment") String isfcapisComment,
			@Param(value = "isfepfComment") String isfepfComment,
			@Param(value = "isfepfaddComment") String isfepfaddComment,
			@Param(value = "isfStampremComment") String isfStampremComment,			
			@Param(value = "isfStampscstComment") String isfStampscstComment,				
			@Param(value = "isfdifferabilComment") String isfdifferabilComment);
	
	@Modifying
	@Query("Update DeptIncentiveDetails isf set  isf.isfMandiComment=:isfMandiComment where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept0(@Param(value = "isfapcId") String apcId, @Param(value = "isfMandiComment") String isfMandiComment);
			
	@Modifying
	@Query("Update DeptIncentiveDetails isf set  isf.isfElecdutyComment=:isfElecdutyComment , isf.isfelepodownComment=:isfelepodownComment  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept25(@Param(value = "isfapcId") String apcId, @Param(value = "isfElecdutyComment") String isfElecdutyComment, @Param(value = "isfelepodownComment") String isfelepodownComment);

	
	@Modifying
	@Query("Update DeptIncentiveDetails isf set  isf.isfStampComment=:isfStampComment ,isf.isfStampremComment=:isfStampremComment , isf.isfStampscstComment=:isfStampscstComment  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept5(@Param(value = "isfapcId") String apcId, @Param(value = "isfStampComment") String isfStampComment, @Param(value = "isfStampremComment") String isfStampremComment, @Param(value = "isfStampscstComment") String isfStampscstComment);

	
@Modifying
	@Query("Update DeptIncentiveDetails isf set  isf.isfepfComment=:isfepfComment ,isf.isfepfaddComment=:isfepfaddComment , isf.isfepfscComment=:isfepfscComment, isf.isfdifferabilComment=:isfdifferabilComment  where isf.isfapcId=:isfapcId ")
	public void updateCommentsConDept4(@Param(value = "isfapcId") String apcId, @Param(value = "isfepfComment") String isfepfComment, @Param(value = "isfepfaddComment") String isfepfaddComment, 
			@Param(value = "isfepfscComment") String isfepfscComment, @Param(value = "isfdifferabilComment") String isfdifferabilComment);

	
@Modifying
@Query("Update DeptIncentiveDetails isf set  isf.isfSgstComment=:isfSgstComment ,isf.isfScstComment=:isfScstComment , isf.isffwComment=:isffwComment, isf.isfBplComment=:isfBplComment, isf.isfcapisaComment=:isfcapisaComment, isf.isfcapisComment=:isfcapisComment, isf.isfinfaComment=:isfinfaComment , isf.isfloanComment=:isfloanComment ,isf.isfinfComment=:isfinfComment ,isf.isfdisComment=:isfdisComment  where isf.isfapcId=:isfapcId ")

public void updateCommentsConDept2(@Param(value = "isfapcId") String apcId, @Param(value = "isfSgstComment") String isfSgstComment, @Param(value = "isfScstComment") String isfScstComment, @Param(value = "isffwComment")  String isffwComment, @Param(value = "isfBplComment") String isfBplComment, @Param(value = "isfcapisaComment") String isfcapisaComment, @Param(value = "isfcapisComment") String isfcapisComment, @Param(value = "isfinfaComment") String isfinfaComment, @Param(value = "isfloanComment") String isfloanComment, @Param(value = "isfinfComment") String isfinfComment, @Param(value = "isfdisComment") String isfdisComment);



}
