package ru.sbt.javaschool.gameoflife;

import ru.sbt.javaschool.gameoflife.algoritms.Algoritm;
import ru.sbt.javaschool.gameoflife.algoritms.BaseAlgoritm;
import ru.sbt.javaschool.gameoflife.entities.GenerationEquals;
import ru.sbt.javaschool.gameoflife.formatters.ConsoleFormatter;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;
import ru.sbt.javaschool.gameoflife.storages.*;
import ru.sbt.javaschool.gameoflife.ui.ConsoleUI;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;
import ru.sbt.javaschool.gameoflife.ui.WindowUI;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static UserInterface getUserInterface(List<String> args) {
        UserInterface view;

        if (args.contains("-w")) {
            view = new WindowUI();
        } else {
            Formatter formatter = new ConsoleFormatter();
            view = new ConsoleUI(formatter);
        }
        return view;
    }

    private static String getIndex(List<String> args, int index) {
        String result = null;
        if (index < args.size()) {
            result = args.get(index);
            if (result.contains("-")) result = null;
        }
        return result;
    }

    private static Storage getStorage(List<String> args) {
        Storage storage = null;
        try {
            int index = args.indexOf("-s");
            if (index != -1) {
                String name = getIndex(args, index + 1);
                String type = getIndex(args, index + 2);
                FileStorageType sType;
                if(name == null) name = "Storage";
                if(type == null) sType = FileStorageType.TXT;
                else {
                    sType = FileStorageType.valueOf(type.toUpperCase());
                }

                storage = new FileStorage(name, new GenerationEquals(), sType);
                ((StorageClearable) storage).clear();
            }
            else storage = new MemoryStorage(new GenerationEquals());
        } catch (GameException e) {
            System.out.println(e.getMessage());
            storage = new MemoryStorage(new GenerationEquals());
        }
        return storage;
    }

    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        UserInterface view = getUserInterface(argsList);
        Storage storage = getStorage(argsList);
        Algoritm algoritm = new BaseAlgoritm(storage);
        Game game = new Game(view, algoritm);
        game.run();
    }
}
