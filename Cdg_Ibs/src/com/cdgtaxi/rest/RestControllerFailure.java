package com.cdgtaxi.rest;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.BusinessHelper;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.LoggerUtil;
	
	public class RestControllerFailure extends CommonWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(RestControllerFailure.class);
	protected BusinessHelper businessHelper;
	
	public RestControllerFailure() {
		businessHelper = (BusinessHelper)SpringUtil.getBean("businessHelper");
	}
	public void redirect() {
	
		PmtbTopUpReq req = null;
		
		try {
			String reqNo = decrypt(getParams());
			req = this.businessHelper.getPrepaidBusiness().getPrepaidCreditTopUpRequest(new BigDecimal(reqNo));
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_RED_DOT_FAIL);
			this.businessHelper.getPaymentBusiness().update(req);
		} catch (Exception e){
			LoggerUtil.printStackTrace(logger, e);
			logger.debug("Failed to generate report for request " + req.getReqNo());
		}
		
	}
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	
}


