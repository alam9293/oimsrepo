package com.cdgtaxi.ibs.common.exception;

import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;

@SuppressWarnings("serial")
public class RewardPlanInEffectException extends Exception {
	LrtbRewardMaster plan;

	public RewardPlanInEffectException(LrtbRewardMaster plan) {
		this.plan = plan;
	}

	@Override
	public String getMessage() {
		return plan.getRewardPlanName() + " has already taken effect.";
	}
}
