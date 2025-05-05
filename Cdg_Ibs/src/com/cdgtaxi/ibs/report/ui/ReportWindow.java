package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Combobox;
import com.cdgtaxi.ibs.web.constraint.ReportAmountConstraint;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.ers2.client.Parameter;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public abstract class ReportWindow extends CommonWindow{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReportWindow.class);
	
	private String report;
	private String reportCategory;
	private Long reportRsrcId;
	private ERSClient client;
	private Parameter[] parameters;
	
	//Report Server Properties
	private static String ip;
	private static String port;
	private static String username;
	private static String password;
	private static String repository;
	

	public String getReport(){
		return this.report;
	}
	public String getReportCategory(){
		return this.reportCategory;
	}
	public ERSClient getClient(){
		return this.client;
	}
	public String getRepository(){
		return repository;
	}
	public ReportWindow(String report, String reportCategory) throws IOException{
		//retrieve properties bean
		Map elixirReportProperties = (Map)SpringUtil.getBean("elixirReportProperties");
		//retrieve properties value
		ip = (String)elixirReportProperties.get("report.server.ip");
		port = (String)elixirReportProperties.get("report.server.port");
		username = (String)elixirReportProperties.get("report.server.username");
		password = (String)elixirReportProperties.get("report.server.password");
		repository = (String)elixirReportProperties.get("report.server.repository.location");
		
//		report = this.getHttpServletRequest().getParameter("report");
//		Map map = Executions.getCurrent().getArg();
//		report = (String)map.get("report");
//		reportCategory = (String)map.get("reportCategory");
		this.report = report;
		this.reportCategory = reportCategory;
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
		
		client = new ERSClient(ip, Integer.parseInt(port), username, password);
		client.setSecure(false);
//		this.setTitle(report+" Report");
		this.setTitle(report);
		
		System.out.println("ip"+ip);
		System.out.println("port"+port);
		System.out.println("username"+username);
		System.out.println("password"+password);
		System.out.println("repository"+repository);
		
	}
	
	public void populateReportFormatList(Listbox listbox) throws NetException{
		//populate the running no. for the label
		if(parameters==null){
			logger.info(repository+"/"+reportCategory+"/"+report+"/"+report+".rml");
			System.out.println(repository+"/"+reportCategory+"/"+report+"/"+report+".rml");
			parameters = client.getParameters(repository+"/"+reportCategory+"/"+report+"/"+report+".rml");
		}
		int totalNoOfParams = parameters.length;
		// fixed for printedBy issue
		boolean printedBy = false;
		for(Parameter param : parameters){
			if(param.getType().startsWith("acctsearch")){
				if(param.getDefaultValue().startsWith("custId")){
					totalNoOfParams-=1;
				}
				if(param.getDefaultValue().startsWith("div")){
					totalNoOfParams-=2;
				}
				if(param.getDefaultValue().startsWith("shipping")){
					totalNoOfParams-=1;
				}
			}else if(param.getName().equals("printedBy")){
				printedBy = true;
			}
			logger.info("name = " + param.getName());
		}
		if(!printedBy){
			totalNoOfParams++;
		}
		Label reportFormatLabel = (Label)this.getFellow("reportFormatLabel");
		reportFormatLabel.setValue(totalNoOfParams+". "+reportFormatLabel.getValue());
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness().getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		for(MstbReportFormatMap formatMap : reportFormatMapList){
			Listitem listItem = new Listitem(formatMap.getReportFormat(), Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if(firstItem){
				listItem.setSelected(true);
				firstItem = false;
			}
			listbox.appendChild(listItem);
		}
	}
	
	protected Datebox populateDatebox(Row row, Label label, String id, String defaultValue) throws NetException{
		Datebox datebox = new com.cdgtaxi.ibs.web.component.Datebox();
		datebox.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		datebox.setId(id);
		datebox.setWidth("190px");
		String[] values = defaultValue.split(":");
		for(String value : values){
			if(value.equals(Constants.REQUIRED)){
				label.setSclass(label.getSclass()+" required");
				datebox.setConstraint(new RequiredConstraint());
			}else if(value.equalsIgnoreCase("MMMYY")){
				datebox.setFormat("MMM-yy");
			}
		}
		row.appendChild(datebox);
		return datebox;
	}
	protected Decimalbox populateDecimalbox(Row row, Label label, String id, String defaultValue) throws NetException{
		Decimalbox decimalbox = new Decimalbox();
		decimalbox.setId(id);
		decimalbox.setWidth("190px");
		String[] values = defaultValue.split(":");
		for(String value : values){
			if(value.equals(Constants.REQUIRED)){
				label.setSclass(label.getSclass()+" required");
				decimalbox.setConstraint(new RequiredConstraint());
			}else if(value.indexOf(",")!=-1){
				String[] places = value.split(",");
				int whole = Integer.parseInt(places[0]);
				int decimal = Integer.parseInt(places[1]);
				decimalbox.setMaxlength(whole+decimal+2);
				decimalbox.setConstraint(new ReportAmountConstraint(whole, decimal));
				decimalbox.setFormat("#######,##0.00");
			}
		}
		row.appendChild(decimalbox);
		return decimalbox;
	}
	protected Intbox populateIntbox(Row row, Label label, String id, String defaultValue) throws NetException{
		Intbox intbox = new Intbox();
		intbox.setId(id);
		intbox.setWidth("190px");
		intbox.setMaxlength(8);
		intbox.setFormat("#,##0");
		String[] values = defaultValue.split(":");
		for(String value : values){
			if(value.equals(Constants.REQUIRED)){
				label.setSclass(label.getSclass()+" required");
				intbox.setConstraint(new RequiredConstraint());
			}
		}
		row.appendChild(intbox);
		return intbox;
	}
	protected Intbox populateBinRangeIntbox(Row row, Label label, String id, String defaultValue) throws NetException{
		Intbox intbox = new Intbox();
		intbox.setId(id);
		intbox.setWidth("190px");
		intbox.setMaxlength(6);
		intbox.setFormat("000000");
		String[] values = defaultValue.split(":");
		for(String value : values){
			if(value.equals(Constants.REQUIRED)){
				label.setSclass(label.getSclass()+" required");
				intbox.setConstraint(new RequiredConstraint());
			}
		}
		row.appendChild(intbox);
		return intbox;
	}
	protected Listbox populateListbox(Row row, Label label, String id, String defaultValue) throws NetException{
		Listbox listbox = new Listbox();
		listbox.setId(id);
		listbox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		listbox.setMultiple(false);
		if(defaultValue!=null && !defaultValue.equals("")){
			String[] values = defaultValue.split(":");
			String type = values[0];
			String code = values[1];
			String required = "";
			
			logger.debug("type: " + type + " code: " + code);
			
			if(values.length>2){
				required = values[2];
			}
			if(required.equals(Constants.REQUIRED)){
				label.setSclass(label.getSclass()+" required");
			}else{
				listbox.appendChild(ComponentUtil.createNotRequiredListItem());
			}
			if(type.equals(Constants.CONFIGURABLE_CONSTANT_TYPE)){
				logger.info("Loading Configurable Constants - "+code);
				Map map = ConfigurableConstants.getMasters(code);
				Set<String> keys = map.keySet();
				for(String key : keys){
					Listitem item = new Listitem();
					item.setLabel((String)map.get(key));
					item.setValue(key);
					listbox.appendChild(item);
				}
			}else if(type.equals(Constants.NON_CONFIGURABLE_CONSTANT_TYPE)){
				logger.info("Loading Non Configurable Constants - "+code);
				Map map = NonConfigurableConstants.REPORT_MAP.get(code);
				Set<String> keys = map.keySet();
				for(String key : keys){
					Listitem item = new Listitem();
					item.setLabel((String)map.get(key));
					item.setValue(key);
					listbox.appendChild(item);
				}
			}else if(type.equals("MASTER")){
				if(code.equals("EM")){// entity
					Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
					for(Integer entityNo : entities.keySet()){
						listbox.appendChild(new Listitem(entities.get(entityNo),entityNo.toString()));
					}
				}else if(code.equals("PT")){
					Map<String, String> productTypes = this.businessHelper.getReportBusiness().getAllProductTypes();
					Set<Listitem> listitems = new TreeSet<Listitem>(new Comparator<Listitem>(){
						public int compare(Listitem o1, Listitem o2) {
							return o1.getLabel().compareTo(o2.getLabel());
						}
					});
					for(String productTypeId : productTypes.keySet()){
						listitems.add(new Listitem(productTypes.get(productTypeId), productTypeId));
					}
					listbox.getChildren().addAll(listitems);
				}else if(code.equals("PT2")){// for issuable and non OTU products
					Map<String, String> productTypes = this.businessHelper.getReportBusiness().getIssuableNonOTUProductTypes();
					Set<Listitem> listitems = new TreeSet<Listitem>(new Comparator<Listitem>(){
						public int compare(Listitem o1, Listitem o2) {
							return o1.getLabel().compareTo(o2.getLabel());
						}
					});
					for(String productTypeId : productTypes.keySet()){
						listitems.add(new Listitem(productTypes.get(productTypeId), productTypeId));
					}
					listbox.getChildren().addAll(listitems);
				}else if(code.equals("PT3")){// for issuable
					Map<String, String> productTypes = this.businessHelper.getReportBusiness().getIssuableProductTypes();
					Set<Listitem> listitems = new TreeSet<Listitem>(new Comparator<Listitem>(){
						public int compare(Listitem o1, Listitem o2) {
							return o1.getLabel().compareTo(o2.getLabel());
						}
					});
					for(String productTypeId : productTypes.keySet()){
						listitems.add(new Listitem(productTypes.get(productTypeId), productTypeId));
					}
					listbox.getChildren().addAll(listitems);
				}else if(code.equals("PT4")){ // for prepaid
					Map<String, String> productTypes = this.businessHelper.getReportBusiness().getPrepaidProductTypes();
					Set<Listitem> listitems = new TreeSet<Listitem>(new Comparator<Listitem>(){
						public int compare(Listitem o1, Listitem o2) {
							return o1.getLabel().compareTo(o2.getLabel());
						}
					});
					for(String productTypeId : productTypes.keySet()){
						listitems.add(new Listitem(productTypes.get(productTypeId), productTypeId));
					}
					listbox.getChildren().addAll(listitems);
				}else if(code.equals("PT5")){ // for Only list product types with bin range starting with 60108965
					Map<String, String> productTypes = this.businessHelper.getReportBusiness().getBinRangeProductTypes();
					Set<Listitem> listitems = new TreeSet<Listitem>(new Comparator<Listitem>(){
						public int compare(Listitem o1, Listitem o2) {
							return o1.getLabel().compareTo(o2.getLabel());
						}
					});
					for(String productTypeId : productTypes.keySet()){
						listitems.add(new Listitem(productTypes.get(productTypeId), productTypeId));
					}
					listbox.getChildren().addAll(listitems);
				}else if(code.equals("AT")){// for account type
					Map<Integer, String> acctTypes = this.businessHelper.getReportBusiness().getAllAccountTypes();
					for(Integer acctTypeNo : acctTypes.keySet()){
						listbox.appendChild(new Listitem(acctTypes.get(acctTypeNo), String.valueOf(acctTypeNo)));
					}
				}else if(code.equals("SP")){
					//This Map is for SalesPerson
					Map<Integer, String> salesPersons = MasterSetup.getSalespersonManager().getAllMasters();
					for(Integer salesPersonId : salesPersons.keySet()){
						listbox.appendChild(new Listitem(salesPersons.get(salesPersonId), salesPersonId.toString()));
					}
				}else if(code.equals("ACQ")){
					//This Map is for Acquirer
					Map<Integer, String> acquirers = this.businessHelper.getReportBusiness().getAllAcquirers();
					for(Integer acquirerId : acquirers.keySet()){
						listbox.appendChild(new Listitem(acquirers.get(acquirerId), acquirerId.toString()));
					}
				}else if(code.equals("IT")){
					//This Map is for Item types
					Map<Integer, String> itemTypes = this.businessHelper.getReportBusiness().getAllItemTypes();
					for(Integer itemTypeNo : itemTypes.keySet()){
						listbox.appendChild(new Listitem(itemTypes.get(itemTypeNo), itemTypeNo.toString()));
					}
				}
				else if(code.equals("CT")){
					Map<Integer, String> creditTerms = this.businessHelper.getReportBusiness().getAllCreditTrems();
					for(Integer creditTerm : creditTerms.keySet()){
						listbox.appendChild(new Listitem(creditTerms.get(creditTerm), creditTerm.toString()));
					}
				} else if(code.equals("PCP")){
					Map<String, String> promotions = this.businessHelper.getReportBusiness().getAllPromotionCashPlusCodes();
					for(String promoCode : promotions.keySet()){
						listbox.appendChild(new Listitem(promotions.get(promoCode), promoCode));
					}
				}
				/**
				 * Added by CDG
				 */
				else if(code.equals("RL")){  //added by vani 28.10.10 for UAM report
					//This Map is for roles						
					Map<Long, String> roles = this.businessHelper.getReportBusiness().getAllRoles();
					
					for(Long roleId : roles.keySet()){
						listbox.appendChild(new Listitem(roles.get(roleId),roleId.toString()));
					}
				}
				else if(code.equals("RP")){  //added by vani 28.10.10 for UAM report				
					Set<String> redeemPointSet = this.businessHelper.getReportBusiness().getAllRedeemPoint();
					for(String redeemPoint: redeemPointSet){
						listbox.appendChild(new Listitem(redeemPoint,redeemPoint));
					}
				}
			}
			((Listitem)listbox.getFirstChild()).setSelected(true);
		}
		row.appendChild(listbox);
		return listbox;
	}
	protected Component populateAccountSearch(Row row, Label label, String id, String defaultValue) throws NetException{
		if(defaultValue!=null && defaultValue.length()!=0){
			if(defaultValue.startsWith("custId")){
				Intbox intbox = new Intbox();
				intbox.setId(id);
				intbox.setMaxlength(6);
				intbox.setTooltiptext("Minimum search criteria is 3 characters");
				intbox.setWidth("300px");
				ZScript script = ZScript.parseContent("reportWindow.searchAccountByAccountNo(event.value, self)");
				script.setLanguage("java");
				EventHandler pdEvent = new EventHandler(script, ConditionImpl.getInstance(null, null));
				intbox.addEventHandler("onChange", pdEvent);
				String[] values = defaultValue.split(":");
				for(String value : values){
					if(value.equals(Constants.REQUIRED)){
						label.setSclass(label.getSclass()+" required");
						intbox.setConstraint(new RequiredConstraint());
					}
				}
				row.appendChild(intbox);
				return intbox;
			}else if(defaultValue.startsWith("custName")){
				Combobox combo = new Combobox();
				combo.setId(id);
				combo.setRows(1);
				combo.setWidth("300px");
				combo.setStyle("text-transform:uppercase");
				combo.setAutodrop(true);
				combo.setAutocomplete(false);
				combo.setTooltiptext("Minimum search criteria is 3 characters");
				ZScript script = ZScript.parseContent("reportWindow.searchAccountByAccountName(event.value, self)");
				script.setLanguage("java");
				EventHandler pdEvent = new EventHandler(script, ConditionImpl.getInstance(null, null));
				combo.addEventHandler("onChanging", pdEvent);
				script = ZScript.parseContent("reportWindow.onSelectAccountName(self.getParent().getPreviousSibling().getChildren().get(1))");
				script.setLanguage("java");
				pdEvent = new EventHandler(script, ConditionImpl.getInstance(null, null));
				combo.addEventHandler("onSelect", pdEvent);
				row.appendChild(combo);
				return combo;
			}else if(defaultValue.startsWith("div")){
				Listbox listbox = new Listbox();
				listbox.setId(id);
				listbox.setMold("select");
				listbox.setMultiple(false);
				ZScript script = ZScript.parseContent("reportWindow.onSelectDivision(self)");
				script.setLanguage("java");
				EventHandler pdEvent = new EventHandler(script, ConditionImpl.getInstance(null, null));
				listbox.addEventHandler("onSelect", pdEvent);
				row.setVisible(false);
				row.appendChild(listbox);
				return listbox;
			}else if(defaultValue.startsWith("dept")){
				Listbox listbox = new Listbox();
				listbox.setId(id);
				listbox.setMold("select");
				listbox.setMultiple(false);
				ZScript script = ZScript.parseContent("reportWindow.onSelectDepartment(self)");
				script.setLanguage("java");
				EventHandler pdEvent = new EventHandler(script, ConditionImpl.getInstance(null, null));
				listbox.addEventHandler("onSelect", pdEvent);
				row.setVisible(false);
				row.appendChild(listbox);
				return listbox;
			}else if(defaultValue.startsWith("shipping")){
				Listbox listbox = new Listbox();
				listbox.setId(id);
				listbox.setMold("select");
				listbox.setWidth("300px");
				listbox.setMultiple(false);
				row.setVisible(false);
				row.appendChild(listbox);
				return listbox;
			}else if(defaultValue.startsWith("cardNo")){
				Textbox textbox= new Textbox();
				textbox.setId(id);
				textbox.setMaxlength(20);
				textbox.setTooltiptext("Search criteria is 16 characters");
				textbox.setWidth("300px");
				row.appendChild(textbox);
				return textbox;
				/*Intbox intbox = new Intbox();
				intbox.setId(id);
				intbox.setMaxlength(20);
				intbox.setTooltiptext("Search criteria is 16 characters");
				intbox.setWidth("300px");
				row.appendChild(intbox);
				return intbox;*/
			}
		}
		return null;
	}
	protected CapsTextbox populateTextbox(Row row, Label label, String id, String defaultValue) throws NetException{
		CapsTextbox textbox = new CapsTextbox();
		textbox.setId(id);
		textbox.setStyle("text-transform: uppercase");
		String[] values = defaultValue.split(":");

		for(String value : values){
			if(value.equals(Constants.REQUIRED)){
				label.setSclass(label.getSclass()+" required");
				textbox.setConstraint(new RequiredConstraint());
			}
		}

		row.appendChild(textbox);
		return textbox;
	}
	public void populateReportParameters(Rows rows) throws NetException
	{
		if(parameters==null){
			parameters = client.getParameters(repository+"/"+reportCategory+"/"+report+"/"+report+".rml");
		}
		System.out.println("parameters :"+parameters);
		for(int i=parameters.length; i!=0; i--){
			Parameter parameter = parameters[i-1];
			if(parameter.getName().equals(Constants.PRINTED_BY)){
				continue;
			}
			Row row = new Row();
			Label label = new Label(parameter.getName());
			label.setSclass("fieldLabel");
			row.appendChild(label);
			if(parameter.getType().startsWith("date")){
				this.populateDatebox(row, label, parameter.getName(), parameter.getDefaultValue());
			}else if(parameter.getType().startsWith("choice")){
				this.populateListbox(row, label, parameter.getName(), parameter.getDefaultValue());
			}else if(parameter.getType().startsWith("acctsearch")){
				this.populateAccountSearch(row, label, parameter.getName(), parameter.getDefaultValue());
			}else if(parameter.getType().startsWith("decimalbox")){
				this.populateDecimalbox(row, label, parameter.getName(), parameter.getDefaultValue());
			}else if(parameter.getType().startsWith("binRangeIntbox")){
				this.populateBinRangeIntbox(row, label, parameter.getName(), parameter.getDefaultValue());
			}else if(parameter.getType().startsWith("intbox")){
				this.populateIntbox(row, label, parameter.getName(), parameter.getDefaultValue());
			}else{//No parameter type, therefore defaulted to a textbox input
				
				System.out.println("value1: " + parameter.getName());
				System.out.println("value: " + parameter.getDefaultValue());
				
				this.populateTextbox(row, label, parameter.getName(), parameter.getDefaultValue());
			}
			rows.insertBefore(row, rows.getFirstChild());
		}
	}
	public Properties generate(Properties reportParamsProperties) throws HttpException, IOException, InterruptedException, NetException {
		logger.info("");
		this.displayProcessing();
		//Retrieve report parameters
		// to be passed from its sub classes
		//Properties reportParamsProperties = new Properties();
		List<String> alternate_required_labels = new ArrayList<String>();
		List<String> alternate_required_values = new ArrayList<String>();
		for(Parameter parameter : parameters){
			if(parameter.getName().equals(Constants.PRINTED_BY)) continue;
			Component component = this.getFellow(parameter.getName());
			if(parameter.getType().startsWith("date")){
				Datebox datebox = (Datebox)component;
				if(datebox.getValue()!=null){
					String name = datebox.getId();
					if(name.indexOf("End") != -1){// end date
						String startName = name.replaceAll("End", "Start");
						String labelNo = name.substring(0, name.indexOf("."));
						String startLabelNo = (Integer.parseInt(labelNo)-1) + "";
						if(startLabelNo.length()!=labelNo.length()){
							startLabelNo = name.substring(0, 1) + startLabelNo;
						}
						startName = (startLabelNo) + startName.substring(labelNo.length());
						Component start = this.getFellowIfAny(startName);
						if(start!=null){
							if(((Datebox)start).getValue()!=null){
								if(((Datebox)start).getValue().after(datebox.getValue())){
									String startNameDisplay = start.getId().substring(start.getId().indexOf(".")+1);
									String endNameDisplay = name.substring(name.indexOf(".")+1);
									Messagebox.show(endNameDisplay+" must be later than "+startNameDisplay, "Report", Messagebox.OK, Messagebox.ERROR);
									return null;
								}
							}
						}
					}
					reportParamsProperties.put(parameter.getName(), DateUtil.convertDateToStr(datebox.getValue(), DateUtil.REPORT_DATE_FORMAT));
				}else{
					reportParamsProperties.put(parameter.getName(), "");
				}
				if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
					alternate_required_labels.add(parameter.getName());
					if(datebox.getValue()!=null){
						alternate_required_values.add(datebox.getValue().toString());
					}
				}
			}
			else if(parameter.getType().startsWith("choice")){
				Listbox listbox = (Listbox)component;
				if(listbox.getSelectedItem().getValue().toString().length()>0){
					reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
				}else{
					reportParamsProperties.put(parameter.getName(), "");
				}
				if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
					alternate_required_labels.add(parameter.getName());
					if(listbox.getSelectedItem().getValue()!=null){
						alternate_required_values.add(listbox.getSelectedItem().getValue().toString());
					}
				}
			}else if(parameter.getType().startsWith("acctsearch")){
				String defaultValue = parameter.getDefaultValue();
				if(defaultValue!=null && defaultValue.length()!=0){
					if(defaultValue.startsWith("custId")){
						Intbox intbox = (Intbox)component;
						if(intbox.getValue()!=null){
							reportParamsProperties.put(parameter.getName(), intbox.getValue().toString());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(intbox.getValue()!=null){
								alternate_required_values.add(intbox.getValue().toString());
							}
						}
					}else if(defaultValue.startsWith("custName")){
						Combobox combo = (Combobox)component;
						if(combo.getSelectedItem()==null && combo.getValue()!=null && combo.getValue().trim().length()!=0){
							Messagebox.show("Please select one account!", "Report", Messagebox.OK, Messagebox.EXCLAMATION);
							return null;
						}else{
							if(combo.getSelectedItem()!=null){
								reportParamsProperties.put(parameter.getName(), combo.getSelectedItem().getLabel().substring(0, combo.getSelectedItem().getLabel().lastIndexOf("(")-1).replaceAll("'", "''"));
							}else{
								reportParamsProperties.put(parameter.getName(), "");
							}
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(combo.getSelectedItem()!=null && combo.getSelectedItem().getValue()!=null){
								alternate_required_values.add(combo.getSelectedItem().getValue().toString().replaceAll("'", "''"));
							}
						}
					}else if(defaultValue.startsWith("div")){
						Listbox listbox = (Listbox)component;
						if(listbox.getSelectedItem()!=null){
							reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(listbox.getSelectedItem().getValue()!=null){
								alternate_required_values.add(listbox.getSelectedItem().getValue().toString().replaceAll("'", "''"));
							}
						}
					}else if(defaultValue.startsWith("dept")){
						Listbox listbox = (Listbox)component;
						if(listbox.getSelectedItem()!=null){
							reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(listbox.getSelectedItem()!=null && listbox.getSelectedItem().getValue()!=null){
								alternate_required_values.add(listbox.getSelectedItem().getValue().toString().replaceAll("'", "''"));
							}
						}
					}else if(defaultValue.startsWith("shipping")){
						Listbox listbox = (Listbox)component;
						if(listbox.getSelectedItem()!=null){
							reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(listbox.getSelectedItem()!=null && listbox.getSelectedItem().getValue()!=null){
								alternate_required_values.add(listbox.getSelectedItem().getValue().toString().replaceAll("'", "''"));
							}
						}
					}else if(defaultValue.startsWith("cardNo")){
						Textbox textbox =(Textbox)component;
						if(textbox.getValue()!=null){
							reportParamsProperties.put(parameter.getName(), textbox.getValue().toString());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(textbox.getValue()!=null){
								alternate_required_values.add(textbox.getValue().toString());
							}
						}
						/*Intbox intbox = (Intbox)component;
						if(intbox.getValue()!=null){
							reportParamsProperties.put(parameter.getName(), intbox.getValue().toString());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
						if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
							alternate_required_labels.add(parameter.getName());
							if(intbox.getValue()!=null){
								alternate_required_values.add(intbox.getValue().toString());
							}
						}*/
					}
				}
			}else if(parameter.getType().startsWith("decimalbox")){
				Decimalbox decimal = (Decimalbox)component;
				if(decimal.getValue()!=null){
					reportParamsProperties.put(parameter.getName(), decimal.getValue().toString());
				} else {
					reportParamsProperties.put(parameter.getName(), "");
				}
				if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
					alternate_required_labels.add(parameter.getName());
					if(decimal.getValue()!=null){
						alternate_required_values.add(decimal.getValue().toString());
					}
				}
			}
			else if(parameter.getType().startsWith("binRangeIntbox")){
				Intbox intbox = (Intbox)component;
				if(intbox.getValue()!=null)
					reportParamsProperties.put(parameter.getName(), intbox.getRawText());
				else
					reportParamsProperties.put(parameter.getName(), "");
			}
			else if(parameter.getType().startsWith("intbox")){
				Intbox intbox = (Intbox)component;
				if(intbox.getValue()!=null)
					reportParamsProperties.put(parameter.getName(), intbox.getRawText());
				else
					reportParamsProperties.put(parameter.getName(), "");
			}
			//Default type is textbox
			else{
				Textbox textbox = (Textbox)component;
				if(component instanceof CapsTextbox){
					textbox.setStyle("text-transform: uppercase");
				}
				if(textbox.getValue()!=null && textbox.getValue().length()>0){
					reportParamsProperties.put(parameter.getName(), textbox.getValue().replaceAll("'", "''"));
				}else{
					reportParamsProperties.put(parameter.getName(), "");
				}
				if(parameter.getDefaultValue().indexOf("ALTERNATE_REQUIRED")!=-1){
					alternate_required_labels.add(parameter.getName());
					if(textbox.getValue()!=null && textbox.getValue().length()!=0){
						alternate_required_values.add(textbox.getValue().replaceAll("'", "''"));
					}
				}
			}
		}
		if(!alternate_required_labels.isEmpty() && alternate_required_values.isEmpty()){
			Messagebox.show("Either one of the following must be selected. " + alternate_required_labels, "Report", Messagebox.OK, Messagebox.ERROR);
			return null;
		}
		//putting the printerBy value in it
		reportParamsProperties.put(Constants.PRINTED_BY, getUserLoginIdAndDomain());
		
		logger.info(report+" Report Parameters:"+reportParamsProperties.toString());
		
		//retrieve format
//		Listbox formatList = (Listbox)this.getFellow("reportFormat");
//		String extension = (String)formatList.getSelectedItem().getLabel();
//		String format = (String)formatList.getSelectedItem().getValue();
//		if(format.equals(Constants.FORMAT_CSV)){
//			StringBuffer dataInCSV = this.generateCSVData(accountNo, accountName, entity, arControl, salesPerson, outstandingAmount, daysLate, type, sortBy, this.getUserLoginId());
//			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
//			Filedownload.save(media);
//		}
//		else{
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+".rml", format, os, reportParamsProperties);
//			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
//			
//			if(format.equals(Constants.FORMAT_EXCEL)){
//				Filedownload.save(media);
//			}
//			else if(format.equals(Constants.FORMAT_PDF)){
//				HashMap params = new HashMap();
//				params.put("report", media);
//				this.forward(Uri.REPORT_RESULT, params);
//			}
//			os.close();
//			client.close();
//		}
		return reportParamsProperties;
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
	public void searchAccountByAccountNo(String customerNo, Intbox intbox) throws InterruptedException{
		logger.info("searchAccountByAccountNo(String customerNo)");
		Combobox accountNameComboBox = (Combobox)intbox.getParent().getNextSibling().getChildren().get(1);
		if(customerNo==null || customerNo.length()==0){
			this.setDivisionInputInvisible(intbox);
			this.setDepartmentInputInvisible(intbox);
			this.setContactInputInvisible(intbox);
			return;
		}
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			String selectedNo = (String)accountNameComboBox.getSelectedItem().getValue();
			if(selectedNo.equals(customerNo)) return;
		}
		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible(intbox);
		this.setDepartmentInputInvisible(intbox);
		
		//append zero in front
		//customerNo = StringUtil.appendLeft(customerNo, 3, "0");
		
		try{
			Map<String, String> accounts = this.businessHelper.getReportBusiness().searchAccount(customerNo, null);
			for(String custNo : accounts.keySet()){
				Comboitem item = new Comboitem(accounts.get(custNo)+" ("+custNo+")");
				item.setValue(custNo);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName(intbox);
			}
			else{
				accountNameComboBox.open();
				intbox.focus();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void searchAccountByAccountName(String name, Combobox combo) throws InterruptedException{
		logger.info("");
		//only begin new search if input is greater than 2
		if(name.length()<3) return;
		
		//accountName still the same as selected one, skip
		if(combo.getSelectedItem()!=null){
			if(name.equals(combo.getSelectedItem().getLabel()))
				return;
		}
		Intbox intbox = (Intbox)combo.getParent().getPreviousSibling().getChildren().get(1);
		//clear textbox for a new search
		Constraint originalConstraint = intbox.getConstraint();
		Constraint nullConstraint = null;
		intbox.setConstraint(nullConstraint);
		intbox.setText("");
		intbox.setConstraint(originalConstraint);
		//Clear list for every new search
		combo.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible(intbox);
		this.setDepartmentInputInvisible(intbox);
		
		try{
			Map<String, String> accounts = this.businessHelper.getReportBusiness().searchAccount(null, name.toUpperCase());
			for(String custNo : accounts.keySet()){
				Comboitem item = new Comboitem(accounts.get(custNo)+" ("+custNo+")");
				item.setValue(custNo);
				combo.appendChild(item);
			}
			if(accounts.size()==1){
				combo.setSelectedIndex(0);
				this.onSelectAccountName(intbox);
			}
			else combo.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void onSelectAccountName(Intbox intbox) throws InterruptedException{
		logger.info("");
		
		try{
			Combobox accountNameComboBox = (Combobox)intbox.getParent().getNextSibling().getChildren().get(1);
			
			if(accountNameComboBox.getSelectedItem()!=null){
				String custNo = (String)accountNameComboBox.getSelectedItem().getValue();
				intbox.setText(custNo);
				
				//Display division or department according to account category
				Map<String, String> divisions = searchDivisionAccount(custNo);
				if(!divisions.isEmpty() && divisions.get(divisions.keySet().iterator().next()).indexOf("(")!=-1){
					this.setDivisionInputVisible(divisions, intbox);
				}else if(divisions.size()!=0){
					this.setDivisionInputVisible(divisions, intbox);
				}
				else{
					this.setDivisionInputInvisible(intbox);
					this.setDepartmentInputInvisible(intbox);
				}
				Map<String, String> contacts = this.businessHelper.getReportBusiness().getAccountContacts(custNo);
				this.setContactInputVisible(contacts, intbox);
			}
			else{
				this.setDivisionInputInvisible(intbox);
				this.setDepartmentInputInvisible(intbox);
				this.setContactInputInvisible(intbox);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public Map<String, String>  searchDivisionAccount(String custNo){
		return this.businessHelper.getReportBusiness().searchChildrenAccount(custNo, false);
	}
	
	
	private void setDivisionInputVisible(Map<String, String> divisions, Intbox intbox){
		Row divisionRow = (Row)intbox.getParent().getNextSibling().getNextSibling();
		if(divisionRow.getChildren().size()==1){
			return;
		}
		if(((Component)divisionRow.getChildren().get(1)).getId().endsWith("Division")){
			divisionRow.setVisible(true);
			Listbox divisionListBox = (Listbox)divisionRow.getChildren().get(1);
			divisionListBox.getChildren().clear();
			divisionListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			
			for(String acctNo : divisions.keySet()){
				Listitem newItem = new Listitem(divisions.get(acctNo));
				newItem.setValue(acctNo);
				divisionListBox.appendChild(newItem);
			}
		}
	}
	
	private void setDepartmentInputVisible(Map<String, String> depts, Intbox intbox){
		Row deptRow = (Row)intbox.getParent().getNextSibling().getNextSibling().getNextSibling();
		if(deptRow.getChildren().size()==1){
			return;
		}
		if(((Component)deptRow.getChildren().get(1)).getId().endsWith("Department")){
			deptRow.setVisible(true);
			Listbox departmentListBox = (Listbox)deptRow.getChildren().get(1);
			departmentListBox.getChildren().clear();
			departmentListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			for(String acctNo : depts.keySet()){
				Listitem newItem = new Listitem(depts.get(acctNo));
				newItem.setValue(acctNo);
				departmentListBox.appendChild(newItem);
			}
		}
	}
	private void setDivisionInputInvisible(Intbox intbox){
		if(((Component)intbox.getParent().getNextSibling().getNextSibling().getChildren().get(1)).getId().endsWith("Division")){
			intbox.getParent().getNextSibling().getNextSibling().setVisible(false);
			Listbox division = (Listbox)intbox.getParent().getNextSibling().getNextSibling().getChildren().get(1);
			division.getChildren().clear();
		}
	}
	
	private void setDepartmentInputInvisible(Intbox intbox){
		if(((Component)intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getChildren().get(1)).getId().endsWith("Department")){
			intbox.getParent().getNextSibling().getNextSibling().getNextSibling().setVisible(false);
			Listbox dept = (Listbox)intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getChildren().get(1);
			dept.getChildren().clear();
		}
	}
	public void onSelectDivision(Listbox divisionListBox) throws InterruptedException{
		logger.info("");
		try{
			Object selectedValue = divisionListBox.getSelectedItem().getValue();
			if(selectedValue instanceof String && selectedValue!=null && selectedValue.toString().length()!=0){
				Intbox intbox = (Intbox)divisionListBox.getParent().getPreviousSibling().getPreviousSibling().getChildren().get(1);
				String code = divisionListBox.getSelectedItem().getLabel().substring(divisionListBox.getSelectedItem().getLabel().lastIndexOf("(")+1, divisionListBox.getSelectedItem().getLabel().length()-1);
				Map<String, String> depts = searchDepartmentAccount(intbox.getValue().toString(), code);
				if(!depts.isEmpty()){
					this.setDepartmentInputVisible(depts, intbox);
				}
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public Map<String, String> searchDepartmentAccount(String custNo, String parentCode){
		return this.businessHelper.getReportBusiness().searchChildrenAccount(custNo, parentCode, false);
	}
	
	
	public void onSelectDepartment(Listbox departmentListBox) throws InterruptedException{
		logger.info("");
		
		try{
			Object selectedValue = departmentListBox.getSelectedItem().getValue();
			if((selectedValue instanceof String) == false){
				Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
				divisionListBox.setSelectedIndex(0);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	//Test
	public void generate_CardIssuance() throws HttpException, IOException, InterruptedException, NetException {
		logger.info("");
		this.displayProcessing();
		
		//Retrieve report parameters
		Properties reportParamsProperties = new Properties();
		for(Parameter parameter : parameters){
			if(parameter.getName().equals(Constants.PRINTED_BY)) continue;
			
			Component component = this.getFellow(parameter.getName());
			
			if(parameter.getType().startsWith("date")){
				Datebox datebox = (Datebox)component;
				if(datebox.getValue()!=null)
					reportParamsProperties.put(parameter.getName(), DateUtil.convertDateToStr(datebox.getValue(), DateUtil.REPORT_DATE_FORMAT));
				else
					reportParamsProperties.put(parameter.getName(), "");
			}
			else if(parameter.getType().startsWith("choice")){
				Listbox listbox = (Listbox)component;
				if(listbox.getSelectedItem().getValue().toString().length()>0)
					reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
				else
					reportParamsProperties.put(parameter.getName(), "");
			}else if(parameter.getType().startsWith("acctsearch")){
				String defaultValue = parameter.getDefaultValue();
				if(defaultValue!=null && defaultValue.length()!=0){
					if(defaultValue.startsWith("custId")){
						Intbox intbox = (Intbox)component;
						if(intbox.getValue()!=null){
							reportParamsProperties.put(parameter.getName(), intbox.getValue().toString());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
					}else if(defaultValue.startsWith("custName")){
						Combobox combo = (Combobox)component;
						if(combo.getSelectedItem()==null && combo.getValue()!=null && combo.getValue().trim().length()!=0){
							Messagebox.show("Please select one account!", "Report", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}else{
							if(combo.getSelectedItem()!=null){
								reportParamsProperties.put(parameter.getName(), combo.getSelectedItem().getLabel().substring(0, combo.getSelectedItem().getLabel().lastIndexOf("(")-1));
							}else{
								reportParamsProperties.put(parameter.getName(), "");
							}
						}
					}else if(defaultValue.startsWith("div")){
						Listbox listbox = (Listbox)component;
						if(listbox.getSelectedItem()!=null){
							reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
					}else if(defaultValue.startsWith("dept")){
						Listbox listbox = (Listbox)component;
						if(listbox.getSelectedItem()!=null){
							reportParamsProperties.put(parameter.getName(), listbox.getSelectedItem().getValue());
						}else{
							reportParamsProperties.put(parameter.getName(), "");
						}
					}
				}
			}
			//Default type is textbox
			else{
				CapsTextbox textbox = (CapsTextbox)component;
				textbox.setStyle("text-transform: uppercase");
				if(textbox.getValue()!=null && textbox.getValue().length()>0)
					reportParamsProperties.put(parameter.getName(), textbox.getValue().replaceAll("'", "''"));
				else
					reportParamsProperties.put(parameter.getName(), "");
			}
		}
		//putting the printerBy value in it
		reportParamsProperties.put(Constants.PRINTED_BY, getUserLoginIdAndDomain());
		
		logger.info(report+" Report Parameters:"+reportParamsProperties.toString());
		
		String productType=reportParamsProperties.getProperty("2. Product Type");
		logger.info("Product Type ID : "+productType);
		boolean issueByBatch= this.businessHelper.getReportBusiness().checkProductTypeByID(productType);
		
		//retrieve format
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		logger.info("Report : "+report );
		logger.info("issueByBatch : "+issueByBatch );
		logger.info("format "+format);
		logger.info("extension "+extension);
		logger.info("test "+repository+"/"+reportCategory+"/"+report+"/"+report+" By Batch.rml" );
		if(issueByBatch)
		   client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+" By Batch.rml", format, os, reportParamsProperties);
		else
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+" Not By Batch.rml", format, os, reportParamsProperties);
		//if(format.equals(Constants.FORMAT_RTF)) format = Constants.FORMAT_WORD;
		AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
		
		if(format.equals(Constants.FORMAT_EXCEL)){
			Filedownload.save(media);
		}
		else if(format.equals(Constants.FORMAT_PDF)){
			HashMap params = new HashMap();
			params.put("report", media);
			this.forward(Uri.REPORT_RESULT, params);
		}
		else if(format.equals(Constants.FORMAT_RTF)){
			Filedownload.save(media);
		}
		os.close();
		client.close();
	}
	private void setContactInputInvisible(Intbox intbox){
		String id = ((Component)intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getChildren().get(1)).getId();
		if(id.endsWith("c. Contact Person") || id.endsWith("d. Contact Person") || id.endsWith("e. Contact Person")){
			intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getNextSibling().setVisible(false);
			Listbox contacts = (Listbox)intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getChildren().get(1);
			
			contacts.getChildren().clear();
		}
	}
	private void setContactInputVisible(Map<String, String> contacts, Intbox intbox){
		Row contactRow = (Row)intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
		if(contactRow.getChildren().size()==1){
			return;
		}
		String id = ((Component)intbox.getParent().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getChildren().get(1)).getId();
		if(id.endsWith("c. Contact Person") || id.endsWith("d. Contact Person") || id.endsWith("e. Contact Person")){
			contactRow.setVisible(true);
			Listbox divisionListBox = (Listbox)contactRow.getChildren().get(1);
			divisionListBox.getChildren().clear();
			divisionListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			
			for(String contactNo : contacts.keySet()){
				Listitem newItem = new Listitem(contacts.get(contactNo));
				newItem.setValue(contactNo);
				divisionListBox.appendChild(newItem);
			}
		}
	}
	public void filterDoubleQuote(Properties reportParamsProperties) {
		
		//Example: account_name = Victor's Company
		//Parameter which contain quote(') symbol will be converted to double quote('') in order to work in generate PDF, XLS (Elixir).
		//However in generate CSV, system in fact will auto convert quote to double quote, converted parameter will be auto convert again to quad quote ('''')
		//which result no records in generating CSV report.
		//Hence one of the solution is revert the converted parameter in generating CSV.
		
		for(Entry<Object, Object> property : reportParamsProperties.entrySet()) {
			Object object = property.getValue();
			if(object instanceof String) {
				String paramInStr = (String) object;
				property.setValue(paramInStr.replaceAll("''", "'"));

			}
		}
	}
	
	
	public abstract void generate() throws HttpException, IOException, InterruptedException, NetException;
	protected String convertYYYYMMDDtoDDMMYYYY(String dateString){
		return DateUtil.convertDateToStr(DateUtil.convertStrToDate(dateString, "yyyy-MM-dd"), DateUtil.GLOBAL_DATE_FORMAT);
	}
	protected String convertYYYYMMDDtoMMMYYYY(String dateString){
		return DateUtil.convertDateToStr(DateUtil.convertStrToDate(dateString, "yyyy-MM-dd"), "MMM-yyyy");
	}
	
	protected List<String[]> convertToStringArray(List<Object[]> results){
	
		List<String[]> strArrayList = Lists.newArrayList();
		for(Object[] objArray: results){
			
			int length = objArray.length;
			String[] strArray = new String[length];
			for(int i=0; i<length; i++){
				if(objArray[i]!=null){
					strArray[i] = objArray[i].toString();
				} else {
					strArray[i] = "";
				}
			}
			strArrayList.add(strArray);
		}
		
		return strArrayList;
	
	}
	
	
	
	protected String appendWithQuote(String str){
		if(!Strings.isNullOrEmpty(str)){
			return "'" + str;
		}
		
		return "";
	}

	public Long getReportRsrcId() {
		return reportRsrcId;
	}
}