USE ROSAS;

DROP TABLE IF_INDEX_14;
CREATE TABLE IF_INDEX_14 AS
SELECT
DEF_VENDORNAME,DEF_CELLNAME,DEF_CELLNAME_CHINESE,TTIME,THOUR,
(NUM_IF_RBs_BAND1*1+ NUM_IF_RBs_BAND2*2+NUM_IF_RBs_BAND3*3+NUM_IF_RBs_BAND4*4+NUM_IF_RBs_BAND5*5)/100 AS IF_INDEX
FROM IF_INDEX_LV12345_Rb_Numbers;