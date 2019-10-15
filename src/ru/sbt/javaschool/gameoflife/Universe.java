package ru.sbt.javaschool.gameoflife;

import java.util.Arrays;
import java.util.Objects;

class Universe {

    private static final char SEPARATOR = '|';

    private static final int[][] offsets = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

    private int currentGeneration;

    public int getCurrentGeneration() {
        return currentGeneration;
    }

    private final int sizeX;

    private final int sizeY;

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    private final Cell[][] cells;

    public Universe(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        cells = new Cell[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                cells[x][y] = new Cell();
        currentGeneration = 1;
    }

    public static Universe copyOf(Universe other) {
        Universe copy = new Universe(other.getSizeX(), other.getSizeY());
        for (int x = 0; x < other.getSizeX(); x++)
            for (int y = 0; y < other.getSizeY(); y++)
                copy.initCell(x, y, other.getCell(x, y).getState());
        return copy;
    }

    public void initCell(int x, int y, CellState state) {
        cells[x][y].setState(state);
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Cell[] cs : cells) {
            out.append(SEPARATOR);
            for (Cell cell : cs)
                out.append(cell.toString()).append(SEPARATOR);
            out.append("\n");
        }
        return out.toString();
    }

    public void nextGeneration() {
        currentGeneration++;

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                int countNeighbors = 0;

                for (int[] d : offsets) {
                    int dx = x + d[0];
                    int dy = y + d[1];

                    if (dx < 0) dx = sizeX - 1;
                    else if (dx >= sizeX) dx = 0;

                    if (dy < 0) dy = sizeY - 1;
                    else if (dy >= sizeY) dy = 0;

                    if (cells[dx][dy].getState() == CellState.ALIVE)
                        countNeighbors++;
                }

                cells[x][y].addCountNeighbors(countNeighbors);
            }
        }
    }

    public boolean isDead() {
        boolean result = true;

        end:
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                if (cells[x][y].getState() != CellState.DEAD) {
                    result = false;
                    break end;
                }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Universe universe = (Universe) o;

        return sizeX == universe.getSizeX() &&
                sizeY == universe.getSizeY() &&
                Arrays.deepEquals(cells, universe.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSizeX(), getSizeY());
        result = 31 * result + Arrays.deepHashCode(cells);
        return result;
    }
}
