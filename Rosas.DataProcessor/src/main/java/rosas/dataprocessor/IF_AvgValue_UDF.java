package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by Administrator on 2016/4/6.
 * ��PRB 0��99 ��ֵ
 */
public class IF_AvgValue_UDF  extends UDF {
    public double evaluate(String[] str) {
        try {
            double sum=0.0;
            int cnt=Common.GetEffectValueCount(str);
            if(cnt<=55)//��Ч��С��55��
            {
                for (int i=0;i<str.length;i++)
                {
                    if(str[i]!=null&&str[i]!=""){
                    sum+=Double.parseDouble(str[i]);
                    }
                }
                if (cnt==0)
                {
                    return 0;
                }
                return Math.round(sum*1000.0/cnt)/1000.0;

            }

            str=Common.MissingValueProcess(str);//�����ֵ


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