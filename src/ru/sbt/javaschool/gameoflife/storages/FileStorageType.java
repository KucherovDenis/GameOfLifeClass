package ru.sbt.javaschool.gameoflife.storages;

public enum FileStorageType {
    NONE(".bin"), XLS(".xls"), XLSX(".xlsx"), TXT(".txt"), JSON (".json");
    private final String extension;

    FileStorageType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static  FileStorageType getOf(String value) {
        value = value.toUpperCase();
        FileStorageType type = NONE;
        switch (value) {
            case "JSON":
            case "TXT":
            case "XLS":
            case "XLSX":
                type = FileStorageType.valueOf(value);
        }
        return type;
    }
}
