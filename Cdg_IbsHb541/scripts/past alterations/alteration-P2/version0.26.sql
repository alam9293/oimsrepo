/* *******************
 * PROMOTION
 * *******************/
alter table MSTB_PROMOTION add(
	EFFECTIVE_CUTOFF_DATE date
);

/* *******************
 * REWARDS
 * *******************/
alter table BMTB_INVOICE_HEADER add(
	AWARDED_REWARDS_PTS number(8) DEFAULT 0
);
alter table BMTB_DRAFT_INV_HEADER add(
	AWARDED_REWARDS_PTS number(8) DEFAULT 0
);

/* *****************
 * PRICE / PTS RATIO
 * *****************/
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO,MASTER_TYPE,MASTER_CODE,INTERFACE_MAPPING_VALUE,MASTER_VALUE,MASTER_STATUS,VERSION) 
VALUES (217,'RPPR','RATIO',null,'3','A',0);