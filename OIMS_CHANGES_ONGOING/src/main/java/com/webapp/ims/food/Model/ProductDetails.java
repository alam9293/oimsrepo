package com.webapp.ims.food.Model;

	import javax.persistence.Column;
	import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
	import javax.persistence.Entity;
	import java.io.Serializable;

	@Entity
	@Table(name = "product_details")
	//@IdClass(Identifier.class)
	public class ProductDetails
	
	{
		
		@Column(name = "unit_id")
		private String unit_id;

		private String control_id;

		@Id
		private String id;
		
	    @Column(name = "products")
	    private String products;
	    @Column(name = "byproducts")
	    private String byproducts;
	    @Column(name = "action")
	    private String action;
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
		public String getProducts() {
			return products;
		}
		public void setProducts(String products) {
			this.products = products;
		}
		public String getByproducts() {
			return byproducts;
		}
		public void setByproducts(String byproducts) {
			this.byproducts = byproducts;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		
	}
