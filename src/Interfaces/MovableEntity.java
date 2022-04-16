package Interfaces;

import Modules.Cell;
import Modules.PlayerKeyAction;

public interface MovableEntity {

    Cell getCell();

    void move(PlayerKeyAction playerKeyAction);

}
