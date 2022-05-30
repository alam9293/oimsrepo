package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;

public class ManageProdSubscWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageProdSubscWindow.class);
	private String custNo;
	@SuppressWarnings("unchecked")
	public ManageProdSubscWindow() throws InterruptedException{
		logger.info("ManageProdSubscWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Manage Product Subscription", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}
		Map<String, Object> accountDetails = this.businessHelper.getAccountBusiness().searchAccount(custNo);
		((Label)this.getFellow("custNo")).setValue((String)accountDetails.get("custNo"));
		((Label)this.getFellow("acctName")).setValue((String)accountDetails.get("acctName"));
		((Label)this.getFellow("nameOnCard")).setValue((String)accountDetails.get("nameOnCard"));
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		while(rows.getChildren().size()!=1){
			rows.getChildren().remove(0);
		}
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		for(Map<String, String> prodSubscription : prodSubscriptions){
			Row row = new Row();
			row.appendChild(new Label(prodSubscription.get("prodType")==null ? "-" : prodSubscription.get("prodType")));
			row.appendChild(new Label(prodSubscription.get("prodDiscount")==null ? "-" : prodSubscription.get("prodDiscount")));
			row.appendChild(new Label(prodSubscription.get("rewards")==null ? "-" : prodSubscription.get("rewards")));
			row.appendChild(new Label(prodSubscription.get("subscribeFee")==null ? "-" : prodSubscription.get("subscribeFee")));
			row.appendChild(new Label(prodSubscription.get("issuanceFee")==null ? "-" : prodSubscription.get("issuanceFee")));
			row.appendChild(new Checkbox());
			row.setValue(prodSubscription.get("prodTypeId"));
			rows.insertBefore(row, rows.getLastChild());
		}
	}
	public void checkAll(boolean checked){
		logger.info("checkAll(boolean checked)");
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		for(Object row : rows.getChildren()){
			if(!row.equals(rows.getLastChild())){
				Checkbox selectCheck = (Checkbox)((Row)row).getLastChild();
				selectCheck.setChecked(checked);
			}
		}
	}
	public void subscribe() throws InterruptedException{
		logger.info("subscribe()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		this.forward(Uri.ADD_PROD_SUBSC, params, this.getParent());
	}
	public void unsubscribe() throws InterruptedException, CniiInterfaceException{
		logger.info("unsubscribe()");
		if(Messagebox.show("Confirm unsubscribe?", "Manage Product Subscription", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			Rows rows = (Rows)this.getFellow("prodSubscriptions");
			List<String> unsubscribes = new ArrayList<String>();
			for(Object row : rows.getChildren()){
				if(!row.equals(rows.getLastChild())){
					Checkbox selectCheck = (Checkbox)((Row)row).getLastChild();
					if(selectCheck.isChecked()){
						unsubscribes.add((String)((Row)row).getValue());
					}
				}
			}
			if(unsubscribes.isEmpty()){
				Messagebox.show("No selected subscription", "Manage Product Subscription", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			this.businessHelper.getAccountBusiness().unsubscribeProductTypeApproval(custNo, unsubscribes);
			Messagebox.show("Subscription sent for approval", "Manage Product Subscription", Messagebox.OK, Messagebox.INFORMATION);
			this.refresh();
		}
	}
}