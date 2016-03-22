package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.ModelConfig;
import cz.fit.dpo.mvcshooter.view.EntityVisitor;

/**
 *
 * @author Ondrej Stuchlik
 */
public abstract class GameObject {
    protected int x,y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean collidesWith(GameObject anotherObject) {
        return Math.abs(this.x - anotherObject.x) < ModelConfig.COLLISION_MARGIN
              && Math.abs(this.y - anotherObject.y) < ModelConfig.COLLISION_MARGIN;
    }
    
    public abstract void accept(EntityVisitor visitor);
}
