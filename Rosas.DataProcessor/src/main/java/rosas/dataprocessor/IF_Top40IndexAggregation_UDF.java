package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.*;

/**
 * Created by Administrator on 2016/4/8.
 * Top40 index aggregation
 * TOP40�����ۼ���
 */
public class IF_Top40IndexAggregation_UDF extends UDF {
    public double evaluate(String[] str) {
        try {
            str = Common.MissingValueProcess(str);
            Map<Integer, Double> map = new HashMap<Integer, Double>();
            for (int i = 0; i < str.length; i++) {
                map.put(i, Double.parseDouble(str[i]));
            }
            List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
            //��ֵ����
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return Double.compare(o2.getValue() * 100, (o1.getValue()) * 100);
                }
            });
            //ȡtop40
            list=list.subList(0, list.size() < 40 ? list.size() : 40);
            //��key����
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return Double.compare(o2.getKey() * 100, (o1.getKey()) * 100);
                }
            });
            double result = 0;
            for (int i = 0; i < (list.size() < 40 ? list.size() : 40) - 1; i++) {
                result += list.get(i).getKey() -list.get(i + 1).getKey()==1?1:0 ;
            }
            return Math.round(result * 10000.0 / 39) / 10000.0;
        } catch (Exception e) {
            return 0;
        }
    }
}