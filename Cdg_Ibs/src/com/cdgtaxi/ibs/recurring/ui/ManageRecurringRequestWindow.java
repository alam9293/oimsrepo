package com.cdgtaxi.ibs.recurring.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.forms.SearchRecurringRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class ManageRecurringRequestWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageRecurringRequestWindow.class);

	private Intbox requestNoLNB;
	private Listbox statusLB, resultLB;
	private Datebox requestDateFromDB, requestDateToDB;

	// After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);

		// populate status listbox
		statusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for (String key : NonConfigurableConstants.RECURRING_REQUEST_STATUS.keySet()) {
			statusLB.appendChild(new Listitem(NonConfigurableConstants.RECURRING_REQUEST_STATUS.get(key), key));
		}
	}

	public void searchRequest() throws InterruptedException {
		logger.info("");

		
		SearchRecurringRequestForm form = new SearchRecurringRequestForm();
		form.requestNo = requestNoLNB.getValue();
		form.status = ((String) statusLB.getSelectedItem().getValue()).length() == 0 ? null
				: (String) statusLB.getSelectedItem().getValue();

		if (requestDateFromDB.getValue() != null && requestDateToDB.getValue() == null)
			requestDateToDB.setValue(requestDateFromDB.getValue());
		else if (requestDateToDB.getValue() != null && requestDateFromDB.getValue() == null)
			requestDateFromDB.setValue(requestDateToDB.getValue());
		
		if (requestDateFromDB.getValue() != null && requestDateToDB.getValue() != null) {
			// Do validation check
			if (requestDateFromDB.getValue().compareTo(requestDateToDB.getValue()) > 0)
				throw new WrongValueException(requestDateFromDB,
						"Request Date From cannot be later than Request Date To");
			else {
				form.requestDateFrom = DateUtil.convertUtilDateToSqlDate(requestDateFromDB.getValue());
				form.requestDateTo = DateUtil.convertUtilDateToSqlDate(requestDateToDB.getValue());
			}
		}
		
		try 
		{
			this.displayProcessing();

			resultLB.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultLB.getItems().clear();

			List<IttbRecurringReq> requests = this.businessHelper.getAdminBusiness()
					.searchIttbRecurringRequest(form);
			if (requests.size() > 0) {

				if (requests.size() > ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert",
							Messagebox.OK, Messagebox.INFORMATION);

				for (IttbRecurringReq request : requests) {

					Listitem item = new Listitem();
					item.setValue(request);
					item.appendChild(newListcell(request.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
					
					String requestType = "Auto";
					if(request.getRecurringAutoManual().equals("M"))
						requestType = "Manual";
					
					item.appendChild(newListcell(requestType));
					item.appendChild(newListcell(NonConfigurableConstants.RECURRING_REQUEST_STATUS.get(request.getStatus())));
					item.appendChild(newListcell(request.getCreatedDt()));
					item.appendChild(newListcell(request.getFileName() == null ? "" : request.getFileName()));

					resultLB.appendChild(item);
				}

				if (requests.size() > ConfigurableConstants.getMaxQueryResult())
					resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());

				if (resultLB.getListfoot() != null)
					resultLB.removeChild(resultLB.getListfoot());
			} else {
				if (resultLB.getListfoot() == null) {
					resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(4));
				}
			}

			resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultLB.setPageSize(10);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void viewRequest() throws InterruptedException {
		logger.info("");

		try {
			// Retrieve selected value
			Listitem selectedItem = resultLB.getSelectedItem();
			IttbRecurringReq request = (IttbRecurringReq) selectedItem.getValue();

			HashMap map = new HashMap();
			map.put("requestNo", request.getReqNo());
			this.forward(Uri.VIEW_RECURRING_REQUEST, map);
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void clear() {
		requestNoLNB.setText("");
		statusLB.setSelectedIndex(0);

		resultLB.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultLB.getItems().clear();
		if (resultLB.getListfoot() == null) {
			resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(4));
		}
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
		
		requestDateFromDB.setText("");
		requestDateToDB.setText("");;
	}

	@Override
	public void refresh() throws InterruptedException {
		this.searchRequest();
	}
}
