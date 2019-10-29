package ru.sbt.javaschool.gameoflife;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleUI implements UserInterface {

    private Scanner input = null;

    private final Formatter formatter;

    private static final int KEY_FILE_CREATOR = 1;
    private static final int KEY_RANDOM_CREATOR = 2;

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
        System.out.println("Выберите способ создания игры:");
        System.out.println(String.format("\t(%d) - загрузить первое поколение из файла;", KEY_FILE_CREATOR));
        System.out.println(String.format("\t(%d) - сформировать первое поколение случайным образом.", KEY_RANDOM_CREATOR));

        int result;
        while (true) {
            if (!input.hasNextInt()) {
                System.out.println("Значение должно быть числом.");
            } else {
                result = input.nextInt();
                if (result == KEY_FILE_CREATOR || result == KEY_RANDOM_CREATOR) break;
                else System.out.println("Значение должно быть (1) или (2).");
            }
        }

        return result;
    }

    private String getFileName() {
        System.out.println("Введите имя файла:");
        String result;
        while (true) {
            if (input.hasNext()) {
                result = input.next();
                if (result != null && !result.trim().isEmpty()) break;
            }
        }

        return result.trim();
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
                creator = new RandomCreator();
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
        System.out.println("Произошла ошибка! " + message);
    }
}
