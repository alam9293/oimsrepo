package com.cdgtaxi.ibs.common.exception;

import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class InsufficientRewardPointsException extends Exception {
	Integer pointsNeeded, pointsAvailable;

	public InsufficientRewardPointsException(Integer pointsNeeded, Integer pointsAvailable) {
		this.pointsNeeded = pointsNeeded;
		this.pointsAvailable = pointsAvailable;
	}

	@Override
	public String getMessage() {
		return "Insufficient Points. " + StringUtil.numberToString(pointsNeeded, StringUtil.GLOBAL_INTEGER_FORMAT) +
		" point(s) required but only " + StringUtil.numberToString(pointsAvailable, StringUtil.GLOBAL_INTEGER_FORMAT) +
		" point(s) are available.";

	}
}
