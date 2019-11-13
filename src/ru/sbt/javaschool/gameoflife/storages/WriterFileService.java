package ru.sbt.javaschool.gameoflife.storages;

import ru.sbt.javaschool.gameoflife.formatters.FileSaveFormatter;
import ru.sbt.javaschool.gameoflife.io.*;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

class WriterFileService {
    public static GenerationWriter getWriter(String fileName) {
        GenerationWriter result;
        Writer writer = null;
        if (FileUtils.isTxtFile(fileName)) writer = new FileSaver(fileName);
        else if (FileUtils.isXlsFile(fileName)) writer = new XlsWriter(fileName);
        else if (FileUtils.isJsonFile(fileName)) writer = new JsonWriter(fileName);

        if (writer != null) {
            result = new GenerationBaseWriter(writer, new FileSaveFormatter());
        } else {
            result = new GenerationSerializer(fileName);
        }

        return result;
    }
}
