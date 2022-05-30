package com.cdgtaxi.ibs.prepaid.ui;

import static com.cdgtaxi.ibs.util.ComponentUtil.getSelectedItems;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelSet;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReqCard;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.product.ui.CommonIssueProductWindow;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@SuppressWarnings("serial")
public class IssuanceReqAddCardWindow extends CommonIssueProductWindow implements ListDataListener{

	private ListModelSet promotionModels;
	private Listbox promotionListbox;
	private String acctNo;
	private Row addPromotionRow;
	private Button addCardButton;
	private PmtbIssuanceReqCard toPopulateCard;
	private String mode;
	

	public class ResultRenderer implements ListitemRenderer {
		public void render(Listitem row, Object obj) {

			MstbPromotionCashPlus model = (MstbPromotionCashPlus) obj;
			row.appendChild(new Listcell(model.getPromoCode()));
			row.appendChild(new Listcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCashplus())));
			row.setValue(model);
		}
	}

	public IssuanceReqAddCardWindow() {
		
		setDivDeptWindowType(TYPE_DEPEND);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) Executions.getCurrent().getArg();
		mode = (String) params.get("mode");
		
		if(NonConfigurableConstants.MODE_EDIT.equals(mode)){
			
			toPopulateCard = (PmtbIssuanceReqCard) params.get("card");
		} else {
			acctNo = (String) params.get("acctNo");
		}
		
	
	}

	@Override
	public void onCreate(CreateEvent ce) throws Exception {
		
		super.onCreate(ce);
		
		if(NonConfigurableConstants.MODE_EDIT.equals(mode)){
			populateFieldsForEdit();
		} else {
			
			AmtbAccount acct = this.businessHelper.getAccountBusiness().getAccountWithParent(acctNo);
			populateAccount(acct);
			
			// default is to waive the issuance fee
			waiveIssuanceFeeChkBox.setChecked(true);
		}
		
		onSelectProductType2();
	}
	
	@Override
	protected void populateAccount(AmtbAccount acct){
		
		//override the whole populate account method
		accountNoIntBox.setDisabled(true);
		accountNameComboBox.setDisabled(true);
		
		String category = acct.getAccountCategory();

		if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
			divisionComboBox.setDisabled(true);
			
		} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
			divisionComboBox.setDisabled(true);
			departmentComboBox.setDisabled(true);
		}
		
		AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
		accountNoIntBox.setText(topAcct.getCustNo());

		Events.sendEvent(new Event(Events.ON_CHANGE, accountNoIntBox, topAcct.getCustNo()));
		
		if (divDeptWindowType.equals(TYPE_CENTRAL)) {
			if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				ComponentUtil.setSelectedItem(divisionComboBox, acct);
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				ComponentUtil.setSelectedItem(departmentComboBox, acct);
			}
		} else if (divDeptWindowType.equals(TYPE_DEPEND)){

			if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				
				AmtbAccount divAccount = acct;
				List<AmtbAccount> divAccounts = Lists.newArrayList(divAccount);
				buildChildAccountListbox(divisionComboBox, divAccounts);
				ComponentUtil.setSelectedItem(divisionComboBox, acct);
				
				
			} else if (category.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				AmtbAccount deptAccount = acct;
				AmtbAccount divAccount = acct.getAmtbAccount();
				
				List<AmtbAccount> divAccounts = Lists.newArrayList(divAccount);
				buildChildAccountListbox(divisionComboBox, divAccounts);
				ComponentUtil.setSelectedItem(divisionComboBox, divAccount);
				
				
				List<AmtbAccount> deptAccounts = Lists.newArrayList(deptAccount);
				buildChildAccountListbox(departmentComboBox, deptAccounts);
				ComponentUtil.setSelectedItem(departmentComboBox, acct);
				
	
			}
		}

	}

	private void populateFieldsForEdit(){
		
		if(toPopulateCard!=null){
			
			logger.debug("start populating edit fields");
			
			IssueProductForm form = IssueProductForm.buildIssueProductForm(toPopulateCard);
			populateIssueProductForm(form);
			
			promotionModels.clear();
			Set<MstbPromotionCashPlus> mstbPromotionCashPluses = toPopulateCard.getMstbPromotionCashPluses();
	
			
			if(mstbPromotionCashPluses!=null){
				promotionModels.addAll(mstbPromotionCashPluses);
			}
		
		}
	
	}

	public void afterCompose() {
		// wire variables
		super.afterCompose();
		promotionListbox.setItemRenderer(new ResultRenderer());
		promotionModels = new ListModelSet();
		promotionModels.addListDataListener(this);
		promotionListbox.setModel(promotionModels);

	}

	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String accountNo, String name) {
		return this.businessHelper.getProductBusiness().getActiveAccountList(accountNo, name);
	}
	
	@Override
	public List<AmtbAccount> populateDivisionAccounts(AmtbAccount topAcct){
		return this.businessHelper.getAccountBusiness().getActiveChargeToParentDivisionAccounts(topAcct);
		
	}
	
	@Override
	public List<AmtbAccount> populateDepartmentAccounts(AmtbAccount divAcct){
		
		return this.businessHelper.getAccountBusiness().getActiveChargeToParentDepartmentAccounts(divAcct);
	}
	
	
	public void addCard() throws InterruptedException {


		PmtbProductType selectedProductType = ComponentUtil.getSelectedItem(productTypeListBox);
		AmtbAccount acct = selectedAccount;
		
		//binding
		IssueProductForm form = getIssueProductForm();
		
		PmtbIssuanceReqCard reqCard = new PmtbIssuanceReqCard();
		PmtbIssuanceReqCard.buildReqCard(reqCard, form);
		
		if(NonConfigurableConstants.MODE_EDIT.equals(mode)){
			reqCard.setReqCardNo(toPopulateCard.getReqCardNo());
		} else {
			reqCard.setReqCardNo(new BigDecimal(new Date().getTime()));
		}
		
		// find issuance fee of the card
		BigDecimal issuanceFee = null;
		AmtbSubscTo subscribeTo = this.businessHelper.getAccountBusiness().getSubscribeTo(acct, selectedProductType, new Date());
		if (subscribeTo != null) {
			logger.debug("found subscribe account: " + subscribeTo.getComp_id().getAmtbAccount().getAccountNo() + " for " + selectedProductType.getProductTypeId());
			MstbIssuanceFeeMaster mstbIssuanceFeeMaster = subscribeTo.getMstbIssuanceFeeMaster();
			if(mstbIssuanceFeeMaster!=null){
				issuanceFee = MasterSetup.getIssuanceManager().getCurrentIssuanceFee(mstbIssuanceFeeMaster.getIssuanceFeeNo());
			}
		} else {
			logger.debug("cannot find any subscribe account for " + selectedProductType.getProductTypeId());
		}
		if (issuanceFee == null) {
			issuanceFee = BigDecimal.valueOf(0);
		}
		reqCard.setIssuanceFee(issuanceFee);

		@SuppressWarnings("unchecked")
		Set<MstbPromotionCashPlus> selectedPromotions = Sets.newHashSet(promotionModels.iterator());
		reqCard.setMstbPromotionCashPluses(selectedPromotions);

		CommonWindow window = this.back();
		if (window instanceof NewIssuanceReqWindow) {
			((NewIssuanceReqWindow) window).addNewCardIntoCardListBox(reqCard);
		}

	}

	public void cancel() throws InterruptedException {
		this.back();
	}

	public void populateTestData() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		expiryDateField.setValue(cal.getTime());
		cardValueField.setValue(new BigDecimal("20"));

	}

	public void promptAddPromotionWindow() throws InterruptedException {
		this.forward(Uri.ADD_PROMOTION_CASHPLUS, null);
	}

	@SuppressWarnings("unchecked")
	private void calculateCashplus() {

		BigDecimal totalCashplus = new BigDecimal(0);
		Set<MstbPromotionCashPlus> promotions = promotionModels.getInnerSet();

		for (MstbPromotionCashPlus promotion : promotions) {
			totalCashplus = totalCashplus.add(promotion.getCashplus());
		}
		cashplusFieldLabel.setValue(StringUtil.bigDecimalToStringWithGDFormat(totalCashplus));
	}

	public void deletePromotion() throws InterruptedException {
		logger.debug("deletePromotion");
		List<MstbPromotionCashPlus> selectedItems = getSelectedItems(promotionListbox);

		promotionModels.removeAll(selectedItems);

	}

	public void addPromotionIntoPromotionsListBox(List<MstbPromotionCashPlus> selectedItems) throws InterruptedException {
		for (Object item : selectedItems) {
			promotionModels.add(item);
		}
	}

	public void onChange(ListDataEvent arg0) {
		calculateCashplus();
	}

	@Override
	public List<PmtbProductType> getIssuableProductTypes(AmtbAccount account) {
		//only show PREPAID products
		return this.businessHelper.getProductBusiness().getIssuableProductTypes(String.valueOf(account.getAccountNo()), true);
	}
	
	
	public String emptyIssuableProductTypeErrorMsg(){
		return "This Account doesn't subscribe any prepaid product type.";
	}
	
	public void afterPopulatedProductDetails(){
		super.afterPopulatedProductDetails();
		
		//if product details shown, then show button
		boolean isProductDetailsShown = commonProductDetailsGrid.isVisible();
		addPromotionRow.setVisible(isProductDetailsShown);
		addCardButton.setVisible(isProductDetailsShown);
		
	}
	
	public void onSelectProductType2() {
		onSelectProductType();
		
		PmtbProductType selectedProductType = ComponentUtil.getSelectedItem(productTypeListBox);
		boolean isVirtual = NonConfigurableConstants.getBoolean(selectedProductType.getVirtualProduct());

		if(isVirtual)
		{
			coporateCheckBox.setChecked(true);
			coporateCheckBox.setDisabled(true);
			((Label) this.getFellow("mobileLabel")).setSclass("fieldLabel required");
		}
		else
		{
			coporateCheckBox.setDisabled(false);
			coporateCheckBox.setChecked(false);
			((Label) this.getFellow("mobileLabel")).setSclass("fieldLabel");
			((CapsTextbox) this.getFellow("mobileField")).setValue("");
		}
		onCheckCorporate();
	}
	


}
