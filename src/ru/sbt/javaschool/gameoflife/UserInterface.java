package ru.sbt.javaschool.gameoflife;

public interface UserInterface {
    void init();
    void showSalutation(String message);
    GameCreator getCreator();
    void draw(DataBroker generation);
    void showEnding(String message);
    void dispose();
}
