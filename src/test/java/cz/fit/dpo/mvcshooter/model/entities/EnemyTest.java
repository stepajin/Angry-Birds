package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.Model;
import java.awt.Point;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author jindra
 */
public class EnemyTest {

    public EnemyTest() {
    }

    @Test
    public void testEnemyMove() throws InterruptedException {
        Model model = new Model();

        Enemy e = new Enemy(100, 100, new Enemy.StaticMovementStrategy());

        model.addEnemy(e);

        Thread.sleep(100);
        Point p = new Point(e.getX(), e.getY());
        Assert.assertEquals(100, p.x);
        Assert.assertEquals(100, p.y);

        e.setMovementStrategy(new Enemy.UpDownMovementStrategy());

        Thread.sleep(100);
        p = new Point(e.getX(), e.getY());
        Assert.assertEquals(100, p.x);
        Assert.assertTrue(100 != p.y);
    }
}
