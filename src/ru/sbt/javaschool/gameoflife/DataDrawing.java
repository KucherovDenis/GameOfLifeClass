package ru.sbt.javaschool.gameoflife;

import java.util.Objects;

public class DataDrawing implements DataBroker {

    public DataDrawing(int generation, Cell[][] cells) {
        this.generation = generation;
        this.cells = Objects.requireNonNull(cells);
    }

    private int generation;

    private Cell[][] cells;

    @Override
    public int getGeneration() {
        return generation;
    }

    @Override
    public Cell getCell(int x, int y) {
        if (x < 0 || x > getSizeX() || y < 0 || y > getSizeY()) return null;
        else return cells[x][y];
    }

    @Override
    public int getSizeX() {
        return cells.length;
    }

    @Override
    public int getSizeY() {
        if(cells[0] != null) return cells[0].length;
        else return 0;
    }
}
