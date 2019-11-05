package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.formatters.Splitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver implements Writer {

    private final String fileName;

    public FileSaver(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(String message, Splitter splitter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(message);
        } catch (IOException e) {
            String msg = String.format(IOMessages.MSG_FILE_WRITE_ERROR, fileName);
            throw new GameException(msg, e);
        }
    }
}
