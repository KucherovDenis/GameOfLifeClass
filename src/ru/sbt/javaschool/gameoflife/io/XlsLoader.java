package ru.sbt.javaschool.gameoflife.io;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.utils.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsLoader implements Loader {

    private final String fileName;

    public XlsLoader(String fileName) {
        this.fileName = fileName;
    }

    private void load(Workbook workbook, List<String> result) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            Cell cell = row.getCell(0);
            String message = cell.getStringCellValue();
            result.add(message);
        }
    }

    @Override
    public List<String> load() {
        File file = new File(fileName);
        List<String> result = new ArrayList<>();

        try (InputStream stream = new FileInputStream(file)) {
            Workbook workbook = null;
            if (FileUtils.isOldXlsInterface(fileName)) {
                workbook = new HSSFWorkbook(stream);
            } else {
                workbook = new XSSFWorkbook(stream);
            }

            load(workbook, result);
            workbook.close();
        } catch (FileNotFoundException e) {
            String message = String.format(IOMessages.MSG_FILE_NOT_FOUND, fileName);
            throw new GameException(message, e);
        } catch (IOException e) {
            String message = String.format(IOMessages.MSG_FILE_READ_ERROR, fileName);
            throw new GameException(message, e);
        }

        return result;
    }
}
