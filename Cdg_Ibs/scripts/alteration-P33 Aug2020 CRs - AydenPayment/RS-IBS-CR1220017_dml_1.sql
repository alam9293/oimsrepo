  ------------- Top Standard statements begin
set echo off
set timing off
set heading off
set feedback off
set serveroutput on size 100000
set define on 
column dt new_value new_dt 
column ext new_value new_ext
select  'RS-IBS-CR0820019_dml_04_' || sys_context('userenv','instance_name') || '_' || to_char(sysdate,'YYYYMMDD_HH24MISS') dt,'.txt' ext from dual;
SPOOL  &new_dt.&new_ext
select 'Running at ' || sys_context('userenv', 'host') || '[' || sys_context('userenv', 'ip_address') || ']' || '@' || sys_context('userenv', 'os_user') 
       || ' as ' || sys_context('userenv', 'current_user') || '@' || sys_context('userenv', 'db_unique_name') as where_run
from   dual;
select 'Run start: ' || to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') as script_run_start
from dual;
set linesize  10000
set pages 5000
set feedback on
set timing on
set heading on
set echo on
set define off
------------- Top Standard statements end
  
BEGIN

    FOR temp in (SELECT * FROM ITTB_SETL_REPORTING_ADYEN) LOOP
        UPDATE TMTB_NON_BILLABLE_TXN_CRCA crca SET crca.FILE_NAME = temp.FILE_NAME where temp.pspreference = crca.psp_ref_no;
        UPDATE TMTB_NON_BILLABLE_TXN_CRCA crca SET crca.UPLOAD_DATE = temp.UPLOAD_DATE where temp.pspreference = crca.psp_ref_no;

    END LOOP;

    FOR temp in (SELECT * FROM ITTB_SETL_REPORTING_AMEX) LOOP
        UPDATE TMTB_NON_BILLABLE_TXN_CRCA crca SET crca.FILE_NAME = temp.FILE_NAME where temp.reference_number = crca.psp_ref_no;
        UPDATE TMTB_NON_BILLABLE_TXN_CRCA crca SET crca.UPLOAD_DATE = temp.UPLOAD_DATE where temp.reference_number = crca.psp_ref_no;

    END LOOP;
	
    FOR txn_no in (SELECT txn_no FROM TMTB_NON_BILLABLE_TXN) LOOP
		UPDATE TMTB_NON_BILLABLE_TXN txn2 SET txn2.txn_id = txn_no.txn_no where txn2.txn_no = txn_no.txn_no;
	END LOOP;

	--Put this constraint name into ddl 16
	SELECT CONSTRAINT_NAME FROM ALL_CONSTRAINTS where constraint_type = 'P' and TABLE_NAME = 'TMTB_NON_BILLABLE_TXN';


commit;
END;
/

  ------------- Bottom Standard statements begin
set echo off
set timing off
set heading off
set feedback off
select 'Run end: ' || to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') as script_run_end
from dual;
spool off
set echo on
set timing on
set heading on
set feedback on
------------- Bottom Standard statements end