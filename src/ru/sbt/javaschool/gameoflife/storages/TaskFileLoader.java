package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.io.GenerationLoader;

import java.util.Objects;
import java.util.concurrent.Callable;

class TaskFileLoader implements Callable<GenerationBroker> {

    private GenerationLoader genLoader;


    public TaskFileLoader(String fileName) {
        String file = Objects.requireNonNull(fileName);
        genLoader = LoaderFileService.getLoader(file);
    }

    @Override
    public GenerationBroker call() {
        return genLoader.load();
    }
}
