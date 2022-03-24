package Modules.Entity;

import Interfaces.Items.ItemStack;
import Modules.Items;

import java.util.UUID;

public class Bacca implements ItemStack {

    private final UUID uuid = UUID.randomUUID();
    private final int amount;


    public Bacca(int amount) {
        this.amount = amount;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Items getItemEnum() {
        return Items.BERRY;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}