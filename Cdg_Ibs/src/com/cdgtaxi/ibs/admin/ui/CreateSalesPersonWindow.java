
package com.cdgtaxi.ibs.admin.ui;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreateSalesPersonWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateSalesPersonWindow.class);

	private Textbox nameTB;
	private Textbox emailTB;
	private Datebox effectiveDateFromDB;
	private Datebox effectiveDateToDB;

	public void onCreate() {
		nameTB = (Textbox) getFellow("nameTB");
		emailTB = (Textbox) getFellow("emailTB");
		effectiveDateFromDB = (Datebox) getFellow("effectiveDateFromDB");
		effectiveDateToDB = (Datebox) getFellow("effectiveDateToDB");

		nameTB.focus();
	}

	public void create() throws InterruptedException {
		if(effectiveDateToDB.getValue()!=null)
			if (!effectiveDateFromDB.getValue().before(effectiveDateToDB.getValue())) {
				Messagebox.show("Effective Date From must be before Effective Date To",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

		MstbSalesperson salesPerson = new MstbSalesperson();
		salesPerson.setName(nameTB.getValue());
		salesPerson.setEmail(emailTB.getValue());
		salesPerson.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(
				effectiveDateFromDB.getValue()));
		salesPerson.setEffectiveDtTo(DateUtil.convertDateToTimestamp(
				effectiveDateToDB.getValue()));

		try {
			businessHelper.getAdminBusiness().createSalesPerson(salesPerson, getUserLoginIdAndDomain());
			MasterSetup.getSalespersonManager().refresh();
			Messagebox.show("Sales Person has been successfully created", "Create Sales Person",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
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

	@Override
	public void refresh() throws InterruptedException {
	}
}
