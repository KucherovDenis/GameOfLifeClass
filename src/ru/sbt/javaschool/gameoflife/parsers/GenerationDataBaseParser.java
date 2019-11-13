package ru.sbt.javaschool.gameoflife.parsers;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.Arrays;
import java.util.List;

public class GenerationDataBaseParser extends GenerationBaseParser {

    private static final String NEW_LINE = "\n";

    private List<String> prepare(String str) {
        String[] strings = str.split(NEW_LINE);
       return Arrays.asList(strings);
    }

    @Override
    public GenerationBroker parsing(List<String> strings) {
        if (strings == null || strings.size() == 0) return null;
        List<String> stringList = prepare(strings.get(0));
        return super.parsing(stringList);
    }
}
