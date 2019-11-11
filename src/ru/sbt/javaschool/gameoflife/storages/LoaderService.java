package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.io.*;
import ru.sbt.javaschool.gameoflife.parsers.GenerationBaseParser;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

class LoaderService {
    public static GenerationLoader getLoader(String fileName) {
        GenerationLoader result;
        Loader loader = null;
        if (FileUtils.isTxtFile(fileName)) loader = new FileLoader(fileName);
        else if (FileUtils.isXlsFile(fileName)) loader = new XlsLoader(fileName);
        else if (FileUtils.isJsonFile(fileName)) loader = new JsonLoader(fileName);

        if (loader != null) {
            result = new GenerationFileLoader(loader, new GenerationBaseParser());
        } else {
            result = new GenerationDeserializer(fileName);
        }

        return result;
    }
}
