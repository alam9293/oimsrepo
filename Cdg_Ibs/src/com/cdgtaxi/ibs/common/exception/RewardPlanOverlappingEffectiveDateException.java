package com.cdgtaxi.ibs.common.exception;

import java.util.Date;

import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class RewardPlanOverlappingEffectiveDateException extends Exception {
//	Date lastEffectiveToDate;
//
//	public RewardPlanOverlappingEffectiveDateException(Date lastEffectiveToDate) {
//		this.lastEffectiveToDate = lastEffectiveToDate;
//	}
//
//	@Override
//	public String getMessage() {
//		return "The specified Effective Date range overlaps with another that is effective to " +
//		DateUtil.convertDateToStr(lastEffectiveToDate, DateUtil.GLOBAL_DATE_FORMAT);
//	}
	
	@Override
	public String getMessage() {
		return "The specified Effective Date range overlaps with another detail";
	}
}
