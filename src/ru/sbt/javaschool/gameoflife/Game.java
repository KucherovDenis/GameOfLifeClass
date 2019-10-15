package ru.sbt.javaschool.gameoflife;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final GameDrawer drawer;

    private Universe universe = null;

    private final List<Universe> universeList;

    public Game(GameDrawer drawer) {
        this.drawer = drawer;
        universeList = new ArrayList<>();
    }

    public void initialize(GameCreator creator) {
        universe = creator.getUniverse();
    }


    public void run() {
        if (universe == null) throw new IllegalArgumentException("Не инициализирована переменная universe.");

        greeting();
        showUniverse();
        do {
            saveUniverse();
            universe.nextGeneration();
            showUniverse();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!isEnd());
        endGame();
    }

    private void greeting() {
        drawer.draw("Simulation start");
        drawer.draw("---------------------");
    }

    private boolean isEnd() {
        return universe.isDead() || isLoopReplay();
    }

    private boolean isLoopReplay() {
        return universeList.contains(universe);

    }

    private void endGame() {
        drawer.draw("---------------------");
        drawer.draw("The End");
    }

    private void showUniverse() {
        drawer.draw(String.format("Generation of the universe №%d: ", universe.getCurrentGeneration()));
        drawer.draw(universe.toString());
    }

    private void saveUniverse() {
        Universe copy = Universe.copyOf(universe);
        universeList.add(copy);
    }


}
