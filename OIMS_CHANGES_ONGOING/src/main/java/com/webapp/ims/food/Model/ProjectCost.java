/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */

@Entity
@Table(name = "project_cost")
///@IdClass(Identifier.class)
public class ProjectCost {

	@Column(name = "unit_id")
	private String unit_id;

	private String control_id;

	@Id
	private String id;

		@Column(name = "itemsconsultantfee")
	    private String itemsconsultantfee;
	    @Column(name = "costconsultantfee")
	    private String costconsultantfee;
	    @Column(name = "itemsfeechargedbycertifyingagency")
	    private String itemsfeechargedbycertifyingagency;
	    @Column(name = "costfeechargedbycertifyingagency")
	    private String costfeechargedbycertifyingagency;
	    @Column(name = "itemstechnicalcivilworkswithreferencetogapstudyreport")
	    private String itemstechnicalcivilworkswithreferencetogapstudyreport;
	    @Column(name = "costtechnicalcivilworkswithreferencetogapstudyreport")
	    private String costtechnicalcivilworkswithreferencetogapstudyreport;
	    @Column(name = "itemsplantmachinerywithreferencetogapanalysisreport")
	    private String itemsplantmachinerywithreferencetogapanalysisreport;
	    @Column(name = "costplantmachinerywithreferencetogapanalysisreport")
	    private String costplantmachinerywithreferencetogapanalysisreport;
	    @Column(name = "itemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000")
	    private String itemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000;
	    @Column(name = "costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h")
	    private String costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h;
	    @Column(name = "itemspatentregistrationfee")
	    private String itemspatentregistrationfee;
	    @Column(name = "costpatentregistrationfee")
	    private String costpatentregistrationfee;
	    @Column(name = "itemstotal")
	    private String itemstotal;
	    @Column(name = "costtotal")
	    private String costtotal;
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
		public String getItemsconsultantfee() {
			return itemsconsultantfee;
		}
		public void setItemsconsultantfee(String itemsconsultantfee) {
			this.itemsconsultantfee = itemsconsultantfee;
		}
		public String getCostconsultantfee() {
			return costconsultantfee;
		}
		public void setCostconsultantfee(String costconsultantfee) {
			this.costconsultantfee = costconsultantfee;
		}
		public String getItemsfeechargedbycertifyingagency() {
			return itemsfeechargedbycertifyingagency;
		}
		public void setItemsfeechargedbycertifyingagency(String itemsfeechargedbycertifyingagency) {
			this.itemsfeechargedbycertifyingagency = itemsfeechargedbycertifyingagency;
		}
		public String getCostfeechargedbycertifyingagency() {
			return costfeechargedbycertifyingagency;
		}
		public void setCostfeechargedbycertifyingagency(String costfeechargedbycertifyingagency) {
			this.costfeechargedbycertifyingagency = costfeechargedbycertifyingagency;
		}
		public String getItemstechnicalcivilworkswithreferencetogapstudyreport() {
			return itemstechnicalcivilworkswithreferencetogapstudyreport;
		}
		public void setItemstechnicalcivilworkswithreferencetogapstudyreport(
				String itemstechnicalcivilworkswithreferencetogapstudyreport) {
			this.itemstechnicalcivilworkswithreferencetogapstudyreport = itemstechnicalcivilworkswithreferencetogapstudyreport;
		}
		public String getCosttechnicalcivilworkswithreferencetogapstudyreport() {
			return costtechnicalcivilworkswithreferencetogapstudyreport;
		}
		public void setCosttechnicalcivilworkswithreferencetogapstudyreport(
				String costtechnicalcivilworkswithreferencetogapstudyreport) {
			this.costtechnicalcivilworkswithreferencetogapstudyreport = costtechnicalcivilworkswithreferencetogapstudyreport;
		}
		public String getItemsplantmachinerywithreferencetogapanalysisreport() {
			return itemsplantmachinerywithreferencetogapanalysisreport;
		}
		public void setItemsplantmachinerywithreferencetogapanalysisreport(
				String itemsplantmachinerywithreferencetogapanalysisreport) {
			this.itemsplantmachinerywithreferencetogapanalysisreport = itemsplantmachinerywithreferencetogapanalysisreport;
		}
		public String getCostplantmachinerywithreferencetogapanalysisreport() {
			return costplantmachinerywithreferencetogapanalysisreport;
		}
		public void setCostplantmachinerywithreferencetogapanalysisreport(
				String costplantmachinerywithreferencetogapanalysisreport) {
			this.costplantmachinerywithreferencetogapanalysisreport = costplantmachinerywithreferencetogapanalysisreport;
		}
		public String getItemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000() {
			return itemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000;
		}
		public void setItemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000(
				String itemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000) {
			this.itemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000 = itemsanyotherexpensesaspertherequirementofgmpghpiso14000iso22000;
		}
		public String getCostanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h() {
			return costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h;
		}
		public void setCostanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h(
				String costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h) {
			this.costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h = costanyotherexpensesaspertherequirementofgmpghpiso14000iso22000h;
		}
		public String getItemspatentregistrationfee() {
			return itemspatentregistrationfee;
		}
		public void setItemspatentregistrationfee(String itemspatentregistrationfee) {
			this.itemspatentregistrationfee = itemspatentregistrationfee;
		}
		public String getCostpatentregistrationfee() {
			return costpatentregistrationfee;
		}
		public void setCostpatentregistrationfee(String costpatentregistrationfee) {
			this.costpatentregistrationfee = costpatentregistrationfee;
		}
		public String getItemstotal() {
			return itemstotal;
		}
		public void setItemstotal(String itemstotal) {
			this.itemstotal = itemstotal;
		}
		public String getCosttotal() {
			return costtotal;
		}
		public void setCosttotal(String costtotal) {
			this.costtotal = costtotal;
		}
	    
}
