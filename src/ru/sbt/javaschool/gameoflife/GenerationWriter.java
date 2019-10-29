package ru.sbt.javaschool.gameoflife;

import java.io.IOException;

public interface GenerationWriter {
    void write(GenerationBroker generation) throws IOException;
}
