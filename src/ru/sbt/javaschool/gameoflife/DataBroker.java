package ru.sbt.javaschool.gameoflife;

public interface DataBroker {

    int getGeneration();

    Cell getCell(int x, int y);

    int getSizeX();

    int getSizeY();
}
