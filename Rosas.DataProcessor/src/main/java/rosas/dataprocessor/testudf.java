package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by Zhuang on 2015/8/25.
 */
public class testudf extends UDF {
    public String evaluate(double IF_INDEX) {
        try {
            if (IF_INDEX == 0)
            {
                return new String("�޸���".getBytes(), "UTF-8");
            }
            else if (IF_INDEX == 1)
            {
                return new String("������".getBytes(), "ASCII");
            }
            else if (IF_INDEX == 2)
            {
                return new String("һ�����".getBytes(), "GB2312");
            }
            else if (IF_INDEX == 3)
            {
                return new String("ǿ����".getBytes(), "UTF-16");
            }
            else if (IF_INDEX == 4)
            {
                return "��ǿ����";
            }
            else
            {
                return new String("������Ч".getBytes(), "UTF-8");
            }

        } catch (Exception e) {
            return "ERROR";
        }
    }
}