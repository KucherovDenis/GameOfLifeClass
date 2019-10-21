package ru.sbt.javaschool.gameoflife;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final GameDrawer drawer;

    private Universe universe = null;

    private final List<Universe> universeList;

    private int countIteration = -1;

    public Game(GameDrawer drawer) {
        this.drawer = drawer;
        universeList = new ArrayList<>();
    }

    public void initialize(GameCreator creator, int countIteration) {
        universe = creator.getUniverse();
        this.countIteration = countIteration;
    }

    public void initialize(GameCreator creator) {
        universe = creator.getUniverse();
    }


    public void run() {
        if (universe == null) throw new NullPointerException("Объект не инициализирован.");

        greeting();
        show();
        do {
            saveUniverse();
            universe.nextGeneration();
            show();

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
        return countIteration == universe.getCurrentGeneration() || universe.isDead() || isReplay();
    }

    private boolean isReplay() {
        return universeList.contains(universe);

    }

    private void endGame() {
        drawer.draw("---------------------");
        drawer.draw("The End");
    }

    private void show() {
        drawer.draw(String.format("Generation of the universe №%d: ", universe.getCurrentGeneration()));
        drawer.draw(universe.toString());
    }

    private void saveUniverse() {
        Universe copy = Universe.copyOf(universe);
        universeList.add(copy);
    }


}
