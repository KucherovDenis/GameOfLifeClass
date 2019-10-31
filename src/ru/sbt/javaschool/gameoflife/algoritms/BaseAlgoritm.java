package ru.sbt.javaschool.gameoflife.algoritms;

import ru.sbt.javaschool.gameoflife.entities.Cell;
import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.storages.Storage;

public class BaseAlgoritm implements Algoritm {

    private static final int[][] offsets = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

    private Generation currentGen = null;

    private Generation nextGen = null;

    private int countIteration = -1;

    private final Storage storage;

    public BaseAlgoritm(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void initialize(GenerationBroker firstGeneration) {
        currentGen = new Generation(firstGeneration);
        nextGen = new Generation(currentGen.getSizeX(), currentGen.getSizeY(), currentGen.getCurrentGeneration() + 1);
    }

    private int countNeighbors(int x, int y) {
        int result = 0;

        for (int[] d : offsets) {
            int dx = x + d[0];
            int dy = y + d[1];

            if (dx < 0) dx = currentGen.getSizeX() - 1;
            else if (dx >= currentGen.getSizeX()) dx = 0;

            if (dy < 0) dy = currentGen.getSizeY() - 1;
            else if (dy >= currentGen.getSizeY()) dy = 0;

            Cell cell = currentGen.getCell(dx, dy);

            if (cell != null && cell.getState() == CellState.ALIVE) {
                result++;
            }
        }

        return result;
    }

    @Override
    public GenerationBroker nextGeneration() {
        if (currentGen == null) return null;

        for (int x = 0; x < currentGen.getSizeX(); x++) {
            for (int y = 0; y < currentGen.getSizeY(); y++) {
                Cell newCell = nextGen.getCell(x, y);
                Cell cell = currentGen.getCell(x, y);
                if (newCell != null && cell != null) {
                    newCell.newValue(cell.getState(), countNeighbors(x, y));
                }
            }
        }

        if (storage != null)
            storage.add(currentGen);
        currentGen = nextGen;
        nextGen = new Generation(currentGen.getSizeX(), currentGen.getSizeY(), currentGen.getCurrentGeneration() + 1);
        return currentGen;
    }

    @Override
    public void setCountIteration(int value) {
        if (value <= 0) countIteration = -1;
        else countIteration = value;
    }

    private boolean isDead() {
        boolean result = true;

        end:
        for (int x = 0; x < currentGen.getSizeX(); x++)
            for (int y = 0; y < currentGen.getSizeY(); y++) {
                Cell cell = currentGen.getCell(x, y);
                if (cell != null && cell.getState() != CellState.DEAD) {
                    result = false;
                    break end;
                }
            }

        return result;
    }

    private boolean isReplay() {
        boolean result = false;
        if (storage != null)
            result = storage.contains(currentGen);
        return result;
    }

    @Override
    public boolean isEnd() {
        boolean result = false;
        if (countIteration != -1) {
            if (countIteration == currentGen.getCurrentGeneration()) result = true;
        } else {
            result = isDead() || isReplay();
        }

        if(result && storage != null) {
            storage.add(currentGen);
        }

        return result;
    }
}