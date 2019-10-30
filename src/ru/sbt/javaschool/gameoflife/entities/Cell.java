package ru.sbt.javaschool.gameoflife.entities;

import java.util.Objects;

public class Cell {
    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        if (this.state == CellState.NONE) this.state = state;
    }

    private CellState state;

    public Cell() {
        this(CellState.NONE);
    }

    public Cell(CellState state) {
        this.state = state;
    }

    public void newValue(CellState state, int neighbors) {
        this.state = neighbors == 3 ? CellState.ALIVE : state;
        this.state = ((neighbors < 2) || (neighbors > 3)) ? CellState.DEAD : this.state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return getState() == cell.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getState());
    }
}
