package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver implements Writer {

    private final String fileName;

    private static final String MSG_FILEWRITE = "Ошибка записи данных в файл %s.";

    public FileSaver(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(message);
        } catch (IOException e) {
            String msg = String.format(MSG_FILEWRITE, fileName);
            throw new GameException(msg, e);
        }
    }
}
