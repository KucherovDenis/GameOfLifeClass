package ru.sbt.javaschool.gameoflife.entities;

import java.io.Serializable;

public interface GenerationBroker extends Serializable {

    int getCurrentGeneration();

    Cell getCell(int x, int y);

    int getSizeX();

    int getSizeY();

    int getHashCode();
}
