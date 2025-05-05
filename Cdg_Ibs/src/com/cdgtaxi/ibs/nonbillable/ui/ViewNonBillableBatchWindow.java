package com.cdgtaxi.ibs.nonbillable.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBankPayment;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.model.forms.BankPaymentInfo.BatchInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
import com.lowagie.text.PageSize;

@SuppressWarnings("serial")
public class ViewNonBillableBatchWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(ViewNonBillableBatchWindow.class);

	private Label batchNoLabel, uploadDateLabel, batchStatusLabel,
			acquirerLabel, midLabel, tidLabel, tripsCountLabel,
			tripsAmtLabel, creditCountLabel, creditAmtLabel, creditDateLabel, pspReferenceNoLabel1,
			markupLabel1, commissionNoLabel1, schemeFeeLabel1, interchangeLabel1, pspReferenceNoLabel2,
			markupLabel2, commissionNoLabel2, schemeFeeLabel2, interchangeLabel2 , aydenMerchantIdLabel1,
			aydenMerchantIdLabel2, grossCreditLabel1, grossCreditLabel2 , grossDebitLabel1 , grossDebitLabel2,
			grossAmountLabel1,grossAmountLabel2,detailLabel,transactionNoLabel,paidDateLabel,sourceLabel,
			paymentReferenceLabel,paidStatusLabel;
	private CapsTextbox taxiNoTextBox, driverIdTextBox, pspRefNoTextBox, policyNoTextBox;
	private Datebox tripDateFromDateBox, tripDateToDateBox;
	private Grid paymentDetailsGrid1, paymentDetailsLabelGrid1, paymentDetailsGrid2,  paymentDetailsLabelGrid2, paymentDetailsGrid3, paymentDetailsLabelGrid3 ;
	private Listbox txnStatusListBox, matchingStatusListBox,paymentTypeListBox, resultList, offlineLB, policyStatusLB;
	private Decimalbox totalAmountDecimalBox;

	public static final String MODE_VIEW = "VIEW";
	public static final String MODE_EDIT = "EDIT";
	public static final String MODE_VIEW_ALLOW_TRIP_VIEW = "VIEW_ALLOW_TRIP_VIEW";

	private String mode;
	private TmtbNonBillableBatch txnBatch;
	private BatchInfo batchInfo;

	@SuppressWarnings("rawtypes")
	public ViewNonBillableBatchWindow(){

		Map params = Executions.getCurrent().getArg();
		mode = (String)params.get("mode");

		if(mode.equals(MODE_VIEW) || mode.equals(MODE_VIEW_ALLOW_TRIP_VIEW))
			txnBatch = (TmtbNonBillableBatch) this.businessHelper.getNonBillableBusiness().getNonbillableBatch((Long)params.get("batchId"));
		else if(mode.equals(MODE_EDIT)){
			batchInfo = (BatchInfo) params.get("batchInfo");
			txnBatch = batchInfo.batch;
		}
	}

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		batchNoLabel.setValue(txnBatch.getBatchNo());
		uploadDateLabel.setValue(DateUtil.convertDateToStr(txnBatch.getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));
		batchStatusLabel.setValue(NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS.get(txnBatch.getStatus()));
		acquirerLabel.setValue(txnBatch.getMstbAcquirer().getName());
		midLabel.setValue(txnBatch.getMid());
		tidLabel.setValue(txnBatch.getTid());
		tripsCountLabel.setValue(txnBatch.getTxnCount()+"");
		tripsAmtLabel.setValue(StringUtil.bigDecimalToString(txnBatch.getTxnAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		creditCountLabel.setValue(txnBatch.getCreditCount()==null ? "-" : txnBatch.getCreditCount().toString());
		creditAmtLabel.setValue(txnBatch.getCreditAmt()==null ? "-" : StringUtil.bigDecimalToString(txnBatch.getCreditAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));

		if(!txnBatch.getBmtbBankPaymentDetails().isEmpty()){
			BmtbBankPayment bankPayment = txnBatch.getBmtbBankPaymentDetails().iterator().next().getBmtbBankPayment();
			creditDateLabel.setValue(DateUtil.convertDateToStr(bankPayment.getCreditDate(), DateUtil.GLOBAL_DATE_FORMAT));
		}
		else creditDateLabel.setValue("-");

		this.populatePaymentType(txnBatch.getMstbAcquirer());

		txnStatusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<String> txnStatusKeys = NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.keySet();
		for(String key : txnStatusKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(key));
			txnStatusListBox.appendChild(item);
		}
		txnStatusListBox.setSelectedIndex(0);

		matchingStatusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<String> matchingStatusKey = NonConfigurableConstants.AYDEN_MATCHING_STATUS.keySet();
		for(String key : matchingStatusKey){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.AYDEN_MATCHING_STATUS.get(key));
			matchingStatusListBox.appendChild(item);
		}
		matchingStatusListBox.setSelectedIndex(0);

		offlineLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<String> ynKeys = NonConfigurableConstants.BOOLEAN_YN.keySet();
		for(String key : ynKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.BOOLEAN_YN.get(key));
			offlineLB.appendChild(item);
		}
		offlineLB.setSelectedIndex(0);
		
		policyStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<String> policyStatusKey = NonConfigurableConstants.HLA_POLICY_STATUS.keySet();
		for(String key : policyStatusKey){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.HLA_POLICY_STATUS.get(key));
			policyStatusLB.appendChild(item);
		}
		policyStatusLB.setSelectedIndex(0);
	}

	public void populatePaymentType(MstbAcquirer acquirer){
		paymentTypeListBox.getChildren().clear();

		Listitem allItem = new Listitem("All", null);
		paymentTypeListBox.appendChild(allItem);

		List<MstbMasterTable> masters = this.businessHelper.getNonBillableBusiness().getPymtType(acquirer);
		for(MstbMasterTable master : masters){
			Listitem item = new Listitem();

			item.setValue(master.getMasterCode());
			item.setLabel(master.getMasterValue());

			paymentTypeListBox.appendChild(item);
		}
		paymentTypeListBox.setSelectedIndex(0);
	}

	public void reset(){
		this.populatePaymentType(txnBatch.getMstbAcquirer());
		txnStatusListBox.setSelectedIndex(0);
		offlineLB.setSelectedIndex(0);
		policyStatusLB.setSelectedIndex(0);
		policyNoTextBox.setValue("");
		taxiNoTextBox.setValue("");
		driverIdTextBox.setValue("");
		tripDateFromDateBox.setText("");
		tripDateToDateBox.setText("");
		totalAmountDecimalBox.setText("");
		pspRefNoTextBox.setText("");

		paymentDetailsGrid1.setVisible(false);
		paymentDetailsLabelGrid1.setVisible(false);
		paymentDetailsGrid2.setVisible(false);
		paymentDetailsLabelGrid2.setVisible(false);
		paymentDetailsGrid3.setVisible(false);
		paymentDetailsLabelGrid3.setVisible(false);

		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(12));
	}

	public void search() throws SuspendNotAllowedException, InterruptedException{
		try{
			if(paymentDetailsGrid1 !=null && paymentDetailsLabelGrid1 !=null){
				paymentDetailsGrid1.setVisible(false);
				paymentDetailsLabelGrid1.setVisible(false);
				paymentDetailsGrid2.setVisible(false);
				paymentDetailsLabelGrid2.setVisible(false);
				paymentDetailsGrid3.setVisible(false);
				paymentDetailsLabelGrid3.setVisible(false);
			}
			if(tripDateFromDateBox.getValue()!=null && tripDateToDateBox.getValue()==null)
				tripDateToDateBox.setValue(tripDateFromDateBox.getValue());
			else if(tripDateFromDateBox.getValue()==null && tripDateToDateBox.getValue()!=null)
				tripDateFromDateBox.setValue(tripDateToDateBox.getValue());

			if(tripDateFromDateBox.getValue()!=null && tripDateToDateBox.getValue()!=null)
				if(tripDateFromDateBox.getValue().compareTo(tripDateToDateBox.getValue()) == 1)
					throw new WrongValueException(tripDateFromDateBox, "Trip Date From cannot be later than Trip Date To.");

			this.displayProcessing();
			resultList.getItems().clear();

			SearchNonBillableTxnForm form = this.buildSearchForm();

			List<TmtbNonBillableTxn> txns = this.businessHelper.getNonBillableBusiness().searchNonBillableTxn(form);
//			txns = this.businessHelper.getNonBillableBusiness().retrieveCrcaWithoutChargebackRefund(txns);

			if(txns.size()>0){

				if(txns.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

				for(TmtbNonBillableTxn txn : txns){
					Listitem item = new Listitem();

					item.setValue(txn);

					item.appendChild(newListcell(txn.getCardNo()));
					item.appendChild(newListcell(txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue()));
					item.appendChild(newListcell(txn.getTripStartDt()));
					item.appendChild(newListcell(txn.getMstbMasterTableByServiceProvider().getMasterValue()));
					item.appendChild(newListcell(txn.getTaxiNo()));
					item.appendChild(newListcell(txn.getNric()));
					item.appendChild(newListcell(txn.getFareAmt()));
					item.appendChild(newListcell(txn.getAdminFee()));
					item.appendChild(newListcell(txn.getGst()));
					item.appendChild(newListcell(txn.getPremiumAmount()));
					item.appendChild(newListcell(txn.getPremiumGst()));
					item.appendChild(newListcell(txn.getTotal()));
					item.appendChild(newListcell(NonConfigurableConstants.BOOLEAN_YN.get(txn.getOfflineFlag())));
					item.appendChild(newListcell(txn.getJobNo()));
					item.appendChild(newListcell(txn.getPspRefNo1() == null ? "-" : txn.getPspRefNo1()));
					item.appendChild(newListcell(txn.getTxnAmount1() == null ? "-" : txn.getTxnAmount1()));
					item.appendChild(newListcell(txn.getPspRefNo2() == null ? "-" : txn.getPspRefNo2()));
					item.appendChild(newListcell(txn.getTxnAmount2() == null ? "-" : txn.getTxnAmount2()));
					item.appendChild(newListcell(txn.getPolicyNumber() == null ? "-" : txn.getPolicyNumber()));
					
					item.appendChild(newListcell((txn.getPolicyStatus() == null ? "-" : NonConfigurableConstants.HLA_POLICY_STATUS.get(txn.getPolicyStatus()))));
					item.appendChild(newListcell((txn.getMatchingStatus() == null ? "-" : NonConfigurableConstants.AYDEN_MATCHING_STATUS.get(txn.getMatchingStatus()))));

					if(mode.equals(MODE_VIEW) || mode.equals(MODE_VIEW_ALLOW_TRIP_VIEW))
						item.appendChild(newListcell(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus())));
					else if(mode.equals(MODE_EDIT)){
						if(batchInfo.rejectedTrips.get(txn.getTxnId()) != null)
							item.appendChild(newListcell(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED)));
						else
							item.appendChild(newListcell(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus())));
					}

					resultList.appendChild(item);
				}

				if(txns.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(12));
				}
			}

			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(11);
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

	@SuppressWarnings("unchecked")
	public void viewTxn() throws InterruptedException{
		if(mode.equals(MODE_VIEW)){
			TmtbNonBillableTxn txn = (TmtbNonBillableTxn) resultList.getSelectedItem().getValue();
			populatePaymentDetails(txn);

			return;
		}
		else if(mode.equals(MODE_EDIT)){
			TmtbNonBillableTxn txn = (TmtbNonBillableTxn) resultList.getSelectedItem().getValue();

			Map map = new HashMap();
			map.put("txn", txn);
			map.put("batch", this.txnBatch);

			if(batchInfo.rejectedTrips.get(txn.getTxnId()) != null){
				txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED);
				txn.setRemarks((String)batchInfo.rejectedTrips.get(txn.getTxnId())[1]);
				map.put("mode", ViewNonBillableTxnWindow.MODE_UNREJECT);
			}
			else
				map.put("mode", ViewNonBillableTxnWindow.MODE_REJECT);

			this.forward(Uri.VIEW_NON_BILLABLE_TXN, map);
		}
		else if(mode.equals(MODE_VIEW_ALLOW_TRIP_VIEW)){
			TmtbNonBillableTxn txn = (TmtbNonBillableTxn) resultList.getSelectedItem().getValue();

			Map map = new HashMap();
			map.put("txn", txn);
			map.put("batch", this.txnBatch);
			map.put("mode", ViewNonBillableTxnWindow.MODE_VIEW);

			this.forward(Uri.VIEW_NON_BILLABLE_TXN, map);
		}
		else
			return;
	}

	private void populatePaymentDetails(TmtbNonBillableTxn txn) {

		List<TmtbNonBillableTxn> txns = new ArrayList<TmtbNonBillableTxn>();
		txns.add(txn);
		txns = this.businessHelper.getNonBillableBusiness().retrieveCrca(txns);
		txn = txns.get(0);

		BigDecimal totalMarkup1 = BigDecimal.ZERO;
		BigDecimal totalCommission1 = BigDecimal.ZERO;
		BigDecimal totalSchemeFee1 = BigDecimal.ZERO;
		BigDecimal totalInterchange1 = BigDecimal.ZERO;
		BigDecimal totalGrossDebit1 = BigDecimal.ZERO;
		BigDecimal totalGrossCredit1 = BigDecimal.ZERO;
		BigDecimal totalGrossAmount1 = BigDecimal.ZERO;
		String aydenMerchantId1 = "-";

		BigDecimal totalMarkup2 = BigDecimal.ZERO;
		BigDecimal totalCommission2 = BigDecimal.ZERO;
		BigDecimal totalSchemeFee2 = BigDecimal.ZERO;
		BigDecimal totalInterchange2 = BigDecimal.ZERO;
		BigDecimal totalGrossDebit2 = BigDecimal.ZERO;
		BigDecimal totalGrossCredit2 = BigDecimal.ZERO;
		BigDecimal totalGrossAmount2 = BigDecimal.ZERO;
		String aydenMerchantId2 = "-";

		String detail = "";
		String transactionNo = "";
		Date paidDate = null;
		String paidStatus = "";
		String paymentRefId = "";
		String source = "";


		Set<TmtbNonBillableTxnCrca> crca1 = txn.getTmtbNonBillableTxnCrca1();
		Set<TmtbNonBillableTxnCrca> crca2 = txn.getTmtbNonBillableTxnCrca2();

		if(crca1 != null  && crca1.size() >0 && crca1.iterator().next() != null ) {

			aydenMerchantId1 = crca1.iterator().next().getSubmissionMerchantId();
			detail = crca1.iterator().next().getDetails();
			transactionNo = crca1.iterator().next().getTransactionNo();
			paidDate = crca1.iterator().next().getUploadDate();
			source = crca1.iterator().next().getSource();
			paidStatus = crca1.iterator().next().getStatus();
			paymentRefId = crca1.iterator().next().getPaymentReference();

			for(TmtbNonBillableTxnCrca crca : crca1) {
				if(crca.getMarkup() != null) {
					totalMarkup1 = totalMarkup1.add(crca.getMarkup());
				}
				if(crca.getCommission() != null) {
					totalCommission1 = totalCommission1.add(crca.getCommission());
				}
				if(crca.getSchemeFee() != null) {
					totalSchemeFee1 = totalSchemeFee1.add(crca.getSchemeFee());
				}
				if(crca.getInterchange() != null) {
					totalInterchange1 = totalInterchange1.add(crca.getInterchange());
				}
				if(crca.getGrossDebit() != null) {
					totalGrossDebit1 = totalGrossDebit1.add(crca.getGrossDebit());
				}
				if(crca.getGrossCredit() != null) {
					totalGrossCredit1 = totalGrossCredit1.add(crca.getGrossCredit());
				}
				if(crca.getGrossAmount() != null) {
					totalGrossAmount1 = totalGrossAmount1.add(crca.getGrossAmount());
				}
			}
		}
		if(crca2 != null && crca2.size() >0 && crca2.iterator().next() != null ) {
			aydenMerchantId2 = crca2.iterator().next().getSubmissionMerchantId();
			for(TmtbNonBillableTxnCrca crca : crca2) {
				if(crca.getMarkup() != null) {
					totalMarkup2 = totalMarkup2.add(crca.getMarkup());
				}
				if(crca.getCommission() != null) {
					totalCommission2 = totalCommission2.add(crca.getCommission());
				}
				if(crca.getSchemeFee() != null) {
					totalSchemeFee2 = totalSchemeFee2.add(crca.getSchemeFee());
				}
				if(crca.getInterchange() != null) {
					totalInterchange2 = totalInterchange2.add(crca.getInterchange());
				}
				if(crca.getGrossDebit() != null) {
					totalGrossDebit2 = totalGrossDebit2.add(crca.getGrossDebit());
				}
				if(crca.getGrossCredit() != null) {
					totalGrossCredit2 = totalGrossCredit2.add(crca.getGrossCredit());
				}
				if(crca.getGrossAmount() != null) {
					totalGrossAmount2 = totalGrossAmount2.add(crca.getGrossAmount());
				}
			}
		}

		if (crca1 != null  && crca1.size() >0 && crca1.iterator().next() != null) {
			pspReferenceNoLabel1.setValue(txn.getPspRefNo1());
			markupLabel1.setValue(StringUtil.bigDecimalToString(totalMarkup1, StringUtil.GLOBAL_DECIMAL_FORMAT));
			commissionNoLabel1.setValue(StringUtil.bigDecimalToString(totalCommission1, StringUtil.GLOBAL_DECIMAL_FORMAT));
			schemeFeeLabel1.setValue(StringUtil.bigDecimalToString(totalSchemeFee1, StringUtil.GLOBAL_DECIMAL_FORMAT));
			interchangeLabel1.setValue(StringUtil.bigDecimalToString(totalInterchange1, StringUtil.GLOBAL_DECIMAL_FORMAT));
			grossCreditLabel1.setValue(StringUtil.bigDecimalToString(totalGrossCredit1, StringUtil.GLOBAL_DECIMAL_FORMAT));
			grossDebitLabel1.setValue(StringUtil.bigDecimalToString(totalGrossDebit1, StringUtil.GLOBAL_DECIMAL_FORMAT));
			grossAmountLabel1.setValue(StringUtil.bigDecimalToString(totalGrossAmount1,StringUtil.GLOBAL_DECIMAL_FORMAT));
			aydenMerchantIdLabel1.setValue(aydenMerchantId1);
			paymentDetailsLabelGrid1.setVisible(true);
			paymentDetailsGrid1.setVisible(true);
		}else{
			if(txn.getPspRefNo1() != null) {
				pspReferenceNoLabel1.setValue(txn.getPspRefNo1());
			}else{
				pspReferenceNoLabel2.setValue("-");
			}
			markupLabel1.setValue("-");
			commissionNoLabel1.setValue("-");
			schemeFeeLabel1.setValue("-");
			interchangeLabel1.setValue("-");
			aydenMerchantIdLabel1.setValue("-");
			grossCreditLabel1.setValue("-");
			grossDebitLabel1.setValue("-");
			grossAmountLabel1.setValue("-");
			paymentDetailsLabelGrid1.setVisible(true);
			paymentDetailsGrid1.setVisible(true);
		}

		if (crca2 != null && crca2.size() >0 && crca2.iterator().next() != null) {
			pspReferenceNoLabel2.setValue(txn.getPspRefNo2());
			markupLabel2.setValue(StringUtil.bigDecimalToString(totalMarkup2, StringUtil.GLOBAL_DECIMAL_FORMAT));
			commissionNoLabel2.setValue(StringUtil.bigDecimalToString(totalCommission2, StringUtil.GLOBAL_DECIMAL_FORMAT));
			schemeFeeLabel2.setValue(StringUtil.bigDecimalToString(totalSchemeFee2, StringUtil.GLOBAL_DECIMAL_FORMAT));
			interchangeLabel2.setValue(StringUtil.bigDecimalToString(totalInterchange2, StringUtil.GLOBAL_DECIMAL_FORMAT));
			grossCreditLabel2.setValue(StringUtil.bigDecimalToString(totalGrossCredit2, StringUtil.GLOBAL_DECIMAL_FORMAT));
			grossDebitLabel2.setValue(StringUtil.bigDecimalToString(totalGrossDebit2, StringUtil.GLOBAL_DECIMAL_FORMAT));
			grossAmountLabel2.setValue(StringUtil.bigDecimalToString(totalGrossAmount2,StringUtil.GLOBAL_DECIMAL_FORMAT));
			aydenMerchantIdLabel2.setValue(aydenMerchantId2);
			paymentDetailsLabelGrid2.setVisible(true);
			paymentDetailsGrid2.setVisible(true);
		}else{
			if(txn.getPspRefNo2() != null) {
				pspReferenceNoLabel2.setValue(txn.getPspRefNo2());
			}else{
				pspReferenceNoLabel2.setValue("-");
			}
			markupLabel2.setValue("-");
			commissionNoLabel2.setValue("-");
			schemeFeeLabel2.setValue("-");
			interchangeLabel2.setValue("-");
			aydenMerchantIdLabel2.setValue("-");
			grossCreditLabel2.setValue("-");
			grossDebitLabel2.setValue("-");
			grossAmountLabel2.setValue("-");
			paymentDetailsLabelGrid2.setVisible(true);
			paymentDetailsGrid2.setVisible(true);
		}

		//Lazada
		if(crca1 != null && crca1.size() > 0 && crca1.iterator().next() != null) {
			detailLabel.setValue(detail);
			transactionNoLabel.setValue(transactionNo);
			paidStatusLabel.setValue(paidStatus);
			paymentReferenceLabel.setValue(paymentRefId);
			sourceLabel.setValue(source);
			paymentDetailsGrid3.setVisible(true);
			paymentDetailsLabelGrid3.setVisible(true);

			if(paidDate != null) {
				paidDateLabel.setValue(paidDate.toString());
			}else{
				paidDateLabel.setValue("-");
			}

		}else{
			detailLabel.setValue("-");
			transactionNoLabel.setValue("-");
			paidDateLabel.setValue("-");
			paidStatusLabel.setValue("-");
			paymentReferenceLabel.setValue("-");
			sourceLabel.setValue("-");
			paymentDetailsGrid3.setVisible(true);
			paymentDetailsLabelGrid3.setVisible(true);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		resultList.clearSelection();
	}

	public void reject(Long txnNo, BigDecimal txnAmount, String remarks){
		batchInfo.rejectedTrips.put(txnNo, new Object[]{txnAmount, remarks});
		Listcell cell = (Listcell)resultList.getSelectedItem().getLastChild();
		cell.setLabel(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED));
		cell.setValue(cell.getLabel());
		resultList.clearSelection();
	}

	public void unReject(Long txnNo){
		batchInfo.rejectedTrips.remove(txnNo);
		Listcell cell = (Listcell)resultList.getSelectedItem().getLastChild();
		cell.setLabel(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(NonConfigurableConstants.NON_BILLABLE_TXN_OPEN));
		cell.setValue(cell.getLabel());
		resultList.clearSelection();
	}

	private SearchNonBillableTxnForm buildSearchForm(){
		SearchNonBillableTxnForm form = new SearchNonBillableTxnForm();

		form.txnBatch = txnBatch;
		form.paymentType = (String)paymentTypeListBox.getSelectedItem().getValue();
		form.txnStatus = (String)txnStatusListBox.getSelectedItem().getValue();
		form.taxiNo = taxiNoTextBox.getValue();
		form.driverID = driverIdTextBox.getValue();
		form.tripDateFrom = tripDateFromDateBox.getValue();
		form.tripDateTo = tripDateToDateBox.getValue();
		form.totalAmount = totalAmountDecimalBox.getValue();
		form.offline = (String)offlineLB.getSelectedItem().getValue();
		form.policyNo = policyNoTextBox.getValue();
		form.policyStatus = (String)policyStatusLB.getSelectedItem().getValue();
		form.pspRefNo = pspRefNoTextBox.getValue();
		form.matchingStatus = (String) matchingStatusListBox.getSelectedItem().getValue();

		if(mode.equals(MODE_EDIT))
			form.rejectedTrips = batchInfo.rejectedTrips.keySet();

		return form;
	}

	public void backToPreviousPage() throws InterruptedException{
		CommonWindow previousPage = this.back();

		if(previousPage instanceof CreateBankPaymentAdviseWindow){
			((CreateBankPaymentAdviseWindow)previousPage).updateBatchInfo(batchInfo);
			((CreateBankPaymentAdviseWindow)previousPage).updateValue();
		}
	}

	public void exportResult() throws InterruptedException, IOException {
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("batchDetails"), "Batch Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("searchTripsGrid"), "Search Trips", null));
		items.add(new PdfExportItem((Grid)this.getFellow("paymentDetailsGrid1"), "Payment Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("paymentDetailsGrid2"), "Payment Details", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("resultList"), "Trips Result", null));

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		//try a3? might all 1 line
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try{
			exp.export(items, out);

			AMedia amedia = new AMedia("Non_Billable_Batch.pdf", "pdf", "application/pdf", out.toByteArray());
			Filedownload.save(amedia);

			out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}

	public void exportCSVResult() throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("User Data");

		/**
		 * Style for the header cells.
		 */
		XSSFCellStyle headerCellStyle = wb.createCellStyle();
		XSSFFont boldFont = wb.createFont();
		//boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFont.setBold(true);
		headerCellStyle.setFont(boldFont);

		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell((short)0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Card No"));
		cell = row.createCell((short)1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Payment Type"));
		cell = row.createCell((short)2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Trip Date Time"));
		cell = row.createCell((short)3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Company Code"));
		cell = row.createCell((short)4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Taxi No"));
		cell = row.createCell((short)5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Driver ID"));
		cell = row.createCell((short)6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Fare Amount($)"));
		cell = row.createCell((short)7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Admin Fee($)"));
		cell = row.createCell((short)8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("GST($)"));
		cell = row.createCell((short)9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Premium($)"));
		cell = row.createCell((short)10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Premium GST($)"));
		cell = row.createCell((short)11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Total Amount($)"));
		cell = row.createCell((short)12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Offline"));
		cell = row.createCell((short)13);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Job No"));
		cell = row.createCell((short)14);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Psp Ref No 1"));
		cell = row.createCell((short)15);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Txn Amount 1"));
		cell = row.createCell((short)16);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Psp Ref No 2"));
		cell = row.createCell((short)17);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Txn Amount 2"));
		cell = row.createCell((short)18);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Policy No."));
		cell = row.createCell((short)19);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Policy Status"));
		cell = row.createCell((short)20);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Matching Status"));
		cell = row.createCell((short)21);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new XSSFRichTextString("Txn Status"));

		int j = 0;
		for(Listitem item: (List<Listitem>)((Listbox) this.getFellow("resultList")).getItems()) {
			TmtbNonBillableTxn txn = (TmtbNonBillableTxn) item.getValue();

			j++;
			XSSFRichTextString temp = null;

			row = sheet.createRow(j);
			cell = row.createCell((short)0);
			temp = new XSSFRichTextString(txn.getCardNo());
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(temp);
			cell = row.createCell((short)1);
			temp = new XSSFRichTextString(txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue());
			cell.setCellValue(temp);
			cell = row.createCell((short)2);
			temp = new XSSFRichTextString(DateUtil.convertDateToStr(txn.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT));
			cell.setCellValue(temp);
			cell = row.createCell((short)3);
			temp = new XSSFRichTextString(txn.getMstbMasterTableByServiceProvider().getMasterValue());
			cell.setCellValue(temp);
			cell = row.createCell((short)4);
			temp = new XSSFRichTextString(txn.getTaxiNo());
			cell.setCellValue(temp);
			cell = row.createCell((short)5);
			temp = new XSSFRichTextString(txn.getNric());
			cell.setCellValue(temp);
			cell = row.createCell((short)6);
			if(txn.getFareAmt() != null) {
				temp = new XSSFRichTextString(txn.getFareAmt().toString());
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)7);
			if(txn.getAdminFee() != null) {
				temp = new XSSFRichTextString(txn.getAdminFee().toString());
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)8);
			if(txn.getGst() != null) {
				temp = new XSSFRichTextString(txn.getGst().toString());
			}
			else{
				temp = new XSSFRichTextString("");
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)9);
			if(txn.getPremiumAmount() != null) {
				temp = new XSSFRichTextString(txn.getPremiumAmount().toString());
			}
			else{
				temp = new XSSFRichTextString("");
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)10);
			if(txn.getPremiumGst() != null) {
				temp = new XSSFRichTextString(txn.getPremiumGst().toString());
			}
			else{
				temp = new XSSFRichTextString("");
			}
				
			cell.setCellValue(temp);
			cell = row.createCell((short)11);
			if(txn.getTotal() != null) {
				temp = new XSSFRichTextString(txn.getTotal().toString());
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)12);
			temp = new XSSFRichTextString(NonConfigurableConstants.BOOLEAN_YN.get(txn.getOfflineFlag()));
			cell.setCellValue(temp);
			cell = row.createCell((short)13);
			temp = new XSSFRichTextString(txn.getJobNo());
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(temp);
			cell = row.createCell((short)14);
			temp = new XSSFRichTextString(txn.getPspRefNo1());
			cell.setCellValue(temp);
			cell = row.createCell((short)15);
			if(txn.getTxnAmount1() != null) {
				temp = new XSSFRichTextString(txn.getTxnAmount1().toString());
			}else{
				temp = new XSSFRichTextString("");
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)16);
			temp = new XSSFRichTextString(txn.getPspRefNo2());
			cell.setCellValue(temp);
			cell = row.createCell((short)17);
			if(txn.getTxnAmount2() != null) {
				temp = new XSSFRichTextString(txn.getTxnAmount2().toString());
			}else{
				temp = new XSSFRichTextString("");
			}
			cell.setCellValue(temp);
			cell = row.createCell((short)18);
			temp = new XSSFRichTextString(txn.getPolicyNumber());
			cell.setCellValue(temp);
			cell = row.createCell((short)19);
			temp = new XSSFRichTextString(NonConfigurableConstants.HLA_POLICY_STATUS.get(txn.getPolicyStatus()));
			cell.setCellValue(temp);
			cell = row.createCell((short)20);
			temp = new XSSFRichTextString(NonConfigurableConstants.AYDEN_MATCHING_STATUS.get(txn.getMatchingStatus()));
			cell.setCellValue(temp);
			cell = row.createCell((short)21);
			temp = new XSSFRichTextString(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus()));
			cell.setCellValue(temp);

		}

		/**
		 * Setting the width of the first three columns.
		 */
		for (int i = 0; i < 17; i++) {
			sheet.autoSizeColumn(i,true);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
		} finally {
			bos.close();
		}
		byte[] bytes = bos.toByteArray();
		AMedia media = new AMedia("Non_Billable_Batch"+"."+ com.cdgtaxi.ibs.report.Constants.EXTENSION_EXCEL, com.cdgtaxi.ibs.report.Constants.EXTENSION_EXCEL,
				com.cdgtaxi.ibs.report.Constants.FORMAT_EXCEL, bytes);
		Filedownload.save(media);
	}

//	public void exportCSVResult() throws InterruptedException, IOException {
//
//		List<TmtbNonBillableTxn> txns = resultList.getItems();
////		txns = this.businessHelper.getNonBillableBusiness().retrieveCrca(txns);
//
//
//		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
//		items.add(new PdfExportItem((Listbox)this.getFellow("resultList"), "Trips Result", null));
//
//		StringBuffer sb = new StringBuffer();
//
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Card No" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Payment Type" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Trip Date Time" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Company Code" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Taxi No" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Driver ID" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Fare Amount($)" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Admin Fee($)" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "GST($)" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Total Amount($)" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Offline" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Psp Ref No 1" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Txn Amount 1" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Psp Ref No 2" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Txn Amount 2" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Txn Status" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + "Matching Status" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//		sb.append("\n");
//
//		//data
//		for(Listitem item: (List<Listitem>)((Listbox) this.getFellow("resultList")).getItems()) {
//			logger.info("item: " + item.getValue());
//			TmtbNonBillableTxn txn = (TmtbNonBillableTxn) item.getValue();
//
//			if (!StringUtil.isBlank(txn.getCardNo())) {
//				sb.append("" + "\'"
//						+ txn.getCardNo() + "\'" + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getTripStartDt() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ DateUtil.convertDateToStr(txn.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT)+ com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getMstbMasterTableByServiceProvider().getMasterValue())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getMstbMasterTableByServiceProvider().getMasterValue() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getTaxiNo())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getTaxiNo() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getNric())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getNric() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getFareAmt() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getFareAmt().toString() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getAdminFee() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getAdminFee().toString() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getGst() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getGst().toString() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getTotal() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getTotal().toString() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getOfflineFlag())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ NonConfigurableConstants.BOOLEAN_YN.get(txn.getOfflineFlag()) + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getPspRefNo1())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getPspRefNo1() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getTxnAmount1() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getTxnAmount1().toString()+ com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getPspRefNo2())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getPspRefNo2() + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (txn.getTxnAmount2() != null) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ txn.getTxnAmount2().toString()+ com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//			if (!StringUtil.isBlank(txn.getStatus())) {
//				sb.append("" + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER
//						+ NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus()) + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			} else {
//				sb.append(com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + com.cdgtaxi.ibs.report.Constants.TEXT_QUALIFIER + ",");
//			}
//
//			sb.append("\n");
//		}
//
//		AMedia media = new AMedia("Non Billable Batch".replaceAll(" ", "_") + "." + "CSV", "CSV", "text/csv",
//				sb.toString());
//		Filedownload.save(media);
//	}
}
