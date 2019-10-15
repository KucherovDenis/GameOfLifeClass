package ru.sbt.javaschool.gameoflife;

public class ConsoleDrawer implements GameDrawer {
    @Override
    public void draw(String message) {
        System.out.println(message);
    }
}
