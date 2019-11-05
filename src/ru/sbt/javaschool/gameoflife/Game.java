package ru.sbt.javaschool.gameoflife;

import ru.sbt.javaschool.gameoflife.algoritms.Algorithm;
import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;

import java.util.Objects;

public class Game {
    private final UserInterface view;

    private final Algorithm algorithm;

    private static final String MSG_GREETING = "Добро пожаловать в симулюцию игры Жизнь.";
    private static final String MSG_ENDGAME = "Конец игры";
    private static final String MSG_GENERATION = "Поколение №%d ";

    public Game(UserInterface view, Algorithm algorithm) {
        this.view = Objects.requireNonNull(view);
        this.algorithm = Objects.requireNonNull(algorithm);
        this.view.init();
    }

    public void run() {
        greeting();


        GenerationBroker generation;
        try {
            GameCreator creator = Objects.requireNonNull(view.getCreator());
            generation = creator.getFirstGeneration();
            algorithm.initialize(generation);
        } catch (RuntimeException e) {
            showError(e);
            return;
        }

        try {
            show(generation);
            Thread.sleep(1000);
            do {
                generation = algorithm.nextGeneration();
                show(generation);
                Thread.sleep(500);
            } while (!(view.isStop() || algorithm.isEnd()));
        } catch (GameException e) {
            showError(e);
        } catch (InterruptedException e) {
            view.showErrorMessage(e.getMessage());
        }
        endGame();
    }

    private void showError(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        String message = throwable.getMessage();
        sb.append(message);
        Throwable t = throwable.getCause();
        while (t != null) {
            sb.append("\n");
            message = t.getMessage();
            sb.append(message);
            t = t.getCause();
        }
        view.showErrorMessage(sb.toString());
    }

    private void greeting() {
        view.showMessage(MSG_GREETING);
    }

    private void endGame() {
        view.showMessage(MSG_ENDGAME);
        view.close();
    }

    private void show(GenerationBroker generation) {
        view.showMessage(String.format(MSG_GENERATION, generation.getCurrentGeneration()));
        view.draw(generation);
    }
}
