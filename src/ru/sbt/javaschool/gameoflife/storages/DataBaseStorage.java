package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.formatters.FileSaveFormatter;
import ru.sbt.javaschool.gameoflife.io.*;
import ru.sbt.javaschool.gameoflife.parsers.GenerationDataBaseParser;

public class DataBaseStorage extends BaseStorage implements StorageCloseable {

    private final DataBase dataBase;

    public DataBaseStorage(DataBase dataBase, Equals<GenerationBroker> equalsImpl) {
        super(equalsImpl);
        this.dataBase = dataBase;
    }

    @Override
    public void add(GenerationBroker generation) {
        GenerationWriter writer = new GenerationBaseWriter(dataBase.getWriter(), new FileSaveFormatter());
        writer.write(generation);
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;
        GenerationLoader reader = new GenerationBaseLoader(dataBase.getLoader(), new GenerationDataBaseParser());
        GenerationBroker current;
        while((current = reader.load()) != null) {
            result = equalsTo(generation, current);
            if (result) break;
        }
        return result;
    }

    @Override
    public void clear() {
        dataBase.clear();
    }

    @Override
    public void close() {
        dataBase.close();
    }
}
