add jar .\Rosas.DataProcessor.jar;
use rosas;
--------------LTE����©�������--MR_INDEX_8-------------
--CREATE TEMPORARY FUNCTION ev1 as 'rosas.dataprocessor.LTE_AdjacentAreaLeakageDefinition_UDAF';
--����UDAF���ô��㷨--
CREATE TEMPORARY FUNCTION ev1 as 'rosas.dataprocessor.MR_OverCoverageIndex_UDAF';
drop table T8_1;
drop table T8_2;
drop table T8_3;
drop table T8_3_1;
drop table T8_3_2;
drop table T8_4;
drop table T8_5;
drop table T8_6;
drop table T8_7;
drop table T8_P1;
drop table T8_P2;
drop table T8_P3;

DROP TABLE T8_0;
CREATE TABLE T8_0 AS
SELECT * FROM MRO WHERE substring(fileheader_starttime,0,10)='{select_date}';



-------���ÿ����С��������������--------
CREATE table T8_1 AS
select * from 
(select DEF_MO1 as MO,MR_LteNcEarfcn,MR_LteNcPci,MR_LteScRSRP,MR_LteNcRSRP,regexp_replace(substring(fileheader_starttime,0,10),'-','') as day from
MRO) t4 join 
(select DEF_MO1 as MO2,regexp_replace(substring(fileheader_starttime,0,10),'-','') as day1,COUNT(*) as YB_totle 
from MRO group by DEF_MO1,regexp_replace(substring(fileheader_starttime,0,10),'-','')) t5
on t4.MO=t5.MO2 and t4.day=t5.day1;

----ѡ��MRO������С��ǿ��ص�С��-----
CREATE TABLE T8_P1 AS
SELECT VALUE1 as v1,OPERATOR1 as op1 FROM PARAM_CONFIG WHERE PARAM='COV_THRES';
CREATE TABLE T8_P2 AS
SELECT VALUE1 as v2,OPERATOR1 as op2 FROM PARAM_CONFIG WHERE PARAM='RSRP_DIFF';
CREATE TABLE T8_P3 AS
SELECT VALUE1 as v3,OPERATOR1 as op3 FROM PARAM_CONFIG WHERE PARAM='SR_SAMPLE_RATIO';

CREATE table T8_2 AS
select * from (select t6.MO as T8_2_MO,t6.day,t6.MR_LteNcEarfcn,t6.MR_LteNcPci,
ev1
(t6.MR_LteScRSRP,t6.MR_LteNcRSRP,t6.YB_totle,V1,OP1,V2,OP2,V3,OP3) as Flag
from
T8_P1,T8_P2,T8_P3,T8_1 t6
group by t6.MO,t6.day,t6.MR_LteNcEarfcn,t6.MR_LteNcPci) t11 where Flag=1;


------ѡ��NRM_EutranRelationTdd�����������ڵ�����----------
CREATE table T8_3_1 AS
select *,regexp_replace(substring(FILEHEADER_DATETIME,0,10),'-','') as day from NRM_EutranRelationTdd;
CREATE table T8_3_2 AS
SELECT max(day) as maxday FROM T8_3_1;
CREATE table T8_3 AS
select DEF_CELLNAME AS NRM_DEF_CELLNAME,Pci AS NRM_PCI,Earfcn AS NRM_EARFCN,fileheader_datetime AS TTIME from T8_3_2 t1,T8_3_1 t2 WHERE t2.day=t1.maxday;
CREATE TABLE T8_5 AS
SELECT * FROM T8_3 T1 JOIN SITE_INFO T2 ON T1.NRM_DEF_CELLNAME=T2.DEF_CELLNAME;


-----������
CREATE TABLE T8_6 AS
select * from
T8_2 v1 left outer join T8_5 v2 on v1.T8_2_MO=v2.DEF_ECI and v1.MR_LteNcEarfcn=v2.NRM_EARFCN and v1.MR_LteNcPci=v2.NRM_PCI
where DEF_ECI is null;

----��T8_6�Ľ������MR_INDEX_8��
CREATE TABLE T8_7 AS
SELECT DEF_CELLNAME AS DEF_CELLNAME1,DEF_CELLNAME_CHINESE as DEF_CELLNAME_CHINESE1,CITY as CITY1,REGION AS REGION1,TOWN AS TOWN1,
GRID AS GRID1,def_eci AS MO1 FROM SITE_INFO; 

drop table PROPERTIES_DB_2;
CREATE TABLE PROPERTIES_DB_2 AS
SELECT DEF_CELLNAME1 as DEF_CELLNAME,'����' as TYPE1,'����������' as TYPE2,
'©����LTE����' as TYPE3,DEF_CELLNAME1 as FAULT_OBJECT,concat(substring(day,0,4),'-',substring(day,5,2),'-',substring(day,7,2)) as TTIME,
CITY1 as CITY,REGION1 AS REGION,TOWN1 AS TOWN,GRID1 AS GRID,
concat('©����LTE������EARFCN=',MR_LteNcEarfcn,',Pci=',MR_LteNcPci) AS FAULT_DESCRIPTION,
'ԭ��' as LABEL,'AllDay' as THOUR,DEF_CELLNAME_CHINESE1 as DEF_CELLNAME_CHINESE 
FROM T8_7 t1 join T8_6 t2 on t1.MO1=t2.t8_2_mo;


DROP TABLE T8_0;
drop table T8_1;
drop table T8_2;
drop table T8_3;
drop table T8_3_1;
drop table T8_3_2;
drop table T8_4;
drop table T8_5;
drop table T8_6;
drop table T8_7;
drop table T8_P1;
drop table T8_P2;
drop table T8_P3;