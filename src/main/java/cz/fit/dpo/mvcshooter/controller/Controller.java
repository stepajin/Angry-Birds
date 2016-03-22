package cz.fit.dpo.mvcshooter.controller;

import cz.fit.dpo.mvcshooter.model.Model;
import cz.fit.dpo.mvcshooter.model.entities.EntityFactory;
import cz.fit.dpo.mvcshooter.view.MainWindow;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ondrej Stuchlik
 */
public class Controller {

    private Model model;
    private MainWindow view;

    public Controller(Model model) {
        this.model = model;
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY() - 43;

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (model.pressed(new Point(x, y))) {
                view.setCursor(Cursor.MOVE_CURSOR);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY() - 43;

        if (SwingUtilities.isLeftMouseButton(e)) {
            model.dragged(new Point(e.getX(), e.getY() - 43));
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            model.release();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            model.changeShootingState();
        }
        view.setCursor(Cursor.DEFAULT_CURSOR);
    }

    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                model.shootCannon();
                break;

            case KeyEvent.VK_HOME:
                //  model.increaseGravity();
                break;
            
            case KeyEvent.VK_END:
                //   model.decreaseGravity();
                break;

            case KeyEvent.VK_F1:
                view.showHelp();
                break;
        }
    }

    public void setView(MainWindow view) {
        this.view = view;
    }
    
    
    public void menuActionSelected(String item) {
        if (item.equals(MainWindow.SAVE_CHECKPOINT)) {
            model.save();
        } else if (item.equals(MainWindow.RETURN_TO_CHECKPOINT)) {
            model.load();
        } else if (item.equals(MainWindow.REALISTIC_GAME_MODE)) {
            EntityFactory.setFactory(EntityFactory.RealisticEntityFactory.getInstance());
        }  else if (item.equals(MainWindow.SIMPLE_GAME_MODE)) {
            EntityFactory.setFactory(EntityFactory.SimpleEntityFactory.getInstance());
        }  else if (item.equals(MainWindow.STUPID_GAME_MODE)) {
            EntityFactory.setFactory(EntityFactory.StupidEntityFactory.getInstance());
        }
    }
}
