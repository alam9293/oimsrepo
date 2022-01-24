package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
@IdClass(Identifier.class)
public class DocumentsReeferVehicles implements Serializable {

	 @Id
	    private String unit_id;
	    @Id
	    private String control_id;
	    @Id
	    private Identifier id;
	    @Column(name = "selfnbspattesteddetailedprojectreportnbsp_data")
	    private byte[] selfnbspattesteddetailedprojectreportnbspData;
	    @Column(name = "selfnbspattesteddetailedprojectreportnbsp")
	    private String selfnbspattesteddetailedprojectreportnbsp;
	    @Column(name = "termloansanctionletter_data")
	    private byte[] termloansanctionletterData;
	    @Column(name = "termloansanctionletter")
	    private String termloansanctionletter;
	    @Column(name = "bankappraisalreport_data")
	    private byte[] bankappraisalreportData;
	    @Column(name = "bankappraisalreport")
	    private String bankappraisalreport;
	    @Column(name = "incorporationcertificateoffirm_data")
	    private byte[] incorporationcertificateoffirmData;
	    @Column(name = "incorporationcertificateoffirm")
	    private String incorporationcertificateoffirm;
	    @Column(name = "partnershipdeedbylaws_data")
	    private byte[] partnershipdeedbylawsData;
	    @Column(name = "partnershipdeedbylaws")
	    private String partnershipdeedbylaws;
	    @Column(name = "copyofdeliverychallanampreceiptofchassisbodyforreefervehicles_data")
	    private byte[] copyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData;
	    @Column(name = "copyofdeliverychallanampreceiptofchassisbodyforreefervehicles")
	    private String copyofdeliverychallanampreceiptofchassisbodyforreefervehicles;
	    @Column(name = "billboucherinvoicescertifiedbycharteredengineer_data")
	    private byte[] billboucherinvoicescertifiedbycharteredengineerData;
	    @Column(name = "billboucherinvoicescertifiedbycharteredengineer")
	    private String billboucherinvoicescertifiedbycharteredengineer;
	    @Column(name = "invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf_data")
	    private byte[] invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData;
	    @Column(name = "invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf")
	    private String invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf;
	    @Column(name = "repaymentscheduleofnbsptermloanforinterestbybankfinancialinst_data")
	    private byte[] repaymentscheduleofnbsptermloanforinterestbybankfinancialinstData;
	    @Column(name = "repaymentscheduleofnbsptermloanforinterestbybankfinancialinst")
	    private String repaymentscheduleofnbsptermloanforinterestbybankfinancialinst;
	    @Column(name = "affidavitonnonjudicialstamppaperofrs100data")
	    private byte[] affidavitonnonjudicialstamppaperofrs100Data;
	    @Column(name = "affidavitonnonjudicialstamppaperofrs100")
	    private String affidavitonnonjudicialstamppaperofrs100;
	    @Column(name = "bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling_data")
	    private byte[] bankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData;
	    @Column(name = "bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling")
	    private String bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling;
	    @Column(name = "technicalspecificationdetailsofnbspreefervehiclemobileprecool_data")
	    private byte[] technicalspecificationdetailsofnbspreefervehiclemobileprecoolData;
	    @Column(name = "technicalspecificationdetailsofnbspreefervehiclemobileprecool")
	    private String technicalspecificationdetailsofnbspreefervehiclemobileprecool;
	
	    
	    
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
		public Identifier getId() {
			return id;
		}
		public void setId(Identifier id) {
			this.id = id;
		}
		public byte[] getSelfnbspattesteddetailedprojectreportnbspData() {
			return selfnbspattesteddetailedprojectreportnbspData;
		}
		public void setSelfnbspattesteddetailedprojectreportnbspData(byte[] selfnbspattesteddetailedprojectreportnbspData) {
			this.selfnbspattesteddetailedprojectreportnbspData = selfnbspattesteddetailedprojectreportnbspData;
		}
		public String getSelfnbspattesteddetailedprojectreportnbsp() {
			return selfnbspattesteddetailedprojectreportnbsp;
		}
		public void setSelfnbspattesteddetailedprojectreportnbsp(String selfnbspattesteddetailedprojectreportnbsp) {
			this.selfnbspattesteddetailedprojectreportnbsp = selfnbspattesteddetailedprojectreportnbsp;
		}
		public byte[] getTermloansanctionletterData() {
			return termloansanctionletterData;
		}
		public void setTermloansanctionletterData(byte[] termloansanctionletterData) {
			this.termloansanctionletterData = termloansanctionletterData;
		}
		public String getTermloansanctionletter() {
			return termloansanctionletter;
		}
		public void setTermloansanctionletter(String termloansanctionletter) {
			this.termloansanctionletter = termloansanctionletter;
		}
		public byte[] getBankappraisalreportData() {
			return bankappraisalreportData;
		}
		public void setBankappraisalreportData(byte[] bankappraisalreportData) {
			this.bankappraisalreportData = bankappraisalreportData;
		}
		public String getBankappraisalreport() {
			return bankappraisalreport;
		}
		public void setBankappraisalreport(String bankappraisalreport) {
			this.bankappraisalreport = bankappraisalreport;
		}
		public byte[] getIncorporationcertificateoffirmData() {
			return incorporationcertificateoffirmData;
		}
		public void setIncorporationcertificateoffirmData(byte[] incorporationcertificateoffirmData) {
			this.incorporationcertificateoffirmData = incorporationcertificateoffirmData;
		}
		public String getIncorporationcertificateoffirm() {
			return incorporationcertificateoffirm;
		}
		public void setIncorporationcertificateoffirm(String incorporationcertificateoffirm) {
			this.incorporationcertificateoffirm = incorporationcertificateoffirm;
		}
		public byte[] getPartnershipdeedbylawsData() {
			return partnershipdeedbylawsData;
		}
		public void setPartnershipdeedbylawsData(byte[] partnershipdeedbylawsData) {
			this.partnershipdeedbylawsData = partnershipdeedbylawsData;
		}
		public String getPartnershipdeedbylaws() {
			return partnershipdeedbylaws;
		}
		public void setPartnershipdeedbylaws(String partnershipdeedbylaws) {
			this.partnershipdeedbylaws = partnershipdeedbylaws;
		}
		public byte[] getCopyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData() {
			return copyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData;
		}
		public void setCopyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData(
				byte[] copyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData) {
			this.copyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData = copyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData;
		}
		public String getCopyofdeliverychallanampreceiptofchassisbodyforreefervehicles() {
			return copyofdeliverychallanampreceiptofchassisbodyforreefervehicles;
		}
		public void setCopyofdeliverychallanampreceiptofchassisbodyforreefervehicles(
				String copyofdeliverychallanampreceiptofchassisbodyforreefervehicles) {
			this.copyofdeliverychallanampreceiptofchassisbodyforreefervehicles = copyofdeliverychallanampreceiptofchassisbodyforreefervehicles;
		}
		public byte[] getBillboucherinvoicescertifiedbycharteredengineerData() {
			return billboucherinvoicescertifiedbycharteredengineerData;
		}
		public void setBillboucherinvoicescertifiedbycharteredengineerData(
				byte[] billboucherinvoicescertifiedbycharteredengineerData) {
			this.billboucherinvoicescertifiedbycharteredengineerData = billboucherinvoicescertifiedbycharteredengineerData;
		}
		public String getBillboucherinvoicescertifiedbycharteredengineer() {
			return billboucherinvoicescertifiedbycharteredengineer;
		}
		public void setBillboucherinvoicescertifiedbycharteredengineer(String billboucherinvoicescertifiedbycharteredengineer) {
			this.billboucherinvoicescertifiedbycharteredengineer = billboucherinvoicescertifiedbycharteredengineer;
		}
		public byte[] getInvoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData() {
			return invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData;
		}
		public void setInvoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData(
				byte[] invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData) {
			this.invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData = invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData;
		}
		public String getInvoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf() {
			return invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf;
		}
		public void setInvoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf(
				String invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf) {
			this.invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf = invoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf;
		}
		public byte[] getRepaymentscheduleofnbsptermloanforinterestbybankfinancialinstData() {
			return repaymentscheduleofnbsptermloanforinterestbybankfinancialinstData;
		}
		public void setRepaymentscheduleofnbsptermloanforinterestbybankfinancialinstData(
				byte[] repaymentscheduleofnbsptermloanforinterestbybankfinancialinstData) {
			this.repaymentscheduleofnbsptermloanforinterestbybankfinancialinstData = repaymentscheduleofnbsptermloanforinterestbybankfinancialinstData;
		}
		public String getRepaymentscheduleofnbsptermloanforinterestbybankfinancialinst() {
			return repaymentscheduleofnbsptermloanforinterestbybankfinancialinst;
		}
		public void setRepaymentscheduleofnbsptermloanforinterestbybankfinancialinst(
				String repaymentscheduleofnbsptermloanforinterestbybankfinancialinst) {
			this.repaymentscheduleofnbsptermloanforinterestbybankfinancialinst = repaymentscheduleofnbsptermloanforinterestbybankfinancialinst;
		}
		public byte[] getAffidavitonnonjudicialstamppaperofrs100Data() {
			return affidavitonnonjudicialstamppaperofrs100Data;
		}
		public void setAffidavitonnonjudicialstamppaperofrs100Data(byte[] affidavitonnonjudicialstamppaperofrs100Data) {
			this.affidavitonnonjudicialstamppaperofrs100Data = affidavitonnonjudicialstamppaperofrs100Data;
		}
		public String getAffidavitonnonjudicialstamppaperofrs100() {
			return affidavitonnonjudicialstamppaperofrs100;
		}
		public void setAffidavitonnonjudicialstamppaperofrs100(String affidavitonnonjudicialstamppaperofrs100) {
			this.affidavitonnonjudicialstamppaperofrs100 = affidavitonnonjudicialstamppaperofrs100;
		}
		public byte[] getBankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData() {
			return bankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData;
		}
		public void setBankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData(
				byte[] bankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData) {
			this.bankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData = bankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData;
		}
		public String getBankclaimnbspcertificatenbspforreefervehiclesmobileprecooling() {
			return bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling;
		}
		public void setBankclaimnbspcertificatenbspforreefervehiclesmobileprecooling(
				String bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling) {
			this.bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling = bankclaimnbspcertificatenbspforreefervehiclesmobileprecooling;
		}
		public byte[] getTechnicalspecificationdetailsofnbspreefervehiclemobileprecoolData() {
			return technicalspecificationdetailsofnbspreefervehiclemobileprecoolData;
		}
		public void setTechnicalspecificationdetailsofnbspreefervehiclemobileprecoolData(
				byte[] technicalspecificationdetailsofnbspreefervehiclemobileprecoolData) {
			this.technicalspecificationdetailsofnbspreefervehiclemobileprecoolData = technicalspecificationdetailsofnbspreefervehiclemobileprecoolData;
		}
		public String getTechnicalspecificationdetailsofnbspreefervehiclemobileprecool() {
			return technicalspecificationdetailsofnbspreefervehiclemobileprecool;
		}
		public void setTechnicalspecificationdetailsofnbspreefervehiclemobileprecool(
				String technicalspecificationdetailsofnbspreefervehiclemobileprecool) {
			this.technicalspecificationdetailsofnbspreefervehiclemobileprecool = technicalspecificationdetailsofnbspreefervehiclemobileprecool;
		}
	    
	    
	
}
