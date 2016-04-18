package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by Administrator on 2016/4/7.
 * ��ֵָʾ����
 * avg��ֵ
 * ����qΪ�����ȣ�qԽ��������Խ�ͣ�ȡ2.5~3���ܱȽϺ���
 */
public class IF_PeakIndicatorVector_UDF  extends UDF {
    public String evaluate(double q,String[] str) {
        try {
            str=Common.MissingValueProcess(str);//�����ֵ
            IF_AvgValue_UDF ev1=new IF_AvgValue_UDF();

            double avg=ev1.evaluate(str);

            IF_StandardDeviation_UDF ave=new IF_StandardDeviation_UDF();
            double StandardDeviation = ave.evaluate( str);//�����׼��


            String result_str="";
            int[] result=new int[str.length];
            for (int i = 0; i < str.length; i++) {
                result[i]=Double.parseDouble(str[i])-avg>StandardDeviation*q?1:0;
                if(result[i]==1)
                {
                    result_str+= Integer.toString(i)+",";
                }
            }


            return result_str.substring(0,result_str.length()-1);
        } catch (Exception e) {
            return "[]";
        }
    }
}