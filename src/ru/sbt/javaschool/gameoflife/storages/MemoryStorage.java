package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.ArrayList;
import java.util.List;

public class MemoryStorage extends BaseStorage {

    private final List<GenerationBroker> generations;

    public MemoryStorage(Equals<GenerationBroker> equalsImpl) {
        super(equalsImpl);
        generations = new ArrayList<>();
    }

    @Override
    public void add(GenerationBroker generation) {
        generations.add(generation);
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;

        for (GenerationBroker current : generations) {
            result = equalsTo(generation, current);
            if (result) break;
        }

        return result;
    }
}
