package ru.sbt.javaschool.gameoflife.io;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.formatters.Splitter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JsonWriter implements Writer {
    private final String fileName;

    public JsonWriter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(String message, Splitter splitter) {
        Objects.requireNonNull(splitter);
        File json = new File(fileName);

        //ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();

        try (JsonGenerator jg = factory.createGenerator(json, JsonEncoding.UTF8);) {
            jg.writeStartObject();
            String[] lines = splitter.split(message);
            jg.writeFieldName("generation");
            jg.writeStartArray();
            for (String line : lines) {
                jg.writeString(line);
            }
            jg.writeEndArray();
            jg.writeEndObject();
        } catch (IOException e) {
            throw new GameException(String.format(IOMessages.MSG_FILE_WRITE_ERROR, fileName), e);
        }
    }
}
