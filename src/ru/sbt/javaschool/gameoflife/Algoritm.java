package ru.sbt.javaschool.gameoflife;

public interface Algoritm {
    void initialize(GenerationBroker firstGeneration);
    GenerationBroker nextGeneration();
    void setCountIteration(int value);
    boolean isEnd();
}
