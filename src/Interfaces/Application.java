package Interfaces;

import java.util.ResourceBundle;

public interface Application {

    String getResources();
    void onDataLoad();

    void onEnable();

    void onDisable();
}
