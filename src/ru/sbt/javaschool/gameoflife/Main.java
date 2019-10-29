package ru.sbt.javaschool.gameoflife;

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
        Algoritm algoritm = new BaseAlgoritm(null);
        Game game = new Game(view, algoritm);
        game.run();
    }
}
