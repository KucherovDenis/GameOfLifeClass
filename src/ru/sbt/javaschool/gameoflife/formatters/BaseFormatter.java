package ru.sbt.javaschool.gameoflife.formatters;

import ru.sbt.javaschool.gameoflife.entities.Cell;
import ru.sbt.javaschool.gameoflife.entities.CellState;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;

public class BaseFormatter implements Formatter {

    private static final String EMPTY_STR = "";
    protected static final String NEW_LINE = "\n";

    protected String SEPARATOR;
    protected String STR_ALIVE;
    protected String STR_DEAD;

    public BaseFormatter() {
        SEPARATOR = EMPTY_STR;
        STR_ALIVE = EMPTY_STR;
        STR_DEAD = EMPTY_STR;
    }

    @Override
    public String toString(GenerationBroker generation) {
        StringBuilder out = new StringBuilder();
        for (int x = 0; x < generation.getSizeX(); x++) {
            out.append(SEPARATOR);
            for (int y = 0; y < generation.getSizeY(); y++) {
                Cell cell = generation.getCell(x, y);
                String s = EMPTY_STR;
                if (cell != null) {
                    if (cell.getState() == CellState.ALIVE) s = STR_ALIVE;
                    else s = STR_DEAD;
                }
                out.append(s).append(SEPARATOR);

            }
            out.append(NEW_LINE);
        }
        return out.toString();
    }

    @Override
    public Splitter getSplitter() {
        return new SplitterImp();
    }

    private static class SplitterImp implements Splitter {

        @Override
        public String[] split(String message) {
            return message.split(NEW_LINE);
        }
    }
}
