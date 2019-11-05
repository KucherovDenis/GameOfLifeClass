package ru.sbt.javaschool.gameoflife.storages;

public enum FileStorageType {
    XLS(".xls"), XLSX(".xlsx"), TXT(".txt");
    private final String extension;

    FileStorageType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
