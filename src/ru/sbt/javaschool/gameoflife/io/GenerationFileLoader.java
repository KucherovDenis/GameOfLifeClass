package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.parsers.Parser;

import java.util.List;
import java.util.Objects;

public class GenerationFileLoader implements GenerationLoader {

    private final Loader loader;
    private final Parser parser;

    public GenerationFileLoader(Loader loader, Parser parser) {
        this.loader = Objects.requireNonNull(loader);
        this.parser = Objects.requireNonNull(parser);
    }

    @Override
    public GenerationBroker load() {
        GenerationBroker result = null;
        List<String> strings = loader.load();
        result = parser.parsing(strings);
        return result;
    }
}
