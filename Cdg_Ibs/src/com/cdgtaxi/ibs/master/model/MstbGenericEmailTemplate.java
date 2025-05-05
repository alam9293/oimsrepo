package com.cdgtaxi.ibs.master.model;

import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;
import com.cdgtaxi.ibs.common.model.Creatable;
import com.cdgtaxi.ibs.common.model.Updatable;

@SuppressWarnings("serial")
public class MstbGenericEmailTemplate implements java.io.Serializable, Creatable, Auditable, Updatable {

	// Fields

	private String name;
	private String content;
	private Timestamp createdDt;
	private String createdBy;
	private Timestamp updatedDt;
	private String updatedBy;
	private Integer version;

	// Constructors

	/** default constructor */
	public MstbGenericEmailTemplate() {
	}

	/** full constructor */
	public MstbGenericEmailTemplate(String name, String content, Timestamp createdDt, String createdBy,
			Timestamp updatedDt, String updatedBy, Integer version) {
		super();
		this.name = name;
		this.content = content;
		this.createdDt = createdDt;
		this.createdBy = createdBy;
		this.updatedDt = updatedDt;
		this.updatedBy = updatedBy;
		this.version = version;
	}

	// Property accessors

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedDt() {
		return this.updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * toString
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("name").append("='").append(getName()).append("' ");
		buffer.append("content").append("='").append(getContent()).append("' ");
		buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");
		buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");
		buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");
		buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");
		buffer.append("version").append("='").append(getVersion()).append("' ");
		buffer.append("]");

		return buffer.toString();
	}


	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MstbGenericEmailTemplate))
			return false;
		MstbGenericEmailTemplate castOther = ( MstbGenericEmailTemplate ) other; 
         
		 return ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getContent()==castOther.getContent()) || ( this.getContent()!=null && castOther.getContent()!=null && this.getContent().equals(castOther.getContent()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   }
   
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result + (getContent() == null ? 0 : this.getContent().hashCode());
		result = 37 * result + (getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode());
		result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
		result = 37 * result + (getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode());
		result = 37 * result + (getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode());
		result = 37 * result + (getVersion() == null ? 0 : this.getVersion().hashCode());
		return result;
	}
}
