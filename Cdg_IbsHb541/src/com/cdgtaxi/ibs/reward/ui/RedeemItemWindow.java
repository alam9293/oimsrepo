package com.cdgtaxi.ibs.reward.ui;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.ConcurrencyFailureException;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InsufficientGiftStockException;
import com.cdgtaxi.ibs.common.exception.InsufficientRewardPointsException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class RedeemItemWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(RedeemItemWindow.class);

	//private final AmtbAccount account;
	private AmtbAccount account;
	private AmtbContactPerson contactPerson;
	private LrtbGiftItem giftItem;

	private Combobox usePointsFromCB;
	private Intbox redeemQtyIB;
	private Decimalbox serialNoStartDMB, serialNoEndDMB;

	@SuppressWarnings("unchecked")
	public RedeemItemWindow() {
		Map params = Executions.getCurrent().getArg();

		Integer accountNo = (Integer) params.get("accountNo");
		Integer contactPersonNo = (Integer) params.get("contactPersonNo");
		Integer giftItemNo = (Integer) params.get("giftItemNo");

		logger.info("Account No = " + accountNo);
		logger.info("Gift Item No = " + giftItemNo);

		account = businessHelper.getRewardBusiness().getAccount(accountNo);
		for (AmtbContactPerson contact : account.getAmtbContactPersons()) {
			if (contact.getContactPersonNo().equals(contactPersonNo)) {
				contactPerson = contact;
				break;
			}
		}
		giftItem = businessHelper.getRewardBusiness().getItem(giftItemNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	public void onCreate() {
		// Account Information
		Label accountNoLabel = (Label) getFellow("accountNoLabel");
		Label accountStatusLabel = (Label) getFellow("accountStatusLabel");
		Label accountNameLabel = (Label) getFellow("accountNameLabel");
		Label contactPersonLabel = (Label) getFellow("contactPersonLabel");
		Label previousPointsLabel = (Label) getFellow("previousPointsLabel");
		Label currentPointsLabel = (Label) getFellow("currentPointsLabel");

		accountNoLabel.setValue(account.getCustNo());

		AmtbAcctStatus status = account.getAmtbAcctStatuses().iterator().next();
		accountStatusLabel.setValue(NonConfigurableConstants.ACCOUNT_STATUS.get(status.getAcctStatus()));

		accountNameLabel.setValue(account.getAccountName());
		String name = contactPerson.getMainContactName();
		if(contactPerson.getSubContactName()!= null && contactPerson.getSubContactName().length() > 0)
			name += " / " + contactPerson.getSubContactName();
		contactPersonLabel.setValue(name);

		Integer currPoints = businessHelper.getRewardBusiness().calcCurrPoints(account);
		currentPointsLabel.setValue(StringUtil.numberToString(
				currPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

		Integer prevPoints = businessHelper.getRewardBusiness().calcPrevPoints(account);
		previousPointsLabel.setValue(StringUtil.numberToString(
				prevPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

		Set<Entry<String, String>> values = NonConfigurableConstants.REWARDS_REDEEM_FROM.entrySet();
		for(Entry<String, String> value : values){
			Comboitem item = new Comboitem(value.getValue());
			item.setValue(value.getKey());
			usePointsFromCB.appendChild(item);
		}
		if(!usePointsFromCB.getChildren().isEmpty()) usePointsFromCB.setSelectedIndex(0);
		
		// Item Information
		Label categoryLabel = (Label) getFellow("categoryLabel");
		Label itemCodeLabel = (Label) getFellow("itemCodeLabel");
		Label itemNameLabel = (Label) getFellow("itemNameLabel");
		Label itemPriceLabel = (Label) getFellow("itemPriceLabel");
		Label itemStockLabel = (Label) getFellow("itemStockLabel");
		Label itemPointsLabel = (Label) getFellow("itemPointsLabel");
		Image itemImage = (Image) getFellow("itemImage");
		Label itemNoImageLabel = (Label) getFellow("itemNoImageLabel");

		categoryLabel.setValue(giftItem.getLrtbGiftCategory().getCategoryName());
		itemCodeLabel.setValue(giftItem.getItemCode());
		itemNameLabel.setValue(giftItem.getItemName());
		itemPriceLabel.setValue(StringUtil.numberToString(
				giftItem.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		itemStockLabel.setValue(StringUtil.numberToString(
				giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));
		itemPointsLabel.setValue(StringUtil.numberToString(
				giftItem.getPoints(), StringUtil.GLOBAL_INTEGER_FORMAT));
		try {
			if (giftItem.getImage() != null) {
				InputStream is = giftItem.getImage().getBinaryStream();
				itemImage.setContent(new AImage(null, is));
				itemNoImageLabel.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void redeemItem() throws InterruptedException {
		Integer redeemQty = redeemQtyIB.getValue();
		String usePointsFrom = (String) usePointsFromCB.getSelectedItem().getValue();
		BigDecimal serialNoStart = serialNoStartDMB.getValue();
		BigDecimal serialNoEnd = serialNoEndDMB.getValue();
		
		try {
			if(serialNoStart != null && serialNoEnd==null)
				throw new WrongValueException(serialNoEndDMB, "Serial No End cannot be null once there is value entered for Serial No Start");
			else if(serialNoEnd != null && serialNoStart==null)
				throw new WrongValueException(serialNoStartDMB, "Serial No Start cannot be null once there is value entered for Serial No End");
			else if(serialNoEnd != null && serialNoStart!= null){
				serialNoStart = serialNoStart.setScale(0, BigDecimal.ROUND_HALF_UP);
				serialNoEnd = serialNoEnd.setScale(0, BigDecimal.ROUND_HALF_UP);
				
				if(serialNoEnd.compareTo(serialNoStart) < 0)
					throw new WrongValueException(serialNoEndDMB, "Serial No End cannot be less than Serial No Start");
				
				Integer serialNoQty = (serialNoEnd.subtract(serialNoStart)).intValue() + 1;
				if(serialNoQty.intValue() != redeemQty.intValue())
					throw new WrongValueException(redeemQtyIB, "Redeem Qty is having a different qty amount as the entered Serial No Range");
			}
			
			Integer pointsUsed = businessHelper.getRewardBusiness().redeemItem(
					account, giftItem, redeemQty, usePointsFrom, contactPerson, serialNoStart, serialNoEnd, getUserLoginIdAndDomain());
			Messagebox.show(
					redeemQty + " items of " + giftItem.getItemName() +
					" have been successfully redeemed by " + account.getAccountName() +
					" for " + pointsUsed + " points.", "Redeem Gift Item",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} 
		catch (WrongValueException wve){
			throw wve;
		}
		catch (InsufficientGiftStockException e) {
			Messagebox.show(e.getMessage(), "Insufficient Stock", Messagebox.OK, Messagebox.ERROR);
			redeemQtyIB.focus();
		} 
		catch (InsufficientRewardPointsException e) {
			Messagebox.show(e.getMessage(), "Insufficient Points", Messagebox.OK, Messagebox.ERROR);
			redeemQtyIB.focus();
		}
		catch(ConcurrencyFailureException e) {
			Messagebox.show("There is concurrent user either redeem same gift item or redeem item with same account. Account's reward point and available stock count of item will be refreshed. Please try to do redemption again.");
			refresh();
		}
		catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.toString());
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		giftItem = businessHelper.getRewardBusiness().getItem(giftItem.getGiftItemNo());

		Label itemStockLabel = (Label) getFellow("itemStockLabel");
		itemStockLabel.setValue(StringUtil.numberToString(
				giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));

		account = businessHelper.getRewardBusiness().getAccount(account.getAccountNo());
		Label previousPointsLabel = (Label) getFellow("previousPointsLabel");
		Label currentPointsLabel = (Label) getFellow("currentPointsLabel");
		Integer currPoints = businessHelper.getRewardBusiness().calcCurrPoints(account);
		currentPointsLabel.setValue(StringUtil.numberToString(
				currPoints, StringUtil.GLOBAL_INTEGER_FORMAT));

		Integer prevPoints = businessHelper.getRewardBusiness().calcPrevPoints(account);
		previousPointsLabel.setValue(StringUtil.numberToString(
				prevPoints, StringUtil.GLOBAL_INTEGER_FORMAT));
		
	}
}
