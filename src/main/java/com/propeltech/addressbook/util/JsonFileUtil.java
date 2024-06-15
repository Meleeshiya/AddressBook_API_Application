package com.propeltech.addressbook.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.propeltech.addressbook.entity.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileUtil {

    @Value("${json.file.path}")
    private String filePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Record> readRecords() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return objectMapper.readValue(file, new TypeReference<List<Record>>() {});
        } else {
            return new ArrayList<>();
        }
    }

    public void writeRecords(List<Record> records) throws IOException {
        objectMapper.writeValue(new File(filePath), records);
    }

}
