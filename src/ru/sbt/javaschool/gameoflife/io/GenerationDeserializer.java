package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;


public class GenerationDeserializer implements GenerationLoader {

    private final String fileName;

    public GenerationDeserializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public GenerationBroker load() {
        return FileUtils.deserialize(fileName, GenerationBroker.class);
    }
}
