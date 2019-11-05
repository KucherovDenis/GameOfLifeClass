package ru.sbt.javaschool.gameoflife.algorithms;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public interface Algorithm {
    void initialize(GenerationBroker firstGeneration);

    GenerationBroker nextGeneration();

    boolean isEnd();
}
