package cz.fit.dpo.mvcshooter.view;

import cz.fit.dpo.mvcshooter.model.entities.Cannon;
import cz.fit.dpo.mvcshooter.model.entities.Collision;
import cz.fit.dpo.mvcshooter.model.entities.Enemy;
import cz.fit.dpo.mvcshooter.model.entities.Missile;

/**
 *
 * @author jindra
 */
public interface EntityVisitor {

    public void draw(Cannon cannon);
    public void draw(Enemy enemy);
    public void draw(Collision collision);
    public void draw(Missile missile);
}
