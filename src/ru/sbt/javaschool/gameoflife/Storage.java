package ru.sbt.javaschool.gameoflife;

import java.util.Comparator;

public interface Storage {

    void add(GenerationBroker generation);
    boolean contains(GenerationBroker generation);
}
