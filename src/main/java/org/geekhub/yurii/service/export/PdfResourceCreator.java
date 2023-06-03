package org.geekhub.yurii.service.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.geekhub.yurii.exception.ResourceCreationException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class PdfResourceCreator<E> extends DocumentResourceCreator<E> {

    public PdfResourceCreator(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public Resource createResource() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, out);

            document.open();
            createTable(document);
            document.close();

            return new ByteArrayResource(out.toByteArray());
        } catch (DocumentException | IOException e) {
            throw new ResourceCreationException("Failed creating pdf resource");
        }
    }

    private void createTable(Document document) throws DocumentException {
        int columns = clazz.getDeclaredFields().length;
        PdfPTable table = new PdfPTable(columns);
        table.setWidthPercentage(100);

        createHeaderRow(table);
        createDataRows(table);

        document.add(table);
    }

    private void createHeaderRow(PdfPTable table) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            PdfPCell cell = new PdfPCell(new Paragraph(field.getName()));
            table.addCell(cell);
        }
    }

    private void createDataRows(PdfPTable table) {
        for (E dataItem : data) {
            fillDataRow(table, dataItem);
        }
    }

    private void fillDataRow(PdfPTable table, E dataItem) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldValue = getFieldValue(field, dataItem);
            PdfPCell cell = new PdfPCell(new Paragraph(fieldValue));
            table.addCell(cell);
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
