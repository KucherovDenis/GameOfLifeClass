package ru.sbt.javaschool.gameoflife.formatters;

public class ConsoleFormatter extends BaseFormatter {

    public ConsoleFormatter() {
        SEPARATOR = "|";
        STR_ALIVE = "*";
        STR_DEAD = " ";
    }
}
