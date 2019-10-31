package ru.sbt.javaschool.gameoflife.creators;

import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public class RandomCreator implements GameCreator {
    @Override
    public GenerationBroker getFirstGeneration() {
        Generation generation = new Generation(10, 20);
        for (int x = 0; x < generation.getSizeX(); x++)
            for (int y = 0; y < generation.getSizeY(); y++)
                generation.initCell(x, y, Math.random() > 0.75 ? CellState.ALIVE : CellState.DEAD);
        return generation;
    }
}