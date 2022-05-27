-- GOVT EINVOICE Line Description configuration
-- use comma as delimiter, in order
-- codes: jobNo, bookedBy, tripStartDate, tripEndDate, paxName, jobType, surcharge, cardNo, taxiNo, destination, pickup, vehicleType, projectCode  
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) VALUES (MSTB_MASTER_TABLE_SQ1.nextVal,'GEBGS','GELD',null,'#jobNo#,#bookedBy#,#paxName#','A',0);
