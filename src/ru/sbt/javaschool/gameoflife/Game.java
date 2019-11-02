package ru.sbt.javaschool.gameoflife;

import ru.sbt.javaschool.gameoflife.algoritms.Algoritm;
import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;

import java.util.Objects;

public class Game {
    private final UserInterface view;

    private final Algoritm algoritm;

    private static final String MSG_GREETING = "Добро пожаловать в симулюцию игры Жизнь.";
    private static final String MSG_ENDGAME = "Конец игры";
    private static final String MSG_GENERATION = "Поколение №%d ";
    //private static

    public Game(UserInterface view, Algoritm algoritm) {
        this.view = Objects.requireNonNull(view);
        this.algoritm = Objects.requireNonNull(algoritm);
        this.view.init();
    }

    public void run() {
        greeting();


        GenerationBroker generation = null;
        try {
            GameCreator creator = Objects.requireNonNull(view.getCreator());
            generation = creator.getFirstGeneration();
            algoritm.initialize(generation);
        } catch (RuntimeException e) {
            showError(e);
            return;
        }

        show(generation);
        try {
            do {
                generation = algoritm.nextGeneration();
                show(generation);
                Thread.sleep(500);
            } while (!algoritm.isEnd());
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
