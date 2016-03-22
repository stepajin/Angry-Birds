package cz.fit.dpo.mvcshooter.model;

import cz.fit.dpo.mvcshooter.model.entities.Cannon;
import cz.fit.dpo.mvcshooter.model.entities.Enemy;
import cz.fit.dpo.mvcshooter.model.entities.Missile;
import cz.fit.dpo.mvcshooter.model.entities.Collision;
import cz.fit.dpo.mvcshooter.model.entities.EntityFactory;
import cz.fit.dpo.mvcshooter.model.entities.GameObject;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Jindrich Stepanek
 */
public class Model {

    List<ModelObserver> observers = new ArrayList<ModelObserver>();

    private Timer timer;
    private long lastSpawnTime;

    ModelInfo info;

    Cannon cannon;
    List<Missile> missiles = new ArrayList<Missile>();
    List<Enemy> enemies = new ArrayList<Enemy>();
    List<Collision> collisions = new ArrayList<Collision>();

    private boolean stickDragged = false;
    private int draggedStickIntitialY;
    private int draggedStickPressedY;
    private boolean rubberDragged = false;

    public Model() {
        cannon = new Cannon();

        for (int i = 0; i < ModelConfig.ENEMIES_COUNT; i++) {
            addEnemy(EntityFactory.getFactory().createEnemy(ModelConfig.ENEMIES_INIT_X[i], ModelConfig.ENEMIES_INIT_Y[i]));
        }

        info = new ModelInfo(cannon.getForce(), cannon.getAngle(),
                ModelConfig.DEFAULT_GRAVITY, 0, cannon.getShootingMode().toString(), EntityFactory.getFactory().toString());

        initTimer();
    }

    public void registerObserver(ModelObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (ModelObserver mo : observers) {
            mo.modelUpdated();
        }
    }

    /**
     * ******************
     *
     * Facade
     *
     ********************
     */
    public void changeShootingState() {
        cannon.changeShootingMode();
    }

    public void shootCannon() {
        missiles.addAll(Arrays.asList(cannon.shoot()));
    }

    public boolean pressed(Point position) {
        Point rubber = cannon.getRubber();

        if (position.x < rubber.x + 10 && position.x > rubber.x - 10 && position.y < rubber.y + 10 && position.y > rubber.y - 10) {
            rubberDragged = true;
            return true;
        } else if (position.x < cannon.getX() + 20 && position.x > cannon.getX() - 20 && position.y < cannon.getY() + 70 && position.y > cannon.getY() - 5) {
            stickDragged = true;
            draggedStickIntitialY = cannon.getY();
            draggedStickPressedY = position.y;
            return true;
        }

        return false;
    }

    public void dragged(Point position) {
        if (rubberDragged) {
            cannon.moveRubber(position);
        } else if (stickDragged) {
            int diff = position.y - draggedStickPressedY;
            cannon.move(draggedStickIntitialY + diff);
        }
    }

    public void release() {
        if (rubberDragged) {
            shootCannon();
        }

        rubberDragged = false;
        stickDragged = false;
    }

    public void setInfo() {
        info.cannonAngle = cannon.getAngle();
        info.cannonForce = cannon.getForce();
        info.shootingMode = cannon.getShootingMode().toString();
        info.gameMode = EntityFactory.getFactory().toString();
    }

    /**
     * **********************
     *
     * Getters
     *
     ***********************
     */
    public int getPlaygroundWidth() {
        return ModelConfig.PLAYGROUND_WIDTH;
    }

    public int getPlaygroundHeight() {
        return ModelConfig.PLAYGROUND_HEIGHT;
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> list = new ArrayList<GameObject>();

        list.add(cannon);
        list.addAll(enemies);
        list.addAll(collisions);
        list.addAll(missiles);
        
        return list;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public List<Collision> getCollisions() {
        return collisions;
    }

    public ModelInfo getInfo() {
        return info;
    }

    /**
     * ********************
     *
     * Private logic
     *
     **********************
     */
    private void checkCollisions() {
        Iterator<Missile> missilesIterator = missiles.iterator();

        while (missilesIterator.hasNext()) {
            Missile missile = missilesIterator.next();

            Iterator<Enemy> enemiesIterator = enemies.iterator();

            while (enemiesIterator.hasNext()) {
                Enemy enemy = enemiesIterator.next();

                if (missile.collidesWith(enemy)) {
                    missilesIterator.remove();
                    enemiesIterator.remove();

                    info.score += ModelConfig.HIT_POINTS;
                    collisions.add(new Collision(enemy.getX(), enemy.getY(), Collision.HIT));
                    break;
                }
            }
        }
    }

    private void moveMissiles() {
        Iterator<Missile> it = missiles.iterator();

        while (it.hasNext()) {
            Missile missile = it.next();

            if (!missile.shouldBeDiscarted()) {
                missile.move(ModelConfig.DEFAULT_GRAVITY);
            } else {
                it.remove();
            }
        }
    }

    private void moveEnemies() {
        for (Enemy e : enemies) {
            e.move();
        }
    }

    private void removeOldCollisions() {
        Iterator<Collision> it = collisions.iterator();

        while (it.hasNext()) {
            Collision collision = it.next();

            if (collision.shouldBeDiscarted()) {
                it.remove();
            }
        }
    }

    private void removeOldEnemies() {
        Iterator<Enemy> it = enemies.iterator();

        while (it.hasNext()) {
            Enemy enemy = it.next();

            if (enemy.shouldBeDiscarted()) {
                it.remove();
                collisions.add(new Collision(enemy.getX(), enemy.getY(), Collision.MISSED));
                info.score -= ModelConfig.MISSED_NEGATIVE_POINTS;
            }
        }
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void spawnEnemy() {
        Enemy e = EntityFactory.getFactory().createEnemyRandom();
        addEnemy(e);
    }

    /**
     * ******************************
     *
     * Private initialization
     *
     ********************************
     */
    private void initTimer() {
        lastSpawnTime = System.currentTimeMillis();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                moveMissiles();
                moveEnemies();
                checkCollisions();
                removeOldCollisions();
                removeOldEnemies();

                long time = System.currentTimeMillis();
                if ((time - lastSpawnTime) > ModelConfig.ENEMY_SPAWN_TIME) {
                    spawnEnemy();
                    lastSpawnTime = System.currentTimeMillis();
                }

                setInfo();
                notifyObservers();
            }
        }, 0, ModelConfig.TICK_TIME);
    }

    /**
     * ***************
     *
     * Memento
     *
     ******************
     */
    private final CheckpointCaretaker checkpointCaretaker = new CheckpointCaretaker();

    public void save() {
        checkpointCaretaker.addCheckpoint(new CheckpointMemento(cannon));
    }

    public void load() {
        if (!checkpointCaretaker.hasCheckpoint()) {
            return;
        }

        CheckpointMemento memento = checkpointCaretaker.getCheckpoint();
        this.cannon = memento.cannon;
    }

    private static class CheckpointCaretaker {

        Stack<CheckpointMemento> stack = new Stack<CheckpointMemento>();

        public void addCheckpoint(CheckpointMemento m) {
            stack.add(m);
        }

        public boolean hasCheckpoint() {
            return !stack.isEmpty();
        }

        public CheckpointMemento getCheckpoint() {
            if (hasCheckpoint()) {
                return stack.pop();
            } else {
                return null;
            }
        }
    }

    private static class CheckpointMemento {

        Cannon cannon;

        public CheckpointMemento(Cannon c) {
            this.cannon = new Cannon();
            this.cannon.move(c.getY());
            this.cannon.moveRubber(c.getRubber());
        }
    }
}
