package Modules.Player;

import Interfaces.Entities.InventoryEntityInterface;
import Interfaces.Entities.MovableEntityInterface;
import Interfaces.Entities.StatisticsEntityInterface;
import Modules.Coordinate;
import Modules.Directions;
import Modules.Entity.Invetory;
import Modules.Entity.Statistics;

import java.util.UUID;

public class Player implements MovableEntityInterface, InventoryEntityInterface, StatisticsEntityInterface {

    private final UUID uuid = UUID.randomUUID();

    private final String playerTag;

    private final Invetory invetory = new Invetory();
    private final Statistics statistics = new Statistics();
    private final Coordinate coordinate;


    public Player(Coordinate coordinate, String playerTag) {
        this.coordinate = coordinate;
        this.playerTag = playerTag;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Coordinate getCoords() {
        return coordinate;
    }

    public String getPlayerTag() {
        return playerTag;
    }

    @Override
    public Invetory getInventory() {
        return invetory;
    }

    @Override
    public void move(Directions directions) {

    }
}
