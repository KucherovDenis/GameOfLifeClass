package ru.sbt.javaschool.gameoflife.formatters;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public interface Formatter {
    String toString(GenerationBroker generation);
    Splitter getSplitter();
}

