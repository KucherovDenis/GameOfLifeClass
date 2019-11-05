package ru.sbt.javaschool.gameoflife.utils;

import ru.sbt.javaschool.gameoflife.GameException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    private static final String XLS_EXT = ".xls";
    private static final String XLS_EXT2 = ".xlsx";
    private static final String TXT_EXT = ".txt";

    public static List<String> readFilesFromDirectory(String folderPath) {
        final File folder = new File(folderPath);
        List<String> fileList = new ArrayList<>();
        final File[] files = folder.listFiles();
        if (files != null)
            for (File file : files) {
                if (!file.isDirectory()) {
                    fileList.add(folder.getPath() + "\\" + file.getName());
                }
            }

        return fileList;
    }

    private static boolean isExtension(String filePath, String extension) {
        Path path = Paths.get(filePath);
        return path.toString().toLowerCase().endsWith(extension);
    }

    public static boolean isXlsFile(String filePath) {
        return isExtension(filePath, XLS_EXT) || isExtension(filePath, XLS_EXT2);
    }

    public static boolean isOldXlsInterface(String filePath) {
        return isExtension(filePath, XLS_EXT);
    }

    public static boolean isTxtFile(String filePath) {
        return isExtension(filePath, TXT_EXT);
    }

    public static <T extends Serializable> void serialize(String fileName, T obj) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(obj);
        }
        catch (IOException e) {
            throw new GameException("Ошибка сериализации.", e);
        }
    }

    public static <T extends Serializable> T deserialize(String fileName, Class<T> cl) {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream inputStream = new ObjectInputStream(fis)) {
             Object o = inputStream.readObject();
             return cl.cast(o);
        }
        catch (IOException | ClassNotFoundException e) {
            throw new GameException("Ошибка десериализации.", e);
        }
    }
}
