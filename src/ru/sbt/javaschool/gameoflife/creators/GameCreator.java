package ru.sbt.javaschool.gameoflife.creators;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public interface GameCreator {
    GenerationBroker getFirstGeneration();
}
