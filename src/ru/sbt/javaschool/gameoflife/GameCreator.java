package ru.sbt.javaschool.gameoflife;

import java.io.IOException;

public interface GameCreator {
    GenerationBroker getFirstGeneration() throws IOException;
}
