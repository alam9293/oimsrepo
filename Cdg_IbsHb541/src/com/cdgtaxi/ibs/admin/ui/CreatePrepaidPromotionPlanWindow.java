package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Decimalbox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class CreatePrepaidPromotionPlanWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(CreatePrepaidPromotionPlanWindow.class);
	private CapsTextbox promoCodeTextBox;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Decimalbox cashplusDeimalbox;
	private Textbox remarksTextBox;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
	}
	
	public void createPlan() throws InterruptedException{
		try{
			this.displayProcessing();
			
			String code = promoCodeTextBox.getValue();
			Date effDateFrom = effDateFromDateBox.getValue();
			Date effDateTo = effDateToDateBox.getValue();
			BigDecimal cashplus = cashplusDeimalbox.getValue();
			String remarks = remarksTextBox.getValue();
			
			
			if(effDateFrom.after(effDateTo)){
				throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			}
		
			MstbPromotionCashPlus newPromotion = new MstbPromotionCashPlus();
			newPromotion.setPromoCode(code);
			newPromotion.setCashplus(cashplus);
			newPromotion.setRemarks(remarks);

			newPromotion.setEffectiveDtFrom(DateUtil.convertDateTo0000Hours(effDateFrom));
			newPromotion.setEffectiveDtTo(DateUtil.convertDateTo2359Hours(effDateTo));
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateCashPlusPromoCode(code)){
				throw new WrongValueException(promoCodeTextBox, "Promo code has been used.");
			}
			
			this.businessHelper.getGenericBusiness().save(newPromotion, getUserLoginIdAndDomain());
	
			//Show result
			Messagebox.show("New prepaid promotion plan created.", "Create Prepaid Promotion Plan", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); 
			//TO DO A REFRESH
			LoggerUtil.printStackTrace(logger, e);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
