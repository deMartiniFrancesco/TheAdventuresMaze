package Modules;

import Main.Game;

public class Grid {

    private final Game main;
    private final int lenCampo = 10;

    private final Cell[] grid = new Cell[lenCampo * lenCampo];


    public Grid(Game main) {
        this.main = main;

        initGrid();

    }

    public Cell[] getGrid() {
        return grid;
    }

    public int getLenCampo() {
        return lenCampo;
    }


    private void initGrid() {
        for (int row = 0; row < lenCampo; row++) {
            for (int colun = 0; colun < lenCampo; colun++) {

            }
        }


    }
}


