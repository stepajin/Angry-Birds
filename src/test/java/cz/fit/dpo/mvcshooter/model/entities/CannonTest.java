package cz.fit.dpo.mvcshooter.model.entities;

import java.awt.Point;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author jindra
 */
public class CannonTest {
    
    private final Cannon cannon = new Cannon();
    
    public CannonTest() {
    }
    
    @Test
    public void testMove() {
        int y = 150;
        cannon.move(y);
        Assert.assertEquals(y, cannon.getY());
    }
    
    @Test
    public void testShootingMode() {
        Missile [] m = cannon.shoot();
        Assert.assertEquals(1, m.length);
        cannon.changeShootingMode();
        m = cannon.shoot();
        Assert.assertEquals(2, m.length);
    }
    
    @Test
    public void moveRubberTest() {
        cannon.moveRubber(new Point(cannon.getX(), cannon.getY() + 10));
        int angle = cannon.getAngle();
        Assert.assertEquals(90, angle);
    
        int force = cannon.getForce();
        cannon.moveRubber(new Point(cannon.getX() + 20, cannon.getY()));
        Assert.assertTrue(cannon.getForce() > force);
        cannon.moveRubber(new Point(cannon.getX() + 5, cannon.getY()));
        Assert.assertTrue(cannon.getForce() < force);
    }
}
