package com.cdgtaxi.ibs.acct.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceFile;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;

//public class ViewDepositWindow extends CommonInnerAcctWindow {
//	private static final long serialVersionUID = 1L;
//	private static Logger logger = Logger.getLogger(ViewDepositWindow.class);
//	private String custNo, acctStatus;
//	
//	@SuppressWarnings("unchecked")
//	public ViewDepositWindow() throws InterruptedException{
//		logger.info("ViewDepositWindow()");
//		//customer number
//		Map<String, String> map = Executions.getCurrent().getArg();
//		custNo = map.get("custNo");
//		if(custNo==null || custNo.trim().length()==0){
//			Messagebox.show("No account number found!", "Create Department", Messagebox.OK, Messagebox.ERROR);
//		}
//		// account status
//		acctStatus = map.get("acctStatus");
//		if(acctStatus==null || acctStatus.trim().length()==0){
//			Messagebox.show("No account status found!", "Create Department", Messagebox.OK, Messagebox.ERROR);
//		}
//	}
//	public void init() throws InterruptedException{
//		logger.info("init()");
//		// checking
//		if(custNo==null || custNo.trim().length()==0){
//			this.back();
//		}else if(acctStatus==null || acctStatus.trim().length()==0){
//			this.back();
//		}
//		BigDecimal totalRequested = new BigDecimal(0);
//		Map<String, Map<String, String>> deposits = this.businessHelper.getAccountBusiness().getDepositDetails(custNo);
//		Map<String, String> depositAmts = deposits.get("depositAmts");
//		((Label)this.getFellow("requiredDeposit")).setValue(depositAmts.get("requiredDeposit"));
//		((Label)this.getFellow("collectedDeposit")).setValue(depositAmts.get("collectedDeposit"));
//		Rows invoices = (Rows)this.getFellow("invoices");
//		invoices.getChildren().clear();
//		for(String invoiceNo : deposits.keySet()){
//			if(deposits.get(invoiceNo).get("invoiceNo")!=null){
//				Map<String, String> deposit = deposits.get(invoiceNo);
//				totalRequested = totalRequested.add(new BigDecimal(deposit.get("invoiceAmt").replaceAll(",", "")));
//				Row invoice = new Row();
//				invoice.appendChild(new Label((invoices.getChildren().size()+1)+""));
//				invoice.appendChild(new Label(deposit.get("invoiceNo")));
//				invoice.appendChild(new Label(deposit.get("invoiceDate")));
//				invoice.appendChild(new Label(deposit.get("invoiceAmt")));
//				invoice.appendChild(new Label(deposit.get("paidAmt")));
//				invoice.appendChild(new Label(deposit.get("outstandingAmt")));
//				invoice.appendChild(new Label(deposit.get("invoiceStatus")));
//				invoices.appendChild(invoice);
//			}
//		}
//		((Label)this.getFellow("totalRequestedDeposit")).setValue(StringUtil.bigDecimalToString(totalRequested, StringUtil.GLOBAL_DECIMAL_FORMAT));
//		Decimalbox depositAmt = (Decimalbox)this.getFellow("depositAmt");
//		Constraint tempConstraint = depositAmt.getConstraint();
//		Constraint nullConstraint = null;
//		depositAmt.setConstraint(nullConstraint);
//		depositAmt.setValue(null);
//		depositAmt.setConstraint(tempConstraint);
//		Datebox invoiceDate = (Datebox)this.getFellow("invoiceDate");
//		tempConstraint = invoiceDate.getConstraint();
//		nullConstraint = null;
//		invoiceDate.setConstraint(nullConstraint);
//		invoiceDate.setValue(DateUtil.getCurrentDate());
//		invoiceDate.setConstraint(tempConstraint);
//	}
//	public void generateInvoice() throws InterruptedException{
//		Map<String, Integer> contacts = this.businessHelper.getAccountBusiness().getAccountMainContact(custNo);
//		boolean hasMainBilling = false;
//		for(String type : contacts.keySet()){
//			if(type.equals("mainBilling")){
//				hasMainBilling = true;
//				break;
//			}
//		}
//		if(!hasMainBilling){
//			Messagebox.show("No main billing contact person! Please create main billing contact person first.", "Create Division", Messagebox.OK, Messagebox.ERROR);
//			return;
//		}
//		if(Messagebox.show("Generate invoice?", "Deposit", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
//			BigDecimal deposit = ((Decimalbox)this.getFellow("depositAmt")).getValue();
//			Date invoiceDate = ((Datebox)this.getFellow("invoiceDate")).getValue();
//			if(deposit!=null && invoiceDate!=null){
//				BmtbInvoiceHeader header = this.businessHelper.getAccountBusiness().generateDepositInvoice(deposit, invoiceDate, custNo, this.getUserLoginId()); 
//				if(header!=null){
//					try{
//						AMedia media = this.generate(header);
//						int response = Messagebox.show(
//								"Invoice has been successfully issued and generated. Do you wish to download the invoice?", "Issue Misc. Invoice",
//								Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION);
//						if (response == Messagebox.YES) {
//							Filedownload.save(media);
//						}
//					}
//					catch(NetException ne){
//						Messagebox.show("Unable to generate invoice in pdf. Please inform the administrator", "Deposit", Messagebox.OK, Messagebox.ERROR);
//					}
//					catch(IOException ioe){
//						Messagebox.show("Unable to generate invoice in pdf. Please inform the administrator", "Deposit", Messagebox.OK, Messagebox.ERROR);
//					}
//					init();
//				}else{
//					Messagebox.show("Unable to generate invoice. Please try again later", "Deposit", Messagebox.OK, Messagebox.ERROR);
//				}
//			}else{
//				Messagebox.show("Please key in deposit amount and invoice date", "Deposit", Messagebox.OK, Messagebox.EXCLAMATION);
//			}
//		}
//	}
//	
//	private AMedia generate(BmtbInvoiceHeader header) throws HttpException, IOException, InterruptedException, NetException {
//		Properties params = new Properties();
//		params.put("invoiceHeaderNo", header.getInvoiceHeaderNo().toString());
//		params.put("reprint", "N");
//
//		String outputFormat = Constants.FORMAT_PDF;
//		String reportName = NonConfigurableConstants.REPORT_NAME_INV_DEPOSIT;
//
//		byte[] bytes =
//			businessHelper.getReportBusiness().generate(reportName, null, outputFormat, params);
//
//		BmtbInvoiceFile newInvoiceFile = new BmtbInvoiceFile();
//		newInvoiceFile.setInvoiceFile(Hibernate.createBlob(bytes));
//		header.setBmtbInvoiceFile(newInvoiceFile);
//		businessHelper.getGenericBusiness().save(newInvoiceFile);
//		businessHelper.getGenericBusiness().update(header);
//
//		return new AMedia(reportName + ".pdf", "pdf", outputFormat, bytes);
//	}
//	
//	@Override
//	public void refresh() throws InterruptedException {
//		init();
//	}
//}
