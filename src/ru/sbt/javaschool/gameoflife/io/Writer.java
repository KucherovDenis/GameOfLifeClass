package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.formatters.Splitter;

public interface Writer {
    default void save(String message) {
        save(message, null);
    }
    void save(String message, Splitter splitter);
}
