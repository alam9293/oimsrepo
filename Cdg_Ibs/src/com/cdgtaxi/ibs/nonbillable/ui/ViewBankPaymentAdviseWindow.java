package com.cdgtaxi.ibs.nonbillable.ui;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.model.BmtbBankPayment;
import com.cdgtaxi.ibs.common.model.BmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.lowagie.text.PageSize;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ViewBankPaymentAdviseWindow extends CommonWindow implements AfterCompose {

    private Label totalTripsAmountLabel, rejectedAmountLabel, mdrLabel,
            txnRefNoLabel, creditDateLabel, collectionAmountLabel, mdrAdjustmentLabel,
            chargebackAmountLabel, remarksLabel, bankInLabel, createdByLabel, createdDateLabel,
            createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel,
            paymentNoLabel, markupLabel, commissionLabel, schemeFeeLabel, interchangeLabel, chargebackReverseLabel,
            refundLabel, refundReverseLabel, otherCreditLabel, otherDebitLabel;
    private Listbox resultList;

    private BmtbBankPayment bankPayment;

    @SuppressWarnings("unchecked")
    public void afterCompose() {
        //wire variables
        Components.wireVariables(this, this);

        Map parameters = Executions.getCurrent().getArg();
        bankPayment = (BmtbBankPayment) parameters.get("bankPayment");

        resultList.removeChild(resultList.getListfoot());
        for (BmtbBankPaymentDetail paymentDetail : bankPayment.getBmtbBankPaymentDetails()) {
            TmtbNonBillableBatch batch = paymentDetail.getTmtbNonBillableBatch();

            //Populate batch listing
            Listitem item = new Listitem();
            item.setValue(batch);

            item.appendChild(newListcell(batch.getBatchNo()));
            item.appendChild(newListcell(batch.getSettlementDate()));
            item.appendChild(newListcell(batch.getMstbAcquirer().getName()));
            if (batch.getTid() != null) item.appendChild(newListcell(batch.getTid()));
            else item.appendChild(newEmptyListcell(null, "-"));
            item.appendChild(newListcell(batch.getTxnCount()));
            item.appendChild(newListcell(batch.getTxnAmt()));

            Object[] rejectedValues = this.businessHelper.getNonBillableBusiness().getRejectedTripsCountAndAmount(batch.getBatchId());
            item.appendChild(newListcell(rejectedValues[0] == null ? 0 : rejectedValues[0]));
            item.appendChild(newListcell(rejectedValues[1] == null ? BigDecimal.ZERO : rejectedValues[1]));

            resultList.appendChild(item);
        }

        paymentNoLabel.setValue(bankPayment.getPaymentNo().toString());
        totalTripsAmountLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getTotalTxnAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        rejectedAmountLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getRejectedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        mdrLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getMdrValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        txnRefNoLabel.setValue(bankPayment.getTxnRefNo());
        creditDateLabel.setValue(DateUtil.convertDateToStr(bankPayment.getCreditDate(), DateUtil.GLOBAL_DATE_FORMAT));
        collectionAmountLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getCollectionAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        mdrAdjustmentLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getMdrAdjustment(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        markupLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getMarkup(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        commissionLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getCommission(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        schemeFeeLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getSchemeFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        interchangeLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getInterchange(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        chargebackAmountLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getChargebackAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        chargebackReverseLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getChargebackReverseAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        refundLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getRefundAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        refundReverseLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getRefundReverseAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        otherCreditLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getOtherCredit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        otherDebitLabel.setValue(StringUtil.bigDecimalToString(bankPayment.getOtherDebit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
        remarksLabel.setValue(bankPayment.getRemarks());

        FmtbBankCode bank = bankPayment.getFmtbBankCode();
        bankInLabel.setValue(bank.getBankName() + " - " + bank.getBranchName() + " (" + bank.getBankAcctNo() + ")");

        createdByLabel.setValue(bankPayment.getCreatedBy());
        createdDateLabel.setValue(DateUtil.convertDateToStr(bankPayment.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
        createdTimeLabel.setValue(DateUtil.convertDateToStr(bankPayment.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
        if (bankPayment.getUpdatedBy() != null) lastUpdatedByLabel.setValue(bankPayment.getUpdatedBy());
        else lastUpdatedByLabel.setValue("-");
        if (bankPayment.getUpdatedDt() != null)
            lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(bankPayment.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
        else lastUpdatedDateLabel.setValue("-");
        if (bankPayment.getUpdatedDt() != null)
            lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(bankPayment.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
        else lastUpdatedTimeLabel.setValue("-");
    }

    @SuppressWarnings("unchecked")
    public void view() throws InterruptedException {
        TmtbNonBillableBatch batch = (TmtbNonBillableBatch) resultList.getSelectedItem().getValue();

        Map map = new HashMap();
        map.put("mode", ViewNonBillableBatchWindow.MODE_VIEW_ALLOW_TRIP_VIEW);
        map.put("batchId", batch.getBatchId());
        this.forward(Uri.VIEW_NON_BILLABLE_BATCH, map);
    }

    @Override
    public void refresh() throws InterruptedException {
        resultList.clearSelection();
    }

    public void exportResult() throws InterruptedException, IOException {
        List<PdfExportItem> items = new ArrayList<PdfExportItem>();
        items.add(new PdfExportItem((Grid) this.getFellow("bankPymtDetailsGrid"), "Bank Payment Advice Details", null));
        items.add(new PdfExportItem((Grid) this.getFellow("bankInCodeGrid"), "Bank In Code", null));
        items.add(new PdfExportItem((Grid) this.getFellow("createdByGrid"), "Created By", null));
        items.add(new PdfExportItem((Grid) this.getFellow("updatedByGrid"), "Updated By", null));
        items.add(new PdfExportItem((Listbox) this.getFellow("resultList"), "Result", null));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
        try {
            exp.export(items, out);

            AMedia amedia = new AMedia("Bank_Payment_Advice_Details.pdf", "pdf", "application/pdf", out.toByteArray());
            Filedownload.save(amedia);

            out.close();
        } catch (Exception e) {
            try {
                Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
                        "Error", Messagebox.OK, Messagebox.ERROR);
            } catch (InterruptedException e1) {
                e.printStackTrace();
            }
        }
    }
}
