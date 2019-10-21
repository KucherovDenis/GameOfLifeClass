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

    private Cell[][] currentCells;

    private Cell[][] nextCells;


    public Universe(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        currentCells = createCells();
        nextCells = createCells();
        currentGeneration = 1;
    }

    private Cell[][] createCells() {
        Cell[][] result = new Cell[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                result[x][y] = new Cell();

        return result;
    }

    public static Universe copyOf(Universe other) {
        Universe copy = new Universe(other.getSizeX(), other.getSizeY());
        for (int x = 0; x < other.getSizeX(); x++)
            for (int y = 0; y < other.getSizeY(); y++)
                copy.initCell(x, y, other.getCell(x, y).getState());
        return copy;
    }

    public void initCell(int x, int y, CellState state) {
        currentCells[x][y].setState(state);
    }

    public Cell getCell(int x, int y) {
        return currentCells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Cell[] cs : currentCells) {
            out.append(SEPARATOR);
            for (Cell cell : cs)
                out.append(cell).append(SEPARATOR);

            out.append('\n');
        }
        return out.toString();
    }

    private int countNeighbors(int x, int y) {
        int result = 0;

        for (int[] d : offsets) {
            int dx = x + d[0];
            int dy = y + d[1];

            if (dx < 0) dx = sizeX - 1;
            else if (dx >= sizeX) dx = 0;

            if (dy < 0) dy = sizeY - 1;
            else if (dy >= sizeY) dy = 0;

            if (currentCells[dx][dy].getState() == CellState.ALIVE)
                result++;
        }

        return result;
    }

    public void nextGeneration() {
        currentGeneration++;

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                nextCells[x][y].newValue(currentCells[x][y].getState(), countNeighbors(x, y));
            }
        }

        currentCells = nextCells;
        nextCells = createCells();
    }

    public boolean isDead() {
        boolean result = true;

        end:
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                if (currentCells[x][y].getState() != CellState.DEAD) {
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
                Arrays.deepEquals(currentCells, universe.currentCells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSizeX(), getSizeY());
        result = 31 * result + Arrays.deepHashCode(currentCells);
        return result;
    }
}
