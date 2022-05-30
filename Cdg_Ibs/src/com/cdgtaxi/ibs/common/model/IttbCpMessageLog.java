package com.cdgtaxi.ibs.common.model;
import java.sql.Clob;
import java.util.Date;
import java.sql.Timestamp;

public class IttbCpMessageLog  implements java.io.Serializable {


    // Fields    

    private Long LogId;
    private String sender;
    private String receiver;
    private String interfaceType;
    private String sentTime;
    private Clob xmlRequest;
    private Clob xmlResponse;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;

	/** default constructor */
    public IttbCpMessageLog() {
    	createdDt = new Timestamp(new Date().getTime());
    }

    public IttbCpMessageLog(String sender, String receiver, String interfaceType, String sentTime, Clob xmlRequest, Clob xmlResponse, String createdBy, Timestamp createdDt, String updatedBy, Timestamp updatedDt) {
    	this.sender = sender;
    	this.receiver = receiver;
    	this.interfaceType = interfaceType;
    	this.sentTime = sentTime;    	
    	this.xmlRequest = xmlRequest;
    	this.xmlResponse = xmlResponse;
    	this.createdBy = createdBy;
    	this.createdDt = createdDt;
    	this.updatedBy = updatedBy;
    	this.updatedDt = updatedDt;
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

    public String getInterfaceType() {
    	return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
    	this.interfaceType = interfaceType;
    }

    public Long getLogId() {
    	return LogId;
    }

    public void setLogId(Long logId) {
    	LogId = logId;
    }

    public String getReceiver() {
    	return receiver;
    }

    public void setReceiver(String receiver) {
    	this.receiver = receiver;
    }

    public String getSender() {
    	return sender;
    }

    public void setSender(String sender) {
    	this.sender = sender;
    }

    public String getSentTime() {
    	return sentTime;
    }

    public void setSentTime(String sentTime) {
    	this.sentTime = sentTime;
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

    public Clob getXmlRequest() {
    	return xmlRequest;
    }

    public void setXmlRequest(Clob xmlRequest) {
    	this.xmlRequest = xmlRequest;
    }

    public Clob getXmlResponse() {
    	return xmlResponse;
    }

    public void setXmlResponse(Clob xmlResponse) {
    	this.xmlResponse = xmlResponse;
    }
      

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("sender").append("='").append(getSender()).append("' ");			
      buffer.append("receiver").append("='").append(getReceiver()).append("' ");			
      buffer.append("interfaceType").append("='").append(getInterfaceType()).append("' ");
      buffer.append("sentTime").append("='").append(getSentTime()).append("' ");
      buffer.append("xmlRequest").append("='").append(getXmlRequest()).append("' ");			
      buffer.append("xmlResponse").append("='").append(getXmlResponse()).append("' ");	
      buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
      buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
      buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");			
      buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }

     public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IttbCpMessageLog) ) return false;
		 IttbCpMessageLog castOther = ( IttbCpMessageLog ) other; 
         
		 return ( (this.getLogId()==castOther.getLogId()) || ( this.getLogId()!=null && castOther.getLogId()!=null && this.getLogId().equals(castOther.getLogId()) ) )
 && ( (this.getSender()==castOther.getSender()) || ( this.getSender()!=null && castOther.getSender()!=null && this.getSender().equals(castOther.getSender()) ) )
 && ( (this.getReceiver()==castOther.getReceiver()) || ( this.getReceiver()!=null && castOther.getReceiver()!=null && this.getReceiver().equals(castOther.getReceiver()) ) )
 && ( (this.getInterfaceType()==castOther.getInterfaceType()) || ( this.getInterfaceType()!=null && castOther.getInterfaceType()!=null && this.getInterfaceType().equals(castOther.getInterfaceType()) ) )
 && ( (this.getSentTime()==castOther.getSentTime()) || ( this.getSentTime()!=null && castOther.getSentTime()!=null && this.getSentTime().equals(castOther.getSentTime()) ) ) 
 && ( (this.getXmlRequest()==castOther.getXmlRequest()) || ( this.getXmlRequest()!=null && castOther.getXmlRequest()!=null && this.getXmlRequest().equals(castOther.getXmlRequest()) ) )
 && ( (this.getXmlResponse()==castOther.getXmlResponse()) || ( this.getXmlResponse()!=null && castOther.getXmlResponse()!=null && this.getXmlResponse().equals(castOther.getXmlResponse()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLogId() == null ? 0 : this.getLogId().hashCode() );
         result = 37 * result + ( getSender() == null ? 0 : this.getSender().hashCode() );
         result = 37 * result + ( getReceiver() == null ? 0 : this.getReceiver().hashCode() );
         result = 37 * result + ( getInterfaceType() == null ? 0 : this.getInterfaceType().hashCode() );
         result = 37 * result + ( getSentTime() == null ? 0 : this.getSentTime().hashCode() );
         result = 37 * result + ( getXmlRequest() == null ? 0 : this.getXmlRequest().hashCode() );
         result = 37 * result + ( getXmlResponse() == null ? 0 : this.getXmlResponse().hashCode() );
         result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
         result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
         result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
         result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );

         return result;
   } 
}
