package com.propeltech.addressbook.service;

import com.propeltech.addressbook.entity.Record;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecordService {

    List<Record> listAllRecords();
    ResponseEntity<Object> viewRecord(String email);
    ResponseEntity<Object> addRecord(Record record);
    ResponseEntity<Object> editRecord(Record record);
    ResponseEntity<Object> deleteRecord(String email);
    ResponseEntity<Object> validateRecord(Record record);



}