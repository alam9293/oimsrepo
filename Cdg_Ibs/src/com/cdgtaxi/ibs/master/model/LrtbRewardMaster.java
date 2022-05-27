package com.cdgtaxi.ibs.master.model;
// Generated Mar 27, 2009 6:34:13 PM by Hibernate Tools 3.1.0.beta4

import java.util.HashSet;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.AmtbApplicationProduct;


/**
 * LrtbRewardMaster generated by hbm2java
 */

public class LrtbRewardMaster  implements java.io.Serializable {


    // Fields    

    private Integer rewardPlanNo;
    private String rewardPlanName;
    private Integer version;
    private Set<AmtbApplicationProduct> amtbApplicationProducts = new HashSet<AmtbApplicationProduct>(0);
    private Set<LrtbRewardDetail> lrtbRewardDetails = new HashSet<LrtbRewardDetail>(0);


    // Constructors

    /** default constructor */
    public LrtbRewardMaster() {
    }

	/** minimal constructor */
    public LrtbRewardMaster(String rewardPlanName) {
        this.rewardPlanName = rewardPlanName;
    }
    
    /** full constructor */
    public LrtbRewardMaster(String rewardPlanName, Integer version, Set<AmtbApplicationProduct> amtbApplicationProducts, Set<LrtbRewardDetail> lrtbRewardDetails) {
        this.rewardPlanName = rewardPlanName;
        this.version = version;
        this.amtbApplicationProducts = amtbApplicationProducts;
        this.lrtbRewardDetails = lrtbRewardDetails;
    }
    

   
    // Property accessors

    public Integer getRewardPlanNo() {
        return this.rewardPlanNo;
    }
    
    public void setRewardPlanNo(Integer rewardPlanNo) {
        this.rewardPlanNo = rewardPlanNo;
    }

    public String getRewardPlanName() {
        return this.rewardPlanName;
    }
    
    public void setRewardPlanName(String rewardPlanName) {
        this.rewardPlanName = rewardPlanName;
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

    public Set<LrtbRewardDetail> getLrtbRewardDetails() {
        return this.lrtbRewardDetails;
    }
    
    public void setLrtbRewardDetails(Set<LrtbRewardDetail> lrtbRewardDetails) {
        this.lrtbRewardDetails = lrtbRewardDetails;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("rewardPlanName").append("='").append(getRewardPlanName()).append("' ");			
      buffer.append("version").append("='").append(getVersion()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LrtbRewardMaster) ) return false;
		 LrtbRewardMaster castOther = ( LrtbRewardMaster ) other; 
         
		 return ( (this.getRewardPlanNo()==castOther.getRewardPlanNo()) || ( this.getRewardPlanNo()!=null && castOther.getRewardPlanNo()!=null && this.getRewardPlanNo().equals(castOther.getRewardPlanNo()) ) )
 && ( (this.getRewardPlanName()==castOther.getRewardPlanName()) || ( this.getRewardPlanName()!=null && castOther.getRewardPlanName()!=null && this.getRewardPlanName().equals(castOther.getRewardPlanName()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRewardPlanNo() == null ? 0 : this.getRewardPlanNo().hashCode() );
         result = 37 * result + ( getRewardPlanName() == null ? 0 : this.getRewardPlanName().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         
         
         return result;
   }   





}
