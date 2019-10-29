package ru.sbt.javaschool.gameoflife;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MemoryStorage implements Storage {

    private List<GenerationBroker> generations;

    private final Comparator<GenerationBroker> comparator;

    public MemoryStorage(Comparator<GenerationBroker> comparator) {
        generations = new ArrayList<>();
        this.comparator = Objects.requireNonNull(comparator);
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
                result = comparator.compare(current, generation) == 0;
                if (result) break end;
            }

        return result;
    }
}
