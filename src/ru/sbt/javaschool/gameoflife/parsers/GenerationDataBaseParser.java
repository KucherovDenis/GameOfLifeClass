package ru.sbt.javaschool.gameoflife.parsers;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.Arrays;
import java.util.List;

public class GenerationDataBaseParser extends GenerationBaseParser {

    private static final String NEW_LINE = "\n";

    private List<String> prepare(String str) {
       return Arrays.asList(str.split(NEW_LINE));
    }

    @Override
    public GenerationBroker parsing(List<String> strings) {
        if (strings == null || strings.size() == 0) return null;
        return super.parsing(prepare(strings.get(0)));
    }
}
