package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.io.*;

import java.util.Objects;
import java.util.concurrent.Callable;

public class ThreadLoader implements Callable<GenerationBroker> {

    private final String fileName;
    private GenerationLoader genLoader;


    public ThreadLoader(String fileName) {
        this.fileName = Objects.requireNonNull(fileName);
        genLoader = LoaderService.getLoader(this.fileName);
    }

    @Override
    public GenerationBroker call() throws Exception {
        return genLoader.load();
    }
}
