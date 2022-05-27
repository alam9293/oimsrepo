package com.cdgtaxi.ibs.billing.business;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.zkoss.zkplus.hibernate.HibernateUtil;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.billing.process.PrintingProcessor;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvFile;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceFile;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoicePrintReq;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.IttbRecurringDtl;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;
import com.cdgtaxi.ibs.common.model.recurring.CSVUtil;
import com.cdgtaxi.ibs.common.model.recurring.RecurringConfigFTP;
import com.cdgtaxi.ibs.common.model.recurring.RecurringCsvData;
import com.cdgtaxi.ibs.common.model.recurring.RecurringCsvHelper;
import com.cdgtaxi.ibs.common.model.recurring.RecurringFileHandler;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.google.common.base.Splitter;

public class InvoiceBusinessImpl extends GenericBusinessImpl implements InvoiceBusiness {
	private static final Logger logger = Logger.getLogger(InvoiceBusinessImpl.class);

	public List<BmtbInvoiceHeader> searchInvoice(SearchInvoiceForm form) {
		return this.daoHelper.getInvoiceDao().searchInvoice(form);
	}

	public List<BmtbDraftInvHeader> searchDraftInvoice(SearchInvoiceForm form) {
		return this.daoHelper.getInvoiceDao().searchDraftInvoice(form);
	}

	public BmtbInvoiceHeader getInvoice(String invoiceNo) {
		return this.daoHelper.getInvoiceDao().getInvoice(invoiceNo);
	}

	public byte[] getInvoiceFile(String invoiceNo) {
		return daoHelper.getInvoiceDao().getInvoiceFile(invoiceNo);
	}

	public byte[] getDraftInvoiceFile(String invoiceNo) {
		return daoHelper.getInvoiceDao().getDraftInvoiceFile(invoiceNo);
	}

	public List<BmtbInvoiceHeader> searchNoteIssuableInvoice(SearchInvoiceForm form) {
		return this.daoHelper.getInvoiceDao().searchNoteIssuableInvoice(form);
	}

	public AmtbAccount getAccountWithEntity(AmtbAccount amtbAccount) {
		return this.daoHelper.getAccountDao().getAccountWithEntity(amtbAccount);
	}

	public boolean print(Integer billGenRequestNo, Integer custNoFrom, Integer custNoTo, Long invoiceNoFrom,
			Long invoiceNoTo, boolean printNoActivity, Set<Listitem> printers, String printedBy) throws IOException {

		List<BmtbInvoiceHeader> invoices = this.daoHelper.getInvoiceDao().getInvoicesForPrinting(billGenRequestNo,
				custNoFrom, custNoTo, invoiceNoFrom, invoiceNoTo, printNoActivity);

		if (!invoices.isEmpty()) {
			BmtbInvoicePrintReq printReq = new BmtbInvoicePrintReq();
			printReq.setBillGenReqNo(billGenRequestNo);
			printReq.setCustNoFrom(custNoFrom);
			printReq.setCustNoTo(custNoTo);
			printReq.setInvoiceNoFrom(invoiceNoFrom);
			printReq.setInvoiceNoTo(invoiceNoTo);
			Long reqId = (Long) this.daoHelper.getGenericDao().save(printReq, printedBy);

			List<Listitem> newList = new ArrayList<Listitem>();
			newList.addAll(printers);
			Map printingProperties = (Map) SpringUtil.getBean("printingProperties");

			// Enhancement 11 May 2010 - #979 Print Invoice Sorting
			int sorting = PrintingProcessor.SORTING_DEFAULT; // default
			if (custNoFrom != null || custNoTo != null)
				sorting = PrintingProcessor.SORTING_ACCOUNT;

			logger.info("Total invoices found:" + invoices.size());
			new PrintingProcessor(reqId, invoices, newList, printingProperties, sorting).start();

			return true;
		} else
			return false;
	}

	public void updateInvoiceHeaderFile(byte[] bytes, BmtbInvoiceHeader header) {

		BmtbInvoiceFile newInvoiceFile = new BmtbInvoiceFile();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Blob image = Hibernate.getLobCreator(session).createBlob(bytes);
		newInvoiceFile.setInvoiceFile(image);
		header.setBmtbInvoiceFile(newInvoiceFile);
		super.save(newInvoiceFile);
		super.update(header);
	}

	public void updateInvoiceHeaderFile(byte[] bytes, BmtbDraftInvHeader header) {

		BmtbDraftInvFile newInvoiceFile = new BmtbDraftInvFile();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Blob image = Hibernate.getLobCreator(session).createBlob(bytes);
		newInvoiceFile.setInvoiceFile(image);
		header.setBmtbDraftInvFile(newInvoiceFile);
		super.save(newInvoiceFile);
		super.update(header);
	}

	public String processRecurringDtl(IttbRecurringReq ittbRecurringReq, int[] stats) {

		logger.info("Starting searchRecurringDtl RequestNo " + ittbRecurringReq.getReqNo() + " with status : "
				+ ittbRecurringReq.getRecurringAutoManual());
		int totalNoOfRecords = 0;
		int totalNoOfRecordsWriteToCsv = 0;
		int totalNoOfNoToken = 0;
		int totalNoOfFailed = 0;
		int totalNoOfZeroDollarSkipped = 0;
		int totalNoOfRecordsDetails = 0;
		int totalNoOfClosed = 0;
		int index = 1;
		String csvFileNameReturn = null;
		Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");

		String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");
		EmailUtil.sendGenericNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_START,recurringFrom);
		int fileupload = 0;
		try {

			List<BmtbInvoiceHeader> invoices = new ArrayList<BmtbInvoiceHeader>();

			// DO AUTO
			if (ittbRecurringReq.getRecurringAutoManual().equals("A")) {
				// select * from accounts where charge date is base on the date there
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(ittbRecurringReq.getChargingDate());
				int day = cal2.get(Calendar.DATE);

				// get all Auto Accounts that is set today date
				List<AmtbAccount> amtbAccountList = this.daoHelper.getAccountDao().getAccountRecurringByChargeDay(day);
				logger.info("Auto Recurring found accounts : " + amtbAccountList.size());
				if (amtbAccountList.size() > 0) {
					// loop through all acct and check invoices.

					for (AmtbAccount acct : amtbAccountList) {
						invoices.addAll(this.daoHelper.getBillGenRequestDao().getAllBmtbInvoicesForRecurring(acct));
						logger.info("Auto Recurring found invoices : " + invoices.size());
					}

				}
				logger.info("Auto Recurring total invoices : " + invoices.size());
			}

			// DO MANUAL
			else if (ittbRecurringReq.getRecurringAutoManual().equals("M")) {
				// a. if billgenreqno not empty
				if (ittbRecurringReq.getBmtbBillGenReq() != null
						&& ittbRecurringReq.getBmtbBillGenReq().getReqNo() != null) {
					logger.info(
							"Recurring a) Manual - BillGen ReqNo : " + ittbRecurringReq.getBmtbBillGenReq().getReqNo());

					invoices = this.daoHelper.getBillGenRequestDao()
							.getAllBmtbInvoicesByBillGenReqNo(ittbRecurringReq.getBmtbBillGenReq());
					logger.info("Manual Recurring a)found invoices : " + invoices.size());

				}
				// b. if invoiceNo from & invoiceNo to not empty
				if (ittbRecurringReq.getInvoiceNoFrom() != null) {
					logger.info("Recurring b) Manual - Invoice Nos : " + ittbRecurringReq.getInvoiceNoFrom() + " to "
							+ ittbRecurringReq.getInvoiceNoTo());

					invoices = this.daoHelper.getBillGenRequestDao().getAllBmtbInvoicesForRecurringByInvoiceNo(
							ittbRecurringReq.getInvoiceNoFrom(), ittbRecurringReq.getInvoiceNoTo());
					logger.info("Manual Recurring b)found invoices : " + invoices.size());
				}
				// c. accountNo & invoiceDate
				else if (ittbRecurringReq.getAccountNos() != null) {
					logger.info("Recurring c) Manual - Account Nos : " + ittbRecurringReq.getAccountNos()
							+ " Invoice Date : " + ittbRecurringReq.getInvoiceDate());
					List<String> accountNosList = new ArrayList();
					Iterable<String> result = Splitter.on(';').split(ittbRecurringReq.getAccountNos());
					for (String s : result) {
						accountNosList.add(s);
					}

					invoices = this.daoHelper.getBillGenRequestDao()
							.getAllBmtbInvoicesForRecurringByCustNo(accountNosList, DateUtil.convertUtilDateToSqlDate(ittbRecurringReq.getInvoiceDate()));
					logger.info("Manual Recurring c) found invoices : " + invoices.size());
				}
				// d. charging date
				else if (ittbRecurringReq.getChargingDate() != null) {
					SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
					Integer intDay = Integer.parseInt(dayFormat.format(ittbRecurringReq.getChargingDate()));
					java.sql.Date firstDate1 = DateUtil.getFirstDateOfMonth(ittbRecurringReq.getChargingDate());
					java.sql.Date endDate1 = DateUtil.getLastDateOfMonth(ittbRecurringReq.getChargingDate());

					logger.info("Recurring d) Manual - Charging Date : " + ittbRecurringReq.getChargingDate());
					List<Integer> accountNoList = new ArrayList();
					List<AmtbAccount> accountNosList = this.daoHelper.getAccountDao()
							.getAccountRecurringByChargeDay(intDay);
					for (AmtbAccount acct : accountNosList) {
						accountNoList.add(acct.getAccountNo());
					}

					invoices = this.daoHelper.getBillGenRequestDao()
//							.getAllBmtbInvoicesForRecurringByAccountNo(accountNoList, firstDate1, endDate1);
							.getAllBmtbInvoicesForRecurringByAccountNo(accountNoList, endDate1);
					logger.info("Manual Recurring d) found invoices : " + invoices.size());
				}
			}

			// do all
			if (!invoices.isEmpty()) {
				// retrieve properties value
				String recurringDirectory = (String) recurringConfigProperties.get("recurring.directory");
				String directorybackupinput = (String) recurringConfigProperties.get("recurring.directorybackupinput");
				String recurringFilename = (String) recurringConfigProperties.get("recurring.fileName");
				String recurringGpgFolder = (String) recurringConfigProperties.get("gpg.scripts.folder");
				String recurringRemote = (String) recurringConfigProperties.get("recurring.remote");
				String sftpUser = (String) recurringConfigProperties.get("ftp.userid");
				String sftpPassord = (String) recurringConfigProperties.get("ftp.password");
				int sftpPort = Integer.parseInt((String) recurringConfigProperties.get("ftp.port"));
				String hostName = (String) recurringConfigProperties.get("ftp.ip");
//				String merchantId = (String) recurringConfigProperties.get("recurring.merchantId");
//				String accountId = (String) recurringConfigProperties.get("recurring.accountId");
				String deleteAsc = (String) recurringConfigProperties.get("deleteAsc");

				RecurringConfigFTP recurringConfigFTP = new RecurringConfigFTP();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
				SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
				SimpleDateFormat yearFormat2 = new SimpleDateFormat("yy");
				SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
				Date invoiceDate = new Date();
				String monthLabelDate = monthFormat.format(invoiceDate);
				String yearLabelDate = yearFormat.format(invoiceDate);
				String yearLabelDate2 = yearFormat2.format(invoiceDate);
				String dayLabelDate = dayFormat.format(invoiceDate);
				String labelDateTime = dateFormat.format(new Date());
				List<RecurringCsvData> csvList = Collections.synchronizedList(new ArrayList<RecurringCsvData>());
				List<BmtbInvoiceHeader> successInvoices = Collections
						.synchronizedList(new ArrayList<BmtbInvoiceHeader>());

//				String csvFilePath1 = convertFileName(recurringFilename, recurringDirectory);
				String csvFilePath1 = convertFileName(recurringFilename, directorybackupinput);

				index = recurringReferencesCheck("RDP_" + yearLabelDate2 + monthLabelDate + dayLabelDate);
				
				for (BmtbInvoiceHeader invoice : invoices) {
					try {
						totalNoOfRecords++;
						if(invoice.getInvoiceStatus().equalsIgnoreCase("O"))
						{
							if (checkInvoice(invoice)) {
	
								HashMap<String, BigDecimal> rcAAmountMap = new HashMap<String, BigDecimal>();
								HashMap<String, BigDecimal> rcCAmountMap = new HashMap<String, BigDecimal>();
								HashMap<String, IttbRecurringChargeTagAcct> rcAMap = new HashMap<String, IttbRecurringChargeTagAcct>();
								HashMap<String, IttbRecurringChargeTagCard> rcCMap = new HashMap<String, IttbRecurringChargeTagCard>();
	
								IttbRecurringChargeTagAcct rcA = null;
								IttbRecurringChargeTagCard rcC = null;
								for (BmtbInvoiceSummary summary : invoice.getBmtbInvoiceSummaries()) {
									for (BmtbInvoiceDetail detail : summary.getBmtbInvoiceDetails()) {
										
										totalNoOfRecordsDetails++;
										if (detail.getOutstandingAmount().compareTo(new BigDecimal("0")) == 0) {
											totalNoOfZeroDollarSkipped++;
											continue;
										} else {
											boolean firstPriotityFound = false;
											if (detail.getPmtbProduct() != null) {
												//check if charge to self or parent
												
												AmtbAccount acct1 = checkSelfOrParent(detail.getPmtbProduct().getAmtbAccount());

												if(acct1.getRecurringFlag() != null && acct1.getRecurringFlag().equalsIgnoreCase("Y"))
												{
													rcC = this.daoHelper.getProductDao()
															.getProductsRecurringTokenByProduct(detail.getPmtbProduct());
													//process credit card first
													if (rcC != null) {
														firstPriotityFound = true;
														if (rcCAmountMap.containsKey(rcC.getRecurringChargeId().getTokenId())) {
															BigDecimal amt = (BigDecimal) rcCAmountMap
																	.get(rcC.getRecurringChargeId().getTokenId());
															amt = amt.add(detail.getOutstandingAmount());
															rcCAmountMap.put(rcC.getRecurringChargeId().getTokenId(), amt);
														} else {
															rcCMap.put(rcC.getRecurringChargeId().getTokenId(), rcC);
															rcCAmountMap.put(rcC.getRecurringChargeId().getTokenId(),
																	detail.getOutstandingAmount());
														}
													}
												}
											}
											//process account next
											if (detail.getAmtbAccount() != null && !firstPriotityFound) {
												AmtbAccount acct1 = checkSelfOrParent(detail.getAmtbAccount());
												
												if(acct1.getRecurringFlag() != null && acct1.getRecurringFlag().equalsIgnoreCase("Y"))
												{
													rcA = this.daoHelper.getAccountDao()
															.getAcctRecurringTokenByAccount(acct1);
													if (rcA != null) {
														if (rcAAmountMap.containsKey(rcA.getRecurringChargeId().getTokenId())) {
															BigDecimal amt = (BigDecimal) rcAAmountMap
																	.get(rcA.getRecurringChargeId().getTokenId());
															amt = amt.add(detail.getOutstandingAmount());
															rcAAmountMap.put(rcA.getRecurringChargeId().getTokenId(), amt);
														} else {
															rcAMap.put(rcA.getRecurringChargeId().getTokenId(), rcA);
															rcAAmountMap.put(rcA.getRecurringChargeId().getTokenId(),
																	detail.getOutstandingAmount());
														}
													}
												}
												else
												{
													logger.info("Invoice Detail skip as the account doesnt have recurring indicator");
												}
											}
	
											if (rcC == null && rcA == null) {
												totalNoOfNoToken++;
												logger.info("Invoice Detail skip as there is no Token found : DetailNo: "
														+ detail.getInvoiceDetailNo());
												continue;
												// we will skip this record as it's not tag to any token
											}
										}
									}
								}
	
								for (Map.Entry<String, IttbRecurringChargeTagAcct> entry : rcAMap.entrySet()) {
	
									IttbRecurringChargeTagAcct rcA2 = entry.getValue();
									BigDecimal totalAmountx = rcAAmountMap.get(entry.getKey());
	
									RecurringCsvData recurringCsv = new RecurringCsvData();
									String referenceBack = StringUtil.appendLeft(("" + index), 5, "0");
									recurringCsv.setReferenceId(
											"RDP_" + yearLabelDate2 + monthLabelDate + dayLabelDate + "_" + referenceBack);
	
									if (rcA2 != null) {
										recurringCsv.setCustomerToken(rcA2.getRecurringChargeId().getTokenId());
										recurringCsv.setCustomerAccountNo(getCsvAccountString(rcA2.getAmtbAccount()));
									}
	
									DecimalFormat df = new DecimalFormat("#.00");
									String totalNewAmt = df.format(totalAmountx);
									recurringCsv.setAmount(totalNewAmt);
	
									recurringCsv.setCurrency("SGD");
	//								recurringCsv.setMerchant(merchantId);
	//								recurringCsv.setAccount(accountId);
	
									recurringCsv.setInvoiceDate(dateFormat.format(invoice.getInvoiceDate()));
									recurringCsv.setInvoiceNo("" + invoice.getInvoiceNo());
	
									IttbRecurringDtl recurringDtl = new IttbRecurringDtl();
									recurringDtl.setIttbRecurringReq(ittbRecurringReq);
									recurringDtl.setReferenceId(recurringCsv.getReferenceId());
									recurringDtl.setInvoiceNo(recurringCsv.getInvoiceNo());
									recurringDtl.setAmount(totalAmountx);
									recurringDtl.setCabchargeCustNo(recurringCsv.getCustomerAccountNo());
									recurringDtl.setCabchargeCardNo(recurringCsv.getCabChargeNo());
									recurringDtl.setCurrency(recurringCsv.getCurrency());
									recurringDtl.setInvoiceDate(invoice.getInvoiceDate());
									recurringDtl.setTokenId(recurringCsv.getCustomerToken());
									this.daoHelper.getGenericDao().save(recurringDtl, "Recurring Process");
	
									if (rcC != null && rcA != null) {
										// send email
									}
	
									index++;
									totalNoOfRecordsWriteToCsv++;
									csvList.add(recurringCsv);
								}
	
								for (Map.Entry<String, IttbRecurringChargeTagCard> entry : rcCMap.entrySet()) {
									IttbRecurringChargeTagCard rcC2 = entry.getValue();
									BigDecimal totalAmountx = rcCAmountMap.get(entry.getKey());
	
									RecurringCsvData recurringCsv = new RecurringCsvData();
									String referenceBack = StringUtil.appendLeft(("" + index), 5, "0");
									recurringCsv.setReferenceId(
											"RDP_" + yearLabelDate2 + monthLabelDate + dayLabelDate + "_" + referenceBack);

									if (rcC2 != null) {
										recurringCsv.setCustomerToken(rcC2.getRecurringChargeId().getTokenId());
										recurringCsv.setCabChargeNo(rcC2.getPmtbProduct().getCardNo());
										recurringCsv.setCustomerAccountNo(
												getCsvAccountString(rcC2.getPmtbProduct().getAmtbAccount()));
									}
	
									DecimalFormat df = new DecimalFormat("#.00");
									String totalNewAmt = df.format(totalAmountx);
									recurringCsv.setAmount(totalNewAmt);
									recurringCsv.setReferenceId(recurringCsv.getReferenceId());
									recurringCsv.setCurrency("SGD");
	//								recurringCsv.setMerchant(merchantId);
	//								recurringCsv.setAccount(accountId);
	
									recurringCsv.setInvoiceDate(dateFormat.format(invoiceDate));
									recurringCsv.setInvoiceNo("" + invoice.getInvoiceNo());
	
									IttbRecurringDtl recurringDtl = new IttbRecurringDtl();
									recurringDtl.setIttbRecurringReq(ittbRecurringReq);
									recurringDtl.setReferenceId(recurringCsv.getReferenceId());
									recurringDtl.setInvoiceNo(recurringCsv.getInvoiceNo());
									recurringDtl.setAmount(totalAmountx);
									recurringDtl.setCabchargeCustNo(recurringCsv.getCustomerAccountNo());
									recurringDtl.setCabchargeCardNo(recurringCsv.getCabChargeNo());
									recurringDtl.setCurrency(recurringCsv.getCurrency());
									recurringDtl.setInvoiceDate(invoiceDate);
									recurringDtl.setTokenId(recurringCsv.getCustomerToken());
									this.daoHelper.getGenericDao().save(recurringDtl, "Recurring Process");
	
									if (rcC != null && rcA != null) {
										// send email
									}
	
									index++;
									totalNoOfRecordsWriteToCsv++;
									csvList.add(recurringCsv);
								}
	
								if (!rcCMap.isEmpty() || !rcAMap.isEmpty()) {
									successInvoices.add(invoice);
								}
							}
						}
						else{
							totalNoOfClosed++;
						}
					} catch (Exception e) {
						totalNoOfFailed++;
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						logger.info("Error : " +sw.toString());
					}
				}

				// end of print csv.
				String csvFilePath = "";
				// create csv

				RecurringCsvHelper csvHelper = new RecurringCsvHelper();
				if (!csvList.isEmpty()) {
					csvFilePath = recurringDirectory + csvFilePath1;
					for (RecurringCsvData recurringCsv : csvList) {
						csvHelper.writeToFile(recurringCsv, "", "", "", csvFilePath);
					}

					logger.info("write to excel path: " + csvFilePath);
					// replace code;
					csvHelper.replaceDelimter(csvFilePath, "\\|", "<|>");

					File f = new File(csvFilePath);
					RecurringConfigFTP.connectFTP(sftpUser, hostName, sftpPassord, sftpPort);

//					// start encrypt
//					String encryptBat = checkFilePathForSpace(recurringGpgFolder + "encrypt_os_recurring_token.bat");
//					String encryptCommand = "cmd /c " + encryptBat + " \"" + csvFilePath + "\"";
//					System.out.println("encryptCommand > " + encryptCommand);
//					logger.info("encryptCommand > " + encryptCommand);
//					logger.info("Recurring Upload Ftp Processor - Encrypt files .. " + csvFilePath);
//					Process pe = Runtime.getRuntime().exec(encryptCommand);
//					pe.waitFor();
//					logger.info("Recurring Upload encrypt successful");

					// end encrypt
//					logger.info("File upload: " + csvFilePath + ".asc");
//					logger.info("Directory: " + recurringRemote);
//					csvFileNameReturn = f.getName();
//					recurringConfig.upload(csvFilePath + ".asc", recurringRemote + f.getName() + ".asc", deleteAsc);

					logger.info("File upload: " + csvFilePath );
					logger.info("Directory: " + recurringRemote);
					fileupload++;
					csvFileNameReturn = f.getName();
					recurringConfigFTP.upload(csvFilePath , recurringRemote + f.getName(), deleteAsc);
					recurringConfigFTP.moveFileToBackUp(csvFilePath, csvFilePath1,
							directorybackupinput);

					// update successInvoices
					if (successInvoices.size() > 0) {
						for (BmtbInvoiceHeader invoicex : successInvoices) {
							if (invoicex.getRecurringDoneFlag() == null || (invoicex.getRecurringDoneFlag() != null
									&& !invoicex.getRecurringDoneFlag().equalsIgnoreCase("Y"))) {
								invoicex.setRecurringDoneFlag("Y");
								this.daoHelper.getGenericDao().update(invoicex);

							}
						}
					}
				} else {
					csvFileNameReturn = "NoInfo";
					logger.info("No Recurring Csv file created as there is no info");
				}
			}
		} catch (Exception e) {
			csvFileNameReturn = "Error";
			logger.info("Error at Upload Recurring > "+e);
			e.printStackTrace();
		}finally {
			EmailUtil.sendGenericRecurringNotificationComplete(ConfigurableConstants.EMAIL_TYPE_RECURRING_COMPLETED,recurringFrom, fileupload);
		}
		stats[0] = totalNoOfRecords;
		stats[1] = totalNoOfRecordsWriteToCsv;
		stats[2] = totalNoOfNoToken;
		stats[3] = totalNoOfFailed;
		stats[4] = totalNoOfZeroDollarSkipped;
		stats[5] = totalNoOfRecordsDetails;
		stats[6] = totalNoOfClosed;

		return csvFileNameReturn;
	}

	public Boolean checkInvoice(BmtbInvoiceHeader invoice) {

		if (invoice != null) {
			AmtbAccount account = invoice.getAmtbAccountByAccountNo();
			if (("Y".equals(account.getRecurringFlag()))
					&& "Y".equals(invoice.getAmtbAccountByAccountNo().getRecurringFlag())) {
				return true;
			}

			return false;

		}
		return false;
	}

	public String checkFilePathForSpace(String path) {

		String trimSpace = "";
		Iterable<String> result0 = Splitter.on("/").split(path);

		int i = 0;
		for (String s0 : result0) {

			if (i != 0)
				trimSpace = trimSpace + "/";
			if (s0.contains(" "))
				trimSpace = trimSpace + "\"" + s0 + "\"";
			else
				trimSpace = trimSpace + s0;

			i++;
		}

		return trimSpace;
	}

	public String getCsvAccountString(AmtbAccount acct) {
		String accountNo = "";

		if (acct != null) {
			AmtbAccount topLvlAccount = acct;
			String code1 = "";
			String code2 = "";
			int i = 0;

			while (topLvlAccount.getAmtbAccount() != null) {
				i++;
				if (i == 1)
					code1 = topLvlAccount.getCode();
				if (i == 2)
					code2 = topLvlAccount.getCode();

				topLvlAccount = topLvlAccount.getAmtbAccount();
			}

			accountNo = topLvlAccount.getCustNo() + code2 + code1;

		}

		return accountNo;
	}

	public String downloadAndProcessRecurringDtl(int[] stats) {
		//Testing use
//		RecurringConfig recurringConfig = new RecurringConfig();
//		Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");
//		String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");
//		String failInvoice = "";
//		String successInvoice = "";
//		int totalNoOfRecords = 0;
//		int totalNoOfSuccessProcess = 0;
//		int totalNoOfFailed = 0;
//		
//		String referenceId = "",  invoiceNo = "", amount = "", custNo ="", cabChargeNo = "", error = "";
//
//		totalNoOfFailed++;
//		referenceId = "RDP_0001" ; invoiceNo = "8392742" ; amount = "23.55" ; custNo = "123450001"; cabChargeNo = "839251237823758"; error = "error lo";
//		failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t " +error +" <br>";
//
//		totalNoOfFailed++;
//		referenceId = "RDP_0002" ; invoiceNo = "8392742" ; amount = "63.55" ; custNo = "123450001"; cabChargeNo = "839251237823758"; error = "Spoil aggga";
//		failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t " +error +" <br>";
//
//		totalNoOfSuccessProcess++;
//		referenceId = "RDP_0003" ; invoiceNo = "8392140" ; amount = "65.55" ; custNo = "123450001"; cabChargeNo = "339251237823752";
//		successInvoice += totalNoOfSuccessProcess + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" <br>";
//
//		totalNoOfSuccessProcess++;
//		referenceId = "RDP_0004" ; invoiceNo = "8392743" ; amount = "43.55" ; custNo = "123450001"; cabChargeNo = "339251237823752";
//		successInvoice += totalNoOfSuccessProcess + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" <br>";
//
//		this.sendRecurringNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_INVOICE,
//			recurringFrom, failInvoice, successInvoice , totalNoOfSuccessProcess , totalNoOfFailed);
		
		Map<String, String> entityMap = new HashMap<String, String>();
		RecurringFileHandler recurringFileHandler = null;
		String failInvoice = "";
		String successInvoice = "";
				
		String referenceId = null;
		String tokenId = null;
		String amount = null;
		String currency = null;
		String merchantId = null;
		String accountId = null;
		String invoiceDate = null;
		String invoiceNo = null;
		String custNo = null;
		String cabChargeNo = null;
		String state = null;
		String error = null;
		int totalNoOfRecords = 0;
		int totalNoOfSuccessProcess = 0;
		int totalNoOfFailed = 0;
		List<String> failReccuringInvoiceList = new ArrayList<String>();

		int filedownload = 0;
		
		RecurringConfigFTP recurringConfigFTP = new RecurringConfigFTP();
		Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");
		String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");

		EmailUtil.sendGenericNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_DOWNLOAD_START,recurringFrom);
		
		try {
//			String recurringGpgFolder = (String) recurringConfigProperties.get("gpg.scripts.folder");
			String sftpUser = (String) recurringConfigProperties.get("ftp.userid");
			String sftpPassord = (String) recurringConfigProperties.get("ftp.password");
			int sftpPort = Integer.parseInt((String) recurringConfigProperties.get("ftp.port"));
			String hostName = (String) recurringConfigProperties.get("ftp.ip");
			String localRecurringDir = (String) recurringConfigProperties.get("localRecurringDir");
			String remoteRecurringDir = (String) recurringConfigProperties.get("remoteRecurringDir");
			String fileToProcess = (String) recurringConfigProperties.get("fileToProcess");
			String localBackUplocalRecurringDir = (String) recurringConfigProperties
					.get("localBackUplocalRecurringDir");
			String sftpArchivedRecurringDir = (String) recurringConfigProperties
					.get("sftpArchivedRecurringDir");
			String localBackUpRecurring = (String) recurringConfigProperties
					.get("localBackUpRecurring");

			MstbMasterTable master = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.OTHERS_TYPE_RECURRING_BANK_IN,
							"CONT");
			Integer bankInNo = Integer.parseInt(master.getMasterValue());
			
			if(bankInNo == null)
				logger.info("Error, BankInNo is null");
			
			String fileName = (String) recurringConfigProperties.get("recurring.fileName");
			String prefix1 = fileToProcess.substring(4, fileName.indexOf("-{1}"));
			String prefix2 = fileToProcess.substring(fileName.indexOf("{1}") + 4);
			// secretKey = c.getConfig().getProperty("ftp.secretKey");
			RecurringConfigFTP.connectFTP(sftpUser, hostName, sftpPassord, sftpPort);

			recurringConfigFTP.downloadFilesInDirectory(remoteRecurringDir, localRecurringDir, prefix1, prefix2);
		
	//		recurringConfig.moveSFTPFilesToArchived(remoteRecurringDir, localRecurringDir, prefix1, prefix2);
			recurringConfigFTP.disconnect();
			
			// this part onwards , need to cater for looping of files in directory instead
			// of filename
			File directoryToDecrypt = new File(localRecurringDir);
			for (File files : directoryToDecrypt.listFiles()) {
//				if (files.getName().contains(prefix1) && files.getName().contains(prefix2)) {
//
//					String decryptBat = this
//							.checkFilePathForSpace(recurringGpgFolder + "decrypt_os_recurring_token.bat");
//					String decryptCommand = "cmd /c " + decryptBat + " \"" + localRecurringDir + files.getName() + "\"";
//					System.out.println("decryptCommand > " + decryptCommand);
//					logger.info("decryptCommand > " + decryptCommand);
//					// logger.info("Recurring Download Decrypt Ftp Processor - Decrypt files .. " +
//					// localPath);
//					Process pe = Runtime.getRuntime().exec(decryptCommand);
//
//					BufferedReader stdInput = new BufferedReader(new InputStreamReader(pe.getInputStream()));
//					BufferedReader stdError = new BufferedReader(new InputStreamReader(pe.getErrorStream()));
//					// Read the output from the command
//					System.out.println("Here is the standard output of the command:\n");
//					String s = null;
//					while ((s = stdInput.readLine()) != null) {
//						System.out.println(s);
//					}
//
//					System.out.println("Here is the standard error of the command (if any):\n");
//					while ((s = stdError.readLine()) != null) {
//						System.out.println(s);
//					}
//					logger.info("Recurring Download decrypt successful");
//					pe.waitFor();
					
					if (files.isDirectory()) {
						continue;
					}
					// RecurringConfig.load();
					// Record the processing start date time
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					String labelDateTime = dateFormat.format(new Date());

					Calendar cal2 = Calendar.getInstance();
					cal2.add(Calendar.MONTH, -1);
					cal2.set(Calendar.HOUR_OF_DAY, 23);
					cal2.set(Calendar.MINUTE, 59);
					cal2.set(Calendar.SECOND, 59);
					cal2.set(Calendar.MILLISECOND, 0);
					cal2.set(Calendar.DATE, cal2.getActualMaximum(Calendar.DATE));

					Date endDate = new Timestamp(cal2.getTimeInMillis());
					// String fileName = files.getName().replace(".asc", "");
					String csvFilePath1 = localRecurringDir + files.getName();
					boolean alreadyExists = new File(csvFilePath1).exists();
				
					if(!files.getName().contains(".asc") && files.getName().contains(prefix1) && files.getName().contains(prefix2)) 
					{
						if (alreadyExists) 
						{
							try
							{
		//						recurringFileHandler = new RecurringFileHandler(localRecurringDir,
		//								files.getName().replace(".asc", ""));
								recurringFileHandler = new RecurringFileHandler(localRecurringDir,
										files.getName());
								
								recurringFileHandler.open();
								filedownload++;
								logger.info("Read Entire File...");
								List<String> linesOfData = recurringFileHandler.readEntireFile();
		
								String[] aa2 = linesOfData.get(1).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
								IttbRecurringDtl recurringDtl2 = new IttbRecurringDtl();
								String referenceId2 = aa2[0];
								String invoiceNo2 = aa2[5];
								String tokenId2 = aa2[1];
								
								recurringDtl2 = this.daoHelper.getAdminDao().getRecurringDtlwInvoice(referenceId2, invoiceNo2, tokenId2);
		
								if (recurringDtl2 == null || recurringDtl2.getIttbRecurringReq() == null) {
									logger.info("Error, cant find recurring Dtl");
								} else {
									IttbRecurringReq req = recurringDtl2.getIttbRecurringReq();
	
									for (int i = 1; i < linesOfData.size(); i++) {
										totalNoOfRecords++;
										String[] aa = linesOfData.get(i).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
										referenceId = aa[0];
										tokenId = aa[1];
										amount = aa[2];
										currency = aa[3];
		//								merchantId = aa[4];
		//								accountId = aa[5];
										invoiceDate = aa[4];
										invoiceNo = aa[5];
										custNo = aa[6];
										try {
											if (aa[7] != null)
												cabChargeNo = aa[7];
											else
												cabChargeNo = "";
										} catch (ArrayIndexOutOfBoundsException e) {
											cabChargeNo = "";
										}
										
										try {
											if (aa[8] != null)
												state = aa[8];
											else
												state = "";
										} catch (ArrayIndexOutOfBoundsException e) {
											state = "";
										}
										try {
											if (aa[9] != null)
												error = aa[9];
											else
												error = "";
										} catch (ArrayIndexOutOfBoundsException e) {
											error = "";
										}
		
										PmtbProduct prod = null;
										AmtbAccount account = null;
										logger.info("Re-validate...");
										IttbRecurringDtl recurringDtl = this.daoHelper.getAdminDao()
												.getRecurringDtlwInvoice(aa[0], aa[5], aa[1]);
		
		//								if (state != null && !state.trim().equalsIgnoreCase("") && 
		//										error != null && !error.trim().equalsIgnoreCase("")	) {
										if ( !state.trim().equalsIgnoreCase("capture")) {
											recurringDtl.setStatus(state);
											recurringDtl.setError(error);
											totalNoOfFailed++;
//											failInvoice += invoiceNo + " (Reason: "+error+") ,";
											failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" <br>";
											
											failReccuringInvoiceList.add(invoiceNo);
											this.daoHelper.getGenericDao().save(recurringDtl, "SFTP AUTO PROCESS");
										} else {
											try
											{
												logger.info("Record "+referenceId +"  invoice: "+invoiceNo +" amount: "+amount);
												
												if (custNo != null && custNo != "") {
													account = getAccount(custNo);
												}
			
												if (cabChargeNo != null && cabChargeNo != "") {
													prod = this.daoHelper.getProductDao().getProductByCard(cabChargeNo);
												}
												ProductSearchCriteria searchForm = new ProductSearchCriteria();
												searchForm.setTokenId(tokenId);
												List<IttbRecurringCharge> record = this.daoHelper.getAccountDao()
														.searchRC(searchForm);
												if (record == null || record.size() == 0) {
													recurringDtl.setStatus("");
													recurringDtl.setError("IBS Error : Token Not Found");
													this.daoHelper.getGenericDao().save(recurringDtl, "SFTP AUTO PROCESS");
													logger.info("Token not found : " + tokenId);
													totalNoOfFailed++;
//													failInvoice += invoiceNo + " (Token not Found) ,";
													failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" (Token not Found) <br>";
													
													failReccuringInvoiceList.add(invoiceNo);
													continue;
												}
			
												// if invoice closed, skip
												BmtbInvoiceHeader ih = this.daoHelper.getInvoiceDao()
														.getInvoiceByInvoiceNo(Long.parseLong(invoiceNo));
			
												if (ih.getInvoiceStatus().equals(NonConfigurableConstants.INVOICE_STATUS_CLOSED)) {
													logger.info("Skipping invoiceNo: " + ih.getInvoiceNo()
															+ " invoice is already closed");
													recurringDtl.setStatus("");
													recurringDtl.setError("IBS Error : Invoice is already Closed");
													this.daoHelper.getGenericDao().save(recurringDtl, "SFTP AUTO PROCESS");
			
													totalNoOfFailed++;
//													failInvoice += invoiceNo + " (Invoice Closed) ,";
													failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" (Invoice Closed) <br>";
													
													continue;
												}
			
												BigDecimal totalAmountx = new BigDecimal(amount);
												List<InvoicePaymentDetail> invoicePaymentDetails = new ArrayList<InvoicePaymentDetail>();
												InvoicePaymentDetail paymentDetail = new InvoicePaymentDetail(ih);
												HashMap<Long, BigDecimal> invoiceDetailAppliedAmountMap = new HashMap<Long, BigDecimal>();
												HashMap<Long, BmtbInvoiceDetail> invoiceDetailAppliedMap = new HashMap<Long, BmtbInvoiceDetail>();
			
												//1. check prod for exact os amount
												if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
												{
													for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
														for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
						
															if (invoiceDetail.getPmtbProduct() != null && prod != null
																	&& invoiceDetail.getPmtbProduct().getProductNo().equals(prod.getProductNo())) {
						
																if(totalAmountx.compareTo(invoiceDetail.getOutstandingAmount()) == 0)
																{
																	BigDecimal totalAmountx2 = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
																	if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
																		totalAmountx = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
																		invoiceDetailAppliedAmountMap.put(invoiceDetail.getInvoiceDetailNo(),
																				invoiceDetail.getOutstandingAmount());
																		invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
																				invoiceDetail);
																	}
																}
															}
														}
													}
												}
												//2. check acct for exact os amount
												if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
												{
													for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
														for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
						
															if (invoiceDetail.getAmtbAccount() != null && account != null
																	&& account.getAccountNo().equals(invoiceDetail.getAmtbAccount().getAccountNo())) {
																if(totalAmountx.compareTo(invoiceDetail.getOutstandingAmount()) == 0)
																{
																	BigDecimal totalAmountx2 = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
																	if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
																		totalAmountx = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
																		invoiceDetailAppliedAmountMap.put(invoiceDetail.getInvoiceDetailNo(),
																				invoiceDetail.getOutstandingAmount());
																		invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
																				invoiceDetail);
																	}
																}
															}
														}
													}
												}
												//3. check prod for not exact amount
												if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
												{
													for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
														for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
									
															if (invoiceDetail.getPmtbProduct() != null && prod != null
																	&& invoiceDetail.getPmtbProduct().getProductNo().equals(prod.getProductNo())) {
				
																BigDecimal totalAmountx2 = totalAmountx
																		.subtract(invoiceDetail.getOutstandingAmount());
																if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
																	 
																	totalAmountx = totalAmountx
																				.subtract(invoiceDetail.getOutstandingAmount());
																	invoiceDetailAppliedAmountMap.put(
																			invoiceDetail.getInvoiceDetailNo(),
																			invoiceDetail.getOutstandingAmount());
																	invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
																			invoiceDetail);
																	
																	logger.info("Adding for Prod DetailNo: "+invoiceDetail.getInvoiceDetailNo()+" . OS : "+invoiceDetail.getOutstandingAmount());
																}
															}
														}
													}
												}
												//4. check prod for not exact amount
												if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
												{
													for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
														for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
									
														 if (invoiceDetail.getAmtbAccount() != null && account != null
																	&& account.getAccountNo().equals(invoiceDetail.getAmtbAccount().getAccountNo())) {
																BigDecimal totalAmountx2 = totalAmountx
																		.subtract(invoiceDetail.getOutstandingAmount());
																if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
																	totalAmountx = totalAmountx
																			.subtract(invoiceDetail.getOutstandingAmount());
																	invoiceDetailAppliedAmountMap.put(
																			invoiceDetail.getInvoiceDetailNo(),
																			invoiceDetail.getOutstandingAmount());
																	invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
																			invoiceDetail);
																	
																	logger.info("Adding for Acct DetailNo: "+invoiceDetail.getInvoiceDetailNo()+" . OS : "+invoiceDetail.getOutstandingAmount());
																	
																}
															}
														}
													}
												}
												paymentDetail.setInvoiceHeader(ih);
												paymentDetail.setInvoiceDetailApplied(invoiceDetailAppliedMap);
												paymentDetail.setInvoiceDetailAppliedAmount(invoiceDetailAppliedAmountMap);
												//logger.info("size of InvoiceDetail Map : "+invoiceDetailAppliedMap.size() + " invoiceDetailAppliedAmountMap > "+invoiceDetailAppliedAmountMap.size());
												invoicePaymentDetails.add(paymentDetail);
			
												BigDecimal paymentAmount = new BigDecimal(amount);
												PaymentInfo paymentInfo = recurringBuildPaymentInfo(custNo, paymentAmount, bankInNo);
												
												logger.info("Processing A Payment - Start : Record "+referenceId +"  invoice: "+invoiceNo +" amount: "+amount);
												Long receiptNo = this.businessHelper.getPaymentBusiness().createPaymentReceipt(paymentInfo,
														invoicePaymentDetails, "IBS RedDot");
												logger.info("Processing A Payment receiptNo : "+receiptNo+" - End");
												
												recurringDtl.setIttbRecurringReq(req);
												recurringDtl.setInvoiceNo(invoiceNo);
												recurringDtl.setAmount(new BigDecimal(amount));
												recurringDtl.setCabchargeCustNo(custNo);
												if (cabChargeNo != null) {
													recurringDtl.setCabchargeCardNo(cabChargeNo);
												}
												recurringDtl.setCurrency(currency);
												recurringDtl.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").parse(invoiceDate));
												recurringDtl.setTokenId(tokenId);
												recurringDtl.setStatus(state);
												recurringDtl.setError(error);
												this.daoHelper.getGenericDao().save(recurringDtl, "Recurring Process");
												totalNoOfSuccessProcess++;
												successInvoice += totalNoOfSuccessProcess + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" <br>";
											}
											catch(Exception e)
											{
												logger.info("Error Reference : "+ referenceId  +" Error Message : "+e.getMessage());
												failReccuringInvoiceList.add(invoiceNo);
												totalNoOfFailed++;
//												failInvoice += invoiceNo + " (Error),";
												failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" (Error) <br>";

												throw new Exception("Error Reference : "+ referenceId  +" Error Message : "+e.getMessage());
											}
										}
									}
									
									req.setReturnFileName(files.getName());
									req.setReturnFileUploadDt(new Timestamp(System.currentTimeMillis()));
									req.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_UPLOADED);
									this.daoHelper.getGenericDao().save(req, "Recurring Auto PROCESS");
		
									this.sendRecurringNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_INVOICE,
											recurringFrom, failInvoice, successInvoice , totalNoOfSuccessProcess , totalNoOfFailed);
									recurringFileHandler.close();
									if(localBackUpRecurring.equals("true")) {
										CSVUtil.moveFileToBackUp("", localRecurringDir + files.getName(), files.getName(),
												localBackUplocalRecurringDir);
										files.delete();
		//								CSVUtil.moveFileToBackUp("", localRecurringDir + files.getName().replace(".asc", ""), files.getName().replace(".asc", ""),
		//										localBackUplocalRecurringDir);
									}else {
										files.delete();
		//								File decryptedFile = new File(localRecurringDir+files.getName().replace(".asc", ""));
		//								decryptedFile.delete();
									}

								}
							}
							catch(Exception e)
							{
								logger.info("Errorrr : "+e);
							}
							finally {
								try {
									RecurringConfigFTP.connectFTP(sftpUser, hostName, sftpPassord, sftpPort);
									recurringConfigFTP.moveSFTPFilesToArchived(remoteRecurringDir, sftpArchivedRecurringDir, files.getName());
									recurringConfigFTP.disconnect();
								}
								catch(Exception e)
								{
									logger.info("Error moving file to archive. Message : "+e);
								}
							}
						}
					}
			}
		} catch (Exception e) {
			logger.info("Errorr : "+e);
			e.printStackTrace();
		}
		
		//set back to "N"
		if(!failReccuringInvoiceList.isEmpty())
		{
			try
			{
				for(String failInvoices : failReccuringInvoiceList)
				{
					 BmtbInvoiceHeader failBmtbInvoice = this.daoHelper.getInvoiceDao().getInvoice(failInvoices);
					 
					 //invoice not close then set the recurringflag back to N
					 if(!failBmtbInvoice.getInvoiceStatus().equalsIgnoreCase("C"))
					 {
						 failBmtbInvoice.setRecurringDoneFlag("N");
						 this.daoHelper.getInvoiceDao().update(failBmtbInvoice);
					 }
				}
			}
			catch(Exception e)
			{
				logger.info("Error at setting invoices back to N for list > "+failReccuringInvoiceList +" . Error : "+e);
			}
		}
		EmailUtil.sendGenericRecurringNotificationComplete(ConfigurableConstants.EMAIL_TYPE_RECURRING_DOWNLOAD_COMPLETED,recurringFrom, filedownload);
		
		stats[0] = totalNoOfRecords;
		stats[1] = totalNoOfSuccessProcess;
		stats[2] = totalNoOfFailed;

		return "Success";
	}

	public void uploadAndProcessRecurringReturnFile(IttbRecurringReq request,
			Map<String, List<String>> listOfUploadedFiles) {
		Map<String, String> entityMap = new HashMap<String, String>();
		String failInvoice = "";
		String successInvoice = "";

		int totalNoOfSuccessProcess = 0;
		int totalNoOfFailed = 0;
		
		String referenceId = null;
		String tokenId = null;
		String amount = null;
		String currency = null;
		String merchantId = null;
		String accountId = null;
		String invoiceDate = null;
		String invoiceNo = null;
		String custNo = null;
		String cabChargeNo = null;
		String state = null;
		String error = null;

		RecurringConfigFTP recurringConfigFTP = new RecurringConfigFTP();
		Map recurringConfigProperties = (Map) SpringUtil.getBean("recurringConfigProperties");
		String recurringFrom = (String) recurringConfigProperties.get("recurringEmailFrom");
		List<String> failReccuringInvoiceList = new ArrayList<String>();
		
		EmailUtil.sendGenericNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_DOWNLOAD_START,recurringFrom);
		
		try {
			String localRecurringDir = (String) recurringConfigProperties.get("localRecurringDir");
			String fileToProcess = (String) recurringConfigProperties.get("fileToProcess");
			String localBackUplocalRecurringDir = (String) recurringConfigProperties
					.get("localBackUplocalRecurringDir");
			// secretKey = c.getConfig().getProperty("ftp.secretKey");

			MstbMasterTable master = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.OTHERS_TYPE_RECURRING_BANK_IN,
							"CONT");
			Integer bankInNo = Integer.parseInt(master.getMasterValue());
		
			if(bankInNo == null)
				logger.info("Error, BankInNo is null");
			
			for (Entry<String, List<String>> entry : listOfUploadedFiles.entrySet()) {

				String fileName = entry.getKey();
				List<String> linesOfData = entry.getValue();

				// RecurringConfig.load();
				// Record the processing start date time
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String labelDateTime = dateFormat.format(new Date());

				logger.info("Read Entire File...");
				String[] aa2 = linesOfData.get(1).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				IttbRecurringDtl recurringDtl2 = new IttbRecurringDtl();
				String referenceId2 = aa2[0];
				String invoiceNo2 = aa2[5];
				String tokenId2 = aa2[1];
				
				recurringDtl2 = this.daoHelper.getAdminDao().getRecurringDtlwInvoice(referenceId2, invoiceNo2, tokenId2);

				if (recurringDtl2 == null || recurringDtl2.getIttbRecurringReq() == null) {
					logger.info("Error, cant find recurring Dtl");
				} else {
					IttbRecurringReq req = recurringDtl2.getIttbRecurringReq();

					for (int i = 1; i < linesOfData.size(); i++) {
						String[] aa = linesOfData.get(i).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
						referenceId = aa[0];
						tokenId = aa[1];
						amount = aa[2];
						currency = aa[3];
//						merchantId = aa[4];
//						accountId = aa[5];
						invoiceDate = aa[4];
						invoiceNo = aa[5];
						custNo = aa[6];
						try {
							if (aa[7] != null)
								cabChargeNo = aa[7];
							else
								cabChargeNo = "";
						} catch (ArrayIndexOutOfBoundsException e) {
							cabChargeNo = "";
						}
						
						try {
							if (aa[8] != null)
								state = aa[8];
							else
								state = "";
						} catch (ArrayIndexOutOfBoundsException e) {
							state = "";
						}
						try {
							if (aa[9] != null)
								error = aa[9];
							else
								error = "";
						} catch (ArrayIndexOutOfBoundsException e) {
							error = "";
						}
						PmtbProduct prod = null;
						AmtbAccount account = null;
						logger.info("Re-validate...");

						IttbRecurringDtl recurringDtl = this.daoHelper.getAdminDao().getRecurringDtlwInvoice(aa[0],
								aa[5], aa[1]);

//						if (state != null && !state.trim().equalsIgnoreCase("") &&
//								error != null && !error.trim().equalsIgnoreCase("")) {
						if (!state.trim().equalsIgnoreCase("capture")) {
							recurringDtl.setStatus(state);
							recurringDtl.setError(error);
							totalNoOfFailed++;
//							failInvoice += invoiceNo + " (Reason: "+error+") ,";
							failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" <br>";
							failReccuringInvoiceList.add(invoiceNo);
							this.daoHelper.getGenericDao().save(recurringDtl, "SFTP MANUAL PROCESS");
						} else {
							try
							{
								if (custNo != null && custNo != "") {
									account = getAccount(custNo);
								}
	
								if (cabChargeNo != null && cabChargeNo != "") {
									prod = this.daoHelper.getProductDao().getProductByCard(cabChargeNo);
								}
								ProductSearchCriteria searchForm = new ProductSearchCriteria();
								searchForm.setTokenId(tokenId);
								List<IttbRecurringCharge> record = this.daoHelper.getAccountDao().searchRC(searchForm);
								if (record == null || record.size() == 0) {
									recurringDtl.setStatus("");
									recurringDtl.setError("IBS Error : Token Not Found");
									this.daoHelper.getGenericDao().save(recurringDtl, "SFTP MANUAL PROCESS");
									logger.info("Token not found : " + tokenId);
									failReccuringInvoiceList.add(invoiceNo);
									totalNoOfFailed++;
//									failInvoice += invoiceNo + " (Token not found) ,";
									failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" (Token not Found) <br>";
									continue;
								}
	
								// if invoice closed, skip
								BmtbInvoiceHeader ih = this.daoHelper.getInvoiceDao()
										.getInvoiceByInvoiceNo(Long.parseLong(invoiceNo));
	
								if (ih.getInvoiceStatus().equals(NonConfigurableConstants.INVOICE_STATUS_CLOSED)) {
									logger.info("Skipping invoiceNo: " + ih.getInvoiceNo() + " invoice is already closed");
	
									recurringDtl.setStatus("");
									recurringDtl.setError("IBS Error : Invoice is already Closed");
									this.daoHelper.getGenericDao().save(recurringDtl, "SFTP MANUAL PROCESS");
									totalNoOfFailed++;
//									failInvoice += invoiceNo + " (Invoice closed),";
									failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" (Invoice Closed) <br>";
									
									continue;
								}
	
								BigDecimal totalAmountx = new BigDecimal(amount);
								List<InvoicePaymentDetail> invoicePaymentDetails = new ArrayList<InvoicePaymentDetail>();
								InvoicePaymentDetail paymentDetail = new InvoicePaymentDetail(ih);
								HashMap<Long, BigDecimal> invoiceDetailAppliedAmountMap = new HashMap<Long, BigDecimal>();
								HashMap<Long, BmtbInvoiceDetail> invoiceDetailAppliedMap = new HashMap<Long, BmtbInvoiceDetail>();
	
								//1. check prod for exact os amount
								if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
								{
									for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
										for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
		
											if (invoiceDetail.getPmtbProduct() != null && prod != null
													&& invoiceDetail.getPmtbProduct().getProductNo().equals(prod.getProductNo())) {
		
												if(totalAmountx.compareTo(invoiceDetail.getOutstandingAmount()) == 0)
												{
													BigDecimal totalAmountx2 = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
													if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
														totalAmountx = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
														invoiceDetailAppliedAmountMap.put(invoiceDetail.getInvoiceDetailNo(),
																invoiceDetail.getOutstandingAmount());
														invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
																invoiceDetail);
													}
												}
											}
										}
									}
								}
								//2. check acct for exact os amount
								if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
								{
									for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
										for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
		
											if (invoiceDetail.getAmtbAccount() != null && account != null
													&& account.getAccountNo().equals(invoiceDetail.getAmtbAccount().getAccountNo())) {
												if(totalAmountx.compareTo(invoiceDetail.getOutstandingAmount()) == 0)
												{
													BigDecimal totalAmountx2 = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
													if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
														totalAmountx = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
														invoiceDetailAppliedAmountMap.put(invoiceDetail.getInvoiceDetailNo(),
																invoiceDetail.getOutstandingAmount());
														invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
																invoiceDetail);
													}
												}
											}
										}
									}
								}
								//3. check prod for not exact..
								if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
								{
									for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
										for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
		
											if (invoiceDetail.getPmtbProduct() != null && prod != null
													&& invoiceDetail.getPmtbProduct().getProductNo().equals(prod.getProductNo())) {
		
												BigDecimal totalAmountx2 = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
												if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
													totalAmountx = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
													invoiceDetailAppliedAmountMap.put(invoiceDetail.getInvoiceDetailNo(),
															invoiceDetail.getOutstandingAmount());
													invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
															invoiceDetail);
												}
											}
										}
									}
								}
								//4. check acct for not exact..
								if(totalAmountx.compareTo(new BigDecimal("0")) > 0)
								{
									for (BmtbInvoiceSummary invoiceSummary : ih.getBmtbInvoiceSummaries()) {
										for (BmtbInvoiceDetail invoiceDetail : invoiceSummary.getBmtbInvoiceDetails()) {
		
											if (invoiceDetail.getAmtbAccount() != null && account != null
													&& account.getAccountNo().equals(invoiceDetail.getAmtbAccount().getAccountNo())) {
												BigDecimal totalAmountx2 = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
												if (totalAmountx2.compareTo(new BigDecimal("0")) >= 0) {
													totalAmountx = totalAmountx.subtract(invoiceDetail.getOutstandingAmount());
													invoiceDetailAppliedAmountMap.put(invoiceDetail.getInvoiceDetailNo(),
															invoiceDetail.getOutstandingAmount());
													invoiceDetailAppliedMap.put(invoiceDetail.getInvoiceDetailNo(),
															invoiceDetail);
												}
											}
										}
									}
								}
								paymentDetail.setInvoiceHeader(ih);
								paymentDetail.setInvoiceDetailApplied(invoiceDetailAppliedMap);
								paymentDetail.setInvoiceDetailAppliedAmount(invoiceDetailAppliedAmountMap);
								invoicePaymentDetails.add(paymentDetail);
	
								BigDecimal paymentAmount = new BigDecimal(amount);
								PaymentInfo paymentInfo = recurringBuildPaymentInfo(custNo, paymentAmount, bankInNo);

								logger.info("Processing M Payment - Start : Record "+referenceId +"  invoice: "+invoiceNo +" amount: "+amount);
								Long receiptNo = this.businessHelper.getPaymentBusiness().createPaymentReceipt(paymentInfo,
										invoicePaymentDetails, "IBS RedDot");
								logger.info("Processing M Payment receiptNo : "+receiptNo+" - End");
								recurringDtl.setIttbRecurringReq(req);
								recurringDtl.setInvoiceNo(invoiceNo);
								recurringDtl.setAmount(new BigDecimal(amount));
								recurringDtl.setCabchargeCustNo(custNo);
								if (cabChargeNo != null) {
									recurringDtl.setCabchargeCardNo(cabChargeNo);
								}
								recurringDtl.setCurrency(currency);
								recurringDtl.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").parse(invoiceDate));
								recurringDtl.setTokenId(tokenId);
								recurringDtl.setStatus(state);
								recurringDtl.setError(error);
								this.daoHelper.getGenericDao().save(recurringDtl, "Recurring Manual Process");
								totalNoOfSuccessProcess++;
//								successInvoice += invoiceNo + " ,";
								successInvoice += totalNoOfSuccessProcess + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" <br>";
							}
							catch(Exception e)
							{
								logger.info("Error Reference : "+ referenceId  +" Error Message : "+e.getMessage());
//								failInvoice += invoiceNo + " (Error),";
								totalNoOfFailed++;
								failInvoice += totalNoOfFailed + " \t\t "+ referenceId +" \t\t "+invoiceNo + " \t\t "+ amount + " \t\t "+custNo + " \t\t "+cabChargeNo +" \t\t "+error+" (Error) <br>";
								failReccuringInvoiceList.add(invoiceNo);
								throw new Exception("Error Reference : "+ referenceId  +" Error Message : "+e.getMessage());
							}
						}
					}

					req.setReturnFileName(fileName);
					req.setReturnFileUploadDt(new Timestamp(System.currentTimeMillis()));
					req.setStatus(NonConfigurableConstants.RECURRING_REQUEST_STATUS_UPLOADED);
					req.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
					req.setUpdatedDt(DateUtil.getCurrentTimestamp());
					this.daoHelper.getGenericDao().save(req, "Recurring PROCESS");

					this.sendRecurringNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_INVOICE, recurringFrom,
							failInvoice, successInvoice, totalNoOfSuccessProcess, totalNoOfFailed);
//					CSVUtil.moveFileToBackUp(labelDateTime, localRecurringDir + fileToProcess, fileToProcess,
//							localBackUplocalRecurringDir);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//set back to "N"
		if(!failReccuringInvoiceList.isEmpty())
		{
			try
			{
				for(String failInvoices : failReccuringInvoiceList)
				{
					 BmtbInvoiceHeader failBmtbInvoice = this.daoHelper.getInvoiceDao().getInvoice(failInvoices);
					 
					 //invoice not close then set the recurringflag back to N
					 if(!failBmtbInvoice.getInvoiceStatus().equalsIgnoreCase("C"))
					 {
						 failBmtbInvoice.setRecurringDoneFlag("N");
						 this.daoHelper.getInvoiceDao().update(failBmtbInvoice);
					 }
				}
			}
			catch(Exception e)
			{
				logger.info("Error at setting invoices back to N for list > "+failReccuringInvoiceList +" . Error : "+e);
			}
		}		
		
		EmailUtil.sendGenericNotification(ConfigurableConstants.EMAIL_TYPE_RECURRING_DOWNLOAD_COMPLETED,recurringFrom);
	}

	public AmtbAccount getAccount(String originalCustNo) {
		// 1. get Account
		AmtbAccount account = null;
		// depending on size,
		// if size 5 or 6 = custno
		// if size 9 or 10 = custno + div
		// if size 13 or 14 = custno + div + dept
		String originalCustomerNo = originalCustNo.trim();
		String custNo = "";
		String divCode = "";
		String deptCode = "";
		try {
			if (originalCustomerNo.length() == 5 || originalCustomerNo.length() == 9
					|| originalCustomerNo.length() == 13)
				custNo = originalCustNo.substring(0, 5);
			else if (originalCustomerNo.length() == 6 || originalCustomerNo.length() == 10
					|| originalCustomerNo.length() == 14)
				custNo = originalCustNo.substring(0, 6);
			else if (originalCustomerNo.length() == 3 || originalCustomerNo.length() == 7
					|| originalCustomerNo.length() == 11)
				custNo = originalCustNo.substring(0, 3);
			else
				throw new Exception("Invalid account");

			if (originalCustomerNo.length() == 9 || originalCustomerNo.length() == 13)
				divCode = originalCustNo.substring(5, 9);
			else if (originalCustomerNo.length() == 10 || originalCustomerNo.length() == 14)
				divCode = originalCustNo.substring(6, 10);
			else if (originalCustomerNo.length() == 7 || originalCustomerNo.length() == 12)
				divCode = originalCustNo.substring(3, 7);
			if (originalCustomerNo.length() == 13)
				deptCode = originalCustNo.substring(9, 13);
			else if (originalCustomerNo.length() == 14)
				deptCode = originalCustNo.substring(10, 14);
			else if (originalCustomerNo.length() == 11)
				deptCode = originalCustNo.substring(7, 11);
			logger.info("CustNo > " + custNo + " || Div Code > " + divCode + "  || Dept Code > " + deptCode);
//			System.out.println("CustNo > " + custNo + " || Div Code > " + divCode + "  || Dept Code > " + deptCode);

			if (!deptCode.trim().equals("")) {
				account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, divCode,
						deptCode, "DEPT");
			} else if (!divCode.trim().equals("")) {
				account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, divCode, "",
						"DIV");
			} else {
				account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, null, null, "");
			}

			if (account == null)
				throw new Exception("Invalid account");

		} catch (Exception c) {

		}
		return account;
	}

	private void sendRecurringNotification(String emailType, String from, String failInvoice, String successInvoice, int totalSuccess, int totalFail) {

		// AmtbAccount acct = req.getAmtbAccount();
		List<String> ccEmails = new ArrayList<String>();

		String emails = ConfigurableConstants.getEmailText(emailType, "EMAIL");

		successInvoice = "Total Successful : " +totalSuccess +" <br>"
						+ "<br>"+successInvoice +"";
		
		failInvoice = "<br><br> Total Fail : "  +totalFail +" <br>"
						+ "<br>"+failInvoice +"";
		
		EmailUtil.sendEmail(from, emails.split(";"),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)

						.replaceAll("#invoiceNo#", successInvoice).replaceAll("#failInvoiceNo#", failInvoice));
	}

	private PaymentInfo recurringBuildPaymentInfo(String accountNo, BigDecimal paymentAmount, Integer bankInNo) {
		PaymentInfo paymentInfo = new PaymentInfo();

		AmtbAccount amtbAccount = getAccount(accountNo);
		AmtbAccount topLvlAccount = amtbAccount;
		AmtbAccount secondLvlAccount = null;
		AmtbAccount thirdLvlAccount = null;
		int i = 0;

		while (topLvlAccount.getAmtbAccount() != null) {
			if (i == 0)
				secondLvlAccount = topLvlAccount;
			if (i == 1) {
				thirdLvlAccount = secondLvlAccount;
				secondLvlAccount = topLvlAccount;
			}
			topLvlAccount = topLvlAccount.getAmtbAccount();
			i++;
		}

		paymentInfo.setAccount(topLvlAccount);

		if (thirdLvlAccount != null) {
			paymentInfo.setDepartment(thirdLvlAccount);
			paymentInfo.setDivision(secondLvlAccount);
		} else if (secondLvlAccount != null) {
			paymentInfo.setDivision(secondLvlAccount);
		}

		// payment mode
		paymentInfo.setPaymentMode(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD);
		// payment date
		paymentInfo.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
		// receipt date
		paymentInfo.setReceiptDt(DateUtil.getCurrentTimestamp());

		if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD)) {
			// txn ref no
			paymentInfo.setTxnRefNo("");
			// payment amount
			paymentInfo.setPaymentAmount(paymentAmount);
		}

		// remarks
		paymentInfo.setRemarks("PAYMENT THROUGH REDDOT");
		// bank in
//		Listbox bankListBox = (Listbox) this.getFellow("bankInBankListBox");
//		if (bankListBox.getSelectedItem() == null) {
//			throw new WrongValueException(bankListBox, "* Mandatory field");
//		}
//		FmtbBankCode selectedBankInBank = (FmtbBankCode) bankListBox.getSelectedItem().getValue();
//		paymentInfo.setBankInNo(selectedBankInBank.getBankCodeNo());

		paymentInfo.setBankInNo( bankInNo);
		
		// excess amount
		paymentInfo.setExcessAmount(new BigDecimal(0));

		return paymentInfo;
	}

	private String convertFileName(String fileName, String directory) {
		Integer counter = 1;
		// add date in file name
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		fileName = fileName.replace("{0}", dateFormat.format(new java.util.Date()));
		// add counter

		String tempfileName = fileName.replace("{1}", String.format("%04d", counter));
		File file = new File(directory + tempfileName);
		while (file.exists()) {
			counter++;
			tempfileName = fileName.replace("{1}", String.format("%04d", counter));
			file = new File(directory + tempfileName);
		}
		return file.getName();
	}
	
	private String getTempFileName(String fileName, String directory) {
		Integer counter = 1;
		// add date in file name
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		fileName = fileName.replace("{0}", dateFormat.format(new java.util.Date()));
		// add counter

		String tempfileName = fileName.replace("{1}", String.format("%04d", counter));

		return tempfileName;
	}
	private int recurringReferencesCheck(String refFront)
	{
		int index = 1;
		
		try 
		{
			IttbRecurringDtl recDtl = this.daoHelper.getProductDao().getRefFrontList(refFront);
			
			if(recDtl == null)
			{
				return index;
			}
			else
			{
				//example RDP_210324_00004
				String refBack = (recDtl.getReferenceId()).substring(11);
				
				Integer intRef = Integer.parseInt(refBack.trim());
				intRef++;
				index = intRef;
				logger.info("References Found, now setting reference to "+index);
			}
		}
		catch(Exception e)
		{
			logger.info("Error at references check. Error : "+e.getMessage());
			index = 1;
		}
		
		return index;
	}
	
	private AmtbAccount checkSelfOrParent(AmtbAccount acct2)
	{
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccountWithParent(acct2.getAccountNo()+"");

		if(acct.getAccountCategory().equalsIgnoreCase("APP") || acct.getAccountCategory().equalsIgnoreCase("SAPP"))
		{
			return acct;
		}
		else
		{
			if(acct.getInvoiceFormat() != null)
			{
				return acct;
			}
			else
			{
				if(acct.getAmtbAccount() != null)
				{
					if(acct.getAmtbAccount().getInvoiceFormat() != null)
					{

						return acct.getAmtbAccount();
					}
					else 
					{
						if(acct.getAmtbAccount().getAmtbAccount() != null)
						{
							if(acct.getAmtbAccount().getAmtbAccount().getInvoiceFormat() != null)
							{
								return acct.getAmtbAccount().getAmtbAccount();
							}
						}
					}	
				}
			}
			
			return acct;
		}
	}
}