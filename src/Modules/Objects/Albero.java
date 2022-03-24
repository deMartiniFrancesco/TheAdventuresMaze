package Modules.Objects;

import Action.ObjectDropAction;
import Action.ObjectInteraction;
import Interfaces.Objects.BreakableObjectInterface;
import Interfaces.Objects.InteractiveObjectInterface;
import Interfaces.Items.ItemStack;
import Interfaces.Objects.SpawnabeObjectInterface;
import Modules.Coordinate;
import Modules.Entity.Bacca;

import java.util.Random;
import java.util.UUID;

public class Albero implements InteractiveObjectInterface, BreakableObjectInterface, SpawnabeObjectInterface {

    private final UUID uuid = UUID.randomUUID();

    private final Coordinate coordinate;
    private final ItemStack bacche;


    public Albero(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.bacche = new Bacca(new Random().nextInt(5));
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
        return new ObjectDropAction(bacche);
    }
}




