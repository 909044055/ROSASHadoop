package rosas.dataprocessor;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Zhuang on 2015/8/4.
 */
public class TDS_AdjacentAreaLeakageDefinition_UDAFTEST {
    TDS_AdjacentAreaLeakageDefinition_UDAF.Evaluator ave;

    @Test
    public void evaluatesExpression() {
        ave = new TDS_AdjacentAreaLeakageDefinition_UDAF.Evaluator();
        try {
            ave.iterate("1","����","-1",5L,"����","0.2");
            int v = ave.terminate();
            Assert.assertEquals(1, v);
        } catch (Exception e) {

        }
    }


}
