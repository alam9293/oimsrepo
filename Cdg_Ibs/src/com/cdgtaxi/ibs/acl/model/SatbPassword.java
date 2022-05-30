package com.cdgtaxi.ibs.acl.model;
// Generated Mar 18, 2009 1:50:21 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;


/**
 * SatbPassword generated by hbm2java
 */

public class SatbPassword  implements java.io.Serializable {


    // Fields    

    private Long passwordId;
    private String password;
    private Timestamp time;
    private SatbUser satbUser;


    // Constructors

    /** default constructor */
    public SatbPassword() {
    }

	/** minimal constructor */
    public SatbPassword(Timestamp time) {
        this.time = time;
    }
    
    /** full constructor */
    public SatbPassword(String password, Timestamp time, SatbUser satbUser) {
        this.password = password;
        this.time = time;
        this.satbUser = satbUser;
    }
    

   
    // Property accessors

    public Long getPasswordId() {
        return this.passwordId;
    }
    
    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getTime() {
        return this.time;
    }
    
    public void setTime(Timestamp time) {
        this.time = time;
    }

    public SatbUser getSatbUser() {
        return this.satbUser;
    }
    
    public void setSatbUser(SatbUser satbUser) {
        this.satbUser = satbUser;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("password").append("='").append(getPassword()).append("' ");			
      buffer.append("time").append("='").append(getTime()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SatbPassword) ) return false;
		 SatbPassword castOther = ( SatbPassword ) other; 
         
		 return ( (this.getPasswordId()==castOther.getPasswordId()) || ( this.getPasswordId()!=null && castOther.getPasswordId()!=null && this.getPasswordId().equals(castOther.getPasswordId()) ) )
 && ( (this.getPassword()==castOther.getPassword()) || ( this.getPassword()!=null && castOther.getPassword()!=null && this.getPassword().equals(castOther.getPassword()) ) )
 && ( (this.getTime()==castOther.getTime()) || ( this.getTime()!=null && castOther.getTime()!=null && this.getTime().equals(castOther.getTime()) ) )
 && ( (this.getSatbUser()==castOther.getSatbUser()) || ( this.getSatbUser()!=null && castOther.getSatbUser()!=null && this.getSatbUser().equals(castOther.getSatbUser()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPasswordId() == null ? 0 : this.getPasswordId().hashCode() );
         result = 37 * result + ( getPassword() == null ? 0 : this.getPassword().hashCode() );
         result = 37 * result + ( getTime() == null ? 0 : this.getTime().hashCode() );
         result = 37 * result + ( getSatbUser() == null ? 0 : this.getSatbUser().hashCode() );
         return result;
   }   





}
