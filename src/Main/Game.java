package Main;

import Interfaces.Application;
import Modules.Objects.Albero;
import Modules.Coordinate;
import Modules.Grid;
import Modules.GridVisualizer;
import Modules.Objects.Cespuglio;
import Modules.Objects.Sasso;
import Modules.Player.Player;
import Providers.CoordsProvider;

public class Game implements Application {

    public static Game istance;
    private CoordsProvider coordsProvider;

    public CoordsProvider getCoordsProvider() {
        return coordsProvider;
    }

    @Override
    public void onDataLoad() {
    }

    @Override
    public void onEnable(){
        istance = this;

        //Init Providers
        coordsProvider = new CoordsProvider(this);



        Grid grid = new Grid(this);

        Player player = new Player(new Coordinate(5, 5), "Frenky");

        grid.addPlayer(player);

        grid.generateObjectRandom(Albero.class, 5);

        grid.generateObjectRandom(Sasso.class, 5);

        grid.generateObjectRandom(Cespuglio.class, 5);


        System.out.println(GridVisualizer.visualizzaTable(grid));

    }



    @Override
    public void onDisable(){}



}
