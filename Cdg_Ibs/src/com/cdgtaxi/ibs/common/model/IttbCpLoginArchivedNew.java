/**
 * This class is Login Archived Model
 * 			- deleted login/access id are to be stored on this table
 * 
 * @author jtaruc
 * @version 07/09/2012
 *
 * Modification History
 *
 * Modified by    Date            Description:
 * -----------    -----------     -----------
 * 
 */

package com.cdgtaxi.ibs.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IttbCpLoginArchivedNew implements Serializable {

    // Fields    
	
    private Long archivedNo;
    private String accessId;
    private BigDecimal productNo;
    private Integer contactPersonNo;   
    private String loginMethod;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;
        
    // 	Constructors
    
    /** default constructor */
    public IttbCpLoginArchivedNew(){    
    }
    
    /** full constructor */
    public IttbCpLoginArchivedNew(Long archivedNo, String accessId, String loginMethod, BigDecimal productNo, Integer contactPersonNo, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt) {
        this.archivedNo = archivedNo;
        this.accessId = accessId;
        this.loginMethod = loginMethod;
        this.productNo = productNo;
        this.contactPersonNo = contactPersonNo;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.updatedBy = updatedBy;
        this.updatedDt = updatedDt;
    }

	public Long getArchivedNo() {
		return archivedNo;
	}

	public void setArchivedNo(Long archivedNo) {
		this.archivedNo = archivedNo;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public BigDecimal getProductNo() {
		return productNo;
	}

	public void setProductNo(BigDecimal productNo) {
		this.productNo = productNo;
	}

	public Integer getContactPersonNo() {
		return contactPersonNo;
	}

	public void setContactPersonNo(Integer contactPersonNo) {
		this.contactPersonNo = contactPersonNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}
 
	/**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("archivedNo").append("='").append(getArchivedNo()).append("' ");		
      buffer.append("accessId").append("='").append(getAccessId()).append("' ");
      buffer.append("loginMethod").append("='").append(getLoginMethod()).append("' ");
      buffer.append("productNo").append("='").append(getProductNo()).append("' ");
      buffer.append("contactPersonNo").append("='").append(getContactPersonNo()).append("' ");
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((archivedNo == null) ? 0 : archivedNo.hashCode());
		result = prime * result
				+ ((contactPersonNo == null) ? 0 : contactPersonNo.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDt == null) ? 0 : createdDt.hashCode());
		result = prime * result + ((accessId == null) ? 0 : accessId.hashCode());
		result = prime * result
				+ ((loginMethod == null) ? 0 : loginMethod.hashCode());
		result = prime * result
				+ ((productNo == null) ? 0 : productNo.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDt == null) ? 0 : updatedDt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IttbCpLoginArchivedNew))
			return false;
		IttbCpLoginArchivedNew other = (IttbCpLoginArchivedNew) obj;
		if (archivedNo == null) {
			if (other.archivedNo != null)
				return false;
		} else if (!archivedNo.equals(other.archivedNo))
			return false;
		if (contactPersonNo == null) {
			if (other.contactPersonNo != null)
				return false;
		} else if (!contactPersonNo.equals(other.contactPersonNo))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDt == null) {
			if (other.createdDt != null)
				return false;
		} else if (!createdDt.equals(other.createdDt))
			return false;
		if (accessId == null) {
			if (other.accessId != null)
				return false;
		} else if (!accessId.equals(other.accessId))
			return false;
		if (loginMethod == null) {
			if (other.loginMethod != null)
				return false;
		} else if (!loginMethod.equals(other.loginMethod))
			return false;
		if (productNo == null) {
			if (other.productNo != null)
				return false;
		} else if (!productNo.equals(other.productNo))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDt == null) {
			if (other.updatedDt != null)
				return false;
		} else if (!updatedDt.equals(other.updatedDt))
			return false;
		return true;
	}
	
}
