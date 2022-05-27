package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class EmailAuditWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EmailAuditWindow.class);

	private Long reportRsrcId;
	private String report = "Email Audit";

	private Datebox dateFromDB, dateToDB;

	public EmailAuditWindow() throws IOException {
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

	}

	public void populateReportFormatList(Listbox listbox) throws NetException {
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness()
				.getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		for (MstbReportFormatMap formatMap : reportFormatMapList) {
			Listitem listItem = new Listitem(formatMap.getReportFormat(),
					Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if (firstItem) {
				listItem.setSelected(true);
				firstItem = false;
			}
			listbox.appendChild(listItem);
		}
	}

	public void generate() throws HttpException, IOException, InterruptedException, NetException,
			WrongValueException {
		logger.info("");
		this.displayProcessing();

		String dateFrom = "";
		String dateTo = "";
		
		if (dateFromDB.getValue() == null)
			throw new WrongValueException(dateFromDB, "* Mandatory field");
		if(dateToDB.getValue() == null)
			throw new WrongValueException(dateToDB, "* Mandartory Field");
		
		if (dateFromDB.getValue() != null && dateToDB.getValue() == null)
			dateToDB.setValue(dateFromDB.getValue());
		else if (dateToDB.getValue() != null && dateFromDB.getValue() == null)
			dateFromDB.setValue(dateToDB.getValue());

		dateFrom = DateUtil
				.convertDateToStr(dateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		dateTo = DateUtil.convertDateToStr(dateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		
		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

		if (format.equals(Constants.FORMAT_CSV)) {
			StringBuffer dataInCSV = this.generateCSVData(dateFrom, dateTo, getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
					dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String dateFrom, String dateTo, String printedBy) {

		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Email Audit Log Report"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if (dateFrom != null && dateFrom.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Request Date From: " + dateFrom
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Request Date To: " + dateTo
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");

			dateTo += " 23:59:59";
		}
	
		// Line Break
		sb.append("\n");

		sb.append(Constants.TEXT_QUALIFIER + "Printed By: " + printedBy + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		// Column Title
		sb.append(Constants.TEXT_QUALIFIER + "Email From" + Constants.TEXT_QUALIFIER + ","); // 0
		sb.append(Constants.TEXT_QUALIFIER + "Email To" + Constants.TEXT_QUALIFIER + ","); // 1
		sb.append(Constants.TEXT_QUALIFIER + "Send Date" + Constants.TEXT_QUALIFIER + ","); // 2
		sb.append(Constants.TEXT_QUALIFIER + "Subject Header" + Constants.TEXT_QUALIFIER + ","); // 3
		sb.append(Constants.TEXT_QUALIFIER + "Job" + Constants.TEXT_QUALIFIER + ","); // 4
		sb.append(Constants.TEXT_QUALIFIER + "Status" + Constants.TEXT_QUALIFIER + ","); // 5
		sb.append(Constants.TEXT_QUALIFIER + "Status Remarks" + Constants.TEXT_QUALIFIER + ","); // 6
		sb.append("\n");

		// Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getEmailAudit(dateFrom, dateTo);
		for (Object[] columnDataObject : rowsOfData) {
			for (int i = 0; i < columnDataObject.length; i++) {
				Object data = columnDataObject[i];

					if (data != null)
						sb.append("" + Constants.TEXT_QUALIFIER + data.toString().replaceAll("\"", "\"\"")
								+ Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
			}
			sb.append("\n");
		}

		if (rowsOfData.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}

		return sb;
	}

	public void reset() {
		dateFromDB.setText("");
		dateToDB.setText("");
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}