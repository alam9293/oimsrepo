package com.cdgtaxi.ibs.admin.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zkplus.hibernate.HibernateUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.email.FileAttachment;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbInvoicePromo;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.google.common.base.Splitter;

@SuppressWarnings("serial")
public class EditInvoicePromoWindow extends CommonWindow implements AfterCompose {

	MstbInvoicePromo invoicePromo;
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> promoList = new ArrayList<Listitem>();
	private static final Logger logger = Logger.getLogger(CreateInvoicePromoWindow.class);
	private CapsTextbox name;
	private Datebox dateFromDateBox;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	private Listbox attachmentLB;
	private Map<byte[], String> attachmentFile = new HashMap();
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		Integer invoicePromoId = (Integer)map.get("invoicePromoId");
		
		invoicePromo = this.businessHelper.getAdminBusiness().getInvoicePromo(invoicePromoId);
		
		if(invoicePromo.getCreatedBy()!=null)createdByLabel.setValue(invoicePromo.getCreatedBy());
		else createdByLabel.setValue("-");
		if(invoicePromo.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(invoicePromo.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(invoicePromo.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(invoicePromo.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(invoicePromo.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(invoicePromo.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(invoicePromo.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(invoicePromo.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(invoicePromo.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(invoicePromo.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
				
		name.setValue(invoicePromo.getName());
		dateFromDateBox.setValue(DateUtil.convertTimestampToUtilDate(invoicePromo.getInsertDt()));
	
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		promoList.add(new Listitem("-", ""));
		promoList.add(new Listitem("Promo 1","Promo 1"));
		promoList.add(new Listitem("Promo 2","Promo 2"));
		promoList.add(new Listitem("Promo 3","Promo 3"));
	}
	
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{

		Listbox overdueList = (Listbox)this.getFellow("checkOverdue");
		for(Object checkOverdue : overdueList.getItems()){
			if(((Listitem)checkOverdue).getValue().equals(invoicePromo.getCheckOverdue())){
				((Listitem)checkOverdue).setSelected(true);
			}
		}
		
		Listbox invoiceCorpList = (Listbox)this.getFellow("checkInvoiceCorp");
		for(Object checkInvoiceCorp : invoiceCorpList.getItems()){
			if(((Listitem)checkInvoiceCorp).getValue().equals(invoicePromo.getCheckInvoiceCorp())){
				((Listitem)checkInvoiceCorp).setSelected(true);
				checkInvoiceCorp(((Listitem)checkInvoiceCorp));
				
				if(invoicePromo.getCheckInvoiceCorpValue() != null)
					((Decimalbox)this.getFellow("checkInvoiceCorpValue")).setValue(invoicePromo.getCheckInvoiceCorpValue());
			}
		}
	
		Listbox invoicePersList = (Listbox)this.getFellow("checkInvoicePers");
		for(Object checkInvoicePers : invoicePersList.getItems()){
			if(((Listitem)checkInvoicePers).getValue().equals(invoicePromo.getCheckInvoicePers())){
				((Listitem)checkInvoicePers).setSelected(true);
				checkInvoicePers(((Listitem)checkInvoicePers));
				
				if(invoicePromo.getCheckInvoiceCorpValue() != null)
					((Decimalbox)this.getFellow("checkInvoicePersValue")).setValue(invoicePromo.getCheckInvoicePersValue());
			}
		}
		
		Listbox invoicePtList = (Listbox)this.getFellow("checkPt");
		for(Object checkInvoicePt : invoicePtList.getItems()){
			if(((Listitem)checkInvoicePt).getValue().equals(invoicePromo.getCheckPt())){
				((Listitem)checkInvoicePt).setSelected(true);
				checkInvoicePt(((Listitem)checkInvoicePt));
				
				if(invoicePromo.getCheckInvoiceCorpValue() != null)
					((Decimalbox)this.getFellow("checkPtValue")).setValue(invoicePromo.getCheckPtValue());
			}
		}
		Listbox checkMainBillList = (Listbox)this.getFellow("checkMainBillingContact");
		for(Object checkMainBill : checkMainBillList.getItems()){
			if(((Listitem)checkMainBill).getValue().equals(invoicePromo.getCheckMainBillContact())){
				((Listitem)checkMainBill).setSelected(true);
			}
		}
		Listbox checkRaceList = (Listbox)this.getFellow("checkRace");
		for(Object checkRace : checkRaceList.getItems()){
			if(((Listitem)checkRace).getValue().equals(invoicePromo.getCheckRace())){
				((Listitem)checkRace).setSelected(true);
			}
		}
		
		Listbox checkPromoList = (Listbox)this.getFellow("checkPromo");
		for(Object checkPromo : checkPromoList.getItems()){
			if(((Listitem)checkPromo).getValue().equals("Promo 1")){
				if(invoicePromo.getPromo1() != null && invoicePromo.getPromo1().equals("Y"))
					((Listitem)checkPromo).setSelected(true);
			}
			else if(((Listitem)checkPromo).getValue().equals("Promo 2")){
				if(invoicePromo.getPromo2() != null && invoicePromo.getPromo2().equals("Y"))
					((Listitem)checkPromo).setSelected(true);
			}
			else if(((Listitem)checkPromo).getValue().equals("Promo 3")){
				if(invoicePromo.getPromo3() != null && invoicePromo.getPromo3().equals("Y"))
					((Listitem)checkPromo).setSelected(true);
			}
		}
		
		Listbox invoiceAltContactList = (Listbox)this.getFellow("checkAltContact");
		for(Object checkAltContact : invoiceAltContactList.getItems()){
			if(((Listitem)checkAltContact).getValue().equals(invoicePromo.getCheckAltContact())){
				((Listitem)checkAltContact).setSelected(true);
			}
		}
		if(invoicePromo.getCheckQuantityValue() != null)
			((Decimalbox)this.getFellow("checkQuantityValue")).setValue(invoicePromo.getCheckQuantityValue());

		List<String> listRace = new ArrayList<String>();
		if(invoicePromo.getCheckRaceValue() != null)
		{
			Iterable<String> raceString = Splitter.on(';').split(invoicePromo.getCheckRaceValue());
			for(String s: raceString){
				listRace.add(s.trim());
			}
		}
		
		Listbox races = (Listbox)this.getFellow("raceList");

		
		if(invoicePromo.getCheckRace().equals("Y"))
			races.setDisabled(false);
		else
			races.setDisabled(true);
		
		Map<String, String> raceMap = ConfigurableConstants.getRace();
		for(Map.Entry<String, String> entry : raceMap.entrySet()){
			Listitem item = new Listitem(entry.getValue(), entry.getKey());
			races.appendChild(item);
			
			if(listRace.contains(entry.getKey()))
				item.setSelected(true);
		}
		
		if(invoicePromo.getFileData() != null) {
			
			try{
			Blob blob = invoicePromo.getFileData();
			InputStream is = blob.getBinaryStream();
			
			File file = File.createTempFile("IBS", "");
			FileOutputStream outputStream = new FileOutputStream(file);
			
			int b = 0;
			while ((b = is.read()) != -1)
			{
				outputStream.write(b); 
			}
			
			logger.info("Temp File: " + file.getAbsolutePath());
			
			this.appendFileAttachmentToListbox(new FileAttachment(invoicePromo.getFileName(), file), blob.getBytes(1, (int)blob.length()));
			
			} catch (Exception e) {}
		}
	}
	
	public void save() throws InterruptedException{
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
					Session session = HibernateUtil.getSessionFactory().openSession();
					session.beginTransaction();
					Blob image = Hibernate.getLobCreator(session).createBlob(entry.getKey());
					invoicePromo.setFileData(image);
					break;
				}
			}
			else {
				invoicePromo.setFileData(null);
				invoicePromo.setFileName(null);
			}
			
			boolean checkPromoNumber = false;
			if(!promo.trim().equals("")) {
			
				checkPromoNumber = this.businessHelper.getAdminBusiness().checkPromoNumber(promo, invoicePromo.getInsertDt(), invoicePromo.getInvoicePromoId());
				if(checkPromoNumber) {
					Messagebox.show("Error: There is a existing Promo 1 2 or 3 on that Date.",
							"Error", Messagebox.OK, Messagebox.ERROR);
				}
			}
			if(!checkPromoNumber) {
				this.businessHelper.getGenericBusiness().update(invoicePromo, getUserLoginIdAndDomain());
				
				//Show result
				Messagebox.show("Invoice Promo updated.", "Edit Invoice Promo", Messagebox.OK, Messagebox.INFORMATION);				
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
		if(selectedItem != null)
		{
			if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
				toPers = true;
			((Decimalbox)this.getFellow("checkInvoicePersValue")).setDisabled(toPers);
		}
	}

	public void checkInvoiceCorp(Listitem selectedItem){
		logger.info("checkInvoiceCorp(Listitem selectedItem)");
		boolean toCorp = false;
		if(selectedItem != null)
		{
			if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
				toCorp = true;
			((Decimalbox)this.getFellow("checkInvoiceCorpValue")).setDisabled(toCorp);
		}
	}
	public void checkInvoicePt(Listitem selectedItem){
		logger.info("checkInvoicePt(Listitem selectedItem)");
		boolean toPt = false;
		if(selectedItem != null)
		{
			if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
				toPt = true;
			((Decimalbox)this.getFellow("checkPtValue")).setDisabled(toPt);
		}
	}
//	public void checkAltContact(Listitem selectedItem){
//		logger.info("checkAltContact(Listitem selectedItem)");
//		boolean toAlt = false;
//		if(selectedItem != null)
//		{
//			if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
//				toAlt = true;
//			((Decimalbox)this.getFellow("checkQuantityValue")).setDisabled(toAlt);
//		}
//	}
	public void checkInvoiceRace(Listitem selectedItem){
		logger.info("checkRace(Listitem selectedItem)");
		boolean toRace = false;
		if(selectedItem != null)
		{
			if(selectedItem.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO))
				toRace = true;
			((Listbox)this.getFellow("raceList")).setDisabled(toRace);
			
			if(toRace)
				((Listbox)this.getFellow("raceList")).clearSelection();
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		
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
	public List<Listitem> getBooleanList() {
		return cloneList(booleanList);
	}
	public void setBooleanList(List<Listitem> booleanList) {
		this.booleanList = booleanList;
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
				.parseContent("editInvoicePromoWindow.deleteRow(self.getParent().getParent())");
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
