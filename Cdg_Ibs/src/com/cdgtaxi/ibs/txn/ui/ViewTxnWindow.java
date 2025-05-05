package com.cdgtaxi.ibs.txn.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReq;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReqFlow;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;
import com.google.common.collect.Ordering;


public class ViewTxnWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(ViewTxnWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> txnStatus = new ArrayList<Listitem>();
	private String txnID = null;
	private static final String TO_BLANK = "(CHANGED TO BLANK)";
	private TmtbAcquireTxn tmtbAcquireTxn;
	private Boolean isCardless = new Boolean(false);
	
	public static Ordering<TmtbTxnReviewReq> TmtbTxnReviewReqNoOrder = Ordering.natural().onResultOf(
			new com.google.common.base.Function<TmtbTxnReviewReq, Integer>() {
				
				public Integer apply(TmtbTxnReviewReq req) {
					return req.getTxnReviewReqNo();
				}
				
			});
	
	

	
	
	public ViewTxnWindow() throws InterruptedException{
		// adding all txn status
		txnStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.TXN_STATUS.keySet()){
			txnStatus.add(new Listitem(NonConfigurableConstants.TXN_STATUS.get(statusCode), statusCode));
		}
		Map<String, String> map = Executions.getCurrent().getArg();
		txnID = (String) map.get("txnID");
	}

	public void refresh() throws InterruptedException {
		logger.debug("View Txn Window refresh()");
		//init();
	}
	
	public void edit() throws InterruptedException {
		logger.debug("View Txn Window edit()");
		// Check if there is a request that is being created already.
		// If there is, block the edit
		if (this.businessHelper.getTxnBusiness().isRequestCreated(txnID))
		{
			Messagebox.show("Unable to create a new transaction request as there is an existing transaction request that is still pending for approval.", "View Trip", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// Validation - Closed account cannot create transaction
		String acctNo = ((Label)this.getFellow("acctNo")).getValue();
		if (this.businessHelper.getTxnBusiness().isAccountClosed(acctNo))
		{
			Messagebox.show("Unable to modify trip as the account is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// creating params
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		// putting txnID into the params
		params.put("txnID", txnID);

		// Forward to view page
		this.forward(Uri.EDIT_TXN, params);
	}
	
	
	public void eReceipt() throws InterruptedException, NetException, IOException {
		
		Properties params = new Properties();
		String jobNo = tmtbAcquireTxn.getJobNo();
		String acquireTxnNo = tmtbAcquireTxn.getAcquireTxnNo().toString();
		
		logger.info("Generating eReceipt with job no: " + jobNo);
		
		params.put("01. Job No", jobNo);
		params.put("02. Acquire No", acquireTxnNo);
		String outputFormat = Constants.FORMAT_PDF;
		String reportName = NonConfigurableConstants.REPORT_NAME_TRIP_RECEIPT;
		String reportCategory = NonConfigurableConstants.REPORT_CATEGORY_TRIPS;
		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, reportCategory, outputFormat, params);
		
		Filedownload.save(bytes, "application/pdf", "eReceipt_" + jobNo + ".pdf");
		
	}
	
	public void init() throws InterruptedException
	{
		if(!this.checkUriAccess(Uri.EDIT_TXN))
			((Button)this.getFellow("editBtn")).setDisabled(true);
		
		try
		{
			
			tmtbAcquireTxn = this.businessHelper.getTxnBusiness().searchTxn(txnID);
			if (tmtbAcquireTxn != null)
			{
				List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
				for(PmtbProductType cardlessProduct : cardlessProducts)
				{
					if(cardlessProduct.getProductTypeId().equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
					{
						isCardless = true;
						break;
					}
				}
				
				// Populate account
				if (tmtbAcquireTxn.getAmtbAccount()!= null)
				{
					AmtbAccount amtbAccount = tmtbAcquireTxn.getAmtbAccount();
					if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(amtbAccount.getAccountCategory()))
					{
						// It is a department
						((Row)this.getFellow("divisionDepartmentRow")).setVisible(true);
						((Label)this.getFellow("department")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
						((Label)this.getFellow("department")).setVisible(true);
						((Label)this.getFellow("division")).setValue(amtbAccount.getAmtbAccount().getAccountName() + "(" + amtbAccount.getAmtbAccount().getCode() + ")");
						((Label)this.getFellow("division")).setVisible(true);
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAmtbAccount().getAccountName());
						// 
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(amtbAccount.getAccountCategory()))
					{
						// It is a division
						((Row)this.getFellow("divisionDepartmentRow")).setVisible(true);
						((Label)this.getFellow("division")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
						((Label)this.getFellow("division")).setVisible(true);
						((Label)this.getFellow("department")).setValue("-");
						((Label)this.getFellow("department")).setVisible(true);
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(amtbAccount.getAccountCategory()))
					{
						// It is a corp
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(amtbAccount.getAccountCategory()))
					{
						// Main applicant only
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(amtbAccount.getAccountCategory()))
					{
						// Sub applicant
						((Row)this.getFellow("applicantSubApplicantRow")).setVisible(true);
						((Label)this.getFellow("subApplicantLabel")).setVisible(true);
						((Label)this.getFellow("subApplicant")).setValue(amtbAccount.getAccountName());
						((Label)this.getFellow("subApplicant")).setVisible(true);
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT);
					}
				}
				else
				{
					throw new Exception("Account is not created properly");
				}
				if (tmtbAcquireTxn.getPmtbProduct() != null)
				{
					PmtbProduct pmtbProduct = tmtbAcquireTxn.getPmtbProduct();
					if (pmtbProduct.getCardNo() != null && !"".equals(pmtbProduct.getCardNo()))
					{
						((Row)this.getFellow("cardNoRow")).setVisible(true);
						((Label)this.getFellow("cardNo")).setValue(pmtbProduct.getCardNo());
						((Label)this.getFellow("nameOnCard")).setValue(pmtbProduct.getNameOnProduct());
					}
				}
				else
				{
					// To display external card
					if (tmtbAcquireTxn.getExternalCardNo() != null && !"".equals(tmtbAcquireTxn.getExternalCardNo()))
					{
						((Row)this.getFellow("cardNoRow")).setVisible(true);
						((Label)this.getFellow("cardNo")).setValue(tmtbAcquireTxn.getExternalCardNo());
						((Label)this.getFellow("nameOnCard")).setValue("-");
					}
				}
				if (tmtbAcquireTxn.getPmtbProductType() != null)
				{
					if (!NonConfigurableConstants.PREMIER_SERVICE.equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
					{
						((Row)this.getFellow("salesDraftRow")).setVisible(true);
						((Label)this.getFellow("productType")).setValue(tmtbAcquireTxn.getPmtbProductType().getName());
						((Button)this.getFellow("eReceiptButton")).setVisible(true);
					}
					
//					if(!isCardless)
//					{
//						((Row)this.getFellow("salesDraftRow")).setVisible(true);
//						((Label)this.getFellow("productType")).setValue(tmtbAcquireTxn.getPmtbProductType().getName());
//						((Button)this.getFellow("eReceiptButton")).setVisible(true);
//					}
				}
				
				if (tmtbAcquireTxn.getTaxiNo() != null && !"".equals(tmtbAcquireTxn.getTaxiNo()))
				{
					((Label)this.getFellow("taxiNo")).setValue(tmtbAcquireTxn.getTaxiNo());
				}
				if (tmtbAcquireTxn.getNric() != null && !"".equals(tmtbAcquireTxn.getNric()))
				{
					((Label)this.getFellow("nric")).setValue(StringUtil.maskNric(tmtbAcquireTxn.getNric()));
				}
				if (tmtbAcquireTxn.getTripStartDt() != null)
				{
					((Label)this.getFellow("tripStartDate")).setValue(DateUtil.convertUtilDateToStr(tmtbAcquireTxn.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT));
					((Label)this.getFellow("tripStartTime")).setValue(DateUtil.convertUtilDateToStr(tmtbAcquireTxn.getTripStartDt(), DateUtil.GLOBAL_TIME_FORMAT));
				}
				if (tmtbAcquireTxn.getTripEndDt() != null)
				{
					((Label)this.getFellow("tripEndDate")).setValue(DateUtil.convertUtilDateToStr(tmtbAcquireTxn.getTripEndDt(), DateUtil.GLOBAL_DATE_FORMAT));					
					((Label)this.getFellow("tripEndTime")).setValue(DateUtil.convertUtilDateToStr(tmtbAcquireTxn.getTripEndDt(), DateUtil.GLOBAL_TIME_FORMAT));					
				}
				
				if (tmtbAcquireTxn.getMstbMasterTableByServiceProvider() != null)
				{
					((Label)this.getFellow("companyCd")).setValue(tmtbAcquireTxn.getMstbMasterTableByServiceProvider().getMasterValue());
				}
				
				if (tmtbAcquireTxn.getJobNo() != null && !"".equals(tmtbAcquireTxn.getJobNo()))
				{
					((Label)this.getFellow("jobNo")).setValue(tmtbAcquireTxn.getJobNo());
				}
				if (tmtbAcquireTxn.getTxnStatus() != null && !"".equals(tmtbAcquireTxn.getTxnStatus()))
				{
					((Label)this.getFellow("txnStatus")).setValue(NonConfigurableConstants.TXN_STATUS.get(tmtbAcquireTxn.getTxnStatus()));
					// Turn off the edit button if the txn status is not not billed or billed
					if ((NonConfigurableConstants.TRANSACTION_STATUS_REFUND.equals(tmtbAcquireTxn.getTxnStatus())) ||
							(NonConfigurableConstants.TRANSACTION_STATUS_VOID.equals(tmtbAcquireTxn.getTxnStatus())))
					{
						Button btn = (Button) this.getFellow("editBtn");
						btn.setVisible(false);
					}

				}
				
				if (tmtbAcquireTxn.getFareAmt() != null)
				{
					((Label)this.getFellow("fareAmt")).setValue(tmtbAcquireTxn.getFareAmt().setScale(2).toString());
				}
				
				
				if (NonConfigurableConstants.getBoolean(tmtbAcquireTxn.getPmtbProductType().getPrepaid())){
					((Row)this.getFellow("prepaidAdminRow")).setVisible(true);
					((Label)this.getFellow("prepaidAdminFee")).setValue(StringUtil.bigDecimalToStringWithGDFormat(tmtbAcquireTxn.getAdminFeeValue()));
					((Label)this.getFellow("prepaidGst")).setValue(StringUtil.bigDecimalToStringWithGDFormat(tmtbAcquireTxn.getGstValue()));
				} else {
					
					((Row)this.getFellow("prepaidAdminRow")).setVisible(false);
				}
				
			
				
				if (tmtbAcquireTxn.getPickupAddress() != null  && !"".equals(tmtbAcquireTxn.getPickupAddress()))
				{
					((Label)this.getFellow("pickup")).setValue(tmtbAcquireTxn.getPickupAddress());
				}
				if (tmtbAcquireTxn.getDestination() != null && !"".equals(tmtbAcquireTxn.getDestination()))
				{
					((Label)this.getFellow("destination")).setValue(tmtbAcquireTxn.getDestination());
				}
				
				if (tmtbAcquireTxn.getProjectDesc() != null && !"".equals(tmtbAcquireTxn.getProjectDesc()))
				{
//					((Row)this.getFellow("projCodeRow")).setVisible(true);
					((Label)this.getFellow("projCode")).setValue(tmtbAcquireTxn.getProjectDesc());
				}
				
				if (tmtbAcquireTxn.getTripCodeReason() != null && !"".equals(tmtbAcquireTxn.getTripCodeReason()))
				{
//					((Row)this.getFellow("projCodeReasonRow")).setVisible(true);
					((Label)this.getFellow("projCodeReason")).setValue(tmtbAcquireTxn.getTripCodeReason());
				}
				
				((Label)this.getFellow("remarks")).setVisible(true);
				((Label)this.getFellow("remarks")).setValue(tmtbAcquireTxn.getRemarks());
					
				// Retrieve the latest FMS values
				if (NonConfigurableConstants.TRANSACTION_STATUS_VOID.equals(tmtbAcquireTxn.getTxnStatus())
						|| NonConfigurableConstants.TRANSACTION_STATUS_REFUND.equals(tmtbAcquireTxn.getTxnStatus()))
				{
					if (!this.businessHelper.getTxnBusiness().hasActiveOrBilledTripByJobNo(tmtbAcquireTxn.getJobNo()))
					{

						List<TmtbTxnReviewReq> list = this.businessHelper.getTxnBusiness().getTxnReqs(tmtbAcquireTxn.getAcquireTxnNo().toString());
						
						if(list != null)
						{
							Iterator<TmtbTxnReviewReq> iter = list.iterator();
							TmtbTxnReviewReq latestTmtbTxnReviewReq = (TmtbTxnReviewReq) iter.next();
							if (NonConfigurableConstants.BOOLEAN_YES.equals(latestTmtbTxnReviewReq.getFmsFlag()))
							{
								// 
								if (latestTmtbTxnReviewReq.getFmsFlag() != null && !"".equals(latestTmtbTxnReviewReq.getFmsFlag()))
								{
									((Label)this.getFellow("toUpdateFMSList")).setValue(latestTmtbTxnReviewReq.getFmsFlag());
								}
								
								if (latestTmtbTxnReviewReq.getUpdateFms() != null && !"".equals(latestTmtbTxnReviewReq.getUpdateFms()))
								{
									((Label)this.getFellow("updateFMSList")).setValue(latestTmtbTxnReviewReq.getUpdateFms());
								}
								if (latestTmtbTxnReviewReq.getFmsAmt() != null && !"".equals(latestTmtbTxnReviewReq.getFmsAmt().setScale(2).toString()))
								{
									((Label)this.getFellow("FMSAmount")).setValue(latestTmtbTxnReviewReq.getFmsAmt().setScale(2).toString());
								}
								if (latestTmtbTxnReviewReq.getIncentiveAmt() != null && !"".equals(latestTmtbTxnReviewReq.getIncentiveAmt().setScale(2).toString()))
								{
									((Label)this.getFellow("incentiveAmt")).setValue(latestTmtbTxnReviewReq.getIncentiveAmt().setScale(2).toString());
								}
								if (latestTmtbTxnReviewReq.getPromoAmt() != null && !"".equals(latestTmtbTxnReviewReq.getPromoAmt().setScale(2).toString()))
								{
									((Label)this.getFellow("promoAmt")).setValue(latestTmtbTxnReviewReq.getPromoAmt().setScale(2).toString());
								}
								if (latestTmtbTxnReviewReq.getCabRewardsAmt() != null && !"".equals(latestTmtbTxnReviewReq.getCabRewardsAmt().setScale(2).toString()))
								{
									((Label)this.getFellow("cabRewardsAmt")).setValue(latestTmtbTxnReviewReq.getCabRewardsAmt().setScale(2).toString());
								}
							}
							else
							{
								if (latestTmtbTxnReviewReq.getFmsFlag() != null && !"".equals(latestTmtbTxnReviewReq.getFmsFlag()))
								{
									((Label)this.getFellow("toUpdateFMSList")).setValue(latestTmtbTxnReviewReq.getFmsFlag());
								}
							}
						}
						else
						{
							((Label)this.getFellow("toUpdateFMSList")).setValue("-");
						}
					}
					else
					{
						if (tmtbAcquireTxn.getFmsFlag() != null && !"".equals(tmtbAcquireTxn.getFmsFlag()))
						{
							((Label)this.getFellow("toUpdateFMSList")).setValue(tmtbAcquireTxn.getFmsFlag());
						}
						
						if (tmtbAcquireTxn.getUpdateFms() != null && !"".equals(tmtbAcquireTxn.getUpdateFms()))
						{
							((Label)this.getFellow("updateFMSList")).setValue(tmtbAcquireTxn.getUpdateFms());
						}
						if (tmtbAcquireTxn.getFmsAmt() != null && !"".equals(tmtbAcquireTxn.getFmsAmt().setScale(2).toString()))
						{
							((Label)this.getFellow("FMSAmount")).setValue(tmtbAcquireTxn.getFmsAmt().setScale(2).toString());
						}
						if (tmtbAcquireTxn.getIncentiveAmt() != null && !"".equals(tmtbAcquireTxn.getIncentiveAmt().setScale(2).toString()))
						{
							((Label)this.getFellow("incentiveAmt")).setValue(tmtbAcquireTxn.getIncentiveAmt().setScale(2).toString());
						}
						if (tmtbAcquireTxn.getPromoAmt() != null && !"".equals(tmtbAcquireTxn.getPromoAmt().setScale(2).toString()))
						{
							((Label)this.getFellow("promoAmt")).setValue(tmtbAcquireTxn.getPromoAmt().setScale(2).toString());
						}
						if (tmtbAcquireTxn.getCabRewardsAmt() != null && !"".equals(tmtbAcquireTxn.getCabRewardsAmt().setScale(2).toString()))
						{
							((Label)this.getFellow("cabRewardsAmt")).setValue(tmtbAcquireTxn.getCabRewardsAmt().setScale(2).toString());
						}
					}
					
					// Retrieve the credit note tied to this billed trips
					if (NonConfigurableConstants.TRANSACTION_STATUS_REFUND.equals(tmtbAcquireTxn.getTxnStatus()))
					{
						String noteNumber = this.businessHelper.getTxnBusiness().getNote(tmtbAcquireTxn.getAcquireTxnNo());
						((Row)this.getFellow("noteIdRow")).setVisible(true);
						if (noteNumber != null)
							((Label)this.getFellow("noteId")).setValue(noteNumber);
						else
							((Label)this.getFellow("noteId")).setValue("-");
					}
				}
				else
				{
					if (tmtbAcquireTxn.getFmsFlag() != null && !"".equals(tmtbAcquireTxn.getFmsFlag()))
					{
						((Label)this.getFellow("toUpdateFMSList")).setValue(tmtbAcquireTxn.getFmsFlag());
					}
					
					if (tmtbAcquireTxn.getUpdateFms() != null && !"".equals(tmtbAcquireTxn.getUpdateFms()))
					{
						((Label)this.getFellow("updateFMSList")).setValue(tmtbAcquireTxn.getUpdateFms());
					}
					if (tmtbAcquireTxn.getFmsAmt() != null && !"".equals(tmtbAcquireTxn.getFmsAmt().setScale(2).toString()))
					{
						((Label)this.getFellow("FMSAmount")).setValue(tmtbAcquireTxn.getFmsAmt().setScale(2).toString());
					}
					if (tmtbAcquireTxn.getIncentiveAmt() != null && !"".equals(tmtbAcquireTxn.getIncentiveAmt().setScale(2).toString()))
					{
						((Label)this.getFellow("incentiveAmt")).setValue(tmtbAcquireTxn.getIncentiveAmt().setScale(2).toString());
					}
					if (tmtbAcquireTxn.getPromoAmt() != null && !"".equals(tmtbAcquireTxn.getPromoAmt().setScale(2).toString()))
					{
						((Label)this.getFellow("promoAmt")).setValue(tmtbAcquireTxn.getPromoAmt().setScale(2).toString());
					}
					if (tmtbAcquireTxn.getCabRewardsAmt() != null && !"".equals(tmtbAcquireTxn.getCabRewardsAmt().setScale(2).toString()))
					{
						((Label)this.getFellow("cabRewardsAmt")).setValue(tmtbAcquireTxn.getCabRewardsAmt().setScale(2).toString());
					}
				}
				
				if (tmtbAcquireTxn.getComplimentary() != null && !"".equals(tmtbAcquireTxn.getComplimentary()))
				{
					((Label)this.getFellow("complimentary")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(tmtbAcquireTxn.getComplimentary()));
				}
				

				if (tmtbAcquireTxn.getUpdatedBy() != null)
					((Label)this.getFellow("lastUpdatedBy")).setValue(tmtbAcquireTxn.getUpdatedBy());
				else
					((Label)this.getFellow("lastUpdatedBy")).setValue(tmtbAcquireTxn.getCreatedBy());

				if(tmtbAcquireTxn.getUpdatedDt() != null)
				{
					((Label)this.getFellow("lastUpdatedDate")).setValue(DateUtil.convertTimestampToStr(tmtbAcquireTxn.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
					((Label)this.getFellow("lastUpdatedTime")).setValue(DateUtil.convertTimestampToStr(tmtbAcquireTxn.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
				}
				else
				{
					((Label)this.getFellow("lastUpdatedDate")).setValue(DateUtil.convertTimestampToStr(tmtbAcquireTxn.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
					((Label)this.getFellow("lastUpdatedTime")).setValue(DateUtil.convertTimestampToStr(tmtbAcquireTxn.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
				}
				
				((Row)this.getFellow("surchargesRow")).setVisible(true);
				if (tmtbAcquireTxn.getSurcharge() != null  && !"".equals(tmtbAcquireTxn.getSurcharge()))
				{
					((Label)this.getFellow("surchargesRemarks")).setValue(tmtbAcquireTxn.getSurcharge());
				}
				
				// TRIP TYPE
				if (tmtbAcquireTxn.getMstbMasterTableByTripType() != null)
				{
					MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByTripType();						
					((Label)this.getFellow("tripType")).setValue(mstbMasterTable.getMasterValue());
				}
				
//				if (NonConfigurableConstants.PREMIER_SERVICE.equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
				if(isCardless)
				{
					if (tmtbAcquireTxn.getPmtbProductType() != null)
					{
						((Row)this.getFellow("productTypeRow")).setVisible(true);
						((Label)this.getFellow("productType_p")).setValue(tmtbAcquireTxn.getPmtbProductType().getName());
					}
					// Premier fields
					((Row)this.getFellow("bookRow")).setVisible(true);
					if (tmtbAcquireTxn.getBookedBy() != null && !"".equals(tmtbAcquireTxn.getBookedBy()))
					{
						((Label)this.getFellow("bookedBy")).setValue(tmtbAcquireTxn.getBookedBy());
					}
					((Row)this.getFellow("bookRow")).setVisible(true);
					if (tmtbAcquireTxn.getBookDateTime() != null)
					{
						((Label)this.getFellow("bookedDate")).setValue(DateUtil.convertUtilDateToStr(tmtbAcquireTxn.getBookDateTime(),"dd/MM/yyyy"));
					}
					((Row)this.getFellow("flightInfoRow")).setVisible(true);
					if (tmtbAcquireTxn.getBookingRef() != null && !"".equals(tmtbAcquireTxn.getBookingRef()))
					{
						((Label)this.getFellow("bookedRef")).setValue(tmtbAcquireTxn.getBookingRef());
					}
					if (tmtbAcquireTxn.getFlightInfo() != null && !"".equals(tmtbAcquireTxn.getFlightInfo()))
					{
						((Label)this.getFellow("flightInfo")).setValue(tmtbAcquireTxn.getFlightInfo());
					}
					
//					((Row)this.getFellow("tripRow")).setVisible(true);
					((Label)this.getFellow("premJobTypeLabel")).setVisible(true);
					((Label)this.getFellow("premJobType")).setVisible(true);
					
					if (tmtbAcquireTxn.getMstbMasterTableByJobType() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByJobType();						
						((Label)this.getFellow("premJobType")).setValue(mstbMasterTable.getMasterValue());
					}

					((Row)this.getFellow("vehicleRow")).setVisible(true);
					if (tmtbAcquireTxn.getMstbMasterTableByVehicleType() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByVehicleType();
						((Label)this.getFellow("vehicleGroup")).setValue(mstbMasterTable.getMasterValue());
					}
					if (tmtbAcquireTxn.getMstbMasterTableByVehicleModel() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByVehicleModel();
						((Label)this.getFellow("premVehicleType")).setValue(mstbMasterTable.getMasterValue());
					}

					((Row)this.getFellow("paxRow")).setVisible(true);
					if (tmtbAcquireTxn.getPassengerName() != null && !"".equals(tmtbAcquireTxn.getPassengerName()))
					{
						((Label)this.getFellow("paxName")).setValue(tmtbAcquireTxn.getPassengerName());
					}
					
					((Label)this.getFellow("levyLabel")).setVisible(true);
					((Label)this.getFellow("levy")).setVisible(true);
					if (tmtbAcquireTxn.getLevy() != null && !"".equals(tmtbAcquireTxn.getLevy().toString()))
					{
						((Label)this.getFellow("levy")).setValue(tmtbAcquireTxn.getLevy().setScale(2).toString());
					}
				}
				else
				{
					if (tmtbAcquireTxn.getSalesDraftNo() != null && !"".equals(tmtbAcquireTxn.getSalesDraftNo()))
					{
						((Label)this.getFellow("salesDraftLabel")).setVisible(true);
						((Label)this.getFellow("salesDraft")).setVisible(true);
						((Label)this.getFellow("salesDraft")).setValue(tmtbAcquireTxn.getSalesDraftNo());
					}
					((Row)this.getFellow("jobVehType")).setVisible(true);
					if (tmtbAcquireTxn.getMstbMasterTableByJobType() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByJobType();						
						((Label)this.getFellow("jobType")).setValue(mstbMasterTable.getMasterValue());
					}
					if (tmtbAcquireTxn.getMstbMasterTableByVehicleModel() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByVehicleModel();
						((Label)this.getFellow("vehicleType")).setValue(mstbMasterTable.getMasterValue());
					}
				}
				
				if (NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE.equalsIgnoreCase(tmtbAcquireTxn.getTxnStatus())
					|| 	NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED.equalsIgnoreCase(tmtbAcquireTxn.getTxnStatus()))
				{
					Grid newTxnDetailsGridTitle = (Grid) this.getFellow("newTxnDetailsGridTitle");
					newTxnDetailsGridTitle.setVisible(true);
					Grid newTxnDetailsGrid = (Grid) this.getFellow("newTxnDetailsGrid");
					newTxnDetailsGrid.setVisible(true);
					
					Iterator<TmtbTxnReviewReq> tmtbTxnReviewReqIterator = tmtbAcquireTxn.getTmtbTxnReviewReqs().iterator();
					// Retrieve the first
					TmtbTxnReviewReq temp = null;
					if (tmtbTxnReviewReqIterator.hasNext())
					{
						temp = tmtbTxnReviewReqIterator.next();
						TmtbTxnReviewReq tmtbTxnReviewReq = null;
						if (temp != null)
						{
							// To do a retrieval so that the join will be intact
							tmtbTxnReviewReq = this.businessHelper.getTxnBusiness().getTxnReq(temp.getTxnReviewReqNo().toString());
					
							if (tmtbTxnReviewReq != null)
							{
								boolean checkTxnBilled = true;
								if(tmtbAcquireTxn != null)
								{
									if(tmtbAcquireTxn.getTxnStatus().equals(NonConfigurableConstants.TRANSACTION_STATUS_BILLED))
										checkTxnBilled = false;
								}
								
								((Label)this.getFellow("actionTxn")).setValue(tmtbTxnReviewReq.getActionTxn());
								if (tmtbTxnReviewReq.getOtuFlag() != null && !"".equals(tmtbTxnReviewReq.getOtuFlag()) && checkTxnBilled)
								{
									((Label)this.getFellow("cancelOTULabel")).setVisible(true);
									((Label)this.getFellow("cancelOTU")).setVisible(true);
									((Label)this.getFellow("cancelOTU")).setValue(tmtbTxnReviewReq.getOtuFlag());					
								}
			
								// populate the changed fields
								
								if (tmtbTxnReviewReq.getAmtbAccount() != null)
								{
									// Set the account
									// Populate account
									AmtbAccount amtbAccount = tmtbTxnReviewReq.getAmtbAccount();
									System.out.println("---amtbaccount---" + amtbAccount.getAccountNo());
									System.out.println("---tmtbAcquireaccount---" + tmtbAcquireTxn.getAmtbAccount().getAccountNo());
									if (tmtbAcquireTxn.getAmtbAccount().getAccountNo().compareTo(amtbAccount.getAccountNo()) != 0)
									{
										if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(amtbAccount.getAccountCategory()))
										{
											// It is a department
											((Row)this.getFellow("newDivisionDepartmentRow")).setVisible(true);
											((Label)this.getFellow("newDepartment")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
											((Label)this.getFellow("newDepartment")).setVisible(true);
											((Label)this.getFellow("newDivision")).setValue(amtbAccount.getAmtbAccount().getAccountName() + "(" + amtbAccount.getAmtbAccount().getCode() + ")");
											((Label)this.getFellow("newDivision")).setVisible(true);
											((Row)this.getFellow("newAcctRow")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setVisible(true);
											((Label)this.getFellow("newName")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setValue(amtbAccount.getAmtbAccount().getAmtbAccount().getCustNo());
											((Label)this.getFellow("newName")).setValue(amtbAccount.getAmtbAccount().getAmtbAccount().getAccountName());
											// 
											setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT, "newBillingContact", "newBillingContactRow");
										}
										else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(amtbAccount.getAccountCategory()))
										{
											// It is a division
											((Row)this.getFellow("newDivisionDepartmentRow")).setVisible(true);
											((Label)this.getFellow("newDivision")).setValue(amtbAccount.getAccountName() + "(" + amtbAccount.getCode() + ")");
											((Label)this.getFellow("newDivision")).setVisible(true);
											((Label)this.getFellow("newDepartment")).setValue("-");
											((Label)this.getFellow("newDepartment")).setVisible(true);
											((Row)this.getFellow("newAcctRow")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setVisible(true);
											((Label)this.getFellow("newName")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
											((Label)this.getFellow("newName")).setValue(amtbAccount.getAmtbAccount().getAccountName());
											setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION, "newBillingContact", "newBillingContactRow");
					
										}
										else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(amtbAccount.getAccountCategory()))
										{
											// It is a corp
											((Row)this.getFellow("newAcctRow")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setValue(amtbAccount.getCustNo());
											((Label)this.getFellow("newAcctNo")).setVisible(true);
											((Label)this.getFellow("newName")).setValue(amtbAccount.getAccountName());
											((Label)this.getFellow("newName")).setVisible(true);
											setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE, "newBillingContact", "newBillingContactRow");
										}
										else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(amtbAccount.getAccountCategory()))
										{
											// Main applicant only
											((Row)this.getFellow("newAcctRow")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setValue(amtbAccount.getCustNo());
											((Label)this.getFellow("newAcctNo")).setVisible(true);
											((Label)this.getFellow("newName")).setValue(amtbAccount.getAccountName());
											((Label)this.getFellow("newName")).setVisible(true);
											setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, "newBillingContact", "newBillingContactRow");
										}
										else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(amtbAccount.getAccountCategory()))
										{
											// Sub applicant
											((Row)this.getFellow("newApplicantSubApplicantRow")).setVisible(true);
											((Label)this.getFellow("newSubApplicantLabel")).setVisible(true);
											((Label)this.getFellow("newSubApplicant")).setValue(amtbAccount.getAccountName());
											((Label)this.getFellow("newSubApplicant")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setVisible(true);
											((Label)this.getFellow("newAcctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
											((Label)this.getFellow("newName")).setVisible(true);
											((Label)this.getFellow("newName")).setValue(amtbAccount.getAmtbAccount().getAccountName());
											setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT, "newBillingContact", "newBillingContactRow");
										}
									}
								}
								
								if (tmtbTxnReviewReq.getPickupAddress() != null)
								{
									if (!tmtbTxnReviewReq.getPickupAddress().equals(tmtbAcquireTxn.getPickupAddress()))
									{
										((Row)this.getFellow("newPickupRow")).setVisible(true);
										((Label)this.getFellow("newPickup")).setVisible(true);
										((Label)this.getFellow("newPickup")).setValue(tmtbTxnReviewReq.getPickupAddress());
									}
								}
								
								if (tmtbTxnReviewReq.getDestination() != null)
								{
									if (!tmtbTxnReviewReq.getDestination().equals(tmtbAcquireTxn.getDestination()))
									{
										((Row)this.getFellow("newDestRow")).setVisible(true);
										((Label)this.getFellow("newDestination")).setVisible(true);
										((Label)this.getFellow("newDestination")).setValue(tmtbTxnReviewReq.getDestination());
									}
								}
								else if (tmtbAcquireTxn.getDestination() != null)
								{
									// this means that the tmtbAcquireTxn had value but the new one does not have value
									((Row)this.getFellow("newDestRow")).setVisible(true);
									((Label)this.getFellow("newDestination")).setVisible(true);
									((Label)this.getFellow("newDestination")).setValue(TO_BLANK);
								}
								
								if (tmtbTxnReviewReq.getMstbMasterTableByServiceProvider() != null)
								{
									if (!tmtbTxnReviewReq.getMstbMasterTableByServiceProvider().equals(tmtbAcquireTxn.getMstbMasterTableByServiceProvider()))
									{
										((Row)this.getFellow("newCompanyCdRow")).setVisible(true);
										((Label)this.getFellow("newCompanyCd")).setVisible(true);
										((Label)this.getFellow("newCompanyCd")).setValue(tmtbTxnReviewReq.getMstbMasterTableByServiceProvider().getMasterValue());
									}
								}
								if (tmtbTxnReviewReq.getFareAmt() != null)
								{
									if (!tmtbTxnReviewReq.getFareAmt().toString().equals(tmtbAcquireTxn.getFareAmt().toString()))
									{
										((Row)this.getFellow("newFareRow")).setVisible(true);
										((Label)this.getFellow("newFareAmt")).setValue(tmtbTxnReviewReq.getFareAmt().setScale(2).toString());
									}
								}
								
								if (tmtbTxnReviewReq.getAdminFeeValue() != null)
								{
									if (!tmtbTxnReviewReq.getAdminFeeValue().toString().equals(tmtbAcquireTxn.getAdminFeeValue().toString()))
									{
										((Row)this.getFellow("newPrepaidAdminFeeRow")).setVisible(true);
										((Label)this.getFellow("newPrepaidAdminFee")).setValue(tmtbTxnReviewReq.getAdminFeeValue().setScale(2).toString());
									}
								}
								
								if (tmtbTxnReviewReq.getGstValue() != null)
								{
									if (!tmtbTxnReviewReq.getGstValue().toString().equals(tmtbAcquireTxn.getGstValue().toString()))
									{
										((Row)this.getFellow("newPrepaidGstRow")).setVisible(true);
										((Label)this.getFellow("newPrepaidGst")).setValue(tmtbTxnReviewReq.getGstValue().setScale(2).toString());
									}
								}
								
								if (tmtbTxnReviewReq.getSalesDraftNo() != null)
								{
									if (!tmtbTxnReviewReq.getSalesDraftNo().equals(tmtbAcquireTxn.getSalesDraftNo()))
									{
										((Row)this.getFellow("newFareRow")).setVisible(true);
										((Label)this.getFellow("newSalesDraft")).setVisible(true);
										((Label)this.getFellow("newSalesDraft")).setValue(tmtbTxnReviewReq.getSalesDraftNo());
									}
								}
								else if (tmtbAcquireTxn.getSalesDraftNo() != null)
								{
									// this means that the tmtbAcquireTxn had value but the new one does not have value
									((Row)this.getFellow("newFareRow")).setVisible(true);
									((Label)this.getFellow("newSalesDraft")).setVisible(true);
									((Label)this.getFellow("newSalesDraft")).setValue(TO_BLANK);
								}
								
								// Complimentary
								if (tmtbTxnReviewReq.getComplimentary() != null)
								{
									if (!tmtbTxnReviewReq.getComplimentary().equals(tmtbAcquireTxn.getComplimentary()))
									{
										((Row)this.getFellow("newComplimentaryRow")).setVisible(true);
										((Label)this.getFellow("newComplimentary")).setValue(tmtbTxnReviewReq.getComplimentary());
									}
								}
								
								if (tmtbTxnReviewReq.getProjectDesc() != null)
								{
									if (!tmtbTxnReviewReq.getProjectDesc().equals(tmtbAcquireTxn.getProjectDesc()))
									{
//										((Row)this.getFellow("newProjCodeRow")).setVisible(true);
										((Label)this.getFellow("newProjCode")).setValue(tmtbTxnReviewReq.getProjectDesc());
									}
								}
								else if (tmtbAcquireTxn.getProjectDesc() != null)
								{
									// this means that the tmtbAcquireTxn had value but the new one does not have value
//									((Row)this.getFellow("newProjCodeRow")).setVisible(true);
									((Label)this.getFellow("newProjCode")).setValue(TO_BLANK);
								}
								
								if (tmtbTxnReviewReq.getTripCodeReason() != null)
								{
									if (!tmtbTxnReviewReq.getTripCodeReason().equals(tmtbAcquireTxn.getTripCodeReason()))
									{
										((Row)this.getFellow("newProjCodeReasonRow")).setVisible(true);
										((Label)this.getFellow("newProjCodeReason")).setValue(tmtbTxnReviewReq.getTripCodeReason());
									}
								}
								else if (tmtbAcquireTxn.getTripCodeReason() != null)
								{
									// this means that the tmtbAcquireTxn had value but the new one does not have value
									((Row)this.getFellow("newProjCodeReasonRow")).setVisible(true);
									((Label)this.getFellow("newProjCodeReason")).setValue(TO_BLANK);
								}
								
								
								if (tmtbTxnReviewReq.getRemarks() != null)
								{
									if (!tmtbTxnReviewReq.getRemarks().equals(tmtbAcquireTxn.getRemarks()))
									{
										((Row)this.getFellow("newRemarksRow")).setVisible(true);
										((Label)this.getFellow("newRemarks")).setVisible(true);
										((Label)this.getFellow("newRemarks")).setValue(tmtbTxnReviewReq.getRemarks());
									}
								}
								else if (tmtbAcquireTxn.getRemarks() != null)
								{
									// this means that the tmtbAcquireTxn had value but the new one does not have value
									((Row)this.getFellow("newRemarksRow")).setVisible(true);
									((Label)this.getFellow("newRemarks")).setVisible(true);
									((Label)this.getFellow("newRemarks")).setValue(TO_BLANK);
								}
								if (tmtbTxnReviewReq.getTripStartDt() != null)
								{
									if (tmtbTxnReviewReq.getTripStartDt().compareTo(tmtbAcquireTxn.getTripStartDt()) != 0)
									{
										((Row)this.getFellow("newTripStartRow")).setVisible(true);
										((Label)this.getFellow("newTripStartDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReq.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT));
										((Label)this.getFellow("newTripStartTime")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReq.getTripStartDt(), DateUtil.GLOBAL_TIME_FORMAT));
										((Label)this.getFellow("newTripStartDate")).setVisible(true);
										((Label)this.getFellow("newTripStartTime")).setVisible(true);
									}
								}
								if (tmtbTxnReviewReq.getTripEndDt() != null)
								{
									if (tmtbTxnReviewReq.getTripEndDt().compareTo(tmtbAcquireTxn.getTripEndDt()) != 0)
									{
										((Row)this.getFellow("newTripEndRow")).setVisible(true);
										((Label)this.getFellow("newTripEndDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReq.getTripEndDt(), DateUtil.GLOBAL_DATE_FORMAT));
										((Label)this.getFellow("newTripEndTime")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReq.getTripEndDt(), DateUtil.GLOBAL_TIME_FORMAT));
										((Label)this.getFellow("newTripEndDate")).setVisible(true);
										((Label)this.getFellow("newTripEndTime")).setVisible(true);
									}
								}
								Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReq.getTmtbTxnReviewReqFlows().iterator();
								if (iter != null)
								{
									TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = iter.next();
									//this.getFellow("updRow").setVisible(false);
									//this.getFellow("reqRow").setVisible(true);
									//this.getFellow("updTimeRow").setVisible(false);
									//this.getFellow("reqTimeRow").setVisible(true);
									//((Label)this.getFellow("reqBy")).setValue(tmtbTxnReviewReqFlow.getSatbUser().getLoginId());
									//((Label)this.getFellow("reqDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
									//((Label)this.getFellow("reqTime")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_TIME_FORMAT));
									((Label)this.getFellow("reqRemarks")).setValue(tmtbTxnReviewReqFlow.getRemarks());
								}
			
			
								if (tmtbTxnReviewReq.getFmsAmt() != null)
								{
									((Label)this.getFellow("newFMSAmount")).setVisible(true);
									((Label)this.getFellow("newFMSAmount")).setValue(tmtbTxnReviewReq.getFmsAmt().setScale(2).toString());
								}
								
								if (tmtbTxnReviewReq.getIncentiveAmt() != null)
								{
									((Label)this.getFellow("newIncentiveAmt")).setVisible(true);
									((Label)this.getFellow("newIncentiveAmt")).setValue(tmtbTxnReviewReq.getIncentiveAmt().setScale(2).toString());
								}
								
								if (tmtbTxnReviewReq.getPromoAmt() != null)
								{
									((Label)this.getFellow("newPromoAmt")).setVisible(true);
									((Label)this.getFellow("newPromoAmt")).setValue(tmtbTxnReviewReq.getPromoAmt().setScale(2).toString());
								}
								if (tmtbTxnReviewReq.getCabRewardsAmt() != null)
								{
									((Label)this.getFellow("newCabRewardsAmt")).setVisible(true);
									((Label)this.getFellow("newCabRewardsAmt")).setValue(tmtbTxnReviewReq.getCabRewardsAmt().setScale(2).toString());
								}
								
								if (tmtbTxnReviewReq.getUpdateFms() != null)
								{
									//if (tmtbTxnReviewReq.getUpdateFms().toString().equals(tmtbAcquireTxn.getUpdateFms()))
									//{
										((Label)this.getFellow("newUpdateFMSList")).setVisible(true);
										((Label)this.getFellow("newUpdateFMSList")).setValue(tmtbTxnReviewReq.getUpdateFms());
									//}
									
								}
								
								if (tmtbTxnReviewReq.getFmsFlag() != null)
								{
									//if (tmtbTxnReviewReq.getFmsFlag().equals(tmtbAcquireTxn.getFmsFlag()))
									//{
										((Label)this.getFellow("newToUpdateFMSList")).setVisible(true);
										((Label)this.getFellow("newToUpdateFMSList")).setValue(tmtbTxnReviewReq.getFmsFlag());
									//}
								}

								if (tmtbTxnReviewReq.getSurcharge() != null)
								{
									if (!tmtbTxnReviewReq.getSurcharge().equals(tmtbAcquireTxn.getSurcharge()))
									{
										((Row)this.getFellow("newSurchargeRow")).setVisible(true);
										((Label)this.getFellow("newSurchargesRemarks")).setValue(tmtbTxnReviewReq.getSurcharge());
									}
								}
								else if (tmtbAcquireTxn.getSurcharge() != null)
								{
									// this means that the tmtbAcquireTxn had value but the new one does not have value
									((Row)this.getFellow("newSurchargeRow")).setVisible(true);
									((Label)this.getFellow("newSurchargesRemarks")).setValue(TO_BLANK);
								}
								
								// TRIP TYPE and vehicle group
								if (tmtbTxnReviewReq.getMstbMasterTableByTripType() != null)
								{
									if (!tmtbTxnReviewReq.getMstbMasterTableByTripType().equals(tmtbAcquireTxn.getMstbMasterTableByTripType()))
									{
										MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByTripType();
										((Row)this.getFellow("newTripTypeRow")).setVisible(true);
										((Label)this.getFellow("newTripType")).setVisible(true);
										((Label)this.getFellow("newTripType")).setValue(mstbMasterTable.getMasterValue());
									}
								}
								
//								if (NonConfigurableConstants.PREMIER_SERVICE.equals(tmtbTxnReviewReq.getPmtbProductType().getProductTypeId()))
								if(isCardless)
								{
									if (tmtbTxnReviewReq.getMstbMasterTableByVehicleType() != null)
									{
										if (!tmtbTxnReviewReq.getMstbMasterTableByVehicleType().equals(tmtbAcquireTxn.getMstbMasterTableByVehicleType()))
										{
											MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByVehicleType();
											((Row)this.getFellow("newVehicleRow")).setVisible(true);
											((Label)this.getFellow("newVehicleGroup")).setVisible(true);
											((Label)this.getFellow("newVehicleGroup")).setValue(mstbMasterTable.getMasterValue());
										}
									}
									
									// Job TYPE and vehicle type
									if (tmtbTxnReviewReq.getMstbMasterTableByJobType() != null)
									{
										if (!tmtbTxnReviewReq.getMstbMasterTableByJobType().equals(tmtbAcquireTxn.getMstbMasterTableByJobType()))
										{
											MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByJobType();
											((Row)this.getFellow("newTripRow")).setVisible(true);
											((Label)this.getFellow("newPremJobType")).setVisible(true);
											((Label)this.getFellow("newPremJobType")).setValue(mstbMasterTable.getMasterValue());
										}
									}
									if (tmtbTxnReviewReq.getMstbMasterTableByVehicleModel() != null)
									{
										if (!tmtbTxnReviewReq.getMstbMasterTableByVehicleModel().equals(tmtbAcquireTxn.getMstbMasterTableByVehicleModel()))
										{
											MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByVehicleModel();
											((Row)this.getFellow("newVehicleRow")).setVisible(true);
											((Label)this.getFellow("newPremVehicleType")).setVisible(true);
											((Label)this.getFellow("newPremVehicleType")).setValue(mstbMasterTable.getMasterValue());
										}
									}
									
									if (tmtbTxnReviewReq.getLevy() != null)
									{
										((Label)this.getFellow("newLevyLabel")).setVisible(true);
										((Label)this.getFellow("newLevy")).setVisible(true);
										((Label)this.getFellow("newLevy")).setValue(tmtbTxnReviewReq.getLevy().setScale(2).toString());
									}
									else
									{
										((Label)this.getFellow("newLevyLabel")).setVisible(true);
										((Label)this.getFellow("newLevy")).setVisible(true);
										((Label)this.getFellow("newLevy")).setValue("-");
									}
									
									if (tmtbTxnReviewReq.getBookedBy() != null)
									{
										if (!tmtbTxnReviewReq.getBookedBy().equals(tmtbAcquireTxn.getBookedBy()))
										{
											((Row)this.getFellow("newBookRow")).setVisible(true);
											((Label)this.getFellow("newBookedBy")).setValue(tmtbTxnReviewReq.getBookedBy());
										}
									}
									else if (tmtbAcquireTxn.getBookedBy() != null)
									{
										// this means that the tmtbAcquireTxn had value but the new one does not have value
										((Row)this.getFellow("newBookRow")).setVisible(true);
										((Label)this.getFellow("newBookedBy")).setValue(TO_BLANK);
									}
									
									if (tmtbTxnReviewReq.getBookDateTime() != null)
									{
										if (!tmtbTxnReviewReq.getBookDateTime().equals(tmtbAcquireTxn.getBookDateTime()))
										{
											((Row)this.getFellow("newBookRow")).setVisible(true);
											((Label)this.getFellow("newBookedDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReq.getBookDateTime(), DateUtil.GLOBAL_DATE_FORMAT));
										}
									}
									else if (tmtbAcquireTxn.getBookDateTime() != null)
									{
										// this means that the tmtbAcquireTxn had value but the new one does not have value
										((Row)this.getFellow("newBookRow")).setVisible(true);
										((Label)this.getFellow("newBookedDate")).setValue(TO_BLANK);
									}
									
									
									if (tmtbTxnReviewReq.getBookingRef() != null)
									{
										if (!tmtbTxnReviewReq.getBookingRef().equals(tmtbAcquireTxn.getBookingRef()))
										{
											((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
											((Label)this.getFellow("newBookedRef")).setValue(tmtbTxnReviewReq.getBookingRef());
										}
									}
									else if (tmtbAcquireTxn.getBookingRef() != null)
									{
										// this means that the tmtbAcquireTxn had value but the new one does not have value
										((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
										((Label)this.getFellow("newBookedRef")).setValue(TO_BLANK);
									}
									
									if (tmtbTxnReviewReq.getFlightInfo() != null)
									{
										if (!tmtbTxnReviewReq.getFlightInfo().equals(tmtbAcquireTxn.getFlightInfo()))
										{
											((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
											((Label)this.getFellow("newFlightInfo")).setValue(tmtbTxnReviewReq.getFlightInfo());
										}
									}
									else if (tmtbAcquireTxn.getFlightInfo() != null)
									{
										// this means that the tmtbAcquireTxn had value but the new one does not have value
										((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
										((Label)this.getFellow("newFlightInfo")).setValue(TO_BLANK);
									}
									
									
									if (tmtbTxnReviewReq.getPassengerName() != null)
									{
										if (!tmtbTxnReviewReq.getPassengerName().equals(tmtbAcquireTxn.getPassengerName()))
										{
											((Row)this.getFellow("newPaxRow")).setVisible(true);
											((Label)this.getFellow("newPaxName")).setValue(tmtbTxnReviewReq.getPassengerName());
										}
									}
									else if (tmtbAcquireTxn.getPassengerName() != null)
									{
										// this means that the tmtbAcquireTxn had value but the new one does not have value
										((Row)this.getFellow("newPaxRow")).setVisible(true);
										((Label)this.getFellow("newPaxName")).setValue(TO_BLANK);
									}
								}
								else
								{
									// Job TYPE and vehicle type
									if (tmtbTxnReviewReq.getMstbMasterTableByJobType() != null)
									{
										if (!tmtbTxnReviewReq.getMstbMasterTableByJobType().equals(tmtbAcquireTxn.getMstbMasterTableByJobType()))
										{
											MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByJobType();
											((Row)this.getFellow("newJobVehTypeRow")).setVisible(true);
											((Label)this.getFellow("newJobType")).setVisible(true);
											((Label)this.getFellow("newJobType")).setValue(mstbMasterTable.getMasterValue());
										}
									}
									if (tmtbTxnReviewReq.getMstbMasterTableByVehicleModel() != null)
									{
										if (!tmtbTxnReviewReq.getMstbMasterTableByVehicleModel().equals(tmtbAcquireTxn.getMstbMasterTableByVehicleModel()))
										{
											MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByVehicleModel();
											((Row)this.getFellow("newJobVehTypeRow")).setVisible(true);
											((Label)this.getFellow("newVehicleType")).setVisible(true);
											((Label)this.getFellow("newVehicleType")).setValue(mstbMasterTable.getMasterValue());
										}
									}
								}
							}
						}
					}
				}
				
				if (NonConfigurableConstants.TRANSACTION_STATUS_VOID.equalsIgnoreCase(tmtbAcquireTxn.getTxnStatus())
						|| 	NonConfigurableConstants.TRANSACTION_STATUS_REFUND.equalsIgnoreCase(tmtbAcquireTxn.getTxnStatus()))
				{
					Grid appGrid = (Grid) this.getFellow("appGrid");
					appGrid.setVisible(true);
					Grid appTitleGrid = (Grid) this.getFellow("appTitleGrid");
					appTitleGrid.setVisible(true);
					//Iterator<TmtbTxnReviewReq> tmtbTxnReviewReqIterator = tmtbAcquireTxn.getTmtbTxnReviewReqs().iterator();
					// Retrieve the first
					
					TmtbTxnReviewReq temp = null;
					Set<TmtbTxnReviewReq> tmtbTxnReviewReqs = tmtbAcquireTxn.getTmtbTxnReviewReqs();
					if(tmtbTxnReviewReqs!=null && !tmtbTxnReviewReqs.isEmpty()){
						List<TmtbTxnReviewReq> sortedReviewReqs =  TmtbTxnReviewReqNoOrder.reverse().sortedCopy(tmtbTxnReviewReqs);
						temp = sortedReviewReqs.get(0);
					}
					

					if (temp != null)
					{
						// To do a retrieval so that the join will be intact
						//tmtbTxnReviewReq = this.businessHelper.getTxnBusiness().getTxnReq(temp.getTxnReviewReqNo().toString());
						List<TmtbTxnReviewReqFlow> tmtbTxnReviewReqFlowList = this.businessHelper.getTxnBusiness().getTxnReqFlows(temp.getTxnReviewReqNo().toString());
				
						if (tmtbTxnReviewReqFlowList != null)
						{							
							Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReqFlowList.iterator();
							if (iter != null)
							{
								
								// Note that the record will be in the order of pending followed by approval
								// For submitter
								TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = iter.next();
								//this.getFellow("updRow").setVisible(false);
								//this.getFellow("reqRow").setVisible(true);
								//this.getFellow("updTimeRow").setVisible(false);
								//this.getFellow("reqTimeRow").setVisible(true);
								//((Label)this.getFellow("reqBy")).setValue(tmtbTxnReviewReqFlow.getSatbUser().getLoginId());
								//((Label)this.getFellow("reqDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
								//((Label)this.getFellow("reqTime")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_TIME_FORMAT));
								((Label)this.getFellow("reqRemarks")).setValue(tmtbTxnReviewReqFlow.getRemarks());

								// For approval
								tmtbTxnReviewReqFlow = iter.next();
								((Label)this.getFellow("appBy")).setValue(tmtbTxnReviewReqFlow.getSatbUser().getLoginId());
								((Label)this.getFellow("appDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
								((Label)this.getFellow("appTime")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_TIME_FORMAT));
								((Label)this.getFellow("appRemarks")).setValue(tmtbTxnReviewReqFlow.getRemarks());
							}
						}
					}
					
				} else {
					
					if(!(tmtbAcquireTxn.getTxnStatus().equals(NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE) || tmtbAcquireTxn.getTxnStatus().equals(NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED))){
						List<Object[]> previousObj = this.businessHelper.getTxnBusiness().getPreviousApproval(tmtbAcquireTxn.getJobNo());
						
						if(previousObj.size() != 0 )
						{
							Object[] object = previousObj.get(0);						
							((Label)this.getFellow("appBy")).setValue(object[3].toString());
							((Label)this.getFellow("appDate")).setValue(DateUtil.convertUtilDateToStr((Date) object[2], DateUtil.GLOBAL_DATE_FORMAT));
							((Label)this.getFellow("appTime")).setValue(DateUtil.convertUtilDateToStr((Date) object[2], DateUtil.GLOBAL_TIME_FORMAT));
							
							if(object[0] != null)
								((Label)this.getFellow("appRemarks")).setValue(object[0].toString());
						}
					}
	
				}
			
				Listbox remarkLists = (Listbox)this.getFellow("remarkLists");
				remarkLists.getItems().clear();
				List<TmtbTxnReviewReq> results = this.businessHelper.getTxnBusiness().searchRemark(tmtbAcquireTxn.getJobNo(), tmtbAcquireTxn.getAcquireTxnNo(), tmtbAcquireTxn.getTxnStatus());
				
				if (results != null && !results.isEmpty())
				{						
					for(TmtbTxnReviewReq txnReview : results)
					{
						String remarkHistory = "-";
						String remarkLastUpdateBy = "-";
						Timestamp remarkUpdateTime = null;
						
						Listitem remarkList = new Listitem(); 
						remarkLists.appendChild(remarkList);
						remarkList.setValue(txnReview);
						
						for(TmtbTxnReviewReqFlow txnReviewFlow : txnReview.getTmtbTxnReviewReqFlows()){
							remarkUpdateTime = txnReviewFlow.getFlowDt();
							remarkLastUpdateBy = txnReviewFlow.getSatbUser().getLoginId();
						}

						remarkList.appendChild(newListcell(remarkLastUpdateBy));
						
						if(remarkUpdateTime != null)
						{
							remarkList.appendChild(newListcell(DateUtil.convertUtilDateToStr(remarkUpdateTime, DateUtil.GLOBAL_DATE_FORMAT)));
							remarkList.appendChild(newListcell(DateUtil.convertUtilDateToStr(remarkUpdateTime, DateUtil.GLOBAL_TIME_FORMAT)));
						}
						else
						{
							remarkList.appendChild(newListcell("-"));
							remarkList.appendChild(newListcell("-"));
						}
						
						if(txnReview.getRemarks() != null)
							 remarkHistory = txnReview.getRemarks();
						
						remarkList.appendChild(newListcell(remarkHistory));
					}
				}
				
				if(results.size()<1){
					if(remarkLists.getListfoot()!=null)
						remarkLists.removeChild(remarkLists.getListfoot());	
					remarkLists.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
				}
				
				remarkLists.setVisible(true);

			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void setContact(AmtbAccount amtbAccount, String acctCat)
	{
		// Get the first main contact since there should only be 1.
		Iterator<AmtbAcctMainContact> mainContacts = null;
/*		if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAccount().getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAcctMainContacts().iterator();			
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAcctMainContacts().iterator();
		}*/
		mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		// Set main contact information
		AmtbAcctMainContact amtbAccountMainContact = mainContacts.next();
		String mainContactName = amtbAccountMainContact.getAmtbContactPerson().getMainContactName();
		String secContactName = amtbAccountMainContact.getAmtbContactPerson().getSubContactName();
		Label billContact = (Label) this.getFellow("billContact");
		if (mainContactName != null && !"".equals(mainContactName))
		{
			if (secContactName != null && !"".equals(secContactName))
			{
				billContact.setValue(mainContactName + "/" + secContactName);
			}
			else
			{
				billContact.setValue(mainContactName);							
			}
		}
	}
	
	public void checkForEditTxnAccess(Button button){
		if(this.checkUriAccess(Uri.EDIT_TXN)) button.setVisible(true);
		else button.setVisible(false);
	}
	
	private void setContact(AmtbAccount amtbAccount, String acctCat, String labelName, String rowLabelName)
	{
		// Get the first main contact since there should only be 1.
		Iterator<AmtbAcctMainContact> mainContacts = null;
/*		if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAccount().getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAcctMainContacts().iterator();			
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAcctMainContacts().iterator();
		}*/
		mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		// Set main contact information
		if (mainContacts != null)
		{
			AmtbAcctMainContact amtbAccountMainContact = mainContacts.next();
			String mainContactName = amtbAccountMainContact.getAmtbContactPerson().getMainContactName();
			String secContactName = amtbAccountMainContact.getAmtbContactPerson().getSubContactName();
			Label billContact = (Label) this.getFellow(labelName);
			Row billContactRow = (Row) this.getFellow(rowLabelName);
			billContactRow.setVisible(true);
			if (mainContactName != null && !"".equals(mainContactName))
			{
				if (secContactName != null && !"".equals(secContactName))
				{
					billContact.setValue(mainContactName + "/" + secContactName);
				}
				else
				{
					billContact.setValue(mainContactName);							
				}
			}
		}
	}
	
	public void exportResult() throws InterruptedException, IOException {
		logger.info("exportResult");
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("viewTxnDetails"), "Account Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("txnDetailsGrid"), "Transaction Details", null));
		Grid newTrxDetails = (Grid)this.getFellow("newTxnDetailsGrid");
		if (newTrxDetails.isVisible()) {
			items.add(new PdfExportItem(newTrxDetails, "New Transaction Details", null));
		}
		items.add(new PdfExportItem((Grid)this.getFellow("gridLastUpdated"), "Last Updated", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("remarkLists"), "Submitter Remarks History", null));
		items.add(new PdfExportItem((Grid)this.getFellow("appGrid"), "Approval", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter();
		try{
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("View_Trip_Txn.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
}
