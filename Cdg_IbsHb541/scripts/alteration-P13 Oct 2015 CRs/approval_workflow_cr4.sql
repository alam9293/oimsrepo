-- Previously in approval_workflow_cr.sql, added 2 new fields to tmtb_non_billable_txn.
-- Previously in approval_workflow_cr2.sql, added 1 new fields to tmtb_non_billable_txn and add 6 new config into mstb_master_table.

-- As going to revert Chargeback and trip , thus will remove it, will add back if needed next time, have backup.

delete from mstb_master_table where master_type = 'EMICBRR';
delete from mstb_master_table where master_type = 'EMICBRA';
delete from mstb_master_table where master_type = 'EMICBRS';

alter table TMTB_NON_BILLABLE_TXN drop column CHARGEBACK_SUBMITTED_USER;
alter table TMTB_NON_BILLABLE_TXN drop column APPROVAL_REMARKS;
alter table TMTB_NON_BILLABLE_TXN drop column APPROVAL_USER;