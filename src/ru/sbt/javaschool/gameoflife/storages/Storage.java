package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;


public interface Storage {

    void add(GenerationBroker generation);
    boolean contains(GenerationBroker generation);
}
