package com.cdgtaxi.ibs.txn.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.ConcurrencyFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReq;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReqFlow;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class AppTxnReqWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(AppTxnReqWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> txnStatus = new ArrayList<Listitem>();
	private String txnReqID = null;
	private TmtbTxnReviewReq tmtbTxnReviewReq = null;
	private TmtbAcquireTxn oldAcquireTxn = null;
	private static final String TO_BLANK = "(CHANGED TO BLANK)";
	private Boolean isCardless = new Boolean (false);

	public AppTxnReqWindow() throws InterruptedException{
		// adding all txn status
		txnStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.TXN_STATUS.keySet()){
			txnStatus.add(new Listitem(NonConfigurableConstants.TXN_STATUS.get(statusCode), statusCode));
		}
		Map<String, String> map = Executions.getCurrent().getArg();
		txnReqID = (String) map.get("txnReqID");
	}

	public void refresh() throws InterruptedException {
		logger.debug("App Txn Window refresh()");
	}

	public void approve() throws InterruptedException
	{
		try
		{
			String remarks = ((CapsTextbox)this.getFellow("appRemarks")).getValue();
			// *************Validation
//			if (remarks == null || "".equals(remarks))
//			{
//				Messagebox.show("Please enter the remarks", "Approve Transaction Request", Messagebox.OK, Messagebox.EXCLAMATION);
//				return;				
//			}
			String user = getUserLoginIdAndDomain();
			// Retrieve the old txn
			if (tmtbTxnReviewReq != null)
			{
				if(this.businessHelper.getTxnBusiness().updateTxn(oldAcquireTxn, tmtbTxnReviewReq, remarks, user, NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED))
				{
					Messagebox.show("Approve Trip", "Trip is approved", Messagebox.OK, Messagebox.INFORMATION);
					
				}else{
					Messagebox.show("Unable to save trip. Please try again later", "Approve Trip", Messagebox.OK, Messagebox.ERROR);
				}
				this.back();			
			}
			else
			{
				throw new Exception("tmtbTxnReviewReq is null");
			}
		}
		catch (WrongValueException wve)
		{
			Messagebox.show("Please enter the mandatory fields", "Approve Trip", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (ConcurrencyFailureException e)
		{
			Messagebox.show("There exists concurrent user approves or rejects the same trip. Please check whether trip has been approved or rejected.", "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void reject() throws InterruptedException
	{
		
		try
		{
			String remarks = ((CapsTextbox)this.getFellow("appRemarks")).getValue();
			// *************Validation
			if (remarks == null || "".equals(remarks))
			{
				Messagebox.show("Please enter the remarks", "Approve Transaction Request", Messagebox.OK, Messagebox.EXCLAMATION);
				return;				
			}
			String user = getUserLoginIdAndDomain();
			// Retrieve the old txn
			if (tmtbTxnReviewReq != null)
			{
				if(this.businessHelper.getTxnBusiness().updateTxn(oldAcquireTxn, tmtbTxnReviewReq, remarks, user, NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED))
				{
					Messagebox.show("Trip is rejected", "Reject Trip", Messagebox.OK, Messagebox.INFORMATION);
					
				}else{
					Messagebox.show("Unable to save trip. Please try again later", "Approve Trip", Messagebox.OK, Messagebox.ERROR);
				}
				this.back();				
			}
			else
			{
				throw new Exception("tmtbTxnReviewReq is null");
			}
		}
		catch (ConcurrencyFailureException e)
		{
			Messagebox.show("There exists concurrent user approves or rejects the same trip. Please check whether trip has been approved or rejected.", "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	
	public void init() throws InterruptedException
	{
		try
		{
			tmtbTxnReviewReq = this.businessHelper.getTxnBusiness().getTxnReq(txnReqID);
			// Get the txn tied to it
			oldAcquireTxn = tmtbTxnReviewReq.getTmtbAcquireTxn();
			if (oldAcquireTxn != null)
			{
				List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
				for(PmtbProductType cardlessProduct : cardlessProducts)
				{
					if(cardlessProduct.getProductTypeId().equals(oldAcquireTxn.getPmtbProductType().getProductTypeId()))
					{
						isCardless = true;
						break;
					}
				}
				
				// Populate account
				if (oldAcquireTxn.getAmtbAccount()!= null)
				{
					AmtbAccount amtbAccount = oldAcquireTxn.getAmtbAccount();
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
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT, "billingContact", "billingContactRow");
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
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION, "billingContact", "billingContactRow");

					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(amtbAccount.getAccountCategory()))
					{
						// It is a corp
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE, "billingContact", "billingContactRow");
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(amtbAccount.getAccountCategory()))
					{
						// Main applicant only
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, "billingContact", "billingContactRow");
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(amtbAccount.getAccountCategory()))
					{
						// Sub applicant
						((Row)this.getFellow("applicantSubApplicantRow")).setVisible(true);
						((Label)this.getFellow("subApplicantLabel")).setVisible(true);
						((Label)this.getFellow("subApplicant")).setValue(amtbAccount.getAccountName());
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT, "billingContact", "billingContactRow" );
					}
				}
				else
				{
					throw new Exception("Account is not created properly");
				}
				if (oldAcquireTxn.getPmtbProduct() != null)
				{
					PmtbProduct pmtbProduct = oldAcquireTxn.getPmtbProduct();
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
					if (oldAcquireTxn.getExternalCardNo() != null && !"".equals(oldAcquireTxn.getExternalCardNo()))
					{
						((Row)this.getFellow("cardNoRow")).setVisible(true);
						((Label)this.getFellow("cardNo")).setValue(oldAcquireTxn.getExternalCardNo());
						((Label)this.getFellow("nameOnCard")).setValue("-");
					}
				}
				if (oldAcquireTxn.getPmtbProductType() != null)
				{
//					if (!NonConfigurableConstants.PREMIER_SERVICE.equals(oldAcquireTxn.getPmtbProductType().getProductTypeId()))
					if(!isCardless)
					{
						((Label)this.getFellow("productType")).setValue(oldAcquireTxn.getPmtbProductType().getName());
					}
				}
				if (oldAcquireTxn.getTaxiNo() != null && !"".equals(oldAcquireTxn.getTaxiNo()))
				{
					((Label)this.getFellow("taxiNo")).setValue(oldAcquireTxn.getTaxiNo());
				}
				if (oldAcquireTxn.getNric() != null && !"".equals(oldAcquireTxn.getNric()))
				{
					((Label)this.getFellow("nric")).setValue(StringUtil.maskNric(oldAcquireTxn.getNric()));
				}
				if (oldAcquireTxn.getTripStartDt() != null)
				{
					((Label)this.getFellow("tripStartDate")).setValue(DateUtil.convertUtilDateToStr(oldAcquireTxn.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT));
					((Label)this.getFellow("tripStartTime")).setValue(DateUtil.convertUtilDateToStr(oldAcquireTxn.getTripStartDt(), DateUtil.GLOBAL_TIME_FORMAT));
				}
				if (oldAcquireTxn.getTripEndDt() != null)
				{
					((Label)this.getFellow("tripEndDate")).setValue(DateUtil.convertUtilDateToStr(oldAcquireTxn.getTripEndDt(), DateUtil.GLOBAL_DATE_FORMAT));					
					((Label)this.getFellow("tripEndTime")).setValue(DateUtil.convertUtilDateToStr(oldAcquireTxn.getTripEndDt(), DateUtil.GLOBAL_TIME_FORMAT));					
				}
				// company code
				if (oldAcquireTxn.getMstbMasterTableByServiceProvider() != null)
				{
					((Label)this.getFellow("companyCd")).setValue(oldAcquireTxn.getMstbMasterTableByServiceProvider().getMasterValue());
				}
				if (oldAcquireTxn.getJobNo() != null && !"".equals(oldAcquireTxn.getJobNo()))
				{
					((Label)this.getFellow("jobNo")).setValue(oldAcquireTxn.getJobNo());
				}
				if (oldAcquireTxn.getTxnStatus() != null && !"".equals(oldAcquireTxn.getTxnStatus()))
				{
					((Label)this.getFellow("txnStatus")).setValue(NonConfigurableConstants.TXN_STATUS.get(oldAcquireTxn.getTxnStatus()));
				}
				
				if (oldAcquireTxn.getFareAmt() != null)
				{
					((Label)this.getFellow("fareAmt")).setValue(oldAcquireTxn.getFareAmt().setScale(2).toString());
				}
				
				if (NonConfigurableConstants.getBoolean(oldAcquireTxn.getPmtbProductType().getPrepaid())){
					((Row)this.getFellow("prepaidAdminRow")).setVisible(true);
					((Label)this.getFellow("prepaidAdminFee")).setValue(StringUtil.bigDecimalToStringWithGDFormat(oldAcquireTxn.getAdminFeeValue()));
					((Label)this.getFellow("prepaidGst")).setValue(StringUtil.bigDecimalToStringWithGDFormat(oldAcquireTxn.getGstValue()));
				} else {
					
					((Row)this.getFellow("prepaidAdminRow")).setVisible(false);
				}
				
				
				if (oldAcquireTxn.getComplimentary() != null && !"".equals(oldAcquireTxn.getComplimentary()))
				{
					((Label)this.getFellow("complimentary")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(oldAcquireTxn.getComplimentary()));
				}
				
				if (oldAcquireTxn.getSalesDraftNo() != null)
				{
					((Row)this.getFellow("salesDraftRow")).setVisible(true);
					((Label)this.getFellow("salesDraft")).setVisible(true);
					((Label)this.getFellow("salesDraft")).setValue(oldAcquireTxn.getSalesDraftNo());
				}
				
				if (oldAcquireTxn.getPickupAddress() != null  && !"".equals(oldAcquireTxn.getPickupAddress()))
				{
					((Label)this.getFellow("pickup")).setValue(oldAcquireTxn.getPickupAddress());
				}
				if (oldAcquireTxn.getDestination() != null && !"".equals(oldAcquireTxn.getDestination()))
				{
					((Label)this.getFellow("destination")).setValue(oldAcquireTxn.getDestination());
				}
				
				if (oldAcquireTxn.getRemarks() != null && !"".equals(oldAcquireTxn.getRemarks()))
				{
					((Label)this.getFellow("remarks")).setVisible(true);
					((Label)this.getFellow("remarks")).setValue(oldAcquireTxn.getRemarks());
				}
				
				if (oldAcquireTxn.getFmsAmt() != null && !"".equals(oldAcquireTxn.getFmsAmt()))
				{
					((Label)this.getFellow("FMSAmount")).setVisible(true);
					((Label)this.getFellow("FMSAmount")).setValue(oldAcquireTxn.getFmsAmt().setScale(2).toString());
				}
				if (oldAcquireTxn.getIncentiveAmt() != null && !"".equals(oldAcquireTxn.getIncentiveAmt()))
				{
					((Label)this.getFellow("incentiveAmt")).setVisible(true);
					((Label)this.getFellow("incentiveAmt")).setValue(oldAcquireTxn.getIncentiveAmt().setScale(2).toString());
				}
				if (oldAcquireTxn.getPromoAmt() != null && !"".equals(oldAcquireTxn.getPromoAmt()))
				{
					((Label)this.getFellow("promoAmt")).setVisible(true);
					((Label)this.getFellow("promoAmt")).setValue(oldAcquireTxn.getPromoAmt().setScale(2).toString());
				}
				if (oldAcquireTxn.getCabRewardsAmt() != null && !"".equals(oldAcquireTxn.getCabRewardsAmt()))
				{
					((Label)this.getFellow("cabRewardsAmt")).setVisible(true);
					((Label)this.getFellow("cabRewardsAmt")).setValue(oldAcquireTxn.getCabRewardsAmt().setScale(2).toString());
				}
				if (oldAcquireTxn.getFmsFlag() != null && !"".equals(oldAcquireTxn.getFmsFlag()))
				{
					((Label)this.getFellow("toUpdateFMSList")).setVisible(true);
					((Label)this.getFellow("toUpdateFMSList")).setValue(oldAcquireTxn.getFmsFlag());
				}
				if (oldAcquireTxn.getUpdateFms() != null && !"".equals(oldAcquireTxn.getUpdateFms()))
				{
					((Label)this.getFellow("updateFMSList")).setVisible(true);
					((Label)this.getFellow("updateFMSList")).setValue(oldAcquireTxn.getUpdateFms());
				}
				
				if (oldAcquireTxn.getRemarks() != null && !"".equals(oldAcquireTxn.getRemarks()))
				{
					((Label)this.getFellow("remarks")).setVisible(true);
					((Label)this.getFellow("remarks")).setValue(oldAcquireTxn.getRemarks());
				}
				
				if (oldAcquireTxn.getProjectDesc() != null && !"".equals(oldAcquireTxn.getProjectDesc()))
				{
					((Row)this.getFellow("projCodeRow")).setVisible(true);
					((Label)this.getFellow("projCode")).setValue(oldAcquireTxn.getProjectDesc());
				}
				
				if (oldAcquireTxn.getTripCodeReason() != null && !"".equals(oldAcquireTxn.getTripCodeReason()))
				{
					((Row)this.getFellow("projCodeReasonRow")).setVisible(true);
					((Label)this.getFellow("projCodeReason")).setValue(oldAcquireTxn.getTripCodeReason());
				}

				if (oldAcquireTxn.getSurcharge() != null  && !"".equals(oldAcquireTxn.getSurcharge()))
				{
					((Row)this.getFellow("surchargeRow")).setVisible(true);
					((Label)this.getFellow("surchargesRemarks")).setVisible(true);
					((Label)this.getFellow("surchargesRemarks")).setValue(oldAcquireTxn.getSurcharge());
				}
				// TRIP TYPE and vehicle group
				if (oldAcquireTxn.getMstbMasterTableByTripType() != null)
				{
					((Row)this.getFellow("newTripTypeRow")).setVisible(true);
					MstbMasterTable mstbMasterTable = oldAcquireTxn.getMstbMasterTableByTripType();	
					((Label)this.getFellow("tripType")).setVisible(true);
					((Label)this.getFellow("tripType")).setValue(mstbMasterTable.getMasterValue());
				}
				
				// Premier fields
//				if (NonConfigurableConstants.PREMIER_SERVICE.equals(oldAcquireTxn.getPmtbProductType().getProductTypeId()))
				if(isCardless)
				{
					if (oldAcquireTxn.getPmtbProductType() != null)
					{
						((Row)this.getFellow("productTypeRow")).setVisible(true);
						((Label)this.getFellow("productType_p")).setValue(oldAcquireTxn.getPmtbProductType().getName());
					}
					
					if (oldAcquireTxn.getBookingRef() != null && !"".equals(oldAcquireTxn.getBookingRef()))
					{
						((Row)this.getFellow("flightInfoRow")).setVisible(true);
						((Label)this.getFellow("bookedRef")).setVisible(true);
						((Label)this.getFellow("bookedRef")).setValue(oldAcquireTxn.getBookingRef());
					}
					if (oldAcquireTxn.getFlightInfo() != null && !"".equals(oldAcquireTxn.getFlightInfo()))
					{
						((Row)this.getFellow("flightInfoRow")).setVisible(true);
						((Label)this.getFellow("flightInfo")).setVisible(true);
						((Label)this.getFellow("flightInfo")).setValue(oldAcquireTxn.getFlightInfo());
					}
					
					
					if (oldAcquireTxn.getBookedBy() != null && !"".equals(oldAcquireTxn.getBookedBy()))
					{
						((Row)this.getFellow("bookedByBookedDateRow")).setVisible(true);
						((Label)this.getFellow("bookedBy")).setVisible(true);
						((Label)this.getFellow("bookedBy")).setValue(oldAcquireTxn.getBookedBy());
					}
					
					if (oldAcquireTxn.getBookDateTime() != null)
					{
						((Row)this.getFellow("bookedByBookedDateRow")).setVisible(true);
						((Label)this.getFellow("bookedDate")).setVisible(true);
						((Label)this.getFellow("bookedDate")).setValue(DateUtil.convertUtilDateToStr(oldAcquireTxn.getBookDateTime(),DateUtil.GLOBAL_DATE_FORMAT));
					}

					if (oldAcquireTxn.getMstbMasterTableByVehicleType() != null)
					{
						((Row)this.getFellow("tripRow")).setVisible(true);
						MstbMasterTable mstbMasterTable = oldAcquireTxn.getMstbMasterTableByVehicleType();
						((Label)this.getFellow("vehicleGroup")).setVisible(true);
						((Label)this.getFellow("vehicleGroup")).setValue(mstbMasterTable.getMasterValue());
					}
					if (oldAcquireTxn.getMstbMasterTableByJobType() != null)
					{
						((Row)this.getFellow("tripRow")).setVisible(true);
						MstbMasterTable mstbMasterTable = oldAcquireTxn.getMstbMasterTableByJobType();	
						((Label)this.getFellow("premJobType")).setVisible(true);
						((Label)this.getFellow("premJobType")).setValue(mstbMasterTable.getMasterValue());
					}
					if (oldAcquireTxn.getMstbMasterTableByVehicleModel() != null)
					{
						((Row)this.getFellow("vehicleRow")).setVisible(true);
						MstbMasterTable mstbMasterTable = oldAcquireTxn.getMstbMasterTableByVehicleModel();
						((Label)this.getFellow("premVehicleType")).setVisible(true);
						((Label)this.getFellow("premVehicleType")).setValue(mstbMasterTable.getMasterValue());
					}
					
					
					if (oldAcquireTxn.getPassengerName() != null && !"".equals(oldAcquireTxn.getPassengerName()))
					{
						((Row)this.getFellow("paxNameLevyRow")).setVisible(true);
						((Label)this.getFellow("paxName")).setVisible(true);
						((Label)this.getFellow("paxName")).setValue(oldAcquireTxn.getPassengerName());
					}
					
					if (oldAcquireTxn.getLevy() != null)
					{
						((Label)this.getFellow("levyLabel")).setVisible(true);
						((Label)this.getFellow("levy")).setVisible(true);
						((Label)this.getFellow("levy")).setValue(oldAcquireTxn.getLevy().setScale(2).toString());
					}
					else
					{
						((Label)this.getFellow("levyLabel")).setVisible(true);
						((Label)this.getFellow("levy")).setVisible(true);
						((Label)this.getFellow("levy")).setValue("-");
					}
				}
				else
				{
					((Row)this.getFellow("jobVehTypeRow")).setVisible(true);
					if (oldAcquireTxn.getMstbMasterTableByJobType() != null)
					{
						MstbMasterTable mstbMasterTable = oldAcquireTxn.getMstbMasterTableByJobType();	
						((Label)this.getFellow("jobType")).setVisible(true);
						((Label)this.getFellow("jobType")).setValue(mstbMasterTable.getMasterValue());
					}
					if (oldAcquireTxn.getMstbMasterTableByVehicleModel() != null)
					{
						MstbMasterTable mstbMasterTable = oldAcquireTxn.getMstbMasterTableByVehicleModel();
						((Label)this.getFellow("vehicleType")).setVisible(true);
						((Label)this.getFellow("vehicleType")).setValue(mstbMasterTable.getMasterValue());
					}
				}

			}

			if (tmtbTxnReviewReq != null)
			{
				((Label)this.getFellow("actionTxn")).setValue(tmtbTxnReviewReq.getActionTxn());
			
				boolean checkTxnBilled = true;
				if(oldAcquireTxn != null)
				{
					if(oldAcquireTxn.getTxnStatus().equals(NonConfigurableConstants.TRANSACTION_STATUS_BILLED))
						checkTxnBilled = false;
				}
				
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
					if (oldAcquireTxn.getAmtbAccount().getAccountNo() != amtbAccount.getAccountNo())
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
					if (!tmtbTxnReviewReq.getPickupAddress().equals(oldAcquireTxn.getPickupAddress()))
					{
						((Row)this.getFellow("newPickupRow")).setVisible(true);
						((Label)this.getFellow("newPickup")).setVisible(true);
						((Label)this.getFellow("newPickup")).setValue(tmtbTxnReviewReq.getPickupAddress());
					}
				}
				
				if (tmtbTxnReviewReq.getDestination() != null)
				{
					if (!tmtbTxnReviewReq.getDestination().equals(oldAcquireTxn.getDestination()))
					{
						((Row)this.getFellow("newDestRow")).setVisible(true);
						((Label)this.getFellow("newDestination")).setVisible(true);
						((Label)this.getFellow("newDestination")).setValue(tmtbTxnReviewReq.getDestination());
					}
				}
				else if (oldAcquireTxn.getDestination() != null)
				{
					// this means that the oldAcquireTxn had value but the new one does not have value
					((Row)this.getFellow("newDestRow")).setVisible(true);
					((Label)this.getFellow("newDestination")).setVisible(true);
					((Label)this.getFellow("newDestination")).setValue(TO_BLANK);
				}
				
				if (tmtbTxnReviewReq.getMstbMasterTableByServiceProvider() != null)
				{
					if (!tmtbTxnReviewReq.getMstbMasterTableByServiceProvider().equals(oldAcquireTxn.getMstbMasterTableByServiceProvider()))
					{
						((Row)this.getFellow("newCompanyCdRow")).setVisible(true);
						((Label)this.getFellow("newCompanyCd")).setVisible(true);
						((Label)this.getFellow("newCompanyCd")).setValue(tmtbTxnReviewReq.getMstbMasterTableByServiceProvider().getMasterValue());
					}
				}
				if (tmtbTxnReviewReq.getFareAmt() != null)
				{
					if (!tmtbTxnReviewReq.getFareAmt().toString().equals(oldAcquireTxn.getFareAmt().toString()))
					{
						((Row)this.getFellow("newFareRow")).setVisible(true);
						((Label)this.getFellow("newFareAmt")).setValue(tmtbTxnReviewReq.getFareAmt().setScale(2).toString());
					}
				}
				
				if (tmtbTxnReviewReq.getAdminFeeValue() != null)
				{
					if (!tmtbTxnReviewReq.getAdminFeeValue().toString().equals(oldAcquireTxn.getAdminFeeValue().toString()))
					{
						((Row)this.getFellow("newPrepaidAdminFeeRow")).setVisible(true);
						((Label)this.getFellow("newPrepaidAdminFee")).setValue(tmtbTxnReviewReq.getAdminFeeValue().setScale(2).toString());
					}
				}
				
				if (tmtbTxnReviewReq.getGstValue() != null)
				{
					if (!tmtbTxnReviewReq.getGstValue().toString().equals(oldAcquireTxn.getGstValue().toString()))
					{
						((Row)this.getFellow("newPrepaidGstRow")).setVisible(true);
						((Label)this.getFellow("newPrepaidGst")).setValue(tmtbTxnReviewReq.getGstValue().setScale(2).toString());
					}
				}
				
				
				
				if (tmtbTxnReviewReq.getSalesDraftNo() != null)
				{
					if (!tmtbTxnReviewReq.getSalesDraftNo().equals(oldAcquireTxn.getSalesDraftNo()))
					{
						((Row)this.getFellow("newFareRow")).setVisible(true);
						((Label)this.getFellow("newSalesDraft")).setVisible(true);
						((Label)this.getFellow("newSalesDraft")).setValue(tmtbTxnReviewReq.getSalesDraftNo());
					}
				}
				else if (oldAcquireTxn.getSalesDraftNo() != null)
				{
					// this means that the oldAcquireTxn had value but the new one does not have value
					((Row)this.getFellow("newFareRow")).setVisible(true);
					((Label)this.getFellow("newSalesDraft")).setVisible(true);
					((Label)this.getFellow("newSalesDraft")).setValue(TO_BLANK);
				}
				
				// Complimentary
				if (tmtbTxnReviewReq.getComplimentary() != null)
				{
					if (!tmtbTxnReviewReq.getComplimentary().equals(oldAcquireTxn.getComplimentary()))
					{
						((Row)this.getFellow("newComplimentaryRow")).setVisible(true);
						((Label)this.getFellow("newComplimentary")).setValue(tmtbTxnReviewReq.getComplimentary());
					}
				}
				
				if (tmtbTxnReviewReq.getProjectDesc() != null)
				{
					if (!tmtbTxnReviewReq.getProjectDesc().equals(oldAcquireTxn.getProjectDesc()))
					{
						((Row)this.getFellow("newProjCodeRow")).setVisible(true);
						((Label)this.getFellow("newProjCode")).setValue(tmtbTxnReviewReq.getProjectDesc());
					}
				}
				else if (oldAcquireTxn.getProjectDesc() != null)
				{
					// this means that the oldAcquireTxn had value but the new one does not have value
					((Row)this.getFellow("newProjCodeRow")).setVisible(true);
					((Label)this.getFellow("newProjCode")).setValue(TO_BLANK);
				}
				
				if (tmtbTxnReviewReq.getTripCodeReason() != null)
				{
					if (!tmtbTxnReviewReq.getTripCodeReason().equals(oldAcquireTxn.getTripCodeReason()))
					{
						((Row)this.getFellow("newProjCodeReasonRow")).setVisible(true);
						((Label)this.getFellow("newProjCodeReason")).setValue(tmtbTxnReviewReq.getTripCodeReason());
					}
				}
				else if (oldAcquireTxn.getTripCodeReason() != null)
				{
					// this means that the oldAcquireTxn had value but the new one does not have value
					((Row)this.getFellow("newProjCodeReasonRow")).setVisible(true);
					((Label)this.getFellow("newProjCodeReason")).setValue(TO_BLANK);
				}
				
				
				if (tmtbTxnReviewReq.getRemarks() != null)
				{
					if (!tmtbTxnReviewReq.getRemarks().equals(oldAcquireTxn.getRemarks()))
					{
						((Row)this.getFellow("newRemarksRow")).setVisible(true);
						((Label)this.getFellow("newRemarks")).setVisible(true);
						((Label)this.getFellow("newRemarks")).setValue(tmtbTxnReviewReq.getRemarks());
					}
				}
				else if (oldAcquireTxn.getRemarks() != null)
				{
					// this means that the oldAcquireTxn had value but the new one does not have value
					((Row)this.getFellow("newRemarksRow")).setVisible(true);
					((Label)this.getFellow("newRemarks")).setVisible(true);
					((Label)this.getFellow("newRemarks")).setValue(TO_BLANK);
				}
				if (tmtbTxnReviewReq.getTripStartDt() != null)
				{
					if (tmtbTxnReviewReq.getTripStartDt().compareTo(oldAcquireTxn.getTripStartDt()) != 0)
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
					if (tmtbTxnReviewReq.getTripEndDt().compareTo(oldAcquireTxn.getTripEndDt()) != 0)
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
					((Label)this.getFellow("reqBy")).setValue(tmtbTxnReviewReqFlow.getSatbUser().getLoginId());
					((Label)this.getFellow("reqDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReqFlow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
					((Label)this.getFellow("reqRemarks")).setValue(tmtbTxnReviewReqFlow.getRemarks());
				}

				if (tmtbTxnReviewReq.getFmsAmt() != null)
				{
					//if (oldAcquireTxn.getFmsAmt() != null)
					//{
						//if (tmtbTxnReviewReq.getFmsAmt().compareTo(oldAcquireTxn.getFmsAmt()) != 0)
						//{
							((Label)this.getFellow("newFMSAmount")).setVisible(true);
							((Label)this.getFellow("newFMSAmount")).setValue(tmtbTxnReviewReq.getFmsAmt().setScale(2).toString());
						//}
					//}
					//else
					//{
						//((Label)this.getFellow("newFMSAmount")).setVisible(true);
						//((Label)this.getFellow("newFMSAmount")).setValue(tmtbTxnReviewReq.getFmsAmt().setScale(2).toString());
					//}
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
					//if (tmtbTxnReviewReq.getUpdateFms().toString().equals(oldAcquireTxn.getUpdateFms()))
					//{
						((Label)this.getFellow("newUpdateFMSList")).setVisible(true);
						((Label)this.getFellow("newUpdateFMSList")).setValue(tmtbTxnReviewReq.getUpdateFms());
					//}
					
				}
				
				if (tmtbTxnReviewReq.getFmsFlag() != null)
				{
					//if (tmtbTxnReviewReq.getFmsFlag().equals(oldAcquireTxn.getFmsFlag()))
					//{
						((Label)this.getFellow("newToUpdateFMSList")).setVisible(true);
						((Label)this.getFellow("newToUpdateFMSList")).setValue(tmtbTxnReviewReq.getFmsFlag());
					//}
				}
				if (tmtbTxnReviewReq.getSurcharge() != null)
				{
					if (!tmtbTxnReviewReq.getSurcharge().equals(oldAcquireTxn.getSurcharge()))
					{
						((Row)this.getFellow("newSurchargeRow")).setVisible(true);
						((Label)this.getFellow("newSurchargesRemarks")).setValue(tmtbTxnReviewReq.getSurcharge());
					}
				}
				else if (oldAcquireTxn.getSurcharge() != null)
				{
					// this means that the oldAcquireTxn had value but the new one does not have value
					((Row)this.getFellow("newSurchargeRow")).setVisible(true);
					((Label)this.getFellow("newSurchargesRemarks")).setValue(TO_BLANK);
				}
				
				// TRIP TYPE and vehicle group
				if (tmtbTxnReviewReq.getMstbMasterTableByTripType() != null)
				{
					if (!tmtbTxnReviewReq.getMstbMasterTableByTripType().equals(oldAcquireTxn.getMstbMasterTableByTripType()))
					{
						MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByTripType();
						((Row)this.getFellow("newTripTypeRow")).setVisible(true);
						((Label)this.getFellow("newTripType")).setVisible(true);
						((Label)this.getFellow("newTripType")).setValue(mstbMasterTable.getMasterValue());
					}
				}
				
//				if (NonConfigurableConstants.PREMIER_SERVICE.equals(tmtbTxnReviewReq.getPmtbProductType().getProductTypeId()))
				if(isCardless)
				{
					if (tmtbTxnReviewReq.getMstbMasterTableByVehicleType() != null)
					{
						if (!tmtbTxnReviewReq.getMstbMasterTableByVehicleType().equals(oldAcquireTxn.getMstbMasterTableByVehicleType()))
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
						if (!tmtbTxnReviewReq.getMstbMasterTableByJobType().equals(oldAcquireTxn.getMstbMasterTableByJobType()))
						{
							MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByJobType();
							((Row)this.getFellow("newTripRow")).setVisible(true);
							((Label)this.getFellow("newPremJobType")).setVisible(true);
							((Label)this.getFellow("newPremJobType")).setValue(mstbMasterTable.getMasterValue());
						}
					}
					if (tmtbTxnReviewReq.getMstbMasterTableByVehicleModel() != null)
					{
						if (!tmtbTxnReviewReq.getMstbMasterTableByVehicleModel().equals(oldAcquireTxn.getMstbMasterTableByVehicleModel()))
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
						
						//if (oldAcquireTxn.getLevy() != null)
						//{
							//if (!tmtbTxnReviewReq.getLevy().equals(oldAcquireTxn.getLevy()))
							//{
								((Label)this.getFellow("newLevy")).setValue(tmtbTxnReviewReq.getLevy().setScale(2).toString());
							//}
						//}
						//else
						//{
							//((Label)this.getFellow("newLevy")).setValue("-");
						//}
					}
					else
					{
						((Label)this.getFellow("newLevyLabel")).setVisible(true);
						((Label)this.getFellow("newLevy")).setVisible(true);
						((Label)this.getFellow("newLevy")).setValue("-");
					}
					
					if (tmtbTxnReviewReq.getBookedBy() != null)
					{
						if (!tmtbTxnReviewReq.getBookedBy().equals(oldAcquireTxn.getBookedBy()))
						{
							((Row)this.getFellow("newBookRow")).setVisible(true);
							((Label)this.getFellow("newBookedBy")).setValue(tmtbTxnReviewReq.getBookedBy());
						}
					}
					else if (oldAcquireTxn.getBookedBy() != null)
					{
						// this means that the oldAcquireTxn had value but the new one does not have value
						((Row)this.getFellow("newBookRow")).setVisible(true);
						((Label)this.getFellow("newBookedBy")).setValue(TO_BLANK);
					}
					
					if (tmtbTxnReviewReq.getBookDateTime() != null)
					{
						if (!tmtbTxnReviewReq.getBookDateTime().equals(oldAcquireTxn.getBookDateTime()))
						{
							((Row)this.getFellow("newBookRow")).setVisible(true);
							((Label)this.getFellow("newBookedDate")).setValue(DateUtil.convertUtilDateToStr(tmtbTxnReviewReq.getBookDateTime(), DateUtil.GLOBAL_DATE_FORMAT));
						}
					}
					else if (oldAcquireTxn.getBookDateTime() != null)
					{
						// this means that the oldAcquireTxn had value but the new one does not have value
						((Row)this.getFellow("newBookRow")).setVisible(true);
						((Label)this.getFellow("newBookedDate")).setValue(TO_BLANK);
					}
					
					
					if (tmtbTxnReviewReq.getBookingRef() != null)
					{
						if (!tmtbTxnReviewReq.getBookingRef().equals(oldAcquireTxn.getBookingRef()))
						{
							((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
							((Label)this.getFellow("newBookedRef")).setValue(tmtbTxnReviewReq.getBookingRef());
						}
					}
					else if (oldAcquireTxn.getBookingRef() != null)
					{
						// this means that the oldAcquireTxn had value but the new one does not have value
						((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
						((Label)this.getFellow("newBookedRef")).setValue(TO_BLANK);
					}
					
					if (tmtbTxnReviewReq.getFlightInfo() != null)
					{
						if (!tmtbTxnReviewReq.getFlightInfo().equals(oldAcquireTxn.getFlightInfo()))
						{
							((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
							((Label)this.getFellow("newFlightInfo")).setValue(tmtbTxnReviewReq.getFlightInfo());
						}
					}
					else if (oldAcquireTxn.getFlightInfo() != null)
					{
						// this means that the oldAcquireTxn had value but the new one does not have value
						((Row)this.getFellow("newFlightInfoRow")).setVisible(true);
						((Label)this.getFellow("newFlightInfo")).setValue(TO_BLANK);
					}
					
					
					if (tmtbTxnReviewReq.getPassengerName() != null)
					{
						if (!tmtbTxnReviewReq.getPassengerName().equals(oldAcquireTxn.getPassengerName()))
						{
							((Row)this.getFellow("newPaxRow")).setVisible(true);
							((Label)this.getFellow("newPaxName")).setValue(tmtbTxnReviewReq.getPassengerName());
						}
					}
					else if (oldAcquireTxn.getPassengerName() != null)
					{
						// this means that the oldAcquireTxn had value but the new one does not have value
						((Row)this.getFellow("newPaxRow")).setVisible(true);
						((Label)this.getFellow("newPaxName")).setValue(TO_BLANK);
					}
										
				}
				else
				{
					// Job TYPE and vehicle type
					if (tmtbTxnReviewReq.getMstbMasterTableByJobType() != null)
					{
						if (!tmtbTxnReviewReq.getMstbMasterTableByJobType().equals(oldAcquireTxn.getMstbMasterTableByJobType()))
						{
							MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByJobType();
							((Row)this.getFellow("newJobVehTypeRow")).setVisible(true);
							((Label)this.getFellow("newJobType")).setVisible(true);
							((Label)this.getFellow("newJobType")).setValue(mstbMasterTable.getMasterValue());
						}
					}
					if (tmtbTxnReviewReq.getMstbMasterTableByVehicleModel() != null)
					{
						if (!tmtbTxnReviewReq.getMstbMasterTableByVehicleModel().equals(oldAcquireTxn.getMstbMasterTableByVehicleModel()))
						{
							MstbMasterTable mstbMasterTable = tmtbTxnReviewReq.getMstbMasterTableByVehicleModel();
							((Row)this.getFellow("newJobVehTypeRow")).setVisible(true);
							((Label)this.getFellow("newVehicleType")).setVisible(true);
							((Label)this.getFellow("newVehicleType")).setValue(mstbMasterTable.getMasterValue());
						}
					}
				}
				
				List<Object[]> previousRemarks = this.businessHelper.getTxnBusiness().getPreviousApproval(oldAcquireTxn.getJobNo());
				
				if(previousRemarks != null && previousRemarks.size() != 0)
				{
					Object[] object = previousRemarks.get(0);
					String remarkStatus = "";
					if((object[1].toString()).equalsIgnoreCase("R"))
						remarkStatus = "(REJECTED)";
					else if ((object[1].toString()).equalsIgnoreCase("A"))
						remarkStatus = "(APPROVED)";
					
					if(object[0] != null)
						((Label)this.getFellow("prevRemarks")).setValue(object[0].toString() + " " +remarkStatus);
					else
						((Label)this.getFellow("prevRemarks")).setValue("-");
				}
				
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
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
	
}
