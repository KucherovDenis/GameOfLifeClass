package ru.sbt.javaschool.gameoflife.io;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import ru.sbt.javaschool.gameoflife.GameException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader implements Loader {

    private final String fileName;

    public JsonLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> load() {
        JsonFactory factory = new JsonFactory();
        List<String> result = new ArrayList<>();

        try (JsonParser jp = factory.createParser(new File(fileName));) {
            while (!jp.isClosed()) {
                JsonToken jsonToken = jp.nextToken();
                if (jsonToken == JsonToken.VALUE_STRING)
                    result.add(jp.getValueAsString());
            }
        } catch (IOException e) {
            throw new GameException(String.format(IOMessages.MSG_FILE_READ_ERROR, fileName), e);
        }

        return result;
    }
}
