add jar .\Rosas.DataProcessor.jar;
use rosas;
--------------���߽ӷ�����---MR_INDEX_7------------
CREATE TEMPORARY FUNCTION ev1 as 'rosas.dataprocessor.Antenna_Reverse_Analysis_1_UDF';
CREATE TEMPORARY FUNCTION ev2 as 'rosas.dataprocessor.Antenna_Reverse_Analysis_UDF';

drop table T7_1;
create table T7_1 as
select * from
(select * from mr_index_3 where THOUR='AllDay') t1
join
SITE_INFO t2
on
t1.mo=t2.DEF_ECI;

drop table MR_INDEX_7;
CREATE TABLE MR_INDEX_7 AS
select t4.MO,'���Թ���' as TYPE1,'�������߹���' as TYPE2,'���߽ӷ�' as TYPE3,DEF_CELLNAME,TTIME,CITY,REGION,TOWN,GRID,concat('�滮�������ʵ�ʸ��Ƿ���ƫ��',FAULT_DESCRIPTION1,'��') as FAULT_DESCRIPTION,'ԭ��' as LABEL,'AllDay' as THOUR,DEF_CELLNAME_CHINESE
from
(select *,ev2(DIR,MR_SECTOR_BEARING) as Flag,ev1(DIR,MR_SECTOR_BEARING) as FAULT_DESCRIPTION1 from T7_1) t4
where Flag=1;


drop table T7_1;
