package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;

public class BirthdayAnnouncementWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BirthdayAnnouncementWindow.class);
	private Listbox accountStatusLB;
	private Long reportRsrcId;
	private String report = "Birthday Announcement";

	private Datebox birthdayDateFromDB, birthdayDateToDB, joinDateFromDB, joinDateToDB;

	public BirthdayAnnouncementWindow() throws IOException {
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}

	public void afterCompose() {
		Components.wireVariables(this, this);

		accountStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		accountStatusLB.setSelectedIndex(0);
		Set<Entry<String,String>> accountStatusEntries = NonConfigurableConstants.ACCOUNT_STATUS.entrySet();
		for(Entry<String,String> entry : accountStatusEntries){
			accountStatusLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
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

		String birthdayDateFrom = "";
		String birthdayDateTo = "";
		String joinDateFrom = "";
		String joinDateTo = "";
		String accountStatus = "";
		String accountStatusLabel = "";
		
		if (birthdayDateFromDB.getValue() == null)
			throw new WrongValueException(birthdayDateFromDB, "* Mandatory field");
		if(birthdayDateToDB.getValue() == null)
			throw new WrongValueException(birthdayDateToDB, "* Mandartory Field");
		
		if (birthdayDateFromDB.getValue() != null && birthdayDateToDB.getValue() == null)
			birthdayDateToDB.setValue(birthdayDateFromDB.getValue());
		else if (birthdayDateToDB.getValue() != null && birthdayDateFromDB.getValue() == null)
			birthdayDateFromDB.setValue(birthdayDateToDB.getValue());

		birthdayDateFrom = DateUtil.convertDateToStr(birthdayDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		birthdayDateTo = DateUtil.convertDateToStr(birthdayDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		
		if (joinDateFromDB.getValue() != null && joinDateToDB.getValue() == null)
			joinDateToDB.setValue(joinDateFromDB.getValue());
		else if (joinDateToDB.getValue() != null && joinDateFromDB.getValue() == null)
			joinDateFromDB.setValue(joinDateToDB.getValue());

		joinDateFrom = DateUtil.convertDateToStr(joinDateFromDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		joinDateTo = DateUtil.convertDateToStr(joinDateToDB.getValue(), DateUtil.REPORT_DATE_FORMAT);
		
		if(accountStatusLB.getSelectedItem().getValue().toString().length()>0){
			String status = accountStatusLB.getSelectedItem().getValue().toString();
			accountStatus =  status;
			accountStatusLabel = NonConfigurableConstants.ACCOUNT_STATUS.get(status);
		}
		else{
			accountStatus = "";
			accountStatusLabel = "";
		}
		
		// retrieve format
		Listbox formatList = (Listbox) this.getFellow("reportFormat");
		if (formatList.getSelectedItem() == null)
			throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String) formatList.getSelectedItem().getLabel();
		String format = (String) formatList.getSelectedItem().getValue();

//		logger.info("test > birthdayDateFrom > "+birthdayDateFrom + " birthdayDateTo > "+birthdayDateTo +" joinDateFrom > "+joinDateFrom +" joinDateTo > "+joinDateTo+" accountStatus > "+accountStatus);
		
		if (format.equals(Constants.FORMAT_CSV)) {
			StringBuffer dataInCSV = this.generateCSVData(birthdayDateFrom, birthdayDateTo, joinDateFrom, joinDateTo, accountStatus, accountStatusLabel, 
					getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
					dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String birthdayDateFrom, String birthdayDateTo, String joinDateFrom, String joinDateTo, String accountStatus, String accountStatusLabel, String printedBy) {

		StringBuffer sb = new StringBuffer();
		
		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Birthday Announcement Report"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if (birthdayDateFrom != null && birthdayDateFrom.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Upcoming Birthday From (MM-DD): " + birthdayDateFrom.substring(5)
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Upcoming Birthday To (MM-DD): " + birthdayDateTo.substring(5)
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");

			birthdayDateTo += " 23:59:59";
		}
		if (joinDateFrom != null && joinDateFrom.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Join Date From (YYYY-MM-DD): " + joinDateFrom
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER + "Join Date To (YYYY-MM-DD): " + joinDateTo
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");

			joinDateTo += " 23:59:59";
		}
		if(accountStatus != null && accountStatus.length() > 0) {
			sb.append(Constants.TEXT_QUALIFIER + "Account Status: " + accountStatusLabel
					+ Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
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
		sb.append(Constants.TEXT_QUALIFIER + "Account No." + Constants.TEXT_QUALIFIER + ","); // 0
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ","); // 1
		sb.append(Constants.TEXT_QUALIFIER + "Sub Applicant Account Name" + Constants.TEXT_QUALIFIER + ","); // 2
		sb.append(Constants.TEXT_QUALIFIER + "Salutation" + Constants.TEXT_QUALIFIER + ","); // 3
		sb.append(Constants.TEXT_QUALIFIER + "Name on Card" + Constants.TEXT_QUALIFIER + ","); // 4
		sb.append(Constants.TEXT_QUALIFIER + "DOB" + Constants.TEXT_QUALIFIER + ","); // 5
		sb.append(Constants.TEXT_QUALIFIER + "Email" + Constants.TEXT_QUALIFIER + ","); // 6
		sb.append(Constants.TEXT_QUALIFIER + "Mobile No." + Constants.TEXT_QUALIFIER + ","); // 7
		sb.append(Constants.TEXT_QUALIFIER + "Block No." + Constants.TEXT_QUALIFIER + ","); // 8
		sb.append(Constants.TEXT_QUALIFIER + "Street Name" + Constants.TEXT_QUALIFIER + ","); // 9
		sb.append(Constants.TEXT_QUALIFIER + "Unit No." + Constants.TEXT_QUALIFIER + ","); // 10
		sb.append(Constants.TEXT_QUALIFIER + "Building Name" + Constants.TEXT_QUALIFIER + ","); // 11
		sb.append(Constants.TEXT_QUALIFIER + "Country" + Constants.TEXT_QUALIFIER + ","); // 12
		sb.append(Constants.TEXT_QUALIFIER + "Postal Code" + Constants.TEXT_QUALIFIER + ","); // 13
		sb.append("\n");

		String birthdayDateFromSQL = birthdayDateFrom.substring(5,7)+ birthdayDateFrom.substring(8,10);
		String birthdayDateToSQL = birthdayDateTo.substring(5,7) + birthdayDateTo.substring(8,10);
		// Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getBirthdayAnnouncement(birthdayDateFromSQL, birthdayDateToSQL, joinDateFrom, joinDateTo, accountStatus);
		for (Object[] columnDataObject : rowsOfData) {
			for (int i = 0; i < columnDataObject.length; i++) {
				Object data = columnDataObject[i];
				if(i==1 || i==5 || i==8){
					if (data != null && !data.toString().trim().equals(""))
						sb.append("" + Constants.TEXT_QUALIFIER + data.toString().replaceAll("\"", "\"\"")
								+ Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + "-"+ Constants.TEXT_QUALIFIER + ",");
				}
				else
				{
					if (data != null)
						sb.append("" + Constants.TEXT_QUALIFIER + data.toString().replaceAll("\"", "\"\"")
								+ Constants.TEXT_QUALIFIER + ",");
					else
						sb.append(Constants.TEXT_QUALIFIER + Constants.TEXT_QUALIFIER + ",");
				}
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
		birthdayDateFromDB.setText("");
		birthdayDateToDB.setText("");
		joinDateFromDB.setText("");
		joinDateToDB.setText("");
		accountStatusLB.setSelectedIndex(0);
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}