package ru.sbt.javaschool.gameoflife;

import ru.sbt.javaschool.gameoflife.algorithms.Algorithm;
import ru.sbt.javaschool.gameoflife.storages.Storage;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;

public interface Settings {
    Storage getStorage();
    UserInterface getUserInterface();
    Algorithm getAlgorithm(Storage storage);
    boolean isHelp();
}
