package ru.sbt.javaschool.gameoflife;

public interface UserInterface {
    void init();
    void showMessage(String message);
    GameCreator getCreator();
    void draw(GenerationBroker generation);
    void close();
    void showErrorMessage(String message);
}
