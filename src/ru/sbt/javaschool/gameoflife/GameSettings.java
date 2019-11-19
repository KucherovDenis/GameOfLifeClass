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

import java.util.Arrays;
import java.util.List;

public final class GameSettings implements Settings {

    private final List<String> args;

    private Storage storage;
    private UserInterface ui;

    private static GameSettings instance;

    private GameSettings(String[] args) {
        this.args = Arrays.asList(args);
        storage = createStorage();
        ui = createUserInterface();
    }

    public static GameSettings getInstance(String[] args) {
        if(instance == null) {
            instance = new GameSettings(args);
        }

        return instance;
    }

    private String getValue(int index) {
        String result = null;
        if (index < args.size()) {
            result = args.get(index);
            if (result.contains("-")) result = null;
        }
        return result;
    }

    private UserInterface createWindowUI() {
        return new WindowUI();
    }

    private UserInterface createFileUI() {
        UserInterface view;
        Formatter formatter = new FileFormatter();
        int index = args.indexOf("-f");
        String fileName = getValue(index + 1);
        if (fileName != null) view = new TextFileUI(fileName, formatter);
        else throw new GameException("Не задано имя файла для вывода данных.");
        return view;
    }

    private UserInterface createConsoleUI() {
        Formatter formatter = new ConsoleFormatter();
        return new ConsoleUI(formatter);
    }

    private UserInterface createUserInterface() {
        UserInterface view;

        if (args.contains("-w")) {
            view = createWindowUI();
        } else if (args.contains("-f")) {
            view = createFileUI();
        } else {
            view = createConsoleUI();
        }

        return view;
    }

    @Override
    public boolean isHelp() {
        return args.contains("-h");
    }

    private Storage createMemoryStorage() {
        return new MemoryStorage(new GenerationEquals());
    }

    private String getName(String value) {
        String tmpValue = value.toLowerCase();
        if (!"\\t".equals(tmpValue) &&
                !"json".equals(tmpValue) &&
                !"txt".equals(tmpValue) &&
                !"xls".equals(tmpValue) &&
                !"xlsx".equals(tmpValue)) {
            return value;
        } else return null;
    }

    private FileStorageType getType(String value) {
        value = value.toUpperCase();
        FileStorageType type = null;
        switch (value) {
            case "JSON":
            case "TXT":
            case "XLS":
            case "XLSX":
                type = FileStorageType.valueOf(value);
        }
        return type;
    }

    private Storage createFileStorage() {
        Storage storage = null;
        int index = args.indexOf("-sf");
        if (index != -1) {
            String name = null;
            FileStorageType type = null;
            boolean isThread = false;
            for (int i = 1; i <= 3; i++) {
                String value = getValue(index + i);
                if (value == null) break;
                isThread = "\\t".equals(value);
                if (name == null) name = getName(value);
                if (type == null) type = getType(value);
            }

            if (name == null) name = "Storage";
            if (type == null) type = FileStorageType.NONE;
            if (isThread) {
                storage = new FileThreadStorage(name, new GenerationEquals(), type);
            } else {
                storage = new FileStorage(name, new GenerationEquals(), type);
            }
        }

        return storage;
    }

    private Storage createDataBaseStorage() {
        Storage storage = null;
        int index = args.indexOf("-sd");
        if (index != -1) {
            String fileName = getValue(index + 1);
            if(fileName == null) {
                throw new GameException("Не задано имя файла базы данных.");
            }
            storage = new DataBaseStorage(new SqliteDataBase(fileName), new GenerationEquals());
        }
        return storage;
    }

    private Storage createStorage() {
        Storage storage;
        if (args.contains("-sf")) {
            storage = createFileStorage();
        } else if (args.contains("-sd")) {
            storage = createDataBaseStorage();
        } else {
            storage = createMemoryStorage();
        }

        if (storage == null)
            storage = createMemoryStorage();
        storage.clear();
        return storage;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public UserInterface getUserInterface() {
        return ui;
    }

    @Override
    public Algorithm getAlgorithm(Storage storage) {
        return new BaseAlgorithm(storage);
    }
}
