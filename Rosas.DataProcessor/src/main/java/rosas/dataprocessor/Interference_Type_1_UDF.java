package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.math.BigDecimal;

/**
 * Created by Zhuang on 2015/8/26.
 * <p/>
 * ��������   UDF1  ���ֵ
 */
public class Interference_Type_1_UDF extends UDF {
    public double evaluate(String[] str) {
        try {
            double sum = 0;
            int effetiveNum = 0;
            for (int i = 0; i < str.length; i++) {
                if (str[i] != null && str[i] != "") {
                    sum += Double.parseDouble(str[i]);
                    effetiveNum++;
                }
            }
            if (effetiveNum==0)
            {
                return 0;
            }
            double result=sum/effetiveNum;
            return CommonFunction.ReservedDecimal(result);
        } catch (Exception e) {
            return 0;
        }
    }
}