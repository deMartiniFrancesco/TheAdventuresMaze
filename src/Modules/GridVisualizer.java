package Modules;

import Modules.Objects.Albero;
import Modules.Objects.Sasso;
import Modules.Player.Player;

public class GridVisualizer {

    enum SKINS {
        PLAYER('P'),
        ALBERO('A'),
        SASSO('S');

        private final char skin;

        SKINS(char skin) {
            this.skin = skin;
        }

        public char getSkin() {
            return skin;
        }
    }

    public static String visualizzaTable(Grid grid) {

        StringBuilder output = new StringBuilder();
        for (int row = 0; row < grid.getLenCampo(); row++) {
            output.append("| ");
            for (int column = 0; column < grid.getLenCampo(); column++) {
                Object element = grid.getCampo().get(row, column);
                if (element != null) {
                    if (element instanceof Player) {
                        output.append(SKINS.PLAYER.getSkin());
                    } else if (element instanceof Albero) {
                        output.append(SKINS.ALBERO.getSkin());
                    }else if (element instanceof Sasso) {
                        output.append(SKINS.SASSO.getSkin());
                    }
                }
                output.append(" | ");
            }
            output.append("\n");
        }
        return output.toString();
    }


}
