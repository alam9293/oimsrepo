package com.webapp.ims.food.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "implementation_schedule")
public class ImplementationSchedule implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemsacquisitiondateofland")
	    private Date itemsacquisitiondateofland;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailsacquisitiondateofland")
	    private Date detailsacquisitiondateofland;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemsdateofbuildingconstructionfrom")
	    private Date itemsdateofbuildingconstructionfrom;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailsdateofbuildingconstructionfrom")
	    private Date detailsdateofbuildingconstructionfrom;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemsdateofbuildingconstructionto")
	    private Date itemsdateofbuildingconstructionto;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailsdateofbuildingconstructionto")
	    private Date detailsdateofbuildingconstructionto;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemsdateofplacingorderforplantmachineryfrom")
	    private Date itemsdateofplacingorderforplantmachineryfrom;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailsdateofplacingorderforplantmachineryfrom")
	    private Date detailsdateofplacingorderforplantmachineryfrom;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemsdateofplacingorderforplantmachineryto")
	    private Date itemsdateofplacingorderforplantmachineryto;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailsdateofplacingorderforplantmachineryto")
	    private Date detailsdateofplacingorderforplantmachineryto;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemstrialproductiondatefrom")
	    private Date itemstrialproductiondatefrom;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailstrialproductiondatefrom")
	    private Date detailstrialproductiondatefrom;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemstrialproductiondateto")
	    private Date itemstrialproductiondateto;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailstrialproductiondateto")
	    private Date detailstrialproductiondateto;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "itemsdateofcommercialproductionrunning")
	    private Date itemsdateofcommercialproductionrunning;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "detailsdateofcommercialproductionrunning")
	    private Date detailsdateofcommercialproductionrunning;
	    
	   
	    
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
	        return this.id;
	    }
	    
	    public void setId(String id) {
	        id = id;
	    }

		public Date getItemsacquisitiondateofland() {
			return itemsacquisitiondateofland;
		}

		public void setItemsacquisitiondateofland(Date itemsacquisitiondateofland) {
			this.itemsacquisitiondateofland = itemsacquisitiondateofland;
		}

		public Date getDetailsacquisitiondateofland() {
			return detailsacquisitiondateofland;
		}

		public void setDetailsacquisitiondateofland(Date detailsacquisitiondateofland) {
			this.detailsacquisitiondateofland = detailsacquisitiondateofland;
		}

		public Date getItemsdateofbuildingconstructionfrom() {
			return itemsdateofbuildingconstructionfrom;
		}

		public void setItemsdateofbuildingconstructionfrom(Date itemsdateofbuildingconstructionfrom) {
			this.itemsdateofbuildingconstructionfrom = itemsdateofbuildingconstructionfrom;
		}

		public Date getDetailsdateofbuildingconstructionfrom() {
			return detailsdateofbuildingconstructionfrom;
		}

		public void setDetailsdateofbuildingconstructionfrom(Date detailsdateofbuildingconstructionfrom) {
			this.detailsdateofbuildingconstructionfrom = detailsdateofbuildingconstructionfrom;
		}

		public Date getItemsdateofbuildingconstructionto() {
			return itemsdateofbuildingconstructionto;
		}

		public void setItemsdateofbuildingconstructionto(Date itemsdateofbuildingconstructionto) {
			this.itemsdateofbuildingconstructionto = itemsdateofbuildingconstructionto;
		}

		public Date getDetailsdateofbuildingconstructionto() {
			return detailsdateofbuildingconstructionto;
		}

		public void setDetailsdateofbuildingconstructionto(Date detailsdateofbuildingconstructionto) {
			this.detailsdateofbuildingconstructionto = detailsdateofbuildingconstructionto;
		}

		public Date getItemsdateofplacingorderforplantmachineryfrom() {
			return itemsdateofplacingorderforplantmachineryfrom;
		}

		public void setItemsdateofplacingorderforplantmachineryfrom(Date itemsdateofplacingorderforplantmachineryfrom) {
			this.itemsdateofplacingorderforplantmachineryfrom = itemsdateofplacingorderforplantmachineryfrom;
		}

		public Date getDetailsdateofplacingorderforplantmachineryfrom() {
			return detailsdateofplacingorderforplantmachineryfrom;
		}

		public void setDetailsdateofplacingorderforplantmachineryfrom(Date detailsdateofplacingorderforplantmachineryfrom) {
			this.detailsdateofplacingorderforplantmachineryfrom = detailsdateofplacingorderforplantmachineryfrom;
		}

		public Date getItemsdateofplacingorderforplantmachineryto() {
			return itemsdateofplacingorderforplantmachineryto;
		}

		public void setItemsdateofplacingorderforplantmachineryto(Date itemsdateofplacingorderforplantmachineryto) {
			this.itemsdateofplacingorderforplantmachineryto = itemsdateofplacingorderforplantmachineryto;
		}

		public Date getDetailsdateofplacingorderforplantmachineryto() {
			return detailsdateofplacingorderforplantmachineryto;
		}

		public void setDetailsdateofplacingorderforplantmachineryto(Date detailsdateofplacingorderforplantmachineryto) {
			this.detailsdateofplacingorderforplantmachineryto = detailsdateofplacingorderforplantmachineryto;
		}

		public Date getItemstrialproductiondatefrom() {
			return itemstrialproductiondatefrom;
		}

		public void setItemstrialproductiondatefrom(Date itemstrialproductiondatefrom) {
			this.itemstrialproductiondatefrom = itemstrialproductiondatefrom;
		}

		public Date getDetailstrialproductiondatefrom() {
			return detailstrialproductiondatefrom;
		}

		public void setDetailstrialproductiondatefrom(Date detailstrialproductiondatefrom) {
			this.detailstrialproductiondatefrom = detailstrialproductiondatefrom;
		}

		public Date getItemstrialproductiondateto() {
			return itemstrialproductiondateto;
		}

		public void setItemstrialproductiondateto(Date itemstrialproductiondateto) {
			this.itemstrialproductiondateto = itemstrialproductiondateto;
		}

		public Date getDetailstrialproductiondateto() {
			return detailstrialproductiondateto;
		}

		public void setDetailstrialproductiondateto(Date detailstrialproductiondateto) {
			this.detailstrialproductiondateto = detailstrialproductiondateto;
		}

		public Date getItemsdateofcommercialproductionrunning() {
			return itemsdateofcommercialproductionrunning;
		}

		public void setItemsdateofcommercialproductionrunning(Date itemsdateofcommercialproductionrunning) {
			this.itemsdateofcommercialproductionrunning = itemsdateofcommercialproductionrunning;
		}

		public Date getDetailsdateofcommercialproductionrunning() {
			return detailsdateofcommercialproductionrunning;
		}

		public void setDetailsdateofcommercialproductionrunning(Date detailsdateofcommercialproductionrunning) {
			this.detailsdateofcommercialproductionrunning = detailsdateofcommercialproductionrunning;
		}
	    
	    }
