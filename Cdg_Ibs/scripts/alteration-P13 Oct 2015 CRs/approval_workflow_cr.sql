alter table TMTB_NON_BILLABLE_TXN add APPROVAL_REMARKS varchar2(500) NULL;

alter table TMTB_NON_BILLABLE_TXN add APPROVAL_USER number NULL;


INSERT INTO SATB_RESOURCE
(RSRC_ID, PAR_RSRC_ID, RSRC_NAME, RSRC_TYPE, URI, SEQUENCE, DISPLAY_NAME, DISPLAY, VERSION)
VALUES((SELECT MAX(rsrc_id)+1 FROM SATB_RESOURCE), (select RSRC_ID from SATB_RESOURCE where rsrc_name='Non Billable'), 'Approve Chargeback', 'U', '/nonbillable/app_chargeback.zul', 1, 'Approve Chargeback', 'Y', 0);


