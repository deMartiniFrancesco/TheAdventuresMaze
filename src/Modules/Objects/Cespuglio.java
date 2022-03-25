package Modules.Objects;

import Interfaces.Objects.BreakableObjectInterface;
import Interfaces.Objects.InteractiveObjectInterface;
import Interfaces.Objects.SpawnabeObjectInterface;
import Modules.Coordinate;

import java.util.UUID;

public class Cespuglio implements InteractiveObjectInterface, BreakableObjectInterface , SpawnabeObjectInterface {

    private final UUID uuid = UUID.randomUUID();

    private final Coordinate coordinate;


    public Cespuglio(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Runnable getBreakAction() {
        return null;
    }

    @Override
    public Runnable getAction() {
        return null;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }
}
