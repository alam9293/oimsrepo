package com.cdgtaxi.ibs.acct.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewRewardsWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewRewardsWindow.class);
	private String custNo, acctStatus;
	private ArrayList<HashMap<String, Object>> expiredRewards = new ArrayList<HashMap<String, Object>>();
	Map<Date, String> expiryDates = new HashMap<Date, String>();
	
	@SuppressWarnings("unchecked")
	public ViewRewardsWindow() throws InterruptedException{
		logger.info("ViewRewardsWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "View Rewards", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "View Rewards", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> accountDetails = this.businessHelper.getAccountBusiness().searchAccount(custNo);
		((Label)this.getFellow("custNo")).setValue((String)accountDetails.get("custNo"));
		((Label)this.getFellow("acctStatus")).setValue((String)accountDetails.get("acctStatus"));
		((Label)this.getFellow("acctType")).setValue((String)accountDetails.get("acctType"));
		((Label)this.getFellow("createdDate")).setValue((String)accountDetails.get("createdDate"));
		ArrayList<HashMap<String, Object>> sortedRewards = this.businessHelper.getAccountBusiness().getRewardsSummary(custNo);
		Comparator<HashMap<String, Object>> cutoffDateComparator =new Comparator<HashMap<String, Object>>(){
			public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
				
				LrtbRewardAccount acct1 = (LrtbRewardAccount)o1.get("rewardAcct");
				LrtbRewardAccount acct2 = (LrtbRewardAccount)o2.get("rewardAcct");
				
				return acct1.getCutOffDt().compareTo(acct2.getCutOffDt());
			}
		};
		Collections.sort(sortedRewards, cutoffDateComparator);
		
		Rows rewardsBalance = (Rows)this.getFellow("rewardsBalance");
		if(!sortedRewards.isEmpty()){
	
			Integer totalReward=0, totalRedeem=0;
			Date currentDate = new Date();
			for(HashMap<String, Object> rewardsMap:sortedRewards){
				
				LrtbRewardAccount rewardAccount = (LrtbRewardAccount) rewardsMap.get("rewardAcct");
				Integer reward = (Integer)rewardsMap.get("reward") + (Integer)rewardsMap.get("initial") + (Integer)rewardsMap.get("adjustment");
				Integer redeem = (Integer)rewardsMap.get("redeem");
				if(currentDate.before(rewardAccount.getExpireDt())){
					totalReward += reward;
					totalRedeem += redeem;
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
				else {
					expiredRewards.add(rewardsMap);
				}
			}
			
			Label totalRewardLabel = (Label)this.getFellow("awardedPts");
			totalRewardLabel.setValue(new DecimalFormat(StringUtil.DECIMAL_IN_INTEGER_FORMAT).format(totalReward));
			Label totalRedeemLabel = (Label)this.getFellow("redeemPts");
			totalRedeemLabel.setValue(new DecimalFormat(StringUtil.DECIMAL_IN_INTEGER_FORMAT).format(0 - totalRedeem));
			Label totalBalanceLabel = (Label)this.getFellow("remainPts");
			totalBalanceLabel.setValue(new DecimalFormat(StringUtil.DECIMAL_IN_INTEGER_FORMAT).format(totalReward + totalRedeem));
			
		}
	}
	public void save() throws InterruptedException{
		Decimalbox initialPtBox = (Decimalbox)this.getFellow("initialRewards");
		if(this.businessHelper.getAccountBusiness().updateInitialRewardsPoints(custNo, initialPtBox.getValue(), getUserLoginIdAndDomain())){
			Messagebox.show("Rewards saved!", "Edit Rewards", Messagebox.OK, Messagebox.INFORMATION);
		}else{
			Messagebox.show("Unable to save rewards. Please try again later!", "Edit Rewards", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void viewExpiredBalances() throws InterruptedException {
		logger.info("viewExpiredBalances()");
		Map<String, Object> args = new HashMap<String,Object>();
		args.put("expiredRewards", expiredRewards);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_EXPIRED_REWARDS, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
}
