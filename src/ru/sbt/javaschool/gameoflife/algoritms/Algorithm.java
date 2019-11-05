package ru.sbt.javaschool.gameoflife.algoritms;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public interface Algorithm {
    void initialize(GenerationBroker firstGeneration);

    GenerationBroker nextGeneration();

    void setCountIteration(int value);

    boolean isEnd();
}
