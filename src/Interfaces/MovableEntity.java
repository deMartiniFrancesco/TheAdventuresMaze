package Interfaces;

import Modules.Cell;
import Modules.Directions;

public interface MovableEntity {

    Cell getCell();

    void move(Directions directions);

}
