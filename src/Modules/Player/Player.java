package Modules.Player;

import Interfaces.Entities.MovableEntityInterface;
import Modules.Cell;
import Modules.Directions;
import Modules.Entity.Invetory;

import java.util.UUID;

public class Player implements MovableEntityInterface {

    private final UUID uuid = UUID.randomUUID();

    private final String playerTag;

    private final Invetory invetory = new Invetory();
    private final Cell cell;


    public Player(Cell cell, String playerTag) {
        this.cell = cell;
        this.playerTag = playerTag;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Cell getCoords() {
        return cell;
    }

    @Override
    public void move(Directions directions) {

    }
}
