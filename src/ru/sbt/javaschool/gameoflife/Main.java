package ru.sbt.javaschool.gameoflife;

public class Main {
    public static void main(String[] args) {

        Game game = new Game(new ConsoleDrawer());
        if (args.length == 3) {
            int sizeX = Integer.parseInt(args[1]);
            int sizeY = Integer.parseInt(args[2]);
            game.initialize(new FileLoader(args[0], sizeX, sizeY), 100);
        } else game.initialize(new DefaultCreator());
        game.run();
    }
}
