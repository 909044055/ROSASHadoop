package rosas.dataprocessor;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by Zhuang on 2015/9/18.
 */
public class Param_Analysis_36_UDF extends UDF {
    public int evaluate(String Str1, String Str2, String Str3,
                        String op1, String v1, String LOGIC1, String op2, String v2) {
        try {
            String[] Strlist1 = Common.StrToList(Str1);
            String[] Strlist2 = Common.StrToList(Str2);
            if (Strlist1.length == 0 || Strlist2.length == 0) {
                return 0;
            }

            if (Double.parseDouble(Strlist1[0].replace(")", "").split(",")[0]) < 1000&&Double.parseDouble(Strlist2[0].replace(")", "").split(",")[0]) < 1000) {
                for (int i = 0; i < Strlist1.length; i++) {
                    String[] strlist_1 = Strlist1[i].replace(")", "").split(",");
                    String[] strlist_2 = Strlist2[i].replace(")", "").split(",");

                    double a = (Double.parseDouble(strlist_1[0]) * 2) +
                            (Double.parseDouble(strlist_2[0]) * 2) +
                            Double.parseDouble(Str3);
                    double V1 = Double.parseDouble(v1);
                    double V2 = Double.parseDouble(v2);

                    if (Common.LogicFun(Common.compare(a, op1, V1), LOGIC1, Common.compare(a, op2, V2))) {
                        return (int) a;
                    }
                }
            } else {
                for (int i = 0; i < Strlist1.length; i++) {
                    for (int j = 0; j < Strlist2.length; j++) {
                        String[] strlist_1 = Strlist1[i].replace(")", "").split(",");
                        String[] strlist_2 = Strlist2[j].replace(")", "").split(",");

                        if (Double.parseDouble(strlist_1[0]) == Double.parseDouble(strlist_2[0])) {
                            double a = (Double.parseDouble(strlist_1[1]) * 2) +
                                    (Double.parseDouble(strlist_2[1]) * 2) +
                                    Double.parseDouble(Str3);
                            double V1 = Double.parseDouble(v1);
                            double V2 = Double.parseDouble(v2);

                            if (Common.LogicFun(Common.compare(a, op1, V1), LOGIC1, Common.compare(a, op2, V2))) {
                                return (int) a;
                            }

                        }
                    }
                }
            }
            return 0;

        } catch (Exception e) {
            return 0;
        }
    }
}