/* *************************************
 * NON BILLABLE TRIPS DOWNLOAD
 * ************************************/

alter table ITTB_SETL_TXN modify(
	 txn_date timestamp,
	 created_Dt timestamp,
	 recon_dt timestamp,
	 trip_Start timestamp,
	 trip_End timestamp,
	 flowthru_Dt timestamp,
	 setl_Date timestamp
);

alter table Tmtb_Non_Billable_Txn modify(
	nric varchar2(15) null
);

/* *************************************
 * INVENTORY
 * ************************************/

INSERT INTO SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) VALUES (616,529,'Approve Request','U','Approve Request',7,'Approve Request','N',0);


