package rosas.dataprocessor;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Zhuang on 2015/8/24.
 */
public class Average_Interference_Level_UDFTEST {
    Average_Interference_Level_UDF ave;
    @Test
    public void evaluatesExpression() {
        ave = new Average_Interference_Level_UDF();
        try {
            String[] row = new String[]{"", "10", "10", "10", null, "10", "10", "10", "10", "10", "10", "10","", "10", "10", "10", null, "10", "10", "10", "10", "10", "10", "10"};
            double a = ave.evaluate(row);
            Assert.assertEquals(10.0, a);
        } catch (Exception e) {
        }
    }

}
