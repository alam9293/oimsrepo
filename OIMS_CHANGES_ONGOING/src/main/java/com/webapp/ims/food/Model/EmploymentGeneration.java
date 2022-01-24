package com.webapp.ims.food.Model;

	import javax.persistence.Column;
	import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
	import javax.persistence.Entity;
	import java.io.Serializable;

	@Entity
	@Table(name = "employment_generation")
	@IdClass(Identifier.class)
	public class EmploymentGeneration
	
	{
		
		@Column(name = "unit_id")
		private String unit_id;

		private String control_id;

		@Id
		private String id;
		
	    @Column(name = "itemsdirect")
	    private String itemsdirect;
	    @Column(name = "noofpersondirect")
	    private String noofpersondirect;
	    @Column(name = "itemsindirect")
	    private String itemsindirect;
	    @Column(name = "noofpersonindirect")
	    private String noofpersonindirect;
	    @Column(name = "itemstotal")
	    private String itemstotal;
	    @Column(name = "noofpersontotal")
	    private String noofpersontotal;
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
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getItemsdirect() {
			return itemsdirect;
		}
		public void setItemsdirect(String itemsdirect) {
			this.itemsdirect = itemsdirect;
		}
		public String getNoofpersondirect() {
			return noofpersondirect;
		}
		public void setNoofpersondirect(String noofpersondirect) {
			this.noofpersondirect = noofpersondirect;
		}
		public String getItemsindirect() {
			return itemsindirect;
		}
		public void setItemsindirect(String itemsindirect) {
			this.itemsindirect = itemsindirect;
		}
		public String getNoofpersonindirect() {
			return noofpersonindirect;
		}
		public void setNoofpersonindirect(String noofpersonindirect) {
			this.noofpersonindirect = noofpersonindirect;
		}
		public String getItemstotal() {
			return itemstotal;
		}
		public void setItemstotal(String itemstotal) {
			this.itemstotal = itemstotal;
		}
		public String getNoofpersontotal() {
			return noofpersontotal;
		}
		public void setNoofpersontotal(String noofpersontotal) {
			this.noofpersontotal = noofpersontotal;
		}  
	    
	}
