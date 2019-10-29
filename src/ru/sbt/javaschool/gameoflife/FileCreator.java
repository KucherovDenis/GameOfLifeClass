package ru.sbt.javaschool.gameoflife;

import java.io.IOException;

public class FileCreator implements GameCreator {

    private final Loader loader;

    public FileCreator(String fileName) {
        loader = new GenerationLoader(fileName);
    }

    @Override
    public GenerationBroker getFirstGeneration() throws IOException {
        return loader.getGeneration();
    }
}
