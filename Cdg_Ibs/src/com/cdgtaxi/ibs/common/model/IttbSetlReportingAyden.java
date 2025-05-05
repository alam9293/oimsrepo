package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class IttbSetlReportingAyden extends BaseEntity{

    BigDecimal id;
    String pspRefNo;
    String merchantRefNo;
    String paymentMethod;
    Date creationDate;
    String type;
    String modificationRefNo;
    String grossCurrency;
    BigDecimal grossDebit;
    BigDecimal grossCredit;
    BigDecimal exchangeRate;
    String netCurrency;
    BigDecimal netDebit;
    BigDecimal netCredit;
    BigDecimal commission;
    BigDecimal markup;
    BigDecimal schemeFees;
    BigDecimal interchange;
    String paymentMethodVariant;
    String modificationMerchantRefNo;
    String batchNo;
    String fileName;
    Date uploadDate;

    public IttbSetlReportingAyden() {
    }

    public IttbSetlReportingAyden(BigDecimal id, String pspRefNo, String merchantRefNo, String paymentMethod, Date creationDate, String type, String modificationRefNo, String grossCurrency, BigDecimal grossDebit, BigDecimal grossCredit, BigDecimal exchangeRate, String netCurrency, BigDecimal netDebit, BigDecimal netCredit, BigDecimal commission, BigDecimal markup, BigDecimal schemeFees, BigDecimal interchange, String paymentMethodVariant, String modificationMerchantRefNo, String batchNo, String fileName, Date uploadDate) {
        this.id = id;
        this.pspRefNo = pspRefNo;
        this.merchantRefNo = merchantRefNo;
        this.paymentMethod = paymentMethod;
        this.creationDate = creationDate;
        this.type = type;
        this.modificationRefNo = modificationRefNo;
        this.grossCurrency = grossCurrency;
        this.grossDebit = grossDebit;
        this.grossCredit = grossCredit;
        this.exchangeRate = exchangeRate;
        this.netCurrency = netCurrency;
        this.netDebit = netDebit;
        this.netCredit = netCredit;
        this.commission = commission;
        this.markup = markup;
        this.schemeFees = schemeFees;
        this.interchange = interchange;
        this.paymentMethodVariant = paymentMethodVariant;
        this.modificationMerchantRefNo = modificationMerchantRefNo;
        this.batchNo = batchNo;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPspRefNo() {
        return pspRefNo;
    }

    public void setPspRefNo(String pspRefNo) {
        this.pspRefNo = pspRefNo;
    }

    public String getMerchantRefNo() {
        return merchantRefNo;
    }

    public void setMerchantRefNo(String merchantRefNo) {
        this.merchantRefNo = merchantRefNo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModificationRefNo() {
        return modificationRefNo;
    }

    public void setModificationRefNo(String modificationRefNo) {
        this.modificationRefNo = modificationRefNo;
    }

    public String getGrossCurrency() {
        return grossCurrency;
    }

    public void setGrossCurrency(String grossCurrency) {
        this.grossCurrency = grossCurrency;
    }

    public BigDecimal getGrossDebit() {
        return grossDebit;
    }

    public void setGrossDebit(BigDecimal grossDebit) {
        this.grossDebit = grossDebit;
    }

    public BigDecimal getGrossCredit() {
        return grossCredit;
    }

    public void setGrossCredit(BigDecimal grossCredit) {
        this.grossCredit = grossCredit;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getNetCurrency() {
        return netCurrency;
    }

    public void setNetCurrency(String netCurrency) {
        this.netCurrency = netCurrency;
    }

    public BigDecimal getNetDebit() {
        return netDebit;
    }

    public void setNetDebit(BigDecimal netDebit) {
        this.netDebit = netDebit;
    }

    public BigDecimal getNetCredit() {
        return netCredit;
    }

    public void setNetCredit(BigDecimal netCredit) {
        this.netCredit = netCredit;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getMarkup() {
        return markup;
    }

    public void setMarkup(BigDecimal markup) {
        this.markup = markup;
    }

    public BigDecimal getSchemeFees() {
        return schemeFees;
    }

    public void setSchemeFees(BigDecimal schemeFees) {
        this.schemeFees = schemeFees;
    }

    public BigDecimal getInterchange() {
        return interchange;
    }

    public void setInterchange(BigDecimal interchange) {
        this.interchange = interchange;
    }

    public String getPaymentMethodVariant() {
        return paymentMethodVariant;
    }

    public void setPaymentMethodVariant(String paymentMethodVariant) {
        this.paymentMethodVariant = paymentMethodVariant;
    }

    public String getModificationMerchantRefNo() {
        return modificationMerchantRefNo;
    }

    public void setModificationMerchantRefNo(String modificationMerchantRefNo) {
        this.modificationMerchantRefNo = modificationMerchantRefNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
