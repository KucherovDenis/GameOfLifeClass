package ru.sbt.javaschool.gameoflife;

public interface DataBroker {

    int getGenerationNum();

    Cell getCell(int x, int y);

    int getSizeX();

    int getSizeY();
}
