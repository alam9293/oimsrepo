package com.cdgtaxi.ibs.govteinv.ui;

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
import com.cdgtaxi.ibs.common.exception.GovtEInvException;
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceHdrDtl;
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class ViewRequestWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewRequestWindow.class);
	private Label requestNoLbl, statusLbl, outgoingFileNameLbl, billGenRequestNoLbl, invoiceDateLbl,
			invoiceNoFromLbl, invoiceNoToLbl, accountNoLbl, accountNameFromLbl, createdByLabel,
			createdDateLabel, createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel,
			lastUpdatedTimeLabel;
	private Button uploadBtn, deleteBtn;
	private IttbGovtEinvoiceReq request;
	private Listbox invoiceLB;

	@SuppressWarnings("rawtypes")
	public ViewRequestWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer requestNo = (Integer) params.get("requestNo");
		request = this.businessHelper.getAdminBusiness().searchGovtEInvRequest(requestNo);
	}

	// After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);

		requestNoLbl.setValue(request.getReqNo().toString());
		statusLbl.setValue(NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS.get(request.getStatus()));
		outgoingFileNameLbl.setValue(request.getFileName());
		billGenRequestNoLbl.setValue(request.getBmtbBillGenReq() != null ? request.getBmtbBillGenReq()
				.getReqNo().toString() : "-");
		invoiceDateLbl.setValue(request.getInvoiceDate() != null ? DateUtil.convertDateToStr(
				request.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT) : "-");
		invoiceNoFromLbl.setValue(request.getInvoiceNoFrom() != null ? request.getInvoiceNoFrom().toString()
				: "-");
		invoiceNoToLbl.setValue(request.getInvoiceNoTo() != null ? request.getInvoiceNoTo().toString() : "-");
		accountNoLbl.setValue(request.getAmtbAccount() != null ? request.getAmtbAccount().getAccountNo()
				.toString() : "-");
		accountNameFromLbl.setValue(request.getAmtbAccount() != null ? request.getAmtbAccount()
				.getAccountName() : "-");

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
			lastUpdatedDateLabel.setValue(DateUtil.convertTimestampToStr(request.getUpdatedDt(),
					DateUtil.GLOBAL_DATE_FORMAT));
			lastUpdatedTimeLabel.setValue(DateUtil.convertTimestampToStr(request.getUpdatedDt(),
					DateUtil.GLOBAL_TIME_FORMAT));
		}

		// Populate invoice list
		if (request.getIttbGovtEinvoiceHdrDtls().size() > 0) {
			invoiceLB.getItems().clear();
			invoiceLB.setMold(Constants.LISTBOX_MOLD_PAGING);
			invoiceLB.setPageSize(10);
			if (invoiceLB.getListfoot() != null)
				invoiceLB.removeChild(invoiceLB.getListfoot());
		} else {
			if (invoiceLB.getListfoot() == null)
				invoiceLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
		}

		for (IttbGovtEinvoiceHdrDtl invoiceHeaderDtl : request.getIttbGovtEinvoiceHdrDtls()) {
			Listitem item = new Listitem();
			item.appendChild(newListcell(invoiceHeaderDtl.getInvoiceNo()));
			item.appendChild(newListcell(invoiceHeaderDtl.getInvoiceDate()));
			item.appendChild(newListcell(invoiceHeaderDtl.getGrossAmt()));
			item.appendChild(newListcell(invoiceHeaderDtl.getGstAmt()));
			item.appendChild(newListcell(invoiceHeaderDtl.getBusinessUnit()));
			item.appendChild(newListcell(invoiceHeaderDtl.getReturnStatus() == null ? "-"
					: NonConfigurableConstants.GOVT_EINV_RETURN_STATUS.get(invoiceHeaderDtl.getReturnStatus())));
			invoiceLB.appendChild(item);
		}

		if (request.getStatus().equals(NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_COMPLETED)
				&& request.getIttbGovtEinvoiceHdrDtls().size() > 0)
			uploadBtn.setVisible(true);
		else
			uploadBtn.setVisible(false);

		if (request.getStatus().equals(NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_PENDING))
			deleteBtn.setVisible(true);
		else
			deleteBtn.setVisible(false);
	}

	public void upload() throws InterruptedException {
		try {
			LinkedHashMap<String, List<String>> listOfUploadedFiles = new LinkedHashMap<String, List<String>>();

			boolean keepAskingForFile = true;
			while (keepAskingForFile) {
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

					// if(!media.getContentType().equals("text/plain")){
					// Messagebox.show("The file uploaded must be a plain text file!",
					// "Error", Messagebox.OK, Messagebox.ERROR);
					// return;
					// }

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

				if (Messagebox.show("Do you want to upload another file?", "Upload Return File",
						Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
					keepAskingForFile = false;
				}
			}

			this.businessHelper.getAdminBusiness().uploadGovEInvReturnFile(request, listOfUploadedFiles);

			this.refresh();

		} catch (HibernateOptimisticLockingFailureException holfe) {
			logger.error(holfe, holfe);
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		} catch (GovtEInvException gei) {
			logger.error(gei, gei);
			Messagebox.show(gei.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
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

			this.request.setStatus(NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_DELETED);
			this.businessHelper.getGenericBusiness().update(request, CommonWindow.getUserLoginIdAndDomain());

			Messagebox.show("The Govt eInv Request has been successfully deleted",
					"Delete Govt eInv Request", Messagebox.OK, Messagebox.INFORMATION);

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
		request = this.businessHelper.getAdminBusiness().searchGovtEInvRequest(request.getReqNo());
		this.afterCompose();
	}
}
