/* *********************
 * CREDIT NOTE FOR PROMO
 * *********************/
ALTER TABLE BMTB_NOTE ADD(
   PROMO_DIS NUMBER(12,2) DEFAULT 0  NOT NULL
);

/* *********************
 * Moving Rewards Plan managing functions to Administration
 * *********************/
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/add_plan_detail.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/create_plan.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/edit_plan.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/edit_plan_detail.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/view_plan.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = '/rewards/view_plan_detail.zul';
update SATB_RESOURCE set PAR_RSRC_ID = (select RSRC_ID from SATB_RESOURCE where URI = 'Administration') where uri = 'Delete Loyalty Plan';

/* *********************
 * Delete Rewards Plan Detail - Administration
 * *********************/
INSERT INTO SATB_RESOURCE 
(RSRC_ID,PAR_RSRC_ID,RSRC_NAME,RSRC_TYPE,URI,SEQUENCE,DISPLAY_NAME,DISPLAY,VERSION) 
VALUES 
(701,537,'Delete Loyalty Plan Detail','U','Delete Loyalty Plan Detail',49,'Delete Loyalty Plan Detail','N',0);





update SATB_RESOURCE set RSRC_NAME = 'Approve Pending Adjustment Req', DISPLAY_NAME='Approve Pending Adjustment Req' where URI = '/rewards/adjustment/view_pending_req.zul'




/* *******************
 * REWARDS
 * *******************/
alter table BMTB_INVOICE_HEADER add(
	FORFEITED_REWARDS_PTS number(8) DEFAULT 0
);
alter table BMTB_DRAFT_INV_HEADER add(
	FORFEITED_REWARDS_PTS number(8) DEFAULT 0
);




/*
for max report result
*/
INSERT INTO MSTB_MASTER_TABLE (MASTER_NO, MASTER_TYPE, MASTER_CODE, INTERFACE_MAPPING_VALUE, MASTER_VALUE, MASTER_STATUS, VERSION) VALUES (MSTB_MASTER_TABLE_SQ1.nextVal, 'MRR', '200', null, '200', 'A', 0);