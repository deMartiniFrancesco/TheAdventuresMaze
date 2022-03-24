package Action;

import Interfaces.Items.ItemStack;

public class ObjectDropAction implements Runnable{

    private final ItemStack itemStack;


    public ObjectDropAction(ItemStack itemStack) {

        this.itemStack = itemStack;

    }


    @Override
    public void run() {

    }
}
