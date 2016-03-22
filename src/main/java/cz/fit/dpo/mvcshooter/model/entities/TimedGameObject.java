package cz.fit.dpo.mvcshooter.model.entities;

/**
 *
 * @author Ondrej Stuchlik
 */
public abstract class TimedGameObject extends GameObject {
    
    private long created;

    public TimedGameObject(int x, int y) {
        super(x, y);
        created = System.currentTimeMillis();
    }
    
    protected boolean shouldBeDiscarted(int liveTime) {
        return (System.currentTimeMillis() - created) > liveTime;
    }

}
