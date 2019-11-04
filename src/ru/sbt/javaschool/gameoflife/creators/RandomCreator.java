package ru.sbt.javaschool.gameoflife.creators;

import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.GameFieldSize;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public class RandomCreator implements GameCreator {

    private final GameFieldSize fieldSize;

    public RandomCreator() {
        this(20, 20);
    }

    public RandomCreator(int sizeX, int sizeY) {
        fieldSize = new GameFieldSize(sizeX, sizeY);
    }

    public RandomCreator(GameFieldSize size) {
        fieldSize = new GameFieldSize(size.getSizeX(), size.getSizeY());
    }

    @Override
    public GenerationBroker getFirstGeneration() {
        Generation generation = new Generation(fieldSize.getSizeX(), fieldSize.getSizeY());
        for (int x = 0; x < generation.getSizeX(); x++)
            for (int y = 0; y < generation.getSizeY(); y++)
                generation.initCell(x, y, Math.random() > 0.75 ? CellState.ALIVE : CellState.DEAD);
        return generation;
    }
}
