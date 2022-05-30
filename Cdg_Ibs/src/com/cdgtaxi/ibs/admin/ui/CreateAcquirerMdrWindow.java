package com.cdgtaxi.ibs.admin.ui;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreateAcquirerMdrWindow extends CommonWindow implements AfterCompose {

	private Listbox acquirerTypeListBox;
	private Decimalbox rateDecimalBox;
	private Datebox effDateFromDateBox;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
	
		List<MstbAcquirer> acquirerTypes = this.businessHelper.getAdminBusiness().getAcquirer();
		for(MstbAcquirer acquirerType : acquirerTypes){
			Listitem item = new Listitem();
			item.setValue(acquirerType);
			item.setLabel(acquirerType.getName());
			acquirerTypeListBox.appendChild(item);
		}
		acquirerTypeListBox.setSelectedIndex(0);
	}
	
	public void create() throws InterruptedException{
		try{
			this.displayProcessing();
			
			MstbAcquirerMdr acquirerMdr = new MstbAcquirerMdr();
			acquirerMdr.setMstbAcquirer((MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue());
			acquirerMdr.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effDateFromDateBox.getValue()));
			acquirerMdr.setRate(rateDecimalBox.getValue());
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(acquirerMdr)){
				Messagebox.show("Duplicate record found.", "Create Acquirer MDR", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				this.businessHelper.getGenericBusiness().save(acquirerMdr, getUserLoginIdAndDomain());
				Messagebox.show("New Acquirer MDR created.", "Create Acquirer MDR", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}
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
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
