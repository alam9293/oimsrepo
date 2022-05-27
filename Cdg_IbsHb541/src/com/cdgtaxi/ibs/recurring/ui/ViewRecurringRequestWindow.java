package com.cdgtaxi.ibs.recurring.ui;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbRecurringDtl;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class ViewRecurringRequestWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewRecurringRequestWindow.class);
	private Label requestNoLbl, statusLbl, outgoingFileNameLbl, requestTypeLbl, billGenRequestNoLbl, invoiceDateLbl,
			invoiceNoFromLbl, invoiceNoToLbl, accountNosLbl, chargingDateLbl, createdByLabel,
			createdDateLabel, createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel,
			lastUpdatedTimeLabel;
	private Button uploadBtn, deleteBtn;
	private IttbRecurringReq request;
	private Listbox invoiceLB;

	@SuppressWarnings("rawtypes")
	public ViewRecurringRequestWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer requestNo = (Integer) params.get("requestNo");
		request = this.businessHelper.getAdminBusiness().searchIttbRecurringRequest(requestNo);
	}

	// After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);

		requestNoLbl.setValue(request.getReqNo().toString());
		statusLbl.setValue(NonConfigurableConstants.RECURRING_REQUEST_STATUS.get(request.getStatus()));
		
		
		String recurringType = "Auto";
		if(request.getRecurringAutoManual().contentEquals("M"))
			recurringType = "Manual";
		requestTypeLbl.setValue(recurringType);
		
		String reqNo = "-";
		
		if(request.getBmtbBillGenReq() != null && request.getBmtbBillGenReq().getReqNo() != null)
			reqNo = (request.getBmtbBillGenReq().getReqNo()).toString();
		
		billGenRequestNoLbl.setValue(reqNo);
		outgoingFileNameLbl.setValue(request.getFileName() != null ? request.getFileName() : "-");
	
		invoiceNoFromLbl.setValue(request.getInvoiceNoFrom() != null ? request.getInvoiceNoFrom().toString()
				: "-");
		invoiceNoToLbl.setValue(request.getInvoiceNoTo() != null ? request.getInvoiceNoTo().toString()
				: "-");
		invoiceDateLbl.setValue(request.getInvoiceDate() != null ? DateUtil.convertDateToStr(
				request.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT) : "-");
		accountNosLbl.setValue(request.getAccountNos() != null ? request.getAccountNos().toString() : "-");
		chargingDateLbl.setValue(request.getChargingDate() != null ? DateUtil.convertDateToStr(
				request.getChargingDate(), DateUtil.GLOBAL_DATE_FORMAT) : "-");
		
		
		
		// Created By Section
		createdByLabel.setValue(request.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertTimestampToStr(request.getCreatedDt(),
				DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertTimestampToStr(request.getCreatedDt(),
				DateUtil.GLOBAL_TIME_FORMAT));

		// Last Updated Section
		if (request.getUpdatedDt() == null) {
			lastUpdatedByLabel.setValue("-");
			lastUpdatedDateLabel.setValue("-");
			lastUpdatedTimeLabel.setValue("-");
		} else {
			lastUpdatedByLabel.setValue(request.getUpdatedBy());
			lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(request.getUpdatedDt(),
					DateUtil.GLOBAL_DATE_FORMAT));
			lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(request.getUpdatedDt(),
					DateUtil.GLOBAL_TIME_FORMAT));
		}

		// Populate invoice list
		if (request.getIttbRecurringDtls().size() > 0) {
			invoiceLB.getItems().clear();
			invoiceLB.setMold(Constants.LISTBOX_MOLD_PAGING);
			invoiceLB.setPageSize(10);
			if (invoiceLB.getListfoot() != null)
				invoiceLB.removeChild(invoiceLB.getListfoot());
		} else {
			if (invoiceLB.getListfoot() == null)
				invoiceLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
		}

		for (IttbRecurringDtl invoiceHeaderDtl : request.getIttbRecurringDtls()) {
			Listitem item = new Listitem();
			item.appendChild(newListcell(invoiceHeaderDtl.getReferenceId()));
			item.appendChild(newListcell(invoiceHeaderDtl.getTokenId()));
			item.appendChild(newListcell(invoiceHeaderDtl.getAmount()));
			item.appendChild(newListcell(invoiceHeaderDtl.getCurrency()));
			item.appendChild(newListcell(invoiceHeaderDtl.getInvoiceDate()));
			item.appendChild(newListcell(invoiceHeaderDtl.getInvoiceNo()));
			item.appendChild(newListcell(invoiceHeaderDtl.getCabchargeCustNo()));
			item.appendChild(newListcell(invoiceHeaderDtl.getCabchargeCardNo()));
			item.appendChild(newListcell(invoiceHeaderDtl.getStatus()));
			item.appendChild(newListcell(invoiceHeaderDtl.getError()));
			invoiceLB.appendChild(item);
		}

		if (request.getStatus().equals(NonConfigurableConstants.RECURRING_REQUEST_STATUS_UPLOADED)
				&& request.getIttbRecurringDtls().size() > 0)
			uploadBtn.setVisible(false);
		else
			uploadBtn.setVisible(true);

		if (request.getStatus().equals(NonConfigurableConstants.RECURRING_REQUEST_STATUS_COMPLETED)) {
			uploadBtn.setVisible(true);
			deleteBtn.setVisible(true);
		}
		else {
			uploadBtn.setVisible(false);
			deleteBtn.setVisible(false);
		}
			
	}

	public void upload() throws InterruptedException {
		try {
			LinkedHashMap<String, List<String>> listOfUploadedFiles = new LinkedHashMap<String, List<String>>();

			Media media = Fileupload.get(true);

			if (media == null) {
				Messagebox.show("You need to choose a file to upload!", "Error", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			System.out.println("file name: " + media.getName());
			if (listOfUploadedFiles.get(media.getName()) != null) {
				Messagebox.show("File rejected has another file with the same name has been uploaded!",
						"Error", Messagebox.OK, Messagebox.ERROR);
			} else {


				BufferedReader reader = new BufferedReader(new StringReader(new String(
						media.getByteData())));
				// Read everything in the line and store first
				List<String> linesOfData = new ArrayList<String>();
				String line;
				while ((line = reader.readLine()) != null) {
					// in case last line is just a carriage return
					if (line.length() > 0)
						linesOfData.add(line);
				}

				listOfUploadedFiles.put(media.getName(), linesOfData);
			}

			this.businessHelper.getInvoiceBusiness().uploadAndProcessRecurringReturnFile(request, listOfUploadedFiles);

			this.refresh();

		} catch (HibernateOptimisticLockingFailureException holfe) {
			logger.error(holfe, holfe);
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		} 
		catch (Exception e) {
			logger.error(e, e);
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void delete() throws InterruptedException {
		try {
			if (Messagebox.show("Are you sure you wish to delete?", "Confirmation", Messagebox.OK
					| Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
				return;
			}

			this.request.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_DELETED);
			this.businessHelper.getGenericBusiness().update(request, CommonWindow.getUserLoginIdAndDomain());

			Messagebox.show("The Recurring Request has been successfully deleted",
					"Delete Recurring Request", Messagebox.OK, Messagebox.INFORMATION);

			this.refresh();
		} catch (HibernateOptimisticLockingFailureException holfe) {
			logger.error(holfe, holfe);
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		} catch (Exception e) {
			logger.error(e, e);
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() {
		request = this.businessHelper.getAdminBusiness().searchIttbRecurringRequest(request.getReqNo());
		this.afterCompose();
	}
}
