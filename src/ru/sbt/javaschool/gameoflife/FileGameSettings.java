package ru.sbt.javaschool.gameoflife;

import ru.sbt.javaschool.gameoflife.algorithms.Algorithm;
import ru.sbt.javaschool.gameoflife.algorithms.BaseAlgorithm;
import ru.sbt.javaschool.gameoflife.entities.GenerationEquals;
import ru.sbt.javaschool.gameoflife.formatters.ConsoleFormatter;
import ru.sbt.javaschool.gameoflife.formatters.FileFormatter;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;
import ru.sbt.javaschool.gameoflife.io.SqliteDataBase;
import ru.sbt.javaschool.gameoflife.storages.*;
import ru.sbt.javaschool.gameoflife.ui.ConsoleUI;
import ru.sbt.javaschool.gameoflife.ui.TextFileUI;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;
import ru.sbt.javaschool.gameoflife.ui.WindowUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileGameSettings implements Settings {

    private Properties property;

    private Storage storage;

    private UserInterface userInterface;

    private BaseAlgorithm algorithm;

    public FileGameSettings(String fileName) {
        property = new Properties();
        try (InputStream fis = new FileInputStream(fileName)) {
            property.load(fis);
            userInterface = createUserInterface();
            storage = createStorage();
            algorithm = createAlgorithm();
        } catch (IOException e) {
            throw new GameException("Ошибка чтения настроек программы.", e);
        }
    }


    private Storage createMemoryStorage() {
        return new MemoryStorage(new GenerationEquals());
    }

    private Storage createDatabaseStorage() {
        String fileName = property.getProperty("dbStorage.fileName");
        if (fileName != null && !fileName.isEmpty())
            return new DataBaseStorage(new SqliteDataBase(fileName), new GenerationEquals());
        else throw new GameException("Не задано имя файла базы данных.");
    }

    private Storage createFileStorage() {
        Storage fileStorage = null;
        String name = property.getProperty("fileStorage.directory", "Storage");
        FileStorageType type = FileStorageType.getOf(property.getProperty("fileStorage.fileType"));
        boolean isThread = Boolean.parseBoolean(property.getProperty("fileStorage.isThread"));
        if (isThread) {
            fileStorage = new FileThreadStorage(name, new GenerationEquals(), type);
        } else {
            fileStorage = new FileStorage(name, new GenerationEquals(), type);
        }


        return fileStorage;
    }

    private Storage createStorage() {
        String storageName = property.getProperty("game.storage");
        Storage st;
        switch (storageName) {
            case "file":
                st = createFileStorage();
                break;

            case "database":
                st = createDatabaseStorage();
                break;

            default:
                st = createMemoryStorage();
        }

        return st;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    private UserInterface createWindowUI() {
        return new WindowUI();
    }

    private UserInterface createTextFileUI() {
        String fileName = property.getProperty("ui.fileName");
        if (fileName != null && !fileName.isEmpty())
            return new TextFileUI(fileName, new FileFormatter());
        else throw new GameException("Не задано имя файла для вывода данных.");
    }

    private UserInterface createConsoleUI() {
        Formatter formatter = new ConsoleFormatter();
        return new ConsoleUI(formatter);
    }

    private UserInterface createUserInterface() {
        String uiName = property.getProperty("game.ui");
        UserInterface ui;
        switch (uiName) {
            case "file":
                ui = createTextFileUI();
                break;

            case "console":
                ui = createConsoleUI();
                break;

            default:
                ui = createWindowUI();
        }

        return ui;
    }

    @Override
    public UserInterface getUserInterface() {
        return userInterface;
    }

    private BaseAlgorithm createAlgorithm() {
        return new BaseAlgorithm();
    }

    @Override
    public Algorithm getAlgorithm(Storage storage) {
        algorithm.addStorage(storage);
        return algorithm;
    }

    @Override
    public boolean isHelp() {
        return false;
    }
}
