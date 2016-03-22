package cz.fit.dpo.mvcshooter.view;

import cz.fit.dpo.mvcshooter.controller.Controller;
import cz.fit.dpo.mvcshooter.model.Model;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Ondrej Stuchlik
 */
public class MainWindow extends JFrame {

    public static final String CHECKPOINT_MENU = "Checkpoint";
    public static final String SAVE_CHECKPOINT = "Save";
    public static final String RETURN_TO_CHECKPOINT = "Return";
    
    
    public static final String GAME_MODE = "Mode";
    public static final String REALISTIC_GAME_MODE = "Realistic";
    public static final String SIMPLE_GAME_MODE = "Simple";
    public static final String STUPID_GAME_MODE = "Stupid";

    private final Controller controller;

    public MainWindow(Model model, final Controller controller) {
        this.controller = controller;

        try {
            controller.setView(this);
            Canvas view = new Canvas(
                    0, 0, model.getPlaygroundWidth(), model.getPlaygroundHeight(), model);

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("MyShooter");
            this.setResizable(false);

            Dimension obrazovka = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(
                    (int) (obrazovka.getWidth() / 2 - 250),
                    (int) (obrazovka.getHeight() / 2 - 250));

            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            JMenu checkpointMenu = new JMenu(CHECKPOINT_MENU);
            JMenuItem newCheckpointAction = new JMenuItem(SAVE_CHECKPOINT);
            JMenuItem returnCheckpointAction = new JMenuItem(RETURN_TO_CHECKPOINT);

            newCheckpointAction.addActionListener(new MenuItemListener(SAVE_CHECKPOINT));
            returnCheckpointAction.addActionListener(new MenuItemListener(RETURN_TO_CHECKPOINT));

            checkpointMenu.add(newCheckpointAction);
            checkpointMenu.add(returnCheckpointAction);
            menuBar.add(checkpointMenu);

            JMenu gameModeMenu = new JMenu(GAME_MODE);
            JMenuItem realisticCheckpointAction = new JMenuItem(REALISTIC_GAME_MODE);
            JMenuItem simpleCheckpointAction = new JMenuItem(SIMPLE_GAME_MODE);
            JMenuItem stupidCheckpointAction = new JMenuItem(STUPID_GAME_MODE);

            realisticCheckpointAction.addActionListener(new MenuItemListener(REALISTIC_GAME_MODE));
            simpleCheckpointAction.addActionListener(new MenuItemListener(SIMPLE_GAME_MODE));
            stupidCheckpointAction.addActionListener(new MenuItemListener(STUPID_GAME_MODE));

            gameModeMenu.add(realisticCheckpointAction);
            gameModeMenu.add(simpleCheckpointAction);
            gameModeMenu.add(stupidCheckpointAction);
            menuBar.add(gameModeMenu);

            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    controller.keyPressed(evt);
                }
            });

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    controller.mouseDragged(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    controller.mousePressed(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    controller.mouseReleased(e);
                }
            };

            this.addMouseListener(mouseAdapter);
            this.addMouseMotionListener(mouseAdapter);

            this.add(view);
            this.pack();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void showHelp() {
        JOptionPane.showMessageDialog(this,
                "Controls: \n"
                + "arrows up/down     cannon vertical movement\n"
                + "arrows left/right     cannon force\n"
                + "page up/down        cannon angle\n"
                + "space                       shoot\n"
                + "home/end                gravity up/down");
    }

    private class MenuItemListener implements ActionListener {

        private String item;

        private MenuItemListener(String item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            controller.menuActionSelected(item);
        }
    }
}
