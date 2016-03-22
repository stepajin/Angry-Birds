package cz.fit.dpo.mvcshooter.model;

import org.junit.Assert;
import org.junit.Test;



/**
 *
 * @author jindra
 */
public class ModelObserverTest {

    boolean called = false;

    public ModelObserverTest() {
    }

    @Test
    public void testModelObserver() throws InterruptedException {
        ModelObserver ob = new ModelObserver() {

            @Override
            public void modelUpdated() {
                called = true;
            }
        };
        
        Model model = new Model();
        model.registerObserver(ob);
        
        Thread.sleep(100);
        
        Assert.assertTrue(called);
    }
}
