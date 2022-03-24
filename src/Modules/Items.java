package Modules;

public enum Items {

    WOOD(1),
    BERRY(2),
    STONE(3);


    private final int numberId;

    Items(int numberId) {
        this.numberId = numberId;
    }

    public int getNumberId() {
        return numberId;
    }
}
