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
import java.util.Optional;
import java.util.Set;

public class SaveJson {

    private final Game game = Game.istance;
    private final Gson gson;

    public SaveJson() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public <T> Set<T> getList(FileReader reader, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(Set.class, clazz).getType();
        return gson.fromJson(reader, typeOfT);
    }

    public Set<SaveObject> getSavedObjectList() {
        try (FileReader reader = new FileReader(game.getResources() + "save.json")) {
            return getList(reader, SaveObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }


    public void saveObject(SaveObject saveObject) {
        Set<SaveObject> saveObjectList = getSavedObjectList();
        SaveObject finalSaveObject = saveObject;
        Optional<SaveObject> objectSaved = saveObjectList.stream()
                .filter(save -> save.equals(finalSaveObject))
                .findFirst();
        if (objectSaved.isPresent()) {
            //sovrascrittura se record

            SaveObject savedObject = objectSaved.get();
            //controllo per ogni livello in player se é record
            boolean edit = false;
            for (TimeLevel record : saveObject.timeLevels()) {
                if (savedObject.timeLevels().removeIf(timeLevel -> timeLevel.levelNumber() == record.levelNumber() && timeLevel.time() > record.time())) {
                    edit = true;
                }
                savedObject.timeLevels().add(record);
            }
            boolean finalEdit = edit;
            saveObjectList.removeIf(savedObject1 -> finalEdit && savedObject1.equals(savedObject));
            saveObject = savedObject;

        }

        saveObjectList.add(saveObject);

        saveOnFile(saveObjectList.toArray(SaveObject[]::new));

    }

    public void saveOnFile(SaveObject[] saveObjects) {

        try (FileWriter writer = new FileWriter(game.getResources() + "save.json")) {
            gson.toJson(saveObjects, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
