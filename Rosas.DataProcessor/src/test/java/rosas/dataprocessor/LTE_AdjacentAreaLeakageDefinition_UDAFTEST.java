package rosas.dataprocessor;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Zhuang on 2015/8/3.
 */
public class LTE_AdjacentAreaLeakageDefinition_UDAFTEST {
    LTE_AdjacentAreaLeakageDefinition_UDAF.Evaluator ave;

    @Test
    public void evaluatesExpression() {
        ave = new LTE_AdjacentAreaLeakageDefinition_UDAF.Evaluator();
        try {
            //String LteScRSRP, String LteNcRSRP,
            //Long total, String value1, String operator1, String value2, String operator2,
            //       String value3, String operator3
            ave.iterate("1","1",50L,"0","����","0","����","0.02","����");
            int v = ave.terminate();
            Assert.assertEquals(1, v);
        } catch (Exception e) {

        }
    }

}
