package ru.sbt.javaschool.gameoflife.creators;

import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public class RandomCreator implements GameCreator {

    private final int sizeX;

    private final int sizeY;

    public RandomCreator () {
        this(20, 20);
    }

    public  RandomCreator(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public GenerationBroker getFirstGeneration() {
        Generation generation = new Generation(sizeX, sizeY);
        for (int x = 0; x < generation.getSizeX(); x++)
            for (int y = 0; y < generation.getSizeY(); y++)
                generation.initCell(x, y, Math.random() > 0.75 ? CellState.ALIVE : CellState.DEAD);
        return generation;
    }
}
