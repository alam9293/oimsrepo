package com.cdgtaxi.ibs.product.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("unchecked")
public class ManageNegativeExternalProductWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageNegativeExternalProductWindow.class);
	
	private Decimalbox cardNoStartDMB, cardNoEndDMB;
	private Listbox productTypeLB, resultLB;
	private Button createBtn, deleteBtn;
	private Checkbox checkAllChkB;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		productTypeLB.appendChild(ComponentUtil.createNotRequiredListItem());
		List<PmtbProductType> productTypes = this.businessHelper.getProductTypeBusiness().getExternalProductType();
		for(PmtbProductType productType : productTypes){
			productTypeLB.appendChild(new Listitem(productType.getName(), productType));
		}
		
		if(!this.checkUriAccess(Uri.CREATE_NEGATIVE_EXTERNAL_PRODUCT))
			this.createBtn.setDisabled(true);
		if(!this.checkUriAccess(Uri.DELETE_NEGATIVE_EXTERNAL_PRODUCT))
			this.deleteBtn.setDisabled(true);
	}
	
	public void search(boolean redirected) throws InterruptedException{
		
		resultLB.getItems().clear();
		
		if(cardNoStartDMB.getValue()==null && cardNoEndDMB.getValue()==null &&
				productTypeLB.getSelectedIndex()==0){
			if(!redirected)
				Messagebox.show("Please provide search criteria","Search Criteria", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		if(cardNoStartDMB.getValue()!=null && cardNoEndDMB.getValue()==null)
			cardNoEndDMB.setValue(cardNoStartDMB.getValue());
		else if(cardNoStartDMB.getValue()==null && cardNoEndDMB.getValue()!=null)
			cardNoStartDMB.setValue(cardNoEndDMB.getValue());
		else if(cardNoStartDMB.getValue()!=null && cardNoEndDMB.getValue()!=null){
			if(cardNoStartDMB.getValue().compareTo(cardNoEndDMB.getValue()) > 0)
				throw new WrongValueException(cardNoStartDMB, "Card No Start cannot be greater than Card No End");
		}
		
		List<Object[]> results = this.businessHelper.getProductBusiness().getNegativeExternalProduct(
				cardNoStartDMB.getValue()!=null ? cardNoStartDMB.getText() : null, 
				cardNoEndDMB.getValue()!=null ? cardNoEndDMB.getText() : null, 
				productTypeLB.getSelectedIndex()==0 ? null : (PmtbProductType)productTypeLB.getSelectedItem().getValue()
			);
		
		if(results.size()>0){
			
			if(results.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for(Object[] result : results){
				Listitem item = new Listitem();
				item.setValue(result[0]);
				item.appendChild(newListcell(result[0]));
				item.appendChild(newListcell(result[1]));
				item.appendChild(newListcell(result[2], DateUtil.LAST_UPDATED_DATE_FORMAT));
				item.appendChild(newListcell(result[3]));
				
				//last one is the checkbox
				Listcell checkboxListCell = new Listcell();
				Checkbox checkbox = new Checkbox();
				if(result[4]!=null && result[4].toString().equals(NonConfigurableConstants.BOOLEAN_YES))
					checkbox.setDisabled(true);
				checkboxListCell.appendChild(checkbox);
				item.appendChild(checkboxListCell);
				
				resultLB.appendChild(item);
			}
			
			if(results.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultLB.getListfoot()!=null)
				resultLB.removeChild(resultLB.getListfoot());
		}
		else{
			if(resultLB.getListfoot()==null){
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(5));
			}
		}
		
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.search(true);
	}
	
	public void reset(){
		checkAllChkB.setChecked(false);
		cardNoStartDMB.setText("");
		cardNoEndDMB.setText("");
		if(productTypeLB.getChildren().size()>0)
			productTypeLB.setSelectedIndex(0);
		
		resultLB.getItems().clear();
		if(resultLB.getListfoot()==null){
			resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(5));
		}
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	public void checkAll(){
		List<Listitem> items = resultLB.getItems();
		if(checkAllChkB.isChecked()){
			for(Listitem item : items){
				Checkbox checkbox = (Checkbox)((Listcell)item.getChildren().get(4)).getFirstChild();
				if(checkbox.isDisabled()==false)
					checkbox.setChecked(true);
			}
		}
		else
			for(Listitem item : items){
				Checkbox checkbox = (Checkbox)((Listcell)item.getChildren().get(4)).getFirstChild();
				if(checkbox.isDisabled()==false)
					checkbox.setChecked(false);
			}
	}
	
	public void delete() throws InterruptedException{
		try{
			int response = Messagebox.show("Confirm to delete selected negative external card(s)?", 
					"Delete Negative External Product", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
			if(response == Messagebox.CANCEL) return;
			
			List<Listitem> items = resultLB.getItems();
			boolean atLeastOneItemIsChecked = false;
			for(Listitem item : items){
				if(((Checkbox)((Listcell)item.getChildren().get(4)).getFirstChild()).isChecked()){
					atLeastOneItemIsChecked = true;
					
					String cardNo = (String)item.getValue();
					if(this.businessHelper.getEnquiryBusiness().isExternalProductRequestExist(cardNo))
						continue;
					
					API.createNegativeProduct(cardNo, NonConfigurableConstants.EXTERNAL_PRODUCT_STATUS_DELETE, getUserLoginIdAndDomain());
				}
			}
			
			if(atLeastOneItemIsChecked==false)
				Messagebox.show("Please check at least one card for deletion", "Delete Negative External Product", Messagebox.OK, Messagebox.ERROR);
			else{
				Messagebox.show("Delete negative external product request inserted.", "Delete Negative External Product", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			}
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
	
	public void create() throws InterruptedException{
		this.forward(Uri.CREATE_NEGATIVE_EXTERNAL_PRODUCT, null);
	}
}
