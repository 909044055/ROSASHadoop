package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.*;

/**
 * Created by Zhuang on 2015/8/26.
 * ����λ�õ�ȷ���㷨
 */
public class Interference_Type_5_UDF extends UDF {
    public String evaluate(String[] str) {
        try {

            List<Integer> B=new ArrayList<Integer>(){};
            List<Integer> PRB_No = new ArrayList<Integer>() {};//PRB�±�
            List<Double> V=new ArrayList<Double>(){};//PRB value
            for (int i = 0; i < str.length; i++) {
                if (str[i] != null && str[i] != "") {
                    V.add(Double.parseDouble(str[i]));
                    PRB_No.add(i + 5);
                }
            }

            while (V.size()>7)
            {
                double max_value=-100000;
                int max_no=-1;
                for (int i=0;i<V.size();i++)
                {
                    if (V.get(i)>max_value) {
                        max_value=V.get(i);
                        max_no=PRB_No.get(i);
                    }
                }

                int left=0;
                int right=0;

                if (CommonFunction.GetIndexFromList(PRB_No, max_no)<= 3)
                {
                    left= max_no;
                }
                else
                {

                    int i=3;
                    while (!PRB_No.contains(max_no-i)) {
                        i--;
                    }
                    if (i<=0)
                    {
                        i=0;
                    }
                    left = max_no - i;
                }

                if(CommonFunction.GetIndexFromList(PRB_No, max_no)>=PRB_No.size()-3)
                {
                    right=max_no;
                }
                else
                {
                    int i=3;
                    while (!PRB_No.contains(max_no+i)) {
                        i++;
                    }
                    right = max_no + i;
                    if (right>PRB_No.get(PRB_No.size()-1))
                    {
                        right=PRB_No.get(PRB_No.size()-1);
                    }
                }

                int leftIndex=CommonFunction.GetIndexFromList(PRB_No, left);
                int rightIndex=CommonFunction.GetIndexFromList(PRB_No, right)+1;
                List<Double> V1= V.subList(leftIndex, rightIndex);
                List<Integer> PRB_No1= PRB_No.subList(leftIndex, rightIndex);





                int min_index=CommonFunction.GetMinIndexInDoubleList(V1);

                if (max_value-V1.get(min_index)>=5)
                {
                    B.add(max_no);
                }

                V1.clear();
                PRB_No1.clear();


            }


            return B.toString().replace(" ","");
        } catch (Exception e) {
            return "Error";
        }
    }
}