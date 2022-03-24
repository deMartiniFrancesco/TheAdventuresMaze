package Interfaces.Items;

import Interfaces.Objects.ObjectInterface;
import Modules.Items;

public interface ItemStack extends ObjectInterface {

    Items getItemEnum();
    int getAmount();

}
