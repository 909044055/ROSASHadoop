package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class IF_GetTypeNo_UDF extends UDF {
    public int evaluate(double avg10to89 ,double slope9to49,double slope50to90,int ClusterMutationTopCount,int max_no, double slope, double smoothness, double standarddeviation, String peakindicatorvector, String peakindicatorvector_2, double top40indexaggregation, double top15andtail15, String flag, double xgxs, double Top40AndLast60Broadcast, double AvgTail15_85,double ave_if_nl,String BAND) {
        try {

            //������ţ�
            //б�ʣ�9-49��<-0.10  and б�ʣ�50-89��>0.10  and����ͻ��top����ֵ<10 and��ֵָʾ���������ڣ�20-80����
            if (slope9to49 < -0.10 && slope50to90 > 0.10 && ClusterMutationTopCount < 10 && !fun1(peakindicatorvector,20, 80)) {
                return 9;
            }
            //��ɢ���ţ�
            //IF  |б��|>K6 0.12  andƽ����>K7 0.86 and��׼��>2.8 and ����ͻ��top����ֵ<4 and���15��prb��ֵ-ǰ��85��PRB��ֵ<4 and ��ֵָʾ���������ڣ�20-80����
            else if (Math.abs(slope) > 0.12 && smoothness > 0.86 && standarddeviation > 2.8 && ClusterMutationTopCount<4&&AvgTail15_85<4&&!fun1(peakindicatorvector,20, 80)&&ave_if_nl<-100.0) {
                return 1;
            }
            //GPS���ţ�
            //IF 49 or 50��Ϊ��ֵand����ͻ��top����ֵ<10�����ұ�С������10�����С�����д���GPS����С����
            else if ((peakindicatorvector.indexOf("49") >= 0 || peakindicatorvector.indexOf("50") >= 0) && ClusterMutationTopCount<10 && flag == "1"&&ave_if_nl<-100.0) {
                return 2;
            }
            //ͬƵ���ţ�
            //IF  {|б��|<0.1 andƽ����>0.80 and 47 or 48 or 49 or 50 or 51��Ϊ��ֵ and ��ֵ������<10����ͻ��top����ֵ<10)} or {��ֵָʾ����ֻ���ڣ�47��48��49��50��51��}�� ��
            else if (
                    ((Math.abs(slope) < 0.1 && smoothness > 0.80&& peakindicatorvector.split(",").length<10 && ClusterMutationTopCount<10
                            && (peakindicatorvector.indexOf("49") >= 0 || peakindicatorvector.indexOf("50") >= 0 || peakindicatorvector.indexOf("47") >= 0 || peakindicatorvector.indexOf("51") >= 0 || peakindicatorvector.indexOf("48") >= 0))&&ave_if_nl<-100.0)
                    || (Arrays.asList("47,48,49,50,51".split(",")).containsAll(Arrays.asList(peakindicatorvector.split(",")))&&ave_if_nl<-100.0)
                    ) {
                return 4;
            }
            //�������ţ�
            //IF -0.1<б��<0.1 andƽ����>0.9 and 0-15���ڷ�ֵָʾ��and �����ϵ��>0.60 and ��ֵ(10-89prb)>-110 and ����ͻ��top����ֵ<10  and  |б�ʣ�9-49��|<0.1 and|б�ʣ�50-89��|<0.1
            else if ((xgxs == 1111.0 || xgxs > 0.6) && slope > -0.1 && slope < 0.1 && smoothness > 0.9 && fun1(peakindicatorvector,0, 15) && avg10to89 > -110.0 && ClusterMutationTopCount < 10 && Math.abs(slope9to49) < 0.1 && Math.abs(slope50to90) < 0.1) {
                return 6;
            }
            //��Ƶ���ţ�
            //IF |б��|<0.1 and and |б�ʣ�9-49��|<0.1 and|б�ʣ�50-89��|<0.1 andƽ����>0.95  and ��׼��<2.5 and���15��prb��ֵ-ǰ��85��PRB��ֵ<5 and�����ϵ��<0.6 and ��ֵ(10-89prb)>-110 and ��ֵָʾ������������Ϊ2��<3 and����ͻ��top����ֵ<10�����ж�Ϊ�������-��Ƶ���� ��
            else if (Math.abs(slope) < 0.1 && Math.abs(slope9to49) < 0.1 && Math.abs(slope50to90) < 0.1 && smoothness > 0.95 && standarddeviation < 2.5 && AvgTail15_85 < 5.0 && avg10to89 > -110.0 && peakindicatorvector_2.split(",").length < 3 && ClusterMutationTopCount < 10 && (xgxs == 1111.0 || xgxs < 0.6)&&ave_if_nl>-95.0) {
                return 5;
            }
            //�������ţ�
            //IF��ֵ����(����Ϊ2��)���ڵ���3�� and 8��9��10��18��19��20ʱ�δ��ڸ��� and����ͻ��top����ֵ<15  and���15��prb��ֵ-ǰ��85��PRB��ֵ<4
            else if (peakindicatorvector_2.split(",").length >= 3  && ClusterMutationTopCount < 15&& AvgTail15_85<4&&ave_if_nl<-95.0) {
                return 3;
            }
            //�����ţ�
            //IF  ƽ����>0.80   and 20<����ͻ��top����ֵ<70
            else if (smoothness > 0.8    && (ClusterMutationTopCount > 20 && ClusterMutationTopCount < 70)&& Math.abs(slope)<0.1&&BAND.equals("D")) {
                return 7;
            }
            //PHS���ţ�
            //If б��>0 andƽ����>0.82 and ���15��prb��ֵ-ǰ��85��PRB��ֵ>5.
            else if (slope > 0.0 && smoothness > 0.82 && AvgTail15_85 > 5) {
                return 8;
            } else {
                return 0;//��������
            }

        } catch (Exception e) {
            return 0;
        }


    }
//0-p���ڷ�ֵָʾ��
    public boolean fun1(String a,int n,int p){

        List<String> list = new ArrayList<String>() {
        };
        for (int i = n; i <= p; i++) {
            list.add(Integer.toString(i));
        }
        List<String> list1=Arrays.asList(a.split(","));
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

/*

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
        else if ((xgxs==1111.0||xgxs > 0.6) && slope > -0.018 && slope < 0.01 && smoothness > 0.9 && fun1(peakindicatorvector)) {
        return 6;
        }
        //��Ƶ���ţ�
        //IF |б��|<0.05 and ƽ����>0.9  and ��׼��<2 and�����ϵ��<0.3�����ж�Ϊ��Ƶ���š�
        else if (Math.abs(slope) < 0.05 && smoothness > 0.9 && standarddeviation < 2 && (xgxs==1111.0||xgxs < 0.3)) {
        return 5;
        }
        //�������ţ�
        //IF��ֵ�������ڵ���4�� and 0.2 <ƽ����<0.9
        else if (peakindicatorvector_2.split(",").length >= 4 && (smoothness > 0.2 && smoothness < 0.9)) {
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


        */