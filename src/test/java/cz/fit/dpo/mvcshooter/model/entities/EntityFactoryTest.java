package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.Model;
import java.awt.Point;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jindra
 */
public class EntityFactoryTest {

    @Test
    public void testEntityFactory() throws InterruptedException {
        Model model = new Model();
        
        EntityFactory.setFactory(EntityFactory.SimpleEntityFactory.getInstance());
        
        Enemy e = EntityFactory.getFactory().createEnemy(100, 100);
        model.getEnemies().add(e);
        
        Thread.sleep(100);
        Point p = new Point(e.getX(), e.getY());
        Assert.assertEquals(100, p.x);
        Assert.assertEquals(100, p.y);
        
        EntityFactory.setFactory(EntityFactory.RealisticEntityFactory.getInstance());
        
        e = EntityFactory.getFactory().createEnemy(100, 100);
        model.getEnemies().add(e);
        
        Thread.sleep(100);
        p = new Point(e.getX(), e.getY());
        Assert.assertEquals(100, p.x);
        Assert.assertTrue(100 != p.y);
    }
}
