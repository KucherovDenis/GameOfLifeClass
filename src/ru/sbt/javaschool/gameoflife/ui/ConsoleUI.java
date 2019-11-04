package ru.sbt.javaschool.gameoflife.ui;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.creators.FileCreator;
import ru.sbt.javaschool.gameoflife.entities.GameFieldSize;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;
import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.creators.RandomCreator;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleUI implements UserInterface {

    private Scanner input = null;

    private final Formatter formatter;

    private static final int KEY_FILE_CREATOR = 1;
    private static final int KEY_RANDOM_CREATOR = 2;

    private static final String MSG_ERROR = "Произошла ошибка! ";
    private static final String MSG_INVALID_FORMAT = "Не верный формат ввода. Значение должно быть числом.";
    private static final String MSG_INPUT_FILE = "Введите имя файла:";
    private static final String MSG_INVALID_VALUE = "Значение должно быть (%d) или (%d).";
    private static final String MSG_SELECT_CREATOR = "Выберите способ создания игры:";
    private static final String MSG_FILE_CREATOR = "\t(%d) - загрузить первое поколение из файла;";
    private static final String MSG_RANDOM_CREATOR = "\t(%d) - сформировать первое поколение случайным образом.";
    private static final String MSG_CONTINUE_GAME = "Продолжить игру? Да(y).";
    private static final String MSG_INPUT_FIELD_SIZE = "Укажите размер поля в формате %s. По умолчанию 20*20.";
    private static final String MSG_DEFAULT_CREATOR = "Задан загрузчик по умолчанию.";

    public ConsoleUI(Formatter formatter) {
        this.formatter = Objects.requireNonNull(formatter);
    }

    @Override
    public void init() {
        if (input == null) input = new Scanner(System.in);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    private int getKeyCreator() {
        System.out.println(MSG_SELECT_CREATOR);
        System.out.println(String.format(MSG_FILE_CREATOR, KEY_FILE_CREATOR));
        System.out.println(String.format(MSG_RANDOM_CREATOR, KEY_RANDOM_CREATOR));

        int result;
        while (true) {
            if (!input.hasNextInt()) {
                System.out.println(MSG_INVALID_FORMAT);
            } else {
                result = input.nextInt();
                if (result == KEY_FILE_CREATOR || result == KEY_RANDOM_CREATOR) break;
                else System.out.println(String.format(MSG_INVALID_VALUE, KEY_FILE_CREATOR, KEY_RANDOM_CREATOR));
            }
        }

        return result;
    }

    private String readInputString() {
        String result;
        while (true) {
            if (input.hasNext()) {
                result = input.next();
                if (result != null && !result.trim().isEmpty()) break;
            }
        }

        return result.trim();
    }

    private String getFileName() {
        System.out.println(MSG_INPUT_FILE);
        return readInputString();
    }

    private GameFieldSize getFieldSize() {
        System.out.println(String.format(MSG_INPUT_FIELD_SIZE, GameFieldSize.format()));
        String val = readInputString();
        return GameFieldSize.get(val);
    }

    @Override
    public GameCreator getCreator() {
        GameCreator creator = null;

        int key = getKeyCreator();
        switch (key) {
            case KEY_FILE_CREATOR:
                String fileName = getFileName();
                creator = new FileCreator(fileName);
                break;

            case KEY_RANDOM_CREATOR:
                try {
                    GameFieldSize size = getFieldSize();
                    creator = new RandomCreator(size);

                } catch (RuntimeException e) {
                    showErrorMessage(e.getMessage() + "\n" + MSG_DEFAULT_CREATOR);
                    creator = new RandomCreator();
                }
                break;
        }

        return creator;
    }

    @Override
    public void draw(GenerationBroker generation) {
        String result = formatter.toString(generation);
        System.out.println(result);
    }

    @Override
    public void close() {
        if (input != null) {
            input.close();
            input = null;
        }
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println(MSG_ERROR + message);
    }

    @Override
    public boolean isStop() {
        System.out.println(MSG_CONTINUE_GAME);
        String answer = input.next();
        return !"y".equals(answer.toLowerCase());
    }
}
