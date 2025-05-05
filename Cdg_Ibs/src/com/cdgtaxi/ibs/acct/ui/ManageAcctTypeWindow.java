package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Vbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ManageAcctTypeWindow extends CommonWindow implements AfterCompose{
	private static final long serialVersionUID = -1562294796857099799L;
	private static Logger logger = Logger.getLogger(ManageAcctTypeWindow.class);
	private List<PmtbProductType> productTypes;
	
	public ManageAcctTypeWindow(){
	}

	public void afterCompose(){
		if(!this.checkUriAccess(Uri.ADD_ACCT_TYPE))
			((Button)this.getFellow("addAcctTypeBtn")).setDisabled(true);
	}
	
	/**
	 * Search for all account types
	 */
	@SuppressWarnings("unchecked")
	public void search() throws InterruptedException{
		logger.info("search()");
		// getting the account type list
		Listbox acctTypeList = (Listbox)this.getFellow("acctTypeList");
		// getting the selected value of the acctTypeList
		String selectedType = (String)acctTypeList.getSelectedItem().getValue();
		// getting all account types that match the selected category
		List<AmtbAcctType> accountTypes = this.businessHelper.getAccountTypeBusiness().getAccountTypes(selectedType);
		if(accountTypes.size()!=0){
			Rows result = (Rows)this.getFellow("results");
			// clearing all previous results
			result.getChildren().clear();
			// getting all product types
			productTypes = this.businessHelper.getAccountTypeBusiness().getAllProductTypes();
			// now listing all account type + product type
			// 3 account types per row. Others will be left empty
			int counter = 0;
			Row row = new Row();
			row.setValue(new LinkedList<AmtbAcctType>());
			for(AmtbAcctType accountType : accountTypes){
				if(counter%3==0){
					result.appendChild(row);
					row = new Row();
					row.setValue(new LinkedList<AmtbAcctType>());
					counter = 0;
				}
				// vbox for each row item
				Vbox vbox = new Vbox();
				// label for account type name
				Label acctTypeLabel = new Label(accountType.getAcctType());
				// setting font to bold
				acctTypeLabel.setStyle("font-weight: bold;color: #000000;");
				// adding account type name to vbox
				vbox.appendChild(acctTypeLabel);
				// for each product type
				for(PmtbProductType productType : productTypes){
					// check box for each product type
					Checkbox check = new Checkbox(productType.getName());
					// checking if it is subscribed.
					for(PmtbProductType subscribed : accountType.getPmtbProductTypes()){
						if(subscribed.getName().equals(productType.getName())){
							check.setChecked(true);
							break;
						}
					}
					vbox.appendChild(check);
				}
				((List<AmtbAcctType>)row.getValue()).add(accountType);
				row.appendChild(vbox);
				counter++;
			}
			result.appendChild(row);
			// showing save button
			this.getFellow("saveButton").setVisible(true);
			// now allowing user to see
			this.getFellow("gridResult").setVisible(true);
		}else{
			// showing alert
			Messagebox.show("No matching result", "Manage Account Type", Messagebox.OK, Messagebox.EXCLAMATION);
			// hiding save button
			this.getFellow("saveButton").setVisible(false);
			// allowing user to see the result
			this.getFellow("gridResult").setVisible(false);
		}
	}
	/**
	 * Saving the changes
	 */
	@SuppressWarnings("unchecked")
	public void save() throws InterruptedException{
		logger.info("save()");
		// list to save all account type
		List<AmtbAcctType> saveAccountTypes = new ArrayList<AmtbAcctType>();
		// extracting result listbox
		Rows result = (Rows)this.getFellow("results");
		// getting the row
		List<Row> resultRows = result.getChildren();
		// should only have 1 row. extracting from first row
		for(Row resultRow : resultRows){
			// getting the list of cells
			List<Vbox> resultBoxes = resultRow.getChildren();
			LinkedList<AmtbAcctType> accountTypes = ((LinkedList<AmtbAcctType>)resultRow.getValue());
			// now looping for each cell
			for(Vbox resultBox : resultBoxes){
				// now getting the account type from the cell
				AmtbAcctType currentAccountType = accountTypes.poll();
				// now getting each product type check box from the vbox
				List checks = resultBox.getChildren();
				// getting all product types
				Set<PmtbProductType> currentProductTypes = currentAccountType.getPmtbProductTypes();
				// clear all product types
				currentProductTypes.clear();
				// looping
				for(Object check : checks){// checking all checkbox
					if(check instanceof Checkbox){
						Checkbox checkbox = ((Checkbox)check);
						if(checkbox.isChecked()){// if checked
							for(PmtbProductType productType : productTypes){// looping thru each product type
								if(productType.getName().equals(checkbox.getLabel())){// if product type name = checked name
									// add product type into account type and break off looping
									currentProductTypes.add(productType);
									break;
								}
							}
						}
					}
				}
				saveAccountTypes.add(currentAccountType);
			}
		}
		// passing it to business layer for saving
		this.businessHelper.getAccountTypeBusiness().saveAccountTypes(saveAccountTypes);
		Messagebox.show("Saved!", "Manage Account Type", Messagebox.OK, Messagebox.INFORMATION);
		this.refresh();
	}
	public void addAcctType() throws InterruptedException{
		logger.info("addAcctType()");
		this.forward(Uri.ADD_ACCT_TYPE, null);
	}
	public List<Listitem> getAccountTemplates(){
		logger.info("getAccountTemplates()");
		ArrayList<Listitem> accountTemplates = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.ACCOUNT_TEMPLATES.keySet()){
			accountTemplates.add(new Listitem(NonConfigurableConstants.ACCOUNT_TEMPLATES.get(key), key));
		}
		return accountTemplates;
	}
	/**
	 * Overriden method from window. Things to do when refresh or back is issued
	 * to this window.
	 */
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		// clearing all results
		Rows results = (Rows)this.getFellow("results");
		results.getChildren().clear();
		this.getFellow("gridResult").setVisible(false);
		// hiding save button
		this.getFellow("saveButton").setVisible(false);
	}
}