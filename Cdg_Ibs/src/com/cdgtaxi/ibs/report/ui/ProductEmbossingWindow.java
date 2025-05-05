package com.cdgtaxi.ibs.report.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;

public class ProductEmbossingWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductEmbossingWindow.class);
	private List<Listitem> productTypes = ComponentUtil.convertToListitems(this.businessHelper.getReportBusiness().getIssuableProductTypes(), true);
	private List<Listitem> sortBy = ComponentUtil.convertToListitems(NonConfigurableConstants.PRODUCT_EMBOSS_SORT_BY, true);
	public ProductEmbossingWindow(){
		logger.info("ProductEmbossingWindow()");
	}
	@Override
	public void refresh() throws InterruptedException {
		Listbox productTypes = ((Listbox)this.getFellow("productTypeList"));
		if(!productTypes.getItems().isEmpty()){
			productTypes.setSelectedIndex(0);
		}
		((Checkbox)this.getFellow("reprint")).setChecked(false);
		((Decimalbox)this.getFellow("cardNoStart")).setValue(null);
		((Decimalbox)this.getFellow("cardNoEnd")).setValue(null);
		((Datebox)this.getFellow("issueStart")).setValue(null);
		((Datebox)this.getFellow("issueEnd")).setValue(null);
		((Datebox)this.getFellow("replaceStart")).setValue(null);
		((Datebox)this.getFellow("replaceEnd")).setValue(null);
		((Datebox)this.getFellow("renewStart")).setValue(null);
		((Datebox)this.getFellow("renewEnd")).setValue(null);
		Listbox sort = ((Listbox)this.getFellow("sortByList"));
		if(!sort.getItems().isEmpty()){
			sort.setSelectedIndex(0);
		}
	}
	public List<Listitem> getProductTypes(){
		return this.productTypes;
	}
	public List<Listitem> getSortBy(){
		return this.sortBy;
	}
	public void generate() throws InterruptedException{
		Listbox productTypeList = (Listbox)this.getFellow("productTypeList");
		String productTypeId = (String)productTypeList.getSelectedItem().getValue();
		String cardNoStart = ((Decimalbox)this.getFellow("cardNoStart")).getText();
		String cardNoEnd = ((Decimalbox)this.getFellow("cardNoEnd")).getText();
		Date issuanceStart = ((Datebox)this.getFellow("issueStart")).getValue();
		Date issuanceEnd = ((Datebox)this.getFellow("issueEnd")).getValue();
		Date replacementStart = ((Datebox)this.getFellow("replaceStart")).getValue();
		Date replacementEnd = ((Datebox)this.getFellow("replaceEnd")).getValue();
		Date renewStart = ((Datebox)this.getFellow("renewStart")).getValue();
		Date renewEnd = ((Datebox)this.getFellow("renewEnd")).getValue();
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		String sortBy = (String)sortByList.getSelectedItem().getValue();
		boolean reprint = ((Checkbox)this.getFellow("reprint")).isChecked();
//		if(reprint && (cardNoStart==null || cardNoStart.length()==0) && issuanceStart==null && replacementStart==null && renewStart==null){
		if((cardNoStart==null || cardNoStart.trim().length()==0) && issuanceStart==null && replacementStart==null && renewStart==null){
			Messagebox.show("Please input card no start, issuance start date, replacement start date or renewal start date!", "Generate Embossing", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
//		if(!reprint && issuanceStart==null && replacementStart==null && renewStart==null){
//			Messagebox.show("Please enter issuance/replacement/renewal date", "Generate Embossing", Messagebox.OK, Messagebox.INFORMATION);
//			return;
//		}
		displayProcessing();

		PmtbProductType pmtbProductType =  this.businessHelper.getProductTypeBusiness().getProductType(productTypeId);
		try
		{
			if(NonConfigurableConstants.BOOLEAN_YES.equals(pmtbProductType.getContactless())) {
				generateContactlessEmbossFile(productTypeId, cardNoStart, cardNoEnd, issuanceStart, issuanceEnd, replacementStart, replacementEnd, renewStart, renewEnd, sortBy, reprint);
			} else {
				generateEmbossFile(productTypeId, cardNoStart, cardNoEnd, issuanceStart, issuanceEnd, replacementStart, replacementEnd, renewStart, renewEnd, sortBy, reprint);
			}
		}
		catch (Exception e ) {
			if(("EMBOSSINGPRODUCTEXP").equals(e.getMessage())) {
				if(this.businessHelper.getReportBusiness().hasEmbossingProducts(productTypeId, cardNoStart, cardNoEnd, issuanceStart, issuanceEnd, replacementStart, replacementEnd, renewStart, renewEnd)){
					Messagebox.show("Card Embossing is previously generated, tick \"Reprint\" checkbox to generate again", "Generate Embossing", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Messagebox.show("No Product Found", "Generate Embossing", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			else {
				e.printStackTrace();
				Messagebox.show(e.getMessage(), "Generate Embossing", Messagebox.OK, Messagebox.INFORMATION);
			}
		}

	}


	private void generateEmbossFile(String productTypeId, String cardNoStart, String cardNoEnd, Date issuanceStart, Date issuanceEnd, Date replacementStart, Date replacementEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint) throws Exception
	{
		List<Map<String, String>> productMaps = this.businessHelper.getReportBusiness().getEmbossingProducts(productTypeId, cardNoStart, cardNoEnd, issuanceStart, issuanceEnd, replacementStart, replacementEnd, renewStart, renewEnd, sortBy, isReprint, getUserLoginIdAndDomain());
		if(!productMaps.isEmpty()){
			StringBuffer data = new StringBuffer();
			if(productMaps.get(0).get("BIN")!=null && productMaps.get(0).get("BIN").equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				data.append("TRACK 1,TRACK 2,CARD NO,CARD NAME,CARD NAME DIVISION,CARD NAME DEPT,FIXED VALUE,EXPIRY DATE");
				data.append("\r\n");
				//data.append(System.getProperty("line.separator"));
			}else{
//				data.append("TRACK 1,CARD NAME,CARD NO,EXPIRY MONTH,TRACK 2,ACCOUNT NAME");
				data.append("BINRANGE,SUBBINRANGE,CARDNO,CHKDIGIT,CARDNAME,YY,MM,ACCOUNTNAME,TRACK2,TRACK1,EXPIRYDATE");
				data.append("\r\n");
			}
			for(Map<String, String> productMap : productMaps){
				// iso cards
				if(productMap.get("BIN")!=null && productMap.get("BIN").equals(NonConfigurableConstants.BOOLEAN_YN_NO)){
					String cardNo = productMap.get("cardNo").toString();
					
					if(cardNo.length() < 16)
						logger.info("CardNo Length too short. "+cardNo);
					
					//BinRange , CardNo 0 to 6
					data.append("\"" + cardNo.substring(0, 6) + "\"");
					data.append(",");
					
					//SubBinRange , CardNo 6 to 10
					data.append("\"" + cardNo.substring(6, 10) + "\"");
					data.append(",");
					
					//SubBinRange , CardNo 10 to 15
					data.append("\"" + cardNo.substring(10, 15) + "\"");
					data.append(",");
					
					//Chkdigit , CardNo 15 to 16
					data.append("\"" + cardNo.substring(15, 16) + "\"");
					data.append(",");
					
					//Cardholder Name
					if(productMap.get("cardHolderName")!=null){
						data.append("\"" + productMap.get("cardHolderName") + "\"");
						data.append(",");
					}
					
					String expiryDate = productMap.get("expiryMth").toString();

					if(expiryDate.length() < 4)
						logger.info("expiryDate Length too short. "+cardNo);

					//Expiry Date YY
					data.append("\"" + expiryDate.substring(0, 2) + "\"");
					data.append(",");
					
					//Expiry Date MM
					data.append("\"" + expiryDate.substring(2, 4) + "\"");
					data.append(",");
					
					//AccountName
					if (productMap.get("acctNameOnCard") != null && !"".equals(productMap.get("acctNameOnCard")))
					{
						data.append("\"" + productMap.get("acctNameOnCard").toUpperCase() + "\"");
						data.append(",");
					}
					else
					{
						data.append(",");
					}

					//Track 2
					data.append("\"101000000000000\"");
					data.append(",");

					//Track1
					data.append("\"" + cardNo + "\"");
					data.append(",");
					
					//ExpiryDate
					data.append("\"" + expiryDate + "\"");
					data.append(",");
					
					logger.info("data = " + data);
					data.append("\n");
					//data.append(System.getProperty("line.separator"));
				}
                else{
					data.append("B");
					data.append(productMap.get("cardNo"));
					data.append("^");
					// non iso cards
                    if (productMap.get("cardNo").length() < 16) {
                        for (int i = 0; i < 16 - productMap.get("cardNo").length(); i++) {
                            data.insert(data.length() - 1, " ");
                        }
                    }
                    StringBuffer buffer = new StringBuffer();
                    if (productMap.get("acctNameOnCard") != null && !"".equals(productMap.get("acctNameOnCard"))) {
                        buffer = new StringBuffer(productMap.get("acctNameOnCard"));
                        while (buffer.length() < 25) {
                            buffer.append(" ");
                        }
                    } else {
                        buffer = new StringBuffer();
                        while (buffer.length() < 25) {
                            buffer.append(" ");
                        }
                    }
                    data.append(buffer);
                    data.append("^,");
                    data.append(productMap.get("cardNo"));

                    data.append("=");
                    data.append(productMap.get("expiryMth") != null && productMap.get("expiryMth").length() != 0 ? productMap.get("expiryMth") : "0000");
                    data.append("101");
                    if (productMap.get("creditLimit") != null) {
                        data.append(productMap.get("creditLimit"));
                    } else {
                        data.append("0000");
                    }
                    data.append("00000000");
                    data.append(",");
                    data.append(productMap.get("otuLuhnCheckCardNo") != null ? productMap.get("otuLuhnCheckCardNo") : productMap.get("cardNo"));
                    data.append(",");
                    data.append(productMap.get("cardName1"));
                    data.append(",");
                    if (productMap.get("cardName2") != null) {
                        data.append(productMap.get("cardName2"));
                    }
                    data.append(",");
                    if (productMap.get("cardName3") != null) {
                        data.append(productMap.get("cardName3"));
                    }
                    data.append(",");
                    if (productMap.get("fixedValue") != null) {
                        StringBuffer creditLimit = new StringBuffer(productMap.get("fixedValue"));
                        while (creditLimit.charAt(0) == '0') {
                            creditLimit.deleteCharAt(0);
                        }
                        data.append("$" + creditLimit.toString());
                    }
                    data.append(",");
                    if (productMap.get("expiryDate") != null) {
                        data.append(productMap.get("expiryDate"));
                    }
                    data.append("\r\n");
                    //data.append(System.getProperty("line.separator"));
                }
			}
			if(productMaps.get(0).get("BIN")!=null && productMaps.get(0).get("BIN").equals(NonConfigurableConstants.BOOLEAN_YN_NO)){
				//Filedownload.save(data.toString(), "txt", "embossing_"+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.EMBOSS_FILE_DATE_FORMAT)+".txt");
				Filedownload.save(data.toString(), "txt", "CARD.csv");
			}else{
				//Filedownload.save(data.toString(), "txt", "embossing_"+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.EMBOSS_FILE_DATE_FORMAT)+".csv");
				Filedownload.save(data.toString(), "txt", "evoucher.csv");
			}
		}
		else {
			throw new Exception("EMBOSSINGPRODUCTEXP");
		}
	}


	private void generateContactlessEmbossFile(String productTypeId, String cardNoStart, String cardNoEnd, Date issuanceStart, Date issuanceEnd, Date replacementStart, Date replacementEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint) throws Exception
	{
		List<Map<String, String>> productMaps = this.businessHelper.getReportBusiness().getContactlessEmbossingProducts(productTypeId, cardNoStart, cardNoEnd, issuanceStart, issuanceEnd, replacementStart, replacementEnd, renewStart, renewEnd, sortBy, isReprint, getUserLoginIdAndDomain());
		if(!productMaps.isEmpty()){
			StringBuffer data = new StringBuffer();

			data.append("TRACK 1,TRACK 2,CARD NO,CARD NAME,CARD NAME DIVISION,CARD NAME DEPT,FIXED VALUE,EXPIRY DATE" +
				",EXPIRY DATE (PERSO),FORCE ONLINE STATUS,OFFLINE COUNT LIMIT,OFFLINE AMOUNT LIMIT (ACCUMULATIVE),OFFLINE AMOUNT LIMIT (PER TXN)");
			data.append("\r\n");

			for(Map<String, String> productMap : productMaps){
				data.append(StringUtils.defaultString(productMap.get("track1")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("track2")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("cardNo")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("cardName")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("cardNameDivision")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("cardNameDepartment")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("fixedValue")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("expiryDate")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("expiryDatePerso")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("forceOnlineStatus")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("offlineCountLimit")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("offlineAmountLimitAccumulative")));
				data.append(",");
				data.append(StringUtils.defaultString(productMap.get("offlineAmountLimitPerTxn")));
				data.append("\r\n");
			}
			Filedownload.save(data.toString(), "csv", "contactless.csv");
		}
		else {
			throw new Exception("EMBOSSINGPRODUCTEXP");
		}



	}




}