package com.cdgtaxi.ibs.prepaid.ui;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReqCard;
import com.cdgtaxi.ibs.common.ui.AccountLabelMacroComponent;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.elixirtech.net.NetException;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

@SuppressWarnings("serial")
public class DetailsTopUpReqWindow extends CommonWindow implements AfterCompose {


	protected static final Logger logger = Logger.getLogger(DetailsTopUpReqWindow.class);
	
	private Label remarksLabel;
	private ListModelList cardModels;
	private Listbox cardListbox;
	private Label creditTermLabel;
	private Label totalAmountLabel;
	protected AccountLabelMacroComponent accountMC;
	protected PmtbTopUpReq req;
	private Checkbox paidByCreditCard;

	public class ResultRenderer implements ListitemRenderer {
	    public void render(Listitem row, Object obj) {
	    	final PmtbTopUpReqCard model = (PmtbTopUpReqCard) obj;
	    	PmtbProduct product = model.getPmtbProduct();
	    
			row.appendChild(new Listcell(product.getCardNo()));
	    	row.appendChild(new Listcell(product.getNameOnProduct()));
	    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getTopUpValue())));
	    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getTopUpCashplus())));

	    	Set<MstbPromotionCashPlus> mstbPromotionCashPluses = model.getMstbPromotionCashPluses();
	    	Set<String> promotionCodeSet = Sets.newHashSet();
	    	for(MstbPromotionCashPlus cashPlus : mstbPromotionCashPluses){
	    		promotionCodeSet.add(cashPlus.getPromoCode());
	    	}
	    	String codeLabel = Joiner.on(", ").join(promotionCodeSet);	
	    	if(Strings.isNullOrEmpty(codeLabel)){
	    		codeLabel = "-";
	    	}
	    	row.appendChild(new Listcell(codeLabel));
	    	
	    	
	    	String balanceExpiryDateStr = DateUtil.convertDateToStr(model.getNewBalanceExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT);
	    	if(Strings.isNullOrEmpty(balanceExpiryDateStr)){
	    		balanceExpiryDateStr = "-";
	    	}
	    	row.appendChild(new Listcell(balanceExpiryDateStr));
	    	row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getTopUpFee())));
	    	
	    	Listcell waiveTopUpFeeListcell = new Listcell();
	    	Checkbox waiveTopUpCheckbox = new Checkbox();
	    	waiveTopUpCheckbox.setDisabled(true);
	    	waiveTopUpCheckbox.setChecked(NonConfigurableConstants.getBoolean(model.getWaiveTopUpFeeFlag()));
	    	
	    	waiveTopUpFeeListcell.appendChild(waiveTopUpCheckbox);
	    	row.appendChild(waiveTopUpFeeListcell);
	    	row.setValue(model);
	    }
	}


	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		cardListbox.setItemRenderer(new ResultRenderer());
		cardModels = new ListModelList();
		cardListbox.setModel(cardModels);
		
		init();
	}
	
	@SuppressWarnings("unchecked")
	public void init(){
		
		Map<String, Object> map = Executions.getCurrent().getArg();

		BigDecimal requestNo = (BigDecimal) map.get("requestNo");
		req = (PmtbTopUpReq) this.businessHelper.getPrepaidBusiness().getPrepaidTopUpRequest(requestNo);
		
		AmtbAccount acct = req.getAmtbAccount();
		acct = this.businessHelper.getAccountBusiness().getAccountWithParent(String.valueOf(acct.getAccountNo()));
		
		accountMC.populateDetails(acct);
	
		remarksLabel.setValue(req.getRequestRemarks());
		MstbCreditTermMaster mstbCreditTermMaster = req.getMstbCreditTermMaster();
		
		Map<MstbCreditTermMaster, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMastersWithCOD();	
		creditTermLabel.setValue(creditTermMasters.get(mstbCreditTermMaster));
		
		totalAmountLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(req.getTotalAmount()));
		
		Set<PmtbTopUpReqCard> pmtbTopUpReqCards = req.getPmtbTopUpReqCards();
		cardModels.addAll(pmtbTopUpReqCards);
		
		if(req.getUri()!=null && req.getStatus().equals(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT)) {
			paidByCreditCard.setChecked(true);
		}else {
			paidByCreditCard.setChecked(false);
		}
		if(req.getStatus().equals(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT)) {
			if(req.getMstbCreditTermMaster()!=null) {
				this.getFellow("reRequest").setVisible(false);
				this.getFellow("resendEmail").setVisible(false);
			}else if (req.getMstbCreditTermMaster() == null && req.getUri()== null){
				this.getFellow("resendEmail").setVisible(false);
			}
			// only allow for COD 
			// if req had been submitted as creditterm , credits would have been generated in 
		}else {
			this.getFellow("reRequest").setVisible(false);
			this.getFellow("resendEmail").setVisible(false);
		}

	}

	
	public void previewMiscInvoice() throws NetException, IOException{
		
		BmtbDraftInvHeader draftHeader = (BmtbDraftInvHeader) this.businessHelper.getPrepaidBusiness().createTopUpInvoice(req, getUserLoginIdAndDomain(), true);
		
		byte[] bytes = this.businessHelper.getReportBusiness().generatePrepaidInvoice(req.getAmtbAccount(), draftHeader.getInvoiceHeaderNo().toString(), true);
		this.businessHelper.getInvoiceBusiness().updateInvoiceHeaderFile(bytes, draftHeader);

		AMedia media = new AMedia(NonConfigurableConstants.REPORT_NAME_INV_MISC + ".pdf", "pdf", Constants.FORMAT_PDF, bytes);
		Filedownload.save(media);
	}
	
	@SuppressWarnings("unchecked")
	public void reRequest() throws InterruptedException, NetException, IOException, ExpectedException{
		String msg = "Top Up request submitted successfully.";
		if(!ComponentUtil.confirmBox("Do you confirm to re-submit the request?", "New Top Up Request")){
			return;
		}
		displayProcessing();
		
		if(paidByCreditCard.isChecked()) {
			 
			if(req.getUri()!=null) {
				msg="Top up had been submitted before";
				
			}else {
			//	String link = getURL().replace("/zkau", "/redirect.zul?");
				String link = getHttpServletRequest().getHeader("Origin")+"/ibs/redirect.zul?";
				this.businessHelper.getPrepaidBusiness().recreateTopUpCreditRequest(req,link);
				this.getFellow("resendEmail").setVisible(true);
			}
		    
			
		}else if (!paidByCreditCard.isChecked()) {
			if(req.getUri()==null) {
				msg="Top up had been submitted before";
				
			}else {
				req.setUri(null);
				req.setFailureUri(null);
				this.businessHelper.getPrepaidBusiness().update(req);
			}
			
		}

		Messagebox.show(msg, "Top Up Request", Messagebox.OK, Messagebox.INFORMATION);
	}
	
	@SuppressWarnings("unchecked")
	public void resendEmail() throws InterruptedException, NetException, IOException{
		
		displayProcessing();
		String responseMsg = "";
		try
		{
			responseMsg= this.businessHelper.getPrepaidBusiness().reddotSendEmail(req.getRedDotInvoiceNo());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			responseMsg = "Error";
		}
		 
		if (responseMsg.trim().equalsIgnoreCase("Success")) 
		{ //success
				System.out.println(""+req.getUri());
		    	String msg = "Email is requested. Please check your email shortly";
				Messagebox.show(msg, "Top Up Request", Messagebox.OK, Messagebox.INFORMATION);
		} else 
		{
		        System.out.println("POST NOT WORKED");
				Messagebox.show("Error", "Top Up Request", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		
		
	}
}
