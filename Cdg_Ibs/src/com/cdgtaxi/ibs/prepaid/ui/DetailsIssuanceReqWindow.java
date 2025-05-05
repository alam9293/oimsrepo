package com.cdgtaxi.ibs.prepaid.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReqCard;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.elixirtech.net.NetException;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

@SuppressWarnings("serial")
public class DetailsIssuanceReqWindow extends CommonWindow implements AfterCompose {

	protected static final Logger logger = Logger.getLogger(DetailsIssuanceReqWindow.class);

	protected Label discountLabel, deliveryChargeLabel, deliveryChargeTxnCodeLabel;
	protected Label remarksLabel;
	protected ListModelList cardModels;
	protected Listbox cardListbox;
	protected Label creditTermLabel;
	protected Label totalAmountLabel;
	protected AccountLabelMacroComponent accountMC;
	
	protected PmtbIssuanceReq req;
	
	public class ResultRenderer implements ListitemRenderer {
		public void render(Listitem row, Object obj) {
			final PmtbIssuanceReqCard model = (PmtbIssuanceReqCard) obj;
			row.appendChild(new Listcell(model.getNameOnProduct()));
			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCardValue())));
			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCashplus())));

			Set<MstbPromotionCashPlus> mstbPromotionCashPluses = model.getMstbPromotionCashPluses();
			Set<String> promotionCodeSet = Sets.newHashSet();
			for (MstbPromotionCashPlus cashPlus : mstbPromotionCashPluses) {
				promotionCodeSet.add(cashPlus.getPromoCode());
			}
			String codeLabel = Joiner.on(", ").join(promotionCodeSet);
			if (Strings.isNullOrEmpty(codeLabel)) {
				codeLabel = "-";
			}
			row.appendChild(new Listcell(codeLabel));
			
			row.appendChild(new Listcell(DateUtil.convertDateToStr(model.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT)));

			Timestamp expiryTime = model.getExpiryTime();
			if(expiryTime!=null){
				row.appendChild(new Listcell(DateUtil.convertDateToStr(expiryTime, DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT)));
			} else {
				row.appendChild(new Listcell(DateUtil.convertDateToStr(model.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT)));
			}

			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getIssuanceFee())));
			
			Listcell waiveIssuanceFeeListcell = new Listcell();
			Checkbox waiveIssuanceCheckbox = new Checkbox();
			waiveIssuanceCheckbox.setDisabled(true);
			waiveIssuanceCheckbox.setChecked(NonConfigurableConstants.getBoolean(model.getWaiveIssuanceFeeFlag()));

			waiveIssuanceFeeListcell.appendChild(waiveIssuanceCheckbox);
			row.appendChild(waiveIssuanceFeeListcell);
			row.setValue(model);
		}
	}

	public void afterCompose() {
		// wire variables
		Components.wireVariables(this, this);
		cardListbox.setItemRenderer(new ResultRenderer());
		cardModels = new ListModelList();
		cardListbox.setModel(cardModels);

		init();

	}

	public void init() {

		@SuppressWarnings("unchecked")
		Map<String, Object> map = Executions.getCurrent().getArg();

		BigDecimal requestNo = (BigDecimal) map.get("requestNo");
		req = (PmtbIssuanceReq) this.businessHelper.getPrepaidBusiness().getPrepaidIssuanceRequest(requestNo);
		
		AmtbAccount acct = req.getAmtbAccount();
		acct = this.businessHelper.getAccountBusiness().getAccountWithParent(String.valueOf(acct.getAccountNo()));
		
		accountMC.populateDetails(acct);
		
		discountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getDiscount()));
		deliveryChargeLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getDeliveryCharge()));
		
		FmtbTransactionCode txnCode = req.getDeliveryChargeTxnCode();
		deliveryChargeTxnCodeLabel.setValue((txnCode!=null)?txnCode.getDescription():"");
		
		remarksLabel.setValue(req.getRequestRemarks());
		MstbCreditTermMaster mstbCreditTermMaster = req.getMstbCreditTermMaster();
		
		Map<MstbCreditTermMaster, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMastersWithCOD();	
		creditTermLabel.setValue(creditTermMasters.get(mstbCreditTermMaster));

		totalAmountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTotalAmount()));
		
		Set<PmtbIssuanceReqCard> pmtbIssuanceReqCards = req.getPmtbIssuanceReqCards();
		cardModels.addAll(pmtbIssuanceReqCards);
		
	}


	public void previewMiscInvoice() throws NetException, IOException {

		BmtbDraftInvHeader draftHeader = (BmtbDraftInvHeader)this.businessHelper.getPrepaidBusiness().createIssuanceInvoice(req, getUserLoginIdAndDomain(), true);
		
		byte[] bytes = this.businessHelper.getReportBusiness().generatePrepaidInvoice(req.getAmtbAccount(), draftHeader.getInvoiceHeaderNo().toString(), true);
		this.businessHelper.getInvoiceBusiness().updateInvoiceHeaderFile(bytes, draftHeader);

		AMedia media = new AMedia(NonConfigurableConstants.REPORT_NAME_INV_MISC_PREPAID + ".pdf", "pdf", Constants.FORMAT_PDF, bytes);
		Filedownload.save(media);
		
	}

	@Override
	public void refresh() throws InterruptedException {

	}

}
