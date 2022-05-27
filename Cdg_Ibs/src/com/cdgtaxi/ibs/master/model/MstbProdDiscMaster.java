package com.cdgtaxi.ibs.master.model;
// Generated Jul 20, 2009 6:36:35 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.AmtbApplicationProduct;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.Creatable;
import com.cdgtaxi.ibs.common.model.Updatable;


/**
 * MstbProdDiscMaster generated by hbm2java
 */

public class MstbProdDiscMaster  implements java.io.Serializable, Creatable, Updatable {


    // Fields    

    private Integer productDiscountPlanNo;
    private String productDiscountPlanName;
    private Timestamp createdDt;
    private String createdBy;
    private Timestamp updatedDt;
    private String updatedBy;
    private Integer version;
    private Set<AmtbApplicationProduct> amtbApplicationProducts = new HashSet<AmtbApplicationProduct>(0);
    private Set<MstbProdDiscDetail> mstbProdDiscDetails = new HashSet<MstbProdDiscDetail>(0);
    private Set<AmtbSubscTo> amtbSubscTos = new HashSet<AmtbSubscTo>(0);


    // Constructors

    /** default constructor */
    public MstbProdDiscMaster() {
    }

	/** minimal constructor */
    public MstbProdDiscMaster(String productDiscountPlanName) {
        this.productDiscountPlanName = productDiscountPlanName;
    }
    
    /** full constructor */
    public MstbProdDiscMaster(String productDiscountPlanName, Timestamp createdDt, String createdBy, Timestamp updatedDt, String updatedBy, Integer version, Set<AmtbApplicationProduct> amtbApplicationProducts, Set<MstbProdDiscDetail> mstbProdDiscDetails, Set<AmtbSubscTo> amtbSubscTos) {
        this.productDiscountPlanName = productDiscountPlanName;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
        this.version = version;
        this.amtbApplicationProducts = amtbApplicationProducts;
        this.mstbProdDiscDetails = mstbProdDiscDetails;
        this.amtbSubscTos = amtbSubscTos;
    }
    

   
    // Property accessors

    public Integer getProductDiscountPlanNo() {
        return this.productDiscountPlanNo;
    }
    
    public void setProductDiscountPlanNo(Integer productDiscountPlanNo) {
        this.productDiscountPlanNo = productDiscountPlanNo;
    }

    public String getProductDiscountPlanName() {
        return this.productDiscountPlanName;
    }
    
    public void setProductDiscountPlanName(String productDiscountPlanName) {
        this.productDiscountPlanName = productDiscountPlanName;
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

    public Set<AmtbApplicationProduct> getAmtbApplicationProducts() {
        return this.amtbApplicationProducts;
    }
    
    public void setAmtbApplicationProducts(Set<AmtbApplicationProduct> amtbApplicationProducts) {
        this.amtbApplicationProducts = amtbApplicationProducts;
    }

    public Set<MstbProdDiscDetail> getMstbProdDiscDetails() {
        return this.mstbProdDiscDetails;
    }
    
    public void setMstbProdDiscDetails(Set<MstbProdDiscDetail> mstbProdDiscDetails) {
        this.mstbProdDiscDetails = mstbProdDiscDetails;
    }

    public Set<AmtbSubscTo> getAmtbSubscTos() {
        return this.amtbSubscTos;
    }
    
    public void setAmtbSubscTos(Set<AmtbSubscTo> amtbSubscTos) {
        this.amtbSubscTos = amtbSubscTos;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("productDiscountPlanName").append("='").append(getProductDiscountPlanName()).append("' ");
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MstbProdDiscMaster) ) return false;
		 MstbProdDiscMaster castOther = ( MstbProdDiscMaster ) other; 
         
		 return ( (this.getProductDiscountPlanNo()==castOther.getProductDiscountPlanNo()) || ( this.getProductDiscountPlanNo()!=null && castOther.getProductDiscountPlanNo()!=null && this.getProductDiscountPlanNo().equals(castOther.getProductDiscountPlanNo()) ) )
 && ( (this.getProductDiscountPlanName()==castOther.getProductDiscountPlanName()) || ( this.getProductDiscountPlanName()!=null && castOther.getProductDiscountPlanName()!=null && this.getProductDiscountPlanName().equals(castOther.getProductDiscountPlanName()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductDiscountPlanNo() == null ? 0 : this.getProductDiscountPlanNo().hashCode() );
         result = 37 * result + ( getProductDiscountPlanName() == null ? 0 : this.getProductDiscountPlanName().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         return result;
   }   

}
