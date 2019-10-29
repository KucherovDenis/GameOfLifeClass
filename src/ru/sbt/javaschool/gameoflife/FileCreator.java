package ru.sbt.javaschool.gameoflife;

import java.io.IOException;
import java.util.List;

public class FileCreator implements GameCreator {

    private final Loader loader;
    private final Parser parser;

    public FileCreator(String fileName) {
        loader = new FileLoader(fileName);
        parser = new GenerationBaseParser();
    }

    @Override
    public GenerationBroker getFirstGeneration() throws IOException {
        List<String> strings = loader.load();
        return parser.parsing(strings);
    }
}
