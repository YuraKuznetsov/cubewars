package org.geekhub.yurii.service.export;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.geekhub.yurii.exception.ResourceCreationException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class WordResourceCreator<E> extends DocumentResourceCreator<E> {

    public WordResourceCreator(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public Resource createResource() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             XWPFDocument document = createDocument())
        {
            document.write(out);
            return new ByteArrayResource(out.toByteArray());
        } catch (IOException e) {
            throw new ResourceCreationException("Failed generating word resource");
        }
    }

    private XWPFDocument createDocument() {
        XWPFDocument document = new XWPFDocument();

        XWPFTable table = createTable(document);

        createHeaderRow(table);
        createDataRows(table);

        return document;
    }

    private XWPFTable createTable(XWPFDocument document) {
        int rows = data.size() + 1;
        int cols = clazz.getDeclaredFields().length;

        XWPFTable table = document.createTable(rows, cols);
        table.setWidth("100%");

        return table;
    }

    private void createHeaderRow(XWPFTable table) {
        XWPFTableRow headerRow = table.getRows().get(0);

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            XWPFTableCell cell = headerRow.getCell(i);
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

            cell.setText(field.getName());
        }
    }

    private void createDataRows(XWPFTable table) {
        for (int i = 0; i < data.size(); i++) {
            XWPFTableRow dataRow = table.getRow(i + 1);
            E dataItem = data.get(i);

            fillDataRow(dataRow, dataItem);
        }
    }

    private void fillDataRow(XWPFTableRow dataRow, E dataItem) {
        Field[] fields = clazz.getDeclaredFields();

        for (int j = 0; j < fields.length; j++) {
            Field field = fields[j];
            String fieldValue = getFieldValue(field, dataItem);

            XWPFTableCell cell = dataRow.getCell(j);
            cell.setText(fieldValue);
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
