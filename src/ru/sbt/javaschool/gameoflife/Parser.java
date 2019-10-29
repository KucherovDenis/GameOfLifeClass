package ru.sbt.javaschool.gameoflife;

import java.util.List;

public interface Parser {
    GenerationBroker parsing(List<String> strings);
}
