

package rosas.dataprocessor;

        import junit.framework.Assert;
        import org.junit.Test;

/**
 * Created by Zhuang on 2015/8/3.
 */
public class All_IF_Type_UDAFTEST {
    All_IF_Type_UDAF.Evaluator ave;

    @Test
    public void evaluatesExpression() {
        ave = new All_IF_Type_UDAF.Evaluator();
        try {
            ave.iterate("��������");
            String v = ave.terminate();
            Assert.assertEquals("��������", v);
        } catch (Exception e) {

        }
    }

}
