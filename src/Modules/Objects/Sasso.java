package Modules.Objects;

import Action.ObjectDropAction;
import Action.ObjectInteraction;
import Interfaces.Items.ItemStack;
import Interfaces.Objects.BreakableObjectInterface;
import Interfaces.Objects.InteractiveObjectInterface;
import Interfaces.Objects.SpawnabeObjectInterface;
import Modules.Coordinate;
import Modules.Entity.Bacca;

import java.util.Random;
import java.util.UUID;

public class Sasso implements InteractiveObjectInterface, BreakableObjectInterface, SpawnabeObjectInterface {

    private final UUID uuid = UUID.randomUUID();

    private final Coordinate coordinate;


    public Sasso(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Runnable getAction() {
        return new ObjectInteraction();
    }

    @Override
    public Runnable getBreakAction() {
        return new ObjectDropAction(null);
    }
}




