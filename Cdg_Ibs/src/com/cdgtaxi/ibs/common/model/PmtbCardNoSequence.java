package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;


@SuppressWarnings("serial")
public class PmtbCardNoSequence  implements java.io.Serializable {

    private Integer seqId;
    private Integer numberOfDigit;
    
	private String binRange;
    private String subBinRange;
    private BigDecimal count;
    private Integer version;
    
	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	public Integer getNumberOfDigit() {
		return numberOfDigit;
	}
	public void setNumberOfDigit(Integer numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}
	public String getBinRange() {
		return binRange;
	}
	public void setBinRange(String binRange) {
		this.binRange = binRange;
	}
	public String getSubBinRange() {
		return subBinRange;
	}
	public void setSubBinRange(String subBinRange) {
		this.subBinRange = subBinRange;
	}
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PmtbCardNoSequence other = (PmtbCardNoSequence) obj;
		if (seqId == null) {
			if (other.seqId != null)
				return false;
		} else if (!seqId.equals(other.seqId))
			return false;
		return true;
	}


}
