package ru.sbt.javaschool.gameoflife.parsers;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.List;

public interface Parser {
    GenerationBroker parsing(List<String> strings);
}
