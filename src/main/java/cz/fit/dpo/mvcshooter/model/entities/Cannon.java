package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.ModelConfig;
import cz.fit.dpo.mvcshooter.view.EntityVisitor;
import java.awt.Point;

/**
 *
 * @author Ondrej Stuchlik
 */
public class Cannon extends GameObject {

    private final Point rubber;

    private int angle;
    private int force;

    private int activeShoootingMode = 0;
    private ShootingMode[] shootingModes = new ShootingMode[]{new SingleShootingMode(), new DoubleShootingMode()};

    public Cannon() {
        super(ModelConfig.CANNON_X, ModelConfig.CANNON_DEFAULT_Y);

        rubber = new Point(ModelConfig.RUBBER_DEFAULT_X, ModelConfig.RUBBER_DEFAULT_Y);
        countForce();
        countAngle();
    }

    public void changeShootingMode() {
        activeShoootingMode = (activeShoootingMode + 1) % shootingModes.length;
    }

    public ShootingMode getShootingMode() {
        return shootingModes[activeShoootingMode];
    }

    public int getForce() {
        return force;
    }

    public int getAngle() {
        return angle;
    }

    public Point getRubber() {
        return rubber;
    }

    private void countAngle() {
        double x1 = x + (x - rubber.x);
        double y1 = y + (y - rubber.y);
        double x2 = x1;
        double y2 = y;

        double a = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
        double b = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
        double cos = a / b;

        angle = (int) Math.toDegrees(Math.acos(cos));

        if (rubber.y < y) {
            angle *= -1;
        }
    }

    private void countForce() {
        int forceRange = ModelConfig.CANNON_MAX_FORCE - ModelConfig.CANNON_MIN_FORCE;
        double rubberLength = Math.sqrt((rubber.x - x) * (rubber.x - x)
                + (rubber.y - y) * (rubber.y - y));

        double ratio = rubberLength / ModelConfig.RUBBER_MAX_LENGTH;

        force = (int) (forceRange * ratio);
    }
    
        @Override
    public void accept(EntityVisitor visitor) {
        visitor.draw(this);
    }


    public void moveRubber(Point position) {
        double length = Math.sqrt((position.x - x) * (position.x - x)
                + (position.y - y) * (position.y - y));

        if (length > ModelConfig.RUBBER_MAX_LENGTH || position.x > this.x) {
            return;
        }

        rubber.setLocation(position);

        countAngle();
        countForce();
    }

    public void move(int y) {
        if (y > ModelConfig.PLAYGROUND_HEIGHT - 30 || y < 30) {
            return;
        }

        int diff = y - this.y;

        this.y = y;
        rubber.y += diff;
    }

    public Missile[] shoot() {
        Missile.MovementStrategy strategy = new Missile.SimpleMovementStrategy(x, y - 20, force, angle);
        return getShootingMode().shoot(x, y - 20, angle, force);
    }

    public static interface ShootingMode {

        public Missile[] shoot(int x, int y, int angle, int force);
    }

    public static class SingleShootingMode implements ShootingMode {

        @Override
        public Missile[] shoot(int x, int y, int angle, int force) {
            return new Missile[]{
                EntityFactory.getFactory().createMissile(x, y, force, angle)
            };
        }

        @Override
        public String toString() {
            return "Single";
        }
    }

    
    public static class DoubleShootingMode implements ShootingMode {

        @Override
        public Missile[] shoot(int x, int y, int angle, int force) {
            return new Missile[]{
                EntityFactory.getFactory().createMissile(x, y, force, angle),
                EntityFactory.getFactory().createMissile(x, y, (int) (force * 0.8), angle)
            };
        }

        @Override
        public String toString() {
            return "Double";
        }
    }

}
