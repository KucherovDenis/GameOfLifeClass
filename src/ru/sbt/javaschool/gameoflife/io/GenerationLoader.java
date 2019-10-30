package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public interface GenerationLoader {
    GenerationBroker load();
}
