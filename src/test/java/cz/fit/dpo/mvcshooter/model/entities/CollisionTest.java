package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.Model;
import cz.fit.dpo.mvcshooter.model.ModelConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jindra
 */
public class CollisionTest {

    Collision c = new Collision(0, 0, 1);

    @Test
    public void testDiscard() throws InterruptedException {
        Model model = new Model();

        Assert.assertFalse(c.shouldBeDiscarted());
        model.getCollisions().add(c);

        Thread.sleep(ModelConfig.COLLISION_LIVE_TIME + 1000);

        Assert.assertTrue(c.shouldBeDiscarted());
    }
}
