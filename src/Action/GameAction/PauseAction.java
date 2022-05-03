package Action.GameAction;

import Grafica.PausePane;
import Main.Game;
import Models.PlayerKeyAction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PauseAction implements Runnable {
    private final Game game = Game.istance;


    private final PausePane pausePane = new PausePane();


    @Override
    public void run() {
        game.isPause = !game.isPause;

        if (game.isPause) {
            game.chronometer.pause();
            pausePane.addAction(List.of(PlayerKeyAction.PAUSE.getKey(), PlayerKeyAction.PAUSE, this));
            // get the rootpane container, here the JFrame, that holds the JButton
            RootPaneContainer win = (RootPaneContainer) SwingUtilities.getWindowAncestor(game.frame);
            // create a *modal* JDialog
            JDialog dialog = new JDialog((Window)win, "", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().add(pausePane);  // add its JPanel to it
            dialog.setUndecorated(true); // give it no borders (if desired)
            dialog.pack(); // size it
            dialog.setLocationRelativeTo((Window) win); // ** Center it over the JFrame **
            dialog.setVisible(true);  // display it, pausing the GUI below it



        } else {
            game.chronometer.resume();
            Window win = SwingUtilities.getWindowAncestor(pausePane);
            win.dispose();  // here -- dispose of the JDialog

        }
    }
}
