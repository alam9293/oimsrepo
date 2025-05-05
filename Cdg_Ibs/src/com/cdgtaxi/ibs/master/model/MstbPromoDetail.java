package com.cdgtaxi.ibs.master.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.acl.security.Auditable;
import com.cdgtaxi.ibs.common.model.PmtbProductType;


@SuppressWarnings("serial")
public class MstbPromoDetail  implements java.io.Serializable, Auditable {

    // Fields    
    private Integer promoDetailNo;
    private String name;
    private String type;
    private String promoType;
    private BigDecimal promoValue;
    private Timestamp effectiveDtFrom;
    private Timestamp effectiveDtTo;
    private Date effectiveCutoffDate; 
    private Integer version;
    private PmtbProductType pmtbProductType;
    private MstbMasterTable mstbMasterTableByJobType;
    private MstbMasterTable mstbMasterTableByVehicleModel;
    private MstbPromotion mstbPromotion;
    // Constructors

    /** default constructor */
    public MstbPromoDetail() {
    }

	/** minimal constructor */
    public MstbPromoDetail(String name, String type, String promoType, BigDecimal promoValue) {
        this.name = name;
        this.type = type;
        this.promoType = promoType;
        this.promoValue = promoValue;
    }
    
    /** full constructor */
    public MstbPromoDetail(String name, String type, String promoType, BigDecimal promoValue, Timestamp effectiveDtFrom, Timestamp effectiveDtTo, Date effectiveCutoffDate,  Integer version, PmtbProductType pmtbProductType, MstbMasterTable mstbMasterTableByJobType, MstbMasterTable mstbMasterTableByVehicleModel, MstbPromotion mstbPromotion) {
        this.name = name;
        this.type = type;
        this.promoType = promoType;
        this.promoValue = promoValue;
        this.effectiveDtFrom = effectiveDtFrom;
        this.effectiveDtTo = effectiveDtTo;
        this.effectiveCutoffDate = effectiveCutoffDate;
    	
        this.version = version;
        this.pmtbProductType = pmtbProductType;
        this.mstbMasterTableByJobType = mstbMasterTableByJobType;
        this.mstbMasterTableByVehicleModel = mstbMasterTableByVehicleModel;
        this.mstbPromotion = mstbPromotion;
    }
    

   
    // Property accessors

    public Integer getPromoDetailNo() {
        return this.promoDetailNo;
    }
    
    public void setPromoDetailNo(Integer promoDetailNo) {
        this.promoDetailNo = promoDetailNo;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getPromoType() {
        return this.promoType;
    }
    
    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public BigDecimal getPromoValue() {
        return this.promoValue;
    }
    
    public void setPromoValue(BigDecimal promoValue) {
        this.promoValue = promoValue;
    }

    public Timestamp getEffectiveDtFrom() {
        return this.effectiveDtFrom;
    }
    
    public void setEffectiveDtFrom(Timestamp effectiveDtFrom) {
        this.effectiveDtFrom = effectiveDtFrom;
    }

    public Timestamp getEffectiveDtTo() {
        return this.effectiveDtTo;
    }
    
    public void setEffectiveDtTo(Timestamp effectiveDtTo) {
        this.effectiveDtTo = effectiveDtTo;
    }

    public Date getEffectiveCutoffDate() {
		return effectiveCutoffDate;
	}

	public void setEffectiveCutoffDate(Date effectiveCutoffDate) {
		this.effectiveCutoffDate = effectiveCutoffDate;
	}
	

	
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public PmtbProductType getPmtbProductType() {
        return this.pmtbProductType;
    }
    
    public void setPmtbProductType(PmtbProductType pmtbProductType) {
        this.pmtbProductType = pmtbProductType;
    }

    public MstbMasterTable getMstbMasterTableByJobType() {
        return this.mstbMasterTableByJobType;
    }
    
    public void setMstbMasterTableByJobType(MstbMasterTable mstbMasterTableByJobType) {
        this.mstbMasterTableByJobType = mstbMasterTableByJobType;
    }

    public MstbMasterTable getMstbMasterTableByVehicleModel() {
        return this.mstbMasterTableByVehicleModel;
    }
    
    public void setMstbMasterTableByVehicleModel(MstbMasterTable mstbMasterTableByVehicleModel) {
        this.mstbMasterTableByVehicleModel = mstbMasterTableByVehicleModel;
    }
    
    public MstbPromotion getMstbPromotion() {
		return mstbPromotion;
	}

	public void setMstbPromotion(MstbPromotion mstbPromotion) {
		this.mstbPromotion = mstbPromotion;
	}

	/**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("name").append("='").append(getName()).append("' ");			
      buffer.append("type").append("='").append(getType()).append("' ");			
      buffer.append("promoType").append("='").append(getPromoType()).append("' ");			
      buffer.append("promoValue").append("='").append(getPromoValue()).append("' ");			
      buffer.append("effectiveDtFrom").append("='").append(getEffectiveDtFrom()).append("' ");			
      buffer.append("effectiveDtTo").append("='").append(getEffectiveDtTo()).append("' ");
      buffer.append("effectiveCutoffDate").append("='").append(getEffectiveCutoffDate()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MstbPromotion) ) return false;
		 MstbPromoDetail castOther = ( MstbPromoDetail ) other; 
         
		 return ( (this.getPromoDetailNo()==castOther.getPromoDetailNo()) || ( this.getPromoDetailNo()!=null && castOther.getPromoDetailNo()!=null && this.getPromoDetailNo().equals(castOther.getPromoDetailNo()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) )
 && ( (this.getPromoType()==castOther.getPromoType()) || ( this.getPromoType()!=null && castOther.getPromoType()!=null && this.getPromoType().equals(castOther.getPromoType()) ) )
 && ( (this.getPromoValue()==castOther.getPromoValue()) || ( this.getPromoValue()!=null && castOther.getPromoValue()!=null && this.getPromoValue().equals(castOther.getPromoValue()) ) )
 && ( (this.getEffectiveDtFrom()==castOther.getEffectiveDtFrom()) || ( this.getEffectiveDtFrom()!=null && castOther.getEffectiveDtFrom()!=null && this.getEffectiveDtFrom().equals(castOther.getEffectiveDtFrom()) ) )
 && ( (this.getEffectiveDtTo()==castOther.getEffectiveDtTo()) || ( this.getEffectiveDtTo()!=null && castOther.getEffectiveDtTo()!=null && this.getEffectiveDtTo().equals(castOther.getEffectiveDtTo()) ) )
 && ( (this.getEffectiveCutoffDate()==castOther.getEffectiveCutoffDate()) || ( this.getEffectiveCutoffDate()!=null && castOther.getEffectiveCutoffDate()!=null && this.getEffectiveCutoffDate().equals(castOther.getEffectiveCutoffDate()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getPmtbProductType()==castOther.getPmtbProductType()) || ( this.getPmtbProductType()!=null && castOther.getPmtbProductType()!=null && this.getPmtbProductType().equals(castOther.getPmtbProductType()) ) )
 && ( (this.getMstbMasterTableByJobType()==castOther.getMstbMasterTableByJobType()) || ( this.getMstbMasterTableByJobType()!=null && castOther.getMstbMasterTableByJobType()!=null && this.getMstbMasterTableByJobType().equals(castOther.getMstbMasterTableByJobType()) ) )
 && ( (this.getMstbMasterTableByVehicleModel()==castOther.getMstbMasterTableByVehicleModel()) || ( this.getMstbMasterTableByVehicleModel()!=null && castOther.getMstbMasterTableByVehicleModel()!=null && this.getMstbMasterTableByVehicleModel().equals(castOther.getMstbMasterTableByVehicleModel()) ) );
		 
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPromoDetailNo() == null ? 0 : this.getPromoDetailNo().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         result = 37 * result + ( getPromoType() == null ? 0 : this.getPromoType().hashCode() );
         result = 37 * result + ( getPromoValue() == null ? 0 : this.getPromoValue().hashCode() );
         result = 37 * result + ( getEffectiveDtFrom() == null ? 0 : this.getEffectiveDtFrom().hashCode() );
         result = 37 * result + ( getEffectiveDtTo() == null ? 0 : this.getEffectiveDtTo().hashCode() );
         result = 37 * result + ( getEffectiveCutoffDate() == null ? 0 : this.getEffectiveCutoffDate().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         
//         result = 37 * result + ( getPmtbProductType() == null ? 0 : this.getPmtbProductType().hashCode() );
//         result = 37 * result + ( getMstbMasterTableByJobType() == null ? 0 : this.getMstbMasterTableByJobType().hashCode() );
//         result = 37 * result + ( getMstbMasterTableByVehicleModel() == null ? 0 : this.getMstbMasterTableByVehicleModel().hashCode() );

         return result;
   }   





}
