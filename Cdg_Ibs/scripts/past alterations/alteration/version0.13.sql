--SENGTAT: added columns to cater for city, state and remarks for misc invoice
ALTER TABLE BMTB_INVOICE_HEADER
ADD (
  ADDRESS_CITY VARCHAR2(35),
  ADDRESS_STATE VARCHAR2(35),
  REMARKS VARCHAR2(500)  
);
--END

--SENGTAT: adding blob column for entity table
ALTER TABLE FMTB_ENTITY_MASTER
ADD (
  LOGO BLOB
);
--END