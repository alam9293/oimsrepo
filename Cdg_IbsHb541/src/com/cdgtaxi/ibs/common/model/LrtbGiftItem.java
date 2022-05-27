package com.cdgtaxi.ibs.common.model;
// Generated Aug 20, 2009 3:48:09 PM by Hibernate Tools 3.1.0.beta4

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.cdgtaxi.ibs.acl.security.Auditable;


/**
 * LrtbGiftItem generated by hbm2java
 */

public class LrtbGiftItem  implements java.io.Serializable, Auditable, Creatable, Updatable {


	// Fields

	private Integer giftItemNo;
	private String itemCode;
	private String itemName;
	private BigDecimal price;
	private Integer stock;
	private Blob image;
	private Integer version;
	private Integer points;
	private Timestamp createdDt;
    private String createdBy;
    private Timestamp updatedDt;
    private String updatedBy;
	private LrtbGiftCategory lrtbGiftCategory;
	private Set<LrtbGiftStock> lrtbGiftStocks = new HashSet<LrtbGiftStock>(0);


	// Constructors

	/** default constructor */
	public LrtbGiftItem() {
	}

	/** minimal constructor */
	public LrtbGiftItem(String itemCode, String itemName, BigDecimal price, Integer stock, Integer points) {
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.price = price;
		this.stock = stock;
		this.points = points;
	}

	/** full constructor */
	public LrtbGiftItem(String itemCode, String itemName, BigDecimal price, Integer stock, Blob image, Integer version, Integer points, Timestamp createdDt, String createdBy, Timestamp updatedDt, String updatedBy, LrtbGiftCategory lrtbGiftCategory, Set<LrtbGiftStock> lrtbGiftStocks) {
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.price = price;
		this.stock = stock;
		this.image = image;
		this.version = version;
		this.points = points;
		this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.updatedDt = updatedDt;
        this.updatedBy = updatedBy;
		this.lrtbGiftCategory = lrtbGiftCategory;
		this.lrtbGiftStocks = lrtbGiftStocks;
	}



	// Property accessors

	public Integer getGiftItemNo() {
		return this.giftItemNo;
	}

	public void setGiftItemNo(Integer giftItemNo) {
		this.giftItemNo = giftItemNo;
	}

	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Blob getImage() {
		return this.image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
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
	
	public LrtbGiftCategory getLrtbGiftCategory() {
		return this.lrtbGiftCategory;
	}

	public void setLrtbGiftCategory(LrtbGiftCategory lrtbGiftCategory) {
		this.lrtbGiftCategory = lrtbGiftCategory;
	}

	public Set<LrtbGiftStock> getLrtbGiftStocks() {
		return this.lrtbGiftStocks;
	}

	public void setLrtbGiftStocks(Set<LrtbGiftStock> lrtbGiftStocks) {
		this.lrtbGiftStocks = lrtbGiftStocks;
	}


	/**
	 * toString
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("itemCode").append("='").append(getItemCode()).append("' ");
		buffer.append("itemName").append("='").append(getItemName()).append("' ");
		buffer.append("price").append("='").append(getPrice()).append("' ");
		buffer.append("stock").append("='").append(getStock()).append("' ");
		buffer.append("image").append("='").append(getImage()).append("' ");
		buffer.append("version").append("='").append(getVersion()).append("' ");
		buffer.append("points").append("='").append(getPoints()).append("' ");
		buffer.append("createdDt").append("='").append(getCreatedDt()).append("' ");			
	    buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");			
	    buffer.append("updatedDt").append("='").append(getUpdatedDt()).append("' ");			
	    buffer.append("updatedBy").append("='").append(getUpdatedBy()).append("' ");	
		buffer.append("]");

		return buffer.toString();
	}


	@Override
	public boolean equals(Object other) {
		if ( (this == other ) ) {
			return true;
		}
		if ( (other == null ) ) {
			return false;
		}
		if ( !(other instanceof LrtbGiftItem) ) {
			return false;
		}
		LrtbGiftItem castOther = ( LrtbGiftItem ) other;

		return ( (this.getGiftItemNo()==castOther.getGiftItemNo()) || ( this.getGiftItemNo()!=null && castOther.getGiftItemNo()!=null && this.getGiftItemNo().equals(castOther.getGiftItemNo()) ) )
		&& ( (this.getItemCode()==castOther.getItemCode()) || ( this.getItemCode()!=null && castOther.getItemCode()!=null && this.getItemCode().equals(castOther.getItemCode()) ) )
		&& ( (this.getItemName()==castOther.getItemName()) || ( this.getItemName()!=null && castOther.getItemName()!=null && this.getItemName().equals(castOther.getItemName()) ) )
		&& ( (this.getPrice()==castOther.getPrice()) || ( this.getPrice()!=null && castOther.getPrice()!=null && this.getPrice().equals(castOther.getPrice()) ) )
		&& ( (this.getStock()==castOther.getStock()) || ( this.getStock()!=null && castOther.getStock()!=null && this.getStock().equals(castOther.getStock()) ) )
		&& ( (this.getImage()==castOther.getImage()) || ( this.getImage()!=null && castOther.getImage()!=null && this.getImage().equals(castOther.getImage()) ) )
		&& ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
		&& ( (this.getPoints()==castOther.getPoints()) || ( this.getPoints()!=null && castOther.getPoints()!=null && this.getPoints().equals(castOther.getPoints()) ) )
		&& ( (this.getCreatedDt()==castOther.getCreatedDt()) || ( this.getCreatedDt()!=null && castOther.getCreatedDt()!=null && this.getCreatedDt().equals(castOther.getCreatedDt()) ) )
 && ( (this.getCreatedBy()==castOther.getCreatedBy()) || ( this.getCreatedBy()!=null && castOther.getCreatedBy()!=null && this.getCreatedBy().equals(castOther.getCreatedBy()) ) )
 && ( (this.getUpdatedDt()==castOther.getUpdatedDt()) || ( this.getUpdatedDt()!=null && castOther.getUpdatedDt()!=null && this.getUpdatedDt().equals(castOther.getUpdatedDt()) ) )
 && ( (this.getUpdatedBy()==castOther.getUpdatedBy()) || ( this.getUpdatedBy()!=null && castOther.getUpdatedBy()!=null && this.getUpdatedBy().equals(castOther.getUpdatedBy()) ) )
		&& ( (this.getLrtbGiftCategory()==castOther.getLrtbGiftCategory()) || ( this.getLrtbGiftCategory()!=null && castOther.getLrtbGiftCategory()!=null && this.getLrtbGiftCategory().equals(castOther.getLrtbGiftCategory()) ) );
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + ( getGiftItemNo() == null ? 0 : this.getGiftItemNo().hashCode() );
		result = 37 * result + ( getItemCode() == null ? 0 : this.getItemCode().hashCode() );
		result = 37 * result + ( getItemName() == null ? 0 : this.getItemName().hashCode() );
		result = 37 * result + ( getPrice() == null ? 0 : this.getPrice().hashCode() );
		result = 37 * result + ( getStock() == null ? 0 : this.getStock().hashCode() );
		result = 37 * result + ( getImage() == null ? 0 : this.getImage().hashCode() );
		result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
		result = 37 * result + ( getPoints() == null ? 0 : this.getPoints().hashCode() );
		result = 37 * result + ( getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode() );
        result = 37 * result + ( getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode() );
        result = 37 * result + ( getUpdatedDt() == null ? 0 : this.getUpdatedDt().hashCode() );
        result = 37 * result + ( getUpdatedBy() == null ? 0 : this.getUpdatedBy().hashCode() );
		result = 37 * result + ( getLrtbGiftCategory() == null ? 0 : this.getLrtbGiftCategory().hashCode() );

		return result;
	}





}
