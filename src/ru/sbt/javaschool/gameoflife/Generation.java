package ru.sbt.javaschool.gameoflife;

import java.util.Objects;

public class Generation implements DataBroker {

    public Generation(int generationNum, Cell[][] cells) {
        this.generationNum = generationNum;
        this.cells = Objects.requireNonNull(cells);
    }

    private final int generationNum;

    private final Cell[][] cells;

    @Override
    public int getGenerationNum() {
        return generationNum;
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
