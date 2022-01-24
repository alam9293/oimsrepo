/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */

@Entity
@Table(name = "product_detail")
///@IdClass(Identifier.class)
public class ProductDetail {

	@Column(name = "unit_id")
	private String unit_id;

	private String control_id;

	@Id
	private String id;

	 @Column(name = "nameoftheproducts")
	    private String nameoftheproducts;
	    @Column(name = "byproducts")
	    private String byproducts;
	    @Column(name = "brandname")
	    private String brandname;
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
		public String getNameoftheproducts() {
			return nameoftheproducts;
		}
		public void setNameoftheproducts(String nameoftheproducts) {
			this.nameoftheproducts = nameoftheproducts;
		}
		public String getByproducts() {
			return byproducts;
		}
		public void setByproducts(String byproducts) {
			this.byproducts = byproducts;
		}
		public String getBrandname() {
			return brandname;
		}
		public void setBrandname(String brandname) {
			this.brandname = brandname;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		
	    
}
