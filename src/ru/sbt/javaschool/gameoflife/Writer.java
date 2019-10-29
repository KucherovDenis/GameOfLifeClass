package ru.sbt.javaschool.gameoflife;

import java.io.IOException;

public interface Writer {
    void save(String message) throws IOException;
}
