package ru.sbt.javaschool.gameoflife;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MemoryStorage implements Storage {

    private List<GenerationBroker> generations;

    private final Equals<GenerationBroker> equalsImpl;

    public MemoryStorage(Equals<GenerationBroker> equalsImpl) {
        generations = new ArrayList<>();
        this.equalsImpl = Objects.requireNonNull(equalsImpl);
    }

    @Override
    public void add(GenerationBroker generation) {
        generations.add(generation);
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;

        end:
        for (GenerationBroker current : generations)
            if (current.getHashCode() == generation.getHashCode()) {
                result = equalsImpl.isEquals(current, generation);
                if (result) break end;
            }

        return result;
    }
}
