add jar .\Rosas.DataProcessor.jar;
use rosas;
--------------TDS_����©�������--MR_INDEX_9-------------
CREATE TEMPORARY FUNCTION ev1 as 'rosas.dataprocessor.Compare_UDF';
CREATE TEMPORARY FUNCTION ev2 as 'rosas.dataprocessor.TDS_AdjacentAreaLeakageDefinition_UDAF';


CREATE TABLE T9_1 AS
SELECT * FROM PARAM_CONFIG,MRO WHERE MR_TdsNcellUarfcn<>NULL ADN PARAM=��COV_THRES��;

CREATE TABLE T9_2 AS
SELECT *,ev1(MR_LteScRSRP,op1,v1) as Flag1 FROM T9_1;
----��¼U
CREATE TABLE T9_3 AS
SELECT *,LPad(fileheader_starttime,10,'') as day FROM T9_2 where Flag1=1;
----��¼UN
CREATE TABLE T9_4 AS
SELECT *,COUNT(*) as totle FROM T9_3 GROUP BY day,DEF_MO1,MR_TdsNcellUarfcn,MR_TdsCellParameterId;

CREATE TABLE T9_5 AS
SELECT day,DEF_MO1,MR_TdsNcellUarfcn,MR_TdsCellParameterId,ev2(MR_TdsPccpchRSCP,op2,v2,totle,op3,v3) as Flag2
FROM
T9_4 GROUP BY
day,DEF_MO1,MR_TdsNcellUarfcn,MR_TdsCellParameterId;

CREATE TABLE T9_6 AS
SELECT * FROM T9_5 WHERE Flag2=1;

----T9_7���е�MO����TDS����©�����С��
CREATE TABLE T9_7 AS
select * from
NRM-UtranRelationTdd t1 left outer join T9_6 t2 on t1.MR_TdsNcellUarfcn=t2.MR_TdsNcellUarfcn,t1.MR_TdsCellParameterId=t2.MR_TdsCellParameterId
where t2.Falg2<>1;

----��T9_7�Ľ��д��MR_INDEX_9��



drop table T9_1;
drop table T9_2;
drop table T9_3;
drop table T9_4;
drop table T9_5;
drop table T9_6;
drop table T9_7;
