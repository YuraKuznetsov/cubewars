package org.geekhub.yurii.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class SqlReader {

    public String read(String fileName) {
        String path = getPath(fileName);

        try {
            Resource resource = new ClassPathResource(path);
            return new String(Files.readAllBytes(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Error while reading " + path, e);
        }
    }

    private String getPath(String fileName) {
        return "db/sql/" + fileName + ".sql";
    }
}
