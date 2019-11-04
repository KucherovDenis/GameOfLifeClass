package ru.sbt.javaschool.gameoflife.entities;

import ru.sbt.javaschool.gameoflife.GameException;

import java.util.Objects;
import java.util.regex.Pattern;

public class GameFieldSize {

    private static final String MSG_INVALID_FORMAT_DATA = "Не верный формат данных.";

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    private int sizeX;

    private int sizeY;

    public GameFieldSize(){
        this(-1, -1);
    }

    public GameFieldSize (int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public static boolean isMatch(String value) {
        return Pattern.matches("\\d{1,2}\\*\\d{1,2}", value);
    }

    public static GameFieldSize get(String value) {
        Objects.requireNonNull(value);
        boolean valid = isMatch(value);
        GameFieldSize fieldSize;
        if(valid) {
            String[] params = value.split("\\*");
            int sizeX = Integer.parseInt(params[0]);
            int sizeY = Integer.parseInt(params[1]);
            fieldSize = new GameFieldSize(sizeX, sizeY);
        } else throw new GameException(MSG_INVALID_FORMAT_DATA);
        return fieldSize;
    }

    public static String format() {
        return "{Число:2}*{Число:2}";
    }
}
