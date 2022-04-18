package Control.Interfaces;

import Models.Cell;
import Models.PlayerKeyAction;

public interface MovableEntity {

    Cell getCell();

    void move(PlayerKeyAction playerKeyAction);

}
