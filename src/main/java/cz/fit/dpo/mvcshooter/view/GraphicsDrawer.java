package cz.fit.dpo.mvcshooter.view;

import cz.fit.dpo.mvcshooter.model.ModelInfo;
import cz.fit.dpo.mvcshooter.model.entities.Cannon;
import cz.fit.dpo.mvcshooter.model.entities.Collision;
import cz.fit.dpo.mvcshooter.model.entities.Enemy;
import cz.fit.dpo.mvcshooter.model.entities.Missile;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Ondrej Stuchlik
 */
public class GraphicsDrawer implements EntityVisitor {

    private static final int INFO_X = 5;
    private static final int INFO_Y = 15;
    private static final int RUBBER_MAX_WIDTH = 70;

    private BufferedImage cannonImage;
    private BufferedImage enemyImage1;
    private BufferedImage enemyImage2;
    private BufferedImage missileImage;
    private BufferedImage hitCollisionImage;
    private BufferedImage missedCollisionImage;

    private Graphics g;
    
    public GraphicsDrawer() {
        try {
            cannonImage = ImageIO.read(getClass().getResourceAsStream("/images/cannon.png"));
            enemyImage1 = ImageIO.read(getClass().getResourceAsStream("/images/enemy1.png"));
            enemyImage2 = ImageIO.read(getClass().getResourceAsStream("/images/enemy2.png"));
            missileImage = ImageIO.read(getClass().getResourceAsStream("/images/missile.png"));
            hitCollisionImage = ImageIO.read(getClass().getResourceAsStream("/images/collision.png"));
            missedCollisionImage = ImageIO.read(getClass().getResourceAsStream("/images/missed.png"));

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    public void setGraphics(Graphics g) {
        this.g = g;
    }

    public void draw(Cannon cannon) {
        Point rubber = cannon.getRubber();
        g.drawLine(cannon.getX() - 15, cannon.getY() - 3, rubber.x, rubber.y);
        g.drawLine(cannon.getX() + 15, cannon.getY() - 3, rubber.x, rubber.y);
        g.fillOval(rubber.x - 5, rubber.y - 5, 10, 10);

        g.drawImage(cannonImage,
                cannon.getX() - cannonImage.getWidth() / 2,
                cannon.getY() - cannonImage.getHeight() / 10, null);
    
    }

    public void draw(Missile missile) {
        g.drawImage(missileImage,
                missile.getX() - missileImage.getWidth() / 2,
                missile.getY() - missileImage.getHeight() / 2, null);
    }

    public void draw(Enemy enemy) {
        Image usedImage = enemy.getType() == 0 ? enemyImage1 : enemyImage2;
        g.drawImage(usedImage,
                enemy.getX() - enemyImage1.getWidth() / 2,
                enemy.getY() - enemyImage1.getHeight() / 2, null);
    }

    public void draw(Collision collision) {
        Image usedImage = (collision.getType() == Collision.HIT ? hitCollisionImage : missedCollisionImage);
        g.drawImage(usedImage,
                collision.getX() - hitCollisionImage.getWidth() / 2,
                collision.getY() - hitCollisionImage.getHeight() / 2, null);
    }

    public void drawInfo(ModelInfo info) {
        g.drawString(
                "Force: " + info.cannonForce
                + ", Angle: " + info.cannonAngle
                + ", Gravity: " + info.gravity
                + ", Score: " + info.score
                + ", Cannon: " + info.shootingMode
                + ", Mode: " + info.gameMode,
                INFO_X, INFO_Y);
    }
}
