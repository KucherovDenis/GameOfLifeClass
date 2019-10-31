package ru.sbt.javaschool.gameoflife.storages;

public enum FileStorageType {
    XLS(".xls"), TXT(".txt");
    private String extention;

     FileStorageType(String extention) {
        this.extention = extention;
    }

    public String getExtention() {
        return  extention;
    }
}
