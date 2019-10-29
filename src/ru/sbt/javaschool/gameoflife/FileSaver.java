package ru.sbt.javaschool.gameoflife;

import java.io.BufferedWriter;
import  java.io.FileWriter;
import java.io.IOException;

public class FileSaver implements Writer {

    private final String fileName;

    public FileSaver(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(String message) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(message);
        }
    }
}
