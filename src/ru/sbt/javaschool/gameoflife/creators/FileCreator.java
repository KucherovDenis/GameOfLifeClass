package ru.sbt.javaschool.gameoflife.creators;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.io.FileLoader;
import ru.sbt.javaschool.gameoflife.io.GenerationFileLoader;
import ru.sbt.javaschool.gameoflife.io.GenerationLoader;
import ru.sbt.javaschool.gameoflife.parsers.GenerationBaseParser;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

public class FileCreator implements GameCreator {

    private final GenerationLoader loader;

    private static final String MSG_GAMENOTCREATE = "Не удалось создать игру.";
    private static final String MSG_FILENOTSUPPORTED = "Не поддерживаемый формат файла.";

    public FileCreator(String fileName) {
        if (FileUtils.isTxtFile(fileName)) {
            loader = new GenerationFileLoader(new FileLoader(fileName), new GenerationBaseParser());
        } else if (FileUtils.isXlsFile(fileName)) {
            loader = null;
        } else throw new GameException(MSG_GAMENOTCREATE + "\n" + MSG_FILENOTSUPPORTED);
    }

    @Override
    public GenerationBroker getFirstGeneration() {
        GenerationBroker result = null;
        try {
            if (loader != null) result = loader.load();
        } catch (GameException e) {
            throw new GameException(MSG_GAMENOTCREATE, e);
        }
        return result;
    }
}
