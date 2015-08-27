package rosas.dataprocessor;

import java.util.List;

/**
 * Created by Zhuang on 2015/8/6.
 */
public class CommonFunction {
    public static boolean compare(double temp1, String op, double temp2) {
        switch (OPERATOR.toOPERATOR(op)) {
            case GT:
                return temp1 > temp2;
            case LT:
                return temp1 < temp2;
            case GE:
                return temp1 >= temp2;
            case LE:
                return temp1 <= temp2;
            case EQ:
                return temp1 == temp2;
            case NE:
                return temp1 != temp2;
            default:
                return false;
        }
    }

    public enum OPERATOR {
        GT, LT, GE, LE, EQ, NE;
        public static OPERATOR toOPERATOR(String str) {
            return valueOf(str);
        }
    }

    public static boolean LogicFun(boolean temp1, String logic, boolean temp2) {
        switch (LOGIC.toLOGIC(logic)) {
            case and:
                return temp1 && temp2;
            case or:
                return temp1 || temp2;
            default:
                return false;
        }
    }

    public enum LOGIC {
        and, or;
        public static LOGIC toLOGIC(String str) {
            return valueOf(str);
        }
    }



    public static int GetMinIndexInDoubleList(List<Double> list)
    {
        int min_index=0;
        for (int i=1;i<list.size();i++)
        {
            if (list.get(min_index)>list.get(i))
            {
                min_index=i;
            }
        }

        return  min_index;
    }


    public static int GetIndexFromList(List<Integer> list,int value)
    {
        for (int i=0;i<list.size();i++)
        {
            if (value==list.get(i))
            {
                return i;
            }
        }
        return 0;

    }



}




