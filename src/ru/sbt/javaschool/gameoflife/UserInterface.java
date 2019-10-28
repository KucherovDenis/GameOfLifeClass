package ru.sbt.javaschool.gameoflife;

public interface UserInterface {
    void init();
    void showSalutation();
    GameCreator getCreator();
    void draw(DataBroker generation);
    void showEnding();
    void dispose();
}
