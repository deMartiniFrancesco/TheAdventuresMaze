package Saving;

import Main.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class SaveJson {

    private final Game game = Game.istance;
    private final Gson gson;

    public SaveJson() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public <T> List<T> getList(FileReader reader, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(reader, typeOfT);
    }

    public List<SaveObject> getSavedObjectList() {
        try (FileReader reader = new FileReader(game.getResources() + "save.json")) {
            return getList(reader, SaveObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public void saveObject(SaveObject saveObject) {
        List<SaveObject> saveObjectList = getSavedObjectList();
        if (saveObjectList
                .stream()
                .map(SaveObject::name)
                .anyMatch(s -> s.equalsIgnoreCase(saveObject.name()))
        ) {
            //sovrascrittura se record
            Optional<SaveObject> objectSaved = saveObjectList.stream()
                    .filter(save -> save.name().equalsIgnoreCase(saveObject.name()))
                    .findFirst();
            if (objectSaved.isEmpty()) {
                return;
            }
            SaveObject savedObject = objectSaved.get();
            //controllo per ogni livello in player se é record
            


        } else {
            //salvataggio nuovo giocatore
        }

    }

    public void saveOnFile(SaveObject[] saveObjects) {

        try (FileWriter writer = new FileWriter(game.getResources() + "save.json")) {
            gson.toJson(saveObjects, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
