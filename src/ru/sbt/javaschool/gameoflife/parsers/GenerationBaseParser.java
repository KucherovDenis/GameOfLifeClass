package ru.sbt.javaschool.gameoflife.parsers;

import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.Generation;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

import java.util.List;

public class GenerationBaseParser implements Parser {

    private static final char DEAD = '.';
    private static final char ALIVE = 'X';

    private Generation createGeneration(String line) {
        Generation result = null;

        if (line != null) {
            String[] params = line.split("\\s+");
            if (params.length >= 2) {
                int sizeX = Integer.parseInt(params[0]);
                int sizeY = Integer.parseInt(params[1]);
                if (params.length == 3) {
                    int num = Integer.parseInt(params[2]);
                    result = new Generation(sizeX, sizeY, num);
                } else result = new Generation(sizeX, sizeY);
            }
        }
        return result;
    }

    private void parsingLine(String line, int x, Generation gen) {
        int y = 0;
        for (char c : line.toCharArray()) {
            if (c == DEAD) gen.initCell(x, y, CellState.DEAD);
            else if (Character.toUpperCase(c) == ALIVE) gen.initCell(x, y, CellState.ALIVE);
            y++;
        }
    }

    @Override
    public GenerationBroker parsing(List<String> strings) {
        if (strings == null || strings.size() == 0) return null;

        Generation generation = createGeneration(strings.get(0));
        if (generation != null)
            for (int x = 0; x < strings.size() - 1; x++) {
                String line = strings.get(x + 1);
                if (line != null)
                    parsingLine(line, x, generation);
            }
        return generation;
    }
}
