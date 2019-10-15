package ru.sbt.javaschool.gameoflife;

import java.util.Objects;

class Cell {

    private static final String STR_ALIVE = "*";
    private static final String STR_DEAD = " ";

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

    public void addCountNeighbors(int count) {
        state = count == 3 ? CellState.ALIVE : state;
        state = ((count < 2) || (count > 3)) ? CellState.DEAD : state;
    }

    @Override
    public String toString() {
        String result = "";
        switch (state) {
            case ALIVE:
                result = STR_ALIVE;
                break;

            case DEAD:
                result = STR_DEAD;
                break;
        }

        return result;
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
