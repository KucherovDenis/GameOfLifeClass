package ru.sbt.javaschool.gameoflife;

public class DefaultCreator implements GameCreator {
    @Override
    public Universe getUniverse() {
        Universe universe = new Universe(10, 10);
        for (int x = 0; x < universe.getSizeX(); x++)
            for (int y = 0; y < universe.getSizeY(); y++)
                universe.initCell(x, y, Math.random() > 0.75 ? CellState.ALIVE : CellState.DEAD);
        return universe;
    }
}
