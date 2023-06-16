package org.geekhub.yurii.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SqlReader {

    public String read(String fileName) {
        String path = getPath(fileName);

        try {
            Resource resource = new ClassPathResource(path);
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading " + path, e);
        }
    }

    private String getPath(String fileName) {
        return "db/sql/" + fileName + ".sql";
    }
}
