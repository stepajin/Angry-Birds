package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.ModelConfig;
import cz.fit.dpo.mvcshooter.view.EntityVisitor;
import java.awt.Point;

/**
 *
 * @author Ondrej Stuchlik
 */
public class Missile extends GameObject {

    private static final double DEGREE_TO_RAD_MULTIPLIER = (2 * Math.PI) / 360;

    private final MovementStrategy movementStrategy;

    public Missile(int x, int y, MovementStrategy movementStrategy) {
        super(x, y);
        this.movementStrategy = movementStrategy;
    }

    public void setPosition(Point positon) {
        x = positon.x;
        y = positon.y;
    }

    public void move(int gravity) {
        setPosition(movementStrategy.move(gravity));
    }

    public boolean shouldBeDiscarted() {
        // don't discard based on y coord, it can still fall down
        return x > ModelConfig.PLAYGROUND_WIDTH;
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.draw(this);
    }

    public static abstract class MovementStrategy {

        protected final int beginVelocity;
        protected  final int beginAngle;
        protected final double beginAngleInRad;
        protected final int beginX;
        protected final int beginY;
        protected final long beginTime;

        public MovementStrategy(int beginX, int beginY, int velocity, int angle) {
            this.beginVelocity = velocity;
            this.beginAngle = angle;
            this.beginAngleInRad = angle * DEGREE_TO_RAD_MULTIPLIER;
            this.beginX = beginX;
            this.beginY = beginY;
            this.beginTime = System.currentTimeMillis();

        }
        
        public int getVelocity() {
            return beginVelocity;
        }
        
        public int getAngle() {
            return beginAngle;
        }
        
        public abstract Point move(int gravity);

    }

    public static class SimpleMovementStrategy extends MovementStrategy {

        public SimpleMovementStrategy(int beginX, int beginY, int velocity, int angle) {
            super(beginX, beginY, velocity, angle);
        }

        @Override
        public Point move(int gravity) {
            double time = (new Long(System.currentTimeMillis() - beginTime)).doubleValue();
            time /= 150;
            int x = (int) (beginX + (beginVelocity * time * Math.cos(beginAngleInRad)));
            int y = (int) (beginY - (beginVelocity * time * Math.sin(beginAngleInRad))
                    + (0.5 * gravity * Math.pow(time, 2)));

            return new Point(x, y);
        }
    }

    public static class StupidMovementStrategy extends MovementStrategy {
        
        public StupidMovementStrategy(int beginX, int beginY, int velocity, int angle) {
            super(beginX, beginY, velocity, angle);
        }

        @Override
        public Point move(int gravity) {
            double time = (new Long(System.currentTimeMillis() - beginTime)).doubleValue();
            time /= 150;
            int x = (int) (beginX + (beginVelocity * time * Math.cos(beginAngleInRad)));
            int y = beginY;
            
            return new Point(x, y);
        }
    }
}
