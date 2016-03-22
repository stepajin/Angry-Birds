package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.ModelConfig;
import cz.fit.dpo.mvcshooter.view.EntityVisitor;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author Ondrej Stuchlik
 */
public class Enemy extends TimedGameObject {

    private static final Random random = new Random();

    private int type;
    private MovementStrategy movementStrategy;

    public Enemy(int x, int y, MovementStrategy movementStrategy) {
        super(x, y);
        type = random.nextInt(2);
        this.movementStrategy = movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
    
    public boolean shouldBeDiscarted() {
        return super.shouldBeDiscarted(ModelConfig.ENEMY_LIVE_TIME);
    }

    public int getType() {
        return type;
    }

    public void move() {
        Point p = movementStrategy.move(x, y);
        x = p.x;
        y = p.y;
    }

        @Override
    public void accept(EntityVisitor visitor) {
        visitor.draw(this);
    }

    
    /**
     * 
     * *******************
     *
     * Movement strategy
     *
     ********************
     */
    public static abstract class MovementStrategy {

        public abstract Point move(int x, int y);
    }

    /**
     * *********
     *
     * Static
     *
     ***********
     */
    public static class StaticMovementStrategy extends MovementStrategy {

        @Override
        public Point move(int x, int y) {
            return new Point(x, y);
        }

    }

    /**
     * **********
     *
     * UpDown
     *
     ************
     */
    public static class UpDownMovementStrategy extends MovementStrategy {

        private static final boolean UP = true;
        private static final boolean DOWN = false;

        boolean direction = new Random().nextBoolean();;
        
        @Override
        public Point move(int x, int y) {
            Point p;
            
            if (direction == UP) {
                p = new Point(x, y - 1);
                if (y - 1 < 20) {
                    direction = DOWN;
                }
            } else {
                p = new Point(x, y + 1);
                if (y + 1 > ModelConfig.PLAYGROUND_HEIGHT - 20) {
                    direction = UP;
                }
            }
            
            return p;
        }
    }

}
