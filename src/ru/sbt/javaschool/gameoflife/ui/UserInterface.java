package ru.sbt.javaschool.gameoflife.ui;

import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public interface UserInterface {
    void init();
    void showMessage(String message);
    GameCreator getCreator();
    void draw(GenerationBroker generation);
    void close();
    void showErrorMessage(String message);
}
