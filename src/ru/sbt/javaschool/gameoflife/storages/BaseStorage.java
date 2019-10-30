package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.Objects;

public abstract class BaseStorage {

    private final Equals<GenerationBroker> equalsImpl;

    public BaseStorage(Equals<GenerationBroker> equalsImpl) {
        this.equalsImpl = Objects.requireNonNull(equalsImpl);
    }

    public Equals<GenerationBroker> getEquals() {
        return  equalsImpl;
    }

    protected boolean equalsTo(GenerationBroker g1, GenerationBroker g2) {
        boolean result = false;
        if(g1 == null || g2 == null) return false;
        if (g1.getHashCode() == g2.getHashCode()) {
            result = getEquals().isEquals(g1, g2);
        }
        return result;
    }
}
