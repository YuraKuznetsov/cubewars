package org.geekhub.yurii.service.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.geekhub.yurii.exception.ResourceCreationException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class ExcelResourceCreator<E> extends DocumentResourceCreator<E> {

    public ExcelResourceCreator(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public Resource createResource() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             Workbook workbook = createWorkbook())
        {
            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());
        } catch (IOException e) {
            throw new ResourceCreationException("Failed generating excel resource");
        }
    }

    private Workbook createWorkbook() {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Cubewars");

        createHeaderRow(sheet);
        createDataRows(sheet);

        sheet.autoSizeColumn(0);

        return workbook;
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(field.getName());
        }
    }

    private void createDataRows(Sheet sheet) {
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            E dataItem = data.get(i);

            fillDataRow(row, dataItem);
        }
    }

    private void fillDataRow(Row row, E dataItem) {
        Field[] fields = clazz.getDeclaredFields();

        for (int j = 0; j < fields.length; j++) {
            Field field = fields[j];
            String fieldValue = getFieldValue(field, dataItem);

            Cell cell = row.createCell(j);
            cell.setCellValue(fieldValue);
        }
    }

    private String getFieldValue(Field field, E object) {
        try {
            field.setAccessible(true);
            return field.get(object).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }
}
