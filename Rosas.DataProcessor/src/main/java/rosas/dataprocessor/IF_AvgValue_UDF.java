package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by Administrator on 2016/4/6.
 * ��PRB 0��99 ��ֵ
 */
public class IF_AvgValue_UDF  extends UDF {
    public double evaluate(String[] str) {
        try {
            str=Common.MissingValueProcess(str);//�����ֵ

            double sum=0.0;
            for (int i=0;i<str.length;i++)
            {
                sum+=Double.parseDouble(str[i]);
            }
            return Math.round(sum*1000.0/str.length)/1000.0;

        } catch (Exception e) {
            return 0;
        }
    }
}