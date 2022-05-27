package com.cdgtaxi.ibs.reward.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.billing.ui.SearchByAccountWindow;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.LrtbGiftStock;
import com.cdgtaxi.ibs.common.model.LrtbRewardTxn;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ViewRedemptionHistoryWindow extends SearchByAccountWindow {
	private static final Logger logger = Logger.getLogger(ViewRedemptionHistoryWindow.class);

	private AmtbAccount account;

	private Listbox resultLB;

	@Override
	public void onCreate(CreateEvent ce) throws Exception{
		super.onCreate(ce);

		resultLB = (Listbox) getFellow("resultList");
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	@Override
	public void onSelectAccountName() throws InterruptedException {
		if(accountNameComboBox.getSelectedItem() != null){
			account = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			accountNoIntBox.setText(account.getCustNo());

			displayProcessing();

			Integer accountNo = account.getAccountNo();
			account = businessHelper.getRewardBusiness().getAccount(accountNo);
			if(account == null){
				Messagebox.show("The corporate/applicant account has either no rewards account or all rewards accounts are expired.", "View Redemption History", Messagebox.OK, Messagebox.ERROR);
				this.reset();
				return;
			}
			
			List<LrtbGiftStock> redemptionHistory =
				businessHelper.getRewardBusiness().getRedemptionHistory(accountNo);

			
			
			// Result List
			resultLB.getItems().clear();
			if (resultLB.getListfoot() != null) {
				resultLB.getListfoot().detach();// .removeChild(resultLB.getListfoot());
			}

			if (redemptionHistory.isEmpty()) {
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(
						resultLB.getListhead().getChildren().size()));
			} else {
				for (LrtbGiftStock redemption : redemptionHistory) {
					Listitem item = new Listitem();

					item.appendChild(new Listcell(redemption.getLrtbGiftItem().getItemName()));
					//item.appendChild(new Listcell(StringUtil.numberToString(-redemption.getTxnQty(), StringUtil.GLOBAL_INTEGER_FORMAT)));
					item.appendChild(newListcell(-redemption.getTxnQty()));
					item.appendChild(newListcell(redemption.getPreviousPts()));
					Integer pointsRedeemed = 0;
					for (LrtbRewardTxn txn : redemption.getLrtbRewardTxns()) {
						pointsRedeemed += -txn.getRewardsPts();
					}
					//item.appendChild(new Listcell(StringUtil.numberToString(pointsRedeemed, StringUtil.GLOBAL_INTEGER_FORMAT)));
					item.appendChild(newListcell(pointsRedeemed));
					item.appendChild(newListcell(redemption.getBalancePts()));
					//item.appendChild(new Listcell(DateUtil.convertDateToStr(redemption.getTxnDt(), DateUtil.GLOBAL_DATE_FORMAT)));
					item.appendChild(newListcell(redemption.getLrtbRewardTxns().iterator().next().getAmtbContactPerson().getMainContactName()));
					item.appendChild(newListcell(redemption.getTxnDt()));
					item.appendChild(newListcell(redemption.getCreatedBy()));

					resultLB.appendChild(item);
				}
			}

			resultLB.setVisible(true);
		} else {
			

			resultLB.setVisible(false);
		}
	}

	@Override
	public void reset() throws InterruptedException {
		accountNoIntBox.setRawValue(null);
		accountNameComboBox.setRawValue(null);
		accountNameComboBox.setSelectedItem(null);
		onSelectAccountName();

		resultLB.getItems().clear();
	}
}
