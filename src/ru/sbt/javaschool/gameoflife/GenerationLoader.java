package ru.sbt.javaschool.gameoflife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GenerationLoader implements Loader {

    private String fileName;
    private static final char DEAD = '.';
    private static final char ALIVE = 'X';

    public GenerationLoader(String fileName) {
        this.fileName = fileName;
    }

    private Generation createGeneration(BufferedReader reader) throws IOException {
        Generation result = null;
        String line = reader.readLine();
        if (line != null) {
            String[] param = line.split("\\s+");
            if (param.length >= 2) {
                int sizeX = Integer.parseInt(param[0]);
                int sizeY = Integer.parseInt(param[1]);
                if (param.length == 3) {
                    int num = Integer.parseInt(param[2]);
                    result = new Generation(sizeX, sizeY, num);
                } else result = new Generation(sizeX, sizeY);
            }
        }
        return result;
    }

    private void parsingLine(String line, int x, Generation gen) {
        int y = 0;
        for (char c : line.toCharArray()) {
            if (x == 12 && y == 35) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (c == DEAD) gen.initCell(x, y, CellState.DEAD);
            else if (Character.toUpperCase(c) == ALIVE) gen.initCell(x, y, CellState.ALIVE);
            y++;
        }
    }

    @Override
    public GenerationBroker getGeneration() throws IOException {
        File file = new File(fileName);
        Generation generation = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            generation = createGeneration(reader);

            String line;
            int x = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) parsingLine(line, x, generation);
                x++;
            }
        }

        return generation;
    }
}
