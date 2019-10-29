package ru.sbt.javaschool.gameoflife;

import java.io.IOException;
import java.util.List;

public interface Loader {
    List<String> load() throws IOException;
}
