package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;


public class GenerationSerializer implements GenerationWriter {

    private final String fileName;

    public GenerationSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write(GenerationBroker generation) {
        FileUtils.serialize(fileName, generation);
    }
}
