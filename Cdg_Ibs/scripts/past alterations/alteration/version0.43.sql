CREATE TABLE MSTB_REPORT_FORMAT_MAP (
	MAP_NO NUMBER(8) NOT NULL,
	RSRC_ID NUMBER(10) NOT NULL,
	REPORT_FORMAT VARCHAR2(10) NOT NULL,
	CONSTRAINT MSTB_REPORT_FORMAT_MAP_FK1 FOREIGN KEY(RSRC_ID) REFERENCES SATB_RESOURCE(RSRC_ID),
	CONSTRAINT MSTB_REPORT_FORMAT_MAP_PK PRIMARY KEY(MAP_NO)
);

alter table ITTB_TRIPS_TXN
add PRIMARY KEY(TRIP_INTF_PK);

Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (426,412,'Soft Copy Invoice & Trips Detail (By Invoice No.)','U','/report/soft_copy_invoice_trip_details_by_invoice_no.zul',1,'Soft Copy Invoice & Trips Detail (By Invoice No.)','N',0);
Insert into SATB_RESOURCE (RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) values (427,412,'Soft Copy Invoice & Trips Detail (By Transaction Status)','U','/report/soft_copy_invoice_trip_details_by_transaction_status.zul',1,'Soft Copy Invoice & Trips Detail (By Transaction Status)','N',0);


insert into MSTB_REPORT_FORMAT_MAP
select NVL(MAX(MAP_NO),0)+1 MAP_NO, MAX(RSRC_ID) RSRC_ID, UPPER('CSV')
from
(
    select max(MAP_NO) as MAP_NO, null as RSRC_ID
    from MSTB_REPORT_FORMAT_MAP MAP
    union
    select null as MAP_NO, RSRC.RSRC_ID
    from SATB_RESOURCE RSRC
    where upper(RSRC_NAME) = upper('Soft Copy Invoice & Trips Detail (By Invoice No.)')
) temp;

insert into MSTB_REPORT_FORMAT_MAP
select NVL(MAX(MAP_NO),0)+1 MAP_NO, MAX(RSRC_ID) RSRC_ID, UPPER('CUSTOMIZE')
from
(
    select max(MAP_NO) as MAP_NO, null as RSRC_ID
    from MSTB_REPORT_FORMAT_MAP MAP
    union
    select null as MAP_NO, RSRC.RSRC_ID
    from SATB_RESOURCE RSRC
    where upper(RSRC_NAME) = upper('Soft Copy Invoice & Trips Detail (By Invoice No.)')
) temp;

insert into MSTB_REPORT_FORMAT_MAP
select NVL(MAX(MAP_NO),0)+1 MAP_NO, MAX(RSRC_ID) RSRC_ID, UPPER('CSV')
from
(
    select max(MAP_NO) as MAP_NO, null as RSRC_ID
    from MSTB_REPORT_FORMAT_MAP MAP
    union
    select null as MAP_NO, RSRC.RSRC_ID
    from SATB_RESOURCE RSRC
    where upper(RSRC_NAME) = upper('Customer Aging Detail')
) temp;

insert into MSTB_REPORT_FORMAT_MAP
select NVL(MAX(MAP_NO),0)+1 MAP_NO, MAX(RSRC_ID) RSRC_ID, UPPER('CSV')
from
(
    select max(MAP_NO) as MAP_NO, null as RSRC_ID
    from MSTB_REPORT_FORMAT_MAP MAP
    union
    select null as MAP_NO, RSRC.RSRC_ID
    from SATB_RESOURCE RSRC
    where upper(RSRC_NAME) = upper('Customer Aging Summary')
) temp;

insert into MSTB_REPORT_FORMAT_MAP
select NVL(MAX(MAP_NO),0)+1 MAP_NO, MAX(RSRC_ID) RSRC_ID, UPPER('CSV')
from
(
    select max(MAP_NO) as MAP_NO, null as RSRC_ID
    from MSTB_REPORT_FORMAT_MAP MAP
    union
    select null as MAP_NO, RSRC.RSRC_ID
    from SATB_RESOURCE RSRC
    where upper(RSRC_NAME) = upper('Soft Copy Invoice & Trips Detail (By Transaction Status)')
) temp;
