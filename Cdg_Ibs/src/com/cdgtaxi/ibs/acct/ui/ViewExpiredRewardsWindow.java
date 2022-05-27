package com.cdgtaxi.ibs.acct.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewExpiredRewardsWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewStatusWindow.class);
	
	private ArrayList<HashMap<String, Object>> expiredRewards = null;
	
	@SuppressWarnings("unchecked")
	public ViewExpiredRewardsWindow() throws InterruptedException{
		logger.info("ViewExpiredRewardsWindow()");
		Map<String, Object> map = Executions.getCurrent().getArg();
		expiredRewards = (ArrayList<HashMap<String, Object>>)map.get("expiredRewards");
		this.setWidth("80%");
		this.setMinwidth(100);
		this.setSizable(false);
		this.setClosable(true);
	}
	public void init(){
		logger.info("init()");
		Rows rewardsBalance = (Rows)this.getFellow("rewardsBalance");
		for(HashMap<String, Object> rewardsMap : expiredRewards){
			
			LrtbRewardAccount rewardAccount = (LrtbRewardAccount) rewardsMap.get("rewardAcct");
			Integer reward = (Integer)rewardsMap.get("reward") + (Integer)rewardsMap.get("initial") + (Integer)rewardsMap.get("adjustment");;
			Integer redeem = (Integer)rewardsMap.get("redeem");
			
			logger.debug("expirepts" + reward + " " + redeem);
			
			Row rewardBalance = new Row();
			rewardBalance.appendChild(new Label(""+(rewardsBalance.getChildren().size()+1)));
			rewardBalance.appendChild(new Label(new DecimalFormat(StringUtil.DECIMAL_IN_INTEGER_FORMAT).format(reward)));
			rewardBalance.appendChild(new Label(new DecimalFormat(StringUtil.DECIMAL_IN_INTEGER_FORMAT).format(-redeem)));
			rewardBalance.appendChild(new Label(new DecimalFormat(StringUtil.DECIMAL_IN_INTEGER_FORMAT).format(reward+redeem)));
			rewardBalance.appendChild(new Label(DateUtil.convertDateToStr(rewardAccount.getCutOffDt(), DateUtil.GLOBAL_DATE_FORMAT)));
			rewardBalance.appendChild(new Label(DateUtil.convertDateToStr(rewardAccount.getExpireDt(), DateUtil.GLOBAL_DATE_FORMAT)));
			rewardBalance.appendChild(new Label(DateUtil.convertDateToStr(rewardAccount.getIbsExpireDt(), DateUtil.GLOBAL_DATE_FORMAT)));
			rewardsBalance.appendChild(rewardBalance);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}
