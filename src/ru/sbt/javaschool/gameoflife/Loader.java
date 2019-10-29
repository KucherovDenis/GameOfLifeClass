package ru.sbt.javaschool.gameoflife;

import java.io.IOException;

public interface Loader {
    GenerationBroker getGeneration() throws IOException;
}
