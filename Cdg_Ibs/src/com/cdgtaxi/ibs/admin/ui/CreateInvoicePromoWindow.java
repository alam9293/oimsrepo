package com.cdgtaxi.ibs.admin.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.email.FileAttachment;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbInvoicePromo;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class CreateInvoicePromoWindow extends CommonWindow implements AfterCompose {
	
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> promoList = new ArrayList<Listitem>();
	private static final Logger logger = Logger.getLogger(CreateInvoicePromoWindow.class);
	private CapsTextbox name;
	private Datebox dateFromDateBox;
	private Listbox attachmentLB;
	private Map<byte[], String> attachmentFile = new HashMap();
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		promoList.add(new Listitem("-", ""));
		promoList.add(new Listitem("Promo 1","Promo 1"));
		promoList.add(new Listitem("Promo 2","Promo 2"));
		promoList.add(new Listitem("Promo 3","Promo 3"));
		
		Listbox races = (Listbox)this.getFellow("raceList");
		Map<String, String> raceMap = ConfigurableConstants.getRace();
		for(Map.Entry<String, String> entry : raceMap.entrySet()){
			Listitem item = new Listitem(entry.getValue(), entry.getKey());
			races.appendChild(item);
		}

	}
	public void create() throws InterruptedException{
		try{
			this.displayProcessing();
			
			String nameValue = "";
			if(name !=null)
				nameValue = name.getValue();
			
			if(nameValue.trim().equals(""))
				throw new WrongValueException(name, "Name cannot be empty.");
			
			Date insertDate = null;
			
			if(dateFromDateBox.getValue() != null)
				insertDate = dateFromDateBox.getValue();
			else 
				throw new WrongValueException(dateFromDateBox, "Insert Date From cannot be empty.");

			String checkOverdue = (String)((Listbox)this.getFellow("checkOverdue")).getSelectedItem().getValue();
			String checkInvoiceCorp = (String)((Listbox)this.getFellow("checkInvoiceCorp")).getSelectedItem().getValue();
			String checkInvoicePers = (String)((Listbox)this.getFellow("checkInvoicePers")).getSelectedItem().getValue();
			String checkPt = (String)((Listbox)this.getFellow("checkPt")).getSelectedItem().getValue();
			String checkMainBillContact = (String)((Listbox)this.getFellow("checkMainBillingContact")).getSelectedItem().getValue();
			String checkRace = (String)((Listbox)this.getFellow("checkRace")).getSelectedItem().getValue();
			String checkAltContact = (String)((Listbox)this.getFellow("checkAltContact")).getSelectedItem().getValue();
			String checkPromo = (String)((Listbox)this.getFellow("checkPromo")).getSelectedItem().getValue();
			
			BigDecimal checkInvoiceCorpValue = new BigDecimal("0.00");
			BigDecimal checkInvoicePersValue = new BigDecimal("0.00");
			BigDecimal checkPtValue = new BigDecimal("0");
			BigDecimal checkQuantityValue = new BigDecimal("1");
			
			Listbox racesList = (Listbox)this.getFellow("raceList");
			if(checkRace.equals("Y") && racesList.getSelectedItems().isEmpty()){
				Messagebox.show("Please select one or more Races.", "Select Race", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			String checkRaceValue = "";
			if(checkRace.equals("Y"))
			{
				for(Object selectedItem : racesList.getSelectedItems()){
					
					if(checkRaceValue.trim().equals(""))
						checkRaceValue = (String) ((Listitem)selectedItem).getValue();
					else
						checkRaceValue = checkRaceValue + ";" + ((Listitem)selectedItem).getValue();
				}
			}
			
			if(checkInvoiceCorp.equalsIgnoreCase("Y"))
				checkInvoiceCorpValue = ((Decimalbox)this.getFellow("checkInvoiceCorpValue")).getValue();
			if(checkInvoicePers.equalsIgnoreCase("Y"))
				checkInvoicePersValue = ((Decimalbox)this.getFellow("checkInvoicePersValue")).getValue();
			if(checkPt.equalsIgnoreCase("Y"))
				checkPtValue = ((Decimalbox)this.getFellow("checkPtValue")).getValue();
			checkQuantityValue = ((Decimalbox)this.getFellow("checkQuantityValue")).getValue();
			
			if(checkQuantityValue == null)
				checkQuantityValue = new BigDecimal("1");
			
			MstbInvoicePromo invoicePromo = new MstbInvoicePromo();
			invoicePromo.setName(nameValue);
			invoicePromo.setInsertDt(new Timestamp(insertDate.getTime()));
			invoicePromo.setCheckOverdue(checkOverdue);
			invoicePromo.setCheckInvoiceCorp(checkInvoiceCorp);
			invoicePromo.setCheckInvoiceCorpValue(checkInvoiceCorpValue);
			invoicePromo.setCheckInvoicePers(checkInvoicePers);
			invoicePromo.setCheckInvoicePersValue(checkInvoicePersValue);
			invoicePromo.setCheckPt(checkPt);
			invoicePromo.setCheckPtValue(checkPtValue);
			invoicePromo.setCheckMainBillContact(checkMainBillContact);
			invoicePromo.setCheckRace(checkRace);
			invoicePromo.setCheckAltContact(checkAltContact);
			invoicePromo.setCheckQuantityValue(checkQuantityValue);
			invoicePromo.setCheckRaceValue(checkRaceValue);
			
			String promo = "";
			
			if(checkPromo.equals("Promo 1")) { 
				invoicePromo.setPromo1("Y");
				invoicePromo.setPromo2("");
				invoicePromo.setPromo3("");
				promo = "1";
			}
			else if(checkPromo.equals("Promo 2")) {
				invoicePromo.setPromo2("Y");
				invoicePromo.setPromo1("");
				invoicePromo.setPromo3("");
				promo = "2";
			}
			else if(checkPromo.equals("Promo 3")) {
				invoicePromo.setPromo3("Y");
				invoicePromo.setPromo2("");
				invoicePromo.setPromo1("");
				promo = "3";
			}
			
			if(!attachmentFile.isEmpty()) {
				for(Map.Entry<byte[], String> entry : attachmentFile.entrySet()) 
				{
					invoicePromo.setFileName(entry.getValue());
					invoicePromo.setFileData(Hibernate.createBlob(entry.getKey()));
					break;
				}
			}
			else {
				invoicePromo.setFileData(null);
				invoicePromo.setFileName(null);
			}
			
			//if got promo, must check
			boolean checkPromoNumber = false;
			if(!promo.trim().equals("")) {
			
				checkPromoNumber = this.businessHelper.getAdminBusiness().checkPromoNumber(promo, invoicePromo.getInsertDt(), 0);
				if(checkPromoNumber) {
					Messagebox.show("Error: There is a existing Promo 1, 2, 3 on that Date.",
							"Error", Messagebox.OK, Messagebox.ERROR);
				}
			}
			if(!checkPromoNumber) {
				this.businessHelper.getGenericBusiness().save(invoicePromo, getUserLoginIdAndDomain());	
				Messagebox.show("New Invoice Promo created.", "Create Invoice Promo", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
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
	
	
	public void checkInvoicePers(Listitem selectedItem){
		logger.info("checkInvoicePers(Listitem selectedItem)");
		boolean toPers = false;
		if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
			toPers = true;
		((Decimalbox)this.getFellow("checkInvoicePersValue")).setDisabled(toPers);
	}

	public void checkInvoiceCorp(Listitem selectedItem){
		logger.info("checkInvoiceCorp(Listitem selectedItem)");
		boolean toCorp = false;
		if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
			toCorp = true;
		((Decimalbox)this.getFellow("checkInvoiceCorpValue")).setDisabled(toCorp);
	}
	public void checkInvoicePt(Listitem selectedItem){
		logger.info("checkInvoicePt(Listitem selectedItem)");
		boolean toPt = false;
		if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
			toPt = true;
		((Decimalbox)this.getFellow("checkPtValue")).setDisabled(toPt);
	}
//	public void checkAltContact(Listitem selectedItem){
//		logger.info("checkAltContact(Listitem selectedItem)");
//		boolean toAlt = false;
//		if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
//			toAlt = true;
//		((Decimalbox)this.getFellow("checkQuantityValue")).setDisabled(toAlt);
//	}
	public void checkInvoiceRace(Listitem selectedItem){
		logger.info("checkRace(Listitem selectedItem)");
		boolean toRace = false;
		if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
			toRace = true;
		((Listbox)this.getFellow("raceList")).setDisabled(toRace);
		
		if(toRace)
			((Listbox)this.getFellow("raceList")).clearSelection();
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
	public List<Listitem> getBooleanList() {
		return cloneList(booleanList);
	}
	public void setBooleanList(List<Listitem> booleanList) {
		this.booleanList = booleanList;
	}
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			if(listitem.isSelected()){
				Listitem newListitem = new Listitem(listitem.getLabel(), listitem.getValue());
				newListitem.setSelected(true);
				returnList.add(newListitem);
			}else{
				returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
			}
		}
		return returnList;
	}
	public List<Listitem> getPromoList() {
		return cloneList(promoList);
	}
	public void attachFile() throws InterruptedException {
		try {
			Media media = Fileupload.get();
			
			if (media != null) {
				byte[] bytes;
				if (!media.getContentType().equals("text/plain")) {

					InputStream is = media.getStreamData();
					long length = is.available();
					bytes = new byte[(int) length];
					is.read(bytes);
				} else {
					bytes = media.getStringData().getBytes();
				}

				File file = File.createTempFile("IBS", "");
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(bytes);
				logger.info("Temp File: " + file.getAbsolutePath());

//				long fileSize = file.length();
//
//				MstbMasterTable master = ConfigurableConstants.getMasterTable(
//						ConfigurableConstants.GENERIC_MASS_EMAIL_MASTER_TYPE,
//						ConfigurableConstants.GENERIC_MASS_EMAIL_FILE_SIZE_MASTER_CODE);
//
//				long maxFileSize = Long.parseLong(master.getMasterValue());
//				if (fileSize > maxFileSize)
//					throw new WrongValueException("Attached file exceeds max file size " + maxFileSize
//							+ " bytes!");

				this.appendFileAttachmentToListbox(new FileAttachment(media.getName(), file), bytes);

			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	private void appendFileAttachmentToListbox(FileAttachment attachment, byte[] bytes) {
		
		for (Object object : attachmentLB.getItems()) {
			Listitem item = (Listitem) object;
			attachmentLB.removeChild(item);
		}
		attachmentFile.clear();
		
		Listitem item = new Listitem();
		item.appendChild(newListcell(attachment.fileName));
		item.setValue(attachment);
		attachmentFile.put(bytes, attachment.fileName);

		Listcell deleteImageCell = new Listcell();
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		// returns a listitem
		ZScript showInfo = ZScript
				.parseContent("createInvoicePromoWindow.deleteRow(self.getParent().getParent())");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		deleteImageCell.appendChild(deleteImage);
		item.appendChild(deleteImageCell);

		attachmentLB.appendChild(item);
		
		((Listbox)this.getFellow("checkAltContact")).setDisabled(true);
		((Decimalbox)this.getFellow("checkQuantityValue")).setDisabled(true);
	}
	public void deleteRow(Listitem item) {
		attachmentLB.removeChild(item);
		attachmentFile.clear();
		
		((Listbox)this.getFellow("checkAltContact")).setDisabled(false);
		((Decimalbox)this.getFellow("checkQuantityValue")).setDisabled(false);
	}
	public void downloadFile() {
		try {
			if(!attachmentFile.isEmpty()) {
				for(Map.Entry<byte[], String> entry : attachmentFile.entrySet()) 
				{
					byte[] file = entry.getKey();
					if (file != null) {
						Filedownload.save(file, "",	entry.getValue());
					}
					break;
				}
			}
			else {
				Messagebox.show("No file to download.", "Edit Invoice Promo", Messagebox.OK, Messagebox.INFORMATION);				
			}
		}catch(Exception e) {}
	}
}
