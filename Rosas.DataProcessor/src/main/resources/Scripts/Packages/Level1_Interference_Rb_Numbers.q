add jar .\Rosas.DataProcessor.jar;
use rosas;
CREATE TEMPORARY FUNCTION ev1 as 'rosas.dataprocessor.Level1_Interference_Rb_Numbers_UDF';

-----参数临时表
DROP TABLE IF_INDEX_9_P1;
CREATE TABLE IF_INDEX_9_P1 AS
select 
OPERATOR1 as op1,VALUE1 as v1,LOGIC,OPERATOR2 as op2,VALUE2 as v2 
from 
param_config 
where PARAM='INTERFERENCE_THRES_B1';

----每条数据符合条件的RB数
DROP TABLE IF_INDEX_9_1;
CREATE TABLE IF_INDEX_9_1 AS
SELECT 
DEF_VENDORNAME,DEF_CELLNAME,DEF_CELLNAME_CHINESE,
substring(fileheader_begintime,0,10) as TTIME,
substring(fileheader_begintime,12,2) as THOUR,
ev1(op1,v1,LOGIC,op2,v2,PHY_ULMeanNL_PRB0,PHY_ULMeanNL_PRB1,PHY_ULMeanNL_PRB2,PHY_ULMeanNL_PRB3,PHY_ULMeanNL_PRB4,PHY_ULMeanNL_PRB5,PHY_ULMeanNL_PRB6,PHY_ULMeanNL_PRB7,PHY_ULMeanNL_PRB8,PHY_ULMeanNL_PRB9,PHY_ULMeanNL_PRB10,PHY_ULMeanNL_PRB11,PHY_ULMeanNL_PRB12,PHY_ULMeanNL_PRB13,PHY_ULMeanNL_PRB14,PHY_ULMeanNL_PRB15,PHY_ULMeanNL_PRB16,PHY_ULMeanNL_PRB17,PHY_ULMeanNL_PRB18,PHY_ULMeanNL_PRB19,PHY_ULMeanNL_PRB20,PHY_ULMeanNL_PRB21,PHY_ULMeanNL_PRB22,PHY_ULMeanNL_PRB23,PHY_ULMeanNL_PRB24,PHY_ULMeanNL_PRB25,PHY_ULMeanNL_PRB26,PHY_ULMeanNL_PRB27,PHY_ULMeanNL_PRB28,PHY_ULMeanNL_PRB29,PHY_ULMeanNL_PRB30,PHY_ULMeanNL_PRB31,PHY_ULMeanNL_PRB32,PHY_ULMeanNL_PRB33,PHY_ULMeanNL_PRB34,PHY_ULMeanNL_PRB35,PHY_ULMeanNL_PRB36,PHY_ULMeanNL_PRB37,PHY_ULMeanNL_PRB38,PHY_ULMeanNL_PRB39,PHY_ULMeanNL_PRB40,PHY_ULMeanNL_PRB41,PHY_ULMeanNL_PRB42,PHY_ULMeanNL_PRB43,PHY_ULMeanNL_PRB44,PHY_ULMeanNL_PRB45,PHY_ULMeanNL_PRB46,PHY_ULMeanNL_PRB47,PHY_ULMeanNL_PRB48,PHY_ULMeanNL_PRB49,PHY_ULMeanNL_PRB50,PHY_ULMeanNL_PRB51,PHY_ULMeanNL_PRB52,PHY_ULMeanNL_PRB53,PHY_ULMeanNL_PRB54,PHY_ULMeanNL_PRB55,PHY_ULMeanNL_PRB56,PHY_ULMeanNL_PRB57,PHY_ULMeanNL_PRB58,PHY_ULMeanNL_PRB59,PHY_ULMeanNL_PRB60,PHY_ULMeanNL_PRB61,PHY_ULMeanNL_PRB62,PHY_ULMeanNL_PRB63,PHY_ULMeanNL_PRB64,PHY_ULMeanNL_PRB65,PHY_ULMeanNL_PRB66,PHY_ULMeanNL_PRB67,PHY_ULMeanNL_PRB68,PHY_ULMeanNL_PRB69,PHY_ULMeanNL_PRB70,PHY_ULMeanNL_PRB71,PHY_ULMeanNL_PRB72,PHY_ULMeanNL_PRB73,PHY_ULMeanNL_PRB74,PHY_ULMeanNL_PRB75,PHY_ULMeanNL_PRB76,PHY_ULMeanNL_PRB77,PHY_ULMeanNL_PRB78,PHY_ULMeanNL_PRB79,PHY_ULMeanNL_PRB80,PHY_ULMeanNL_PRB81,PHY_ULMeanNL_PRB82,PHY_ULMeanNL_PRB83,PHY_ULMeanNL_PRB84,PHY_ULMeanNL_PRB85,PHY_ULMeanNL_PRB86,PHY_ULMeanNL_PRB87,PHY_ULMeanNL_PRB88,PHY_ULMeanNL_PRB89,PHY_ULMeanNL_PRB90,PHY_ULMeanNL_PRB91,PHY_ULMeanNL_PRB92,PHY_ULMeanNL_PRB93,PHY_ULMeanNL_PRB94,PHY_ULMeanNL_PRB95,PHY_ULMeanNL_PRB96,PHY_ULMeanNL_PRB97,PHY_ULMeanNL_PRB98,PHY_ULMeanNL_PRB99)
as SingleRow_NUM_IF_RBs_BAND1
FROM 
IF_INDEX_9_P1,PM_EutranCellTdd;



----小时级
DROP TABLE IF_INDEX_9;
CREATE TABLE IF_INDEX_9 AS 
SELECT 
DEF_VENDORNAME,DEF_CELLNAME,DEF_CELLNAME_CHINESE,TTIME,THOUR,
MAX(SingleRow_NUM_IF_RBs_BAND1) as NUM_IF_RBs_BAND1
FROM 
IF_INDEX_9_1
GROUP BY 
DEF_VENDORNAME,DEF_CELLNAME,DEF_CELLNAME_CHINESE,TTIME,THOUR;

----全天级
INSERT OVERWRITE TABLE IF_INDEX_9 
SELECT 
DEF_VENDORNAME,DEF_CELLNAME,DEF_CELLNAME_CHINESE,TTIME,'AllDay' as THOUR,
MAX(SingleRow_NUM_IF_RBs_BAND1) as NUM_IF_RBs_BAND1
FROM 
IF_INDEX_9_1
GROUP BY 
DEF_VENDORNAME,DEF_CELLNAME,DEF_CELLNAME_CHINESE,TTIME;


----删除临时表
DROP TABLE IF_INDEX_9_P1;
DROP TABLE IF_INDEX_9_1;
