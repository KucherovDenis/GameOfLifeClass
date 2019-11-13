package ru.sbt.javaschool.gameoflife.io;

public interface DataBase {
    void clear();
    Loader getLoader();
    Writer getWriter();
    void close();
}
