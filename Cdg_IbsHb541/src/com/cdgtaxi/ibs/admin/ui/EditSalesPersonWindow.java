package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.constraint.EqualOrLaterThanNowConstraint;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;

@SuppressWarnings("serial")
public class EditSalesPersonWindow extends CommonWindow{
	private static final Logger logger = Logger.getLogger(EditSalesPersonWindow.class);

	private final MstbSalesperson salesPerson;
	private Textbox nameTB;
	private Textbox emailTB;
	private Datebox effectiveDateFromDB;
	private Datebox effectiveDateToDB;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public EditSalesPersonWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer salesPersonNo =  (Integer) params.get("salesPersonNo");
		logger.info("Sales Person No = " + salesPersonNo);
		salesPerson = businessHelper.getAdminBusiness().getSalesPerson(salesPersonNo);
	}

	public void onCreate() {
		Components.wireVariables(this, this);
	/*	nameTB = (Textbox) getFellow("nameTB");
		emailTB = (Textbox) getFellow("emailTB");
		effectiveDateFromDB = (Datebox) getFellow("effectiveDateFromDB");
		effectiveDateToDB = (Datebox) getFellow("effectiveDateToDB");
*/
		nameTB.setValue(salesPerson.getName());
		emailTB.setValue(salesPerson.getEmail());
		effectiveDateFromDB.setValue(salesPerson.getEffectiveDtFrom());
		if(salesPerson.getEffectiveDtFrom().compareTo(DateUtil.getCurrentTimestamp()) < 0)
			effectiveDateFromDB.setDisabled(true);
		else
			effectiveDateFromDB.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		effectiveDateToDB.setValue(salesPerson.getEffectiveDtTo());
		effectiveDateToDB.setConstraint(new EqualOrLaterThanNowConstraint());
		
		if(salesPerson.getCreatedDt()!=null)createdByLabel.setValue(salesPerson.getCreatedBy());
		else createdByLabel.setValue("-");
		if(salesPerson.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(salesPerson.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(salesPerson.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(salesPerson.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(salesPerson.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(salesPerson.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(salesPerson.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(salesPerson.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(salesPerson.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(salesPerson.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	public void update() throws InterruptedException {
		if(effectiveDateToDB.getValue()!=null)
			if (!effectiveDateFromDB.getValue().before(effectiveDateToDB.getValue())) {
				Messagebox.show("Effective Date From must be before Effective Date To",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		
		salesPerson.setName(nameTB.getValue());
		salesPerson.setEmail(emailTB.getValue());
		if(!effectiveDateFromDB.isDisabled())
			salesPerson.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDateFromDB.getValue()));
			salesPerson.setEffectiveDtTo(DateUtil.convertDateToTimestamp(effectiveDateToDB.getValue()));

		try {
			businessHelper.getAdminBusiness().updateSalesPerson(salesPerson, getUserLoginIdAndDomain());
			MasterSetup.getSalespersonManager().refresh();
			Messagebox.show(
					"Sales Person has been successfully saved.", "Save Sales Person",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		}  
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateNameError e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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
