package ru.sbt.javaschool.gameoflife.storages;

import org.omg.CORBA.Environment;
import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.entities.Equals;
import ru.sbt.javaschool.gameoflife.entities.GenerationBroker;
import ru.sbt.javaschool.gameoflife.formatters.FileFormatter;
import ru.sbt.javaschool.gameoflife.formatters.Formatter;
import ru.sbt.javaschool.gameoflife.io.*;
import ru.sbt.javaschool.gameoflife.parsers.GenerationBaseParser;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileStorage extends BaseStorage implements StorageClearable {

    private final String folder;

    private final String MSG_STORAGELOAD = "Ошибка чтения данных из хранилища.";
    private final String MSG_STORAGEWRITE = "Ошибка записи данных в хранилище.";
    private final String MSG_STORAGENOTCREATE = "Не возможно создать директорию %s.";
    private final String MSG_FORMATNOTSUPPORTED = "Формат %s не поддерживается.";

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
                throw new GameException(String.format(MSG_STORAGENOTCREATE, folder));
    }

    @Override
    public void clear() {
        List<String> files = FileUtils.readFilesFromDirectory(folder);
        for (String fileName : files) {
            final File file = new File(fileName);
            file.delete();
        }
    }

    @Override
    public void add(GenerationBroker generation) {
        String fileName = folder + "\\" + generation.getCurrentGeneration();
        try {

            fileName += storageType.getExtention();
            Writer writer = null;
            switch (storageType) {
                case TXT:
                    writer = new FileSaver(fileName);
                    break;

                case XLS:
                    writer = new XlsWriter(fileName);
                    break;
            }
            if(writer == null)
                throw new GameException(String.format(MSG_FORMATNOTSUPPORTED, storageType.getExtention()));

            GenerationWriter genWriter = new GenerationFileWriter(writer, new FileFormatter());
            genWriter.write(generation);
        } catch (GameException e) {
            throw new GameException(MSG_STORAGEWRITE, e);
        }
    }

    private GenerationBroker load(String fileName) {
        GenerationBroker result = null;

        try {
            Loader loader = null;
            if(FileUtils.isTxtFile(fileName)) loader = new FileLoader(fileName);
            else if(FileUtils.isXlsFile(fileName)) loader = new XlsLoader(fileName);
            if(loader == null)
                throw new GameException(String.format(MSG_FORMATNOTSUPPORTED, storageType.getExtention()));

            GenerationLoader genLoader = new GenerationFileLoader(loader, new GenerationBaseParser());
            result = genLoader.load();
        } catch (GameException e) {
            throw new GameException(MSG_STORAGELOAD, e);
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
