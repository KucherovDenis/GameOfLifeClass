package ru.sbt.javaschool.gameoflife;

public interface GenerationBroker {

    int getCurrentGeneration();

    Cell getCell(int x, int y);

    int getSizeX();

    int getSizeY();

    int getHashCode();
}
