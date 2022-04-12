package Modules;

import Interfaces.Entities.MovableEntityInterface;

import java.util.UUID;

public class Player implements MovableEntityInterface {

    private final UUID uuid = UUID.randomUUID();

    private final Cell cell;


    public Player(Cell cell) {
        this.cell = cell;
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public void move(Directions directions) {

    }
}
