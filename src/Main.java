import Main.Game;

import java.io.File;


public class Main {
    public static void main(String[] args) {

        //              CALCOLO PATH RELATIVO UNIVERSALE
        //----------------------------------------------------------------------
        String tempPath = new File(
                String.valueOf(Main.class.getPackage()).replace("package ", "").replace(".", "/")
        ).getParent();
        if (tempPath == null) {
            tempPath = "";
        }
        File uesrPath = new File(System.getProperty("user.dir"));
        String projectPath = uesrPath.getName().equals(tempPath) ?
                uesrPath.getPath() :
                new File(uesrPath.getPath() + "/src").exists() ?
                        uesrPath.getPath() + "/src/" + tempPath :
                        uesrPath.getPath() + tempPath;
        //----------------------------------------------------------------------

        Game game = new Game(projectPath);

        game.onDataLoad();

        if (true) {
            game.onTest();
        } else {
            game.onEnable();
        }
    }

}
