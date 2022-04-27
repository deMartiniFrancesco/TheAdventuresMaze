package Saving;

import Main.Game;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveJson {

    private final Game game = Game.istance;
    private final Gson gson;

    public SaveJson() {
        gson = new Gson();
    }

    public Object getObjectFromJsonFile(String jsonData) throws IOException {
//        JsonObject object = (JsonObject) JsonParser.parseString(jsonData);
//        return object;
        return gson.fromJson(Files.newBufferedReader(Paths.get(jsonData)), Object.class);
    }


    public void saveOnFile(SaveObject saveObject) {

        try (Writer writer = new FileWriter(game.getResources() + "save.json")) {
            gson.toJson(saveObject , writer);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println();
    }

}
