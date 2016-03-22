package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.ModelConfig;
import java.util.Random;

/**
 *
 * @author jindra
 */
public abstract class EntityFactory {
    
    private static EntityFactory FACTORY = RealisticEntityFactory.getInstance();
    
    public static final EntityFactory getFactory() {
        return FACTORY;
    }
    
    public static final void setFactory(EntityFactory factory) {
        EntityFactory.FACTORY = factory;    
    }

    public final Enemy createEnemyRandom() {
        int xRange = ModelConfig.PLAYGROUND_WIDTH - ModelConfig.CANNON_X - 50;

        Random r = new Random();
        int x = ModelConfig.PLAYGROUND_WIDTH - r.nextInt(xRange);
        int y = r.nextInt(ModelConfig.PLAYGROUND_HEIGHT - 50) + 25;
        
        return createEnemy(x, y);
    }   
    
    public abstract Enemy createEnemy(int x, int y);

    public abstract Missile createMissile(int x, int y, int force, int angle);

    /**
     * ***********
     *
     *  Simple
     *
     ************
     */
    public static class SimpleEntityFactory extends EntityFactory {

        private static SimpleEntityFactory INSTANCE = null;

        public static SimpleEntityFactory getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new SimpleEntityFactory();
            }

            return INSTANCE;
        }

        private SimpleEntityFactory() {
        }

        @Override
        public Enemy createEnemy(int x, int y) {
            return new Enemy(x, y, new Enemy.StaticMovementStrategy());
        }

        @Override
        public Missile createMissile(int x, int y, int force, int angle) {
            Missile.MovementStrategy strategy = new Missile.SimpleMovementStrategy(x, y, force, angle);
            return new Missile(x, y, strategy);
        }
        
        @Override
        public String toString() {
            return "Simple";
        }
    }

    /**
     * ***********
     *
     * Stupid
     *
     ************
     */
    public static class StupidEntityFactory extends EntityFactory {

        private static StupidEntityFactory INSTANCE = null;

        public static StupidEntityFactory getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new StupidEntityFactory();
            }

            return INSTANCE;
        }

        private StupidEntityFactory() {
        }

        @Override
        public Enemy createEnemy(int x, int y) {
            return new Enemy(x, y, new Enemy.StaticMovementStrategy());
        }

        @Override
        public Missile createMissile(int x, int y, int force, int angle) {
            Missile.MovementStrategy strategy = new Missile.StupidMovementStrategy(x, y, force, angle);
            return new Missile(x, y, strategy);
        }
        
        @Override
        public String toString() {
            return "Stupid";
        }
    }

    /**
     * *************
     *
     * Realistic
     *
     **************
     */
    public static class RealisticEntityFactory extends EntityFactory {

        private static RealisticEntityFactory INSTANCE = null;

        public static RealisticEntityFactory getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new RealisticEntityFactory();
            }

            return INSTANCE;
        }

        private RealisticEntityFactory() {
        }

        @Override
        public Enemy createEnemy(int x, int y) {
            return new Enemy(x, y, new Enemy.UpDownMovementStrategy());
        }

        @Override
        public Missile createMissile(int x, int y, int force, int angle) {
            Missile.MovementStrategy strategy = new Missile.SimpleMovementStrategy(x, y, force, angle);
            return new Missile(x, y, strategy);
        }
        
        @Override
        public String toString() {
            return "Realistic";
        }

    }

}
