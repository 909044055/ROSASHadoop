add jar .\Rosas.DataProcessor.jar;
use rosas;
--------------LTE����©�������--MR_INDEX_8-------------
CREATE TEMPORARY FUNCTION ev1 as 'rosas.dataprocessor.LTE_AdjacentAreaLeakageDefinition_UDAF';
drop table T8_1;
drop table T8_2;
drop table T8_3;
drop table T8_4;
drop table T8_3_1;
drop table T8_3_2;
-------���ÿ����С��������������--------
CREATE table T8_1 AS
select * from 
(select MO,MR_LteScRSRP,MR_LteNcRSRP,regexp_replace(substring(FILEHEADER_STARTTIME,0,10),'-','') as day from
MRO) t4 join 
(select DEF_MO1 as MO2,regexp_replace(substring(FILEHEADER_STARTTIME,0,10),'-','') as day1,COUNT(*) as YB_totle 
from MRO group by DEF_MO1,regexp_replace(substring(FILEHEADER_STARTTIME,0,10),'-','')) t5
on t4.MO=t5.MO2 and t4.day=t5.day1;

------ѡ��MRO������С��ǿ��ص�С��-----
CREATE table T8_2 AS
select * from (select t6.DEF_MO1 as T8_2_MO,t6.day,t6.MR_LteNcEarfcn,t6.MR_LteNcPci,
ev1
(t6.MR_LteScRSRP,t6.MR_LteNcRSRP,t6.YB_totle,
t1.VALUE1,t1.OPERATOR1,
t2.VALUE1,t2.OPERATOR1,
t3.VALUE1,t3.OPERATOR1) as Flag
from
PARAM_CONFIG t1,PARAM_CONFIG t2,PARAM_CONFIG t3,T8_1 t6
where t1.PARAM='COV_THRES' and t2.PARAM='RSRP_DIFF' and t3.PARAM='SR_SAMPLE_RATIO'
group by t6.DEF_MO1,t6.day,t6.MR_LteNcEarfcn,t6.MR_LteNcPci) t7
where Flag=1;


------ѡ��NRM_EutranRelationTdd�����������ڵ�����----------
CREATE table T8_3_1 AS
select *,regexp_replace(substring(FILEHEADER_STARTTIME,0,10),'-','') as day from NRM_EutranRelationTdd;
CREATE table T8_3_2 AS
SELECT max(day) as maxday FROM T8_3_1;
CREATE table T8_3 AS
select *,def_mo1 as T8_3_MO from T8_3_2 t1,T8_3_1 t2 WHERE t2.day=t1.maxday;



-------������
CREATE TABLE T8_4 AS
select t8.T8_2_MO from
(T8_2 v1 left outer join T8_3 v2 on v1.T8_2_MO=v2.T8_3_MO and v1.MR_LteNcEarfcn=v2.Pci and v1.MR_LteNcPci=v2.Earfcn) t8
where T8_3_MO=NULL;

----��T8_1�Ľ������MR_INDEX_8��


--drop table T8_1;
--drop table T8_2;
--drop table T8_3;
--drop table T8_4;
