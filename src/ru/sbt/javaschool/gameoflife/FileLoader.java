package ru.sbt.javaschool.gameoflife;

import java.io.FileReader;
import java.io.IOException;

public class FileLoader implements GameCreator {

    private final int sizeX;

    private final int sizeY;

    private final String fileName;

    public FileLoader(String fileName, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.fileName = fileName;
    }

    @Override
    public Universe getUniverse() {
        Universe result = new Universe(sizeX, sizeY);
        try (FileReader reader = new FileReader(fileName)) {
            int r;
            int x = 0;
            int y = 0;

            do {
                r = reader.read();
                if (r == 10) continue;

                char c = (char) r;
                if (y != sizeY && (c == '.' || c == 'X')) {
                    if (c == 'X') result.initCell(x, y, CellState.ALIVE);
                    else result.initCell(x, y, CellState.DEAD);
                    y++;
                }

                if (r == 13) {
                    x++;
                    y = 0;
                }
            }
            while (r != -1 || x == sizeY);
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }
}
