package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "capital_investment")
public class FoodCapitalInvestmentDetails implements Serializable {

	private String unit_id;
	private String control_id;
	@Id
	private String id;

	@Column(name = "itemslandcost")
	private String itemslandcost;
	@Column(name = "proposedinvestmentlandcost")
	private String proposedinvestmentlandcost;
	@Column(name = "appraisedcostbybanklandcost")
	private String appraisedcostbybanklandcost;
	@Column(name = "itemscentralprocessingcenter")
	private String itemscentralprocessingcenter;
	@Column(name = "proposedinvestmentcentralprocessingcenter")
	private String proposedinvestmentcentralprocessingcenter;
	@Column(name = "appraisedcostbybankcentralprocessingcenter")
	private String appraisedcostbybankcentralprocessingcenter;
	@Column(name = "itemsprimaryprocessingcenter")
	private String itemsprimaryprocessingcenter;
	@Column(name = "proposedinvestmentprimaryprocessingcenter")
	private String proposedinvestmentprimaryprocessingcenter;
	@Column(name = "appraisedcostbybankprimaryprocessingcenter")
	private String appraisedcostbybankprimaryprocessingcenter;
	@Column(name = "itemsbasicenablinginfrastructurecentralprocessingcenter")
	private String itemsbasicenablinginfrastructurecentralprocessingcenter;
	@Column(name = "proposedinvestmentbasicenablinginfrastructurecentralprocessingce")
	private String proposedinvestmentbasicenablinginfrastructurecentralprocessingce;
	@Column(name = "appraisedcostbybankbasicenablinginfrastructurecentralprocessingc")
	private String appraisedcostbybankbasicenablinginfrastructurecentralprocessingc;
	@Column(name = "itemsbasicenablinginfrastructureprimaryprocessingcenter")
	private String itemsbasicenablinginfrastructureprimaryprocessingcenter;
	@Column(name = "proposedinvestmentbasicenablinginfrastructureprimaryprocessingce")
	private String proposedinvestmentbasicenablinginfrastructureprimaryprocessingce;
	@Column(name = "appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc")
	private String appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc;
	@Column(name = "itemscoreinfrastructurecentralprocessingcenter")
	private String itemscoreinfrastructurecentralprocessingcenter;
	@Column(name = "proposedinvestmentcoreinfrastructurecentralprocessingcenter")
	private String proposedinvestmentcoreinfrastructurecentralprocessingcenter;
	@Column(name = "appraisedcostbybankcoreinfrastructurecentralprocessingcenter")
	private String appraisedcostbybankcoreinfrastructurecentralprocessingcenter;
	@Column(name = "itemscoreinfrastructureprimaryprocessingcenter")
	private String itemscoreinfrastructureprimaryprocessingcenter;
	@Column(name = "proposedinvestmentcoreinfrastructureprimaryprocessingcenter")
	private String proposedinvestmentcoreinfrastructureprimaryprocessingcenter;
	@Column(name = "appraisedcostbybankcoreinfrastructureprimaryprocessingcenter")
	private String appraisedcostbybankcoreinfrastructureprimaryprocessingcenter;
	@Column(name = "itemsnoncoreinfrastructurecentralprocessingcenter")
	private String itemsnoncoreinfrastructurecentralprocessingcenter;
	@Column(name = "proposedinvestmentnoncoreinfrastructurecentralprocessingcenter")
	private String proposedinvestmentnoncoreinfrastructurecentralprocessingcenter;
	@Column(name = "appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter")
	private String appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter;
	@Column(name = "itemsnoncoreinfrastructureprimaryprocessingcenter")
	private String itemsnoncoreinfrastructureprimaryprocessingcenter;
	@Column(name = "proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter")
	private String proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter;
	@Column(name = "appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter")
	private String appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter;
	@Column(name = "itemsinterestduringconstruction")
	private String itemsinterestduringconstruction;
	@Column(name = "proposedinvestmentinterestduringconstruction")
	private String proposedinvestmentinterestduringconstruction;
	@Column(name = "appraisedcostbybankinterestduringconstruction")
	private String appraisedcostbybankinterestduringconstruction;
	@Column(name = "itemsprojectmanagementconsultantandconsultancyfee")
	private String itemsprojectmanagementconsultantandconsultancyfee;
	@Column(name = "proposedinvestmentprojectmanagementconsultantandconsultancyfee")
	private String proposedinvestmentprojectmanagementconsultantandconsultancyfee;
	@Column(name = "appraisedcostbybankprojectmanagementconsultantandconsultancyfee")
	private String appraisedcostbybankprojectmanagementconsultantandconsultancyfee;
	@Column(name = "itemspreliminaryandpreparativeexpenses")
	private String itemspreliminaryandpreparativeexpenses;
	@Column(name = "proposedinvestmentpreliminaryandpreparativeexpenses")
	private String proposedinvestmentpreliminaryandpreparativeexpenses;
	@Column(name = "appraisedcostbybankpreliminaryandpreparativeexpenses")
	private String appraisedcostbybankpreliminaryandpreparativeexpenses;
	@Column(name = "itemsmarginmoneyforworkingcapital")
	private String itemsmarginmoneyforworkingcapital;
	@Column(name = "proposedinvestmentmarginmoneyforworkingcapital")
	private String proposedinvestmentmarginmoneyforworkingcapital;
	@Column(name = "appraisedcostbybankmarginmoneyforworkingcapital")
	private String appraisedcostbybankmarginmoneyforworkingcapital;
	@Column(name = "itemscontingencies")
	private String itemscontingencies;
	@Column(name = "proposedinvestmentcontingencies")
	private String proposedinvestmentcontingencies;
	@Column(name = "appraisedcostbybankcontingencies")
	private String appraisedcostbybankcontingencies;
	@Column(name = "itemstotal")
	private String itemstotal;
	@Column(name = "proposedinvestmenttotal")
	private String proposedinvestmenttotal;
	@Column(name = "appraisedcostbybanktotal")
	private String appraisedcostbybanktotal;

	

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemslandcost() {
		return itemslandcost;
	}

	public void setItemslandcost(String itemslandcost) {
		this.itemslandcost = itemslandcost;
	}

	public String getProposedinvestmentlandcost() {
		return proposedinvestmentlandcost;
	}

	public void setProposedinvestmentlandcost(String proposedinvestmentlandcost) {
		this.proposedinvestmentlandcost = proposedinvestmentlandcost;
	}

	public String getAppraisedcostbybanklandcost() {
		return appraisedcostbybanklandcost;
	}

	public void setAppraisedcostbybanklandcost(String appraisedcostbybanklandcost) {
		this.appraisedcostbybanklandcost = appraisedcostbybanklandcost;
	}

	public String getItemscentralprocessingcenter() {
		return itemscentralprocessingcenter;
	}

	public void setItemscentralprocessingcenter(String itemscentralprocessingcenter) {
		this.itemscentralprocessingcenter = itemscentralprocessingcenter;
	}

	public String getProposedinvestmentcentralprocessingcenter() {
		return proposedinvestmentcentralprocessingcenter;
	}

	public void setProposedinvestmentcentralprocessingcenter(String proposedinvestmentcentralprocessingcenter) {
		this.proposedinvestmentcentralprocessingcenter = proposedinvestmentcentralprocessingcenter;
	}

	public String getAppraisedcostbybankcentralprocessingcenter() {
		return appraisedcostbybankcentralprocessingcenter;
	}

	public void setAppraisedcostbybankcentralprocessingcenter(String appraisedcostbybankcentralprocessingcenter) {
		this.appraisedcostbybankcentralprocessingcenter = appraisedcostbybankcentralprocessingcenter;
	}

	public String getItemsprimaryprocessingcenter() {
		return itemsprimaryprocessingcenter;
	}

	public void setItemsprimaryprocessingcenter(String itemsprimaryprocessingcenter) {
		this.itemsprimaryprocessingcenter = itemsprimaryprocessingcenter;
	}

	public String getProposedinvestmentprimaryprocessingcenter() {
		return proposedinvestmentprimaryprocessingcenter;
	}

	public void setProposedinvestmentprimaryprocessingcenter(String proposedinvestmentprimaryprocessingcenter) {
		this.proposedinvestmentprimaryprocessingcenter = proposedinvestmentprimaryprocessingcenter;
	}

	public String getAppraisedcostbybankprimaryprocessingcenter() {
		return appraisedcostbybankprimaryprocessingcenter;
	}

	public void setAppraisedcostbybankprimaryprocessingcenter(String appraisedcostbybankprimaryprocessingcenter) {
		this.appraisedcostbybankprimaryprocessingcenter = appraisedcostbybankprimaryprocessingcenter;
	}

	public String getItemsbasicenablinginfrastructurecentralprocessingcenter() {
		return itemsbasicenablinginfrastructurecentralprocessingcenter;
	}

	public void setItemsbasicenablinginfrastructurecentralprocessingcenter(
			String itemsbasicenablinginfrastructurecentralprocessingcenter) {
		this.itemsbasicenablinginfrastructurecentralprocessingcenter = itemsbasicenablinginfrastructurecentralprocessingcenter;
	}

	public String getProposedinvestmentbasicenablinginfrastructurecentralprocessingce() {
		return proposedinvestmentbasicenablinginfrastructurecentralprocessingce;
	}

	public void setProposedinvestmentbasicenablinginfrastructurecentralprocessingce(
			String proposedinvestmentbasicenablinginfrastructurecentralprocessingce) {
		this.proposedinvestmentbasicenablinginfrastructurecentralprocessingce = proposedinvestmentbasicenablinginfrastructurecentralprocessingce;
	}

	public String getAppraisedcostbybankbasicenablinginfrastructurecentralprocessingc() {
		return appraisedcostbybankbasicenablinginfrastructurecentralprocessingc;
	}

	public void setAppraisedcostbybankbasicenablinginfrastructurecentralprocessingc(
			String appraisedcostbybankbasicenablinginfrastructurecentralprocessingc) {
		this.appraisedcostbybankbasicenablinginfrastructurecentralprocessingc = appraisedcostbybankbasicenablinginfrastructurecentralprocessingc;
	}

	public String getItemsbasicenablinginfrastructureprimaryprocessingcenter() {
		return itemsbasicenablinginfrastructureprimaryprocessingcenter;
	}

	public void setItemsbasicenablinginfrastructureprimaryprocessingcenter(
			String itemsbasicenablinginfrastructureprimaryprocessingcenter) {
		this.itemsbasicenablinginfrastructureprimaryprocessingcenter = itemsbasicenablinginfrastructureprimaryprocessingcenter;
	}

	public String getProposedinvestmentbasicenablinginfrastructureprimaryprocessingce() {
		return proposedinvestmentbasicenablinginfrastructureprimaryprocessingce;
	}

	public void setProposedinvestmentbasicenablinginfrastructureprimaryprocessingce(
			String proposedinvestmentbasicenablinginfrastructureprimaryprocessingce) {
		this.proposedinvestmentbasicenablinginfrastructureprimaryprocessingce = proposedinvestmentbasicenablinginfrastructureprimaryprocessingce;
	}

	public String getAppraisedcostbybankbasicenablinginfrastructureprimaryprocessingc() {
		return appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc;
	}

	public void setAppraisedcostbybankbasicenablinginfrastructureprimaryprocessingc(
			String appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc) {
		this.appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc = appraisedcostbybankbasicenablinginfrastructureprimaryprocessingc;
	}

	public String getItemscoreinfrastructurecentralprocessingcenter() {
		return itemscoreinfrastructurecentralprocessingcenter;
	}

	public void setItemscoreinfrastructurecentralprocessingcenter(
			String itemscoreinfrastructurecentralprocessingcenter) {
		this.itemscoreinfrastructurecentralprocessingcenter = itemscoreinfrastructurecentralprocessingcenter;
	}

	public String getProposedinvestmentcoreinfrastructurecentralprocessingcenter() {
		return proposedinvestmentcoreinfrastructurecentralprocessingcenter;
	}

	public void setProposedinvestmentcoreinfrastructurecentralprocessingcenter(
			String proposedinvestmentcoreinfrastructurecentralprocessingcenter) {
		this.proposedinvestmentcoreinfrastructurecentralprocessingcenter = proposedinvestmentcoreinfrastructurecentralprocessingcenter;
	}

	public String getAppraisedcostbybankcoreinfrastructurecentralprocessingcenter() {
		return appraisedcostbybankcoreinfrastructurecentralprocessingcenter;
	}

	public void setAppraisedcostbybankcoreinfrastructurecentralprocessingcenter(
			String appraisedcostbybankcoreinfrastructurecentralprocessingcenter) {
		this.appraisedcostbybankcoreinfrastructurecentralprocessingcenter = appraisedcostbybankcoreinfrastructurecentralprocessingcenter;
	}

	public String getItemscoreinfrastructureprimaryprocessingcenter() {
		return itemscoreinfrastructureprimaryprocessingcenter;
	}

	public void setItemscoreinfrastructureprimaryprocessingcenter(
			String itemscoreinfrastructureprimaryprocessingcenter) {
		this.itemscoreinfrastructureprimaryprocessingcenter = itemscoreinfrastructureprimaryprocessingcenter;
	}

	public String getProposedinvestmentcoreinfrastructureprimaryprocessingcenter() {
		return proposedinvestmentcoreinfrastructureprimaryprocessingcenter;
	}

	public void setProposedinvestmentcoreinfrastructureprimaryprocessingcenter(
			String proposedinvestmentcoreinfrastructureprimaryprocessingcenter) {
		this.proposedinvestmentcoreinfrastructureprimaryprocessingcenter = proposedinvestmentcoreinfrastructureprimaryprocessingcenter;
	}

	public String getAppraisedcostbybankcoreinfrastructureprimaryprocessingcenter() {
		return appraisedcostbybankcoreinfrastructureprimaryprocessingcenter;
	}

	public void setAppraisedcostbybankcoreinfrastructureprimaryprocessingcenter(
			String appraisedcostbybankcoreinfrastructureprimaryprocessingcenter) {
		this.appraisedcostbybankcoreinfrastructureprimaryprocessingcenter = appraisedcostbybankcoreinfrastructureprimaryprocessingcenter;
	}

	public String getItemsnoncoreinfrastructurecentralprocessingcenter() {
		return itemsnoncoreinfrastructurecentralprocessingcenter;
	}

	public void setItemsnoncoreinfrastructurecentralprocessingcenter(
			String itemsnoncoreinfrastructurecentralprocessingcenter) {
		this.itemsnoncoreinfrastructurecentralprocessingcenter = itemsnoncoreinfrastructurecentralprocessingcenter;
	}

	public String getProposedinvestmentnoncoreinfrastructurecentralprocessingcenter() {
		return proposedinvestmentnoncoreinfrastructurecentralprocessingcenter;
	}

	public void setProposedinvestmentnoncoreinfrastructurecentralprocessingcenter(
			String proposedinvestmentnoncoreinfrastructurecentralprocessingcenter) {
		this.proposedinvestmentnoncoreinfrastructurecentralprocessingcenter = proposedinvestmentnoncoreinfrastructurecentralprocessingcenter;
	}

	public String getAppraisedcostbybanknoncoreinfrastructurecentralprocessingcenter() {
		return appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter;
	}

	public void setAppraisedcostbybanknoncoreinfrastructurecentralprocessingcenter(
			String appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter) {
		this.appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter = appraisedcostbybanknoncoreinfrastructurecentralprocessingcenter;
	}

	public String getItemsnoncoreinfrastructureprimaryprocessingcenter() {
		return itemsnoncoreinfrastructureprimaryprocessingcenter;
	}

	public void setItemsnoncoreinfrastructureprimaryprocessingcenter(
			String itemsnoncoreinfrastructureprimaryprocessingcenter) {
		this.itemsnoncoreinfrastructureprimaryprocessingcenter = itemsnoncoreinfrastructureprimaryprocessingcenter;
	}

	public String getProposedinvestmentnoncoreinfrastructureprimaryprocessingcenter() {
		return proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter;
	}

	public void setProposedinvestmentnoncoreinfrastructureprimaryprocessingcenter(
			String proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter) {
		this.proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter = proposedinvestmentnoncoreinfrastructureprimaryprocessingcenter;
	}

	public String getAppraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter() {
		return appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter;
	}

	public void setAppraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter(
			String appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter) {
		this.appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter = appraisedcostbybanknoncoreinfrastructureprimaryprocessingcenter;
	}

	public String getItemsinterestduringconstruction() {
		return itemsinterestduringconstruction;
	}

	public void setItemsinterestduringconstruction(String itemsinterestduringconstruction) {
		this.itemsinterestduringconstruction = itemsinterestduringconstruction;
	}

	public String getProposedinvestmentinterestduringconstruction() {
		return proposedinvestmentinterestduringconstruction;
	}

	public void setProposedinvestmentinterestduringconstruction(String proposedinvestmentinterestduringconstruction) {
		this.proposedinvestmentinterestduringconstruction = proposedinvestmentinterestduringconstruction;
	}

	public String getAppraisedcostbybankinterestduringconstruction() {
		return appraisedcostbybankinterestduringconstruction;
	}

	public void setAppraisedcostbybankinterestduringconstruction(String appraisedcostbybankinterestduringconstruction) {
		this.appraisedcostbybankinterestduringconstruction = appraisedcostbybankinterestduringconstruction;
	}

	public String getItemsprojectmanagementconsultantandconsultancyfee() {
		return itemsprojectmanagementconsultantandconsultancyfee;
	}

	public void setItemsprojectmanagementconsultantandconsultancyfee(
			String itemsprojectmanagementconsultantandconsultancyfee) {
		this.itemsprojectmanagementconsultantandconsultancyfee = itemsprojectmanagementconsultantandconsultancyfee;
	}

	public String getProposedinvestmentprojectmanagementconsultantandconsultancyfee() {
		return proposedinvestmentprojectmanagementconsultantandconsultancyfee;
	}

	public void setProposedinvestmentprojectmanagementconsultantandconsultancyfee(
			String proposedinvestmentprojectmanagementconsultantandconsultancyfee) {
		this.proposedinvestmentprojectmanagementconsultantandconsultancyfee = proposedinvestmentprojectmanagementconsultantandconsultancyfee;
	}

	public String getAppraisedcostbybankprojectmanagementconsultantandconsultancyfee() {
		return appraisedcostbybankprojectmanagementconsultantandconsultancyfee;
	}

	public void setAppraisedcostbybankprojectmanagementconsultantandconsultancyfee(
			String appraisedcostbybankprojectmanagementconsultantandconsultancyfee) {
		this.appraisedcostbybankprojectmanagementconsultantandconsultancyfee = appraisedcostbybankprojectmanagementconsultantandconsultancyfee;
	}

	public String getItemspreliminaryandpreparativeexpenses() {
		return itemspreliminaryandpreparativeexpenses;
	}

	public void setItemspreliminaryandpreparativeexpenses(String itemspreliminaryandpreparativeexpenses) {
		this.itemspreliminaryandpreparativeexpenses = itemspreliminaryandpreparativeexpenses;
	}

	public String getProposedinvestmentpreliminaryandpreparativeexpenses() {
		return proposedinvestmentpreliminaryandpreparativeexpenses;
	}

	public void setProposedinvestmentpreliminaryandpreparativeexpenses(
			String proposedinvestmentpreliminaryandpreparativeexpenses) {
		this.proposedinvestmentpreliminaryandpreparativeexpenses = proposedinvestmentpreliminaryandpreparativeexpenses;
	}

	public String getAppraisedcostbybankpreliminaryandpreparativeexpenses() {
		return appraisedcostbybankpreliminaryandpreparativeexpenses;
	}

	public void setAppraisedcostbybankpreliminaryandpreparativeexpenses(
			String appraisedcostbybankpreliminaryandpreparativeexpenses) {
		this.appraisedcostbybankpreliminaryandpreparativeexpenses = appraisedcostbybankpreliminaryandpreparativeexpenses;
	}

	public String getItemsmarginmoneyforworkingcapital() {
		return itemsmarginmoneyforworkingcapital;
	}

	public void setItemsmarginmoneyforworkingcapital(String itemsmarginmoneyforworkingcapital) {
		this.itemsmarginmoneyforworkingcapital = itemsmarginmoneyforworkingcapital;
	}

	public String getProposedinvestmentmarginmoneyforworkingcapital() {
		return proposedinvestmentmarginmoneyforworkingcapital;
	}

	public void setProposedinvestmentmarginmoneyforworkingcapital(
			String proposedinvestmentmarginmoneyforworkingcapital) {
		this.proposedinvestmentmarginmoneyforworkingcapital = proposedinvestmentmarginmoneyforworkingcapital;
	}

	public String getAppraisedcostbybankmarginmoneyforworkingcapital() {
		return appraisedcostbybankmarginmoneyforworkingcapital;
	}

	public void setAppraisedcostbybankmarginmoneyforworkingcapital(
			String appraisedcostbybankmarginmoneyforworkingcapital) {
		this.appraisedcostbybankmarginmoneyforworkingcapital = appraisedcostbybankmarginmoneyforworkingcapital;
	}

	public String getItemscontingencies() {
		return itemscontingencies;
	}

	public void setItemscontingencies(String itemscontingencies) {
		this.itemscontingencies = itemscontingencies;
	}

	public String getProposedinvestmentcontingencies() {
		return proposedinvestmentcontingencies;
	}

	public void setProposedinvestmentcontingencies(String proposedinvestmentcontingencies) {
		this.proposedinvestmentcontingencies = proposedinvestmentcontingencies;
	}

	public String getAppraisedcostbybankcontingencies() {
		return appraisedcostbybankcontingencies;
	}

	public void setAppraisedcostbybankcontingencies(String appraisedcostbybankcontingencies) {
		this.appraisedcostbybankcontingencies = appraisedcostbybankcontingencies;
	}

	public String getItemstotal() {
		return itemstotal;
	}

	public void setItemstotal(String itemstotal) {
		this.itemstotal = itemstotal;
	}

	public String getProposedinvestmenttotal() {
		return proposedinvestmenttotal;
	}

	public void setProposedinvestmenttotal(String proposedinvestmenttotal) {
		this.proposedinvestmenttotal = proposedinvestmenttotal;
	}

	public String getAppraisedcostbybanktotal() {
		return appraisedcostbybanktotal;
	}

	public void setAppraisedcostbybanktotal(String appraisedcostbybanktotal) {
		this.appraisedcostbybanktotal = appraisedcostbybanktotal;
	}

}
