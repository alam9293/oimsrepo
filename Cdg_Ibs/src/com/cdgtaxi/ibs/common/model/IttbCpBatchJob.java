package com.cdgtaxi.ibs.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class IttbCpBatchJob
  implements Serializable
{
  private String batchJobId;
  private String batchJobName;
  private String status;
  private String createdBy;
  private Timestamp createdDt;
  private String updatedBy;
  private Timestamp updatedDt;

  public IttbCpBatchJob()
  {
  }

  public IttbCpBatchJob(String batchJobName, String status, String createdBy, Timestamp createdDt)
  {
    this.batchJobName = batchJobName;
    this.status = status;
    this.createdBy = createdBy;
    this.createdDt = createdDt;
  }

  public IttbCpBatchJob(String batchJobName, String status, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt)
  {
    this.batchJobName = batchJobName;
    this.status = status;
    this.createdBy = createdBy;
    this.createdDt = createdDt;
    this.updatedBy = updatedBy;
    this.updatedDt = updatedDt;
  }

  public String getBatchJobId()
  {
    return this.batchJobId;
  }

  public void setBatchJobId(String batchJobId) {
    this.batchJobId = batchJobId;
  }

  public String getBatchJobName() {
    return this.batchJobName;
  }

  public void setBatchJobName(String batchJobName) {
    this.batchJobName = batchJobName;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Timestamp getCreatedDt() {
    return this.createdDt;
  }

  public void setCreatedDt(Timestamp createdDt) {
    this.createdDt = createdDt;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Timestamp getUpdatedDt() {
    return this.updatedDt;
  }

  public void setUpdatedDt(Timestamp updatedDt) {
    this.updatedDt = updatedDt;
  }

  public String toString()
  {
    StringBuffer buffer = new StringBuffer();

    buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
    buffer.append("batchJobName").append("='").append(getBatchJobName()).append("' ");
    buffer.append("status").append("='").append(getStatus()).append("' ");
    buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");
    buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");
    buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");
    buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");
    buffer.append("]");

    return buffer.toString();
  }

  public boolean equals(Object other)
  {
    if (this == other) return true;
    if (other == null) return false;
    if (!(other instanceof IttbCpBatchJob)) return false;
    IttbCpBatchJob castOther = (IttbCpBatchJob)other;

    return ((getBatchJobId() == castOther.getBatchJobId()) || ((getBatchJobId() != null) && (castOther.getBatchJobId() != null) && (getBatchJobId().equals(castOther.getBatchJobId())))) && 
      ((getBatchJobName() == castOther.getBatchJobName()) || ((getBatchJobName() != null) && (castOther.getBatchJobName() != null) && (getBatchJobName().equals(castOther.getBatchJobName())))) && 
      ((getStatus() == castOther.getStatus()) || ((getStatus() != null) && (castOther.getStatus() != null) && (getStatus().equals(castOther.getStatus())))) && 
      ((getCreatedBy() == castOther.getCreatedBy()) || ((getCreatedBy() != null) && (castOther.getCreatedBy() != null) && (getCreatedBy().equals(castOther.getCreatedBy())))) && 
      ((getCreatedDt() == castOther.getCreatedDt()) || ((getCreatedDt() != null) && (castOther.getCreatedDt() != null) && (getCreatedDt().equals(castOther.getCreatedDt())))) && 
      ((getUpdatedBy() == castOther.getUpdatedBy()) || ((getUpdatedBy() != null) && (castOther.getUpdatedBy() != null) && (getUpdatedBy().equals(castOther.getUpdatedBy())))) && (
      (getUpdatedDt() == castOther.getUpdatedDt()) || ((getUpdatedDt() != null) && (castOther.getUpdatedDt() != null) && (getUpdatedDt().equals(castOther.getUpdatedDt()))));
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (getBatchJobId() == null ? 0 : getBatchJobId().hashCode());
    result = 37 * result + (getBatchJobName() == null ? 0 : getBatchJobName().hashCode());
    result = 37 * result + (getStatus() == null ? 0 : getStatus().hashCode());
    result = 37 * result + (getCreatedBy() == null ? 0 : getCreatedBy().hashCode());
    result = 37 * result + (getCreatedDt() == null ? 0 : getCreatedDt().hashCode());
    result = 37 * result + (getUpdatedBy() == null ? 0 : getUpdatedBy().hashCode());
    result = 37 * result + (getUpdatedDt() == null ? 0 : getUpdatedDt().hashCode());
    return result;
  }
}