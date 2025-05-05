-- Previously in approval_workflow_cr.sql, added the menu to view 'Approve Chargeback'. 
-- As going to revert Chargeback, thus will remove it, and add back this menu/sql in next deployment.


-- DELETE all user role that is able to view 'Approve Chargeback screen'
delete from SATB_ROLE_RESOURCE where rsrc_id = (select rsrc_id from satb_resource where rsrc_name = 'Approve Chargeback');

-- Delete the menu 'Approve Chargeback' so that user unable to see it anymore.
delete from satb_resource where rsrc_name = 'Approve Chargeback';