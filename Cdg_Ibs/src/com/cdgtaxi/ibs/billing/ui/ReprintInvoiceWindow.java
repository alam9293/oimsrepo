package com.cdgtaxi.ibs.billing.ui;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.elixirtech.ers2.client.ERSClient;

public class ReprintInvoiceWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReprintInvoiceWindow.class);
	private final BmtbInvoiceHeader bmtbInvoiceHeader;
	private final AmtbAccount debtToaccount;

	private ERSClient client;

	//Report Server Properties
	private static String ip;
	private static String port;
	private static String username;
	private static String password;
	private static String repository;

	@SuppressWarnings("unchecked")
	public ReprintInvoiceWindow(){
		Map params = Executions.getCurrent().getArg();
		bmtbInvoiceHeader = (BmtbInvoiceHeader)this.businessHelper.getGenericBusiness().get(BmtbInvoiceHeader.class, (Long)params.get("invoiceHeaderNo"));
		debtToaccount = this.businessHelper.getAccountBusiness().getAccount(""+params.get("accountNo"));
	}

	public void cancel(){
		this.detach();
	}

	public void reprint() throws InterruptedException{
		logger.info("");

		try{
			Properties reportParamsProperties = new Properties();

			//customer name
			CapsTextbox customerNameTextBox = (CapsTextbox)this.getFellow("customerNameTextBox");
			String customerName = customerNameTextBox.getValue();
			
			//address 1
			CapsTextbox blockNoTextBox = (CapsTextbox)this.getFellow("blockNoTextBox");
			String blockNo = blockNoTextBox.getValue();
			CapsTextbox streetNameTextBox = (CapsTextbox)this.getFellow("streetNameTextBox");
			String streetName = streetNameTextBox.getValue();
			String address1 = "";
			if(blockNo.length()>0) {
				address1 = blockNo + " " +streetName;
			} else {
				address1 = streetName;
			}

			//address 2
			CapsTextbox unitNoTextBox = (CapsTextbox)this.getFellow("unitNoTextBox");
			String unitNo = unitNoTextBox.getValue();
			CapsTextbox buildingNameTextBox = (CapsTextbox)this.getFellow("buildingNameTextBox");
			String buildingName = buildingNameTextBox.getValue();
			String address2 = null;
			if(unitNo.length()>0 && buildingName.length()>0) {
				address2 = unitNo+" "+buildingName;
			} else if(unitNo.length()>0) {
				address2 = unitNo;
			} else if(buildingName.length()>0) {
				address2 = buildingName;
			}

			//address 3
			CapsTextbox areaTextBox = (CapsTextbox)this.getFellow("areaTextBox");
			String area = areaTextBox.getValue();
			String address3 = null;
			if(area.length()>0) {
				address3 = area;
			}

			//country
			Listbox countryListBox = (Listbox)this.getFellow("countryListBox");
			String countryCode = (String)countryListBox.getSelectedItem().getValue();
			String country = ConfigurableConstants.getCountries().get(countryCode);

			//city
			CapsTextbox cityTextBox = (CapsTextbox)this.getFellow("cityTextBox");
			String city = "";
			if(cityTextBox.isDisabled()==false) {
				city = cityTextBox.getValue();
			}
			
			//state
			CapsTextbox stateTextBox = (CapsTextbox)this.getFellow("stateTextBox");
			String state = "";
			if(stateTextBox.isDisabled()==false) {
				state = stateTextBox.getValue();
			}

			//Postal
			CapsTextbox postalTextBox = (CapsTextbox)this.getFellow("postalTextBox");
			String postal = postalTextBox.getValue();

			//address4
			String address4 = null;
			if(country.equals("SINGAPORE")) address4 = country + " " + postal;
			else address4 = country + " " + state + " " + city + " " + postal;
			
			//Billing Contact
			CapsTextbox billingContactTextBox = (CapsTextbox)this.getFellow("billingContactTextBox");
			String billingContact = billingContactTextBox.getValue();
			
			//Billed To
			CapsTextbox billedToTextBox = (CapsTextbox)this.getFellow("billedToTextBox");
			String billedTo = billedToTextBox.getValue(); 
			
			if(address1!=null) reportParamsProperties.put("address 1", address1.replaceAll("'", "''"));
			if(address2!=null) reportParamsProperties.put("address 2", address2.replaceAll("'", "''"));
			if(address3!=null) reportParamsProperties.put("address 3", address3.replaceAll("'", "''"));
			if(address4!=null) reportParamsProperties.put("address 4", address4.replaceAll("'", "''"));
			reportParamsProperties.put("customer name", customerName.replaceAll("'", "''"));
			reportParamsProperties.put("billing contact", billingContact.replaceAll("'", "''"));
			reportParamsProperties.put("postal", postal);
			reportParamsProperties.put("bill to account name", billedTo.replaceAll("'", "''"));

			Map pdfGenProperties = (Map)SpringUtil.getBean("pdfGenProperties");
			Integer.parseInt((String)pdfGenProperties.get("pdfgen.buffer.duedate"));
			reportParamsProperties.put("bufferDays", pdfGenProperties.get("pdfgen.buffer.duedate"));
			
			//Prepare the elixir client
			//retrieve properties bean
			Map elixirReportProperties = (Map)SpringUtil.getBean("elixirReportProperties");
			//retrieve properties value
			ip = (String)elixirReportProperties.get("report.server.ip");
			port = (String)elixirReportProperties.get("report.server.port");
			username = (String)elixirReportProperties.get("report.server.username");
			password = (String)elixirReportProperties.get("report.server.password");
			repository = (String)elixirReportProperties.get("report.server.repository.location");

			client = new ERSClient(ip, Integer.parseInt(port), username, password);
			client.setSecure(false);

			//pulling the data from original tables and not draft tables
			reportParamsProperties.put("invoiceHeaderNo", ""+bmtbInvoiceHeader.getInvoiceHeaderNo());

			String reportName = NonConfigurableConstants.REPORT_NAME_INV_MISC;

			this.displayProcessing();
			logger.info("Reprint Invoice ["+reportName+"] in PDF - Invoice Header No "+bmtbInvoiceHeader.getInvoiceHeaderNo());
			ByteArrayOutputStream pdfOS = new ByteArrayOutputStream();
			client.renderReport(repository+"/"+reportName+"/"+reportName+".rml", Constants.FORMAT_PDF, pdfOS, reportParamsProperties);
			byte[] pdfByteArray = pdfOS.toByteArray();
			pdfOS.close();

			Filedownload.save(pdfByteArray, "application/pdf", "invoice_" + bmtbInvoiceHeader.getInvoiceNo() + "_reprint.pdf");

			this.detach();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {

	}

	public void afterCompose() {
		try{
			AmtbContactPerson mainContactPerson = this.businessHelper.getAccountBusiness().getMainContactByType(debtToaccount.getAccountNo(), NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);
			
			CapsTextbox customerNameTextBox = (CapsTextbox)this.getFellow("customerNameTextBox");
			String accountName = "";
			if(debtToaccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
				accountName = debtToaccount.getAmtbAccount().getAmtbAccount().getAccountName();
			else if(debtToaccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) ||
				debtToaccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) 
				accountName = debtToaccount.getAmtbAccount().getAccountName();
			else
				accountName = debtToaccount.getAccountName();
			customerNameTextBox.setValue(accountName);
			
			CapsTextbox blockNoTextBox = (CapsTextbox)this.getFellow("blockNoTextBox");
			if(mainContactPerson.getAddressBlock()!=null)blockNoTextBox.setValue(mainContactPerson.getAddressBlock());
			CapsTextbox unitNoTextBox = (CapsTextbox)this.getFellow("unitNoTextBox");
			if(mainContactPerson.getAddressUnit()!=null)unitNoTextBox.setValue(mainContactPerson.getAddressUnit());
			CapsTextbox streetNameTextBox = (CapsTextbox)this.getFellow("streetNameTextBox");
			streetNameTextBox.setValue(mainContactPerson.getAddressStreet());
			CapsTextbox buildingNameTextBox = (CapsTextbox)this.getFellow("buildingNameTextBox");
			if(mainContactPerson.getAddressBuilding()!=null)buildingNameTextBox.setValue(mainContactPerson.getAddressBuilding());
			CapsTextbox areaTextBox = (CapsTextbox)this.getFellow("areaTextBox");
			if(mainContactPerson.getAddressArea()!=null)areaTextBox.setValue(mainContactPerson.getAddressArea());
			
			Listbox countryListBox = (Listbox)this.getFellow("countryListBox");
			Map<String, String> masterCountry = ConfigurableConstants.getCountries();
			boolean firstItem = true;
			for(String masterCode : masterCountry.keySet()){
				Listitem item = countryListBox.appendItem(masterCountry.get(masterCode), masterCode);
	
				if(firstItem){
					item.setSelected(true);
					firstItem = false;
				}
				
				if(mainContactPerson.getMstbMasterTableByAddressCountry()!=null){
					if(mainContactPerson.getMstbMasterTableByAddressCountry().getMasterCode().equals(masterCode)){
						item.setSelected(true);
						if(!masterCode.equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
							CapsTextbox cityTextBox = (CapsTextbox)this.getFellow("cityTextBox");
							cityTextBox.setValue(mainContactPerson.getAddressCity());
							CapsTextbox stateTextBox = (CapsTextbox)this.getFellow("stateTextBox");
							stateTextBox.setValue(mainContactPerson.getAddressState());
						}
					}
				}
				//default selection to Singapore
				else if(masterCode.equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)) {
					item.setSelected(true);
				}
			}
	
			CapsTextbox postalTextBox = (CapsTextbox)this.getFellow("postalTextBox");
			postalTextBox.setValue(mainContactPerson.getAddressPostal());
			CapsTextbox billingContactTextBox = (CapsTextbox)this.getFellow("billingContactTextBox");
			String billingContactName = mainContactPerson.getMainContactName();
			if(mainContactPerson.getMstbMasterTableByMainContactSal()!=null)
				billingContactName = mainContactPerson.getMstbMasterTableByMainContactSal().getMasterValue()+" "+billingContactName;
			String subBillingContactName = mainContactPerson.getSubContactName();
			if(subBillingContactName!=null && mainContactPerson.getMstbMasterTableBySubContactSal()!=null)
				subBillingContactName = mainContactPerson.getMstbMasterTableBySubContactSal().getMasterValue()+" "+subBillingContactName;
			if(subBillingContactName!=null)
				billingContactName = billingContactName+" / "+subBillingContactName;
			billingContactTextBox.setValue(billingContactName);
			
			this.onSelectCountry(countryListBox);
			
			CapsTextbox billedToTextBox = (CapsTextbox)this.getFellow("billedToTextBox");
			String billedTo = null;
			if(debtToaccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
				billedTo = debtToaccount.getAmtbAccount().getAccountName()+" ("+debtToaccount.getAmtbAccount().getCode()+") / "+
					debtToaccount.getAccountName()+" ("+debtToaccount.getCode()+")";
			else if(debtToaccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) ||
				debtToaccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) 
				billedTo = debtToaccount.getAccountName()+" ("+debtToaccount.getCode()+")";
			billedToTextBox.setValue(billedTo);
		}
		catch(WrongValueException wve){
			throw wve;
		}
	}

	public void onSelectCountry(Listbox listbox){
		String countryCode = (String)listbox.getSelectedItem().getValue();
		if(countryCode.equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			((CapsTextbox)this.getFellow("cityTextBox")).setDisabled(true);
			((CapsTextbox)this.getFellow("stateTextBox")).setDisabled(true);
		}
		else{
			((CapsTextbox)this.getFellow("cityTextBox")).setDisabled(false);
			((CapsTextbox)this.getFellow("stateTextBox")).setDisabled(false);
		}
	}
}
