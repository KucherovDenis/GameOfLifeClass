package ru.sbt.javaschool.gameoflife.ui;

import ru.sbt.javaschool.gameoflife.creators.GameCreator;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;

import java.io.*;

public class TextFileUI extends ConsoleUI {

    private static final String MSG_ENDING = "Работа завершена.";
    private static final String MSG_NUM_GENERATION = "Поколение №%d...";

    private final String fileName;

    private PrintStream outFile = null;

    public TextFileUI(String fileName, Formatter formatter) {
        super(formatter);
        this.fileName = fileName;
    }

    @Override
    public void init() {
        super.init();
        if (outFile == null) {
            try {
                outFile = new PrintStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void showMessage(String message) {
        if (outFile == null) return;
        PrintStream outConsole = System.out;
        System.setOut(outFile);
        super.showMessage(message);
        System.setOut(outConsole);
    }

    @Override
    public void draw(GenerationBroker generation) {
        if (outFile == null) return;
        System.out.println(String.format(MSG_NUM_GENERATION, generation.getCurrentGeneration()));
        PrintStream outConsole = System.out;
        System.setOut(outFile);
        super.draw(generation);
        System.setOut(outConsole);
    }

    @Override
    public void close() {
        if(outFile != null) {
            outFile.close();
            outFile = null;
        }
        super.close();
        System.out.println(MSG_ENDING);
    }

    @Override
    public void showErrorMessage(String message) {
        if (outFile == null) return;
        System.out.println(message);
        PrintStream outConsole = System.out;
        System.setOut(outFile);
        super.showErrorMessage(message);
        System.setOut(outConsole);
    }

    @Override
    public boolean isStop() {
        return false;
    }
}
