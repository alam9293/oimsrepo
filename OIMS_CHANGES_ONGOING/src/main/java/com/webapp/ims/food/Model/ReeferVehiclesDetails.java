package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_details")
public class ReeferVehiclesDetails implements Serializable {


	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "itemsnumberofthereefervehiclesmobileprecoolingvans")
	    private String itemsnumberofthereefervehiclesmobileprecoolingvans;
	    @Column(name = "quantitynumberofthereefervehiclesmobileprecoolingvans")
	    private String quantitynumberofthereefervehiclesmobileprecoolingvans;
	    @Column(name = "itemscapacityofthereefervehiclesmobileprecoolingvans")
	    private String itemscapacityofthereefervehiclesmobileprecoolingvans;
	    @Column(name = "quantitycapacityofthereefervehiclesmobileprecoolingvans")
	    private String quantitycapacityofthereefervehiclesmobileprecoolingvans;
	    
	    
	   
	    public String getUnit_id() {
			return unit_id;
		}

		public void setUnit_id(String unit_id) {
			this.unit_id = unit_id;
		}

		public String getControl_id() {
			return control_id;
		}

		public void setControl_id(String control_id) {
			this.control_id = control_id;
		}

		public String getItemsnumberofthereefervehiclesmobileprecoolingvans() {
			return itemsnumberofthereefervehiclesmobileprecoolingvans;
		}

		public void setItemsnumberofthereefervehiclesmobileprecoolingvans(
				String itemsnumberofthereefervehiclesmobileprecoolingvans) {
			this.itemsnumberofthereefervehiclesmobileprecoolingvans = itemsnumberofthereefervehiclesmobileprecoolingvans;
		}

		public String getQuantitynumberofthereefervehiclesmobileprecoolingvans() {
			return quantitynumberofthereefervehiclesmobileprecoolingvans;
		}

		public void setQuantitynumberofthereefervehiclesmobileprecoolingvans(
				String quantitynumberofthereefervehiclesmobileprecoolingvans) {
			this.quantitynumberofthereefervehiclesmobileprecoolingvans = quantitynumberofthereefervehiclesmobileprecoolingvans;
		}

		public String getItemscapacityofthereefervehiclesmobileprecoolingvans() {
			return itemscapacityofthereefervehiclesmobileprecoolingvans;
		}

		public void setItemscapacityofthereefervehiclesmobileprecoolingvans(
				String itemscapacityofthereefervehiclesmobileprecoolingvans) {
			this.itemscapacityofthereefervehiclesmobileprecoolingvans = itemscapacityofthereefervehiclesmobileprecoolingvans;
		}

		public String getQuantitycapacityofthereefervehiclesmobileprecoolingvans() {
			return quantitycapacityofthereefervehiclesmobileprecoolingvans;
		}

		public void setQuantitycapacityofthereefervehiclesmobileprecoolingvans(
				String quantitycapacityofthereefervehiclesmobileprecoolingvans) {
			this.quantitycapacityofthereefervehiclesmobileprecoolingvans = quantitycapacityofthereefervehiclesmobileprecoolingvans;
		}

		public String getId() {
	        return this.id;
	    }
	    
	    public void setId(String id) {
	        id = id;
	    }
	    
	    public String getitemsnumberofthereefervehiclesmobileprecoolingvans() {
	        return this.itemsnumberofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public void setitemsnumberofthereefervehiclesmobileprecoolingvans(final String itemsnumberofthereefervehiclesmobileprecoolingvans) {
	        this.itemsnumberofthereefervehiclesmobileprecoolingvans = itemsnumberofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public String getquantitynumberofthereefervehiclesmobileprecoolingvans() {
	        return this.quantitynumberofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public void setquantitynumberofthereefervehiclesmobileprecoolingvans(final String quantitynumberofthereefervehiclesmobileprecoolingvans) {
	        this.quantitynumberofthereefervehiclesmobileprecoolingvans = quantitynumberofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public String getitemscapacityofthereefervehiclesmobileprecoolingvans() {
	        return this.itemscapacityofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public void setitemscapacityofthereefervehiclesmobileprecoolingvans(final String itemscapacityofthereefervehiclesmobileprecoolingvans) {
	        this.itemscapacityofthereefervehiclesmobileprecoolingvans = itemscapacityofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public String getquantitycapacityofthereefervehiclesmobileprecoolingvans() {
	        return this.quantitycapacityofthereefervehiclesmobileprecoolingvans;
	    }
	    
	    public void setquantitycapacityofthereefervehiclesmobileprecoolingvans(final String quantitycapacityofthereefervehiclesmobileprecoolingvans) {
	        this.quantitycapacityofthereefervehiclesmobileprecoolingvans = quantitycapacityofthereefervehiclesmobileprecoolingvans;
	    }
}
