package ru.sbt.javaschool.gameoflife.entities;

import java.util.Arrays;
import java.util.Objects;

public class Generation implements GenerationBroker {

    private final int sizeX;

    private final int sizeY;

    private int generationNum;

    protected Cell[][] cells;

    public Generation(int sizeX, int sizeY) {
        this(sizeX, sizeY, 1);
    }

    public Generation(int sizeX, int sizeY, int num) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.generationNum = num;
        cells = createCells();
    }

    public Generation(GenerationBroker generation) {
        this(generation.getSizeX(), generation.getSizeY(), generation.getCurrentGeneration());
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

    public void initCell(int x, int y, CellState state) {
        cells[x][y].setState(state);
    }

    protected Cell[][] createCells() {
        Cell[][] result = new Cell[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                result[x][y] = new Cell();

        return result;
    }

    @Override
    public int getCurrentGeneration() {
        return generationNum;
    }

    @Override
    public Cell getCell(int x, int y) {
        if (x < 0 || x > getSizeX() || y < 0 || y > getSizeY()) return null;
        else return cells[x][y];
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSizeX(), getSizeY());
        result = 31 * result + Arrays.deepHashCode(cells);
        return result;
    }

    @Override
    public int getHashCode() {
        return this.hashCode();
    }


}
