package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.formatters.FileSaveFormatter;
import ru.sbt.javaschool.gameoflife.io.*;
import ru.sbt.javaschool.gameoflife.parsers.GenerationBaseParser;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileStorage extends BaseStorage implements StorageClearable {

    private final String folder;

    private final String MSG_STORAGE_LOAD = "Ошибка чтения данных из хранилища.";
    private final String MSG_STORAGE_WRITE = "Ошибка записи данных в хранилище.";
    private final String MSG_STORAGE_NOT_CREATE = "Не возможно создать директорию %s.";
    private final String MSG_FORMAT_NOT_SUPPORTED = "Формат %s не поддерживается.";

    private final FileStorageType storageType;

    public FileStorage(String folder, Equals<GenerationBroker> equalsImpl) {
        this(folder, equalsImpl, FileStorageType.TXT);
    }

    public FileStorage(String folder, Equals<GenerationBroker> equalsImpl, FileStorageType storageType) {
        super(equalsImpl);
        this.folder = Objects.requireNonNull(folder);
        this.storageType = storageType;
        File dir = new File(folder);
        if (!dir.exists())
            if (!dir.mkdir())
                throw new GameException(String.format(MSG_STORAGE_NOT_CREATE, folder));
    }

    @Override
    public void clear() {
        List<String> files = FileUtils.readFilesFromDirectory(folder);
        for (String fileName : files) {
            final File file = new File(fileName);
            file.delete();
        }
    }

    private void write(String filePath, GenerationBroker generation) {
        try {
            final String fileName = filePath + storageType.getExtension();
            Writer writer;
            switch (storageType) {
                case TXT:
                    writer = new FileSaver(fileName);
                    break;

                case XLS:
                case XLSX:
                    writer = new XlsWriter(fileName);
                    break;
                case JSON:
                    writer = new JsonWriter(fileName);
                    break;
                default:
                    //throw new GameException(String.format(MSG_FORMAT_NOT_SUPPORTED, storageType.getExtension()));
                    writer = null;
            }

            GenerationWriter genWriter;
            if (writer != null) {
                genWriter = new GenerationFileWriter(writer, new FileSaveFormatter());
            } else {
                genWriter = new GenerationSerializer(fileName);
            }
            genWriter.write(generation);
        } catch (GameException e) {
            throw new GameException(MSG_STORAGE_WRITE, e);
        }
    }

    @Override
    public void add(GenerationBroker generation) {
        String filePath = folder + "\\" + generation.getCurrentGeneration();
        write(filePath, generation);
    }

    private GenerationBroker load(String fileName) {
        GenerationBroker result;

        try {
            Loader loader = null;
            if (FileUtils.isTxtFile(fileName)) loader = new FileLoader(fileName);
            else if (FileUtils.isXlsFile(fileName)) loader = new XlsLoader(fileName);
            else if (FileUtils.isJsonFile(fileName)) loader = new JsonLoader(fileName);

            GenerationLoader genLoader;
            if (loader != null) {
                genLoader = new GenerationFileLoader(loader, new GenerationBaseParser());
                //throw new GameException(String.format(MSG_FORMAT_NOT_SUPPORTED, storageType.getExtension()));
            } else {
                genLoader = new GenerationDeserializer(fileName);
            }
            result = genLoader.load();
        } catch (GameException e) {
            throw new GameException(MSG_STORAGE_LOAD, e);
        }
        return result;
    }

    @Override
    public boolean contains(GenerationBroker generation) {
        boolean result = false;
        List<String> files = FileUtils.readFilesFromDirectory(folder);

        for (int i = files.size() - 1; i != 0; i--) {
            String fileName = files.get(i);
            GenerationBroker current = load(fileName);
            result = equalsTo(generation, current);
            if (result) break;
        }


        return result;
    }
}
