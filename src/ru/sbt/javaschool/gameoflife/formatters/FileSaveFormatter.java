package ru.sbt.javaschool.gameoflife.formatters;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public class FileSaveFormatter extends FileFormatter {

    @Override
    public String toString(GenerationBroker generation) {
        String result = String.format("%d %d %d", generation.getSizeX(), generation.getSizeY(), generation.getCurrentGeneration());
        result += NEW_LINE;
        result += super.toString(generation);
        return result;
    }
}
