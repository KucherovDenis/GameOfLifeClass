package ru.sbt.javaschool.gameoflife.io;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.sbt.javaschool.gameoflife.GameException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsLoader implements Loader {

    private final String fileName;

    public XlsLoader(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public List<String> load() {
        File file = new File(fileName);
        List<String> result = new ArrayList<>();
        try (InputStream stream = new FileInputStream(file);
             Workbook workbook = new HSSFWorkbook(stream);) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.rowIterator();
            while(iterator.hasNext()) {
                Row row = iterator.next();
                Cell cell = row.getCell(0);
                String message = cell.getStringCellValue();
                result.add(message);
            }
        } catch (FileNotFoundException e) {
            String message = String.format(IOMessages.MSG_FILENOTFOUND, fileName);
            throw new GameException(message, e);
        } catch (IOException e) {
            String message = String.format(IOMessages.MSG_FILEREAD, fileName);
            throw new GameException(message, e);
        }

        return result;
    }
}
