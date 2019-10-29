package ru.sbt.javaschool.gameoflife;

import java.io.IOException;

public class GenerationFileWriter implements GenerationWriter {

    private final Writer writer;
    private final Formatter formatter;

    public  GenerationFileWriter(String fileName) {
        writer = new FileSaver(fileName);
        formatter = new FileFormatter();
    }

    @Override
    public void write(GenerationBroker generation) throws IOException {
        String format = formatter.toString(generation);
        writer.save(format);
    }
}
