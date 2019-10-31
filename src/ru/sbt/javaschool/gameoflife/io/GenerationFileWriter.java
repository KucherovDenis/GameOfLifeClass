package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;

import java.util.Objects;

public class GenerationFileWriter implements GenerationWriter {

    private final Writer writer;
    private final Formatter formatter;

    public GenerationFileWriter(Writer writer, Formatter formatter) {
        this.writer = Objects.requireNonNull(writer);
        this.formatter = Objects.requireNonNull(formatter);
    }

    @Override
    public void write(GenerationBroker generation) {
        String format = formatter.toString(generation);
        writer.save(format, formatter.getSplitter());
    }
}
