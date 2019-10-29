package ru.sbt.javaschool.gameoflife;

public class GenerationEquals implements Equals<GenerationBroker> {
    @Override
    public boolean isEquals(GenerationBroker g1, GenerationBroker g2) {
        if (g1 == g2) return true;
        if (g1 == null || g2 == null) return false;
        if (g1.getSizeX() != g2.getSizeX() || g1.getSizeY() != g2.getSizeY()) return false;

        for (int x = 0; x < g1.getSizeX(); x++)
            for (int y = 0; y < g1.getSizeY(); y++) {
               Cell c1 = g1.getCell(x, y);
               Cell c2 = g2.getCell(x, y);
               if(!c1.equals(c2))
                   return false;
            }
        return true;
    }
}
