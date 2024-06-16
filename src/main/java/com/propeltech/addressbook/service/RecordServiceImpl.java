package com.propeltech.addressbook.service;

import com.propeltech.addressbook.entity.Record;
import com.propeltech.addressbook.util.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private JsonFileUtil jsonFileUtil;

    private List<Record> records;

    @PostConstruct
    public void init() {
        try {
            records = new ArrayList<>(jsonFileUtil.readRecords());
        } catch (IOException e) {
            records = new ArrayList<>();
        }
    }

    private void saveRecords() {
        try {
            jsonFileUtil.writeRecords(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Record> listAllRecords() {
        return records;
    }

    @Override
    public Record viewRecord(String email) {
        return records.stream()
                .filter(record -> record.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Record addRecord(Record record) {
        records.add(record);
        saveRecords();
        return record;
    }

    @Override
    public Record editRecord(Record record) {
        Optional<Record> existingEntry = records.stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(record.getEmail()))
                .findFirst();
        if (existingEntry.isPresent()) {
            records.remove(existingEntry.get());
            records.add(record);
            saveRecords();
            return record;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteRecord(String email) {
        boolean isRemoved = records.removeIf(entry -> entry.getEmail().equalsIgnoreCase(email));
        if (isRemoved) {
            saveRecords();
        }
        return isRemoved;
    }

}
