package ru.sbt.javaschool.gameoflife;


import ru.sbt.javaschool.gameoflife.algorithms.Algorithm;
import ru.sbt.javaschool.gameoflife.algorithms.BaseAlgorithm;
import ru.sbt.javaschool.gameoflife.storages.Storage;
import ru.sbt.javaschool.gameoflife.storages.StorageCloseable;
import ru.sbt.javaschool.gameoflife.ui.UserInterface;


/**
 * <p>Ключ <strong>-w</strong> данные выводятся в графическом окне.</p>
 * <br\>
 * <p>Ключ <strong>-f [имя_файла]</strong> данные выводятся в текстовый файл.<br\>
 * <strong>[имя_файла]</strong> путь к файлу куда будут выводиться данные.</p>
 * <br\>
 * <p>Ключ <strong>-sf [директория] [тип_вывода] [\t]</strong> задает хранилище поколений ввиде набора файлов.
 * Допустимо задавать <strong>-sf [директория]</strong>.<br\>
 * <strong>[директория]</strong> путь к директории хранилища. Если параметр не задан используется каталог <strong>Storage</strong>.<br\>
 * <strong>[тип_вывода]</strong> данные в хранилище сохраняются в <strong>txt, xls, xlsx, json</strong>.
 * Если парметр не задан используется java сериализация.
 * <strong>[\t]</strong> файлы будут читаться в отдельных потоках. Необязательный параметр.</p>
 * <br\>
 *  <p>Ключ <strong>-sd [имя_файла]</strong> задает хранилище поколений в базе данных.
 *  <strong>[имя_файла]</strong> файл базы данных. Обязательный параметр.</p>
 * <br\>
 * <p>Если ключ <strong>-w</strong> или <strong>-f</strong> не заданы данные выводятся на консоль.
 * Если ключи <strong>-sf</strong>,<strong>-sd</strong> не заданы в качестве хранилища используется оперативная память.</p>
 */
public class Main {
    private static void showHelp() {
        System.out.println("Ключ -w \tданные выводятся в графическом окне.");
        System.out.println("Ключ -f [имя_файла] \tданные выводятся в текстовый файл.");
        System.out.println("\t[имя_файла] \tпуть к файлу куда будут выводиться данные.");
        System.out.println("Ключ -sf [директория] [тип_вывода] [\\t] \tзадает хранилище поколений ввиде набора файлов.\n" +
                "Допустимо задавать -sf [директория].");
        System.out.println("\t[директория] \tпуть к директории хранилища. Если параметр не задан используется каталог Storage.");
        System.out.println("\t[тип_вывода] \tданные в хранилище сохраняются в txt, xls, xlsx, json. Если парметр не задан используется java сериализация.");
        System.out.println("\t[\\t] \t файлы будут читаться в отдельных потоках. Необязательный параметр.");
        System.out.println("Ключ -sd [имя_файла] \tзадает хранилище поколений в базе данных.\n");
        System.out.println("\t[имя_файла] \tфайл базы данных. Обязательный параметр.");
        System.out.println("Если ключ -w или -f не заданы данные выводятся на консоль.");
        System.out.println("Если ключи -sf, -sd не заданы в качестве хранилища используется оперативная память.");
    }

    public static void main(String[] args) {
        GameSettings settings = new GameSettings(args);
        if (settings.isHelp()) showHelp();
        else {
            UserInterface view;
            Storage storage;
            try {
                view = settings.getUserInterface();
                storage = settings.getStorage();
                storage.clear();
            } catch (GameException e) {
                System.out.println(e.getMessage());
                return;
            }

            Algorithm algorithm = new BaseAlgorithm(storage);
            Game game = new Game(view, algorithm);
            game.run();

            if (storage instanceof StorageCloseable)
                ((StorageCloseable) storage).close();
        }
    }
}
