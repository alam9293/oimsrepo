package com.cdgtaxi.ibs.nonbillable.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class ViewNonBillableTxnWindow extends CommonWindow implements AfterCompose {
	
	private static Logger logger = Logger.getLogger(ViewNonBillableTxnWindow.class);
	
	private Label acquirerLabel, paymentTypeLabel, cardNoLabel,
		uploadDateLabel, fareAmountLabel, jobNoLabel, adminFeeLabel,
		tripDTLabel, gstLabel, driverIdLabel, totalAmountLabel,
		taxiNoLabel, statusLabel, companyCodeLabel, remarksLabel, batchNoLabel, offlineLabel, tripTypeLabel,
		policyNoLabel, policyStatusLabel, premiumLabel, premiumGstLabel;
	
	//famous fields
	private Label createdByLabel, createdDateLabel, createdTimeLabel,
		lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	
	//Various Div to hide/unhide depending on mode
	private Div rejectDiv, viewRejectDiv, chargeBackDiv,chargeBackReverseDiv,refundDiv, refundReverseDiv, viewRefundDiv, viewChargeBackDiv,
			viewFMSChargeBackDiv, rejectFMSChargeBackDiv, updateFMSDiv;
	
	//Reject Trips
	private Button rejectButton, unRejectButton;
	private CapsTextbox remarksTextBox;
	
	//Chargeback
	private Label chargebackTotalAmountInputLabel;
	private Datebox chargebackDateDB;
	private Listbox reasonLB, typeLB;
	private Decimalbox chargebackFareDMB, chargebackAdminDMB, chargebackGstDMB;
	private CapsTextbox chargebackRemarksTextBox;
	private Button chargebackButton, updateChargebackButton;

	//Chargeback Reverse
	private Label chargebackReverseDateLabel,chargebackReverseReasonLabel,chargebackReverseMarkupLabel,
			chargebackReverseSchemeFeeLabel,chargebackReverseGrossCreditLabel, chargebackReverseCommissionLabel;

	//View Chargeback
	private Label chargebackDateLabel, chargebackReasonLabel, chargebackTypeLabel,
		chargebackTotalAmountLabel, chargebackRemarksLabel, chargebackFareAmountLabel,
		chargebackAdminFeeLabel, chargebackGSTLabel;

	//Refunded
	private Label refundDateLabel, refundReasonLabel, refundTypeLabel, refundFareAmountLabel, refundAdminFeeLabel,
			refundRemarksLabel, refundGSTLabel, refundTotalAmountLabel, refundTotalAmountInputLabel;
	private Listbox refundReasonLB, refundTypeLB;
	private Datebox refundDateDB;
	private Decimalbox refundFareDMB,refundAdminDMB,refundGstDMB;
	private CapsTextbox refundRemarksTextBox;
	private Button updateRefundButton;

	//Refunded Reverse
	private Label refundReverseDateLabel,refundReverseReasonLabel,refundReverseMarkupLabel,
			refundReverseSchemeFeeLabel,refundReverseGrossCreditLabel, refundReverseCommissionLabel;
	//Update FMS
	private Listbox updateFMSLB, updateTypeLB, cancelPolicyLB;
	private Decimalbox levyAmtDMB, fmsAmtDMB, incentiveAmtDMB, promoAmtDMB, cabRewardsAmtDMB; 
	private Label levyAmtLabel;
	private Row FMSRow;
	private Row FMSRow2;
	private Row FMSRow3;
	
	public static final String MODE_VIEW = "VIEW";
	public static final String MODE_REJECT = "REJECT";
	public static final String MODE_UNREJECT = "UNREJECT";
	public static final String MODE_CHARGEBACK = "CHARGEBACK";
	public static final String MODE_REFUNDED = "REFUNDED";
	private String mode;
	
	private TmtbNonBillableTxn txn;
	private TmtbNonBillableBatch batch;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Map params = Executions.getCurrent().getArg();
		mode = (String)params.get("mode");
		txn = (TmtbNonBillableTxn) params.get("txn");
		batch = (TmtbNonBillableBatch) params.get("batch");

		BigDecimal grossAmount = new BigDecimal("0.00");
		BigDecimal commission = new BigDecimal("0.00");
		BigDecimal markup = new BigDecimal("0.00");
		BigDecimal schemeFee = new BigDecimal("0.00");
		BigDecimal interchange = new BigDecimal("0.00");

		if(cancelPolicyLB != null)
			cancelPolicyLB.setDisabled(true);
		
		if(mode.equals(MODE_REJECT)){
			rejectDiv.setVisible(true);
			rejectButton.setVisible(true);
			
			fmsVisible(mode);
		}
		else if(mode.equals(MODE_UNREJECT)){
			rejectDiv.setVisible(true);
			unRejectButton.setVisible(true);
			remarksTextBox.setValue(txn.getRemarks());
			
			fmsVisible(mode);
		}
		else if(mode.equals(MODE_CHARGEBACK)){

			this.populateChargebackReasonAndType();

			List<TmtbNonBillableTxn> txns = new ArrayList<TmtbNonBillableTxn>();
			txns.add(txn);
			txns = this.businessHelper.getNonBillableBusiness().retrieveCrca(txns);
			txn = txns.get(0);

			//toDo Norman: to be confirmed when actual internal settlement data comes in, currently internal settlement file chargeback - 2nd file reverse chargeback = 1+2nd chargeback (if have)
			Set<TmtbNonBillableTxnCrca> reverseChargeBackTxnCrca1 = txn.getTmtbNonBillableTxnCrca1();
			Set<TmtbNonBillableTxnCrca> reverseChargeBackTxnCrca2 = txn.getTmtbNonBillableTxnCrca2();

			boolean hasChargebackReverse = false;

			if(reverseChargeBackTxnCrca1 != null) {
				for (TmtbNonBillableTxnCrca crca1 : reverseChargeBackTxnCrca1) {
					if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE)) {
						hasChargebackReverse = true;
					}
				}
			}
			if(reverseChargeBackTxnCrca2 != null) {
				for (TmtbNonBillableTxnCrca crca2 : reverseChargeBackTxnCrca2) {
					if (crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE)) {
						hasChargebackReverse = true;
					}
				}
			}

			fmsVisible(mode);

			if(txn.getNric()==null)
				updateFMSLB.setDisabled(true);
			
			logger.info("checking policy status : "+txn.getPolicyStatus());
			if(cancelPolicyLB != null)
			{
				if(txn.getPolicyStatus() != null && (txn.getPolicyStatus().equals(NonConfigurableConstants.HLA_POLICY_STATUS_ACTIVE))
						&& txn.getPolicyNumber() != null)
				{
					cancelPolicyLB.setDisabled(false);
					cancelPolicyLB.appendChild(ComponentUtil.createNotRequiredListItem());
					Set<String> ynKeys = NonConfigurableConstants.BOOLEAN_YN.keySet();
					for(String key : ynKeys){
						Listitem item = new Listitem();
						item.setValue(key);
						item.setLabel(NonConfigurableConstants.BOOLEAN_YN.get(key));
						cancelPolicyLB.appendChild(item);
					}
					cancelPolicyLB.setSelectedIndex(0);
				}
			}
			
			if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK)){
				//the chance of split chargeback is not likely. Did not cater for it
				if(hasChargebackReverse){
					chargeBackReverseDiv.setVisible(true);
					if (reverseChargeBackTxnCrca1 != null && reverseChargeBackTxnCrca1.size() >0) {
						for(TmtbNonBillableTxnCrca crca1 : reverseChargeBackTxnCrca1) {
							if(crca1 != null && crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE)) {
								if (crca1.getGrossAmount() != null)
									grossAmount = grossAmount.add(crca1.getGrossAmount()); //implemented this in batchjob
								if (crca1.getCommission() != null)
									commission = commission.add(crca1.getCommission());
								if (crca1.getMarkup() != null)
									markup = markup.add(crca1.getMarkup());
								if (crca1.getSchemeFee() != null)
									schemeFee = schemeFee.add(crca1.getSchemeFee());
								if (crca1.getInterchange() != null)
									interchange = interchange.add(crca1.getInterchange());
							}
							if(crca1 != null && (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE))){
								chargebackReverseDateLabel.setValue(DateUtil.convertDateToStr(crca1.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
								chargebackReverseReasonLabel.setValue(crca1.getChargebackReasonDescription());
							}
						}

					}

						if (reverseChargeBackTxnCrca2 != null && reverseChargeBackTxnCrca2.size() >0) {
							for(TmtbNonBillableTxnCrca crca2 : reverseChargeBackTxnCrca2) {
								if(crca2 != null && crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE)) {
									if (crca2.getGrossAmount() != null)
										grossAmount = grossAmount.add(crca2.getGrossAmount()); //implemented this in batchjob
									if (crca2.getCommission() != null)
										commission = commission.add(crca2.getCommission());
									if (crca2.getMarkup() != null)
										markup = markup.add(crca2.getMarkup());
									if (crca2.getSchemeFee() != null)
										schemeFee = schemeFee.add(crca2.getSchemeFee());
									if (crca2.getInterchange() != null)
										interchange = interchange.add(crca2.getInterchange());
								}

								if(crca2 != null && crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE)){
									chargebackReverseDateLabel.setValue(DateUtil.convertDateToStr(crca2.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
									chargebackReverseReasonLabel.setValue(crca2.getChargebackReasonDescription());
								}

							}

						}

						chargebackReverseGrossCreditLabel.setValue(StringUtil.bigDecimalToString(grossAmount,StringUtil.GLOBAL_DECIMAL_FORMAT));
						chargebackReverseMarkupLabel.setValue(StringUtil.bigDecimalToString(markup,StringUtil.GLOBAL_DECIMAL_FORMAT));
						chargebackReverseCommissionLabel.setValue(StringUtil.bigDecimalToString(commission,StringUtil.GLOBAL_DECIMAL_FORMAT));
						chargebackReverseSchemeFeeLabel.setValue(StringUtil.bigDecimalToString(schemeFee,StringUtil.GLOBAL_DECIMAL_FORMAT));
					}

				updateChargebackButton.setVisible(true);
				if(txn.getChargebackRefundDate() != null)
					chargebackDateDB.setValue(txn.getChargebackRefundDate());
				
				chargebackFareDMB.setValue(txn.getChargebackRefundFareAmt());
				chargebackAdminDMB.setValue(txn.getChargebackRefundAdminFee());
				chargebackGstDMB.setValue(txn.getChargebackRefundGst());
				
				if(txn.getRemarks() != null)
					chargebackRemarksTextBox.setValue(txn.getRemarks().toUpperCase());
				else
					chargebackRemarksTextBox.setValue(txn.getRemarks());
				
				this.updateChargebackTotalAmount();
			}
			else{
				chargebackFareDMB.setValue(txn.getFareAmt());
				chargebackAdminDMB.setValue(txn.getAdminFee());
				chargebackGstDMB.setValue(txn.getGst());
				chargebackButton.setVisible(true);
				
				this.updateChargebackTotalAmount();
			}
		}else if(mode.equals(MODE_REFUNDED)){
			this.populateRefundedReasonAndType();

			List<TmtbNonBillableTxn> txns = new ArrayList<TmtbNonBillableTxn>();
			txns.add(txn);
			txns = this.businessHelper.getNonBillableBusiness().retrieveCrca(txns);
			txn = txns.get(0);

			Set<TmtbNonBillableTxnCrca> reverseRefundTxnCrca1 = txn.getTmtbNonBillableTxnCrca1();
			Set<TmtbNonBillableTxnCrca> reverseRefundTxnCrca2 = txn.getTmtbNonBillableTxnCrca2();

			boolean hasRefundReverse = false;

			if(reverseRefundTxnCrca1 != null) {
				for (TmtbNonBillableTxnCrca crca1 : reverseRefundTxnCrca1) {
					if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)) {
						hasRefundReverse = true;
					}
				}
			}

			if(reverseRefundTxnCrca2 != null) {
				for (TmtbNonBillableTxnCrca crca1 : reverseRefundTxnCrca2) {
					if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)) {
						hasRefundReverse = true;
					}
				}
			}

			fmsVisible(mode);

			if(txn.getNric()==null)
				updateFMSLB.setDisabled(true);

			if(hasRefundReverse){
				refundReverseDiv.setVisible(true);

				if (reverseRefundTxnCrca1 != null && reverseRefundTxnCrca1.size() >0) {
					for(TmtbNonBillableTxnCrca crca1 : reverseRefundTxnCrca1) {
						if(crca1 != null && crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)) {
							if (crca1.getGrossAmount() != null)
								grossAmount = grossAmount.add(crca1.getGrossAmount()); //implemented this in batchjob
							if (crca1.getCommission() != null)
								commission = commission.add(crca1.getCommission());
							if (crca1.getMarkup() != null)
								markup = markup.add(crca1.getMarkup());
							if (crca1.getSchemeFee() != null)
								schemeFee = schemeFee.add(crca1.getSchemeFee());
							if (crca1.getInterchange() != null)
								interchange = interchange.add(crca1.getInterchange());
						}
						if(crca1 != null && crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)){
							refundReverseDateLabel.setValue(DateUtil.convertDateToStr(crca1.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
							refundReverseReasonLabel.setValue(crca1.getChargebackReasonDescription());
						}
					}

				}

				if (reverseRefundTxnCrca2 != null && reverseRefundTxnCrca2.size() >0) {
					for(TmtbNonBillableTxnCrca crca2 : reverseRefundTxnCrca2) {
						if(crca2 != null && crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)) {
							if (crca2.getGrossAmount() != null)
								grossAmount = grossAmount.add(crca2.getGrossAmount()); //implemented this in batchjob
							if (crca2.getCommission() != null)
								commission = commission.add(crca2.getCommission());
							if (crca2.getMarkup() != null)
								markup = markup.add(crca2.getMarkup());
							if (crca2.getSchemeFee() != null)
								schemeFee = schemeFee.add(crca2.getSchemeFee());
							if (crca2.getInterchange() != null)
								interchange = interchange.add(crca2.getInterchange());
						}

						if(crca2 != null && crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)){
							refundReverseDateLabel.setValue(DateUtil.convertDateToStr(crca2.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
							refundReverseReasonLabel.setValue(crca2.getChargebackReasonDescription());
						}

					}

				}

				refundReverseGrossCreditLabel.setValue(StringUtil.bigDecimalToString(grossAmount,StringUtil.GLOBAL_DECIMAL_FORMAT));
				refundReverseMarkupLabel.setValue(StringUtil.bigDecimalToString(markup,StringUtil.GLOBAL_DECIMAL_FORMAT));
				refundReverseCommissionLabel.setValue(StringUtil.bigDecimalToString(commission,StringUtil.GLOBAL_DECIMAL_FORMAT));
				refundReverseSchemeFeeLabel.setValue(StringUtil.bigDecimalToString(schemeFee,StringUtil.GLOBAL_DECIMAL_FORMAT));
			}

			if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED)){
				updateRefundButton.setVisible(true);
				//txn chargeback date,fare,admin and gst  cannot be null
				refundDateDB.setValue(txn.getChargebackRefundDate());
				refundFareDMB.setValue(txn.getChargebackRefundFareAmt());
				refundAdminDMB.setValue(txn.getChargebackRefundAdminFee());
				refundGstDMB.setValue(txn.getChargebackRefundGst());

				if(txn.getRemarks() != null)
					refundRemarksTextBox.setValue(txn.getRemarks().toUpperCase());
				else
					refundRemarksTextBox.setValue(txn.getRemarks());

				this.updateRefundTotalAmount();
			}
		}
		else{
			
			if(mode.equals(MODE_VIEW))
				fmsVisible(mode);
			
			if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED)){
				viewRejectDiv.setVisible(true);
				remarksLabel.setValue(txn.getRemarks());
			}
			else if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK)){
				viewChargeBackDiv.setVisible(true);
				updateFMSDiv.setVisible(true);
				chargebackDateLabel.setValue(DateUtil.convertDateToStr(txn.getChargebackRefundDate(), DateUtil.GLOBAL_DATE_FORMAT));
				chargebackReasonLabel.setValue(txn.getMstbMasterTableByChargebackRefundReason().getMasterValue());
				chargebackTypeLabel.setValue(txn.getMstbMasterTableByChargebackType().getMasterValue());
				
				if(txn.getRemarks() != null)
					chargebackRemarksLabel.setValue(txn.getRemarks().toUpperCase());
				else
					chargebackRemarksLabel.setValue(txn.getRemarks());
				
				chargebackFareAmountLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundFareAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				chargebackAdminFeeLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundAdminFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				chargebackGSTLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundGst(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				chargebackTotalAmountLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundFareAmt().add(txn.getChargebackRefundAdminFee()).add(txn.getChargebackRefundGst()), StringUtil.GLOBAL_DECIMAL_FORMAT));
			}else if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED)){
				viewRefundDiv.setVisible(true);
				updateFMSDiv.setVisible(true);
				refundDateLabel.setValue(DateUtil.convertDateToStr(txn.getChargebackRefundDate(), DateUtil.GLOBAL_DATE_FORMAT));
				refundReasonLabel.setValue(txn.getMstbMasterTableByChargebackRefundReason().getMasterValue());
				refundTypeLabel.setValue(txn.getMstbMasterTableByChargebackType().getMasterValue());

				if(txn.getRemarks() != null)
					refundRemarksLabel.setValue(txn.getRemarks().toUpperCase());
				else
					refundRemarksLabel.setValue(txn.getRemarks());

				refundFareAmountLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundFareAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				refundAdminFeeLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundAdminFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				refundGSTLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundGst(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				refundTotalAmountLabel.setValue(StringUtil.bigDecimalToString(txn.getChargebackRefundFareAmt().add(txn.getChargebackRefundAdminFee()).add(txn.getChargebackRefundGst()), StringUtil.GLOBAL_DECIMAL_FORMAT));

			}
		}

		//Populate the labels
		acquirerLabel.setValue(batch.getMstbAcquirer().getName());
		paymentTypeLabel.setValue(txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue());
		cardNoLabel.setValue(txn.getCardNo());
		uploadDateLabel.setValue(DateUtil.convertDateToStr(batch.getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));
		fareAmountLabel.setValue(StringUtil.bigDecimalToString(txn.getFareAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		jobNoLabel.setValue(txn.getJobNo());
		adminFeeLabel.setValue(StringUtil.bigDecimalToString(txn.getAdminFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		tripDTLabel.setValue(DateUtil.convertDateToStr(txn.getTripStartDt(), DateUtil.LAST_UPDATED_DATE_FORMAT));
		gstLabel.setValue(StringUtil.bigDecimalToString(txn.getGst(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		driverIdLabel.setValue(txn.getNric());
		totalAmountLabel.setValue(StringUtil.bigDecimalToString(txn.getTotal(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		taxiNoLabel.setValue(txn.getTaxiNo());
		statusLabel.setValue(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus()));
		companyCodeLabel.setValue(txn.getMstbMasterTableByServiceProvider().getMasterValue());
		

		policyNoLabel.setValue(txn.getPolicyNumber());
		policyStatusLabel.setValue(NonConfigurableConstants.HLA_POLICY_STATUS.get(txn.getPolicyStatus()));
		premiumLabel.setValue(StringUtil.bigDecimalToString(txn.getPremiumAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		premiumGstLabel.setValue(StringUtil.bigDecimalToString(txn.getPremiumGst(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		
		MstbMasterTable tripTypeMaster = ConfigurableConstants
				.getMasterTableByInterfaceMapping(ConfigurableConstants.VEHICLE_TRIP_TYPE,
						txn.getProductId());
		if(tripTypeMaster!= null)
			tripTypeLabel.setValue(tripTypeMaster.getMasterValue());
		else
			tripTypeLabel.setValue("-");
		batchNoLabel.setValue(batch.getBatchNo());
		offlineLabel.setValue(NonConfigurableConstants.BOOLEAN_YN.get(txn.getOfflineFlag()));
		
		createdByLabel.setValue(txn.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertDateToStr(txn.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertDateToStr(txn.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		if(txn.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(txn.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(txn.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(txn.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(txn.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(txn.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	private void populateChargebackReasonAndType(){
		Map<String, String> reasonMap = ConfigurableConstants.getMasters(ConfigurableConstants.CHARGEBACK_REASON);
		//reason code disabled therefore must populate back that item for display
		if(txn.getMstbMasterTableByChargebackRefundReason()!=null)
			if(reasonMap.get(txn.getMstbMasterTableByChargebackRefundReason().getMasterCode())== null)
				reasonLB.appendChild(new Listitem(txn.getMstbMasterTableByChargebackRefundReason().getMasterValue(),
						txn.getMstbMasterTableByChargebackRefundReason().getMasterCode()));
		
		List<Listitem> reasons = ComponentUtil.convertToListitems(reasonMap, true);
		for(Listitem listItem : reasons){
			reasonLB.appendChild(listItem);
			if(txn.getMstbMasterTableByChargebackRefundReason()!=null)
				if(txn.getMstbMasterTableByChargebackRefundReason().getMasterCode().equals(listItem.getValue()))
					listItem.setSelected(true);
		}
		if(reasonLB.getSelectedItem()==null)
			reasonLB.setSelectedIndex(0);
		
		Map<String, String> typeMap = ConfigurableConstants.getMasters(ConfigurableConstants.CHARGEBACK_TYPE);
		//reason code disabled therefore must populate back that item for display
		if(txn.getMstbMasterTableByChargebackType()!=null)
			if(typeMap.get(txn.getMstbMasterTableByChargebackType().getMasterCode())== null)
				typeLB.appendChild(new Listitem(txn.getMstbMasterTableByChargebackType().getMasterValue(),
						txn.getMstbMasterTableByChargebackType().getMasterCode()));
		
		List<Listitem> types = ComponentUtil.convertToListitems(typeMap, true);
		for(Listitem listItem : types){
			typeLB.appendChild(listItem);
			if(txn.getMstbMasterTableByChargebackType()!=null)
				if(txn.getMstbMasterTableByChargebackType().getMasterCode().equals(listItem.getValue()))
					listItem.setSelected(true);
		}
		if(typeLB.getSelectedItem()==null)
			typeLB.setSelectedIndex(0);
	}

	private void populateRefundedReasonAndType(){
		Map<String, String> reasonMap = ConfigurableConstants.getMasters(ConfigurableConstants.CHARGEBACK_REASON); //take from Chargeback
		//reason code disabled therefore must populate back that item for display
		if(txn.getMstbMasterTableByChargebackRefundReason()!=null)
			if(reasonMap.get(txn.getMstbMasterTableByChargebackRefundReason().getMasterCode())== null)
				refundReasonLB.appendChild(new Listitem(txn.getMstbMasterTableByChargebackRefundReason().getMasterValue(),
						txn.getMstbMasterTableByChargebackRefundReason().getMasterCode()));

		List<Listitem> reasons = ComponentUtil.convertToListitems(reasonMap, true);
		for(Listitem listItem : reasons){
			refundReasonLB.appendChild(listItem);
			if(txn.getMstbMasterTableByChargebackRefundReason()!=null)
				if(txn.getMstbMasterTableByChargebackRefundReason().getMasterCode().equals(listItem.getValue()))
					listItem.setSelected(true);
		}
		if(refundReasonLB.getSelectedItem()==null)
			refundReasonLB.setSelectedIndex(0);

		Map<String, String> typeMap = ConfigurableConstants.getMasters(ConfigurableConstants.CHARGEBACK_TYPE);
		//reason code disabled therefore must populate back that item for display
		if(txn.getMstbMasterTableByChargebackType()!=null)
			if(typeMap.get(txn.getMstbMasterTableByChargebackType().getMasterCode())== null)
				refundTypeLB.appendChild(new Listitem(txn.getMstbMasterTableByChargebackType().getMasterValue(),
						txn.getMstbMasterTableByChargebackType().getMasterCode()));

		List<Listitem> types = ComponentUtil.convertToListitems(typeMap, true);
		for(Listitem listItem : types){
			refundTypeLB.appendChild(listItem);
			if(txn.getMstbMasterTableByChargebackType()!=null)
				if(txn.getMstbMasterTableByChargebackType().getMasterCode().equals(listItem.getValue()))
					listItem.setSelected(true);
		}
		if(refundTypeLB.getSelectedItem()==null)
			refundTypeLB.setSelectedIndex(0);
	}
	
	public void reject() throws InterruptedException{
		((ViewNonBillableBatchWindow)this.getPreviousPage()).reject(txn.getTxnId(), txn.getTotal(), remarksTextBox.getValue());
		this.back();
	}
	
	public void unReject() throws InterruptedException{
		((ViewNonBillableBatchWindow)this.getPreviousPage()).unReject(txn.getTxnId());
		this.back();
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
	
	public void onSelectUpdateFMS() throws InterruptedException {
		if(updateFMSLB.getSelectedItem().getValue().equals(NonConfigurableConstants.BOOLEAN_YES)){
			FMSRow.setVisible(true);
			FMSRow2.setVisible(true);
			FMSRow3.setVisible(true);
			levyAmtDMB.setVisible(true);
			levyAmtLabel.setVisible(true);
		}
		else{
			FMSRow.setVisible(false);
			FMSRow2.setVisible(false);
			FMSRow3.setVisible(false);
			levyAmtDMB.setVisible(false);
			levyAmtLabel.setVisible(false);
		}
	}
	
	public void chargeback() throws InterruptedException{
		try{
			String updateCancel = NonConfigurableConstants.BOOLEAN_YN_NO;

			if(cancelPolicyLB.isDisabled()) 
				updateCancel = NonConfigurableConstants.BOOLEAN_YN_NO;
			else
				updateCancel = (String) cancelPolicyLB.getSelectedItem().getValue();
			
			String updateFMS = (String) updateFMSLB.getSelectedItem().getValue();
			if(updateFMSLB.isDisabled()) updateFMS = NonConfigurableConstants.BOOLEAN_YN_NO;
			
			String updateType = (String)updateTypeLB.getSelectedItem().getValue();
			
			if(updateFMS == null || updateFMS.length()==0){
				Messagebox.show("Update FMS is a mandatory field", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			if(updateFMS.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				if(updateType==null || updateType.length()==0){
					Messagebox.show("Refund/Collection? is a mandatory field", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			
			this.displayProcessing();
			
			if(updateFMS.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(
						txn.getTaxiNo(), txn.getNric(), txn.getTripStartDt(), txn.getTripEndDt()==null ? txn.getTripStartDt() : txn.getTripEndDt());
				if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg))
				{
					// Note: error message will be changed again
					Messagebox.show("Interface Error to FMS - " + "Driver Association Not Valid", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				else
					logger.info("FMS Interface is disabled");
			}

			if(updateCancel.equals(NonConfigurableConstants.BOOLEAN_YN_YES)) {
				txn.setPolicyStatus(NonConfigurableConstants.HLA_POLICY_STATUS_CANCELLED_MANUAL);
			}
			if(batch.getBmtbBankPaymentDetails() == null || batch.getBmtbBankPaymentDetails().size() == 0){
				Messagebox.show("Create Payment Advise has not been done yet","Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			java.sql.Date chargebackDate = DateUtil.convertUtilDateToSqlDate(chargebackDateDB.getValue());
			java.sql.Date creditDate = batch.getBmtbBankPaymentDetails().iterator().next().getBmtbBankPayment().getCreditDate();
			if(chargebackDate.compareTo(DateUtil.getCurrentDate()) > 0)
				throw new WrongValueException(chargebackDateDB, "Chargeback Date must be before or equals to Current Date");
			else if(chargebackDate.compareTo(creditDate) == -1)
				throw new WrongValueException(chargebackDateDB, "Chargeback Date must be after or equals to Credit Date");
			
			//validation check
			if(txn.getFareAmt().compareTo(chargebackFareDMB.getValue()) == -1)
				throw new WrongValueException(chargebackFareDMB, "Chargeback Fare Amount cannot be greater than Txn Fare Amount");
			if(txn.getAdminFee().compareTo(chargebackAdminDMB.getValue()) == -1)
				throw new WrongValueException(chargebackAdminDMB, "Chargeback Admin Fee cannot be greater than Txn Admin Fee");
			if(txn.getGst().compareTo(chargebackGstDMB.getValue()) == -1)
				throw new WrongValueException(chargebackGstDMB, "Chargeback GST cannot be greater than Txn GST");
			if(txn.getTotal().compareTo(chargebackFareDMB.getValue().add(chargebackAdminDMB.getValue().add(chargebackGstDMB.getValue()))) == -1)
				throw new WrongValueException(chargebackFareDMB, "Chargeback Total Amount cannot be greater than Txn Total Amount");
			
			txn.setChargebackRefundDate(chargebackDate);
			txn.setChargebackRefundFareAmt(chargebackFareDMB.getValue());
			txn.setChargebackRefundAdminFee(chargebackAdminDMB.getValue());
			txn.setChargebackRefundGst(chargebackGstDMB.getValue());
			
			txn.setMstbMasterTableByChargebackRefundReason(
					ConfigurableConstants.getMasterTable(ConfigurableConstants.CHARGEBACK_REASON, 
							(String)reasonLB.getSelectedItem().getValue()));
			txn.setMstbMasterTableByChargebackType(
					ConfigurableConstants.getMasterTable(ConfigurableConstants.CHARGEBACK_TYPE, 
							(String)typeLB.getSelectedItem().getValue()));
			
			if(chargebackRemarksTextBox.getValue() != null)
				txn.setRemarks(chargebackRemarksTextBox.getValue().toUpperCase());
			else
				txn.setRemarks(chargebackRemarksTextBox.getValue());
			
			txn.setFmsFlag(updateFMS);
			if(updateFMS.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				txn.setUpdateFms(updateType);
				txn.setLevy(levyAmtDMB.getValue());
				txn.setFmsAmt(fmsAmtDMB.getValue());
				txn.setIncentiveAmt(incentiveAmtDMB.getValue());
				txn.setPromoAmt(promoAmtDMB.getValue());
				txn.setCabRewardsAmt(cabRewardsAmtDMB.getValue());
			}
			
			this.businessHelper.getNonBillableBusiness().chargeback(txn, getUserLoginIdAndDomain());
			
			//Show result
			Messagebox.show("Chargeback Non Billable Trip successful.", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void refund() throws InterruptedException{
		try{
			String updateCancel = (String) cancelPolicyLB.getSelectedItem().getValue();
			if(cancelPolicyLB.isDisabled()) updateCancel = NonConfigurableConstants.BOOLEAN_YN_NO;
			String updateFMS = (String) updateFMSLB.getSelectedItem().getValue();
			if(updateFMSLB.isDisabled()) updateFMS = NonConfigurableConstants.BOOLEAN_YN_NO;

			String updateType = (String)updateTypeLB.getSelectedItem().getValue();

			if(updateFMS == null || updateFMS.length()==0){
				Messagebox.show("Update FMS is a mandatory field", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			if(updateFMS.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				if(updateType==null || updateType.length()==0){
					Messagebox.show("Refund/Collection? is a mandatory field", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			if(updateCancel.equals(NonConfigurableConstants.BOOLEAN_YN_YES)) {
				txn.setPolicyStatus(NonConfigurableConstants.HLA_POLICY_STATUS_CANCELLED_MANUAL);
			}
			this.displayProcessing();

			if(updateFMS.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(
						txn.getTaxiNo(), txn.getNric(), txn.getTripStartDt(), txn.getTripEndDt()==null ? txn.getTripStartDt() : txn.getTripEndDt());
				if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg))
				{
					// Note: error message will be changed again
					Messagebox.show("Interface Error to FMS - " + "Driver Association Not Valid", "Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				else
					logger.info("FMS Interface is disabled");
			}

			java.sql.Date refundDate = DateUtil.convertUtilDateToSqlDate(refundDateDB.getValue());
			if(batch.getBmtbBankPaymentDetails() == null || batch.getBmtbBankPaymentDetails().size() == 0){
				Messagebox.show("Create Payment Advise has not been done yet","Chargeback Non Billable Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			java.sql.Date creditDate = batch.getBmtbBankPaymentDetails().iterator().next().getBmtbBankPayment().getCreditDate();
			if(refundDate.compareTo(DateUtil.getCurrentDate()) > 0)
				throw new WrongValueException(refundDateDB, "Refunded Date must be before or equals to Current Date");
			else if(refundDate.compareTo(creditDate) == -1)
				throw new WrongValueException(refundDateDB, "Refunded Date must be after or equals to Credit Date");

			//validation check
			if(txn.getFareAmt().compareTo(refundFareDMB.getValue()) == -1)
				throw new WrongValueException(refundFareDMB, "Refunded Fare Amount cannot be greater than Txn Fare Amount");
			if(txn.getAdminFee().compareTo(refundAdminDMB.getValue()) == -1)
				throw new WrongValueException(refundAdminDMB, "Refunded Admin Fee cannot be greater than Txn Admin Fee");
			if(txn.getGst().compareTo(refundGstDMB.getValue()) == -1)
				throw new WrongValueException(refundGstDMB, "Refunded GST cannot be greater than Txn GST");
			if(txn.getTotal().compareTo(refundFareDMB.getValue().add(refundAdminDMB.getValue().add(refundGstDMB.getValue()))) == -1)
				throw new WrongValueException(refundFareDMB, "Refunded Total Amount cannot be greater than Txn Total Amount");

			txn.setChargebackRefundDate(refundDate);
			txn.setChargebackRefundFareAmt(refundFareDMB.getValue());
			txn.setChargebackRefundAdminFee(refundAdminDMB.getValue());
			txn.setChargebackRefundGst(refundGstDMB.getValue());

			txn.setMstbMasterTableByChargebackRefundReason(
					ConfigurableConstants.getMasterTable(ConfigurableConstants.CHARGEBACK_REASON,
							(String)refundReasonLB.getSelectedItem().getValue()));
			txn.setMstbMasterTableByChargebackType(
					ConfigurableConstants.getMasterTable(ConfigurableConstants.CHARGEBACK_TYPE,
							(String)refundTypeLB.getSelectedItem().getValue()));

			if(refundRemarksTextBox.getValue() != null)
				txn.setRemarks(refundRemarksTextBox.getValue().toUpperCase());
			else
				txn.setRemarks(refundRemarksTextBox.getValue());

			txn.setFmsFlag(updateFMS);
			if(updateFMS.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				txn.setUpdateFms(updateType);
				txn.setLevy(levyAmtDMB.getValue());
				txn.setFmsAmt(fmsAmtDMB.getValue());
				txn.setIncentiveAmt(incentiveAmtDMB.getValue());
				txn.setPromoAmt(promoAmtDMB.getValue());
				txn.setCabRewardsAmt(cabRewardsAmtDMB.getValue());
			}

			this.businessHelper.getNonBillableBusiness().refund(txn, getUserLoginIdAndDomain());

			//Show result
			Messagebox.show("Refunded Non Billable Trip successful.", "Refunded Non Billable Trip", Messagebox.OK, Messagebox.INFORMATION);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void updateChargebackTotalAmount(){
		BigDecimal totalAmount = BigDecimal.ZERO;
		
		if(chargebackFareDMB.getValue()!=null)
			totalAmount = totalAmount.add(chargebackFareDMB.getValue());
		if(chargebackAdminDMB.getValue()!=null)
			totalAmount = totalAmount.add(chargebackAdminDMB.getValue());
		if(chargebackGstDMB.getValue()!=null)
			totalAmount = totalAmount.add(chargebackGstDMB.getValue());
		
		chargebackTotalAmountInputLabel.setValue(StringUtil.bigDecimalToString(totalAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
	}

	public void updateRefundTotalAmount(){
		BigDecimal totalAmount = BigDecimal.ZERO;

		if(refundFareDMB.getValue()!=null)
			totalAmount = totalAmount.add(refundFareDMB.getValue());
		if(refundAdminDMB.getValue()!=null)
			totalAmount = totalAmount.add(refundAdminDMB.getValue());
		if(refundGstDMB.getValue()!=null)
			totalAmount = totalAmount.add(refundGstDMB.getValue());

		refundTotalAmountInputLabel.setValue(StringUtil.bigDecimalToString(totalAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
	}
	
//	public void updateChargebackAdminFeeAndGSTAmount(){
//		
//		BigDecimal gstRate = new GSTMasterManager().getCurrentGST();
//		
//		BigDecimal fareAmount = chargebackFareDMB.getValue();
//		
//		BigDecimal adminAmount = fareAmount.multiply(new BigDecimal(0.1)).setScale(2, BigDecimal.ROUND_HALF_UP);
//		chargebackAdminDMB.setValue(adminAmount);
//		
//		BigDecimal gstAmount = adminAmount.multiply(gstRate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
//		chargebackGstDMB.setValue(gstAmount);
//		
//		updateChargebackTotalAmount();
//	}
	
	public void exportResult() throws InterruptedException, IOException {
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("nonbillableTxnDetailsGrid"), "Non Billable Transaction Details", null));
		if (((Div)this.getFellow("viewRejectDiv")).isVisible()) {
			items.add(new PdfExportItem((Grid)this.getFellow("rejectGrid"), "Reject Remarks", null));
		}
		if (((Div)this.getFellow("viewChargeBackDiv")).isVisible()) {
			items.add(new PdfExportItem((Grid)this.getFellow("chargebackGrid"), "Chargeback Details", null));
		}
		if (((Div)this.getFellow("viewFMSChargeBackDiv")).isVisible()) {
			items.add(new PdfExportItem((Grid)this.getFellow("viewUpdateFMSGrid"), "FMS", null));
		}
		if (((Div)this.getFellow("rejectFMSChargeBackDiv")).isVisible()) {
			items.add(new PdfExportItem((Grid)this.getFellow("rejectUpdateFMSGrid"), "FMS", null));
		}
				
		if (((Div)this.getFellow("chargeBackDiv")).isVisible()) {
			if(((Grid)this.getFellow("chargebackEnterGrid")).isVisible()) {
				
				if(chargebackRemarksTextBox.getValue() != null)
					chargebackRemarksTextBox.setValue(chargebackRemarksTextBox.getValue().toUpperCase());
				else
					chargebackRemarksTextBox.setValue(chargebackRemarksTextBox.getValue());
					
				items.add(new PdfExportItem((Grid)this.getFellow("chargebackEnterGrid"), "Chargeback Non Billable Trip", null));
			}
			if(((Grid)this.getFellow("updateFMSGrid")).isVisible()) 
				items.add(new PdfExportItem((Grid)this.getFellow("updateFMSGrid"), "Update FMS", null));
		}
		items.add(new PdfExportItem((Grid)this.getFellow("createdByGrid"), "Created By", null));
		items.add(new PdfExportItem((Grid)this.getFellow("updatedByGrid"), "Updated by", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter();
		try{
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("Chargeback_Details.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
	
	public void fmsVisible(String theMode) {

		String typeMode = "reject";
		if(theMode.equals(MODE_VIEW)) {
			typeMode = "view";
			viewFMSChargeBackDiv.setVisible(true);
		}
		else if(theMode.equals(MODE_CHARGEBACK)) {
			typeMode = "prev";
			chargeBackDiv.setVisible(true);
			updateFMSDiv.setVisible(true);
		}else if(theMode.equals(MODE_REFUNDED)){
			typeMode = "prev";
			refundDiv.setVisible(true);
			updateFMSDiv.setVisible(true);
		}
		else {
			typeMode = "reject";
			rejectFMSChargeBackDiv.setVisible(true);
		}
			
			
		if (txn.getFmsAmt() != null && !"".equals(txn.getFmsAmt().toString()))
		{
			((Row)this.getFellow(typeMode+"FMSRow")).setVisible(true);
			((Label)this.getFellow(typeMode+"FMSAmount")).setVisible(true);
			((Label)this.getFellow(typeMode+"FMSAmount")).setValue(txn.getFmsAmt().setScale(2).toString());
		
			if (txn.getLevy() != null && !"".equals(txn.getLevy().toString()))
			{
				((Label)this.getFellow(typeMode+"LevyLabel")).setVisible(true);
				((Label)this.getFellow(typeMode+"LevyAmount")).setVisible(true);
				((Label)this.getFellow(typeMode+"LevyAmount")).setValue(txn.getLevy().setScale(2).toString());
			}
			
			if (txn.getIncentiveAmt() != null && !"".equals(txn.getIncentiveAmt().toString()))
			{
				((Row)this.getFellow(typeMode+"FMSRow2")).setVisible(true);
				((Label)this.getFellow(typeMode+"IncentiveAmt")).setVisible(true);
				((Label)this.getFellow(typeMode+"IncentiveAmt")).setValue(txn.getIncentiveAmt().setScale(2).toString());
			}
			
			if (txn.getPromoAmt() != null && !"".equals(txn.getPromoAmt().toString()))
			{
				((Row)this.getFellow(typeMode+"FMSRow2")).setVisible(true);
				((Label)this.getFellow(typeMode+"PromoAmt")).setVisible(true);
				((Label)this.getFellow(typeMode+"PromoAmt")).setValue(txn.getPromoAmt().setScale(2).toString());
			}
			
			if (txn.getCabRewardsAmt() != null && !"".equals(txn.getCabRewardsAmt().toString()))
			{
				((Row)this.getFellow(typeMode+"FMSRow3")).setVisible(true);
				((Label)this.getFellow(typeMode+"CabRewardsAmt")).setVisible(true);
				((Label)this.getFellow(typeMode+"CabRewardsAmt")).setValue(txn.getCabRewardsAmt().setScale(2).toString());
			}
			
			if (txn.getFmsFlag() != null && !"".equals(txn.getFmsFlag()))
			{
				((Row)this.getFellow(typeMode+"FMSRow")).setVisible(true);
				((Label)this.getFellow(typeMode+"ToUpdateFMSList")).setVisible(true);
				((Label)this.getFellow(typeMode+"ToUpdateFMSList")).setValue(txn.getFmsFlag());
			}
				
			if (txn.getUpdateFms() != null && !"".equals(txn.getUpdateFms()))
			{
				((Row)this.getFellow(typeMode+"FMSRow")).setVisible(true);
				((Row)this.getFellow(typeMode+"FMSRow2")).setVisible(true);
				((Row)this.getFellow(typeMode+"FMSRow3")).setVisible(true);
				((Label)this.getFellow(typeMode+"UpdateFMSList")).setVisible(true);
				((Label)this.getFellow(typeMode+"UpdateFMSList")).setValue(txn.getUpdateFms());
			}
		}
	}
}
