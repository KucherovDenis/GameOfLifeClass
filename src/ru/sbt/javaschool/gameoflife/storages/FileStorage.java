package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.formatters.FileFormatter;
import ru.sbt.javaschool.gameoflife.io.*;
import ru.sbt.javaschool.gameoflife.parsers.GenerationBaseParser;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileStorage extends BaseStorage implements StorageClearable {

    private final String folder;

    private final String MSG_STORAGELOAD = "Ошибка чтения данных из хранилища.";
    private final String MSG_STORAGEWRITE = "Ошибка записи данных в хранилище.";

    public FileStorage(String folder, Equals<GenerationBroker> equalsImpl) {
        super(equalsImpl);
        this.folder = Objects.requireNonNull(folder);
    }

    @Override
    public void clear() {
        List<String> files = FileUtils.readFilesFromDirectory(folder);
        for (String fileName : files) {
            final File file = new File(fileName);
            file.delete();
        }
    }

    @Override
    public void add(GenerationBroker generation) {
        String fileName = folder + "\\" + generation.getCurrentGeneration() + ".txt";
        try {
            GenerationWriter writer = new GenerationFileWriter(new FileSaver(fileName), new FileFormatter());
            writer.write(generation);
        } catch (GameException e) {
            throw new GameException(MSG_STORAGEWRITE, e);
        }
    }

    private GenerationBroker load(String fileName) {
        GenerationBroker result = null;

        try {
            GenerationLoader loader = new GenerationFileLoader(new FileLoader(fileName), new GenerationBaseParser());
            result = loader.load();
        } catch (GameException e) {
            throw new GameException(MSG_STORAGELOAD, e);
        }
        return result;
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;
        List<String> files = FileUtils.readFilesFromDirectory(folder);

        for (int i = files.size() - 1; i != 0; i--) {
            String fileName = files.get(i);
            GenerationBroker current = load(fileName);
            result = equalsTo(generation, current);
            if (result) break;
        }


        return result;
    }
}
