package rosas.dataprocessor;

import org.apache.avro.generic.GenericData;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class DELETE_IF_GetTypeNo_UDF extends UDF {
    public int evaluate(int max_no, double slope, double smoothness, double standarddeviation, String peakindicatorvector, double top40indexaggregation, double top15andtail15, String flag, double Top40AndLast60Broadcast, double AvgTail15_85) {
        try {


            //��ɢ���ţ�
            //IFб��<K6 -0.12 andƽ����>K7 0.86 and��׼��>3
            if (slope < -0.12 && smoothness > 0.86 && standarddeviation > 3) {
                return 1;
            }
            //GPS���ţ�
            //IF 49 or 50��Ϊ��ֵ�����ұ�С������10�����С�����д���GPS����С����
            else if ((peakindicatorvector.indexOf("49") >= 0 || peakindicatorvector.indexOf("50") >= 0) && flag == "1") {
                return 2;
            }
            //ͬƵ���ţ�
            //IF  |б��|<0.02 andƽ����>0.95 and 47 ~ 51 ����һ��Ϊ��ֵ��
            else if (Math.abs(slope) < 0.02 && smoothness > 0.88 && (peakindicatorvector.indexOf("49") >= 0 || peakindicatorvector.indexOf("50") >= 0 || peakindicatorvector.indexOf("47") >= 0 || peakindicatorvector.indexOf("51") >= 0 || peakindicatorvector.indexOf("48") >= 0)) {
                return 4;
            }
            //�������ţ�
            //IF-0.018<б��<0.01 andƽ����>0.9 and 0-9���ڷ�ֵָʾ��and �����ϵ��>0.60
            else if (slope > -0.018 && slope < 0.01 && smoothness > 0.9&& fun1(peakindicatorvector) ) {
                return 6;
            }
            //��Ƶ���ţ�
            //IF |б��|<0.05 and ƽ����>0.9  and ��׼��<2 and�����ϵ��<0.3�����ж�Ϊ��Ƶ���š�
            else if (Math.abs(slope) < 0.05 && smoothness > 0.9 && standarddeviation < 2) {
                return 5;
            }
            //�������ţ�
            //IF��ֵ�������ڵ���4�� and 0.4 <ƽ����<0.85
            else if (peakindicatorvector.split(",").length >= 4 && (smoothness > 0.4 && smoothness < 0.85)) {
                return 3;
            }
            //�����ţ�
            //IF  ƽ����>0.80 and top40������>0.88.and top40��ֵ-last60��ֵ>15.
            else if (smoothness > 0.8 && top40indexaggregation > 0.88 && Top40AndLast60Broadcast > 15) {
                return 7;
            }
            //PHS���ţ�
            //If б��>0.01 andƽ����>0.82 and TOP15��TAIL15�����ص���>0.90 and ���15��prb��ֵ-ǰ��85��PRB��ֵ>15.
            else if (slope > 0.01 && smoothness > 0.82 && top15andtail15 > 0.9 && AvgTail15_85 > 15) {
                return 8;
            } else {
                return 0;//��������
            }

        } catch (Exception e) {
            return 0;
        }
    }

    public boolean fun1(String a){

        List<String> list = new ArrayList<String>() {
        };
        for (int i = -1; i < 10; i++) {
            list.add(Integer.toString(i));
        }

        List<String> list1=new ArrayList<String>(){};
        list1=Arrays.asList(a.split(","));


        try {


            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (Integer.parseInt(list1.get(i)) == Integer.parseInt(list.get(j))) {
                        return true;
                    }

                }
            }
        }
        catch (Exception ex)
        {
            return false;
        }
        return false;

    }

}