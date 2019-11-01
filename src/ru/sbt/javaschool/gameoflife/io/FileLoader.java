package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> load() {
        File file = new File(fileName);
        List<String> results = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) results.add(line);
            }
        } catch (FileNotFoundException e) {
            String message = String.format(IOMessages.MSG_FILE_NOT_FOUND, fileName);
            throw new GameException(message, e);
        } catch (IOException e) {
            String message = String.format(IOMessages.MSG_FILE_READ_ERROR, fileName);
            throw new GameException(message, e);
        }

        return results;
    }
}
