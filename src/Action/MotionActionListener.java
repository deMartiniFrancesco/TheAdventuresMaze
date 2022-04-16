package Action;

import Interfaces.MovableEntity;
import Modules.Directions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MotionActionListener extends AbstractAction implements ActionListener {
        private final Directions direction;
        private final MovableEntity target;

        public MotionActionListener(String name, Directions directions, MovableEntity target) {
            super(name);
            this.direction = directions;
            this.target = target;
        }

        public void actionPerformed(ActionEvent e) {
            target.move(direction);
        }
    }