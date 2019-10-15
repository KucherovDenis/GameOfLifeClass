package ru.sbt.javaschool.gameoflife;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(new ConsoleDrawer());
        game.initialize(new DefaultCreator());
        game.run();
    }
}
