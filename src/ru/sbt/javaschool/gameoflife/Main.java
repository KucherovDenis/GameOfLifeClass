package ru.sbt.javaschool.gameoflife;


import ru.sbt.javaschool.gameoflife.algorithms.Algorithm;
import ru.sbt.javaschool.gameoflife.algorithms.BaseAlgorithm;
import ru.sbt.javaschool.gameoflife.entities.GenerationEquals;
import ru.sbt.javaschool.gameoflife.formatters.ConsoleFormatter;
import ru.sbt.javaschool.gameoflife.formatters.FileFormatter;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;
import ru.sbt.javaschool.gameoflife.storages.*;
import ru.sbt.javaschool.gameoflife.ui.ConsoleUI;
import ru.sbt.javaschool.gameoflife.ui.TextFileUI;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;
import ru.sbt.javaschool.gameoflife.ui.WindowUI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Ключ <strong>-w</strong> данные выводятся в графическом окне.</p>
 * <br\>
 * <p>Ключ <strong>-f [имя_файла]</strong> данные выводятся в текстовый файл.<br\>
 * <strong>[имя_файла]</strong> путь к файлу куда будут выводиться данные.</p>
 * <br\>
 * <p>Ключ <strong>-s [директория] [тип_вывода]</strong> задает хранилище поколений.
 * Допустимо задавать <strong>-s [директория]</strong>.<br\>
 * <strong>[директория]</strong> путь к директории хранилища. Если параметр не задан используется каталог <strong>Storage</strong>.<br\>
 * <strong>[тип_вывода]</strong> данные в хранилище сохраняются в <strong>txt, xls, xlsx, json</strong>.
 * Если парметр не задан используется java сериализация.</p>
 * <br\>
 * <p>Если ключ <strong>-w</strong> или <strong>-f</strong> не заданы данные выводятся на консоль.
 * Если ключ <strong>-s</strong> не задан в качестве хранилища используется оперативная память.</p>
 */
public class Main {

    private static UserInterface getUserInterface(List<String> args) {
        UserInterface view;

        if (args.contains("-w")) {
            view = new WindowUI();
        } else if (args.contains("-f")) {
            Formatter formatter = new FileFormatter();
            int index = args.indexOf("-f");
            String fileName = getValue(args, index + 1);
            if (fileName != null) view = new TextFileUI(fileName, formatter);
            else throw new GameException("Не задано имя файла для вывода данных.");
        } else {
            Formatter formatter = new ConsoleFormatter();
            view = new ConsoleUI(formatter);
        }

        return view;
    }

    private static String getValue(List<String> args, int index) {
        String result = null;
        if (index < args.size()) {
            result = args.get(index);
            if (result.contains("-")) result = null;
        }
        return result;
    }

    private static Storage getStorage(List<String> args) {
        Storage storage;
        try {
            int index = args.indexOf("-s");
            if (index != -1) {
                String name = getValue(args, index + 1);
                String type = getValue(args, index + 2);
                FileStorageType sType;
                if (name == null) name = "Storage";
                if (type == null) sType = FileStorageType.NONE;
                else {
                    sType = FileStorageType.valueOf(type.toUpperCase());
                }

                storage = new FileThreadStorage(name, new GenerationEquals(), sType);
                ((StorageClearable) storage).clear();
            } else storage = new MemoryStorage(new GenerationEquals());
        } catch (GameException e) {
            System.out.println(e.getMessage());
            storage = new MemoryStorage(new GenerationEquals());
        }
        return storage;
    }

    private static void showHelp() {
        System.out.println("Ключ -w \tданные выводятся в графическом окне.");
        System.out.println("Ключ -f [имя_файла] \tданные выводятся в текстовый файл.");
        System.out.println("\t[имя_файла] \tпуть к файлу куда будут выводиться данные.");
        System.out.println("Ключ -s [директория] [тип_вывода] \tзадает хранилище поколений.\n" +
                "Допустимо задавать -s [директория].");
        System.out.println("\t[директория] \tпуть к директории хранилища. Если параметр не задан используется каталог Storage.");
        System.out.println("\t[тип_вывода] \tданные в хранилище сохраняются в txt, xls, xlsx, json. Если парметр не задан используется java сериализация.");
        System.out.println("Если ключ -w или -f не заданы данные выводятся на консоль.");
        System.out.println("Если ключ -s не задан в качестве хранилища используется оперативная память.");
    }

    public static void main(String[] args) throws IOException {
        List<String> argsList = Arrays.asList(args);
        if (argsList.contains("-h")) showHelp();
        else {
            UserInterface view;
            try {
                view = getUserInterface(argsList);
            } catch (GameException e) {
                System.out.println(e.getMessage());
                return;
            }

            Storage storage = getStorage(argsList);
            Algorithm algorithm = new BaseAlgorithm(storage);
            Game game = new Game(view, algorithm);
            game.run();
        }
    }
}
