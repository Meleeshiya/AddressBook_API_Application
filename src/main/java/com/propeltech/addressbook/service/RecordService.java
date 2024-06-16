package com.propeltech.addressbook.service;

import com.propeltech.addressbook.entity.Record;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecordService {

    List<Record> listAllRecords();
    Record viewRecord(String email);
    Record addRecord(Record record);
    Record editRecord(Record record);
    boolean deleteRecord(String email);
    ResponseEntity<Object> validateRecord(Record record);



}