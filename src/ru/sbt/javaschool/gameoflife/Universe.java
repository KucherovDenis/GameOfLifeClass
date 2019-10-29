package ru.sbt.javaschool.gameoflife;

import java.util.Arrays;
import java.util.Objects;

class Universe extends Generation {

    private static final int[][] offsets = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

    private Cell[][] nextCells;


    public Universe(int sizeX, int sizeY) {
        super(sizeX, sizeY);

        nextCells = createCells();
    }

    public Universe(GenerationBroker generation) {
        this(generation.getSizeX(), generation.getSizeY());
        initCells(generation);
    }

    private void initCells(GenerationBroker generation) {
        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                Cell cell = generation.getCell(x, y);
                if (cell != null)
                    initCell(x, y, cell.getState());
            }
        }
    }

    public static Universe copyOf(Universe other) {
        Universe copy = new Universe(other.getSizeX(), other.getSizeY());
        for (int x = 0; x < other.getSizeX(); x++)
            for (int y = 0; y < other.getSizeY(); y++)
                copy.initCell(x, y, other.getCell(x, y).getState());
        return copy;
    }

    private int countNeighbors(int x, int y) {
        int result = 0;

        for (int[] d : offsets) {
            int dx = x + d[0];
            int dy = y + d[1];

            if (dx < 0) dx = getSizeX() - 1;
            else if (dx >= getSizeX()) dx = 0;

            if (dy < 0) dy = getSizeY() - 1;
            else if (dy >= getSizeY()) dy = 0;

            if (cells[dx][dy].getState() == CellState.ALIVE)
                result++;
        }

        return result;
    }

    public void nextGeneration() {
        incGeneration();

        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                nextCells[x][y].newValue(cells[x][y].getState(), countNeighbors(x, y));
            }
        }

        cells = nextCells;
        nextCells = createCells();
    }

    public boolean isDead() {
        boolean result = true;

        end:
        for (int x = 0; x < getSizeX(); x++)
            for (int y = 0; y < getSizeY(); y++)
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

        return getSizeX() == universe.getSizeX() &&
                getSizeY() == universe.getSizeY() &&
                Arrays.deepEquals(cells, universe.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSizeX(), getSizeY());
        result = 31 * result + Arrays.deepHashCode(cells);
        return result;
    }
}
