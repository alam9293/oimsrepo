/* *************************************
 * NON BILLABLE ACQUIRER PAYMENT TYPE
 * ************************************/

alter table mstb_acquirer_pymt_type add (
	master_no number(8)
);

update mstb_acquirer_pymt_type set master_no = (
	select master_no from mstb_master_table
	where master_type ='APT' and master_code = mstb_acquirer_pymt_type.value
);

alter table mstb_acquirer_pymt_type modify(
	master_no number(8) not null
);

alter table mstb_acquirer_pymt_type drop column name;
alter table mstb_acquirer_pymt_type drop column value;

ALTER TABLE mstb_acquirer_pymt_type ADD CONSTRAINT mstb_acquirer_pymt_type_FK2 FOREIGN KEY (master_no) REFERENCES MSTB_MASTER_TABLE (master_no) ENABLE;
CREATE INDEX mstb_acquirer_pymt_type_N4 ON Mstb_Acquirer_Pymt_Type(master_no);

/* *************************************
 * NON BILLABLE TRIPS DOWNLOAD
 * ************************************/

CREATE TABLE ITTB_SETL_TXN_REQ
(
   REQ_NO decimal(18) PRIMARY KEY NOT NULL,
   STATUS varchar2(1) NOT NULL,
   REQUEST_DATE date NOT NULL,
   CREATED_BY varchar2(80),
   CREATED_DT timestamp,
   UPDATED_BY varchar2(80),
   UPDATED_DT timestamp,
   VERSION decimal(9) DEFAULT 0

);
create SEQUENCE ITTB_SETL_TXN_REQ_SQ1 MINVALUE 1 MAXVALUE 999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;