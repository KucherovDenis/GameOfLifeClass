package ru.sbt.javaschool.gameoflife;

import java.io.IOException;
import java.util.Objects;

public class Game {
    private final UserInterface view;

    private final Algoritm algoritm;

    public Game(UserInterface view, Algoritm algoritm) {
        this.view = Objects.requireNonNull(view);
        this.algoritm = Objects.requireNonNull(algoritm);
        this.view.init();
    }

    public void run() {
        greeting();

        GameCreator creator = Objects.requireNonNull(view.getCreator());
        GenerationBroker generation = null;
        try {
            generation = creator.getFirstGeneration();
            algoritm.initialize(generation);
        } catch (IOException e) {
            view.showErrorMessage(e.getMessage());
            return;
        }

        show(generation);
        do {
            generation = algoritm.nextGeneration();
            show(generation);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                view.showErrorMessage(e.getMessage());
            }
        } while (!algoritm.isEnd());
        endGame();
    }

    private void greeting() {
        view.showMessage("Добро пожаловать в симулюцию игры Жизнь.");
    }

    private void endGame() {
        view.showMessage("Конец игры");
        view.close();
    }

    private void show(GenerationBroker generation) {
        view.showMessage(String.format("Поколение №%d: ", generation.getCurrentGeneration()));
        view.draw(generation);
    }
}
