package com.cdgtaxi.ibs.acl.model;
// Generated Mar 18, 2009 1:50:22 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;


/**
 * SatbSession generated by hbm2java
 */

public class SatbSession  implements java.io.Serializable {


    // Fields    

    private Long sessionId;
    private String loginId;
    private Timestamp loginTime;
    private String ipAddr;
    private String status;
    private Timestamp lastActivityTime;
    private String sessionKey;


    // Constructors

    /** default constructor */
    public SatbSession() {
    }

	/** minimal constructor */
    public SatbSession(String loginId) {
        this.loginId = loginId;
    }
    
    /** full constructor */
    public SatbSession(String loginId, Timestamp loginTime, String ipAddr, String status, Timestamp lastActivityTime, String sessionKey) {
        this.loginId = loginId;
        this.loginTime = loginTime;
        this.ipAddr = ipAddr;
        this.status = status;
        this.lastActivityTime = lastActivityTime;
        this.sessionKey = sessionKey;
    }
    

   
    // Property accessors

    public Long getSessionId() {
        return this.sessionId;
    }
    
    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginId() {
        return this.loginId;
    }
    
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Timestamp getLoginTime() {
        return this.loginTime;
    }
    
    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddr() {
        return this.ipAddr;
    }
    
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastActivityTime() {
        return this.lastActivityTime;
    }
    
    public void setLastActivityTime(Timestamp lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }
    
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
   

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("loginId").append("='").append(getLoginId()).append("' ");			
      buffer.append("loginTime").append("='").append(getLoginTime()).append("' ");			
      buffer.append("ipAddr").append("='").append(getIpAddr()).append("' ");			
      buffer.append("status").append("='").append(getStatus()).append("' ");			
      buffer.append("lastActivityTime").append("='").append(getLastActivityTime()).append("' ");			
      buffer.append("sessionKey").append("='").append(getSessionKey()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SatbSession) ) return false;
		 SatbSession castOther = ( SatbSession ) other; 
         
		 return ( (this.getSessionId()==castOther.getSessionId()) || ( this.getSessionId()!=null && castOther.getSessionId()!=null && this.getSessionId().equals(castOther.getSessionId()) ) )
 && ( (this.getLoginId()==castOther.getLoginId()) || ( this.getLoginId()!=null && castOther.getLoginId()!=null && this.getLoginId().equals(castOther.getLoginId()) ) )
 && ( (this.getLoginTime()==castOther.getLoginTime()) || ( this.getLoginTime()!=null && castOther.getLoginTime()!=null && this.getLoginTime().equals(castOther.getLoginTime()) ) )
 && ( (this.getIpAddr()==castOther.getIpAddr()) || ( this.getIpAddr()!=null && castOther.getIpAddr()!=null && this.getIpAddr().equals(castOther.getIpAddr()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getLastActivityTime()==castOther.getLastActivityTime()) || ( this.getLastActivityTime()!=null && castOther.getLastActivityTime()!=null && this.getLastActivityTime().equals(castOther.getLastActivityTime()) ) )
 && ( (this.getSessionKey()==castOther.getSessionKey()) || ( this.getSessionKey()!=null && castOther.getSessionKey()!=null && this.getSessionKey().equals(castOther.getSessionKey()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSessionId() == null ? 0 : this.getSessionId().hashCode() );
         result = 37 * result + ( getLoginId() == null ? 0 : this.getLoginId().hashCode() );
         result = 37 * result + ( getLoginTime() == null ? 0 : this.getLoginTime().hashCode() );
         result = 37 * result + ( getIpAddr() == null ? 0 : this.getIpAddr().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getLastActivityTime() == null ? 0 : this.getLastActivityTime().hashCode() );
         result = 37 * result + ( getSessionKey() == null ? 0 : this.getSessionKey().hashCode() );
         return result;
   }   





}
