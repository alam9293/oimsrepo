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
@Table(name = "ImplementationSchedule")
///@IdClass(Identifier.class)
public class ImplementationScheduleTQM {

	private String Unit_Id;
    private String Control_ID;
    @Id
    private String id;

	@Column(name = "itemsitemsofwork")
    private String itemsitemsofwork;
    @Column(name = "detailsitemsofwork")
    private String detailsitemsofwork;
    @Column(name = "itemsdateofimplementation")
    private String itemsdateofimplementation;
    @Column(name = "detailsdateofimplementation")
    private String detailsdateofimplementation;
	
	public String getUnit_Id() {
		return Unit_Id;
	}
	public void setUnit_Id(String unit_Id) {
		Unit_Id = unit_Id;
	}
	public String getControl_ID() {
		return Control_ID;
	}
	public void setControl_ID(String control_ID) {
		Control_ID = control_ID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemsitemsofwork() {
		return itemsitemsofwork;
	}
	public void setItemsitemsofwork(String itemsitemsofwork) {
		this.itemsitemsofwork = itemsitemsofwork;
	}
	public String getDetailsitemsofwork() {
		return detailsitemsofwork;
	}
	public void setDetailsitemsofwork(String detailsitemsofwork) {
		this.detailsitemsofwork = detailsitemsofwork;
	}
	public String getItemsdateofimplementation() {
		return itemsdateofimplementation;
	}
	public void setItemsdateofimplementation(String itemsdateofimplementation) {
		this.itemsdateofimplementation = itemsdateofimplementation;
	}
	public String getDetailsdateofimplementation() {
		return detailsdateofimplementation;
	}
	public void setDetailsdateofimplementation(String detailsdateofimplementation) {
		this.detailsdateofimplementation = detailsdateofimplementation;
	}
    

}
