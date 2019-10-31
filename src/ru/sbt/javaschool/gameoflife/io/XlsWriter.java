package ru.sbt.javaschool.gameoflife.io;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.formatters.Splitter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class XlsWriter implements Writer {

    private final String fileName;
    private static final String SHEET_NAME = "Generation";

    public XlsWriter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(String message, Splitter splitter) {
        Objects.requireNonNull(splitter);
        File excel = new File(fileName);
        try (Workbook book = new HSSFWorkbook();
             FileOutputStream os = new FileOutputStream(excel);) {
            Sheet sheet = book.createSheet(SHEET_NAME);
            String[] lines = splitter.split(message);
            for (int i = 0; i < lines.length; i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(lines[i]);
            }

            sheet.autoSizeColumn(0);
            book.write(os);
        } catch (IOException e) {
            throw new GameException(String.format(IOMessages.MSG_FILEWRITE, fileName), e);
        }
    }
}
