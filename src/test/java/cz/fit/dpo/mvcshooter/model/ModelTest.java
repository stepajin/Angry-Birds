package cz.fit.dpo.mvcshooter.model;

import cz.fit.dpo.mvcshooter.model.entities.Enemy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author jindra
 */
public class ModelTest {

    private final Model model = new Model();

    public ModelTest() {
    }

    @Test
    public void testRemoveOldEnemies() throws InterruptedException {
        Enemy[] e = new Enemy[4];
        e[0] = Mockito.mock(Enemy.class);
        e[1] = Mockito.mock(Enemy.class);
        e[2] = Mockito.mock(Enemy.class);
        e[3] = Mockito.mock(Enemy.class);

        when(e[0].shouldBeDiscarted()).thenReturn(false);
        when(e[1].shouldBeDiscarted()).thenReturn(true);
        when(e[2].shouldBeDiscarted()).thenReturn(true);
        when(e[3].shouldBeDiscarted()).thenReturn(false);
        
        model.addEnemy(e[0]);
        model.addEnemy(e[1]);
        model.addEnemy(e[2]);
        model.addEnemy(e[3]);
        
        Thread.sleep(100);
        
        Assert.assertTrue(model.getEnemies().contains(e[0]));    
        Assert.assertFalse(model.getEnemies().contains(e[1]));    
        Assert.assertFalse(model.getEnemies().contains(e[2]));    
        Assert.assertTrue(model.getEnemies().contains(e[3]));
    }

    @Test
    public void testMemento() {
        int[] y = {100, 200, 300};

        model.save();
        model.getCannon().move(y[0]);
        model.save();
        model.getCannon().move(y[1]);
        model.save();
        model.getCannon().move(y[2]);

        Assert.assertEquals(y[2], model.getCannon().getY());
        model.load();
        Assert.assertEquals(y[1], model.getCannon().getY());
        model.load();
        Assert.assertEquals(y[0], model.getCannon().getY());
        model.load();
        Assert.assertEquals(ModelConfig.CANNON_DEFAULT_Y, model.getCannon().getY());
    }
}
