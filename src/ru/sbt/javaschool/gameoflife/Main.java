package ru.sbt.javaschool.gameoflife;

import ru.sbt.javaschool.gameoflife.algoritms.Algoritm;
import ru.sbt.javaschool.gameoflife.algoritms.BaseAlgoritm;
import ru.sbt.javaschool.gameoflife.entities.GenerationEquals;
import ru.sbt.javaschool.gameoflife.formatters.ConsoleFormatter;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;
import ru.sbt.javaschool.gameoflife.storages.*;
import ru.sbt.javaschool.gameoflife.ui.ConsoleUI;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;

public class Main {
    public static void main(String[] args) {

        /*Game game = new Game(new ConsoleDrawer());
        if (args.length == 3) {
            int sizeX = Integer.parseInt(args[1]);
            int sizeY = Integer.parseInt(args[2]);
            game.initialize(new FileCreator(args[0], sizeX, sizeY), 100);
        } else game.initialize(new RandomCreator());*/
        Formatter formatter = new ConsoleFormatter();
        UserInterface view  = new ConsoleUI(formatter);
        Storage storage = null;
        try {
            storage = new FileStorage("Storage", new GenerationEquals(), FileStorageType.XLS);
            ((StorageClearable)storage).clear();
        } catch (GameException e) {
            System.out.println(e.getMessage());
            storage = new MemoryStorage(new GenerationEquals());
        }
        Algoritm algoritm = new BaseAlgoritm(storage);
        Game game = new Game(view, algoritm);
        game.run();
    }
}
