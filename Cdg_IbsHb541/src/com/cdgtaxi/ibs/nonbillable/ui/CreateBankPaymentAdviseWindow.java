package com.cdgtaxi.ibs.nonbillable.ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.forms.BankPaymentInfo;
import com.cdgtaxi.ibs.common.model.forms.BankPaymentInfo.BatchInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class CreateBankPaymentAdviseWindow extends CommonWindow implements AfterCompose {

    private CapsTextbox txnRefNoTextBox, remarksTextBox;
    private Datebox creditDateBox;
    private Decimalbox collectionAmountDecimalBox, adjustmentDecimalBox, chargebackDecimalBox;
    private Label totalTripsAmountLabel, rejectedAmountLabel, mdrLabel, receiptAmountLabel,markupLabel,commissionLabel,schemeFeeLabel,
            interchangeLabel,chargebackReverseLabel, refundLabel, refundReverseLabel, otherCreditLabel, otherDebitLabel, premiumAmountLabel, premiumGstLabel;
    private Listbox bankInBankListBox, resultList;
    private BankPaymentInfo bankPaymentInfo;
    private Button createButton;

    @SuppressWarnings("unchecked")
    public void afterCompose() {
        //wire variables
        Components.wireVariables(this, this);

        //Using the selected batch ids from previous window, we re-retrieve the records from the database
        //to grab latest information in case information gets updated.
        Map parameters = Executions.getCurrent().getArg();
        List<Long> batchIds = (List<Long>) parameters.get("batchIds");
        List<TmtbNonBillableBatch> batches = this.businessHelper.getNonBillableBusiness().getNonbillableBatch(batchIds);
        if (batches.isEmpty()) {
            try {
                Messagebox.show("Unable to re-retrieve all the selected batches.",
                        "Error", Messagebox.OK, Messagebox.ERROR);
                this.back();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        resultList.removeChild(resultList.getListfoot());
        bankPaymentInfo = new BankPaymentInfo();
        for (TmtbNonBillableBatch batch : batches) {
            BatchInfo batchInfo = bankPaymentInfo.new BatchInfo();
            batchInfo.batch = batch;

            //Accumulate Total Trips Amount
            bankPaymentInfo.totalTripsAmount = bankPaymentInfo.totalTripsAmount.add(batch.getTxnAmt());
            if(batch.getMarkup() != null) {
                bankPaymentInfo.markup = bankPaymentInfo.markup.add(batch.getMarkup().abs());
            }
            if(batch.getCommission() != null) {
                bankPaymentInfo.commission = bankPaymentInfo.commission.add(batch.getCommission().abs());
            }
            if(batch.getSchemeFee() != null) {
                bankPaymentInfo.schemeFee = bankPaymentInfo.schemeFee.add(batch.getSchemeFee().abs());
            }
            if(batch.getInterchange() != null) {
                bankPaymentInfo.interchange = bankPaymentInfo.interchange.add(batch.getInterchange().abs());
            }
            if(batch.getChargebackAmt() != null) {
                bankPaymentInfo.chargebackAmount = bankPaymentInfo.chargebackAmount.add(batch.getChargebackAmt());
            }
            if(batch.getChargebackReverseAmt() != null) {
                bankPaymentInfo.chargebackReverse = bankPaymentInfo.chargebackReverse.add(batch.getChargebackReverseAmt());
            }
            if(batch.getRefundAmt() != null) {
                bankPaymentInfo.refund = bankPaymentInfo.refund.add(batch.getRefundAmt());
            }
            if(batch.getRefundReverseAmt() != null) {
                bankPaymentInfo.refundReverse = bankPaymentInfo.refundReverse.add(batch.getRefundReverseAmt());
            }
            if(batch.getOtherCreditAmt() != null) {
                bankPaymentInfo.otherCredit = bankPaymentInfo.otherCredit.add(batch.getOtherCreditAmt());
            }
            if(batch.getOtherCreditAmt() != null) {
                bankPaymentInfo.otherDebit = bankPaymentInfo.otherDebit.add(batch.getOtherDebitAmt());
            }

            if(batch.getPremiumAmount() != null) {
            	bankPaymentInfo.premiumAmount = bankPaymentInfo.premiumAmount.add(batch.getPremiumAmount());
            }
            if(batch.getPremiumGst() != null) {
            	bankPaymentInfo.premiumGst = bankPaymentInfo.premiumGst.add(batch.getPremiumGst());
            }

            //Populate batch listing
            Listitem item = new Listitem();
            item.setValue(batchInfo);

            item.appendChild(newListcell(batch.getBatchNo()));
            item.appendChild(newListcell(batch.getSettlementDate()));
            item.appendChild(newListcell(batch.getMstbAcquirer().getName()));
            if (batch.getTid() != null) item.appendChild(newListcell(batch.getTid()));
            else item.appendChild(newEmptyListcell(null, "-"));
            item.appendChild(newListcell(batch.getTxnCount()));
            item.appendChild(newListcell(batch.getTxnAmt()));
            item.appendChild(newListcell(0));
            item.appendChild(newListcell(BigDecimal.ZERO));
            resultList.appendChild(item);
        }

        totalTripsAmountLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.totalTripsAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
        markupLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.markup, StringUtil.GLOBAL_DECIMAL_FORMAT));
        commissionLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.commission, StringUtil.GLOBAL_DECIMAL_FORMAT));
        schemeFeeLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.schemeFee, StringUtil.GLOBAL_DECIMAL_FORMAT));
        interchangeLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.interchange, StringUtil.GLOBAL_DECIMAL_FORMAT));
        chargebackDecimalBox.setValue(bankPaymentInfo.chargebackAmount.subtract(bankPaymentInfo.chargebackReverse));
//        chargebackReverseLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.chargebackReverse, StringUtil.GLOBAL_DECIMAL_FORMAT));
        refundLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.refund.subtract(bankPaymentInfo.refundReverse), StringUtil.GLOBAL_DECIMAL_FORMAT));
//        refundReverseLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.refundReverse, StringUtil.GLOBAL_DECIMAL_FORMAT));
//        otherCreditLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.otherCredit, StringUtil.GLOBAL_DECIMAL_FORMAT));
//        otherDebitLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.otherDebit, StringUtil.GLOBAL_DECIMAL_FORMAT));

        receiptAmountLabel.setValue("");
        premiumAmountLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.premiumAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
        premiumGstLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.premiumGst, StringUtil.GLOBAL_DECIMAL_FORMAT));

        MstbAcquirerMdr mdr = this.businessHelper.getNonBillableBusiness().getMDR(batches.get(0).getMstbAcquirer());
        bankPaymentInfo.mdrPercentage = mdr.getRate();

        List<FmtbBankCode> banks = this.businessHelper.getNonBillableBusiness().getBankInBanksForNonBillable(batchIds);
        for (FmtbBankCode bank : banks) {
            Listitem listItem = new Listitem(bank.getBankName() + " - " + bank.getBranchName() + " (" + bank.getBankAcctNo() + ")", bank.getBankCodeNo());
            if (bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES))
                listItem.setSelected(true);
            bankInBankListBox.appendChild(listItem);
        }
        //if no defaulted bank, then made the first one as default
        if (bankInBankListBox.getSelectedItem() == null)
            if (banks.size() > 0)
                bankInBankListBox.setSelectedIndex(0);

        try {
            this.updateValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


	@SuppressWarnings("unchecked")
    public void create() throws InterruptedException {
        this.displayProcessing();

        if (bankInBankListBox.getSelectedItem() == null) {
            Messagebox.show("Bank cannot be null",
                    "Create Bank Payment Advice", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        try {
            this.updateValue();

            //Retrieve remaining values from input
            bankPaymentInfo.txnRefNo = txnRefNoTextBox.getValue();
            bankPaymentInfo.creditDate = DateUtil.convertUtilDateToSqlDate(creditDateBox.getValue());
            bankPaymentInfo.collectionAmount = collectionAmountDecimalBox.getValue();
            bankPaymentInfo.mdrAdjustment = adjustmentDecimalBox.getValue();
            bankPaymentInfo.chargebackAmount = chargebackDecimalBox.getValue();
            bankPaymentInfo.remarks = remarksTextBox.getValue();
            bankPaymentInfo.bankInCode = (Integer) bankInBankListBox.getSelectedItem().getValue();

            //Validation
            if (bankPaymentInfo.collectionAmount.compareTo(bankPaymentInfo.receiptAmount) != 0) {
                Messagebox.show("Bank Collection Amount is not equal to Receipt Amount.",
                        "Create Bank Payment Advice", Messagebox.OK, Messagebox.ERROR);
                return;
            }

            //this has to be after validation if not it will keep appending on the batch info.
            List<Listitem> items = resultList.getItems();
            for (Listitem item : items) {
                BatchInfo batchInfo = (BatchInfo) item.getValue();
                bankPaymentInfo.batchList.add(batchInfo);
            }

            createButton.setDisabled(true);
            Long receiptNo = this.businessHelper.getNonBillableBusiness().createBankPaymentAdvise(bankPaymentInfo, getUserLoginIdAndDomain());

            //Show result
            Messagebox.show("New bank payment Advice created (Payment No: " + receiptNo + ").", "Create Bank Payment Advice", Messagebox.OK, Messagebox.INFORMATION);

            //Refresh search result
            ManageNonBillableBatchWindow window = (ManageNonBillableBatchWindow) this.back();
            window.search();
        } catch (WrongValueException wve) {
            throw wve;
        } catch (HibernateOptimisticLockingFailureException holfe) {
            Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
                    "Error", Messagebox.OK, Messagebox.ERROR);

            Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

            holfe.printStackTrace();
        } catch (Exception e) {
            Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
                    "Error", Messagebox.OK, Messagebox.ERROR);
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
	//MDR uses existing but markup/commission/scheme fee/interchange is just for show purposes should be already in the mdr fields
    public void updateValue() throws InterruptedException {
        //Date check for credit date against settlement date
        Date smallestSettlementDate = DateUtil.getCurrentDate();

        //Repopulate the rejected amount
        BigDecimal rejectedAmount = BigDecimal.ZERO;
        List<Listitem> items = resultList.getItems();
        for (Listitem item : items) {
            BatchInfo batchInfo = (BatchInfo) item.getValue();
            rejectedAmount = rejectedAmount.add(batchInfo.rejectedAmount);

            if (batchInfo.batch.getSettlementDate().compareTo(smallestSettlementDate) == -1)
                smallestSettlementDate = batchInfo.batch.getSettlementDate();
        }

        if (this.creditDateBox.getRawValue() != null)
            if (((java.util.Date) this.creditDateBox.getRawValue()).compareTo(smallestSettlementDate) == -1)
                throw new WrongValueException(creditDateBox, "Credit Date must be after Settlement Date");

        bankPaymentInfo.rejectedAmount = rejectedAmount;
        rejectedAmountLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.rejectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));

        if (bankPaymentInfo.mdrPercentage.compareTo(BigDecimal.ZERO) != 0)
            bankPaymentInfo.mdrAmount = bankPaymentInfo.totalTripsAmount
                    .subtract(bankPaymentInfo.rejectedAmount)
                    .multiply(bankPaymentInfo.mdrPercentage)
                    .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        mdrLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.mdrAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));

        bankPaymentInfo.mdrAdjustment = adjustmentDecimalBox.getValue();
        bankPaymentInfo.chargebackAmount = chargebackDecimalBox.getValue();

        if (bankPaymentInfo.mdrAdjustment == null &&
                bankPaymentInfo.chargebackAmount == null)
            return;

        bankPaymentInfo.receiptAmount =
                bankPaymentInfo.totalTripsAmount
                        .subtract(bankPaymentInfo.rejectedAmount)
                        .subtract(bankPaymentInfo.mdrAmount)
                        .subtract(bankPaymentInfo.mdrAdjustment)
                        .subtract(bankPaymentInfo.chargebackAmount)
                        .subtract(bankPaymentInfo.interchange)
                        .subtract(bankPaymentInfo.markup)
                        .subtract(bankPaymentInfo.commission)
                        .subtract(bankPaymentInfo.schemeFee);

        //Re-calculate the receipt amount
        receiptAmountLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.receiptAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
        premiumAmountLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.premiumAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
        premiumGstLabel.setValue(StringUtil.bigDecimalToString(bankPaymentInfo.premiumGst, StringUtil.GLOBAL_DECIMAL_FORMAT));
    }

    @SuppressWarnings("unchecked")
    public void edit() throws InterruptedException {
        this.displayProcessing();
        BatchInfo batchInfo = (BatchInfo) resultList.getSelectedItem().getValue();

        Map map = new HashMap();
        map.put("mode", ViewNonBillableBatchWindow.MODE_EDIT);
        map.put("batchInfo", batchInfo);
        this.forward(Uri.VIEW_NON_BILLABLE_BATCH, map);
    }

    public void updateBatchInfo(BatchInfo batchInfo) throws InterruptedException {
        batchInfo.calculateRejectedCountAndAmount();
        Listitem item = resultList.getSelectedItem();

        //update batch info
        item.setValue(batchInfo);
        //update cell values for rejected count and rejected amount
        Listcell cell7 = (Listcell) item.getChildren().get(6);
        cell7.setLabel(batchInfo.rejectedCount.toString());
        cell7.setValue(batchInfo.rejectedCount);
        Listcell cell8 = (Listcell) item.getChildren().get(7);
        cell8.setLabel(StringUtil.bigDecimalToString(batchInfo.rejectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
        cell8.setValue(batchInfo.rejectedAmount);

        resultList.clearSelection();
    }

    @Override
    public void refresh() throws InterruptedException {

    }
}
