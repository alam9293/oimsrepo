package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ViewSalesPersonWindow extends CommonWindow{
	private static final Logger logger = Logger.getLogger(ViewSalesPersonWindow.class);

	private final MstbSalesperson salesPerson;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Label nameLbl,emailLbl,effectiveDateFromDBLbl,effectiveDateToDBLbl;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public ViewSalesPersonWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer salesPersonNo =  (Integer) params.get("salesPersonNo");
		logger.info("Sales Person No = " + salesPersonNo);
		salesPerson = businessHelper.getAdminBusiness().getSalesPerson(salesPersonNo);
	}

	public void onCreate() {
		Components.wireVariables(this, this);
		nameLbl.setValue(salesPerson.getName());
		emailLbl.setValue(salesPerson.getEmail());
		effectiveDateFromDBLbl.setValue(DateUtil.convertDateToStr(salesPerson.getEffectiveDtFrom(), "dd/MM/yyyy"));
		effectiveDateToDBLbl.setValue(DateUtil.convertDateToStr(salesPerson.getEffectiveDtTo(), "dd/MM/yyyy"));

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

}
