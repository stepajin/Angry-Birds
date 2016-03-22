package cz.fit.dpo.mvcshooter.model.entities;

import cz.fit.dpo.mvcshooter.model.ModelConfig;
import cz.fit.dpo.mvcshooter.view.EntityVisitor;

/**
 *
 * @author Ondrej Stuchlik
 */
public class Collision extends TimedGameObject {

    public static final int HIT = 1;
    public static final int MISSED = 2;
    
    int type;
    
    public Collision(int x, int y, int type) {
        super(x, y);
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
        @Override
    public void accept(EntityVisitor visitor) {
        visitor.draw(this);
    }

    
    public boolean shouldBeDiscarted() {
        return super.shouldBeDiscarted(ModelConfig.COLLISION_LIVE_TIME);
    }
    
}
