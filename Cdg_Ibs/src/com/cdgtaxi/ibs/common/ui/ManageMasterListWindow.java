package com.cdgtaxi.ibs.common.ui;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

@SuppressWarnings("serial")
public class ManageMasterListWindow extends CommonWindow {
	private static Logger logger = Logger.getLogger(ManageMasterListWindow.class);

	private MstbMasterTable selectedMaster;

	private Row codeTBRow, codeLabelRow;

	private Label typeLabel, codeLabel;
	private Listbox typeLB, tableLB, groupLB;
	private final Listitem newValueItem = new Listitem("[New Code]");
	private Textbox codeTB, interfaceMapValueTB; //valueTB
	private FCKeditor valueFE;
	private Radiogroup statusRG;
	private Radio statusActive, statusInactive;
	private Button createButton, updateButton;
	private String masterGrouping;
	Entry<String, Set<MstbMasterTable>> entryGroup;
	public ManageMasterListWindow(){
		//		Map<String,String> map = Executions.getCurrent().getArg();
		//		this.type = map.get("type");
		//		this.setWidth("75%");
		//		this.setMinwidth(100);
		//		this.setSizable(false);
		//		this.setClosable(true);
	}

	public void onCreate() {
		codeTBRow = (Row) getFellow("codeTBRow");
		codeLabelRow = (Row) getFellow("codeLabelRow");
		typeLabel = (Label) getFellow("typeLabel");
		codeLabel = (Label) getFellow("codeLabel");
		typeLB = (Listbox) getFellow("typeLB");
		tableLB = (Listbox) getFellow("tableLB");
		codeTB = (Textbox) getFellow("codeTB");
		//		valueTB = (Textbox) getFellow("valueTB");
		valueFE = (FCKeditor) getFellow("valueFE");
		statusRG = (Radiogroup) getFellow("statusRG");
		statusActive = (Radio) getFellow("statusActive");
		statusInactive = (Radio) getFellow("statusInactive");
		interfaceMapValueTB = (Textbox) getFellow("interfaceMapValueTB");
		createButton = (Button) getFellow("createButton");
		updateButton = (Button) getFellow("updateButton");
		groupLB = (Listbox) getFellow("groupLB");
		//groupLabel = (Label) getFellow("groupLabel");

		refresh();
	}


	@SuppressWarnings("unchecked")
	public void populateGroupingItems() {
		if (groupLB.getSelectedItem() == null) {
			return;
		}
		
		Set<MstbMasterTable> selectedGroupTypes = (Set<MstbMasterTable>) ((Entry) groupLB.getSelectedItem().getValue()).getValue();

		typeLB.getChildren().clear();
		for (MstbMasterTable masterRecord : selectedGroupTypes) {
			String label = ConfigurableConstants.getTypeName(masterRecord.getMasterType()) + " (" + masterRecord.getMasterType() + ")";
			
			Map<String, Set<MstbMasterTable>> types = new TreeMap<String, Set<MstbMasterTable>>(
					new Comparator<String>() {
						public int compare(String type1, String type2) {
							String name1 = ConfigurableConstants.getTypeName(type1);
							String name2 = ConfigurableConstants.getTypeName(type2);
							name1 = (name1 == null) ? "" : name1;
							name2 = (name2 == null) ? "" : name2;
							return name1.compareTo(name2);
						}
					});

			Map<String,String> masterCodesAndValuesMap = ConfigurableConstants.getAllMasters(masterRecord.getMasterType());
			for (String masterCode : masterCodesAndValuesMap.keySet()) {
				MstbMasterTable table = ConfigurableConstants.getAllMasterTable(masterRecord.getMasterType(), masterCode);
				String type = table.getMasterType();
				Set<MstbMasterTable> typeSet = types.get(type);
				if (typeSet == null) {
					typeSet = new TreeSet<MstbMasterTable>(new Comparator<MstbMasterTable>() {
						public int compare(MstbMasterTable o1, MstbMasterTable o2) {
							return o1.getMasterCode().compareTo(o2.getMasterCode());
						}
					});
					types.put(type, typeSet);
				}
				typeSet.add(table);
			}
			
			HashMap<String, Set<MstbMasterTable>> dummyMap = new HashMap<String, Set<MstbMasterTable>>();
			dummyMap.put(masterRecord.getMasterType(), types.get(masterRecord.getMasterType()));
			typeLB.appendChild(new Listitem(label,  dummyMap.entrySet().iterator().next()));
		}
		entryGroup = (Entry<String, Set<MstbMasterTable>>) groupLB
				.getSelectedItem().getValue();
		masterGrouping =entryGroup.getKey();
		populateItems();

	}
	
	@SuppressWarnings("unchecked")
	public void populateItems() {
		if (typeLB.getSelectedItem() == null) {
			return;
		}

		Set<MstbMasterTable> typeSet = (Set<MstbMasterTable>) ((Entry<String, Set<MstbMasterTable>>) typeLB.getSelectedItem().getValue()).getValue();
		
		tableLB.getChildren().clear();
		tableLB.appendChild(newValueItem);
		for (MstbMasterTable table : typeSet) {
			tableLB.appendChild(new Listitem(table.getMasterCode(), table));
		}
		tableLB.selectItem(newValueItem);
		populateTableDetails();
	}

	@SuppressWarnings("unchecked")
	public void populateTableDetails() {
		if (tableLB.getSelectedItem() == null) {
			return;
		}

		MstbMasterTable table = (MstbMasterTable) tableLB.getSelectedItem().getValue();
		if (table == null) { 
			// New Value
			codeTBRow.setVisible(true);
			codeLabelRow.setVisible(false);

			selectedMaster = null;
			// typeLabel.setValue(typeLB.getSelectedItem().getLabel());
			Entry<String, Set<MstbMasterTable>> entry = (Entry<String, Set<MstbMasterTable>>) typeLB
					.getSelectedItem().getValue();
			typeLabel.setValue(entry.getKey());
			codeTB.setRawValue(null);
			// valueTB.setRawValue(null);
			valueFE.setValue(null);
			statusActive.setSelected(true);
			statusInactive.setSelected(false);
			interfaceMapValueTB.setRawValue(null);
			createButton.setVisible(true);
			updateButton.setVisible(false);

			codeTB.focus();
		} else {
			codeTBRow.setVisible(false);
			codeLabelRow.setVisible(true);

			selectedMaster = table;
			typeLabel.setValue(table.getMasterType());
			codeLabel.setValue(table.getMasterCode());
			// valueTB.setValue(table.getMasterValue());
			valueFE.setValue(table.getMasterValue());
			// statusRG.setSelectedItem(new Radio(table.getMasterStatus()));
			if (table.getMasterStatus().equals(NonConfigurableConstants.STATUS_ACTIVE)) {
				statusActive.setSelected(true);
			} else {
				statusInactive.setSelected(true);
			}
			interfaceMapValueTB.setValue(table.getInterfaceMappingValue());
			createButton.setVisible(false);
			updateButton.setVisible(true);

			// valueTB.focus();
		}
	}

	public void updateTable() throws InterruptedException {
		String html = valueFE.getValue();
		html = cleanHtml(html);
		if(html == null || html.length()==0){
			Messagebox.show("Please enter the master value.", "Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		selectedMaster.setMasterStatus(statusRG.getSelectedItem().getValue());
		//		selectedMaster.setMasterValue(valueTB.getValue());
		selectedMaster.setMasterValue(html);
		selectedMaster.setInterfaceMappingValue(interfaceMapValueTB.getValue());

		try {
			businessHelper.getGenericBusiness().update(selectedMaster);
//			businessHelper.getAdminBusiness().update(selectedMaster);
			
			if(selectedMaster.getMasterType().equals(ConfigurableConstants.REWARDS_GRACE_PERIOD)){
				
				List<LrtbRewardAccount> rewardsAccounts = this.businessHelper.getAdminBusiness().getActiveRewardsAccount();
				
				//update grace period would affect expire date and further affect IBS expire date since it is calculated depend on expire date. 
				if(selectedMaster.getMasterCode().equals(NonConfigurableConstants.REWARDS_GRACE_PERIOD_MASTER_CODE)){

					Integer extMthToIBSExpiry = new Integer(ConfigurableConstants.getMasterTable(ConfigurableConstants.REWARDS_GRACE_PERIOD, NonConfigurableConstants.REWARDS_IBS_GRACE_PERIOD_MASTER_CODE).getMasterValue());
					Integer extMthToExpiry = new Integer(selectedMaster.getMasterValue());
					for(LrtbRewardAccount rewardsAccount : rewardsAccounts){
						Integer orgValue = rewardsAccount.getExtMthToExpiry()==null ? 0 : rewardsAccount.getExtMthToExpiry();
						Integer difference = extMthToExpiry - orgValue;
						rewardsAccount.setExtMthToExpiry(extMthToExpiry);
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(rewardsAccount.getExpireDt().getTime());
						calendar.add(Calendar.MONTH, difference);
						rewardsAccount.setExpireDt(new Timestamp(calendar.getTimeInMillis()));
						calendar.add(Calendar.MONTH, extMthToIBSExpiry);
						rewardsAccount.setIbsExpireDt(new Timestamp(calendar.getTimeInMillis()));
						businessHelper.getGenericBusiness().update(rewardsAccount);
					}
				}
				//update IBS grace period only affect IBS expire date.
				else if(selectedMaster.getMasterCode().equals(NonConfigurableConstants.REWARDS_IBS_GRACE_PERIOD_MASTER_CODE)){
					
					Integer extMthToIBSExpiry = new Integer(selectedMaster.getMasterValue());
					for(LrtbRewardAccount rewardsAccount : rewardsAccounts){
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(rewardsAccount.getExpireDt().getTime());
						calendar.add(Calendar.MONTH, extMthToIBSExpiry);
						rewardsAccount.setIbsExpireDt(new Timestamp(calendar.getTimeInMillis()));
						businessHelper.getGenericBusiness().update(rewardsAccount);
					}
				}
			}
			
			ConfigurableConstants.refresh();
			
			Messagebox.show(
					"Record has been successfully updated.", "Update Master Record",
					Messagebox.OK, Messagebox.INFORMATION);
			//		} catch (RewardPlanOverlappingEffectiveDateException e) {
			//			throw new WrongValueException(startDateDB, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.toString());
		}
	}

	private String cleanHtml(String html) {
		// if only 1 set of <p> tag exists, remove it and treat the value as raw value. other
		if (html.split("<p>").length == 2) {
			html = html.replaceFirst("^<p>(.*)</p>$", "$1");
		}
		
		return html;
	}

	@SuppressWarnings("unchecked")
	public void saveTable() throws InterruptedException {
		String html = valueFE.getValue();
		html = cleanHtml(html);
		if(html == null || html.length()==0){
			Messagebox.show("Please enter the master value.", "Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		MstbMasterTable newMaster = new MstbMasterTable();
		newMaster.setMasterType(typeLabel.getValue());
		newMaster.setMasterCode(codeTB.getValue());
		newMaster.setMasterStatus(statusRG.getSelectedItem().getValue());
		System.out.println(interfaceMapValueTB.getValue());
		newMaster.setInterfaceMappingValue(interfaceMapValueTB.getValue());
		newMaster.setMasterGrouping(masterGrouping);
		newMaster.setMasterValue(html);

		//		String selectedType = typeLB.getSelectedItem().getLabel();
		Entry<String, Set<MstbMasterTable>> selectedEntry =
			(Entry<String, Set<MstbMasterTable>>) typeLB.getSelectedItem().getValue();

		try {
			businessHelper.getGenericBusiness().save(newMaster);

			// refresh the listings
			ConfigurableConstants.refresh();
			refresh();
			for (Listitem item : (List<Listitem>) typeLB.getChildren()) {
				Entry<String, Set<MstbMasterTable>> entry =
					(Entry<String, Set<MstbMasterTable>>) item.getValue();
				if (entry.getKey().equals(selectedEntry.getKey())) {
					typeLB.setSelectedItem(item);
					break;
				}
			}
			populateItems();
			Messagebox.show(
					"Record has been successfully saved.", "Save Master Record",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			Messagebox.show("Please specify another Code.", "Code Exists", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.toString());
		}
	}

	@Override
	public void refresh() {

		List<MstbMasterTable> masterTables = ConfigurableConstants.getAllMasterTable();
		
		Map<String, Set<MstbMasterTable>> groups =
				new TreeMap<String, Set<MstbMasterTable>>(new Comparator<String>() {
					public int compare(String group1, String group2) {
						String name1 = group1;
						String name2 = group2;
						name1 = (name1 == null) ? "" : name1;
						name2 = (name2 == null) ? "" : name2;
						return name1.compareTo(name2);
					}
				});
		
		//This is to find distinct master grouping and populate into a group set
		for (MstbMasterTable table : masterTables) {			
			String group = table.getMasterGrouping();
			Set<MstbMasterTable> groupSet = groups.get(group);
			if (groupSet == null) {
				groupSet = new TreeSet<MstbMasterTable>(new Comparator<MstbMasterTable>() {
					public int compare(MstbMasterTable o1, MstbMasterTable o2) {
						String name1 = ConfigurableConstants.getTypeName(o1.getMasterType());
						String name2 = ConfigurableConstants.getTypeName(o2.getMasterType());
						name1 = (name1 == null) ? "" : name1;
						name2 = (name2 == null) ? "" : name2;
						return name1.compareTo(name2);
					}
				});
				groups.put(group, groupSet);
			}
			groupSet.add(table);
		}
		
		//This is to populate the group list box values
		groupLB.getChildren().clear();
		for (Entry<String, Set<MstbMasterTable>> entry : groups.entrySet()) {
			String group = entry.getKey();
			groupLB.appendChild(new Listitem(group, entry));
		}
		
	}
}