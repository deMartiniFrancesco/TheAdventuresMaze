package Action;

import Interfaces.Entities.MovableEntityInterface;
import Modules.Directions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MotionAction extends AbstractAction implements ActionListener {
        private final Directions direction;
        private final MovableEntityInterface target;

        public MotionAction(String name, Directions directions, MovableEntityInterface target) {
            super(name);
            this.direction = directions;
            this.target = target;
        }

        public void actionPerformed(ActionEvent e) {
            target.move(direction);
        }
    }