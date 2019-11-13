package ru.sbt.javaschool.gameoflife.creators;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.io.FileLoader;
import ru.sbt.javaschool.gameoflife.io.GenerationBaseLoader;
import ru.sbt.javaschool.gameoflife.io.GenerationLoader;
import ru.sbt.javaschool.gameoflife.io.XlsLoader;
import ru.sbt.javaschool.gameoflife.parsers.GenerationBaseParser;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

public class FileCreator implements GameCreator {

    private final GenerationLoader loader;

    private static final String MSG_GAME_NOT_CREATE = "Не удалось создать игру.";
    private static final String MSG_FILE_NOT_SUPPORTED = "Не поддерживаемый формат файла.";

    public FileCreator(String fileName) {
        if (FileUtils.isTxtFile(fileName)) {
            loader = new GenerationBaseLoader(new FileLoader(fileName), new GenerationBaseParser());
        } else if (FileUtils.isXlsFile(fileName)) {
            loader = new GenerationBaseLoader(new XlsLoader(fileName), new GenerationBaseParser());
        } else throw new GameException(MSG_GAME_NOT_CREATE + "\n" + MSG_FILE_NOT_SUPPORTED);
    }

    @Override
    public GenerationBroker getFirstGeneration() {
        GenerationBroker result = null;
        try {
            if (loader != null) result = loader.load();
        } catch (GameException e) {
            throw new GameException(MSG_GAME_NOT_CREATE, e);
        }
        return result;
    }
}
