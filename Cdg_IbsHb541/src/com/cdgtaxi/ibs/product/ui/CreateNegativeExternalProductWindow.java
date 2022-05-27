package com.cdgtaxi.ibs.product.ui;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Decimalbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwNegativeList;

public class CreateNegativeExternalProductWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateNegativeExternalProductWindow.class);
	
	private Decimalbox cardNoDMB;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	@Override
	public void refresh() throws InterruptedException {
	}
	
	public void create() throws InterruptedException{
		try{
			String cardNo = null;
			if(cardNoDMB.getValue()!=null) cardNo = cardNoDMB.getText();
			
			if(cardNo==null)
				throw new WrongValueException(cardNoDMB, "Card No is Mandatory!");
			else if(cardNo.length()<=10)
				throw new WrongValueException(cardNoDMB, "Invalid card no length!");
			
			if(this.businessHelper.getProductTypeBusiness()
					.getExternalProductType(cardNo.substring(0, 6), cardNo.substring(6, 10))
					== null)
				throw new WrongValueException(cardNoDMB, "No external card product type found from entered card no!");
			
			if(this.businessHelper.getGenericBusiness().get(AsvwNegativeList.class, cardNo)!=null)
				throw new WrongValueException(cardNoDMB, "Card no existed!");
			
			if(this.businessHelper.getEnquiryBusiness().isExternalProductRequestExist(cardNo))
				throw new WrongValueException(cardNoDMB, "There is a pending request existed!");
			
			int response = Messagebox.show("Confirm to add external card?", 
					"Create Negative External Product", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			if(response == Messagebox.CANCEL) return;
			
			API.createNegativeProduct(cardNo, NonConfigurableConstants.EXTERNAL_PRODUCT_STATUS_NEW, getUserLoginIdAndDomain());
			
			//Show result
			Messagebox.show("Create negative external product request inserted.", "Create Negative External Product", Messagebox.OK, Messagebox.INFORMATION);
			
			this.back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
}
